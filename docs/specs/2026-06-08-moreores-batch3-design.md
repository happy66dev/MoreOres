# MoreOres 第三批矿物设计规格 (v0.0.3Alpha)

> 粘液科技附属插件 — 新增8种锡系矿物资源
>
> 日期: 2026-06-08 | 版本: 0.3

---

## 1. 概述

在 v0.0.2Alpha 已有的24种矿物基础上，新增8种锡系矿物。获取途径：磨石合成、挖掘掉落、GEO矿机。本次新增首次涉及 THE_END 环境的GEO资源和 SlimefunItem 作为磨石配方输入。

---

## 2. 新增矿物清单

| # | ID | 显示名 | 材质 | 获取方式 | 概率/产出 |
|---|-----|--------|------|----------|-----------|
| 25 | `CASSITERITE` | 锡石 | BROWN_DYE | 磨石: 磨制花岗岩×1 | →1 |
| 26 | `STANNITE` | 黄锡矿 | MOSSY_COBBLESTONE | 挖掘金矿(含深层) | 10% |
| 27 | `TEALLITE` | 硫锡铅矿 | CHARCOAL | 磨石: 2方铅矿→1 | 2:1 |
| 28 | `FRANCKEITE` | 辉锑锡铅矿 | DEEPSLATE | GEO矿机 沙漠+恶地群系 | 1~3/区块 |
| 29 | `CYLINDRITE` | 圆柱锡矿 | BLACKSTONE | GEO矿机 SAVANNA群系 | 1~3/区块 |
| 30 | `CANFIELDITE` | 黑硫银锡矿 | AMETHYST_SHARD | 挖掘石头(STONE) | 3% |
| 31 | `MALAYAITE` | 马来亚石 | WHITE_TERRACOTTA | GEO矿机 草原(PLAINS)群系 | 1~3/区块 |
| 32 | `VARLAMOFFITE` | 水锡石 | ORANGE_TERRACOTTA | GEO矿机 末地(THE_END) | 1~3/区块 |

### 磨石合成型 (2种)

| 矿物 | 原料 | 产出 | 注意 |
|------|------|------|------|
| 锡石 | 磨制花岗岩(POLISHED_GRANITE) | 1 | GrindStone配方 |
| 硫锡铅矿 | 方铅矿(GALENA) ×2 | 1 | 输入是SlimefunItemStack，非原版Material |

### 挖掘掉落型 (2种)

| 矿物 | 挖掘目标 | 概率 |
|------|----------|------|
| 黄锡矿 | GOLD_ORE + DEEPSLATE_GOLD_ORE | 10% |
| 黑硫银锡矿 | Material.STONE (不含COBBLESTONE) | 3% |

### GEO矿机型 (4种)

| 矿物 | 环境 | 群系 |
|------|------|------|
| 辉锑锡铅矿 | NORMAL | DESERT + BADLANDS + WOODED_BADLANDS + ERODED_BADLANDS |
| 圆柱锡矿 | NORMAL | SAVANNA + SAVANNA_PLATEAU + WINDSWEPT_SAVANNA |
| 马来亚石 | NORMAL | PLAINS + SUNFLOWER_PLAINS |
| 水锡石 | THE_END | 不限群系 |

---

## 3. 代码变更

### 3.1 MoreOresItems.java

新增常量 + 注册调用。硫锡铅矿的磨石配方需在GALENA声明后构建：

```java
ItemStack[] GALENA_TWO = new ItemStack[]{
    new SlimefunItemStack("GALENA_TEMP", Material.PURPLE_DYE, "TEMP"), null, ... // ❌ 不可行
};
```

**推荐方案**：硫锡铅矿在registerAll()中单独注册，配方数组运行时读取GALENA：

```java
// 在registerAll()中
new MoreOresItem(MORE_ORES, TEALLITE, RecipeType.GRIND_STONE,
    new ItemStack[]{new ItemStack(GALENA, 2), null, null, null, null, null, null, null, null})
    .setUseableInWorkbench(false)
    .register(MoreOres.getInstance());
```

> GALENA 是 SlimefunItemStack，可直接 `new ItemStack(GALENA, 2)` 构造。

### 3.2 OreMiningListener.java

新增2组Set + 2组检查链：

```java
private static final Set<Material> GOLD_ORE_TARGETS = Set.of(Material.GOLD_ORE, Material.DEEPSLATE_GOLD_ORE);
// 复用现有 MAGNESIUM_ZINC_TARGETS = Set.of(Material.STONE) 或新增
private static final Set<Material> STONE_TARGETS = Set.of(Material.STONE);
```

### 3.3 MoreOresResources.java

新增群系Set + 4个GEO资源类（含水锡石的THE_END环境）。

### 3.4 config.yml

```yaml
ores:
  stannite:
    chance: 0.10
  canfieldite:
    chance: 0.03

geo_miner:
  franckeite:
    min: 1
    max: 3
  cylindrite:
    min: 1
    max: 3
  malayaite:
    min: 1
    max: 3
  varlamoffite:
    min: 1
    max: 3
```

### 3.5 pom.xml

版本 → `v0.0.3Alpha`

### 3.6 README.md

追加第三批矿物表格，总数 24→32。

---

## 4. 设计决策

1. **方铅矿作为磨石输入**：`new ItemStack(GALENA, 2)` 可在运行时构造，因GALENA是SlimefunItemStack继承ItemStack。
2. **黑硫银锡矿复用STONE_TARGETS**：与镁锌石共享 `Material.STONE` Set，节省代码。
3. **水锡石THE_END环境**：`getDefaultSupply()` 中判断 `environment == World.Environment.THE_END`。
4. **辉锑锡铅矿沙漠+恶地**：DESERT + BADLANDS + WOODED_BADLANDS + ERODED_BADLANDS。
