package io.github.happy66dev.moreores;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.api.researches.Research;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public final class MoreOresItems {

    public static final ItemGroup MORE_ORES = new ItemGroup(
            new NamespacedKey(MoreOres.getInstance(), "more_ores"),
            new CustomItemStack(Material.IRON_ORE, "&6MoreOres &7- 更多矿物")
    );

    public static final SlimefunItemStack YELLOW_IRON_ORE = new SlimefunItemStack(
            "YELLOW_IRON_ORE", Material.IRON_ORE, "&e黄铁矿石", "", "&7挖掘铁矿或铜矿有概率获得"
    );
    public static final SlimefunItemStack BAUXITE = new SlimefunItemStack(
            "BAUXITE", Material.COAL_ORE, "&8铝土矿", "", "&7挖掘黏土/铁矿/煤矿/红石矿有概率获得"
    );
    public static final SlimefunItemStack BLUE_CORUNDUM = new SlimefunItemStack(
            "BLUE_CORUNDUM", Material.DIAMOND, "&1蓝刚玉", "", "&7挖掘深层绿宝石有极低概率获得"
    );
    public static final SlimefunItemStack RED_CORUNDUM = new SlimefunItemStack(
            "RED_CORUNDUM", Material.REDSTONE, "&c红刚玉", "", "&7挖掘深层绿宝石有极低概率获得"
    );
    public static final SlimefunItemStack SPHALERITE = new SlimefunItemStack(
            "SPHALERITE", Material.IRON_ORE, "&f闪锌矿", "", "&7挖掘铜矿有概率获得"
    );
    public static final SlimefunItemStack STACKED_ZINC_ORE = new SlimefunItemStack(
            "STACKED_ZINC_ORE", Material.IRON_ORE, "&7异锌矿", "", "&7挖掘铁矿有概率获得"
    );
    public static final SlimefunItemStack SMITHSONITE = new SlimefunItemStack(
            "SMITHSONITE", Material.IRON_ORE, "&7菱锌矿", "", "&7挖掘铁矿有概率获得"
    );
    public static final SlimefunItemStack MAGNESIUM_ZINC_ORE = new SlimefunItemStack(
            "MAGNESIUM_ZINC_ORE", Material.IRON_ORE, "&7镁锌石", "", "&7挖掘石头有概率获得"
    );
    public static final SlimefunItemStack RED_ZINCITE = new SlimefunItemStack(
            "RED_ZINCITE", Material.REDSTONE_ORE, "&4红锌石", "", "&7通过磨石研磨方解石获得", "&7或挖掘红石矿有概率获得"
    );
    public static final SlimefunItemStack BRUCITE = new SlimefunItemStack(
            "BRUCITE", Material.ICE, "&b水镁石", "", "&7通过磨石研磨方解石获得"
    );
    public static final SlimefunItemStack BISCHOFITE = new SlimefunItemStack(
            "BISCHOFITE", Material.LAPIS_ORE, "&1水氯镁石", "", "&7通过GEO矿机在海洋/高山群系获得"
    );
    public static final SlimefunItemStack BRINE = new SlimefunItemStack(
            "BRINE", Material.WATER_BUCKET, "&3卤水", "", "&7通过GEO矿机在海洋/高山群系获得"
    );
    public static final SlimefunItemStack CARNALLITE = new SlimefunItemStack(
            "CARNALLITE", Material.DIAMOND_ORE, "&d光卤石", "", "&7通过GEO矿机在任意群系获得"
    );

    private static final ItemStack[] NULL_RECIPE = new ItemStack[] {
            null, null, null, null, null, null, null, null, null
    };

    private static final ItemStack[] CALCITE_GRIND_RECIPE = new ItemStack[] {
            new ItemStack(Material.CALCITE), null, null, null, null, null, null, null, null
    };

    private static int researchId = 9800;

    private MoreOresItems() {}

    public static void registerAll(MoreOres plugin) {
        registerDropOre(YELLOW_IRON_ORE, "yellow_iron_ore", "黄铁矿石", 1);
        registerDropOre(BAUXITE, "bauxite", "铝土矿", 2);
        registerDropOre(BLUE_CORUNDUM, "blue_corundum", "蓝刚玉", 5);
        registerDropOre(RED_CORUNDUM, "red_corundum", "红刚玉", 5);
        registerDropOre(SPHALERITE, "sphalerite", "闪锌矿", 1);
        registerDropOre(STACKED_ZINC_ORE, "stacked_zinc_ore", "异锌矿", 1);
        registerDropOre(SMITHSONITE, "smithsonite", "菱锌矿", 1);
        registerDropOre(MAGNESIUM_ZINC_ORE, "magnesium_zinc_ore", "镁锌石", 2);

        registerGrindOre(RED_ZINCITE, "red_zincite", "红锌石", 2);
        registerGrindOre(BRUCITE, "brucite", "水镁石", 1);

        registerGeoOre(BISCHOFITE, "bischofite", "水氯镁石", 3);
        registerGeoOre(BRINE, "brine", "卤水", 3);
        registerGeoOre(CARNALLITE, "carnallite", "光卤石", 3);
    }

    private static void registerDropOre(SlimefunItemStack item, String key, String name, int cost) {
        new MoreOresItem(MORE_ORES, item, RecipeType.NULL, NULL_RECIPE)
                .setUseableInWorkbench(false)
                .register(MoreOres.getInstance());
        new Research(new NamespacedKey(MoreOres.getInstance(), key), researchId++, name, cost)
                .addItems(item).register();
    }

    private static void registerGrindOre(SlimefunItemStack item, String key, String name, int cost) {
        new MoreOresItem(MORE_ORES, item, RecipeType.GRIND_STONE, CALCITE_GRIND_RECIPE)
                .setUseableInWorkbench(false)
                .register(MoreOres.getInstance());
        new Research(new NamespacedKey(MoreOres.getInstance(), key), researchId++, name, cost)
                .addItems(item).register();
    }

    private static void registerGeoOre(SlimefunItemStack item, String key, String name, int cost) {
        new MoreOresItem(MORE_ORES, item, RecipeType.GEO_MINER, NULL_RECIPE)
                .setUseableInWorkbench(false)
                .register(MoreOres.getInstance());
        new Research(new NamespacedKey(MoreOres.getInstance(), key), researchId++, name, cost)
                .addItems(item).register();
    }

    static class MoreOresItem extends SlimefunItem implements NotPlaceable {
        MoreOresItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
            super(itemGroup, item, recipeType, recipe);
        }
    }
}
