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

    private MoreOresResources() {}

    public static void registerAll() {
        new BischofiteResource().register();
        new BrineResource().register();
        new CarnalliteResource().register();
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
}
