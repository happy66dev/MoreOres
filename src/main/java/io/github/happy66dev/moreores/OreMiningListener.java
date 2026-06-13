package io.github.happy66dev.moreores;

// 导入粘液科技物品栈类，用于引用MoreOres自定义矿物物品
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
// 导入材质枚举，表示Minecraft中的方块和物品类型
import org.bukkit.Material;
// 导入方块类，用于获取被挖掘方块的信息和在世界中掉落物品
import org.bukkit.block.Block;
// 导入附魔枚举，用于检查玩家工具是否带有精准采集附魔
import org.bukkit.enchantments.Enchantment;
// 导入玩家类，用于获取玩家手持工具信息
import org.bukkit.entity.Player;
// 导入事件注解，标记方法为事件处理器
import org.bukkit.event.EventHandler;
// 导入事件优先级枚举，控制多个监听器的执行顺序
import org.bukkit.event.EventPriority;
// 导入监听器接口，标记类为Bukkit事件监听器
import org.bukkit.event.Listener;
// 导入方块破坏事件，当玩家破坏方块时触发
import org.bukkit.event.block.BlockBreakEvent;
// 导入物品栈类，表示玩家手持的工具
import org.bukkit.inventory.ItemStack;
// 导入不可变集合工具，用于创建高效的只读集合
import java.util.Set;
// 导入线程本地随机数生成器，高性能的随机数生成（避免多线程竞争）
import java.util.concurrent.ThreadLocalRandom;

/**
 * 矿物挖掘监听器喵~
 *
 * 当玩家挖掘原版矿石方块时，这个监听器会根据配置的概率，
 * 额外掉落MoreOres自定义矿物物品。每种矿物有独立的概率判定，
 * 互不影响（一个挖掘动作可以同时掉落多种矿物）喵~
 *
 * 工作流程：
 * 1. 玩家破坏一个方块
 * 2. 检查是否为精准采集（是则跳过）
 * 3. 获取被破坏方块的材质
 * 4. 对每种矿物独立判定：材质匹配 + 概率判定 → 掉落
 *
 * 当前支持的矿物掉落：
 * - 黄铁矿石：挖掘铁矿/铜矿，15%概率
 * - 铝土矿：挖掘黏土/铁矿/煤矿/红石矿，10%概率
 * - 蓝刚玉/红刚玉：挖掘深层绿宝石矿，0.01%概率
 * - 闪锌矿：挖掘铜矿，10%概率
 * - 异锌矿/菱锌矿：挖掘铁矿，20%/10%概率
 * - 镁锌石/黑硫银锡矿：挖掘石头，1%/3%概率
 * - 红锌石：挖掘红石矿，10%概率
 * - 方铅矿/白铅矿/铅矾：挖掘铜矿，8%/4%/2%概率
 * - 铬铅矿：挖掘深层铁矿，3%概率
 * - 硫锑铅矿：挖掘青金石矿，2%概率
 * - 脆硫锑铅矿：挖掘绿宝石矿，10%概率
 * - 钒铅矿：挖掘深层红石矿，5%概率
 * - 钼铅矿：挖掘钻石矿，5%概率
 * - 黄锡矿：挖掘金矿，10%概率
 * - 银金矿：挖掘普通金矿，5%概率
 * - 深红银矿/淡红银矿/脆银矿/锑银矿：挖掘下界金矿/石英矿
 * - 碲银矿：挖掘深层金矿，5%概率
 */
public class OreMiningListener implements Listener {

    /**
     * 插件主类引用喵~
     * 用于访问配置文件获取各矿物的掉落概率
     */
    private final MoreOres plugin;

    /**
     * 黄铁矿石的目标方块集合喵~
     * 挖掘铁矿或铜矿时有概率掉落黄铁矿石
     */
    private static final Set<Material> IRON_ORE_TARGETS = Set.of(Material.IRON_ORE, Material.COPPER_ORE);

    /**
     * 铝土矿的目标方块集合喵~
     * 铝土矿在现实中常与黏土和铁矿共存，所以挖掘这些方块有概率掉落
     */
    private static final Set<Material> BAUXITE_TARGETS = Set.of(Material.CLAY, Material.IRON_ORE, Material.COAL_ORE, Material.REDSTONE_ORE);

    /**
     * 刚玉的目标方块集合喵~
     * 蓝刚玉和红刚玉都从深层绿宝石矿中极低概率掉落
     */
    private static final Set<Material> CORUNDUM_TARGETS = Set.of(Material.DEEPSLATE_EMERALD_ORE);

    /**
     * 闪锌矿的目标方块集合喵~
     * 闪锌矿在现实中常与铜矿共生
     */
    private static final Set<Material> SPHALERITE_TARGETS = Set.of(Material.COPPER_ORE);

    /**
     * 异锌矿/菱锌矿的目标方块集合喵~
     * 这两种锌矿物都从铁矿中掉落
     */
    private static final Set<Material> ZINC_ORE_TARGETS = Set.of(Material.IRON_ORE);

    /**
     * 红锌石的目标方块集合喵~
     * 红锌石从红石矿中掉落
     */
    private static final Set<Material> RED_ZINCITE_TARGETS = Set.of(Material.REDSTONE_ORE);

    /**
     * 铜矿的目标方块集合喵~
     * 包含普通铜矿和深层铜矿，方铅矿/白铅矿/铅矾都从这里掉落
     */
    private static final Set<Material> COPPER_ORE_TARGETS = Set.of(Material.COPPER_ORE, Material.DEEPSLATE_COPPER_ORE);

    /**
     * 深层铁矿目标喵~ 铬铅矿从这里掉落
     */
    private static final Set<Material> DEEPSLATE_IRON_TARGETS = Set.of(Material.DEEPSLATE_IRON_ORE);

    /**
     * 青金石矿目标喵~ 硫锑铅矿从这里掉落
     */
    private static final Set<Material> LAPIS_ORE_TARGETS = Set.of(Material.LAPIS_ORE, Material.DEEPSLATE_LAPIS_ORE);

    /**
     * 绿宝石矿目标喵~ 脆硫锑铅矿从这里掉落
     */
    private static final Set<Material> EMERALD_ORE_TARGETS = Set.of(Material.EMERALD_ORE, Material.DEEPSLATE_EMERALD_ORE);

    /**
     * 深层红石矿目标喵~ 钒铅矿从这里掉落
     */
    private static final Set<Material> DEEPSLATE_REDSTONE_TARGETS = Set.of(Material.DEEPSLATE_REDSTONE_ORE);

    /**
     * 钻石矿目标喵~ 钼铅矿从这里掉落
     */
    private static final Set<Material> DIAMOND_ORE_TARGETS = Set.of(Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE);

    /**
     * 石头目标喵~ 镁锌石和黑硫银锡矿从这里掉落
     */
    private static final Set<Material> STONE_TARGETS = Set.of(Material.STONE);

    /**
     * 仅普通金矿目标喵~ 银金矿只从普通金矿（非深层）掉落
     */
    private static final Set<Material> GOLD_ORE_ONLY = Set.of(Material.GOLD_ORE);

    /**
     * 金矿完整目标集合喵~ 黄锡矿从普通和深层金矿都掉落
     */
    private static final Set<Material> GOLD_ORE_FULL = Set.of(Material.GOLD_ORE, Material.DEEPSLATE_GOLD_ORE);

    /**
     * 下界金矿目标喵~ 深红银矿和锑银矿从这里掉落
     */
    private static final Set<Material> NETHER_GOLD_ORE_TARGETS = Set.of(Material.NETHER_GOLD_ORE);

    /**
     * 下界石英矿目标喵~ 淡红银矿和脆银矿从这里掉落
     */
    private static final Set<Material> NETHER_QUARTZ_ORE_TARGETS = Set.of(Material.NETHER_QUARTZ_ORE);

    /**
     * 仅深层金矿目标喵~ 碲银矿只从深层金矿掉落
     */
    private static final Set<Material> DEEPSLATE_GOLD_ONLY = Set.of(Material.DEEPSLATE_GOLD_ORE);

    /**
     * 构造函数喵~
     * 保存插件引用，以便后续访问配置文件中的概率设置喵~
     *
     * @param plugin MoreOres插件主类实例
     */
    public OreMiningListener(MoreOres plugin) {
        this.plugin = plugin;
    }

    /**
     * 方块破坏事件处理器喵~
     *
     * 当任何玩家破坏方块时，Bukkit会调用此方法。
     * 使用 MONITOR 优先级意味着在所有其他监听器处理完毕后才执行，
     * 这样不会干扰其他插件的逻辑喵~
     *
     * ignoreCancelled = true 表示如果事件已被其他监听器取消（如保护插件），
     * 则不再处理，避免在受保护区域内产生矿物掉落喵~
     *
     * @param event 方块破坏事件，包含玩家、方块位置等信息
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        // 获取破坏方块的玩家喵~
        Player player = event.getPlayer();

        // 喵～防御：玩家为null时直接返回（理论上不会发生，但做防御处理更安全）
        if (player == null) return;

        // 获取玩家主手持有的工具（镐子、斧头等）
        ItemStack tool = player.getInventory().getItemInMainHand();

        // 如果玩家使用精准采集附魔的工具，则不触发矿物掉落
        // 精准采集会直接获取方块本身，不应该额外掉落矿物喵~
        if (hasSilkTouch(tool)) return;

        // 获取被破坏方块的材质类型（如 IRON_ORE、COPPER_ORE 等）
        Material broken = event.getBlock().getType();

        // 获取当前线程的随机数生成器喵~
        // 使用 ThreadLocalRandom 而非 Random，性能更好且线程安全
        ThreadLocalRandom random = ThreadLocalRandom.current();

        // === 第一批矿物：黄铁矿系 ===

        // 黄铁矿石：挖掘铁矿/铜矿，15%概率掉落
        rollAndDrop(event, random, broken, "ores.yellow_iron_ore.chance", 0.15, IRON_ORE_TARGETS, MoreOresItems.YELLOW_IRON_ORE);

        // 铝土矿：挖掘黏土/铁矿/煤矿/红石矿，10%概率掉落
        rollAndDrop(event, random, broken, "ores.bauxite.chance", 0.10, BAUXITE_TARGETS, MoreOresItems.BAUXITE);

        // 蓝刚玉：挖掘深层绿宝石矿，0.01%概率掉落（极稀有）
        rollAndDrop(event, random, broken, "ores.blue_corundum.chance", 0.0001, CORUNDUM_TARGETS, MoreOresItems.BLUE_CORUNDUM);

        // 红刚玉：挖掘深层绿宝石矿，0.01%概率掉落（极稀有）
        rollAndDrop(event, random, broken, "ores.red_corundum.chance", 0.0001, CORUNDUM_TARGETS, MoreOresItems.RED_CORUNDUM);

        // 闪锌矿：挖掘铜矿，10%概率掉落
        rollAndDrop(event, random, broken, "ores.sphalerite.chance", 0.10, SPHALERITE_TARGETS, MoreOresItems.SPHALERITE);

        // 异锌矿：挖掘铁矿，20%概率掉落（较常见）
        rollAndDrop(event, random, broken, "ores.stacked_zinc_ore.chance", 0.20, ZINC_ORE_TARGETS, MoreOresItems.STACKED_ZINC_ORE);

        // 菱锌矿：挖掘铁矿，10%概率掉落
        rollAndDrop(event, random, broken, "ores.smithsonite.chance", 0.10, ZINC_ORE_TARGETS, MoreOresItems.SMITHSONITE);

        // 镁锌石：挖掘石头，1%概率掉落（低概率，石头很常见）
        rollAndDrop(event, random, broken, "ores.magnesium_zinc_ore.chance", 0.01, STONE_TARGETS, MoreOresItems.MAGNESIUM_ZINC_ORE);

        // 红锌石：挖掘红石矿，10%概率掉落
        rollAndDrop(event, random, broken, "ores.red_zincite.chance", 0.10, RED_ZINCITE_TARGETS, MoreOresItems.RED_ZINCITE);

        // === 第二批矿物：铅系 ===

        // 方铅矿：挖掘铜矿/深层铜矿，8%概率掉落
        rollAndDrop(event, random, broken, "ores.galena.chance", 0.08, COPPER_ORE_TARGETS, MoreOresItems.GALENA);

        // 白铅矿：挖掘铜矿/深层铜矿，4%概率掉落
        rollAndDrop(event, random, broken, "ores.cerussite.chance", 0.04, COPPER_ORE_TARGETS, MoreOresItems.CERUSSITE);

        // 铅矾：挖掘铜矿/深层铜矿，2%概率掉落（较稀有）
        rollAndDrop(event, random, broken, "ores.anglesite.chance", 0.02, COPPER_ORE_TARGETS, MoreOresItems.ANGLESITE);

        // 铬铅矿：挖掘深层铁矿，3%概率掉落
        rollAndDrop(event, random, broken, "ores.crocoite.chance", 0.03, DEEPSLATE_IRON_TARGETS, MoreOresItems.CROCOITE);

        // 硫锑铅矿：挖掘青金石矿，2%概率掉落
        rollAndDrop(event, random, broken, "ores.boulangerite.chance", 0.02, LAPIS_ORE_TARGETS, MoreOresItems.BOULANGERITE);

        // 脆硫锑铅矿：挖掘绿宝石矿，10%概率掉落
        rollAndDrop(event, random, broken, "ores.jamesonite.chance", 0.10, EMERALD_ORE_TARGETS, MoreOresItems.JAMESONITE);

        // 钒铅矿：挖掘深层红石矿，5%概率掉落
        rollAndDrop(event, random, broken, "ores.vanadinite.chance", 0.05, DEEPSLATE_REDSTONE_TARGETS, MoreOresItems.VANADINITE);

        // 钼铅矿：挖掘钻石矿，5%概率掉落
        rollAndDrop(event, random, broken, "ores.wulfenite.chance", 0.05, DIAMOND_ORE_TARGETS, MoreOresItems.WULFENITE);

        // === 第三批矿物：锡系 ===

        // 黄锡矿：挖掘金矿（普通+深层），10%概率掉落
        rollAndDrop(event, random, broken, "ores.stannite.chance", 0.10, GOLD_ORE_FULL, MoreOresItems.STANNITE);

        // 黑硫银锡矿：挖掘石头，3%概率掉落
        rollAndDrop(event, random, broken, "ores.canfieldite.chance", 0.03, STONE_TARGETS, MoreOresItems.CANFIELDITE);

        // 银金矿：仅挖掘普通金矿（非深层），5%概率掉落
        rollAndDrop(event, random, broken, "ores.electrum.chance", 0.05, GOLD_ORE_ONLY, MoreOresItems.ELECTRUM);

        // === 第四批矿物：银系 ===

        // 深红银矿：挖掘下界金矿，10%概率掉落
        rollAndDrop(event, random, broken, "ores.pyrargyrite.chance", 0.10, NETHER_GOLD_ORE_TARGETS, MoreOresItems.PYRARGYRITE);

        // 淡红银矿：挖掘下界石英矿，10%概率掉落
        rollAndDrop(event, random, broken, "ores.proustite.chance", 0.10, NETHER_QUARTZ_ORE_TARGETS, MoreOresItems.PROUSTITE);

        // 脆银矿：挖掘下界石英矿，5%概率掉落
        rollAndDrop(event, random, broken, "ores.stephanite.chance", 0.05, NETHER_QUARTZ_ORE_TARGETS, MoreOresItems.STEPHANITE);

        // 锑银矿：挖掘下界金矿，5%概率掉落
        rollAndDrop(event, random, broken, "ores.dyscrasite.chance", 0.05, NETHER_GOLD_ORE_TARGETS, MoreOresItems.DYSKRASITE);

        // 碲银矿：仅挖掘深层金矿，5%概率掉落
        rollAndDrop(event, random, broken, "ores.hessite.chance", 0.05, DEEPSLATE_GOLD_ONLY, MoreOresItems.HESSITE);
    }

    /**
     * 概率判定并掉落矿物的辅助方法喵~
     *
     * 这个方法将重复的概率判定逻辑封装起来，避免在 onBlockBreak 中写大量重复代码。
     * 每种矿物都通过这个方法进行独立的概率判定喵~
     *
     * 工作流程：
     * 1. 检查被挖掘的方块材质是否在目标集合中
     * 2. 如果匹配，从配置文件读取该矿物的掉落概率（默认使用传入的值）
     * 3. 生成随机数，如果小于概率值则掉落矿物
     *
     * 注意：每种矿物的概率判定是独立的！一次挖掘可以同时触发多种矿物的掉落喵~
     *
     * @param event 方块破坏事件
     * @param random 随机数生成器（复用同一实例，性能更好）
     * @param broken 被破坏的方块材质
     * @param configKey 配置文件中的概率键名（如 "ores.yellow_iron_ore.chance"）
     * @param defaultChance 默认概率值（配置文件中未设置时使用）
     * @param targets 允许掉落该矿物的目标方块集合
     * @param ore 要掉落的MoreOres矿物物品
     */
    private void rollAndDrop(BlockBreakEvent event, ThreadLocalRandom random, Material broken,
                             String configKey, double defaultChance, Set<Material> targets, SlimefunItemStack ore) {
        // 喵～防御：如果被挖掘的方块不在目标集合中，直接返回不做任何处理
        if (!targets.contains(broken)) return;

        // 从插件配置文件读取该矿物的掉落概率
        // 如果配置文件中没有设置，则使用方法参数提供的默认值
        double chance = plugin.getConfig().getDouble(configKey, defaultChance);

        // 生成0.0~1.0之间的随机数，如果小于配置的概率则触发掉落
        if (random.nextDouble() < chance) {
            // 在被挖掘方块的位置上方0.5格处掉落矿物物品
            dropOre(event, ore);
        }
    }

    /**
     * 检查工具是否带有精准采集附魔喵~
     *
     * 精准采集（Silk Touch）是一种Minecraft附魔，可以让玩家直接采集方块本身。
     * 如果玩家使用精准采集工具挖掘矿石，不应该额外掉落MoreOres矿物，
     * 因为玩家已经有了"完美采集"的能力喵~
     *
     * @param tool 玩家手持的工具物品栈
     * @return 如果工具带有精准采集附魔返回true，否则返回false
     */
    private boolean hasSilkTouch(ItemStack tool) {
        // 喵～防御：如果工具为空或空气物品，肯定没有精准采集附魔
        if (tool == null || tool.getType().isAir()) return false;

        // 检查工具的附魔列表中是否包含精准采集
        // getEnchantments() 返回一个 Map<Enchantment, Integer>，包含所有附魔及其等级
        return tool.getEnchantments().containsKey(Enchantment.SILK_TOUCH);
    }

    /**
     * 在指定位置掉落矿物物品喵~
     *
     * 使用 dropItemNaturally 方法会在掉落物上添加少量随机偏移，
     * 模拟真实的物品散落效果，而不是精确地掉落在方块中心喵~
     *
     * @param event 方块破坏事件
     * @param ore 要掉落的MoreOres矿物物品
     */
    private void dropOre(BlockBreakEvent event, SlimefunItemStack ore) {
        // 获取被破坏的方块对象
        Block block = event.getBlock();

        // 在方块中心位置（+0.5, +0.5, +0.5）自然掉落矿物物品
        // clone() 方法创建物品栈的副本，避免修改原始物品数据喵~
        block.getWorld().dropItemNaturally(block.getLocation().add(0.5, 0.5, 0.5), ore.clone());
    }
}
