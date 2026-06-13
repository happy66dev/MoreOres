// 当前文件属于 MoreOres 插件的物品注册中心
package io.github.happy66dev.moreores;

// 导入 Slimefun 的自定义物品栈工具类，用于创建带样式的物品图标
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
// 导入 Slimefun 的物品分组类，用于在 GUI 中创建一个分类标签页
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
// 导入 Slimefun 的物品基类，所有 Slimefun 物品都继承自这个类
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
// 导入 Slimefun 的物品栈定义类，用来定义一个物品的 ID、材质、显示名和描述
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
// 导入 Slimefun 的配方类型类，定义物品的获取方式（如磨石、GEO矿机等）
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
// 导入 Slimefun 的研究（科技树）类，用于注册解锁物品所需的研究节点
import io.github.thebusybiscuit.slimefun4.api.researches.Research;
// 导入 Slimefun 的不可放置接口，实现后矿物方块不会被放置到世界中
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
// 导入 Bukkit 的材质枚举类，用来指定物品的外观材质
import org.bukkit.Material;
// 导入 Bukkit 的命名空间键类，用于为物品和研究创建唯一的标识符
import org.bukkit.NamespacedKey;
// 导入 Bukkit 的物品栈类，代表 Minecraft 中的一个物品实例
import org.bukkit.inventory.ItemStack;

/**
 * MoreOres 插件的物品注册中心。
 * <p>
 * 本类集中定义了所有 43 种自定义矿物物品（包括矿石、粉末、机器），
 * 并提供了三种辅助注册方法，分别对应不同的矿物获取途径：
 * <ul>
 *   <li>{@link #registerDropOre} — 通过挖掘原版矿石概率掉落获得（无合成配方）</li>
 *   <li>{@link #registerGrindOreCustom} — 通过磨石（Grind Stone）研磨获得</li>
 *   <li>{@link {@link #registerGeoOre}} — 通过 GEO 矿机在特定群系获得</li>
 * </ul>
 * 矿物按照批次分为四组，researchId 从 9800 开始自增，保证每个研究节点 ID 唯一。
 * </p>
 *
 * @author happy66dev
 * @since 1.0.0
 */
// 定义公共最终类，不允许被继承或实例化
public final class MoreOresItems {

    // ======================== 物品分组 ========================

    /**
     * MoreOres 插件的物品分组（分类标签页）。
     * <p>
     * 在 Slimefun 的 GUI 菜单中会显示一个铁矿石图标，
     * 名称为 "&6MoreOres &7- 更多矿物"，玩家点击即可浏览本插件的所有物品。
     * </p>
     */
    // 创建并注册一个物品分组，使用铁矿石材质作为图标，key 为 "more_ores"
    public static final ItemGroup MORE_ORES = new ItemGroup(
            // 使用插件实例和自定义键名创建唯一的命名空间键
            new NamespacedKey(MoreOres.getInstance(), "more_ores"),
            // 使用铁矿石材质，显示名称包含金色的插件名和灰色的中文副标题
            new CustomItemStack(Material.IRON_ORE, "&6MoreOres &7- 更多矿物")
    );

    // ======================== 第一批矿物：黄铁矿系 / 刚玉 / 锌系 / 镁 ========================

    /**
     * 黄铁矿石。
     * <p>矿物名：黄铁矿石 | 英文ID：YELLOW_IRON_ORE | 获取方式：挖掘铁矿或铜矿概率掉落 | 材质：铁矿石</p>
     */
    // 定义黄铁矿石物品，ID 为 YELLOW_IRON_ORE，使用铁矿石外观，金色显示名称
    public static final SlimefunItemStack YELLOW_IRON_ORE = new SlimefunItemStack(
            // 物品的唯一字符串 ID，用于持久化存储和查找
            "YELLOW_IRON_ORE",
            // 物品在游戏中的外观材质，使用原版铁矿石
            Material.IRON_ORE,
            // 物品的显示名称，&e 为金色，中文名为"黄铁矿石"
            "&e黄铁矿石",
            // 第一行 lore 描述，此处为空字符串作为分隔
            "",
            // 第二行 lore 描述，提示玩家获取方式：挖掘铁矿或铜矿有概率获得
            "&7挖掘铁矿或铜矿有概率获得"
    );

    /**
     * 铝土矿。
     * <p>矿物名：铝土矿 | 英文ID：BAUXITE | 获取方式：挖掘黏土/铁矿/煤矿/红石矿概率掉落 | 材质：煤矿石</p>
     */
    // 定义铝土矿物品，ID 为 BAUXITE，使用煤矿石外观，深灰色显示名称
    public static final SlimefunItemStack BAUXITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "BAUXITE",
            // 使用煤矿石作为外观材质
            Material.COAL_ORE,
            // 显示名称，&8 为深灰色，中文名为"铝土矿"
            "&8铝土矿",
            // lore 分隔空行
            "",
            // 提示获取方式：挖掘黏土、铁矿、煤矿、红石矿有概率获得
            "&7挖掘黏土/铁矿/煤矿/红石矿有概率获得"
    );

    /**
     * 蓝刚玉。
     * <p>矿物名：蓝刚玉 | 英文ID：BLUE_CORUNDUM | 获取方式：挖掘深层绿宝石矿极低概率掉落 | 材质：钻石</p>
     */
    // 定义蓝刚玉物品，ID 为 BLUE_CORUNDUM，使用钻石外观，深蓝色显示名称
    public static final SlimefunItemStack BLUE_CORUNDUM = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "BLUE_CORUNDUM",
            // 使用钻石作为外观材质，象征蓝色宝石
            Material.DIAMOND,
            // 显示名称，&1 为深蓝色，中文名为"蓝刚玉"
            "&1蓝刚玉",
            // lore 分隔空行
            "",
            // 提示获取方式：挖掘深层绿宝石有极低概率获得
            "&7挖掘深层绿宝石有极低概率获得"
    );

    /**
     * 红刚玉。
     * <p>矿物名：红刚玉 | 英文ID：RED_CORUNDUM | 获取方式：挖掘深层绿宝石矿极低概率掉落 | 材质：红石</p>
     */
    // 定义红刚玉物品，ID 为 RED_CORUNDUM，使用红石外观，红色显示名称
    public static final SlimefunItemStack RED_CORUNDUM = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "RED_CORUNDUM",
            // 使用红石作为外观材质，象征红色宝石
            Material.REDSTONE,
            // 显示名称，&c 为红色，中文名为"红刚玉"
            "&c红刚玉",
            // lore 分隔空行
            "",
            // 提示获取方式：挖掘深层绿宝石有极低概率获得
            "&7挖掘深层绿宝石有极低概率获得"
    );

    /**
     * 闪锌矿。
     * <p>矿物名：闪锌矿 | 英文ID：SPHALERITE | 获取方式：挖掘铜矿概率掉落 | 材质：铁矿石</p>
     */
    // 定义闪锌矿物品，ID 为 SPHALERITE，使用铁矿石外观，白色显示名称
    public static final SlimefunItemStack SPHALERITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "SPHALERITE",
            // 使用铁矿石作为外观材质
            Material.IRON_ORE,
            // 显示名称，&f 为白色，中文名为"闪锌矿"
            "&f闪锌矿",
            // lore 分隔空行
            "",
            // 提示获取方式：挖掘铜矿有概率获得
            "&7挖掘铜矿有概率获得"
    );

    /**
     * 异锌矿。
     * <p>矿物名：异锌矿 | 英文ID：STACKED_ZINC_ORE | 获取方式：挖掘铁矿概率掉落 | 材质：铁矿石</p>
     */
    // 定义异锌矿物品，ID 为 STACKED_ZINC_ORE，使用铁矿石外观，灰色显示名称
    public static final SlimefunItemStack STACKED_ZINC_ORE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "STACKED_ZINC_ORE",
            // 使用铁矿石作为外观材质
            Material.IRON_ORE,
            // 显示名称，&7 为灰色，中文名为"异锌矿"
            "&7异锌矿",
            // lore 分隔空行
            "",
            // 提示获取方式：挖掘铁矿有概率获得
            "&7挖掘铁矿有概率获得"
    );

    /**
     * 菱锌矿。
     * <p>矿物名：菱锌矿 | 英文ID：SMITHSONITE | 获取方式：挖掘铁矿概率掉落 | 材质：铁矿石</p>
     */
    // 定义菱锌矿物品，ID 为 SMITHSONITE，使用铁矿石外观，灰色显示名称
    public static final SlimefunItemStack SMITHSONITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "SMITHSONITE",
            // 使用铁矿石作为外观材质
            Material.IRON_ORE,
            // 显示名称，&7 为灰色，中文名为"菱锌矿"
            "&7菱锌矿",
            // lore 分隔空行
            "",
            // 提示获取方式：挖掘铁矿有概率获得
            "&7挖掘铁矿有概率获得"
    );

    /**
     * 镁锌石。
     * <p>矿物名：镁锌石 | 英文ID：MAGNESIUM_ZINC_ORE | 获取方式：挖掘石头概率掉落 | 材质：铁矿石</p>
     */
    // 定义镁锌石物品，ID 为 MAGNESIUM_ZINC_ORE，使用铁矿石外观，灰色显示名称
    public static final SlimefunItemStack MAGNESIUM_ZINC_ORE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "MAGNESIUM_ZINC_ORE",
            // 使用铁矿石作为外观材质
            Material.IRON_ORE,
            // 显示名称，&7 为灰色，中文名为"镁锌石"
            "&7镁锌石",
            // lore 分隔空行
            "",
            // 提示获取方式：挖掘石头有概率获得
            "&7挖掘石头有概率获得"
    );

    /**
     * 红锌石。
     * <p>矿物名：红锌石 | 英文ID：RED_ZINCITE | 获取方式：挖掘红石矿概率掉落 | 材质：红石矿石</p>
     */
    // 定义红锌石物品，ID 为 RED_ZINCITE，使用红石矿石外观，深红色显示名称
    public static final SlimefunItemStack RED_ZINCITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "RED_ZINCITE",
            // 使用红石矿石作为外观材质
            Material.REDSTONE_ORE,
            // 显示名称，&4 为深红色，中文名为"红锌石"
            "&4红锌石",
            // lore 分隔空行
            "",
            // 提示获取方式：挖掘红石矿有概率获得
            "&7挖掘红石矿有概率获得"
    );

    /**
     * 水镁石。
     * <p>矿物名：水镁石 | 英文ID：BRUCITE | 获取方式：通过磨石研磨2个方解石获得 | 材质：冰</p>
     */
    // 定义水镁石物品，ID 为 BRUCITE，使用冰材质，青色显示名称
    public static final SlimefunItemStack BRUCITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "BRUCITE",
            // 使用冰作为外观材质，与水镁石的"水"属性相呼应
            Material.ICE,
            // 显示名称，&b 为青色，中文名为"水镁石"
            "&b水镁石",
            // lore 分隔空行
            "",
            // 提示获取方式：通过磨石研磨2个方解石获得
            "&7通过磨石研磨2个方解石获得"
    );

    /**
     * 水氯镁石。
     * <p>矿物名：水氯镁石 | 英文ID：BISCHOFITE | 获取方式：通过GEO矿机在海洋/高山群系获得 | 材质：青金石矿石</p>
     */
    // 定义水氯镁石物品，ID 为 BISCHOFITE，使用青金石矿石外观，深蓝色显示名称
    public static final SlimefunItemStack BISCHOFITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "BISCHOFITE",
            // 使用青金石矿石作为外观材质
            Material.LAPIS_ORE,
            // 显示名称，&1 为深蓝色，中文名为"水氯镁石"
            "&1水氯镁石",
            // lore 分隔空行
            "",
            // 提示获取方式：通过 GEO 矿机在海洋或高山群系获得
            "&7通过GEO矿机在海洋/高山群系获得"
    );

    /**
     * 卤水。
     * <p>矿物名：卤水 | 英文ID：BRINE | 获取方式：通过GEO矿机在海洋/高山群系获得 | 材质：水桶</p>
     */
    // 定义卤水物品，ID 为 BRINE，使用水桶外观，深青色显示名称
    public static final SlimefunItemStack BRINE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "BRINE",
            // 使用水桶作为外观材质，与卤水的液体属性相呼应
            Material.WATER_BUCKET,
            // 显示名称，&3 为深青色，中文名为"卤水"
            "&3卤水",
            // lore 分隔空行
            "",
            // 提示获取方式：通过 GEO 矿机在海洋或高山群系获得
            "&7通过GEO矿机在海洋/高山群系获得"
    );

    /**
     * 光卤石。
     * <p>矿物名：光卤石 | 英文ID：CARNALLITE | 获取方式：通过GEO矿机在任意群系获得 | 材质：钻石矿石</p>
     */
    // 定义光卤石物品，ID 为 CARNALLITE，使用钻石矿石外观，粉色显示名称
    public static final SlimefunItemStack CARNALLITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "CARNALLITE",
            // 使用钻石矿石作为外观材质
            Material.DIAMOND_ORE,
            // 显示名称，&d 为粉色，中文名为"光卤石"
            "&d光卤石",
            // lore 分隔空行
            "",
            // 提示获取方式：通过 GEO 矿机在任意群系获得
            "&7通过GEO矿机在任意群系获得"
    );

    // ======================== 第二批矿物：铅系（挖掘掉落 + GEO矿机） ========================

    /**
     * 方铅矿。
     * <p>矿物名：方铅矿 | 英文ID：GALENA | 获取方式：挖掘铜矿概率掉落 | 材质：紫色染料</p>
     */
    // 定义方铅矿物品，ID 为 GALENA，使用紫色染料外观，紫色显示名称
    public static final SlimefunItemStack GALENA = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "GALENA",
            // 使用紫色染料作为外观材质，方铅矿具有铅的紫色光泽
            Material.PURPLE_DYE,
            // 显示名称，&5 为紫色，中文名为"方铅矿"
            "&5方铅矿",
            // lore 分隔空行
            "",
            // 提示获取方式：挖掘铜矿有概率获得
            "&7挖掘铜矿有概率获得"
    );

    /**
     * 白铅矿。
     * <p>矿物名：白铅矿 | 英文ID：CERUSSITE | 获取方式：挖掘铜矿概率掉落 | 材质：白色染料</p>
     */
    // 定义白铅矿物品，ID 为 CERUSSITE，使用白色染料外观，白色显示名称
    public static final SlimefunItemStack CERUSSITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "CERUSSITE",
            // 使用白色染料作为外观材质，与"白铅矿"的白色特征一致
            Material.WHITE_DYE,
            // 显示名称，&f 为白色，中文名为"白铅矿"
            "&f白铅矿",
            // lore 分隔空行
            "",
            // 提示获取方式：挖掘铜矿有概率获得
            "&7挖掘铜矿有概率获得"
    );

    /**
     * 铅矾。
     * <p>矿物名：铅矾 | 英文ID：ANGLESITE | 获取方式：挖掘铜矿概率掉落 | 材质：淡灰色染料</p>
     */
    // 定义铅矾物品，ID 为 ANGLESITE，使用淡灰色染料外观，灰色显示名称
    public static final SlimefunItemStack ANGLESITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "ANGLESITE",
            // 使用淡灰色染料作为外观材质
            Material.LIGHT_GRAY_DYE,
            // 显示名称，&7 为灰色，中文名为"铅矾"
            "&7铅矾",
            // lore 分隔空行
            "",
            // 提示获取方式：挖掘铜矿有概率获得
            "&7挖掘铜矿有概率获得"
    );

    /**
     * 铬铅矿。
     * <p>矿物名：铬铅矿 | 英文ID：CROCOITE | 获取方式：挖掘深层铁矿概率掉落 | 材质：橙色染料</p>
     */
    // 定义铬铅矿物品，ID 为 CROCOITE，使用橙色染料外观，金色显示名称
    public static final SlimefunItemStack CROCOITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "CROCOITE",
            // 使用橙色染料作为外观材质，铬铅矿具有鲜艳的橙色
            Material.ORANGE_DYE,
            // 显示名称，&6 为金色，中文名为"铬铅矿"
            "&6铬铅矿",
            // lore 分隔空行
            "",
            // 提示获取方式：挖掘深层铁矿有概率获得
            "&7挖掘深层铁矿有概率获得"
    );

    /**
     * 硫锑铅矿。
     * <p>矿物名：硫锑铅矿 | 英文ID：BOULANGERITE | 获取方式：挖掘青金石矿概率掉落 | 材质：灰色染料</p>
     */
    // 定义硫锑铅矿物品，ID 为 BOULANGERITE，使用灰色染料外观，深灰色显示名称
    public static final SlimefunItemStack BOULANGERITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "BOULANGERITE",
            // 使用灰色染料作为外观材质
            Material.GRAY_DYE,
            // 显示名称，&8 为深灰色，中文名为"硫锑铅矿"
            "&8硫锑铅矿",
            // lore 分隔空行
            "",
            // 提示获取方式：挖掘青金石矿有概率获得
            "&7挖掘青金石矿有概率获得"
    );

    /**
     * 脆硫锑铅矿。
     * <p>矿物名：脆硫锑铅矿 | 英文ID：JAMESONITE | 获取方式：挖掘绿宝石矿概率掉落 | 材质：黑色染料</p>
     */
    // 定义脆硫锑铅矿物品，ID 为 JAMESONITE，使用黑色染料外观，黑色显示名称
    public static final SlimefunItemStack JAMESONITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "JAMESONITE",
            // 使用黑色染料作为外观材质
            Material.BLACK_DYE,
            // 显示名称，&0 为黑色，中文名为"脆硫锑铅矿"
            "&0脆硫锑铅矿",
            // lore 分隔空行
            "",
            // 提示获取方式：挖掘绿宝石矿有概率获得
            "&7挖掘绿宝石矿有概率获得"
    );

    /**
     * 钒铅矿。
     * <p>矿物名：钒铅矿 | 英文ID：VANADINITE | 获取方式：挖掘深层红石矿概率掉落 | 材质：红色染料</p>
     */
    // 定义钒铅矿物品，ID 为 VANADINITE，使用红色染料外观，红色显示名称
    public static final SlimefunItemStack VANADINITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "VANADINITE",
            // 使用红色染料作为外观材质，钒铅矿呈鲜红色
            Material.RED_DYE,
            // 显示名称，&c 为红色，中文名为"钒铅矿"
            "&c钒铅矿",
            // lore 分隔空行
            "",
            // 提示获取方式：挖掘深层红石矿有概率获得
            "&7挖掘深层红石矿有概率获得"
    );

    /**
     * 钼铅矿。
     * <p>矿物名：钼铅矿 | 英文ID：WULFENITE | 获取方式：挖掘钻石矿概率掉落 | 材质：萤石</p>
     */
    // 定义钼铅矿物品，ID 为 WULFENITE，使用萤石外观，金色显示名称
    public static final SlimefunItemStack WULFENITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "WULFENITE",
            // 使用萤石作为外观材质，钼铅矿呈明亮的黄橙色
            Material.GLOWSTONE,
            // 显示名称，&e 为金色，中文名为"钼铅矿"
            "&e钼铅矿",
            // lore 分隔空行
            "",
            // 提示获取方式：挖掘钻石矿有概率获得
            "&7挖掘钻石矿有概率获得"
    );

    /**
     * 磷氯铅矿。
     * <p>矿物名：磷氯铅矿 | 英文ID：PYROMORPHITE | 获取方式：通过GEO矿机在河流/湖泊/海洋群系获得 | 材质：淡绿色染料</p>
     */
    // 定义磷氯铅矿物品，ID 为 PYROMORPHITE，使用淡绿色染料外观，绿色显示名称
    public static final SlimefunItemStack PYROMORPHITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "PYROMORPHITE",
            // 使用淡绿色染料作为外观材质，磷氯铅矿呈黄绿色
            Material.LIME_DYE,
            // 显示名称，&a 为绿色，中文名为"磷氯铅矿"
            "&a磷氯铅矿",
            // lore 分隔空行
            "",
            // 提示获取方式：通过 GEO 矿机在河流、湖泊、海洋群系获得
            "&7通过GEO矿机在河流/湖泊/海洋群系获得"
    );

    /**
     * 砷铅矿。
     * <p>矿物名：砷铅矿 | 英文ID：MIMETITE | 获取方式：通过GEO矿机在地狱获得 | 材质：黄色染料</p>
     */
    // 定义砷铅矿物品，ID 为 MIMETITE，使用黄色染料外观，金色显示名称
    public static final SlimefunItemStack MIMETITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "MIMETITE",
            // 使用黄色染料作为外观材质，砷铅矿呈黄色
            Material.YELLOW_DYE,
            // 显示名称，&e 为金色，中文名为"砷铅矿"
            "&e砷铅矿",
            // lore 分隔空行
            "",
            // 提示获取方式：通过 GEO 矿机在下界（地狱）获得
            "&7通过GEO矿机在地狱获得"
    );

    /**
     * 车轮矿。
     * <p>矿物名：车轮矿 | 英文ID：BOURNONITE | 获取方式：通过GEO矿机在雪原群系获得 | 材质：灰色染料</p>
     */
    // 定义车轮矿物品，ID 为 BOURNONITE，使用灰色染料外观，灰色显示名称
    public static final SlimefunItemStack BOURNONITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "BOURNONITE",
            // 使用灰色染料作为外观材质
            Material.GRAY_DYE,
            // 显示名称，&7 为灰色，中文名为"车轮矿"
            "&7车轮矿",
            // lore 分隔空行
            "",
            // 提示获取方式：通过 GEO 矿机在雪原群系获得
            "&7通过GEO矿机在雪原群系获得"
    );

    // ======================== 第三批矿物：锡系（磨石研磨 + 挖掘掉落 + GEO矿机） ========================

    /**
     * 锡石。
     * <p>矿物名：锡石 | 英文ID：CASSITERITE | 获取方式：通过磨石研磨磨制花岗岩获得 | 材质：棕色染料</p>
     */
    // 定义锡石物品，ID 为 CASSITERITE，使用棕色染料外观，金色显示名称
    public static final SlimefunItemStack CASSITERITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "CASSITERITE",
            // 使用棕色染料作为外观材质
            Material.BROWN_DYE,
            // 显示名称，&6 为金色，中文名为"锡石"
            "&6锡石",
            // lore 分隔空行
            "",
            // 提示获取方式：通过磨石研磨磨制花岗岩获得
            "&7通过磨石研磨磨制花岗岩获得"
    );

    /**
     * 黄锡矿。
     * <p>矿物名：黄锡矿 | 英文ID：STANNITE | 获取方式：挖掘黄金矿概率掉落 | 材质：苔石</p>
     */
    // 定义黄锡矿物品，ID 为 STANNITE，使用苔石外观，深绿色显示名称
    public static final SlimefunItemStack STANNITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "STANNITE",
            // 使用苔石作为外观材质
            Material.MOSSY_COBBLESTONE,
            // 显示名称，&2 为深绿色，中文名为"黄锡矿"
            "&2黄锡矿",
            // lore 分隔空行
            "",
            // 提示获取方式：挖掘黄金矿有概率获得
            "&7挖掘黄金矿有概率获得"
    );

    /**
     * 硫锡铅矿。
     * <p>矿物名：硫锡铅矿 | 英文ID：TEALLITE | 获取方式：通过磨石研磨2个方铅矿获得 | 材质：木炭</p>
     */
    // 定义硫锡铅矿物品，ID 为 TEALLITE，使用木炭外观，深灰色显示名称
    public static final SlimefunItemStack TEALLITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "TEALLITE",
            // 使用木炭作为外观材质
            Material.CHARCOAL,
            // 显示名称，&8 为深灰色，中文名为"硫锡铅矿"
            "&8硫锡铅矿",
            // lore 分隔空行
            "",
            // 提示获取方式：通过磨石研磨2个方铅矿获得
            "&7通过磨石研磨2个方铅矿获得"
    );

    /**
     * 辉锑锡铅矿。
     * <p>矿物名：辉锑锡铅矿 | 英文ID：FRANCKEITE | 获取方式：通过GEO矿机在沙漠/恶地群系获得 | 材质：深板岩</p>
     */
    // 定义辉锑锡铅矿物品，ID 为 FRANCKEITE，使用深板岩外观，灰色显示名称
    public static final SlimefunItemStack FRANCKEITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "FRANCKEITE",
            // 使用深板岩作为外观材质
            Material.DEEPSLATE,
            // 显示名称，&7 为灰色，中文名为"辉锑锡铅矿"
            "&7辉锑锡铅矿",
            // lore 分隔空行
            "",
            // 提示获取方式：通过 GEO 矿机在沙漠或恶地群系获得
            "&7通过GEO矿机在沙漠/恶地群系获得"
    );

    /**
     * 圆柱锡矿。
     * <p>矿物名：圆柱锡矿 | 英文ID：CYLINDRITE | 获取方式：通过GEO矿机在金合欢森林群系获得 | 材质：玄武岩</p>
     */
    // 定义圆柱锡矿物品，ID 为 CYLINDRITE，使用玄武岩外观，深灰色显示名称
    public static final SlimefunItemStack CYLINDRITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "CYLINDRITE",
            // 使用玄武岩作为外观材质
            Material.BLACKSTONE,
            // 显示名称，&8 为深灰色，中文名为"圆柱锡矿"
            "&8圆柱锡矿",
            // lore 分隔空行
            "",
            // 提示获取方式：通过 GEO 矿机在金合欢森林群系获得
            "&7通过GEO矿机在金合欢森林群系获得"
    );

    /**
     * 黑硫银锡矿。
     * <p>矿物名：黑硫银锡矿 | 英文ID：CANFIELDITE | 获取方式：挖掘石头概率掉落 | 材质：紫水晶碎片</p>
     */
    // 定义黑硫银锡矿物品，ID 为 CANFIELDITE，使用紫水晶碎片外观，紫色显示名称
    public static final SlimefunItemStack CANFIELDITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "CANFIELDITE",
            // 使用紫水晶碎片作为外观材质
            Material.AMETHYST_SHARD,
            // 显示名称，&5 为紫色，中文名为"黑硫银锡矿"
            "&5黑硫银锡矿",
            // lore 分隔空行
            "",
            // 提示获取方式：挖掘石头有概率获得
            "&7挖掘石头有概率获得"
    );

    /**
     * 马来亚石。
     * <p>矿物名：马来亚石 | 英文ID：MALAYAITE | 获取方式：通过GEO矿机在草原群系获得 | 材质：白色陶瓦</p>
     */
    // 定义马来亚石物品，ID 为 MALAYAITE，使用白色陶瓦外观，白色显示名称
    public static final SlimefunItemStack MALAYAITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "MALAYAITE",
            // 使用白色陶瓦作为外观材质
            Material.WHITE_TERRACOTTA,
            // 显示名称，&f 为白色，中文名为"马来亚石"
            "&f马来亚石",
            // lore 分隔空行
            "",
            // 提示获取方式：通过 GEO 矿机在草原群系获得
            "&7通过GEO矿机在草原群系获得"
    );

    /**
     * 水锡石。
     * <p>矿物名：水锡石 | 英文ID：VARLAMOFFITE | 获取方式：通过GEO矿机在末地获得 | 材质：橙色陶瓦</p>
     */
    // 定义水锡石物品，ID 为 VARLAMOFFITE，使用橙色陶瓦外观，金色显示名称
    public static final SlimefunItemStack VARLAMOFFITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "VARLAMOFFITE",
            // 使用橙色陶瓦作为外观材质
            Material.ORANGE_TERRACOTTA,
            // 显示名称，&6 为金色，中文名为"水锡石"
            "&6水锡石",
            // lore 分隔空行
            "",
            // 提示获取方式：通过 GEO 矿机在末地获得
            "&7通过GEO矿机在末地获得"
    );

    // ======================== 第四批矿物：银系（挖掘掉落 + GEO矿机 + 磨石研磨） ========================

    /**
     * 银金矿。
     * <p>矿物名：银金矿 | 英文ID：ELECTRUM | 获取方式：挖掘普通金矿概率掉落 | 材质：黄色羊毛</p>
     */
    // 定义银金矿物品，ID 为 ELECTRUM，使用黄色羊毛外观，金色显示名称
    public static final SlimefunItemStack ELECTRUM = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "ELECTRUM",
            // 使用黄色羊毛作为外观材质
            Material.YELLOW_WOOL,
            // 显示名称，&e 为金色，中文名为"银金矿"
            "&e银金矿",
            // lore 分隔空行
            "",
            // 提示获取方式：挖掘普通金矿有概率获得
            "&7挖掘普通金矿有概率获得"
    );

    /**
     * 辉银矿。
     * <p>矿物名：辉银矿 | 英文ID：ARGENTITE | 获取方式：通过GEO矿机在地狱获得 | 材质：青色陶瓦</p>
     */
    // 定义辉银矿物品，ID 为 ARGENTITE，使用青色陶瓦外观，深青色显示名称
    public static final SlimefunItemStack ARGENTITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "ARGENTITE",
            // 使用青色陶瓦作为外观材质
            Material.CYAN_TERRACOTTA,
            // 显示名称，&3 为深青色，中文名为"辉银矿"
            "&3辉银矿",
            // lore 分隔空行
            "",
            // 提示获取方式：通过 GEO 矿机在下界（地狱）获得
            "&7通过GEO矿机在地狱获得"
    );

    /**
     * 深红银矿。
     * <p>矿物名：深红银矿 | 英文ID：PYRARGYRITE | 获取方式：挖掘下界金矿概率掉落 | 材质：下界砖</p>
     */
    // 定义深红银矿物品，ID 为 PYRARGYRITE，使用下界砖外观，深红色显示名称
    public static final SlimefunItemStack PYRARGYRITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "PYRARGYRITE",
            // 使用下界砖作为外观材质，与下界矿石的主题一致
            Material.NETHER_BRICKS,
            // 显示名称，&4 为深红色，中文名为"深红银矿"
            "&4深红银矿",
            // lore 分隔空行
            "",
            // 提示获取方式：挖掘下界金矿有概率获得
            "&7挖掘下界金矿有概率获得"
    );

    /**
     * 淡红银矿。
     * <p>矿物名：淡红银矿 | 英文ID：PROUSTITE | 获取方式：挖掘下界石英矿概率掉落 | 材质：下界疣块</p>
     */
    // 定义淡红银矿物品，ID 为 PROUSTITE，使用下界疣块外观，红色显示名称
    public static final SlimefunItemStack PROUSTITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "PROUSTITE",
            // 使用下界疣块作为外观材质
            Material.NETHER_WART_BLOCK,
            // 显示名称，&c 为红色，中文名为"淡红银矿"
            "&c淡红银矿",
            // lore 分隔空行
            "",
            // 提示获取方式：挖掘下界石英矿有概率获得
            "&7挖掘下界石英矿有概率获得"
    );

    /**
     * 角银矿。
     * <p>矿物名：角银矿 | 英文ID：CERARGYRITE | 获取方式：通过GEO矿机在沙漠/恶地群系获得 | 材质：石英</p>
     */
    // 定义角银矿物品，ID 为 CERARGYRITE，使用石英外观，白色显示名称
    public static final SlimefunItemStack CERARGYRITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "CERARGYRITE",
            // 使用石英作为外观材质
            Material.QUARTZ,
            // 显示名称，&f 为白色，中文名为"角银矿"
            "&f角银矿",
            // lore 分隔空行
            "",
            // 提示获取方式：通过 GEO 矿机在沙漠或恶地群系获得
            "&7通过GEO矿机在沙漠/恶地群系获得"
    );

    /**
     * 脆银矿。
     * <p>矿物名：脆银矿 | 英文ID：STEPHANITE | 获取方式：挖掘下界石英矿概率掉落 | 材质：黑色陶瓦</p>
     */
    // 定义脆银矿物品，ID 为 STEPHANITE，使用黑色陶瓦外观，深灰色显示名称
    public static final SlimefunItemStack STEPHANITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "STEPHANITE",
            // 使用黑色陶瓦作为外观材质
            Material.BLACK_TERRACOTTA,
            // 显示名称，&8 为深灰色，中文名为"脆银矿"
            "&8脆银矿",
            // lore 分隔空行
            "",
            // 提示获取方式：挖掘下界石英矿有概率获得
            "&7挖掘下界石英矿有概率获得"
    );

    /**
     * 锑银矿。
     * <p>矿物名：锑银矿 | 英文ID：DYSKRASITE | 获取方式：挖掘下界金矿概率掉落 | 材质：红色陶瓦</p>
     */
    // 定义锑银矿物品，ID 为 DYSKRASITE，使用红色陶瓦外观，红色显示名称
    public static final SlimefunItemStack DYSKRASITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "DYSKRASITE",
            // 使用红色陶瓦作为外观材质
            Material.RED_TERRACOTTA,
            // 显示名称，&c 为红色，中文名为"锑银矿"
            "&c锑银矿",
            // lore 分隔空行
            "",
            // 提示获取方式：挖掘下界金矿有概率获得
            "&7挖掘下界金矿有概率获得"
    );

    /**
     * 硒银矿。
     * <p>矿物名：硒银矿 | 英文ID：NAUMANNITE | 获取方式：通过GEO矿机在玄武岩三角洲群系获得 | 材质：灰色陶瓦</p>
     */
    // 定义硒银矿物品，ID 为 NAUMANNITE，使用灰色陶瓦外观，灰色显示名称
    public static final SlimefunItemStack NAUMANNITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "NAUMANNITE",
            // 使用灰色陶瓦作为外观材质
            Material.GRAY_TERRACOTTA,
            // 显示名称，&7 为灰色，中文名为"硒银矿"
            "&7硒银矿",
            // lore 分隔空行
            "",
            // 提示获取方式：通过 GEO 矿机在玄武岩三角洲群系获得
            "&7通过GEO矿机在玄武岩三角洲群系获得"
    );

    /**
     * 碲银矿。
     * <p>矿物名：碲银矿 | 英文ID：HESSITE | 获取方式：挖掘深层金矿概率掉落 | 材质：金锭</p>
     */
    // 定义碲银矿物品，ID 为 HESSITE，使用金锭外观，金色显示名称
    public static final SlimefunItemStack HESSITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "HESSITE",
            // 使用金锭作为外观材质
            Material.GOLD_INGOT,
            // 显示名称，&6 为金色，中文名为"碲银矿"
            "&6碲银矿",
            // lore 分隔空行
            "",
            // 提示获取方式：挖掘深层金矿有概率获得
            "&7挖掘深层金矿有概率获得"
    );

    /**
     * 硫锑铜银矿。
     * <p>矿物名：硫锑铜银矿 | 英文ID：POLYBASITE | 获取方式：通过磨石研磨16个磨制黑石获得 | 材质：淡灰色羊毛</p>
     */
    // 定义硫锑铜银矿物品，ID 为 POLYBASITE，使用淡灰色羊毛外观，灰色显示名称
    public static final SlimefunItemStack POLYBASITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "POLYBASITE",
            // 使用淡灰色羊毛作为外观材质
            Material.LIGHT_GRAY_WOOL,
            // 显示名称，&7 为灰色，中文名为"硫锑铜银矿"
            "&7硫锑铜银矿",
            // lore 分隔空行
            "",
            // 提示获取方式：通过磨石研磨16个磨制黑石获得
            "&7通过磨石研磨16个磨制黑石获得"
    );

    /**
     * 锌锑方辉银矿。
     * <p>矿物名：锌锑方辉银矿 | 英文ID：FREIESLEBENITE | 获取方式：通过磨石研磨2个闪锌矿或16个磨制玄武岩获得 | 材质：黑色羊毛</p>
     * <p>注意：此矿物有两套磨石配方——第一套使用2个闪锌矿，第二套使用16个磨制玄武岩。</p>
     */
    // 定义锌锑方辉银矿物品，ID 为 FREIESLEBENITE，使用黑色羊毛外观，深灰色显示名称
    public static final SlimefunItemStack FREIESLEBENITE = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "FREIESLEBENITE",
            // 使用黑色羊毛作为外观材质
            Material.BLACK_WOOL,
            // 显示名称，&8 为深灰色，中文名为"锌锑方辉银矿"
            "&8锌锑方辉银矿",
            // 第一行 lore 描述，提示第一种获取方式：研磨2个闪锌矿
            "&7通过磨石研磨2个闪锌矿获得",
            // 第二行 lore 描述，提示第二种获取方式：研磨16个磨制玄武岩
            "&7或通过磨石研磨16个磨制玄武岩获得"
    );

    // ======================== 特殊物品：黄铁矿粉 & 加热反应室 ========================

    /**
     * 黄铁矿粉。
     * <p>矿物名：黄铁矿粉 | 英文ID：PYRITE_DUST | 获取方式：通过矿石粉碎机粉碎黄铁矿石获得，每次产出2个 | 材质：萤石粉</p>
     */
    // 定义黄铁矿粉物品，ID 为 PYRITE_DUST，使用萤石粉外观，白色显示名称
    public static final SlimefunItemStack PYRITE_DUST = new SlimefunItemStack(
            // 物品的唯一字符串 ID
            "PYRITE_DUST",
            // 使用萤石粉作为外观材质
            Material.GLOWSTONE_DUST,
            // 显示名称，&f 为白色，中文名为"黄铁矿粉"
            "&f黄铁矿粉",
            // lore 分隔空行
            "",
            // 提示获取方式：通过矿石粉碎机粉碎黄铁矿石获得
            "&7通过矿石粉碎机粉碎黄铁矿石获得"
    );

    /**
     * 普通加热反应室。
     * <p>机器名：普通加热反应室 | 英文ID：HEATED_REACTION_CHAMBER | 功率：30 J/s | 功能：在内部进行加热化学反应</p>
     */
    // 定义普通加热反应室物品，ID 为 HEATED_REACTION_CHAMBER，使用高炉外观
    public static final SlimefunItemStack HEATED_REACTION_CHAMBER_ITEM = new SlimefunItemStack(
            // 物品的唯一字符串 ID，注意这里不带 _ITEM 后缀，保持与注册 key 一致
            "HEATED_REACTION_CHAMBER",
            // 使用高炉作为外观材质，符合加热反应室的工业主题
            Material.BLAST_FURNACE,
            // 显示名称，&c 为红色，表示加热主题
            "&c普通加热反应室",
            // lore 分隔空行
            "",
            // 第二行 lore 显示功率信息：&e⚡ 表示能量图标，30 J/s 表示每秒处理速度
            "&8⇨ &e⚡ &730 J/s",
            // 第三行 lore 描述功能：可在内部进行加热化学反应
            "&7可在内部进行加热化学反应"
    );

    // ======================== 配方数组定义 ========================

    /**
     * 空配方数组，用于不需要合成配方的矿物（如挖掘掉落和 GEO 矿机获取的矿物）。
     * <p>
     * 磨石配方采用 3x3 工作台布局，共 9 个格子，
     * 此处全部设为 null 表示无需任何材料输入。
     * </p>
     */
    // 创建一个包含 9 个 null 的空配方数组，表示 3x3 工作台没有任何输入材料
    private static final ItemStack[] NULL_RECIPE = new ItemStack[] {
            // 第一行三个格子全部为空
            null, null, null,
            // 第二行三个格子全部为空
            null, null, null,
            // 第三行三个格子全部为空
            null, null, null
    };

    /**
     * 磨石配方：2个方解石 → 水镁石。
     * <p>用途：在磨石中放入2个方解石，研磨产出1个水镁石（BRUCITE）。</p>
     */
    // 创建方解石研磨配方，2个方解石放在工作台第一个格子（左上角）
    private static final ItemStack[] CALCITE_2_GRIND_RECIPE = new ItemStack[] {
            // 左上角放入2个方解石作为研磨原料
            new ItemStack(Material.CALCITE, 2),
            // 第一行中间和右边为空
            null, null,
            // 第二行三个格子全部为空
            null, null, null,
            // 第三行三个格子全部为空
            null, null, null
    };

    /**
     * 磨石配方：1个磨制花岗岩 → 锡石。
     * <p>用途：在磨石中放入1个磨制花岗岩，研磨产出1个锡石（CASSITERITE）。</p>
     */
    // 创建磨制花岗岩研磨配方，1个磨制花岗岩放在工作台第一个格子
    private static final ItemStack[] POLISHED_GRANITE_GRIND_RECIPE = new ItemStack[] {
            // 左上角放入1个磨制花岗岩作为研磨原料
            new ItemStack(Material.POLISHED_GRANITE),
            // 第一行中间和右边为空
            null, null,
            // 第二行三个格子全部为空
            null, null, null,
            // 第三行三个格子全部为空
            null, null, null
    };

    /**
     * 磨石配方：16个磨制黑石 → 硫锑铜银矿。
     * <p>用途：在磨石中放入16个磨制黑石，研磨产出1个硫锑铜银矿（POLYBASITE）。</p>
     */
    // 创建磨制黑石研磨配方，16个磨制黑石放在工作台第一个格子
    private static final ItemStack[] POLISHED_BLACKSTONE_16_GRIND_RECIPE = new ItemStack[] {
            // 左上角放入16个磨制黑石作为研磨原料
            new ItemStack(Material.POLISHED_BLACKSTONE, 16),
            // 第一行中间和右边为空
            null, null,
            // 第二行三个格子全部为空
            null, null, null,
            // 第三行三个格子全部为空
            null, null, null
    };

    /**
     * 磨石配方：16个磨制玄武岩 → 锌锑方辉银矿。
     * <p>用途：在磨石中放入16个磨制玄武岩，研磨产出1个锌锑方辉银矿（FREIESLEBENITE）。</p>
     * <p>这是锌锑方辉银矿的第二套配方，第一套使用2个闪锌矿。</p>
     */
    // 创建磨制玄武岩研磨配方，16个磨制玄武岩放在工作台第一个格子
    private static final ItemStack[] POLISHED_BASALT_16_GRIND_RECIPE = new ItemStack[] {
            // 左上角放入16个磨制玄武岩作为研磨原料
            new ItemStack(Material.POLISHED_BASALT, 16),
            // 第一行中间和右边为空
            null, null,
            // 第二行三个格子全部为空
            null, null, null,
            // 第三行三个格子全部为空
            null, null, null
    };

    // ======================== 研究ID计数器 ========================

    /**
     * 研究节点的自增 ID 计数器。
     * <p>
     * Slimefun 的每个研究节点都需要一个全局唯一的数字 ID。
     * 本插件从 9800 开始，每注册一个新矿物的研究时自增1，
     * 保证不会与其他插件的 ID 冲突。
     * </p>
     */
    // 初始化研究 ID 为 9800，每次注册新研究后会自增
    private static int researchId = 9800;

    // ======================== 私有构造函数 ========================

    /**
     * 私有构造函数，防止外部实例化本工具类。
     * <p>本类仅包含静态成员和静态方法，不需要被实例化。</p>
     */
    // 私有构造方法，阻止通过 new 创建 MoreOresItems 的实例
    private MoreOresItems() {}

    // ======================== 辅助注册方法 ========================

    /**
     * 注册通过磨石（Grind Stone）研磨获取的矿物。
     * <p>
     * 此方法完成两件事：
     * <ol>
     *   <li>创建一个 MoreOresItem 并绑定磨石配方，注册到 Slimefun 系统</li>
     *   <li>创建对应的研究节点，玩家需要消耗经验值解锁该矿物</li>
     * </ol>
     * 注册后的矿物会被禁止在工作台中使用（setUseableInWorkbench(false)），
     * 因为矿物是原材料，不应被当作合成材料放入工作台。
     * </p>
     *
     * @param item   要注册的矿物物品栈，包含 ID、材质、名称和描述信息
     * @param key    研究节点的唯一键名，用于创建 NamespacedKey
     * @param name   研究的显示名称，玩家在科技树中看到的名字
     * @param cost   解锁研究所需的经验等级消耗，数值越大解锁越贵
     * @param recipe 磨石配方数组，长度为9，对应3x3工作台布局中的材料
     */
    // 定义私有静态方法 registerGrindOreCustom，用于注册磨石研磨类矿物
    private static void registerGrindOreCustom(SlimefunItemStack item, String key, String name, int cost, ItemStack[] recipe) {
        // 创建 MoreOresItem 实例，指定所属分组、物品栈、配方类型为磨石、以及配方数组
        new MoreOresItem(MORE_ORES, item, RecipeType.GRIND_STONE, recipe)
                // 禁止该物品在普通工作台中使用，只允许在磨石中使用
                .setUseableInWorkbench(false)
                // 将物品注册到 Slimefun 插件系统中，使其在游戏中生效
                .register(MoreOres.getInstance());
        // 创建研究节点，使用插件实例创建唯一的命名空间键，researchId 自增保证唯一
        new Research(new NamespacedKey(MoreOres.getInstance(), key), researchId++, name, cost)
                // 将该矿物物品添加到研究节点中，解锁研究后才能获得该物品
                .addItems(item)
                // 将研究节点注册到 Slimefun 的科技树系统中
                .register();
    }

    /**
     * 注册所有 MoreOres 插件的矿物和机器物品。
     * <p>
     * 本方法是整个插件的物品注册入口，在插件启用时被调用。
     * 按照矿物的获取方式和批次顺序，依次调用三个辅助注册方法：
     * <ul>
     *   <li>{@link #registerDropOre} — 挖掘掉落类矿物</li>
     *   <li>{@link #registerGrindOreCustom} — 磨石研磨类矿物</li>
     *   <li>{@link {@link #registerGeoOre}} — GEO矿机类矿物</li>
     * </ul>
     * 最后单独注册黄铁矿粉（通过矿石粉碎机获得，有自定义配方产出2个）。
     * </p>
     *
     * @param plugin MoreOres 插件主类实例，用于获取插件引用和命名空间
     */
    // 定义公共静态方法 registerAll，作为插件所有物品的注册入口
    public static void registerAll(MoreOres plugin) {

        // ==================== 第一批：黄铁矿系 / 刚玉 / 锌系 / 镁 ====================

        // 注册黄铁矿石：挖掘铁矿或铜矿概率掉落，研究消耗1级经验
        registerDropOre(YELLOW_IRON_ORE, "yellow_iron_ore", "黄铁矿石", 1);
        // 注册铝土矿：挖掘黏土/铁矿/煤矿/红石矿概率掉落，研究消耗2级经验
        registerDropOre(BAUXITE, "bauxite", "铝土矿", 2);
        // 注册蓝刚玉：挖掘深层绿宝石矿极低概率掉落，研究消耗5级经验（稀有矿物）
        registerDropOre(BLUE_CORUNDUM, "blue_corundum", "蓝刚玉", 5);
        // 注册红刚玉：挖掘深层绿宝石矿极低概率掉落，研究消耗5级经验（稀有矿物）
        registerDropOre(RED_CORUNDUM, "red_corundum", "红刚玉", 5);
        // 注册闪锌矿：挖掘铜矿概率掉落，研究消耗1级经验
        registerDropOre(SPHALERITE, "sphalerite", "闪锌矿", 1);
        // 注册异锌矿：挖掘铁矿概率掉落，研究消耗1级经验
        registerDropOre(STACKED_ZINC_ORE, "stacked_zinc_ore", "异锌矿", 1);
        // 注册菱锌矿：挖掘铁矿概率掉落，研究消耗1级经验
        registerDropOre(SMITHSONITE, "smithsonite", "菱锌矿", 1);
        // 注册镁锌石：挖掘石头概率掉落，研究消耗2级经验
        registerDropOre(MAGNESIUM_ZINC_ORE, "magnesium_zinc_ore", "镁锌石", 2);
        // 注册红锌石：挖掘红石矿概率掉落，研究消耗2级经验
        registerDropOre(RED_ZINCITE, "red_zincite", "红锌石", 2);

        // 注册水镁石：通过磨石研磨2个方解石获得，使用 CALCITE_2_GRIND_RECIPE 配方，研究消耗1级经验
        registerGrindOreCustom(BRUCITE, "brucite", "水镁石", 1, CALCITE_2_GRIND_RECIPE);

        // 注册水氯镁石：通过 GEO 矿机在海洋/高山群系获得，研究消耗3级经验
        registerGeoOre(BISCHOFITE, "bischofite", "水氯镁石", 3);
        // 注册卤水：通过 GEO 矿机在海洋/高山群系获得，研究消耗3级经验
        registerGeoOre(BRINE, "brine", "卤水", 3);
        // 注册光卤石：通过 GEO 矿机在任意群系获得，研究消耗3级经验
        registerGeoOre(CARNALLITE, "carnallite", "光卤石", 3);

        // ==================== 第二批：铅系（挖掘掉落 + GEO矿机） ====================

        // 注册方铅矿：挖掘铜矿概率掉落，研究消耗2级经验
        registerDropOre(GALENA, "galena", "方铅矿", 2);
        // 注册白铅矿：挖掘铜矿概率掉落，研究消耗2级经验
        registerDropOre(CERUSSITE, "cerussite", "白铅矿", 2);
        // 注册铅矾：挖掘铜矿概率掉落，研究消耗2级经验
        registerDropOre(ANGLESITE, "anglesite", "铅矾", 2);
        // 注册铬铅矿：挖掘深层铁矿概率掉落，研究消耗2级经验
        registerDropOre(CROCOITE, "crocoite", "铬铅矿", 2);
        // 注册硫锑铅矿：挖掘青金石矿概率掉落，研究消耗2级经验
        registerDropOre(BOULANGERITE, "boulangerite", "硫锑铅矿", 2);
        // 注册脆硫锑铅矿：挖掘绿宝石矿概率掉落，研究消耗2级经验
        registerDropOre(JAMESONITE, "jamesonite", "脆硫锑铅矿", 2);
        // 注册钒铅矿：挖掘深层红石矿概率掉落，研究消耗2级经验
        registerDropOre(VANADINITE, "vanadinite", "钒铅矿", 2);
        // 注册钼铅矿：挖掘钻石矿概率掉落，研究消耗2级经验
        registerDropOre(WULFENITE, "wulfenite", "钼铅矿", 2);

        // 注册磷氯铅矿：通过 GEO 矿机在河流/湖泊/海洋群系获得，研究消耗3级经验
        registerGeoOre(PYROMORPHITE, "pyromorphite", "磷氯铅矿", 3);
        // 注册砷铅矿：通过 GEO 矿机在下界获得，研究消耗3级经验
        registerGeoOre(MIMETITE, "mimetite", "砷铅矿", 3);
        // 注册车轮矿：通过 GEO 矿机在雪原群系获得，研究消耗3级经验
        registerGeoOre(BOURNONITE, "bournonite", "车轮矿", 3);

        // ==================== 第三批：锡系（磨石研磨 + 挖掘掉落 + GEO矿机） ====================

        // 注册锡石：通过磨石研磨1个磨制花岗岩获得，使用 POLISHED_GRANITE_GRIND_RECIPE 配方，研究消耗2级经验
        registerGrindOreCustom(CASSITERITE, "cassiterite", "锡石", 2, POLISHED_GRANITE_GRIND_RECIPE);

        // 克隆方铅矿物品栈，用于创建硫锡铅矿的自定义磨石配方（需要2个方铅矿）
        ItemStack galenaInput = GALENA.clone();
        // 将方铅矿数量设置为2，作为磨石配方的输入材料
        galenaInput.setAmount(2);
        // 注册硫锡铅矿：通过磨石研磨2个方铅矿获得，配方在运行时动态创建，研究消耗3级经验
        registerGrindOreCustom(TEALLITE, "teallite", "硫锡铅矿", 3,
                // 创建包含2个方铅矿的配方数组，放在第一个格子，其余格子为空
                new ItemStack[]{galenaInput, null, null, null, null, null, null, null, null});

        // 注册黄锡矿：挖掘黄金矿概率掉落，研究消耗2级经验
        registerDropOre(STANNITE, "stannite", "黄锡矿", 2);
        // 注册黑硫银锡矿：挖掘石头概率掉落，研究消耗2级经验
        registerDropOre(CANFIELDITE, "canfieldite", "黑硫银锡矿", 2);

        // 注册辉锑锡铅矿：通过 GEO 矿机在沙漠/恶地群系获得，研究消耗3级经验
        registerGeoOre(FRANCKEITE, "franckeite", "辉锑锡铅矿", 3);
        // 注册圆柱锡矿：通过 GEO 矿机在金合欢森林群系获得，研究消耗3级经验
        registerGeoOre(CYLINDRITE, "cylindrite", "圆柱锡矿", 3);
        // 注册马来亚石：通过 GEO 矿机在草原群系获得，研究消耗3级经验
        registerGeoOre(MALAYAITE, "malayaite", "马来亚石", 3);
        // 注册水锡石：通过 GEO 矿机在末地获得，研究消耗3级经验
        registerGeoOre(VARLAMOFFITE, "varlamoffite", "水锡石", 3);

        // ==================== 第四批：银系（挖掘掉落 + GEO矿机 + 磨石研磨） ====================

        // 注册银金矿：挖掘普通金矿概率掉落，研究消耗2级经验
        registerDropOre(ELECTRUM, "electrum", "银金矿", 2);
        // 注册深红银矿：挖掘下界金矿概率掉落，研究消耗2级经验
        registerDropOre(PYRARGYRITE, "pyrargyrite", "深红银矿", 2);
        // 注册淡红银矿：挖掘下界石英矿概率掉落，研究消耗2级经验
        registerDropOre(PROUSTITE, "proustite", "淡红银矿", 2);
        // 注册脆银矿：挖掘下界石英矿概率掉落，研究消耗2级经验
        registerDropOre(STEPHANITE, "stephanite", "脆银矿", 2);
        // 注册锑银矿：挖掘下界金矿概率掉落，研究消耗2级经验
        registerDropOre(DYSKRASITE, "dyscrasite", "锑银矿", 2);
        // 注册碲银矿：挖掘深层金矿概率掉落，研究消耗2级经验
        registerDropOre(HESSITE, "hessite", "碲银矿", 2);

        // 注册硫锑铜银矿：通过磨石研磨16个磨制黑石获得，使用 POLISHED_BLACKSTONE_16_GRIND_RECIPE 配方，研究消耗3级经验
        registerGrindOreCustom(POLYBASITE, "polybasite", "硫锑铜银矿", 3, POLISHED_BLACKSTONE_16_GRIND_RECIPE);

        // 克隆闪锌矿物品栈，用于创建锌锑方辉银矿的第一套配方（需要2个闪锌矿）
        ItemStack sphalerite2 = SPHALERITE.clone();
        // 将闪锌矿数量设置为2，作为磨石配方的输入材料
        sphalerite2.setAmount(2);
        // 注册锌锑方辉银矿的第一套磨石配方：2个闪锌矿 → 1个锌锑方辉银矿，研究消耗4级经验
        registerGrindOreCustom(FREIESLEBENITE, "freieslebenite", "锌锑方辉银矿", 4,
                // 创建包含2个闪锌矿的配方数组，放在第一个格子，其余格子为空
                new ItemStack[]{sphalerite2, null, null, null, null, null, null, null, null});
        // 注册锌锑方辉银矿的第二套磨石配方：16个磨制玄武岩 → 1个锌锑方辉银矿
        // 直接向磨石系统注册额外配方，绕过 registerGrindOreCustom 方法以避免重复创建研究节点
        RecipeType.GRIND_STONE.register(POLISHED_BASALT_16_GRIND_RECIPE, FREIESLEBENITE);

        // 注册辉银矿：通过 GEO 矿机在下界获得，研究消耗3级经验
        registerGeoOre(ARGENTITE, "argentite", "辉银矿", 3);
        // 注册角银矿：通过 GEO 矿机在沙漠/恶地群系获得，研究消耗3级经验
        registerGeoOre(CERARGYRITE, "cerargyrite", "角银矿", 3);
        // 注册硒银矿：通过 GEO 矿机在玄武岩三角洲群系获得，研究消耗3级经验
        registerGeoOre(NAUMANNITE, "naumannite", "硒银矿", 3);

        // ==================== 特殊注册：黄铁矿粉（矿石粉碎机配方，自定义产出2个） ====================

        // 创建黄铁矿粉的矿石粉碎机配方：1个黄铁矿石 → 2个黄铁矿粉
        new MoreOresItem(MORE_ORES, PYRITE_DUST, RecipeType.ORE_CRUSHER,
                // 配方输入：第一个格子放入1个黄铁矿石，其余格子为空
                new ItemStack[]{YELLOW_IRON_ORE, null, null, null, null, null, null, null, null},
                // 配方输出：使用 recipeOutput 参数指定产出2个黄铁矿粉（默认产出为1个）
                new SlimefunItemStack(PYRITE_DUST, 2))
                // 禁止在普通工作台中使用，只能通过矿石粉碎机获取
                .setUseableInWorkbench(false)
                // 将物品注册到 Slimefun 系统中
                .register(MoreOres.getInstance());
        // 创建黄铁矿粉的研究节点，研究消耗2级经验
        new Research(new NamespacedKey(MoreOres.getInstance(), "pyrite_dust"), researchId++, "黄铁矿粉", 2)
                // 将黄铁矿粉添加到研究节点中
                .addItems(PYRITE_DUST)
                // 注册研究节点到科技树系统
                .register();
    }

    /**
     * 注册通过挖掘原版矿石概率掉落获取的矿物（无合成配方）。
     * <p>
     * 这类矿物没有特定的合成或加工途径，而是通过挖掘原版矿石方块时
     * 概率掉落获得。注册时使用 RecipeType.NULL 和空配方数组，
     * 表示该物品不存在任何合成配方。
     * </p>
     *
     * @param item   要注册的矿物物品栈，包含 ID、材质、名称和描述信息
     * @param key    研究节点的唯一键名，用于创建 NamespacedKey
     * @param name   研究的显示名称，玩家在科技树中看到的名字
     * @param cost   解锁研究所需的经验等级消耗，数值越大解锁越贵
     */
    // 定义私有静态方法 registerDropOre，用于注册挖掘掉落类矿物
    private static void registerDropOre(SlimefunItemStack item, String key, String name, int cost) {
        // 创建 MoreOresItem 实例，使用 RecipeType.NULL 和空配方表示该物品是通过掉落获取的
        new MoreOresItem(MORE_ORES, item, RecipeType.NULL, NULL_RECIPE)
                // 禁止在普通工作台中使用，矿物原料不应作为合成材料
                .setUseableInWorkbench(false)
                // 将物品注册到 Slimefun 插件系统中
                .register(MoreOres.getInstance());
        // 创建研究节点，使用插件实例创建唯一的命名空间键，researchId 自增保证唯一
        new Research(new NamespacedKey(MoreOres.getInstance(), key), researchId++, name, cost)
                // 将该矿物物品添加到研究节点中
                .addItems(item)
                // 注册研究节点到科技树系统
                .register();
    }

    /**
     * 注册通过 GEO 矿机（Geological Scanner）获取的矿物。
     * <p>
     * GEO 矿机是 Slimefun 提供的自动化矿物采集机器，
     * 会在特定的生物群系中扫描并产出对应的矿物。
     * 注册时使用 RecipeType.GEO_MINER 和空配方数组，
     * 因为 GEO 矿机的产出由群系决定，不依赖工作台配方。
     * </p>
     *
     * @param item   要注册的矿物物品栈，包含 ID、材质、名称和描述信息
     * @param key    研究节点的唯一键名，用于创建 NamespacedKey
     * @param name   研究的显示名称，玩家在科技树中看到的名字
     * @param cost   解锁研究所需的经验等级消耗，数值越大解锁越贵
     */
    // 定义私有静态方法 registerGeoOre，用于注册 GEO 矿机类矿物
    private static void registerGeoOre(SlimefunItemStack item, String key, String name, int cost) {
        // 创建 MoreOresItem 实例，使用 RecipeType.GEO_MINER 表示该物品通过 GEO 矿机获取
        new MoreOresItem(MORE_ORES, item, RecipeType.GEO_MINER, NULL_RECIPE)
                // 禁止在普通工作台中使用
                .setUseableInWorkbench(false)
                // 将物品注册到 Slimefun 插件系统中
                .register(MoreOres.getInstance());
        // 创建研究节点，使用插件实例创建唯一的命名空间键，researchId 自增保证唯一
        new Research(new NamespacedKey(MoreOres.getInstance(), key), researchId++, name, cost)
                // 将该矿物物品添加到研究节点中
                .addItems(item)
                // 注册研究节点到科技树系统
                .register();
    }

    /**
     * MoreOres 插件的自定义物品类，继承自 {@link SlimefunItem} 并实现 {@link NotPlaceable} 接口。
     * <p>
     * 本类是 SlimefunItem 的简单包装，主要作用是：
     * <ul>
     *   <li>提供两个构造函数以支持不同的配方类型（有无自定义产出）</li>
     *   <li>实现 NotPlaceable 接口，确保矿物物品不会被玩家放置到世界中成为方块</li>
     * </ul>
     * 这个内部类的设计使得矿物只能作为物品存在于背包中，而不能像原版矿石那样被放置。
     * </p>
     */
    // 定义包级私有的内部类 MoreOresItem，继承 SlimefunItem 并实现 NotPlaceable 接口
    static class MoreOresItem extends SlimefunItem implements NotPlaceable {

        /**
         * 标准构造函数，用于不需要自定义产出的配方类型。
         * <p>
         * 适用于大多数矿物注册场景，如挖掘掉落（NULL）、磨石研磨（GRIND_STONE）、GEO矿机（GEO_MINER）。
         * 配方产出由 Slimefun 默认行为决定（输入什么产出什么，数量不变）。
         * </p>
         *
         * @param itemGroup 物品所属的分组，决定在 Slimefun GUI 中显示在哪个标签页
         * @param item      要创建的物品栈，定义了物品的 ID、材质、名称和描述
         * @param recipeType 配方类型，决定物品的获取途径（如磨石、GEO矿机等）
         * @param recipe    配方数组，长度为9，对应3x3工作台布局中的输入材料
         */
        // 定义四参数构造函数，调用父类 SlimefunItem 的对应构造函数
        MoreOresItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
            // 调用父类 SlimefunItem 的构造函数，传入分组、物品栈、配方类型和配方数组
            super(itemGroup, item, recipeType, recipe);
        }

        /**
         * 带自定义产出的构造函数，用于需要指定产出数量或产出物品的配方类型。
         * <p>
         * 适用于矿石粉碎机（ORE_CRUSHER）等场景，例如黄铁矿石粉碎后产出2个黄铁矿粉，
         * 需要通过 recipeOutput 参数覆盖默认的产出设置。
         * </p>
         *
         * @param itemGroup   物品所属的分组，决定在 Slimefun GUI 中显示在哪个标签页
         * @param item        要创建的物品栈，定义了物品的 ID、材质、名称和描述
         * @param recipeType  配方类型，决定物品的获取途径
         * @param recipe      配方数组，长度为9，对应3x3工作台布局中的输入材料
         * @param recipeOutput 自定义的配方产出物品栈，替代默认的产出（可以指定产出数量）
         */
        // 定义五参数构造函数，额外接收 recipeOutput 参数来自定义配方产出
        MoreOresItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, ItemStack recipeOutput) {
            // 调用父类 SlimefunItem 的五参数构造函数，传入自定义的产出物品栈
            super(itemGroup, item, recipeType, recipe, recipeOutput);
        }
    }
}
