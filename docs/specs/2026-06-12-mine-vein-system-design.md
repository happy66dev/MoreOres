# MoreOres 矿区系统设计规格 (v0.1.0)

> 粘液科技附属插件 — 区块矿脉系统 + 矿机 + 仓库 + 扫描器 + 钻头
>
> 日期: 2026-06-12 | 版本: 1.0-draft

---

## 1. 概述

在 MoreOres 已有43种矿物和机器的基础上，新增完整的**区块矿脉系统**。该系统将Minecraft世界划分为区块，每个区块拥有独立的矿量数据，玩家通过矿脉扫描器探测、矿机开采、矿产仓库存储来获取矿物。

### 核心概念

| 概念 | 定义 |
|------|------|
| **岩上空间** | 基岩以上的部分（不含太空），即 Y ≥ world.getMinHeight() 以上 |
| **岩下空间** | 基岩以下的部分，即 Y < world.getMinHeight()（通常 Y < -64） |
| **矿脉** | 区块中蕴含的矿物总量，以数量表示 |
| **矿区** | 矿量较高的区块（矿脉等级 ≥ 中），4096个区块中约128个 |

---

## 2. 子系统一：区块矿量系统 (MineVeinManager)

### 2.1 核心算法

每个区块的矿量由区块坐标 `(chunkX, chunkZ)` 作为伪随机种子确定，保证：
- 同一区块每次查询结果一致（确定性）
- 不同区块结果独立（随机性）

```java
long seed = world.getSeed() ^ (chunkX * 73856093L) ^ (chunkZ * 19349663L);
Random rng = new Random(seed);
```

### 2.2 矿脉等级

| 等级 | 矿量范围 | 概率 | 含义 |
|------|----------|------|------|
| 空 | 0 ~ 300 | 自动（非矿区） | 无商业价值，微量矿产 |
| 小 | 300 ~ 1,000 | 50%（矿区中） | 小型矿脉 |
| 中 | 1,000 ~ 15,000 | 30%（矿区中） | 中型矿脉 |
| 大 | 15,000 ~ 50,000 | 19.5%（矿区中） | 大型矿脉 |
| 超大 | 50,000+ | 0.5%（矿区中） | 超大型矿脉 |

### 2.3 矿区判定

1. 先判定是否为矿区：概率 = 128/4096 = 3.125%
2. 如果是矿区，再判定矿脉等级和具体矿量
3. 如果非矿区，矿量在 0~300 随机

```java
boolean isMiningZone = rng.nextDouble() < 0.03125;
if (isMiningZone) {
    double tierRoll = rng.nextDouble();
    if (tierRoll < 0.005)        // 超大 0.5%
        veinSize = 50000 + rng.nextInt(200000);
    else if (tierRoll < 0.02)    // 大 19.5%
        veinSize = 15000 + rng.nextInt(35000);
    else if (tierRoll < 0.32)    // 中 30%
        veinSize = 1000 + rng.nextInt(14000);
    else                         // 小 50%
        veinSize = 300 + rng.nextInt(700);
} else {
    veinSize = rng.nextInt(300); // 非矿区 0~300
}
```

### 2.4 岩上/岩下空间比例

| 环境 | 岩上空间占比 | 说明 |
|------|-------------|------|
| 矿区 | ~15% | 矿区中大部分矿在岩下 |
| 非矿区 | ~20% | 非矿区岩上占比稍高 |

```java
double surfaceRatio = isMiningZone ? 0.15 : 0.20;
int surfaceVein = (int)(veinSize * surfaceRatio);
int deepVein = veinSize - surfaceVein;
```

### 2.5 矿物种类分配

**矿物种类由区块所在群系决定**，每个群系有固定矿物池。矿物按稀有度分为三级权重：

| 稀有度 | 权重 | 含义 |
|--------|------|------|
| 高 (H) | 40 | 常见矿物 |
| 中 (M) | 20 | 较常见至较稀有 |
| 低 (L) | 5 | 稀有至极稀有 |

区块产出时，从群系矿物池中按权重随机选取 1~3 种矿物。高权重矿物被选中概率更高。

```java
// 加权随机选取
private SlimefunItemStack pickWeightedOre(List<WeightedOre> pool, Random rng) {
    int totalWeight = pool.stream().mapToInt(o -> o.rarity.weight).sum();
    int roll = rng.nextInt(totalWeight);
    for (WeightedOre ore : pool) {
        roll -= ore.rarity.weight;
        if (roll < 0) return ore.item;
    }
    return pool.get(pool.size() - 1).fallback;
}

int mineralCount = 1 + rng.nextInt(3); // 1~3种
List<SlimefunItemStack> minerals = pickMultipleWeighted(pool, mineralCount, rng);
```

详细的群系-矿物-权重映射表见 [biome-ore-mapping.md](biome-ore-mapping.md)。

### 2.6 数据存储

矿量数据存储在自定义数据文件中（不修改原版世界数据）：

**文件路径**: `plugins/MoreOres/vein_data/<worldName>.dat`

**存储格式**: 每条记录包含：
```
chunkX (int) | chunkZ (int) | veinSize (int) | isMiningZone (boolean) | minerals (String[])
```

**加载策略**: 懒加载 — 首次查询某区块时计算并缓存，后续从内存读取

### 2.7 数据类

```java
public class ChunkVeinData {
    int chunkX;
    int chunkZ;
    int totalVeinSize;       // 总矿量
    int remainingVeinSize;   // 剩余矿量（开采后减少）
    boolean isMiningZone;    // 是否矿区
    String[] mineralIds;     // 1~3种矿物ID
    double surfaceRatio;     // 岩上空间占比
}
```

---

## 3. 子系统二：矿脉扫描器 (VeinScanner)

### 3.1 基本属性

| 属性 | 值 |
|------|-----|
| ID | `VEIN_SCANNER` |
| 显示名 | &b矿脉扫描器 |
| 材质 | COMPASS |
| 合成台 | ENHANCED_CRAFTING_TABLE |
| 使用方式 | 右键使用，扫描后不消耗（可重复使用） |

### 3.2 扫描信息

右键使用后，向玩家发送以下信息（聊天消息），物品不消耗：

| 信息 | 说明 |
|------|------|
| 附近32格内矿区 | 列出32格内所有区块的矿脉等级 |
| 岩上空间矿产数量 | 当前区块的岩上空间矿量估算 |
| 矿区矿床种类 | 当前区块的矿物种类列表 |

### 3.3 数量估算规则

矿量四舍五入到最高位数，例如：
- 12,345 → "约 10,000"
- 567 → "约 600"
- 42 → "约 40"
- 3 → "约 0"

```java
private String estimateAmount(int amount) {
    if (amount == 0) return "0";
    int magnitude = (int) Math.pow(10, (int) Math.log10(amount));
    int rounded = Math.round((float) amount / magnitude) * magnitude;
    return "约 " + rounded;
}
```

### 3.4 合成配方 (3×3)

```
铁锭    红石    铁锭
红石  指南针  红石
铁锭    红石    铁锭
```

---

## 4. 子系统三：矿机 (ChunkMiner)

### 4.1 基本属性

| 属性 | 值 |
|------|-----|
| ID | `CHUNK_MINER` |
| 显示名 | &6矿机 |
| 材质 | NETHERITE_PICKAXE (head texture) 或 BLAST_FURNACE |
| 合成台 | ENHANCED_CRAFTING_TABLE |
| 基类 | AContainer (Slimefun电力机器) |
| UI | 3行27格，3输入+3输出 |

### 4.2 工作机制

1. 矿机放置在某个区块内
2. 获取当前区块的 `ChunkVeinData`（有矿脉的区块矿量大，无矿脉的区块矿量小，但矿物种类由群系决定）
3. **所有区块都能通过矿机获取矿物**：
   - 有矿脉区块：矿量大（300~50,000+），产出丰富
   - 无矿脉区块：矿量小（0~300），产出稀少，但矿物种类相同
4. 根据以下公式计算每周期产出速率：

```
产出速率 (矿物/周期) = 基础速率 × 深度系数 × 钻头系数
```

| 参数 | 说明 | 默认值 |
|------|------|--------|
| 基础速率 | 每个处理周期(10s)产出1个矿物 | 1 |
| 深度系数 | 矿机放置的Y坐标越低，系数越高 | 1.0 ~ 3.0 |
| 钻头系数 | 由安装的钻头等级决定 | 1.0 ~ 5.0（预留） |

### 4.3 深度系数计算

```java
double depthFactor = 1.0 + (double)(64 - minerY) / 64;
// Y=64 → 1.0, Y=0 → 2.0, Y=-64 → 3.0
// 低于-64时固定为3.0
if (minerY < -64) depthFactor = 3.0;
```

### 4.4 动态耗电

```
耗电 (J/s) = 基础耗电 × 深度系数 × 钻头系数
```

| 参数 | 值 |
|------|-----|
| 基础耗电 | 20 J/s (10 J/tick) |

### 4.5 采矿损耗

开采有损耗，并非矿有多少就能挖多少：
- 每次产出消耗的矿量 = 实际产出量 × 损耗系数
- 损耗系数 = 1.2（固定，即每产出1个矿物消耗1.2个矿量）

### 4.6 岩上/岩下空间产出

矿机在岩上空间和岩下空间都能开采矿物，但岩上空间占比受限：

- 有矿脉区块：岩上空间产出约占 15%
- 无矿脉区块：岩上空间产出约占 20%

岩下空间（Y < world.getMinHeight()）需要高级钻头才能开采，但产出更丰富（深度系数更高）。

### 4.7 剩余矿量耗尽

当区块的 `remainingVeinSize ≤ 0` 时，矿机停止工作并显示"矿脉已耗尽"。

### 4.7 UI 槽位

```
行1: 灰 灰 灰 灰 灰 灰 灰 灰 灰
行2: 灰 ① ① ① 箭 ② ② ② 灰
行3: 灰 灰 灰 灰 灰 灰 灰 灰 灰
```

- ① 输入槽（10, 11, 12）— 放置钻头、探测器等配件
- 箭 进度条（13）
- ② 输出槽（14, 15, 16）— 产出矿物

### 4.9 货运兼容

- 兼容原版漏斗（Hopper）
- 兼容粘液科技货运系统（CargoNet）
- 输出槽的物品可被漏斗/货运自动取出

### 4.9 合成配方 (3×3)

```
铁镐      钻石      铁镐
红石    电炉-I    红石
钢板      箱子      钢板
```

---

## 5. 子系统四：矿产仓库 (OreWarehouse)

### 5.1 基本属性

| 属性 | 值 |
|------|-----|
| ID | `ORE_WAREHOUSE` |
| 显示名 | &e矿产仓库 |
| 材质 | CHEST |
| 合成台 | ENHANCED_CRAFTING_TABLE |
| 基类 | SlimefunItem（自定义容器） |
| UI | 6行54格大箱子 |

### 5.2 工作机制

1. 矿产仓库是一个大容量存储容器
2. 通过漏斗/货运接收矿机产出的矿物
3. 玩家可手动打开UI取出矿物
4. 支持多种矿物同时存储（每个槽位独立）

### 5.3 货运兼容

- 兼容原版漏斗（Hopper）
- 兼容粘液科技货运系统（CargoNet）

### 5.4 合成配方 (3×3)

```
箱子      铁锭      箱子
铁锭    钻石块    铁锭
箱子      铁锭      箱子
```

---

## 6. 子系统五：钻头系统 (DrillBit)

### 6.1 设计理念

钻头是矿机的**配件**，安装在矿机的输入槽中，影响矿机的行为。等级系统预留框架，具体等级数量待定。

### 6.2 基本属性

| 属性 | 值 |
|------|-----|
| ID | `DRILL_BIT_TIER_1` ~ `DRILL_BIT_TIER_N` |
| 显示名 | 钻头 Mk.1 ~ Mk.N |
| 材质 | 各等级不同（预留） |
| 合成台 | ENHANCED_CRAFTING_TABLE |

### 6.3 钻头对矿机的影响

| 参数 | 钻头等级影响 |
|------|-------------|
| 产出速率 | 更高等级 = 更高钻头系数 |
| 耗电量 | 更高等级 = 更高耗电 |
| 采集深度 | 更高等级 = 可挖掘更深的矿脉（预留） |
| 岩下空间 | 高等级钻头可钻穿基岩（预留） |

### 6.5 框架预留

```java
public interface DrillBitProvider {
    int getTier();                    // 钻头等级
    double getSpeedMultiplier();      // 速度系数
    double getEnergyMultiplier();     // 耗电系数
    int getMaxDepth();                // 最大挖掘深度
    boolean canMineBedrock();         // 是否可钻穿基岩
}
```

---

## 7. 子系统六：钻头矿物探测器 (DrillProbe)

### 7.1 基本属性

| 属性 | 值 |
|------|-----|
| ID | `DRILL_PROBE` |
| 显示名 | &d钻头矿物探测器 |
| 材质 | SPYGLASS |
| 合成台 | ENHANCED_CRAFTING_TABLE |
| 使用方式 | 放入矿机的输入槽中（独立物品，与钻头共存） |

### 7.2 功能

放入矿机的输入槽后，矿机在开采过程中获得额外增益：

| 增益 | 效果 |
|------|------|
| 精确矿脉大小 | 矿机UI显示当前区块精确矿脉剩余量（而非估算值） |
| 矿脉高度范围 | 显示矿脉所在Y坐标范围 |
| 开采深度指示 | 显示矿机当前开采深度 |

增益信息显示在矿机的UI中（通过lore或动态物品更新）。

### 7.3 合成配方 (3×3)

```
红石      望远镜    红石
铁锭    指南针    铁锭
红石      玻璃      红石
```

---

## 8. 数据流

```
玩家放置矿机
    ↓
MineVeinManager.getVeinData(chunkX, chunkZ)
    ↓
ChunkVeinData { totalVeinSize, remainingVeinSize, minerals[], ... }
    ↓
ChunkMiner 根据 深度×钻头 计算产出速率
    ↓
每个处理周期：
    1. 检查 remainingVeinSize > 0
    2. 计算产出量 = baseRate × depthFactor × drillFactor
    3. 消耗矿量 = 产出量 × 1.2（损耗）
    4. remainingVeinSize -= 消耗矿量
    5. 输出矿物到输出槽
    ↓
漏斗/货运自动取出 → 矿产仓库存储
```

---

## 9. 文件变更清单

| 操作 | 文件 | 说明 |
|------|------|------|
| 新建 | `MineVeinManager.java` | 区块矿量系统核心 |
| 新建 | `ChunkVeinData.java` | 区块矿量数据类 |
| 新建 | `VeinScanner.java` | 矿脉扫描器 |
| 新建 | `ChunkMiner.java` | 矿机 (extends AContainer) |
| 新建 | `OreWarehouse.java` | 矿产仓库 |
| 新建 | `DrillBit.java` | 钻头系统（预留框架） |
| 新建 | `DrillProbe.java` | 钻头矿物探测器 |
| 修改 | `MoreOresItems.java` | 新增物品常量 + 注册 |
| 修改 | `MoreOres.java` | 注册新机器/物品 |
| 修改 | `config.yml` | 新增系统配置 |
| 修改 | `pom.xml` | 版本更新 |
| 修改 | `README.md` | 新增系统说明 |

---

## 10. config.yml 新增配置

```yaml
mine_vein:
  # 矿区概率 (128/4096)
  mining_zone_chance: 0.03125
  # 岩上空间占比
  surface_ratio_mining_zone: 0.15
  surface_ratio_normal: 0.20
  # 采集损耗系数
  mining_loss_factor: 1.2
  # 扫描器范围（格）
  scanner_range: 32

chunk_miner:
  # 基础耗电 (J/tick)
  base_energy_consumption: 10
  # 基础处理周期（秒）
  base_processing_time: 10
  # 基础产出量
  base_output_amount: 1
```

---

## 11. 设计决策记录

1. **矿量确定性** — 用区块坐标+世界种子做随机种子，保证同一区块每次查询结果一致
2. **懒加载** — 首次查询时计算并缓存，避免启动时遍历所有区块
3. **矿脉耗尽** — remainingVeinSize 递减，耗尽后矿机停止
4. **钻头预留** — 具体等级数量待定，但接口框架先定义好
5. **货运兼容** — 使用标准 Bukkit Inventory 接口，天然兼容漏斗和粘液货运
6. **损耗机制** — 1.2倍损耗系数，避免玩家无限制获取矿物
7. **卫星跳过** — 涉及外部addon依赖，暂不实现
8. **矿物种类群系决定** — 每个群系有固定矿物池，而非全局随机，增加真实感
9. **三级稀有度权重** — 高(40)、中(20)、低(5)，加权随机选取，常见矿物概率更高

---

## 12. Notes

- 本系统不依赖任何外部addon，全部在 MoreOres 内部实现
- 矿量数据文件存储在 `plugins/MoreOres/vein_data/` 目录下，按世界名分文件
- 钻头等级数量待定，但接口框架（DrillBitProvider）在 v0.1 中先定义好，后续只需新增实现类
- 矿机的 AContainer 基类天然兼容漏斗和粘液货运，无需额外代码
- 扫描器是 SlimefunItem（NotPlaceable），右键使用触发 PlayerInteractEvent
- 矿产仓库使用 Slimefun 的 BlockMenu 系统实现自定义容器UI
- 损耗系数（1.2）和矿区概率（3.125%）均可通过 config.yml 自定义

---

## 13. v0.1 框架架构图

```
┌─────────────────────────────────────────────────────────────┐
│                      MoreOres v0.1.0                        │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌──────────────────────────────────────────────────────┐   │
│  │              MineVeinManager (核心)                   │   │
│  │  - getVeinData(chunkX, chunkZ) → ChunkVeinData       │   │
│  │  - 基于种子的确定性矿量计算                            │   │
│  │  - 懒加载 + 内存缓存                                   │   │
│  │  - 文件持久化 (vein_data/<world>.dat)                  │   │
│  └──────────────────────┬───────────────────────────────┘   │
│                         │                                   │
│          ┌──────────────┼──────────────┐                    │
│          │              │              │                    │
│  ┌───────▼───────┐ ┌────▼────┐ ┌──────▼──────┐             │
│  │  VeinScanner  │ │ChunkMiner│ │ OreWarehouse│             │
│  │  矿脉扫描器   │ │  矿机    │ │  矿产仓库   │             │
│  │               │ │         │ │             │             │
│  │ 右键使用      │ │ AContainer│ │ BlockMenu  │             │
│  │ 扫描32格内   │ │         │ │ 大容量存储  │             │
│  │ 矿区/矿种    │ │ 3入3出   │ │ 兼容漏斗    │             │
│  │ 数量估算      │ │ 动态耗电 │ │ 兼容货运    │             │
│  └───────────────┘ │         │ └─────────────┘             │
│                    │ ┌───────┤                              │
│                    │ │配件槽 │                              │
│                    │ └───┬───┘                              │
│                    └─────┼──────────────────────────────────┘
│                          │                                  │
│              ┌───────────┴───────────┐                      │
│              │                       │                      │
│      ┌───────▼───────┐     ┌─────────▼─────────┐           │
│      │   DrillBit    │     │   DrillProbe      │           │
│      │   钻头(配件)  │     │ 钻头矿物探测器    │           │
│      │               │     │ (配件)            │           │
│      │ 速度/耗电系数 │     │ 精确矿脉大小      │           │
│      │ 深度限制      │     │ 矿脉高度范围      │           │
│      │ 等级框架预留  │     │ 当前开采深度      │           │
│      └───────────────┘     └───────────────────┘           │
│                                                             │
├─────────────────────────────────────────────────────────────┤
│  数据流:                                                    │
│                                                             │
│  区块坐标 ──→ MineVeinManager ──→ ChunkVeinData            │
│                                    │                        │
│  VeinScanner ──读取──→ ChunkVeinData ──→ 聊天消息          │
│                                                             │
│  ChunkMiner ──读取──→ ChunkVeinData                        │
│       │                                                     │
│       ├── 有矿脉区块: 矿量大,产出丰富                      │
│       ├── 无矿脉区块: 矿量小,产出稀少(矿物种类相同)       │
│       ├── 深度系数 × 钻头系数 → 产出速率                   │
│       ├── remainingVeinSize -= 产出量 × 1.2（损耗）        │
│       └── 输出矿物到输出槽                                  │
│            │                                                │
│            └──→ 漏斗/货运 ──→ OreWarehouse                 │
│                                                             │
│  DrillProbe ──安装在矿机输入槽──→ 显示矿脉详情             │
└─────────────────────────────────────────────────────────────┘
```
