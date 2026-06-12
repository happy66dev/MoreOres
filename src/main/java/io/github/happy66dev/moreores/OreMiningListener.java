package io.github.happy66dev.moreores;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class OreMiningListener implements Listener {

    private final MoreOres plugin;

    private static final Set<Material> IRON_ORE_TARGETS = Set.of(Material.IRON_ORE, Material.COPPER_ORE);
    private static final Set<Material> BAUXITE_TARGETS = Set.of(Material.CLAY, Material.IRON_ORE, Material.COAL_ORE, Material.REDSTONE_ORE);
    private static final Set<Material> CORUNDUM_TARGETS = Set.of(Material.DEEPSLATE_EMERALD_ORE);
    private static final Set<Material> SPHALERITE_TARGETS = Set.of(Material.COPPER_ORE);
    private static final Set<Material> ZINC_ORE_TARGETS = Set.of(Material.IRON_ORE);
    private static final Set<Material> RED_ZINCITE_TARGETS = Set.of(Material.REDSTONE_ORE);

    private static final Set<Material> COPPER_ORE_TARGETS = Set.of(Material.COPPER_ORE, Material.DEEPSLATE_COPPER_ORE);
    private static final Set<Material> DEEPSLATE_IRON_TARGETS = Set.of(Material.DEEPSLATE_IRON_ORE);
    private static final Set<Material> LAPIS_ORE_TARGETS = Set.of(Material.LAPIS_ORE, Material.DEEPSLATE_LAPIS_ORE);
    private static final Set<Material> EMERALD_ORE_TARGETS = Set.of(Material.EMERALD_ORE, Material.DEEPSLATE_EMERALD_ORE);
    private static final Set<Material> DEEPSLATE_REDSTONE_TARGETS = Set.of(Material.DEEPSLATE_REDSTONE_ORE);
    private static final Set<Material> DIAMOND_ORE_TARGETS = Set.of(Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE);
    private static final Set<Material> STONE_TARGETS = Set.of(Material.STONE);

    private static final Set<Material> GOLD_ORE_ONLY = Set.of(Material.GOLD_ORE);
    private static final Set<Material> GOLD_ORE_FULL = Set.of(Material.GOLD_ORE, Material.DEEPSLATE_GOLD_ORE);
    private static final Set<Material> NETHER_GOLD_ORE_TARGETS = Set.of(Material.NETHER_GOLD_ORE);
    private static final Set<Material> NETHER_QUARTZ_ORE_TARGETS = Set.of(Material.NETHER_QUARTZ_ORE);
    private static final Set<Material> DEEPSLATE_GOLD_ONLY = Set.of(Material.DEEPSLATE_GOLD_ORE);

    public OreMiningListener(MoreOres plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player == null) return;

        ItemStack tool = player.getInventory().getItemInMainHand();
        if (hasSilkTouch(tool)) return;

        Material broken = event.getBlock().getType();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        rollAndDrop(event, random, broken, "ores.yellow_iron_ore.chance", 0.15, IRON_ORE_TARGETS, MoreOresItems.YELLOW_IRON_ORE);
        rollAndDrop(event, random, broken, "ores.bauxite.chance", 0.10, BAUXITE_TARGETS, MoreOresItems.BAUXITE);
        rollAndDrop(event, random, broken, "ores.blue_corundum.chance", 0.0001, CORUNDUM_TARGETS, MoreOresItems.BLUE_CORUNDUM);
        rollAndDrop(event, random, broken, "ores.red_corundum.chance", 0.0001, CORUNDUM_TARGETS, MoreOresItems.RED_CORUNDUM);
        rollAndDrop(event, random, broken, "ores.sphalerite.chance", 0.10, SPHALERITE_TARGETS, MoreOresItems.SPHALERITE);
        rollAndDrop(event, random, broken, "ores.stacked_zinc_ore.chance", 0.20, ZINC_ORE_TARGETS, MoreOresItems.STACKED_ZINC_ORE);
        rollAndDrop(event, random, broken, "ores.smithsonite.chance", 0.10, ZINC_ORE_TARGETS, MoreOresItems.SMITHSONITE);
        rollAndDrop(event, random, broken, "ores.magnesium_zinc_ore.chance", 0.01, STONE_TARGETS, MoreOresItems.MAGNESIUM_ZINC_ORE);
        rollAndDrop(event, random, broken, "ores.red_zincite.chance", 0.10, RED_ZINCITE_TARGETS, MoreOresItems.RED_ZINCITE);

        rollAndDrop(event, random, broken, "ores.galena.chance", 0.08, COPPER_ORE_TARGETS, MoreOresItems.GALENA);
        rollAndDrop(event, random, broken, "ores.cerussite.chance", 0.04, COPPER_ORE_TARGETS, MoreOresItems.CERUSSITE);
        rollAndDrop(event, random, broken, "ores.anglesite.chance", 0.02, COPPER_ORE_TARGETS, MoreOresItems.ANGLESITE);
        rollAndDrop(event, random, broken, "ores.crocoite.chance", 0.03, DEEPSLATE_IRON_TARGETS, MoreOresItems.CROCOITE);
        rollAndDrop(event, random, broken, "ores.boulangerite.chance", 0.02, LAPIS_ORE_TARGETS, MoreOresItems.BOULANGERITE);
        rollAndDrop(event, random, broken, "ores.jamesonite.chance", 0.10, EMERALD_ORE_TARGETS, MoreOresItems.JAMESONITE);
        rollAndDrop(event, random, broken, "ores.vanadinite.chance", 0.05, DEEPSLATE_REDSTONE_TARGETS, MoreOresItems.VANADINITE);
        rollAndDrop(event, random, broken, "ores.wulfenite.chance", 0.05, DIAMOND_ORE_TARGETS, MoreOresItems.WULFENITE);

        rollAndDrop(event, random, broken, "ores.stannite.chance", 0.10, GOLD_ORE_FULL, MoreOresItems.STANNITE);
        rollAndDrop(event, random, broken, "ores.canfieldite.chance", 0.03, STONE_TARGETS, MoreOresItems.CANFIELDITE);
        rollAndDrop(event, random, broken, "ores.electrum.chance", 0.05, GOLD_ORE_ONLY, MoreOresItems.ELECTRUM);

        rollAndDrop(event, random, broken, "ores.pyrargyrite.chance", 0.10, NETHER_GOLD_ORE_TARGETS, MoreOresItems.PYRARGYRITE);
        rollAndDrop(event, random, broken, "ores.proustite.chance", 0.10, NETHER_QUARTZ_ORE_TARGETS, MoreOresItems.PROUSTITE);
        rollAndDrop(event, random, broken, "ores.stephanite.chance", 0.05, NETHER_QUARTZ_ORE_TARGETS, MoreOresItems.STEPHANITE);
        rollAndDrop(event, random, broken, "ores.dyscrasite.chance", 0.05, NETHER_GOLD_ORE_TARGETS, MoreOresItems.DYSKRASITE);
        rollAndDrop(event, random, broken, "ores.hessite.chance", 0.05, DEEPSLATE_GOLD_ONLY, MoreOresItems.HESSITE);
    }

    private void rollAndDrop(BlockBreakEvent event, ThreadLocalRandom random, Material broken,
                             String configKey, double defaultChance, Set<Material> targets, SlimefunItemStack ore) {
        if (!targets.contains(broken)) return;
        double chance = plugin.getConfig().getDouble(configKey, defaultChance);
        if (random.nextDouble() < chance) {
            dropOre(event, ore);
        }
    }

    private boolean hasSilkTouch(ItemStack tool) {
        if (tool == null || tool.getType().isAir()) return false;
        return tool.getEnchantments().containsKey(Enchantment.SILK_TOUCH);
    }

    private void dropOre(BlockBreakEvent event, SlimefunItemStack ore) {
        Block block = event.getBlock();
        block.getWorld().dropItemNaturally(block.getLocation().add(0.5, 0.5, 0.5), ore.clone());
    }
}
