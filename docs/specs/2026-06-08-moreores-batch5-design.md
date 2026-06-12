# MoreOres 新物品+机器设计规格 (v0.0.5Alpha)

> 粘液科技附属插件 — 新增黄铁矿粉 + 普通加热反应室
>
> 日期: 2026-06-08 | 版本: 0.5

---

## 1. 概述

本次新增两类内容：
1. **黄铁矿粉** — 矿石粉碎机产物（资源物品）
2. **普通加热反应室** — 新电力机器，支持3输入3输出，进行加热化学反应

---

## 2. 新增物品：黄铁矿粉

| 属性 | 值 |
|------|-----|
| ID | `PYRITE_DUST` |
| 显示名 | &f黄铁矿粉 |
| 材质 | GLOWSTONE_DUST |
| 获取方式 | 矿石粉碎机(ORE_CRUSHER): 1黄铁矿石 → 2黄铁矿粉 |
| 约束 | NotPlaceable, setUseableInWorkbench(false) |

### 注册方式

黄铁矿粉需注册为 `RecipeType.ORE_CRUSHER` 物品，配方数组: `[YELLOW_IRON_ORE×1, null×8]`，并用 `SlimefunItemStack(PYRITE_DUST, 2)` 作为 recipeOutput（产出2个）：

```java
new MoreOresItem(MORE_ORES, PYRITE_DUST, RecipeType.ORE_CRUSHER,
    new ItemStack[]{YELLOW_IRON_ORE, null, null, null, null, null, null, null, null},
    new SlimefunItemStack(PYRITE_DUST, 2))
    .setUseableInWorkbench(false)
    .register(plugin);
```

---

## 3. 新增机器：普通加热反应室 (Heated Reaction Chamber)

### 3.1 基本属性

| 属性 | 值 |
|------|-----|
| ID | `HEATED_REACTION_CHAMBER` |
| 显示名 | &c普通加热反应室 |
| 材质 | BLAST_FURNACE |
| 耗电 | 30 J/s (每tick 15J) |
| 能量容量 | 256 J |
| 处理速度 | 1 |
| 基类 | AContainer |
| 合成台 | ENHANCED_CRAFTING_TABLE |

### 3.2 合成配方 (3×3)

```
玻璃          加热线圈(HEATING_COIL)    玻璃
加热线圈  电炉-I(ELECTRIC_FURNACE)    加热线圈
箱子          钢板(STEEL_PLATE)           箱子
```

代码数组：

```java
new ItemStack[]{
    new ItemStack(Material.GLASS),      SlimefunItems.HEATING_COIL,         new ItemStack(Material.GLASS),
    SlimefunItems.HEATING_COIL,         SlimefunItems.ELECTRIC_FURNACE,     SlimefunItems.HEATING_COIL,
    new ItemStack(Material.CHEST),      SlimefunItems.STEEL_PLATE,          new ItemStack(Material.CHEST)
}
```

### 3.3 UI 槽位设计

AContainer 默认使用 **45格(5行)** 布局。由于需求要求小型箱子(27格/3行)，需在机器类中**覆写 `constructMenu`** 自定义3行布局：

```
槽位编号(3行×9列):
行1: 0  1  2  3  4  5  6  7  8
行2: 9  10 11 12 13 14 15 16 17
行3: 18 19 20 21 22 23 24 25 26

实际分配:
灰: 0,1,2,3,4,5,6,7,8, 9, 13, 17, 18,19,20,21,22,23,24,25,26
输入: 10, 11, 12
进度条(箭头): 13
输出: 14, 15, 16
```

### 3.4 getInputSlots / getOutputSlots

```java
@Override
public int[] getInputSlots() {
    return new int[]{10, 11, 12};
}

@Override
public int[] getOutputSlots() {
    return new int[]{14, 15, 16};
}
```

边框槽位（灰色玻璃板）：0,1,2,3,4,5,6,7,8, 9, 13, 17, 18,19,20,21,22,23,24,25,26

### 3.5 机器配方 (MachineRecipe)

| # | 输入 | 输出 | 时间 |
|---|------|------|------|
| 1 | 黄铁矿粉 ×1 | 粗铁(RAW_IRON) ×1 | 3s |
| 2a | 粗铁 ×1 + 炭(COAL) ×1 | 铁锭(IRON_INGOT) ×1 | 6s |
| 2b | 粗铁 ×1 + 木炭(CHARCOAL) ×1 | 铁锭(IRON_INGOT) ×1 | 6s |
| 3 | 黄铁矿石 ×2 | 粗铁(RAW_IRON) ×3 | 12s |

> 配方2有两个变体：炭和木炭均可，需注册两条MachineRecipe。

### 3.6 配方注册代码

```java
// 配方1: 黄铁矿粉 → 粗铁
machine.registerRecipe(new MachineRecipe(3,
    new ItemStack[]{MoreOresItems.PYRITE_DUST},
    new ItemStack[]{new ItemStack(Material.RAW_IRON)}));

// 配方2a: 粗铁 + 炭 → 铁锭
machine.registerRecipe(new MachineRecipe(6,
    new ItemStack[]{new ItemStack(Material.RAW_IRON), new ItemStack(Material.COAL)},
    new ItemStack[]{new ItemStack(Material.IRON_INGOT)}));

// 配方2b: 粗铁 + 木炭 → 铁锭
machine.registerRecipe(new MachineRecipe(6,
    new ItemStack[]{new ItemStack(Material.RAW_IRON), new ItemStack(Material.CHARCOAL)},
    new ItemStack[]{new ItemStack(Material.IRON_INGOT)}));

// 配方3: 黄铁矿石×2 → 粗铁×3
machine.registerRecipe(new MachineRecipe(12,
    new ItemStack[]{new ItemStack(MoreOresItems.YELLOW_IRON_ORE, 2)},
    new ItemStack[]{new ItemStack(Material.RAW_IRON, 3)}));
```

---

## 4. 文件变更清单

| 操作 | 文件 | 内容 |
|------|------|------|
| 修改 | `MoreOresItems.java` | 新增 PYRITE_DUST 常量 + 注册 |
| 新建 | `HeatedReactionChamber.java` | 机器类 extends AContainer |
| 修改 | `MoreOres.java` | 在onEnable中注册机器 |
| 修改 | `README.md` | 追加新内容说明 |
| 修改 | `pom.xml` | 版本 → v0.0.5Alpha |

---

## 5. 设计决策

1. **黄铁矿粉是 NotPlaceable** — 与现有矿物保持一致。
2. **机器使用27格布局(3行)** — 符合 UI 描述（小型箱子）且匹配 AContainer 默认布局。
3. **配方2分两条** — `MachineRecipe` 不支持"或"逻辑，炭和木炭分别注册独立配方。
4. **配方3输入黄铁矿石×2** — 直接用 `new ItemStack(MoreOresItems.YELLOW_IRON_ORE, 2)` 构造。
5. **能量容量256J** — 维持10s满载供电的标准配置。
6. **进度条使用CLOCK** — 在槽位13显示，视觉上代表计时/反应进度。
