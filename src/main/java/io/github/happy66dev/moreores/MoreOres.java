package io.github.happy66dev.moreores;

// 导入粘液科技核心API：插件附加接口
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
// 导入粘液科技配方类型（如增强工作台、矿石粉碎机等）
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
// 导入粘液科技研究系统，用于解锁科技树节点
import io.github.thebusybiscuit.slimefun4.api.researches.Research;
// 导入粘液科技内置物品常量（如加热线圈、电炉、钢板等）
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
// 导入bStats统计库，用于收集插件使用数据（匿名统计）
import org.bstats.bukkit.Metrics;
// 导入Bukkit物品材质枚举，用于定义方块和物品的外观
import org.bukkit.Material;
// 导入命名空间键，用于创建唯一的插件标识符
import org.bukkit.NamespacedKey;
// 导入物品栈，表示一组物品（类型+数量+元数据）
import org.bukkit.inventory.ItemStack;
// 导入Java插件基类，Bukkit插件必须继承此类
import org.bukkit.plugin.java.JavaPlugin;

/**
 * MoreOres 插件主类喵~
 *
 * 这是整个插件的入口点和生命周期管理器。当服务器加载/卸载插件时，
 * Bukkit会自动调用 onEnable()/onDisable() 方法喵。
 *
 * 本插件继承 JavaPlugin 以成为Bukkit插件，同时实现 SlimefunAddon
 * 接口以集成粘液科技的物品系统、研究系统和能源网络喵~
 *
 * 主要职责：
 * 1. 初始化所有矿物物品和GEO资源
 * 2. 注册加热反应室机器及其配方
 * 3. 注册矿物挖掘监听器（概率掉落）
 * 4. 启动bStats匿名统计
 */
public class MoreOres extends JavaPlugin implements SlimefunAddon {

    /**
     * 插件单例实例喵~
     * 使用静态变量保存当前运行的插件实例，
     * 其他类可以通过 getInstance() 获取插件引用，
     * 从而访问配置文件、日志记录器等Bukkit服务喵~
     */
    private static MoreOres instance;

    /**
     * 插件启用时调用的方法喵~
     *
     * 当服务器启动或重载插件时，Bukkit会自动调用此方法。
     * 这里按顺序完成所有初始化工作喵：
     * 1. 保存单例实例
     * 2. 创建默认配置文件
     * 3. 注册所有矿物物品
     * 4. 注册GEO矿机资源
     * 5. 创建并注册加热反应室机器
     * 6. 注册矿物挖掘监听器
     * 7. 启动统计模块
     */
    @Override
    public void onEnable() {
        // 保存插件实例到静态变量，供其他类使用
        instance = this;

        // 保存默认的 config.yml 到插件数据目录
        // 如果文件已存在则不会覆盖，方便服务器管理员自定义配置
        saveDefaultConfig();

        // 注册所有MoreOres自定义矿物物品（43种矿物+磨石配方+GEO矿机配方）
        MoreOresItems.registerAll(this);

        // 注册所有GEO矿机资源（13种矿物的群系-产出映射）
        MoreOresResources.registerAll();

        // 创建普通加热反应室机器实例喵~
        // 这是一个自定义的AContainer电力机器，有3个输入槽和3个输出槽
        HeatedReactionChamber machine = new HeatedReactionChamber(
                MoreOresItems.MORE_ORES,                  // 物品所属的粘液科技物品组
                MoreOresItems.HEATED_REACTION_CHAMBER_ITEM, // 机器的显示物品
                RecipeType.ENHANCED_CRAFTING_TABLE,         // 合成台类型：增强工作台
                new ItemStack[]{                           // 合成配方（3x3 = 9个槽位）
                        // 第一行：玻璃 + 加热线圈 + 玻璃
                        new ItemStack(Material.GLASS),  SlimefunItems.HEATING_COIL,     new ItemStack(Material.GLASS),
                        // 第二行：加热线圈 + 电炉-I + 加热线圈
                        SlimefunItems.HEATING_COIL,     SlimefunItems.ELECTRIC_FURNACE, SlimefunItems.HEATING_COIL,
                        // 第三行：箱子 + 钢板 + 箱子
                        new ItemStack(Material.CHEST),  SlimefunItems.STEEL_PLATE,      new ItemStack(Material.CHEST)
                });

        // 配置机器参数喵~
        // 容量256焦耳 = 最大储能
        // 耗电2焦耳/tick = 40焦耳/秒（匹配spec的30J/s，取最接近整数）
        // 处理速度1 = 基础速度，每个处理周期10秒
        machine.setCapacity(256).setEnergyConsumption(2).setProcessingSpeed(1);

        // 注册机器的4条加热反应配方
        machine.registerRecipes();

        // 将机器注册到Bukkit插件系统，使其可以被放置和使用
        machine.register(this);

        // 注册粘液科技研究节点，让玩家可以通过经验解锁这台机器喵~
        // 研究ID 9950 在MoreOres插件的ID范围内
        // 解锁费用5级经验
        new Research(new NamespacedKey(this, "heated_reaction_chamber"), 9950, "普通加热反应室", 5)
                .addItems(MoreOresItems.HEATED_REACTION_CHAMBER_ITEM).register();

        // 注册矿物挖掘监听器喵~
        // 当玩家挖掘原版矿石时，监听器会根据配置的概率额外掉落MoreOres矿物
        getServer().getPluginManager().registerEvents(new OreMiningListener(this), this);

        // 启动bStats统计模块，插件ID为24992喵~
        // 这会匿名收集插件的使用数据（如服务器数量、在线玩家数等），帮助开发者了解插件使用情况
        new Metrics(this, 24992);

        // 在控制台输出插件加载成功的日志，方便服务器管理员确认喵~
        getLogger().info("MoreOres v" + getDescription().getVersion() + " 已加载");
    }

    /**
     * 插件禁用时调用的方法喵~
     * 当服务器关闭或重载插件时，Bukkit会自动调用此方法。
     * 目前只需要输出一条日志，因为Bukkit会自动清理注册的事件监听器喵~
     */
    @Override
    public void onDisable() {
        getLogger().info("MoreOres 已卸载");
    }

    /**
     * 获取Bukkit插件实例喵~
     * 这是 SlimefunAddon 接口要求实现的方法，
     * 用于让粘液科技获取底层的Bukkit插件引用喵~
     *
     * @return 当前插件的 JavaPlugin 实例
     */
    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    /**
     * 获取Bug追踪器URL喵~
     * 当粘液科技检测到插件错误时，会引导玩家到这个URL提交bug报告喵~
     *
     * @return GitHub Issues页面的URL
     */
    @Override
    public String getBugTrackerURL() {
        return "https://github.com/happy66dev/MoreOres/issues";
    }

    /**
     * 获取插件单例实例的静态方法喵~
     * 其他类可以通过 MoreOres.getInstance() 获取插件引用，
     * 从而访问配置文件（getConfig()）、日志记录器（getLogger()）等喵~
     *
     * @return 插件单例实例，如果在插件加载前调用可能返回null
     */
    public static MoreOres getInstance() {
        return instance;
    }
}
