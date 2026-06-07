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
            "RED_ZINCITE", Material.REDSTONE_ORE, "&4红锌石", "", "&7挖掘红石矿有概率获得"
    );
    public static final SlimefunItemStack BRUCITE = new SlimefunItemStack(
            "BRUCITE", Material.ICE, "&b水镁石", "", "&7通过磨石研磨2个方解石获得"
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
    public static final SlimefunItemStack CASSITERITE = new SlimefunItemStack(
            "CASSITERITE", Material.BROWN_DYE, "&6锡石", "", "&7通过磨石研磨磨制花岗岩获得"
    );
    public static final SlimefunItemStack STANNITE = new SlimefunItemStack(
            "STANNITE", Material.MOSSY_COBBLESTONE, "&2黄锡矿", "", "&7挖掘黄金矿有概率获得"
    );
    public static final SlimefunItemStack TEALLITE = new SlimefunItemStack(
            "TEALLITE", Material.CHARCOAL, "&8硫锡铅矿", "", "&7通过磨石研磨2个方铅矿获得"
    );
    public static final SlimefunItemStack FRANCKEITE = new SlimefunItemStack(
            "FRANCKEITE", Material.DEEPSLATE, "&7辉锑锡铅矿", "", "&7通过GEO矿机在沙漠/恶地群系获得"
    );
    public static final SlimefunItemStack CYLINDRITE = new SlimefunItemStack(
            "CYLINDRITE", Material.BLACKSTONE, "&8圆柱锡矿", "", "&7通过GEO矿机在金合欢森林群系获得"
    );
    public static final SlimefunItemStack CANFIELDITE = new SlimefunItemStack(
            "CANFIELDITE", Material.AMETHYST_SHARD, "&5黑硫银锡矿", "", "&7挖掘石头有概率获得"
    );
    public static final SlimefunItemStack MALAYAITE = new SlimefunItemStack(
            "MALAYAITE", Material.WHITE_TERRACOTTA, "&f马来亚石", "", "&7通过GEO矿机在草原群系获得"
    );
    public static final SlimefunItemStack VARLAMOFFITE = new SlimefunItemStack(
            "VARLAMOFFITE", Material.ORANGE_TERRACOTTA, "&6水锡石", "", "&7通过GEO矿机在末地获得"
    );
    public static final SlimefunItemStack ELECTRUM = new SlimefunItemStack(
            "ELECTRUM", Material.YELLOW_WOOL, "&e银金矿", "", "&7挖掘普通金矿有概率获得"
    );
    public static final SlimefunItemStack ARGENTITE = new SlimefunItemStack(
            "ARGENTITE", Material.CYAN_TERRACOTTA, "&3辉银矿", "", "&7通过GEO矿机在地狱获得"
    );
    public static final SlimefunItemStack PYRARGYRITE = new SlimefunItemStack(
            "PYRARGYRITE", Material.NETHER_BRICKS, "&4深红银矿", "", "&7挖掘下界金矿有概率获得"
    );
    public static final SlimefunItemStack PROUSTITE = new SlimefunItemStack(
            "PROUSTITE", Material.NETHER_WART_BLOCK, "&c淡红银矿", "", "&7挖掘下界石英矿有概率获得"
    );
    public static final SlimefunItemStack CERARGYRITE = new SlimefunItemStack(
            "CERARGYRITE", Material.QUARTZ, "&f角银矿", "", "&7通过GEO矿机在沙漠/恶地群系获得"
    );
    public static final SlimefunItemStack STEPHANITE = new SlimefunItemStack(
            "STEPHANITE", Material.BLACK_TERRACOTTA, "&8脆银矿", "", "&7挖掘下界石英矿有概率获得"
    );
    public static final SlimefunItemStack DYSKRASITE = new SlimefunItemStack(
            "DYSKRASITE", Material.RED_TERRACOTTA, "&c锑银矿", "", "&7挖掘下界金矿有概率获得"
    );
    public static final SlimefunItemStack NAUMANNITE = new SlimefunItemStack(
            "NAUMANNITE", Material.GRAY_TERRACOTTA, "&7硒银矿", "", "&7通过GEO矿机在玄武岩三角洲群系获得"
    );
    public static final SlimefunItemStack HESSITE = new SlimefunItemStack(
            "HESSITE", Material.GOLD_INGOT, "&6碲银矿", "", "&7挖掘深层金矿有概率获得"
    );
    public static final SlimefunItemStack POLYBASITE = new SlimefunItemStack(
            "POLYBASITE", Material.LIGHT_GRAY_WOOL, "&7硫锑铜银矿", "", "&7通过磨石研磨16个磨制黑石获得"
    );
    public static final SlimefunItemStack FREIESLEBENITE = new SlimefunItemStack(
            "FREIESLEBENITE", Material.BLACK_WOOL, "&8锌锑方辉银矿", "", "&7通过磨石研磨2个闪锌矿获得", "&7或通过磨石研磨16个磨制玄武岩获得"
    );

    private static final ItemStack[] NULL_RECIPE = new ItemStack[] {
            null, null, null, null, null, null, null, null, null
    };

    private static final ItemStack[] CALCITE_2_GRIND_RECIPE = new ItemStack[] {
            new ItemStack(Material.CALCITE, 2), null, null, null, null, null, null, null, null
    };

    private static final ItemStack[] POLISHED_GRANITE_GRIND_RECIPE = new ItemStack[] {
            new ItemStack(Material.POLISHED_GRANITE), null, null, null, null, null, null, null, null
    };

    private static final ItemStack[] POLISHED_BLACKSTONE_16_GRIND_RECIPE = new ItemStack[] {
            new ItemStack(Material.POLISHED_BLACKSTONE, 16), null, null, null, null, null, null, null, null
    };

    private static final ItemStack[] POLISHED_BASALT_16_GRIND_RECIPE = new ItemStack[] {
            new ItemStack(Material.POLISHED_BASALT, 16), null, null, null, null, null, null, null, null
    };

    private static int researchId = 9800;

    private MoreOresItems() {}

    private static void registerGrindOreCustom(SlimefunItemStack item, String key, String name, int cost, ItemStack[] recipe) {
        new MoreOresItem(MORE_ORES, item, RecipeType.GRIND_STONE, recipe)
                .setUseableInWorkbench(false)
                .register(MoreOres.getInstance());
        new Research(new NamespacedKey(MoreOres.getInstance(), key), researchId++, name, cost)
                .addItems(item).register();
    }

    public static void registerAll(MoreOres plugin) {
        registerDropOre(YELLOW_IRON_ORE, "yellow_iron_ore", "黄铁矿石", 1);
        registerDropOre(BAUXITE, "bauxite", "铝土矿", 2);
        registerDropOre(BLUE_CORUNDUM, "blue_corundum", "蓝刚玉", 5);
        registerDropOre(RED_CORUNDUM, "red_corundum", "红刚玉", 5);
        registerDropOre(SPHALERITE, "sphalerite", "闪锌矿", 1);
        registerDropOre(STACKED_ZINC_ORE, "stacked_zinc_ore", "异锌矿", 1);
        registerDropOre(SMITHSONITE, "smithsonite", "菱锌矿", 1);
        registerDropOre(MAGNESIUM_ZINC_ORE, "magnesium_zinc_ore", "镁锌石", 2);
        registerDropOre(RED_ZINCITE, "red_zincite", "红锌石", 2);

        registerGrindOreCustom(BRUCITE, "brucite", "水镁石", 1, CALCITE_2_GRIND_RECIPE);

        registerGeoOre(BISCHOFITE, "bischofite", "水氯镁石", 3);
        registerGeoOre(BRINE, "brine", "卤水", 3);
        registerGeoOre(CARNALLITE, "carnallite", "光卤石", 3);

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

        registerGrindOreCustom(CASSITERITE, "cassiterite", "锡石", 2, POLISHED_GRANITE_GRIND_RECIPE);

        ItemStack galenaInput = GALENA.clone();
        galenaInput.setAmount(2);
        registerGrindOreCustom(TEALLITE, "teallite", "硫锡铅矿", 3,
                new ItemStack[]{galenaInput, null, null, null, null, null, null, null, null});

        registerDropOre(STANNITE, "stannite", "黄锡矿", 2);
        registerDropOre(CANFIELDITE, "canfieldite", "黑硫银锡矿", 2);

        registerGeoOre(FRANCKEITE, "franckeite", "辉锑锡铅矿", 3);
        registerGeoOre(CYLINDRITE, "cylindrite", "圆柱锡矿", 3);
        registerGeoOre(MALAYAITE, "malayaite", "马来亚石", 3);
        registerGeoOre(VARLAMOFFITE, "varlamoffite", "水锡石", 3);

        registerDropOre(ELECTRUM, "electrum", "银金矿", 2);
        registerDropOre(PYRARGYRITE, "pyrargyrite", "深红银矿", 2);
        registerDropOre(PROUSTITE, "proustite", "淡红银矿", 2);
        registerDropOre(STEPHANITE, "stephanite", "脆银矿", 2);
        registerDropOre(DYSKRASITE, "dyscrasite", "锑银矿", 2);
        registerDropOre(HESSITE, "hessite", "碲银矿", 2);

        registerGrindOreCustom(POLYBASITE, "polybasite", "硫锑铜银矿", 3, POLISHED_BLACKSTONE_16_GRIND_RECIPE);

        ItemStack sphalerite2 = SPHALERITE.clone();
        sphalerite2.setAmount(2);
        registerGrindOreCustom(FREIESLEBENITE, "freieslebenite", "锌锑方辉银矿", 4,
                new ItemStack[]{sphalerite2, null, null, null, null, null, null, null, null});
        RecipeType.GRIND_STONE.register(POLISHED_BASALT_16_GRIND_RECIPE, FREIESLEBENITE);

        registerGeoOre(ARGENTITE, "argentite", "辉银矿", 3);
        registerGeoOre(CERARGYRITE, "cerargyrite", "角银矿", 3);
        registerGeoOre(NAUMANNITE, "naumannite", "硒银矿", 3);
    }

    private static void registerDropOre(SlimefunItemStack item, String key, String name, int cost) {
        new MoreOresItem(MORE_ORES, item, RecipeType.NULL, NULL_RECIPE)
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
