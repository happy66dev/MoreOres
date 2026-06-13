package io.github.happy66dev.moreores;

// 导入粘液科技物品组，用于将物品归类到特定的创造模式标签页中
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
// 导入粘液科技物品栈，定义自定义物品的ID、材质和显示名称
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
// 导入配方类型，标识物品是通过哪种工作台或机器制作的
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
// 导入自定义物品栈工具类，用于创建带自定义名称的物品
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
// 导入粘液科技AContainer抽象类，所有电力机器的基类
// 提供了电力网络集成、物品处理、进度条等核心功能
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
// 导入机器配方类，定义机器的输入→输出转换规则和处理时间
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
// 导入方块菜单预设，用于自定义机器UI的布局
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
// 导入材质枚举，用于定义UI中的装饰物品和进度条显示
import org.bukkit.Material;
// 导入物品栈，表示一组物品（类型+数量）
import org.bukkit.inventory.ItemStack;

/**
 * 普通加热反应室喵~
 *
 * 这是一个自定义的电力机器，继承自 AContainer（粘液科技机器基类）。
 * 它有3个输入槽和3个输出槽，可以进行加热条件下的化学反应喵。
 *
 * 工作原理：
 * 1. 玩家将原料放入3个输入槽
 * 2. 机器消耗电力，根据配方进行反应
 * 3. 反应完成后，产物出现在3个输出槽中
 * 4. 输出槽的物品可以通过漏斗或粘液科技货运自动取出
 *
 * 当前支持的配方：
 * - 黄铁矿粉 → 粗铁（3秒）
 * - 粗铁 + 炭 → 铁锭（6秒）
 * - 粗铁 + 木炭 → 铁锭（6秒）
 * - 黄铁矿石×2 → 粗铁×3（12秒）
 *
 * UI布局（3行27格）：
 * 灰灰灰灰灰灰灰灰灰
 * 灰 输入 箭头 输出 灰
 * 灰灰灰灰灰灰灰灰灰
 */
public class HeatedReactionChamber extends AContainer {

    /**
     * 边框槽位列表喵~
     * 这些槽位在UI中显示为灰色玻璃板，作为装饰性边框，
     * 阻止玩家在这些位置放置物品喵~
     */
    private static final int[] BORDER_SLOTS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};

    /**
     * 输入槽位列表喵~
     * 玩家将原料放入这些槽位，机器会从中取出原料进行反应。
     * 3个输入槽允许同时放入多种原料（如粗铁+炭的配方）喵~
     */
    private static final int[] INPUT_SLOTS = {10, 11, 12};

    /**
     * 输出槽位列表喵~
     * 反应完成后的产物会被放入这些槽位。
     * 3个输出槽支持多产物配方（如黄铁矿石×2→粗铁×3）喵~
     */
    private static final int[] OUTPUT_SLOTS = {14, 15, 16};

    /**
     * 进度条槽位编号喵~
     * 在机器工作时，这个槽位会显示一个时钟物品作为进度条，
     * 让玩家直观地看到反应进行到哪一步了喵~
     */
    private static final int PROGRESS_SLOT = 13;

    /**
     * 构造函数喵~
     * 调用父类AContainer的构造函数，将物品组、物品、配方类型和合成配方传递给粘液科技系统喵~
     *
     * @param itemGroup 物品所属的粘液科技物品组（MoreOres矿物组）
     * @param item 机器的显示物品（包含ID、材质、显示名称等信息）
     * @param recipeType 合成配方的制作台类型（增强工作台）
     * @param recipe 合成配方（9个槽位的物品数组，对应3x3工作台）
     */
    public HeatedReactionChamber(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        // 将参数传递给AContainer父类进行初始化
        super(itemGroup, item, recipeType, recipe);
    }

    /**
     * 获取输入槽位数组喵~
     * AContainer需要知道哪些槽位是输入槽，以便从中取出原料进行处理喵~
     *
     * @return 输入槽位数组 {10, 11, 12}
     */
    @Override
    public int[] getInputSlots() {
        return INPUT_SLOTS;
    }

    /**
     * 获取输出槽位数组喵~
     * AContainer需要知道哪些槽位是输出槽，以便将产物放入喵~
     *
     * @return 输出槽位数组 {14, 15, 16}
     */
    @Override
    public int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    /**
     * 获取机器的唯一标识符喵~
     * 粘液科技用这个字符串来区分不同类型的机器，
     * 用于保存和加载机器状态喵~
     *
     * @return 机器的唯一标识符 "HEATED_REACTION_CHAMBER"
     */
    @Override
    public String getMachineIdentifier() {
        return "HEATED_REACTION_CHAMBER";
    }

    /**
     * 获取进度条物品喵~
     * 这个物品会在机器工作时显示在进度条槽位上，
     * 使用时钟材质表示"正在计时中"的含义喵~
     *
     * @return 时钟物品作为进度条显示
     */
    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.CLOCK);
    }

    /**
     * 构建机器的UI菜单布局喵~
     *
     * 这个方法在机器被第一次放置时调用，设置UI中每个槽位的外观和交互行为：
     * 1. 边框槽位显示灰色玻璃板，禁止交互
     * 2. 进度条槽位显示"进行中..."文本，禁止交互
     * 3. 输出槽位禁止玩家放入物品（只允许机器产出）
     *
     * @param preset 菜单预设对象，用于配置UI布局
     */
    @Override
    protected void constructMenu(BlockMenuPreset preset) {
        // 遍历所有边框槽位，放置灰色玻璃板作为装饰喵~
        for (int slot : BORDER_SLOTS) {
            // 在边框槽位放置灰色玻璃板，显示空格作为名称
            preset.addItem(slot,
                    new CustomItemStack(Material.GRAY_STAINED_GLASS_PANE, " "),
                    // 禁止玩家点击边框槽位（返回false表示不允许操作）
                    (p, s, item, action) -> false);
        }

        // 在进度条槽位放置时钟物品，显示"进行中..."文本喵~
        preset.addItem(PROGRESS_SLOT,
                new CustomItemStack(Material.CLOCK, "&7进行中..."),
                // 禁止玩家点击进度条槽位
                (p, s, item, action) -> false);

        // 为输出槽位添加点击处理器，允许玩家取出产物喵~
        // 注意：这里设置为 false 表示禁止玩家通过UI操作取出
        // 实际的取出需要通过漏斗或Shift+点击来实现
        for (int slot : OUTPUT_SLOTS) {
            preset.addMenuClickHandler(slot, (p, s, item, action) -> false);
        }
    }

    /**
     * 获取机器的UI标题喵~
     * 当玩家右键打开机器界面时，标题栏会显示这个名称喵~
     *
     * @return UI标题 "普通加热反应室"
     */
    @Override
    public String getInventoryTitle() {
        return "普通加热反应室";
    }

    /**
     * 注册所有加热反应配方喵~
     *
     * 每条配方定义了：输入物品 → 输出物品 + 处理时间（秒）
     * MachineRecipe 的构造函数参数：
     * - 处理时间（秒，内部会转换为tick数：秒数 × 2）
     * - 输入物品数组（每种物品占一个槽位）
     * - 输出物品数组（产物放入对应的输出槽）
     *
     * 注意：Minecraft每秒20个tick，所以3秒 = 6个tick喵~
     */
    public void registerRecipes() {
        // 配方1：1个黄铁矿粉 → 1个粗铁，处理时间3秒
        // 黄铁矿粉是通过矿石粉碎机粉碎黄铁矿石获得的
        registerRecipe(new MachineRecipe(3,
                new ItemStack[]{MoreOresItems.PYRITE_DUST},           // 输入：1个黄铁矿粉
                new ItemStack[]{new ItemStack(Material.RAW_IRON)}));  // 输出：1个粗铁

        // 配方2a：1个粗铁 + 1个炭 → 1个铁锭，处理时间6秒
        // 粗铁加热还原为铁锭的经典冶炼过程喵~
        registerRecipe(new MachineRecipe(6,
                new ItemStack[]{new ItemStack(Material.RAW_IRON), new ItemStack(Material.COAL)},  // 输入：粗铁 + 炭
                new ItemStack[]{new ItemStack(Material.IRON_INGOT)}));                            // 输出：铁锭

        // 配方2b：1个粗铁 + 1个木炭 → 1个铁锭，处理时间6秒
        // 木炭作为炭的替代品，提供相同的还原效果喵~
        registerRecipe(new MachineRecipe(6,
                new ItemStack[]{new ItemStack(Material.RAW_IRON), new ItemStack(Material.CHARCOAL)}, // 输入：粗铁 + 木炭
                new ItemStack[]{new ItemStack(Material.IRON_INGOT)}));                               // 输出：铁锭

        // 配方3：2个黄铁矿石 → 3个粗铁，处理时间12秒
        // 大量黄铁矿石加热分解为粗铁喵~
        ItemStack yellowIronOre2 = MoreOresItems.YELLOW_IRON_ORE.clone(); // 克隆一个黄铁矿石物品栈
        yellowIronOre2.setAmount(2); // 设置数量为2个
        registerRecipe(new MachineRecipe(12,
                new ItemStack[]{yellowIronOre2},                            // 输入：2个黄铁矿石
                new ItemStack[]{new ItemStack(Material.RAW_IRON, 3)}));     // 输出：3个粗铁
    }
}
