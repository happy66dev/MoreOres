package io.github.happy66dev.moreores;

import io.github.thebusybiscuit.slimefun4.api.geo.GEOResource;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Set;

public final class MoreOresResources {

    private static final Set<Biome> OCEAN_BIOMES = Set.of(
            Biome.OCEAN, Biome.COLD_OCEAN, Biome.DEEP_OCEAN,
            Biome.DEEP_COLD_OCEAN, Biome.DEEP_FROZEN_OCEAN,
            Biome.DEEP_LUKEWARM_OCEAN, Biome.FROZEN_OCEAN,
            Biome.LUKEWARM_OCEAN, Biome.WARM_OCEAN
    );

    private static final Set<Biome> MOUNTAIN_BIOMES = Set.of(
            Biome.JAGGED_PEAKS, Biome.FROZEN_PEAKS, Biome.STONY_PEAKS,
            Biome.SNOWY_SLOPES, Biome.GROVE, Biome.MEADOW,
            Biome.WINDSWEPT_HILLS, Biome.WINDSWEPT_GRAVELLY_HILLS,
            Biome.WINDSWEPT_FOREST
    );

    private static final Set<Biome> RIVER_BIOMES = Set.of(
            Biome.RIVER, Biome.FROZEN_RIVER
    );

    private static final Set<Biome> SNOWY_BIOMES = Set.of(
            Biome.SNOWY_PLAINS, Biome.SNOWY_TAIGA, Biome.SNOWY_BEACH,
            Biome.ICE_SPIKES, Biome.FROZEN_OCEAN, Biome.DEEP_FROZEN_OCEAN,
            Biome.FROZEN_RIVER, Biome.FROZEN_PEAKS, Biome.JAGGED_PEAKS,
            Biome.SNOWY_SLOPES, Biome.GROVE
    );

    private static final Set<Biome> DESERT_BADLANDS_BIOMES = Set.of(
            Biome.DESERT, Biome.BADLANDS, Biome.WOODED_BADLANDS, Biome.ERODED_BADLANDS
    );

    private static final Set<Biome> SAVANNA_BIOMES = Set.of(
            Biome.SAVANNA, Biome.SAVANNA_PLATEAU, Biome.WINDSWEPT_SAVANNA
    );

    private static final Set<Biome> PLAINS_BIOMES = Set.of(
            Biome.PLAINS, Biome.SUNFLOWER_PLAINS
    );

    private static final Set<Biome> BASALT_DELTAS_BIOME = Set.of(Biome.BASALT_DELTAS);

    private MoreOresResources() {}

    public static void registerAll() {
        new BischofiteResource().register();
        new BrineResource().register();
        new CarnalliteResource().register();
        new PyromorphiteResource().register();
        new MimetiteResource().register();
        new BournoniteResource().register();
        new FranckeiteResource().register();
        new CylindriteResource().register();
        new MalayaiteResource().register();
        new VarlamoffiteResource().register();
        new ArgentiteResource().register();
        new CerargyriteResource().register();
        new NaumanniteResource().register();
    }

    static abstract class MoreOresGeoResource implements GEOResource {
        @Override
        public boolean isObtainableFromGEOMiner() {
            return true;
        }
    }

    static class BischofiteResource extends MoreOresGeoResource {
        private final NamespacedKey key = new NamespacedKey(MoreOres.getInstance(), "bischofite");

        @Override
        public NamespacedKey getKey() {
            return key;
        }

        @Override
        @Nonnull
        public String getName() {
            return "水氯镁石";
        }

        @Override
        @Nonnull
        public ItemStack getItem() {
            return MoreOresItems.BISCHOFITE.clone();
        }

        @Override
        public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
            if (environment != World.Environment.NORMAL) return 0;
            if (OCEAN_BIOMES.contains(biome) || MOUNTAIN_BIOMES.contains(biome)) {
                return MoreOres.getInstance().getConfig().getInt("geo_miner.bischofite.min", 1);
            }
            return 0;
        }

        @Override
        public int getMaxDeviation() {
            int max = MoreOres.getInstance().getConfig().getInt("geo_miner.bischofite.max", 3);
            int min = MoreOres.getInstance().getConfig().getInt("geo_miner.bischofite.min", 1);
            return max - min;
        }
    }

    static class BrineResource extends MoreOresGeoResource {
        private final NamespacedKey key = new NamespacedKey(MoreOres.getInstance(), "brine");

        @Override
        public NamespacedKey getKey() {
            return key;
        }

        @Override
        @Nonnull
        public String getName() {
            return "卤水";
        }

        @Override
        @Nonnull
        public ItemStack getItem() {
            return MoreOresItems.BRINE.clone();
        }

        @Override
        public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
            if (environment != World.Environment.NORMAL) return 0;
            if (OCEAN_BIOMES.contains(biome) || MOUNTAIN_BIOMES.contains(biome)) {
                return MoreOres.getInstance().getConfig().getInt("geo_miner.brine.min", 1);
            }
            return 0;
        }

        @Override
        public int getMaxDeviation() {
            int max = MoreOres.getInstance().getConfig().getInt("geo_miner.brine.max", 3);
            int min = MoreOres.getInstance().getConfig().getInt("geo_miner.brine.min", 1);
            return max - min;
        }
    }

    static class CarnalliteResource extends MoreOresGeoResource {
        private final NamespacedKey key = new NamespacedKey(MoreOres.getInstance(), "carnallite");

        @Override
        public NamespacedKey getKey() {
            return key;
        }

        @Override
        @Nonnull
        public String getName() {
            return "光卤石";
        }

        @Override
        @Nonnull
        public ItemStack getItem() {
            return MoreOresItems.CARNALLITE.clone();
        }

        @Override
        public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
            if (environment != World.Environment.NORMAL) return 0;
            return MoreOres.getInstance().getConfig().getInt("geo_miner.carnallite.min", 1);
        }

        @Override
        public int getMaxDeviation() {
            int max = MoreOres.getInstance().getConfig().getInt("geo_miner.carnallite.max", 3);
            int min = MoreOres.getInstance().getConfig().getInt("geo_miner.carnallite.min", 1);
            return max - min;
        }
    }

    static class PyromorphiteResource extends MoreOresGeoResource {
        private final NamespacedKey key = new NamespacedKey(MoreOres.getInstance(), "pyromorphite");

        @Override
        public NamespacedKey getKey() {
            return key;
        }

        @Override
        @Nonnull
        public String getName() {
            return "磷氯铅矿";
        }

        @Override
        @Nonnull
        public ItemStack getItem() {
            return MoreOresItems.PYROMORPHITE.clone();
        }

        @Override
        public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
            if (environment != World.Environment.NORMAL) return 0;
            if (OCEAN_BIOMES.contains(biome) || RIVER_BIOMES.contains(biome)) {
                return MoreOres.getInstance().getConfig().getInt("geo_miner.pyromorphite.min", 1);
            }
            return 0;
        }

        @Override
        public int getMaxDeviation() {
            int max = MoreOres.getInstance().getConfig().getInt("geo_miner.pyromorphite.max", 5);
            int min = MoreOres.getInstance().getConfig().getInt("geo_miner.pyromorphite.min", 1);
            return max - min;
        }
    }

    static class MimetiteResource extends MoreOresGeoResource {
        private final NamespacedKey key = new NamespacedKey(MoreOres.getInstance(), "mimetite");

        @Override
        public NamespacedKey getKey() {
            return key;
        }

        @Override
        @Nonnull
        public String getName() {
            return "砷铅矿";
        }

        @Override
        @Nonnull
        public ItemStack getItem() {
            return MoreOresItems.MIMETITE.clone();
        }

        @Override
        public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
            if (environment != World.Environment.NETHER) return 0;
            return MoreOres.getInstance().getConfig().getInt("geo_miner.mimetite.min", 1);
        }

        @Override
        public int getMaxDeviation() {
            int max = MoreOres.getInstance().getConfig().getInt("geo_miner.mimetite.max", 5);
            int min = MoreOres.getInstance().getConfig().getInt("geo_miner.mimetite.min", 1);
            return max - min;
        }
    }

    static class BournoniteResource extends MoreOresGeoResource {
        private final NamespacedKey key = new NamespacedKey(MoreOres.getInstance(), "bournonite");

        @Override
        public NamespacedKey getKey() {
            return key;
        }

        @Override
        @Nonnull
        public String getName() {
            return "车轮矿";
        }

        @Override
        @Nonnull
        public ItemStack getItem() {
            return MoreOresItems.BOURNONITE.clone();
        }

        @Override
        public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
            if (environment != World.Environment.NORMAL) return 0;
            if (SNOWY_BIOMES.contains(biome)) {
                return MoreOres.getInstance().getConfig().getInt("geo_miner.bournonite.min", 1);
            }
            return 0;
        }

        @Override
        public int getMaxDeviation() {
            int max = MoreOres.getInstance().getConfig().getInt("geo_miner.bournonite.max", 5);
            int min = MoreOres.getInstance().getConfig().getInt("geo_miner.bournonite.min", 1);
            return max - min;
        }
    }

    static class FranckeiteResource extends MoreOresGeoResource {
        private final NamespacedKey key = new NamespacedKey(MoreOres.getInstance(), "franckeite");

        @Override
        public NamespacedKey getKey() {
            return key;
        }

        @Override
        @Nonnull
        public String getName() {
            return "辉锑锡铅矿";
        }

        @Override
        @Nonnull
        public ItemStack getItem() {
            return MoreOresItems.FRANCKEITE.clone();
        }

        @Override
        public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
            if (environment != World.Environment.NORMAL) return 0;
            if (DESERT_BADLANDS_BIOMES.contains(biome)) {
                return MoreOres.getInstance().getConfig().getInt("geo_miner.franckeite.min", 1);
            }
            return 0;
        }

        @Override
        public int getMaxDeviation() {
            int max = MoreOres.getInstance().getConfig().getInt("geo_miner.franckeite.max", 3);
            int min = MoreOres.getInstance().getConfig().getInt("geo_miner.franckeite.min", 1);
            return max - min;
        }
    }

    static class CylindriteResource extends MoreOresGeoResource {
        private final NamespacedKey key = new NamespacedKey(MoreOres.getInstance(), "cylindrite");

        @Override
        public NamespacedKey getKey() {
            return key;
        }

        @Override
        @Nonnull
        public String getName() {
            return "圆柱锡矿";
        }

        @Override
        @Nonnull
        public ItemStack getItem() {
            return MoreOresItems.CYLINDRITE.clone();
        }

        @Override
        public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
            if (environment != World.Environment.NORMAL) return 0;
            if (SAVANNA_BIOMES.contains(biome)) {
                return MoreOres.getInstance().getConfig().getInt("geo_miner.cylindrite.min", 1);
            }
            return 0;
        }

        @Override
        public int getMaxDeviation() {
            int max = MoreOres.getInstance().getConfig().getInt("geo_miner.cylindrite.max", 3);
            int min = MoreOres.getInstance().getConfig().getInt("geo_miner.cylindrite.min", 1);
            return max - min;
        }
    }

    static class MalayaiteResource extends MoreOresGeoResource {
        private final NamespacedKey key = new NamespacedKey(MoreOres.getInstance(), "malayaite");

        @Override
        public NamespacedKey getKey() {
            return key;
        }

        @Override
        @Nonnull
        public String getName() {
            return "马来亚石";
        }

        @Override
        @Nonnull
        public ItemStack getItem() {
            return MoreOresItems.MALAYAITE.clone();
        }

        @Override
        public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
            if (environment != World.Environment.NORMAL) return 0;
            if (PLAINS_BIOMES.contains(biome)) {
                return MoreOres.getInstance().getConfig().getInt("geo_miner.malayaite.min", 1);
            }
            return 0;
        }

        @Override
        public int getMaxDeviation() {
            int max = MoreOres.getInstance().getConfig().getInt("geo_miner.malayaite.max", 3);
            int min = MoreOres.getInstance().getConfig().getInt("geo_miner.malayaite.min", 1);
            return max - min;
        }
    }

    static class VarlamoffiteResource extends MoreOresGeoResource {
        private final NamespacedKey key = new NamespacedKey(MoreOres.getInstance(), "varlamoffite");

        @Override
        public NamespacedKey getKey() {
            return key;
        }

        @Override
        @Nonnull
        public String getName() {
            return "水锡石";
        }

        @Override
        @Nonnull
        public ItemStack getItem() {
            return MoreOresItems.VARLAMOFFITE.clone();
        }

        @Override
        public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
            if (environment != World.Environment.THE_END) return 0;
            return MoreOres.getInstance().getConfig().getInt("geo_miner.varlamoffite.min", 1);
        }

        @Override
        public int getMaxDeviation() {
            int max = MoreOres.getInstance().getConfig().getInt("geo_miner.varlamoffite.max", 3);
            int min = MoreOres.getInstance().getConfig().getInt("geo_miner.varlamoffite.min", 1);
            return max - min;
        }
    }

    static class ArgentiteResource extends MoreOresGeoResource {
        private final NamespacedKey key = new NamespacedKey(MoreOres.getInstance(), "argentite");

        @Override
        public NamespacedKey getKey() {
            return key;
        }

        @Override
        @Nonnull
        public String getName() {
            return "辉银矿";
        }

        @Override
        @Nonnull
        public ItemStack getItem() {
            return MoreOresItems.ARGENTITE.clone();
        }

        @Override
        public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
            if (environment != World.Environment.NETHER) return 0;
            return MoreOres.getInstance().getConfig().getInt("geo_miner.argentite.min", 1);
        }

        @Override
        public int getMaxDeviation() {
            int max = MoreOres.getInstance().getConfig().getInt("geo_miner.argentite.max", 3);
            int min = MoreOres.getInstance().getConfig().getInt("geo_miner.argentite.min", 1);
            return max - min;
        }
    }

    static class CerargyriteResource extends MoreOresGeoResource {
        private final NamespacedKey key = new NamespacedKey(MoreOres.getInstance(), "cerargyrite");

        @Override
        public NamespacedKey getKey() {
            return key;
        }

        @Override
        @Nonnull
        public String getName() {
            return "角银矿";
        }

        @Override
        @Nonnull
        public ItemStack getItem() {
            return MoreOresItems.CERARGYRITE.clone();
        }

        @Override
        public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
            if (environment != World.Environment.NORMAL) return 0;
            if (DESERT_BADLANDS_BIOMES.contains(biome)) {
                return MoreOres.getInstance().getConfig().getInt("geo_miner.cerargyrite.min", 1);
            }
            return 0;
        }

        @Override
        public int getMaxDeviation() {
            int max = MoreOres.getInstance().getConfig().getInt("geo_miner.cerargyrite.max", 3);
            int min = MoreOres.getInstance().getConfig().getInt("geo_miner.cerargyrite.min", 1);
            return max - min;
        }
    }

    static class NaumanniteResource extends MoreOresGeoResource {
        private final NamespacedKey key = new NamespacedKey(MoreOres.getInstance(), "naumannite");

        @Override
        public NamespacedKey getKey() {
            return key;
        }

        @Override
        @Nonnull
        public String getName() {
            return "硒银矿";
        }

        @Override
        @Nonnull
        public ItemStack getItem() {
            return MoreOresItems.NAUMANNITE.clone();
        }

        @Override
        public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
            if (environment != World.Environment.NETHER) return 0;
            if (BASALT_DELTAS_BIOME.contains(biome)) {
                return MoreOres.getInstance().getConfig().getInt("geo_miner.naumannite.min", 1);
            }
            return 0;
        }

        @Override
        public int getMaxDeviation() {
            int max = MoreOres.getInstance().getConfig().getInt("geo_miner.naumannite.max", 3);
            int min = MoreOres.getInstance().getConfig().getInt("geo_miner.naumannite.min", 1);
            return max - min;
        }
    }
}
