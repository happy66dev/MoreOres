# MoreOres 第二批矿物设计规格 (v0.0.2Alpha)

> 粘液科技附属插件 — 新增11种铅系矿物资源
>
> 日期: 2026-06-07 | 版本: 0.2

---

## 1. 概述

在 v0.0.1Alpha 已有的13种矿物基础上，新增11种铅系矿物。获取途径：挖掘原版方块掉落、GEO矿机。所有矿物继承相同的统一约束（不可放置、不可工作台合成、不可熔炉烧制、精准采集不掉落）。

本次变更最大化复用现有代码结构，仅做**增量添加**，不修改现有逻辑。

---

## 2. 新增矿物清单

### 2.1 挖掘掉落型 (8种)

| # | 物品ID | 显示名 | 材质 | 挖掘目标 | 概率 | 说明 |
|---|--------|--------|------|----------|------|------|
| 14 | `GALENA` | 方铅矿 | PURPLE_DYE | COPPER_ORE + DEEPSLATE_COPPER_ORE | 8% | |
| 15 | `CERUSSITE` | 白铅矿 | WHITE_DYE | COPPER_ORE + DEEPSLATE_COPPER_ORE | 4% | |
| 16 | `ANGLESITE` | 铅矾 | LIGHT_GRAY_DYE | COPPER_ORE + DEEPSLATE_COPPER_ORE | 2% | |
| 17 | `CROCOITE` | 铬铅矿 | ORANGE_DYE | DEEPSLATE_IRON_ORE | 3% | 仅深层，不含普通铁矿 |
| 18 | `BOULANGERITE` | 硫锑铅矿 | GRAY_DYE | LAPIS_ORE + DEEPSLATE_LAPIS_ORE | 2% | |
| 19 | `JAMESONITE` | 脆硫锑铅矿 | BLACK_DYE | EMERALD_ORE + DEEPSLATE_EMERALD_ORE | 10% | |
| 20 | `VANADINITE` | 钒铅矿 | RED_DYE | DEEPSLATE_REDSTONE_ORE | 5% | 仅深层 |
| 21 | `WULFENITE` | 钼铅矿 | GLOWSTONE | DIAMOND_ORE + DEEPSLATE_DIAMOND_ORE | 5% | |

> 注：方铅矿/白铅矿/铅矾共享相同的铜矿目标方块Set，节省代码。  
> 所有概率写入 `config.yml` 可自定义。

### 2.2 GEO矿机型 (3种)

| # | 物品ID | 显示名 | 材质 | 环境 | 群系 | 产出量/区块 |
|---|--------|--------|------|------|------|-------------|
| 22 | `PYROMORPHITE` | 磷氯铅矿 | LIME_DYE | NORMAL | RIVER(河流) + LAKE + OCEAN | 1 ~ 5 |
| 23 | `MIMETITE` | 砷铅矿 | YELLOW_DYE | NETHER | 无限制(任意下界群系) | 1 ~ 5 |
| 24 | `BOURNONITE` | 车轮矿 | GRAY_DYE | NORMAL | SNOWY(雪原系列群系) | 1 ~ 5 |

### 2.3 矿物汇总 (第二批)

| # | ID | 显示名 | 材质 | 类型 | 概率/产出 |
|---|-----|--------|------|------|-----------|
| 14 | GALENA | 方铅矿 | PURPLE_DYE | 挖掘掉落 | 8% |
| 15 | CERUSSITE | 白铅矿 | WHITE_DYE | 挖掘掉落 | 4% |
| 16 | ANGLESITE | 铅矾 | LIGHT_GRAY_DYE | 挖掘掉落 | 2% |
| 17 | CROCOITE | 铬铅矿 | ORANGE_DYE | 挖掘掉落 | 3% |
| 18 | BOULANGERITE | 硫锑铅矿 | GRAY_DYE | 挖掘掉落 | 2% |
| 19 | JAMESONITE | 脆硫锑铅矿 | BLACK_DYE | 挖掘掉落 | 10% |
| 20 | VANADINITE | 钒铅矿 | RED_DYE | 挖掘掉落 | 5% |
| 21 | WULFENITE | 钼铅矿 | GLOWSTONE | 挖掘掉落 | 5% |
| 22 | PYROMORPHITE | 磷氯铅矿 | LIME_DYE | GEO矿机 | 1~5/区块 |
| 23 | MIMETITE | 砷铅矿 | YELLOW_DYE | GEO矿机 | 1~5/区块 |
| 24 | BOURNONITE | 车轮矿 | GRAY_DYE | GEO矿机 | 1~5/区块 |

---

## 3. 代码变更清单

### 3.1 MoreOresItems.java

**新增 11 个 SlimefunItemStack 常量：**

```java
public static final SlimefunItemStack GALENA = new SlimefunItemStack(
    "GALENA", Material.PURPLE_DYE, "&5方铅矿", "", "&7挖掘铜矿有概率获得"
);
public static final SlimefunItemStack CERUSSITE = new SlimefunItemStack(
    "CERUSSITE", Material.WHITE_DYE, "&f白铅矿", "", "&7挖掘铜矿有概率获得"
);
public static final SlimefunItemStack ANGLESITE = new SlimefunItemStack(
    "ANGLESITE", Material.LIGHT_GRAY_DYE, "&7铅矾", "", "&7挖掘铜矿有概率获得"
);
public static final SlimefunItemStack CROCOITE = new SlimefunItemStack(
    "CROCOITE", Material.ORANGE_DYE, "&6铬铅矿", "", "&7挖掘深层铁矿有概率获得"
);
public static final SlimefunItemStack BOULANGERITE = new SlimefunItemStack(
    "BOULANGERITE", Material.GRAY_DYE, "&8硫锑铅矿", "", "&7挖掘青金石矿有概率获得"
);
public static final SlimefunItemStack JAMESONITE = new SlimefunItemStack(
    "JAMESONITE", Material.BLACK_DYE, "&0脆硫锑铅矿", "", "&7挖掘绿宝石矿有概率获得"
);
public static final SlimefunItemStack VANADINITE = new SlimefunItemStack(
    "VANADINITE", Material.RED_DYE, "&c钒铅矿", "", "&7挖掘深层红石矿有概率获得"
);
public static final SlimefunItemStack WULFENITE = new SlimefunItemStack(
    "WULFENITE", Material.GLOWSTONE, "&e钼铅矿", "", "&7挖掘钻石矿有概率获得"
);
public static final SlimefunItemStack PYROMORPHITE = new SlimefunItemStack(
    "PYROMORPHITE", Material.LIME_DYE, "&a磷氯铅矿", "", "&7通过GEO矿机在河流/湖泊/海洋群系获得"
);
public static final SlimefunItemStack MIMETITE = new SlimefunItemStack(
    "MIMETITE", Material.YELLOW_DYE, "&e砷铅矿", "", "&7通过GEO矿机在地狱获得"
);
public static final SlimefunItemStack BOURNONITE = new SlimefunItemStack(
    "BOURNONITE", Material.GRAY_DYE, "&7车轮矿", "", "&7通过GEO矿机在雪原群系获得"
);
```

**registerAll() 新增调用：**

```java
registerDropOre(GALENA, "galena", "方铅矿", 2);
registerDropOre(CERUSSITE, "cerussite", "白铅矿", 2);
registerDropOre(ANGLESITE, "anglesite", "铅矾", 2);
registerDropOre(CROCOITE, "crocoite", "铬铅矿", 2);
registerDropOre(BOULANGERITE, "boulangerite", "硫锑铅矿", 2);
registerDropOre(JAMESONITE, "jamesonite", "脆硫锑铅矿", 2);
registerDropOre(VANADINITE, "vanadinite", "钒铅矿", 2);
registerDropOre(WULFENITE, "wulfenite", "钼铅矿", 2);

registerGeoOre(PYROMORPHITE, "pyromorphite", "磷氯铅矿", 3);
registerGeoOre(MIMETITE, "mimetite", "砷铅矿", 3);
registerGeoOre(BOURNONITE, "bournonite", "车轮矿", 3);
```

### 3.2 OreMiningListener.java

**新增 6 组目标方块 Set：**

```java
private static final Set<Material> COPPER_ORE_TARGETS = Set.of(Material.COPPER_ORE, Material.DEEPSLATE_COPPER_ORE);
private static final Set<Material> DEEPSLATE_IRON_TARGETS = Set.of(Material.DEEPSLATE_IRON_ORE);
private static final Set<Material> LAPIS_ORE_TARGETS = Set.of(Material.LAPIS_ORE, Material.DEEPSLATE_LAPIS_ORE);
private static final Set<Material> EMERALD_ORE_TARGETS = Set.of(Material.EMERALD_ORE, Material.DEEPSLATE_EMERALD_ORE);
private static final Set<Material> DEEPSLATE_REDSTONE_TARGETS = Set.of(Material.DEEPSLATE_REDSTONE_ORE);
private static final Set<Material> DIAMOND_ORE_TARGETS = Set.of(Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE);
```

**onBlockBreak() 末尾新增 8 组检查链**（方铅矿/白铅矿/铅矾合并到一个铜矿目标Set，各自独立概率）：

```java
// 共享 COPPER_ORE_TARGETS
chance = plugin.getConfig().getDouble("ores.galena.chance", 0.08);
if (COPPER_ORE_TARGETS.contains(broken) && random.nextDouble() < chance) {
    dropOre(event, MoreOresItems.GALENA);
    return;
}
chance = plugin.getConfig().getDouble("ores.cerussite.chance", 0.04);
if (COPPER_ORE_TARGETS.contains(broken) && random.nextDouble() < chance) {
    dropOre(event, MoreOresItems.CERUSSITE);
    return;
}
chance = plugin.getConfig().getDouble("ores.anglesite.chance", 0.02);
if (COPPER_ORE_TARGETS.contains(broken) && random.nextDouble() < chance) {
    dropOre(event, MoreOresItems.ANGLESITE);
    return;
}
// crocoite / boulangerite / jamesonite / vanadinite / wulfenite ...
```

> 注意：COPPER_ORE_TARGETS 被方铅矿/白铅矿/铅矾共享，但它们各自由独立概率决定，最终只掉落一个（先匹配先掉落，与现有模式一致）。

### 3.3 MoreOresResources.java

**新增群系 Set：**

```java
private static final Set<Biome> RIVER_LAKE_BIOMES = Set.of(
    Biome.RIVER, Biome.FROZEN_RIVER
);
// OCEAN_BIOMES 已存在，追加 PYROMORPHITE 使用

private static final Set<Biome> SNOWY_BIOMES = Set.of(
    Biome.SNOWY_PLAINS, Biome.SNOWY_TAIGA, Biome.SNOWY_BEACH,
    Biome.ICE_SPIKES, Biome.FROZEN_OCEAN, Biome.DEEP_FROZEN_OCEAN,
    Biome.FROZEN_RIVER, Biome.FROZEN_PEAKS, Biome.JAGGED_PEAKS,
    Biome.SNOWY_SLOPES, Biome.GROVE
);
```

**新增 3 个 GEO 资源类（模式与现有完全一致）：**

- `PyromorphiteResource`: NORMAL 环境 + (RIVER/LAKE_BIOMES ∪ OCEAN_BIOMES)，产出量 `config geo_miner.pyromorphite.min/max` 默认 1~5
- `MimetiteResource`: NETHER 环境，不限群系，产出量 `config geo_miner.mimetite.min/max` 默认 1~5
- `BournoniteResource`: NORMAL 环境 + SNOWY_BIOMES，产出量 `config geo_miner.bournonite.min/max` 默认 1~5

**registerAll() 追加 3 行：**

```java
new PyromorphiteResource().register();
new MimetiteResource().register();
new BournoniteResource().register();
```

### 3.4 config.yml

**ores 节点追加：**

```yaml
  galena:
    chance: 0.08
  cerussite:
    chance: 0.04
  anglesite:
    chance: 0.02
  crocoite:
    chance: 0.03
  boulangerite:
    chance: 0.02
  jamesonite:
    chance: 0.10
  vanadinite:
    chance: 0.05
  wulfenite:
    chance: 0.05
```

**geo_miner 节点追加：**

```yaml
  pyromorphite:
    min: 1
    max: 5
  mimetite:
    min: 1
    max: 5
  bournonite:
    min: 1
    max: 5
```

### 3.5 README.md

新增第二批矿物表格，与第一批保持相同格式。

---

## 4. 不修改的部分

- `MoreOres.java` — 主插件类不变（MoreOresItems/MoreOresResources 内部自动处理）
- `pom.xml` — 版本号更新为 `v0.0.2Alpha`
- `LICENSE` — 不变
- `plugin.yml` — 不变
- 第一批 13 种矿物的任何逻辑 — 不变

---

## 5. 设计决策

1. **铜矿目标方块含深层**：Material.COPPER_ORE + DEEPSLATE_COPPER_ORE 都覆盖，与 Minecraft 1.18+ 矿石生成机制一致。
2. **方铅矿/白铅矿/铅矾共享 COPPER_ORE_TARGETS**：减少代码重复，各自独立概率决定掉落。
3. **NETHER 环境 GEO 资源**：需传入 `World.Environment.NETHER`，在 getDefaultSupply() 中只在该环境返回供给量。
4. **RIVER + LAKE 群系**：Minecraft 1.18+ 移除了一些 Biome 但 RIVER/FROZEN_RIVER 仍存在。LAKE 在 1.18+ 不存在，实际使用 RIVER + OCEAN 系列覆盖"河流、湖泊、海洋"语义。
5. **版本号 v0.0.2Alpha**：pom.xml version 从 `v0.0.1Alpha` 更新到 `v0.0.2Alpha`。
