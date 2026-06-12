# MoreOres 第四批矿物设计规格 (v0.0.4Alpha)

> 粘液科技附属插件 — 新增11种银系矿物资源
>
> 日期: 2026-06-08 | 版本: 0.4

---

## 1. 概述

在 v0.0.3Alpha 已有的32种矿物基础上，新增11种银系矿物。获取途径：挖掘掉落（含下界矿石）、磨石合成（含多输入/多配方）、GEO矿机（含NETHER环境限定群系）。本次新增首次涉及单一SlimefunItem对应多套GrindStone配方（锌锑方辉银矿）。

---

## 2. 新增矿物清单

| # | ID | 显示名 | 材质 | 获取方式 | 概率/产出 |
|---|-----|--------|------|----------|-----------|
| 33 | `ELECTRUM` | 银金矿 | YELLOW_WOOL | 挖掘普通金矿(仅GOLD_ORE) | 5% |
| 34 | `ARGENTITE` | 辉银矿 | CYAN_TERRACOTTA | GEO矿机 NETHER不限群系 | 1~3/区块 |
| 35 | `PYRARGYRITE` | 深红银矿 | NETHER_BRICKS | 挖掘下界金矿(NETHER_GOLD_ORE) | 10% |
| 36 | `PROUSTITE` | 淡红银矿 | NETHER_WART_BLOCK | 挖掘下界石英矿 | 10% |
| 37 | `CERARGYRITE` | 角银矿 | QUARTZ | GEO矿机 沙漠+恶地(NORMAL) | 1~3/区块 |
| 38 | `STEPHANITE` | 脆银矿 | BLACK_TERRACOTTA | 挖掘下界石英矿 | 5% |
| 39 | `DYSKRASITE` | 锑银矿 | RED_TERRACOTTA | 挖掘下界金矿(NETHER_GOLD_ORE) | 5% |
| 40 | `NAUMANNITE` | 硒银矿 | GRAY_TERRACOTTA | GEO矿机 NETHER BASALT_DELTAS | 1~3/区块 |
| 41 | `HESSITE` | 碲银矿 | GOLD_INGOT | 挖掘深层金矿(仅DEEPSLATE_GOLD_ORE) | 5% |
| 42 | `POLYBASITE` | 硫锑铜银矿 | LIGHT_GRAY_WOOL | 磨石: 16磨制黑石→1 | 16:1 |
| 43 | `FREIESLEBENITE` | 锌锑方辉银矿 | BLACK_WOOL | 磨石: 2闪锌矿→1 或 16磨制玄武岩→1 | 2:1 / 16:1 |

### 获取归类

| 类型 | 数量 | 矿物 |
|------|------|------|
| 挖掘掉落 | 6 | 银金矿、深红银矿、淡红银矿、脆银矿、锑银矿、碲银矿 |
| 磨石合成 | 2 | 硫锑铜银矿(1套配方)、锌锑方辉银矿(2套配方) |
| GEO矿机 | 3 | 辉银矿、角银矿、硒银矿 |

---

## 3. 代码变更

### 3.1 MoreOresItems.java

**新增 SlimefunItemStack 常量 (11个)：**

```java
ELECTRUM:       "ELECTRUM",       YELLOW_WOOL,      "&e银金矿"
ARGENTITE:      "ARGENTITE",      CYAN_TERRACOTTA,  "&3辉银矿"
PYRARGYRITE:    "PYRARGYRITE",    NETHER_BRICKS,    "&4深红银矿"
PROUSTITE:      "PROUSTITE",      NETHER_WART_BLOCK,"&c淡红银矿"
CERARGYRITE:    "CERARGYRITE",    QUARTZ,           "&f角银矿"
STEPHANITE:     "STEPHANITE",     BLACK_TERRACOTTA, "&8脆银矿"
DYSKRASITE:     "DYSKRASITE",     RED_TERRACOTTA,   "&c锑银矿"
NAUMANNITE:     "NAUMANNITE",     GRAY_TERRACOTTA,  "&7硒银矿"
HESSITE:        "HESSITE",        GOLD_INGOT,       "&6碲银矿"
POLYBASITE:     "POLYBASITE",     LIGHT_GRAY_WOOL,  "&7硫锑铜银矿"
FREIESLEBENITE: "FREIESLEBENITE", BLACK_WOOL,       "&8锌锑方辉银矿"
```

**新增配方数组：**

```java
// 16个磨制黑石(硫锑铜银矿)
new ItemStack(Material.POLISHED_BLACKSTONE, 16), null×8

// 16个磨制玄武岩(锌锑方辉银矿配方B)
new ItemStack(Material.POLISHED_BASALT, 16), null×8

// 2个闪锌矿(锌锑方辉银矿配方A) — 运行时构建: SPHALERITE.clone().setAmount(2)
```

**registerAll() 追加：**

```java
// 挖掘掉落
registerDropOre(ELECTRUM, "electrum", "银金矿", 2);
registerDropOre(PYRARGYRITE, "pyrargyrite", "深红银矿", 2);
registerDropOre(PROUSTITE, "proustite", "淡红银矿", 2);
registerDropOre(STEPHANITE, "stephanite", "脆银矿", 2);
registerDropOre(DYSKRASITE, "dyscrasite", "锑银矿", 2);
registerDropOre(HESSITE, "hessite", "碲银矿", 2);

// 磨石
ItemStack polishedBlackstone16 = new ItemStack(Material.POLISHED_BLACKSTONE, 16);
registerGrindOreCustom(POLYBASITE, "polybasite", "硫锑铜银矿", 3,
    new ItemStack[]{polishedBlackstone16, null×8});

ItemStack sphalerite2 = SPHALERITE.clone(); sphalerite2.setAmount(2);
registerGrindOreCustom(FREIESLEBENITE, "freieslebenite", "锌锑方辉银矿", 3,
    new ItemStack[]{sphalerite2, null×8});

ItemStack polishedBasalt16 = new ItemStack(Material.POLISHED_BASALT, 16);
// 仅注册SlimefunItem，不重复注册Research → 直接内联
new MoreOresItem(MORE_ORES, FREIESLEBENITE, RecipeType.GRIND_STONE,
    new ItemStack[]{polishedBasalt16, null×8})
    .setUseableInWorkbench(false)
    .register(plugin);

// GEO矿机
registerGeoOre(ARGENTITE, "argentite", "辉银矿", 3);
registerGeoOre(CERARGYRITE, "cerargyrite", "角银矿", 3);
registerGeoOre(NAUMANNITE, "naumannite", "硒银矿", 3);
```

> **锌锑方辉银矿双配方处理**：第一个配方注册时同时创建 SlimefunItem + Research；第二个配方仅创建 SlimefunItem（不重复Research）。Slimefun允许同一物品注册多个RecipeType配方。

### 3.2 OreMiningListener.java

**新增 Set：**

```java
private static final Set<Material> GOLD_ORE_ONLY = Set.of(Material.GOLD_ORE);
private static final Set<Material> NETHER_GOLD_ORE_TARGETS = Set.of(Material.NETHER_GOLD_ORE);
private static final Set<Material> NETHER_QUARTZ_ORE_TARGETS = Set.of(Material.NETHER_QUARTZ_ORE);
private static final Set<Material> DEEPSLATE_GOLD_ONLY = Set.of(Material.DEEPSLATE_GOLD_ORE);
```

**新增检查链（按顺序，先匹配先掉落）：**

```java
// 银金矿 — 仅普通金矿
// 深红银矿 — 下界金矿 10%
// 淡红银矿 — 下界石英 10%
// 脆银矿 — 下界石英 5%
// 锑银矿 — 下界金矿 5%
// 碲银矿 — 仅深层金矿 5%
```

### 3.3 MoreOresResources.java

**新增 GEO 资源类：**

| 资源类 | 环境 | 群系 |
|--------|------|------|
| ArgentiteResource | NETHER | 不限 |
| CerargyriteResource | NORMAL | DESERT_BADLANDS_BIOMES (复用现有) |
| NaumanniteResource | NETHER | BASALT_DELTAS |

**新增群系 Set：**

```java
private static final Set<Biome> BASALT_DELTAS_BIOME = Set.of(Biome.BASALT_DELTAS);
```

### 3.4 config.yml

```yaml
ores:
  electrum:
    chance: 0.05
  pyrargyrite:
    chance: 0.10
  proustite:
    chance: 0.10
  stephanite:
    chance: 0.05
  dyscrasite:
    chance: 0.05
  hessite:
    chance: 0.05

geo_miner:
  argentite:
    min: 1
    max: 3
  cerargyrite:
    min: 1
    max: 3
  naumannite:
    min: 1
    max: 3
```

### 3.5 pom.xml

版本 → `v0.0.4Alpha`

### 3.6 README.md

累加第四批表格，总数 32→43。

---

## 4. 设计决策

1. **银金矿/碲银矿分离**：银金矿仅 `GOLD_ORE`（普通，不含深层），碲银矿仅 `DEEPSLATE_GOLD_ORE`。
2. **下界金矿/下界石英矿共享目标**：深红银矿+锑银矿共享 `NETHER_GOLD_ORE`；淡红银矿+脆银矿共享 `NETHER_QUARTZ_ORE`。各自独立概率，先匹配先掉落。
3. **锌锑方辉银矿两套GrindStone配方**：第一套(闪锌矿2→1)同时注册SlimefunItem+Research；第二套(磨制玄武岩16→1)仅注册SlimefunItem不重复Research。Slimefun支持同一SlimefunItemKey注册多个配方类型。
4. **硒银矿BASALT_DELTAS**：仅在NETHER环境的BASALT_DELTAS群系提供供给。
5. **辉银矿NETHER不限群系**：与砷铅矿(Mimetite)模式一致，但使用不同config key。
