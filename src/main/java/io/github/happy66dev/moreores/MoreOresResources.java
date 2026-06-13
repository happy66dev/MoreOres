/**
 * MoreOres 插件的 GEO 矿机资源注册类。
 *
 * <p>本类负责定义和注册 13 种自定义矿物在 SlimeFun GEO 矿机中的产出配置。
 * GEO 矿机是一种可以根据所在维度（主世界/下界/末地）和群系（海洋/高山/沙漠等）
 * 自动产出不同矿物资源的机器。</p>
 *
 * <p>每种矿物通过一个内部类来表示，每个内部类实现了 {@link GEOResource} 接口，
 * 提供矿物的唯一标识符、显示名称、物品图标、默认产出量和随机偏差值。</p>
 *
 * <p>产出规则概述：</p>
 * <ul>
 *   <li>水氯镁石 / 卤水：主世界 + 海洋群系或高山群系</li>
 *   <li>光卤石：主世界任意群系</li>
 *   <li>磷氯铅矿：主世界 + 海洋群系或河流群系</li>
 *   <li>砷铅矿：下界任意群系</li>
 *   <li>车轮矿：主世界 + 雪原群系</li>
 *   <li>辉锑锡铅矿：主世界 + 沙漠或恶地群系</li>
 *   <li>圆柱锡矿：主世界 + 金合欢群系</li>
 *   <li>马来亚石：主世界 + 草原群系</li>
 *   <li>水锡石：末地任意群系</li>
 *   <li>辉银矿：下界任意群系</li>
 *   <li>角银矿：主世界 + 沙漠或恶地群系</li>
 *   <li>硒银矿：下界 + 玄武岩三角洲群系</li>
 * </ul>
 *
 * <p>所有产出数量均可通过 config.yml 中的配置项进行自定义修改。</p>
 *
 * @author happy66dev
 */
package io.github.happy66dev.moreores;

// 导入 SlimeFun 的 GEOResource 接口，GEO 矿机资源需要实现此接口
import io.github.thebusybiscuit.slimefun4.api.geo.GEOResource;
// 导入 Bukkit 的 NamespacedKey 类，用于为资源创建唯一的命名空间标识符
import org.bukkit.NamespacedKey;
// 导入 Bukkit 的 World 类，用于判断玩家所在维度（主世界/下界/末地）
import org.bukkit.World;
// 导入 Bukkit 的 Biome 枚举，用于判断玩家所在群系类型
import org.bukkit.block.Biome;
// 导入 Bukkit 的 ItemStack 类，表示 GEO 矿机产出的物品堆叠
import org.bukkit.inventory.ItemStack;

// 导入 javax 的 Nonnull 注解，标注方法参数和返回值不允许为 null
import javax.annotation.Nonnull;
// 导入 Java 的 Set 集合接口，用于存储群系分组
import java.util.Set;

/**
 * GEO 矿机资源注册类，包含 13 种矿物的群系-产出映射配置。
 *
 * <p>本类为工具类，不能被实例化，只提供静态方法 {@link #registerAll()} 来批量注册所有矿物资源。</p>
 */
public final class MoreOresResources {

    /**
     * 海洋群系集合，包含所有类型的海洋群系。
     *
     * <p>用于判定矿机是否处于海洋环境中，适用于水氯镁石、卤水和磷氯铅矿的产出判断。</p>
     * <p>涵盖：普通海洋、寒冷海洋、深海、深寒海洋、深冻海洋、
     * 深温海洋、冻洋、温海洋、暖海洋共 9 种群系。</p>
     */
    private static final Set<Biome> OCEAN_BIOMES = Set.of(
            // 普通海洋
            Biome.OCEAN,
            // 寒冷海洋（温度较低的海洋区域）
            Biome.COLD_OCEAN,
            // 深海（海洋底部深层区域）
            Biome.DEEP_OCEAN,
            // 深寒海洋（深层寒冷海洋）
            Biome.DEEP_COLD_OCEAN,
            // 深冻海洋（深层冰冻海洋）
            Biome.DEEP_FROZEN_OCEAN,
            // 深温海洋（深层温暖海洋）
            Biome.DEEP_LUKEWARM_OCEAN,
            // 冻洋（冰冻的海洋表面）
            Biome.FROZEN_OCEAN,
            // 温海洋（温度适中的海洋区域）
            Biome.LUKEWARM_OCEAN,
            // 暖海洋（温度最高的海洋区域）
            Biome.WARM_OCEAN
    );

    /**
     * 高山群系集合，包含所有高海拔的山地群系。
     *
     * <p>用于判定矿机是否处于高山环境中，适用于水氯镁石和卤水的产出判断。</p>
     * <p>涵盖：尖锐山峰、冻峰、石峰、雪坡、树林、草甸、
     * 风吹山丘、风吹碎石山丘、风吹森林共 9 种群系。</p>
     */
    private static final Set<Biome> MOUNTAIN_BIOMES = Set.of(
            // 尖锐山峰（高耸的尖锐山峰群系）
            Biome.JAGGED_PEAKS,
            // 冻峰（冰雪覆盖的山峰群系）
            Biome.FROZEN_PEAKS,
            // 石峰（裸露岩石的山峰群系）
            Biome.STONY_PEAKS,
            // 雪坡（积雪的山坡区域）
            Biome.SNOWY_SLOPES,
            // 树林（山地间的树林谷地群系）
            Biome.GROVE,
            // 草甸（高山上的草地群系）
            Biome.MEADOW,
            // 风吹山丘（被强风侵蚀的山丘群系）
            Biome.WINDSWEPT_HILLS,
            // 风吹碎石山丘（碎石遍布的风吹山丘群系）
            Biome.WINDSWEPT_GRAVELLY_HILLS,
            // 风吹森林（被风吹拂的山地森林群系）
            Biome.WINDSWEPT_FOREST
    );

    /**
     * 河流群系集合，包含所有河流类型群系。
     *
     * <p>用于判定矿机是否处于河流环境中，适用于磷氯铅矿的产出判断。</p>
     */
    private static final Set<Biome> RIVER_BIOMES = Set.of(
            // 普通河流
            Biome.RIVER,
            // 冻结的河流（表面结冰的河流）
            Biome.FROZEN_RIVER
    );

    /**
     * 雪原群系集合，包含所有与冰雪相关的寒冷群系。
     *
     * <p>用于判定矿机是否处于雪原环境中，适用于车轮矿的产出判断。</p>
     * <p>涵盖：雪原、雪林、雪滩、冰刺、冻洋、深冻海洋、
     * 冻河、冻峰、尖锐山峰、雪坡、树林共 11 种群系。</p>
     */
    private static final Set<Biome> SNOWY_BIOMES = Set.of(
            // 雪原（平坦的积雪草原）
            Biome.SNOWY_PLAINS,
            // 雪林（积雪覆盖的针叶林）
            Biome.SNOWY_TAIGA,
            // 雪滩（积雪的沙滩区域）
            Biome.SNOWY_BEACH,
            // 冰刺（高耸冰柱的冰原群系）
            Biome.ICE_SPIKES,
            // 冻洋（冰冻的海洋表面）
            Biome.FROZEN_OCEAN,
            // 深冻海洋（深层冰冻海洋区域）
            Biome.DEEP_FROZEN_OCEAN,
            // 冻河（表面结冰的河流）
            Biome.FROZEN_RIVER,
            // 冻峰（冰雪覆盖的山峰）
            Biome.FROZEN_PEAKS,
            // 尖锐山峰（高耸的尖锐山峰群系）
            Biome.JAGGED_PEAKS,
            // 雪坡（积雪的山坡区域）
            Biome.SNOWY_SLOPES,
            // 树林（山地间的树林谷地）
            Biome.GROVE
    );

    /**
     * 沙漠与恶地群系集合，包含所有干旱荒漠类群系。
     *
     * <p>用于判定矿机是否处于沙漠或恶地环境中，适用于辉锑锡铅矿和角银矿的产出判断。</p>
     */
    private static final Set<Biome> DESERT_BADLANDS_BIOMES = Set.of(
            // 沙漠（干旱的沙地群系）
            Biome.DESERT,
            // 恶地（色彩斑斓的侵蚀高地群系）
            Biome.BADLANDS,
            // 丛林恶地（长有树木的恶地群系）
            Biome.WOODED_BADLANDS,
            // 侵蚀恶地（被严重侵蚀的恶地群系）
            Biome.ERODED_BADLANDS
    );

    /**
     * 金合欢群系集合，包含所有金合欢（热带草原）类群系。
     *
     * <p>用于判定矿机是否处于金合欢环境中，适用于圆柱锡矿的产出判断。</p>
     */
    private static final Set<Biome> SAVANNA_BIOMES = Set.of(
            // 金合欢草原（热带稀树草原）
            Biome.SAVANNA,
            // 金合欢高原（地势较高的金合欢群系）
            Biome.SAVANNA_PLATEAU,
            // 风吹金合欢（被强风侵蚀的金合欢群系）
            Biome.WINDSWEPT_SAVANNA
    );

    /**
     * 草原群系集合，包含所有平坦的草地区域群系。
     *
     * <p>用于判定矿机是否处于草原环境中，适用于马来亚石的产出判断。</p>
     */
    private static final Set<Biome> PLAINS_BIOMES = Set.of(
            // 普通草原（平坦的草地区域）
            Biome.PLAINS,
            // 向日葵草原（长满向日葵的草原群系）
            Biome.SUNFLOWER_PLAINS
    );

    /**
     * 玄武岩三角洲群系集合（仅包含下界的玄武岩三角洲）。
     *
     * <p>用于判定矿机是否处于下界玄武岩三角洲环境中，适用于硒银矿的产出判断。</p>
     */
    private static final Set<Biome> BASALT_DELTAS_BIOME = Set.of(
            // 玄武岩三角洲（下界中由玄武岩和黑曜石构成的三角洲地形）
            Biome.BASALT_DELTAS
    );

    /**
     * 私有构造方法，防止本工具类被实例化。
     *
     * <p>本类仅提供静态方法和静态字段，不需要创建实例对象。</p>
     */
    private MoreOresResources() {}

    /**
     * 批量注册所有 13 种矿物的 GEO 矿机资源。
     *
     * <p>调用此方法后，GEO 矿机就能识别并产出本插件添加的所有矿物。
     * 每种矿物资源在创建后立即调用 {@link GEOResource#register()} 方法
     * 将自身注册到 SlimeFun 的 GEO 系统中。</p>
     */
    public static void registerAll() {
        // 创建并注册水氯镁石的 GEO 资源
        new BischofiteResource().register();
        // 创建并注册卤水的 GEO 资源
        new BrineResource().register();
        // 创建并注册光卤石的 GEO 资源
        new CarnalliteResource().register();
        // 创建并注册磷氯铅矿的 GEO 资源
        new PyromorphiteResource().register();
        // 创建并注册砷铅矿的 GEO 资源
        new MimetiteResource().register();
        // 创建并注册车轮矿的 GEO 资源
        new BournoniteResource().register();
        // 创建并注册辉锑锡铅矿的 GEO 资源
        new FranckeiteResource().register();
        // 创建并注册圆柱锡矿的 GEO 资源
        new CylindriteResource().register();
        // 创建并注册马来亚石的 GEO 资源
        new MalayaiteResource().register();
        // 创建并注册水锡石的 GEO 资源
        new VarlamoffiteResource().register();
        // 创建并注册辉银矿的 GEO 资源
        new ArgentiteResource().register();
        // 创建并注册角银矿的 GEO 资源
        new CerargyriteResource().register();
        // 创建并注册硒银矿的 GEO 资源
        new NaumanniteResource().register();
    }

    /**
     * MoreOres 系列 GEO 资源的抽象基类。
     *
     * <p>所有 13 种矿物的 GEO 资源内部类都继承自此类。
     * 本类实现了 {@link GEOResource} 接口中的 {@link #isObtainableFromGEOMiner()} 方法，
     * 统一返回 {@code true}，表示这些矿物都可以通过 GEO 矿机获取。</p>
     */
    static abstract class MoreOresGeoResource implements GEOResource {

        /**
         * 判断当前资源是否可以通过 GEO 矿机获取。
         *
         * @return 始终返回 {@code true}，表示所有 MoreOres 矿物都支持 GEO 矿机产出
         */
        @Override
        public boolean isObtainableFromGEOMiner() {
            // 喵～所有 MoreOres 矿物都允许通过 GEO 矿机获取，所以始终返回 true
            return true;
        }
    }

    /**
     * 水氯镁石（Bischofite）的 GEO 资源定义类。
     *
     * <p>水氯镁石可在主世界（NORMAL）的海洋群系或高山群系中由 GEO 矿机产出。</p>
     */
    static class BischofiteResource extends MoreOresGeoResource {

        /**
         * 水氯镁石在 SlimeFun 系统中的唯一标识符。
         * 使用插件命名空间和矿物英文名 "bischofite" 创建。
         */
        private final NamespacedKey key = new NamespacedKey(MoreOres.getInstance(), "bischofite");

        /**
         * 获取水氯镁石的唯一标识符。
         *
         * @return 水氯镁石对应的 NamespacedKey 实例
         */
        @Override
        public NamespacedKey getKey() {
            // 返回水氯镁石的唯一标识符，用于在 SlimeFun 系统中识别此资源
            return key;
        }

        /**
         * 获取水氯镁石的显示名称。
         *
         * @return 中文名称 "水氯镁石"
         */
        @Override
        @Nonnull
        public String getName() {
            // 返回矿物的中文显示名称，玩家在 GEO 矿机界面中看到的就是这个名字
            return "水氯镁石";
        }

        /**
         * 获取水氯镁石对应的物品图标。
         *
         * @return 水氯镁石的物品堆叠副本
         */
        @Override
        @Nonnull
        public ItemStack getItem() {
            // 返回 MoreOresItems 中预定义的水氯镁石物品的克隆副本，避免修改原始物品
            return MoreOresItems.BISCHOFITE.clone();
        }

        /**
         * 根据维度环境和群系计算水氯镁石的默认产出量。
         *
         * <p>产出规则：仅在主世界（NORMAL）的海洋群系或高山群系中产出，
         * 其他维度或群系返回 0（不产出）。</p>
         *
         * @param environment 当前维度环境（主世界/下界/末地）
         * @param biome 当前所在群系类型
         * @return 该环境和群系下水氯镁石的默认产出量，不满足条件时返回 0
         */
        @Override
        public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
            // 如果不是主世界维度，则不产出水氯镁石
            if (environment != World.Environment.NORMAL) return 0;
            // 如果当前群系属于海洋群系或高山群系，则从配置文件中读取最小产出量
            if (OCEAN_BIOMES.contains(biome) || MOUNTAIN_BIOMES.contains(biome)) {
                // 从 config.yml 读取水氯镁石的最小产出量，默认值为 1
                return MoreOres.getInstance().getConfig().getInt("geo_miner.bischofite.min", 1);
            }
            // 不满足群系条件时，返回 0 表示不产出
            return 0;
        }

        /**
         * 获取水氯镁石产出量的最大随机偏差值。
         *
         * <p>偏差值 = 最大产出量 - 最小产出量，GEO 矿机会在此范围内随机波动产出。</p>
         *
         * @return 水氯镁石产出量的偏差范围
         */
        @Override
        public int getMaxDeviation() {
            // 从配置文件读取水氯镁石的最大产出量，默认值为 3
            int max = MoreOres.getInstance().getConfig().getInt("geo_miner.bischofite.max", 3);
            // 从配置文件读取水氯镁石的最小产出量，默认值为 1
            int min = MoreOres.getInstance().getConfig().getInt("geo_miner.bischofite.min", 1);
            // 偏差值 = 最大值 - 最小值，用于 GEO 矿机计算随机产出波动范围
            return max - min;
        }
    }

    /**
     * 卤水（Brine）的 GEO 资源定义类。
     *
     * <p>卤水可在主世界（NORMAL）的海洋群系或高山群系中由 GEO 矿机产出。</p>
     */
    static class BrineResource extends MoreOresGeoResource {

        /**
         * 卤水在 SlimeFun 系统中的唯一标识符。
         * 使用插件命名空间和矿物英文名 "brine" 创建。
         */
        private final NamespacedKey key = new NamespacedKey(MoreOres.getInstance(), "brine");

        /**
         * 获取卤水的唯一标识符。
         *
         * @return 卤水对应的 NamespacedKey 实例
         */
        @Override
        public NamespacedKey getKey() {
            // 返回卤水的唯一标识符
            return key;
        }

        /**
         * 获取卤水的显示名称。
         *
         * @return 中文名称 "卤水"
         */
        @Override
        @Nonnull
        public String getName() {
            // 返回矿物的中文显示名称
            return "卤水";
        }

        /**
         * 获取卤水对应的物品图标。
         *
         * @return 卤水的物品堆叠副本
         */
        @Override
        @Nonnull
        public ItemStack getItem() {
            // 返回 MoreOresItems 中预定义的卤水物品的克隆副本
            return MoreOresItems.BRINE.clone();
        }

        /**
         * 根据维度环境和群系计算卤水的默认产出量。
         *
         * <p>产出规则：仅在主世界（NORMAL）的海洋群系或高山群系中产出。</p>
         *
         * @param environment 当前维度环境
         * @param biome 当前所在群系类型
         * @return 该环境和群系下卤水的默认产出量
         */
        @Override
        public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
            // 如果不是主世界维度，则不产出卤水
            if (environment != World.Environment.NORMAL) return 0;
            // 如果当前群系属于海洋群系或高山群系，则从配置文件中读取最小产出量
            if (OCEAN_BIOMES.contains(biome) || MOUNTAIN_BIOMES.contains(biome)) {
                // 从 config.yml 读取卤水的最小产出量，默认值为 1
                return MoreOres.getInstance().getConfig().getInt("geo_miner.brine.min", 1);
            }
            // 不满足群系条件时，返回 0 表示不产出
            return 0;
        }

        /**
         * 获取卤水产出量的最大随机偏差值。
         *
         * @return 卤水产出量的偏差范围
         */
        @Override
        public int getMaxDeviation() {
            // 从配置文件读取卤水的最大产出量，默认值为 3
            int max = MoreOres.getInstance().getConfig().getInt("geo_miner.brine.max", 3);
            // 从配置文件读取卤水的最小产出量，默认值为 1
            int min = MoreOres.getInstance().getConfig().getInt("geo_miner.brine.min", 1);
            // 偏差值 = 最大值 - 最小值
            return max - min;
        }
    }

    /**
     * 光卤石（Carnallite）的 GEO 资源定义类。
     *
     * <p>光卤石可在主世界（NORMAL）的任意群系中由 GEO 矿机产出，
     * 是一种不限群系、主世界通用的矿物资源。</p>
     */
    static class CarnalliteResource extends MoreOresGeoResource {

        /**
         * 光卤石在 SlimeFun 系统中的唯一标识符。
         * 使用插件命名空间和矿物英文名 "carnallite" 创建。
         */
        private final NamespacedKey key = new NamespacedKey(MoreOres.getInstance(), "carnallite");

        /**
         * 获取光卤石的唯一标识符。
         *
         * @return 光卤石对应的 NamespacedKey 实例
         */
        @Override
        public NamespacedKey getKey() {
            // 返回光卤石的唯一标识符
            return key;
        }

        /**
         * 获取光卤石的显示名称。
         *
         * @return 中文名称 "光卤石"
         */
        @Override
        @Nonnull
        public String getName() {
            // 返回矿物的中文显示名称
            return "光卤石";
        }

        /**
         * 获取光卤石对应的物品图标。
         *
         * @return 光卤石的物品堆叠副本
         */
        @Override
        @Nonnull
        public ItemStack getItem() {
            // 返回 MoreOresItems 中预定义的光卤石物品的克隆副本
            return MoreOresItems.CARNALLITE.clone();
        }

        /**
         * 根据维度环境和群系计算光卤石的默认产出量。
         *
         * <p>产出规则：仅在主世界（NORMAL）的任意群系中均产出，
         * 不限制具体群系类型。</p>
         *
         * @param environment 当前维度环境
         * @param biome 当前所在群系类型
         * @return 主世界下光卤石的默认产出量，非主世界返回 0
         */
        @Override
        public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
            // 如果不是主世界维度，则不产出光卤石
            if (environment != World.Environment.NORMAL) return 0;
            // 主世界任意群系都产出光卤石，直接从配置文件中读取最小产出量
            return MoreOres.getInstance().getConfig().getInt("geo_miner.carnallite.min", 1);
        }

        /**
         * 获取光卤石产出量的最大随机偏差值。
         *
         * @return 光卤石产出量的偏差范围
         */
        @Override
        public int getMaxDeviation() {
            // 从配置文件读取光卤石的最大产出量，默认值为 3
            int max = MoreOres.getInstance().getConfig().getInt("geo_miner.carnallite.max", 3);
            // 从配置文件读取光卤石的最小产出量，默认值为 1
            int min = MoreOres.getInstance().getConfig().getInt("geo_miner.carnallite.min", 1);
            // 偏差值 = 最大值 - 最小值
            return max - min;
        }
    }

    /**
     * 磷氯铅矿（Pyromorphite）的 GEO 资源定义类。
     *
     * <p>磷氯铅矿可在主世界（NORMAL）的海洋群系或河流群系中由 GEO 矿机产出。</p>
     */
    static class PyromorphiteResource extends MoreOresGeoResource {

        /**
         * 磷氯铅矿在 SlimeFun 系统中的唯一标识符。
         * 使用插件命名空间和矿物英文名 "pyromorphite" 创建。
         */
        private final NamespacedKey key = new NamespacedKey(MoreOres.getInstance(), "pyromorphite");

        /**
         * 获取磷氯铅矿的唯一标识符。
         *
         * @return 磷氯铅矿对应的 NamespacedKey 实例
         */
        @Override
        public NamespacedKey getKey() {
            // 返回磷氯铅矿的唯一标识符
            return key;
        }

        /**
         * 获取磷氯铅矿的显示名称。
         *
         * @return 中文名称 "磷氯铅矿"
         */
        @Override
        @Nonnull
        public String getName() {
            // 返回矿物的中文显示名称
            return "磷氯铅矿";
        }

        /**
         * 获取磷氯铅矿对应的物品图标。
         *
         * @return 磷氯铅矿的物品堆叠副本
         */
        @Override
        @Nonnull
        public ItemStack getItem() {
            // 返回 MoreOresItems 中预定义的磷氯铅矿物品的克隆副本
            return MoreOresItems.PYROMORPHITE.clone();
        }

        /**
         * 根据维度环境和群系计算磷氯铅矿的默认产出量。
         *
         * <p>产出规则：仅在主世界（NORMAL）的海洋群系或河流群系中产出。</p>
         *
         * @param environment 当前维度环境
         * @param biome 当前所在群系类型
         * @return 该环境和群系下磷氯铅矿的默认产出量
         */
        @Override
        public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
            // 如果不是主世界维度，则不产出磷氯铅矿
            if (environment != World.Environment.NORMAL) return 0;
            // 如果当前群系属于海洋群系或河流群系，则从配置文件中读取最小产出量
            if (OCEAN_BIOMES.contains(biome) || RIVER_BIOMES.contains(biome)) {
                // 从 config.yml 读取磷氯铅矿的最小产出量，默认值为 1
                return MoreOres.getInstance().getConfig().getInt("geo_miner.pyromorphite.min", 1);
            }
            // 不满足群系条件时，返回 0 表示不产出
            return 0;
        }

        /**
         * 获取磷氯铅矿产出量的最大随机偏差值。
         *
         * @return 磷氯铅矿产出量的偏差范围
         */
        @Override
        public int getMaxDeviation() {
            // 从配置文件读取磷氯铅矿的最大产出量，默认值为 5（比其他矿物偏差更大）
            int max = MoreOres.getInstance().getConfig().getInt("geo_miner.pyromorphite.max", 5);
            // 从配置文件读取磷氯铅矿的最小产出量，默认值为 1
            int min = MoreOres.getInstance().getConfig().getInt("geo_miner.pyromorphite.min", 1);
            // 偏差值 = 最大值 - 最小值
            return max - min;
        }
    }

    /**
     * 砷铅矿（Mimetite）的 GEO 资源定义类。
     *
     * <p>砷铅矿可在下界（NETHER）的任意群系中由 GEO 矿机产出，
     * 是一种下界通用的矿物资源。</p>
     */
    static class MimetiteResource extends MoreOresGeoResource {

        /**
         * 砷铅矿在 SlimeFun 系统中的唯一标识符。
         * 使用插件命名空间和矿物英文名 "mimetite" 创建。
         */
        private final NamespacedKey key = new NamespacedKey(MoreOres.getInstance(), "mimetite");

        /**
         * 获取砷铅矿的唯一标识符。
         *
         * @return 砷铅矿对应的 NamespacedKey 实例
         */
        @Override
        public NamespacedKey getKey() {
            // 返回砷铅矿的唯一标识符
            return key;
        }

        /**
         * 获取砷铅矿的显示名称。
         *
         * @return 中文名称 "砷铅矿"
         */
        @Override
        @Nonnull
        public String getName() {
            // 返回矿物的中文显示名称
            return "砷铅矿";
        }

        /**
         * 获取砷铅矿对应的物品图标。
         *
         * @return 砷铅矿的物品堆叠副本
         */
        @Override
        @Nonnull
        public ItemStack getItem() {
            // 返回 MoreOresItems 中预定义的砷铅矿物品的克隆副本
            return MoreOresItems.MIMETITE.clone();
        }

        /**
         * 根据维度环境和群系计算砷铅矿的默认产出量。
         *
         * <p>产出规则：仅在下界（NETHER）的任意群系中均产出，
         * 不限制具体群系类型。</p>
         *
         * @param environment 当前维度环境
         * @param biome 当前所在群系类型
         * @return 下界中砷铅矿的默认产出量，非下界返回 0
         */
        @Override
        public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
            // 如果不是下界维度，则不产出砷铅矿
            if (environment != World.Environment.NETHER) return 0;
            // 下界任意群系都产出砷铅矿，直接从配置文件中读取最小产出量
            return MoreOres.getInstance().getConfig().getInt("geo_miner.mimetite.min", 1);
        }

        /**
         * 获取砷铅矿产出量的最大随机偏差值。
         *
         * @return 砷铅矿产出量的偏差范围
         */
        @Override
        public int getMaxDeviation() {
            // 从配置文件读取砷铅矿的最大产出量，默认值为 5
            int max = MoreOres.getInstance().getConfig().getInt("geo_miner.mimetite.max", 5);
            // 从配置文件读取砷铅矿的最小产出量，默认值为 1
            int min = MoreOres.getInstance().getConfig().getInt("geo_miner.mimetite.min", 1);
            // 偏差值 = 最大值 - 最小值
            return max - min;
        }
    }

    /**
     * 车轮矿（Bournonite）的 GEO 资源定义类。
     *
     * <p>车轮矿可在主世界（NORMAL）的雪原群系中由 GEO 矿机产出。</p>
     */
    static class BournoniteResource extends MoreOresGeoResource {

        /**
         * 车轮矿在 SlimeFun 系统中的唯一标识符。
         * 使用插件命名空间和矿物英文名 "bournonite" 创建。
         */
        private final NamespacedKey key = new NamespacedKey(MoreOres.getInstance(), "bournonite");

        /**
         * 获取车轮矿的唯一标识符。
         *
         * @return 车轮矿对应的 NamespacedKey 实例
         */
        @Override
        public NamespacedKey getKey() {
            // 返回车轮矿的唯一标识符
            return key;
        }

        /**
         * 获取车轮矿的显示名称。
         *
         * @return 中文名称 "车轮矿"
         */
        @Override
        @Nonnull
        public String getName() {
            // 返回矿物的中文显示名称
            return "车轮矿";
        }

        /**
         * 获取车轮矿对应的物品图标。
         *
         * @return 车轮矿的物品堆叠副本
         */
        @Override
        @Nonnull
        public ItemStack getItem() {
            // 返回 MoreOresItems 中预定义的车轮矿物品的克隆副本
            return MoreOresItems.BOURNONITE.clone();
        }

        /**
         * 根据维度环境和群系计算车轮矿的默认产出量。
         *
         * <p>产出规则：仅在主世界（NORMAL）的雪原群系中产出，
         * 包括雪原、雪林、冰刺、冻洋等多种冰雪覆盖的群系。</p>
         *
         * @param environment 当前维度环境
         * @param biome 当前所在群系类型
         * @return 该环境和群系下车轮矿的默认产出量
         */
        @Override
        public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
            // 如果不是主世界维度，则不产出车轮矿
            if (environment != World.Environment.NORMAL) return 0;
            // 如果当前群系属于雪原群系集合，则从配置文件中读取最小产出量
            if (SNOWY_BIOMES.contains(biome)) {
                // 从 config.yml 读取车轮矿的最小产出量，默认值为 1
                return MoreOres.getInstance().getConfig().getInt("geo_miner.bournonite.min", 1);
            }
            // 不满足群系条件时，返回 0 表示不产出
            return 0;
        }

        /**
         * 获取车轮矿产出量的最大随机偏差值。
         *
         * @return 车轮矿产出量的偏差范围
         */
        @Override
        public int getMaxDeviation() {
            // 从配置文件读取车轮矿的最大产出量，默认值为 5
            int max = MoreOres.getInstance().getConfig().getInt("geo_miner.bournonite.max", 5);
            // 从配置文件读取车轮矿的最小产出量，默认值为 1
            int min = MoreOres.getInstance().getConfig().getInt("geo_miner.bournonite.min", 1);
            // 偏差值 = 最大值 - 最小值
            return max - min;
        }
    }

    /**
     * 辉锑锡铅矿（Franckeite）的 GEO 资源定义类。
     *
     * <p>辉锑锡铅矿可在主世界（NORMAL）的沙漠或恶地群系中由 GEO 矿机产出。</p>
     */
    static class FranckeiteResource extends MoreOresGeoResource {

        /**
         * 辉锑锡铅矿在 SlimeFun 系统中的唯一标识符。
         * 使用插件命名空间和矿物英文名 "franckeite" 创建。
         */
        private final NamespacedKey key = new NamespacedKey(MoreOres.getInstance(), "franckeite");

        /**
         * 获取辉锑锡铅矿的唯一标识符。
         *
         * @return 辉锑锡铅矿对应的 NamespacedKey 实例
         */
        @Override
        public NamespacedKey getKey() {
            // 返回辉锑锡铅矿的唯一标识符
            return key;
        }

        /**
         * 获取辉锑锡铅矿的显示名称。
         *
         * @return 中文名称 "辉锑锡铅矿"
         */
        @Override
        @Nonnull
        public String getName() {
            // 返回矿物的中文显示名称
            return "辉锑锡铅矿";
        }

        /**
         * 获取辉锑锡铅矿对应的物品图标。
         *
         * @return 辉锑锡铅矿的物品堆叠副本
         */
        @Override
        @Nonnull
        public ItemStack getItem() {
            // 返回 MoreOresItems 中预定义的辉锑锡铅矿物品的克隆副本
            return MoreOresItems.FRANCKEITE.clone();
        }

        /**
         * 根据维度环境和群系计算辉锑锡铅矿的默认产出量。
         *
         * <p>产出规则：仅在主世界（NORMAL）的沙漠或恶地群系中产出，
         * 包括沙漠、恶地、丛林恶地和侵蚀恶地。</p>
         *
         * @param environment 当前维度环境
         * @param biome 当前所在群系类型
         * @return 该环境和群系下辉锑锡铅矿的默认产出量
         */
        @Override
        public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
            // 如果不是主世界维度，则不产出辉锑锡铅矿
            if (environment != World.Environment.NORMAL) return 0;
            // 如果当前群系属于沙漠或恶地群系，则从配置文件中读取最小产出量
            if (DESERT_BADLANDS_BIOMES.contains(biome)) {
                // 从 config.yml 读取辉锑锡铅矿的最小产出量，默认值为 1
                return MoreOres.getInstance().getConfig().getInt("geo_miner.franckeite.min", 1);
            }
            // 不满足群系条件时，返回 0 表示不产出
            return 0;
        }

        /**
         * 获取辉锑锡铅矿产出量的最大随机偏差值。
         *
         * @return 辉锑锡铅矿产出量的偏差范围
         */
        @Override
        public int getMaxDeviation() {
            // 从配置文件读取辉锑锡铅矿的最大产出量，默认值为 3
            int max = MoreOres.getInstance().getConfig().getInt("geo_miner.franckeite.max", 3);
            // 从配置文件读取辉锑锡铅矿的最小产出量，默认值为 1
            int min = MoreOres.getInstance().getConfig().getInt("geo_miner.franckeite.min", 1);
            // 偏差值 = 最大值 - 最小值
            return max - min;
        }
    }

    /**
     * 圆柱锡矿（Cylindrite）的 GEO 资源定义类。
     *
     * <p>圆柱锡矿可在主世界（NORMAL）的金合欢群系中由 GEO 矿机产出。</p>
     */
    static class CylindriteResource extends MoreOresGeoResource {

        /**
         * 圆柱锡矿在 SlimeFun 系统中的唯一标识符。
         * 使用插件命名空间和矿物英文名 "cylindrite" 创建。
         */
        private final NamespacedKey key = new NamespacedKey(MoreOres.getInstance(), "cylindrite");

        /**
         * 获取圆柱锡矿的唯一标识符。
         *
         * @return 圆柱锡矿对应的 NamespacedKey 实例
         */
        @Override
        public NamespacedKey getKey() {
            // 返回圆柱锡矿的唯一标识符
            return key;
        }

        /**
         * 获取圆柱锡矿的显示名称。
         *
         * @return 中文名称 "圆柱锡矿"
         */
        @Override
        @Nonnull
        public String getName() {
            // 返回矿物的中文显示名称
            return "圆柱锡矿";
        }

        /**
         * 获取圆柱锡矿对应的物品图标。
         *
         * @return 圆柱锡矿的物品堆叠副本
         */
        @Override
        @Nonnull
        public ItemStack getItem() {
            // 返回 MoreOresItems 中预定义的圆柱锡矿物品的克隆副本
            return MoreOresItems.CYLINDRITE.clone();
        }

        /**
         * 根据维度环境和群系计算圆柱锡矿的默认产出量。
         *
         * <p>产出规则：仅在主世界（NORMAL）的金合欢群系中产出，
         * 包括金合欢草原、金合欢高原和风吹金合欢。</p>
         *
         * @param environment 当前维度环境
         * @param biome 当前所在群系类型
         * @return 该环境和群系下圆柱锡矿的默认产出量
         */
        @Override
        public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
            // 如果不是主世界维度，则不产出圆柱锡矿
            if (environment != World.Environment.NORMAL) return 0;
            // 如果当前群系属于金合欢群系，则从配置文件中读取最小产出量
            if (SAVANNA_BIOMES.contains(biome)) {
                // 从 config.yml 读取圆柱锡矿的最小产出量，默认值为 1
                return MoreOres.getInstance().getConfig().getInt("geo_miner.cylindrite.min", 1);
            }
            // 不满足群系条件时，返回 0 表示不产出
            return 0;
        }

        /**
         * 获取圆柱锡矿产出量的最大随机偏差值。
         *
         * @return 圆柱锡矿产出量的偏差范围
         */
        @Override
        public int getMaxDeviation() {
            // 从配置文件读取圆柱锡矿的最大产出量，默认值为 3
            int max = MoreOres.getInstance().getConfig().getInt("geo_miner.cylindrite.max", 3);
            // 从配置文件读取圆柱锡矿的最小产出量，默认值为 1
            int min = MoreOres.getInstance().getConfig().getInt("geo_miner.cylindrite.min", 1);
            // 偏差值 = 最大值 - 最小值
            return max - min;
        }
    }

    /**
     * 马来亚石（Malayaite）的 GEO 资源定义类。
     *
     * <p>马来亚石可在主世界（NORMAL）的草原群系中由 GEO 矿机产出。</p>
     */
    static class MalayaiteResource extends MoreOresGeoResource {

        /**
         * 马来亚石在 SlimeFun 系统中的唯一标识符。
         * 使用插件命名空间和矿物英文名 "malayaite" 创建。
         */
        private final NamespacedKey key = new NamespacedKey(MoreOres.getInstance(), "malayaite");

        /**
         * 获取马来亚石的唯一标识符。
         *
         * @return 马来亚石对应的 NamespacedKey 实例
         */
        @Override
        public NamespacedKey getKey() {
            // 返回马来亚石的唯一标识符
            return key;
        }

        /**
         * 获取马来亚石的显示名称。
         *
         * @return 中文名称 "马来亚石"
         */
        @Override
        @Nonnull
        public String getName() {
            // 返回矿物的中文显示名称
            return "马来亚石";
        }

        /**
         * 获取马来亚石对应的物品图标。
         *
         * @return 马来亚石的物品堆叠副本
         */
        @Override
        @Nonnull
        public ItemStack getItem() {
            // 返回 MoreOresItems 中预定义的马来亚石物品的克隆副本
            return MoreOresItems.MALAYAITE.clone();
        }

        /**
         * 根据维度环境和群系计算马来亚石的默认产出量。
         *
         * <p>产出规则：仅在主世界（NORMAL）的草原群系中产出，
         * 包括普通草原和向日葵草原。</p>
         *
         * @param environment 当前维度环境
         * @param biome 当前所在群系类型
         * @return 该环境和群系下马来亚石的默认产出量
         */
        @Override
        public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
            // 如果不是主世界维度，则不产出马来亚石
            if (environment != World.Environment.NORMAL) return 0;
            // 如果当前群系属于草原群系，则从配置文件中读取最小产出量
            if (PLAINS_BIOMES.contains(biome)) {
                // 从 config.yml 读取马来亚石的最小产出量，默认值为 1
                return MoreOres.getInstance().getConfig().getInt("geo_miner.malayaite.min", 1);
            }
            // 不满足群系条件时，返回 0 表示不产出
            return 0;
        }

        /**
         * 获取马来亚石产出量的最大随机偏差值。
         *
         * @return 马来亚石产出量的偏差范围
         */
        @Override
        public int getMaxDeviation() {
            // 从配置文件读取马来亚石的最大产出量，默认值为 3
            int max = MoreOres.getInstance().getConfig().getInt("geo_miner.malayaite.max", 3);
            // 从配置文件读取马来亚石的最小产出量，默认值为 1
            int min = MoreOres.getInstance().getConfig().getInt("geo_miner.malayaite.min", 1);
            // 偏差值 = 最大值 - 最小值
            return max - min;
        }
    }

    /**
     * 水锡石（Varlamoffite）的 GEO 资源定义类。
     *
     * <p>水锡石可在末地（THE_END）的任意群系中由 GEO 矿机产出，
     * 是一种末地专属的矿物资源。</p>
     */
    static class VarlamoffiteResource extends MoreOresGeoResource {

        /**
         * 水锡石在 SlimeFun 系统中的唯一标识符。
         * 使用插件命名空间和矿物英文名 "varlamoffite" 创建。
         */
        private final NamespacedKey key = new NamespacedKey(MoreOres.getInstance(), "varlamoffite");

        /**
         * 获取水锡石的唯一标识符。
         *
         * @return 水锡石对应的 NamespacedKey 实例
         */
        @Override
        public NamespacedKey getKey() {
            // 返回水锡石的唯一标识符
            return key;
        }

        /**
         * 获取水锡石的显示名称。
         *
         * @return 中文名称 "水锡石"
         */
        @Override
        @Nonnull
        public String getName() {
            // 返回矿物的中文显示名称
            return "水锡石";
        }

        /**
         * 获取水锡石对应的物品图标。
         *
         * @return 水锡石的物品堆叠副本
         */
        @Override
        @Nonnull
        public ItemStack getItem() {
            // 返回 MoreOresItems 中预定义的水锡石物品的克隆副本
            return MoreOresItems.VARLAMOFFITE.clone();
        }

        /**
         * 根据维度环境和群系计算水锡石的默认产出量。
         *
         * <p>产出规则：仅在末地（THE_END）的任意群系中均产出，
         * 不限制具体群系类型。</p>
         *
         * @param environment 当前维度环境
         * @param biome 当前所在群系类型
         * @return 末地中水锡石的默认产出量，非末地返回 0
         */
        @Override
        public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
            // 如果不是末地维度，则不产出水锡石
            if (environment != World.Environment.THE_END) return 0;
            // 末地任意群系都产出水锡石，直接从配置文件中读取最小产出量
            return MoreOres.getInstance().getConfig().getInt("geo_miner.varlamoffite.min", 1);
        }

        /**
         * 获取水锡石产出量的最大随机偏差值。
         *
         * @return 水锡石产出量的偏差范围
         */
        @Override
        public int getMaxDeviation() {
            // 从配置文件读取水锡石的最大产出量，默认值为 3
            int max = MoreOres.getInstance().getConfig().getInt("geo_miner.varlamoffite.max", 3);
            // 从配置文件读取水锡石的最小产出量，默认值为 1
            int min = MoreOres.getInstance().getConfig().getInt("geo_miner.varlamoffite.min", 1);
            // 偏差值 = 最大值 - 最小值
            return max - min;
        }
    }

    /**
     * 辉银矿（Argentite）的 GEO 资源定义类。
     *
     * <p>辉银矿可在下界（NETHER）的任意群系中由 GEO 矿机产出，
     * 是一种下界通用的矿物资源。</p>
     */
    static class ArgentiteResource extends MoreOresGeoResource {

        /**
         * 辉银矿在 SlimeFun 系统中的唯一标识符。
         * 使用插件命名空间和矿物英文名 "argentite" 创建。
         */
        private final NamespacedKey key = new NamespacedKey(MoreOres.getInstance(), "argentite");

        /**
         * 获取辉银矿的唯一标识符。
         *
         * @return 辉银矿对应的 NamespacedKey 实例
         */
        @Override
        public NamespacedKey getKey() {
            // 返回辉银矿的唯一标识符
            return key;
        }

        /**
         * 获取辉银矿的显示名称。
         *
         * @return 中文名称 "辉银矿"
         */
        @Override
        @Nonnull
        public String getName() {
            // 返回矿物的中文显示名称
            return "辉银矿";
        }

        /**
         * 获取辉银矿对应的物品图标。
         *
         * @return 辉银矿的物品堆叠副本
         */
        @Override
        @Nonnull
        public ItemStack getItem() {
            // 返回 MoreOresItems 中预定义的辉银矿物品的克隆副本
            return MoreOresItems.ARGENTITE.clone();
        }

        /**
         * 根据维度环境和群系计算辉银矿的默认产出量。
         *
         * <p>产出规则：仅在下界（NETHER）的任意群系中均产出，
         * 不限制具体群系类型。</p>
         *
         * @param environment 当前维度环境
         * @param biome 当前所在群系类型
         * @return 下界中辉银矿的默认产出量，非下界返回 0
         */
        @Override
        public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
            // 如果不是下界维度，则不产出辉银矿
            if (environment != World.Environment.NETHER) return 0;
            // 下界任意群系都产出辉银矿，直接从配置文件中读取最小产出量
            return MoreOres.getInstance().getConfig().getInt("geo_miner.argentite.min", 1);
        }

        /**
         * 获取辉银矿产出量的最大随机偏差值。
         *
         * @return 辉银矿产出量的偏差范围
         */
        @Override
        public int getMaxDeviation() {
            // 从配置文件读取辉银矿的最大产出量，默认值为 3
            int max = MoreOres.getInstance().getConfig().getInt("geo_miner.argentite.max", 3);
            // 从配置文件读取辉银矿的最小产出量，默认值为 1
            int min = MoreOres.getInstance().getConfig().getInt("geo_miner.argentite.min", 1);
            // 偏差值 = 最大值 - 最小值
            return max - min;
        }
    }

    /**
     * 角银矿（Cerargyrite）的 GEO 资源定义类。
     *
     * <p>角银矿可在主世界（NORMAL）的沙漠或恶地群系中由 GEO 矿机产出。</p>
     */
    static class CerargyriteResource extends MoreOresGeoResource {

        /**
         * 角银矿在 SlimeFun 系统中的唯一标识符。
         * 使用插件命名空间和矿物英文名 "cerargyrite" 创建。
         */
        private final NamespacedKey key = new NamespacedKey(MoreOres.getInstance(), "cerargyrite");

        /**
         * 获取角银矿的唯一标识符。
         *
         * @return 角银矿对应的 NamespacedKey 实例
         */
        @Override
        public NamespacedKey getKey() {
            // 返回角银矿的唯一标识符
            return key;
        }

        /**
         * 获取角银矿的显示名称。
         *
         * @return 中文名称 "角银矿"
         */
        @Override
        @Nonnull
        public String getName() {
            // 返回矿物的中文显示名称
            return "角银矿";
        }

        /**
         * 获取角银矿对应的物品图标。
         *
         * @return 角银矿的物品堆叠副本
         */
        @Override
        @Nonnull
        public ItemStack getItem() {
            // 返回 MoreOresItems 中预定义的角银矿物品的克隆副本
            return MoreOresItems.CERARGYRITE.clone();
        }

        /**
         * 根据维度环境和群系计算角银矿的默认产出量。
         *
         * <p>产出规则：仅在主世界（NORMAL）的沙漠或恶地群系中产出，
         * 包括沙漠、恶地、丛林恶地和侵蚀恶地。</p>
         *
         * @param environment 当前维度环境
         * @param biome 当前所在群系类型
         * @return 该环境和群系下角银矿的默认产出量
         */
        @Override
        public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
            // 如果不是主世界维度，则不产出角银矿
            if (environment != World.Environment.NORMAL) return 0;
            // 如果当前群系属于沙漠或恶地群系，则从配置文件中读取最小产出量
            if (DESERT_BADLANDS_BIOMES.contains(biome)) {
                // 从 config.yml 读取角银矿的最小产出量，默认值为 1
                return MoreOres.getInstance().getConfig().getInt("geo_miner.cerargyrite.min", 1);
            }
            // 不满足群系条件时，返回 0 表示不产出
            return 0;
        }

        /**
         * 获取角银矿产出量的最大随机偏差值。
         *
         * @return 角银矿产出量的偏差范围
         */
        @Override
        public int getMaxDeviation() {
            // 从配置文件读取角银矿的最大产出量，默认值为 3
            int max = MoreOres.getInstance().getConfig().getInt("geo_miner.cerargyrite.max", 3);
            // 从配置文件读取角银矿的最小产出量，默认值为 1
            int min = MoreOres.getInstance().getConfig().getInt("geo_miner.cerargyrite.min", 1);
            // 偏差值 = 最大值 - 最小值
            return max - min;
        }
    }

    /**
     * 硒银矿（Naumannite）的 GEO 资源定义类。
     *
     * <p>硒银矿可在下界（NETHER）的玄武岩三角洲群系中由 GEO 矿机产出，
     * 是一种下界限定特定群系的稀有矿物。</p>
     */
    static class NaumanniteResource extends MoreOresGeoResource {

        /**
         * 硒银矿在 SlimeFun 系统中的唯一标识符。
         * 使用插件命名空间和矿物英文名 "naumannite" 创建。
         */
        private final NamespacedKey key = new NamespacedKey(MoreOres.getInstance(), "naumannite");

        /**
         * 获取硒银矿的唯一标识符。
         *
         * @return 硒银矿对应的 NamespacedKey 实例
         */
        @Override
        public NamespacedKey getKey() {
            // 返回硒银矿的唯一标识符
            return key;
        }

        /**
         * 获取硒银矿的显示名称。
         *
         * @return 中文名称 "硒银矿"
         */
        @Override
        @Nonnull
        public String getName() {
            // 返回矿物的中文显示名称
            return "硒银矿";
        }

        /**
         * 获取硒银矿对应的物品图标。
         *
         * @return 硒银矿的物品堆叠副本
         */
        @Override
        @Nonnull
        public ItemStack getItem() {
            // 返回 MoreOresItems 中预定义的硒银矿物品的克隆副本
            return MoreOresItems.NAUMANNITE.clone();
        }

        /**
         * 根据维度环境和群系计算硒银矿的默认产出量。
         *
         * <p>产出规则：仅在下界（NETHER）的玄武岩三角洲群系中产出，
         * 这是所有矿物中限制最严格的产出条件之一。</p>
         *
         * @param environment 当前维度环境
         * @param biome 当前所在群系类型
         * @return 该环境和群系下硒银矿的默认产出量
         */
        @Override
        public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
            // 如果不是下界维度，则不产出硒银矿
            if (environment != World.Environment.NETHER) return 0;
            // 如果当前群系属于玄武岩三角洲，则从配置文件中读取最小产出量
            if (BASALT_DELTAS_BIOME.contains(biome)) {
                // 从 config.yml 读取硒银矿的最小产出量，默认值为 1
                return MoreOres.getInstance().getConfig().getInt("geo_miner.naumannite.min", 1);
            }
            // 不满足群系条件时，返回 0 表示不产出
            return 0;
        }

        /**
         * 获取硒银矿产出量的最大随机偏差值。
         *
         * @return 硒银矿产出量的偏差范围
         */
        @Override
        public int getMaxDeviation() {
            // 从配置文件读取硒银矿的最大产出量，默认值为 3
            int max = MoreOres.getInstance().getConfig().getInt("geo_miner.naumannite.max", 3);
            // 从配置文件读取硒银矿的最小产出量，默认值为 1
            int min = MoreOres.getInstance().getConfig().getInt("geo_miner.naumannite.min", 1);
            // 偏差值 = 最大值 - 最小值
            return max - min;
        }
    }
}
