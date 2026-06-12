# 矿脉群系矿物映射表

> 日期: 2026-06-12 | 修订: 2026-06-12

## 矿物稀有度等级

| 等级 | 权重 | 含义 |
|------|------|------|
| 高 (H) | 40 | 常见矿物，高概率产出 |
| 中 (M) | 20 | 较常见至较稀有 |
| 低 (L) | 5 | 稀有至极稀有 |
| V | — | MC原版物品（石英/萤石），非MoreOres矿物 |

## 群系-矿物-权重表

| 群系 | 矿物 | 稀有度 | 权重 | MC Biome 枚举 |
|------|------|:------:|:----:|---------------|
| 沙漠 | 铬铅矿 | 低 | 5 | DESERT |
| 热带草原 | 锡石 | 高 | 40 | SAVANNA, SAVANNA_PLATEAU, WINDSWEPT_SAVANNA |
| 热带草原 | 黄锡矿 | 中 | 20 | SAVANNA, SAVANNA_PLATEAU, WINDSWEPT_SAVANNA |
| 山地 | 闪锌矿 | 高 | 40 | JAGGED_PEAKS, FROZEN_PEAKS, STONY_PEAKS, SNOWY_SLOPES, GROVE, WINDSWEPT_HILLS, WINDSWEPT_GRAVELLY_HILLS, WINDSWEPT_FOREST, ICE_SPIKES |
| 山地 | 红锌石 | 低 | 5 | 同上 |
| 山地 | 镁锌石 | 低 | 5 | 同上 |
| 草原 | 方铅矿 | 高 | 40 | PLAINS, SUNFLOWER_PLAINS |
| 草原 | 闪锌矿 | 高 | 40 | 同上 |
| 草原 | 水镁石 | 高 | 40 | 同上 |
| 草原 | 辉银矿 | 高 | 40 | 同上 |
| 草原 | 脆硫锑铅矿 | 中 | 20 | 同上 |
| 草原 | 车轮矿 | 中 | 20 | 同上 |
| 草原 | 黄锡矿 | 中 | 20 | 同上 |
| 草原 | 深红银矿 | 中 | 20 | 同上 |
| 草原 | 淡红银矿 | 中 | 20 | 同上 |
| 草原 | 脆银矿 | 中 | 20 | 同上 |
| 草原 | 硫锑铜银矿 | 中 | 20 | 同上 |
| 草原 | 白铅矿 | 中 | 20 | 同上 |
| 草原 | 银金矿 | 中 | 20 | 同上 |
| 草原 | 硫锑铅矿 | 低 | 5 | 同上 |
| 草原 | 硒银矿 | 低 | 5 | 同上 |
| 草原 | 碲银矿 | 低 | 5 | 同上 |
| 草原 | 石英 | V | (原版) | 同上 |
| 草原 | 萤石 | V | (原版) | 同上 |
| 草甸 | 白铅矿 | 中 | 20 | MEADOW |
| 草甸 | 铅矾 | 中 | 20 | 同上 |
| 草甸 | 角银矿 | 中 | 20 | 同上 |
| 草甸 | 异锌矿 | 中 | 20 | 同上 |
| 草甸 | 菱锌矿 | 中 | 20 | 同上 |
| 草甸 | 铬铅矿 | 低 | 5 | 同上 |
| 草甸 | 磷氯铅矿 | 低 | 5 | 同上 |
| 草甸 | 钒铅矿 | 低 | 5 | 同上 |
| 草甸 | 砷铅矿 | 低 | 5 | 同上 |
| 草甸 | 钼铅矿 | 低 | 5 | 同上 |
| 暖水海洋 | 水氯镁石 | 高 | 40 | WARM_OCEAN |
| 恶地 | 闪锌矿 | 高 | 40 | BADLANDS, WOODED_BADLANDS, ERODED_BADLANDS |
| 恶地 | 水镁石 | 高 | 40 | 同上 |
| 雪山 | 锌锑方辉银矿 | 低 | 5 | SNOWY_SLOPES, ICE_SPIKES, JAGGED_PEAKS, GROVE, MUSHROOM_FIELDS |
| 云杉林 | 锑银矿 | 低 | 5 | OLD_GROWTH_SPRUCE_TAIGA, OLD_GROWTH_PINE_TAIGA, TAIGA, SNOWY_TAIGA |
| 山区 | 辉锑锡铅矿 | 低 | 5 | STONY_PEAKS, JAGGED_PEAKS, FROZEN_PEAKS, WINDSWEPT_HILLS, WINDSWEPT_GRAVELLY_HILLS, WINDSWEPT_FOREST, ICE_SPIKES, SNOWY_SLOPES, GROVE |
| 山区 | 圆柱锡矿 | 低 | 5 | 同上 |
| 山区 | 黑硫银锡矿 | 低 | 5 | 同上 |
| 深海 | 硫锡铅矿 | 低 | 5 | DEEP_OCEAN, DEEP_COLD_OCEAN, DEEP_LUKEWARM_OCEAN, DEEP_FROZEN_OCEAN |
| 沙滩 | 锡石 | 高 | 40 | BEACH, SNOWY_BEACH |
| 沙滩 | 水锡石 | 低 | 5 | 同上 |
| 热带雨林 | 马来亚石 | 低 | 5 | JUNGLE, BAMBOO_JUNGLE, SPARSE_JUNGLE |

## 群系与矿物的多对多关系（代码用）

> 每个区块查询时，先获取中心块所在群系，再从该群系的矿物池中按权重选取 1~3 种矿物。

| 矿物 | 稀有度 | 所有可出现的群系 |
|------|:------:|-----------------|
| 方铅矿 GALENA | 高 | PLAINS, SUNFLOWER_PLAINS |
| 闪锌矿 SPHALERITE | 高 | 山地系列, 草原系列, 恶地系列 |
| 锡石 CASSITERITE | 高 | 热带草原系列, 沙滩系列 |
| 辉银矿 ARGENTITE | 高 | PLAINS, SUNFLOWER_PLAINS |
| 水镁石 BRUCITE | 高 | PLAINS, SUNFLOWER_PLAINS, 恶地系列 |
| 水氯镁石 BISCHOFITE | 高 | WARM_OCEAN |
| 白铅矿 CERUSSITE | 中 | PLAINS, SUNFLOWER_PLAINS, MEADOW |
| 铅矾 ANGLESITE | 中 | MEADOW |
| 脆硫锑铅矿 JAMESONITE | 中 | PLAINS, SUNFLOWER_PLAINS |
| 车轮矿 BOURNONITE | 中 | PLAINS, SUNFLOWER_PLAINS |
| 黄锡矿 STANNITE | 中 | 热带草原系列, PLAINS, SUNFLOWER_PLAINS |
| 异锌矿 STACKED_ZINC_ORE | 中 | MEADOW |
| 菱锌矿 SMITHSONITE | 中 | MEADOW |
| 角银矿 CERARGYRITE | 中 | MEADOW |
| 深红银矿 PYRARGYRITE | 中 | PLAINS, SUNFLOWER_PLAINS |
| 淡红银矿 PROUSTITE | 中 | PLAINS, SUNFLOWER_PLAINS |
| 脆银矿 STEPHANITE | 中 | PLAINS, SUNFLOWER_PLAINS |
| 硫锑铜银矿 POLYBASITE | 中 | PLAINS, SUNFLOWER_PLAINS |
| 银金矿 ELECTRUM | 中 | PLAINS, SUNFLOWER_PLAINS |
| 铬铅矿 CROCOITE | 低 | DESERT, MEADOW |
| 硫锑铅矿 BOULANGERITE | 低 | PLAINS, SUNFLOWER_PLAINS |
| 磷氯铅矿 PYROMORPHITE | 低 | MEADOW |
| 钒铅矿 VANADINITE | 低 | MEADOW |
| 砷铅矿 MIMETITE | 低 | MEADOW |
| 钼铅矿 WULFENITE | 低 | MEADOW |
| 硫锡铅矿 TEALLITE | 低 | 深海系列 |
| 辉锑锡铅矿 FRANCKEITE | 低 | 山区系列 |
| 圆柱锡矿 CYLINDRITE | 低 | 山区系列 |
| 黑硫银锡矿 CANFIELDITE | 低 | 山区系列 |
| 马来亚石 MALAYAITE | 低 | JUNGLE 系列 |
| 水锡石 VARLAMOFFITE | 低 | 沙滩系列 |
| 锑银矿 DYSKRASITE | 低 | TAIGA 系列 |
| 硒银矿 NAUMANNITE | 低 | PLAINS, SUNFLOWER_PLAINS |
| 碲银矿 HESSITE | 低 | PLAINS, SUNFLOWER_PLAINS |
| 锌锑方辉银矿 FREIESLEBENITE | 低 | 雪山系列, MUSHROOM_FIELDS |
| 红锌石 RED_ZINCITE | 低 | 山地系列 |
| 镁锌石 MAGNESIUM_ZINC_ORE | 低 | 山地系列 |

## 未分配矿物

以下矿物尚未分配到任何群系的矿脉池中，等待后续补充：

| 矿物 | 说明 |
|------|------|
| 黄铁矿石 YELLOW_IRON_ORE | 第一批矿物 |
| 铝土矿 BAUXITE | 第一批矿物 |
| 蓝刚玉 BLUE_CORUNDUM | 第一批矿物（极稀有） |
| 红刚玉 RED_CORUNDUM | 第一批矿物（极稀有） |
| 方解石 → 水镁石 BRUCITE | 已通过磨石获取 |

> 注：黄铁矿石、铝土矿、蓝刚玉、红刚玉未在矿脉系统中使用，仅通过原版挖掘掉落获取。如需加入矿脉池请补充。

## 未覆盖MC群系

以下群系暂未分配矿物池，后续版本补充：

| 群系 | MC Biome 枚举 |
|------|---------------|
| 岩石海岸 | STONY_SHORE |
| 黑森林 | DARK_FOREST |
| 樱花树林 | CHERRY_GROVE |
| 繁茂洞穴 | LUSH_CAVES |
| 滴水石洞穴 | DRIPSTONE_CAVES |
