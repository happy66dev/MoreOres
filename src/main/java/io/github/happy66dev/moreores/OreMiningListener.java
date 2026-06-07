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

    private static final Set<Material> YELLOW_IRON_ORE_TARGETS = Set.of(Material.IRON_ORE, Material.COPPER_ORE);
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

    private static final Set<Material> GOLD_ORE_TARGETS = Set.of(Material.GOLD_ORE, Material.DEEPSLATE_GOLD_ORE);
    private static final Set<Material> STONE_TARGETS = Set.of(Material.STONE);

    private static final Set<Material> GOLD_ORE_ONLY = Set.of(Material.GOLD_ORE);
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
        double chance;

        chance = plugin.getConfig().getDouble("ores.yellow_iron_ore.chance", 0.15);
        if (YELLOW_IRON_ORE_TARGETS.contains(broken) && random.nextDouble() < chance) {
            dropOre(event, MoreOresItems.YELLOW_IRON_ORE);
            return;
        }

        chance = plugin.getConfig().getDouble("ores.bauxite.chance", 0.10);
        if (BAUXITE_TARGETS.contains(broken) && random.nextDouble() < chance) {
            dropOre(event, MoreOresItems.BAUXITE);
            return;
        }

        chance = plugin.getConfig().getDouble("ores.blue_corundum.chance", 0.0001);
        if (CORUNDUM_TARGETS.contains(broken) && random.nextDouble() < chance) {
            dropOre(event, MoreOresItems.BLUE_CORUNDUM);
            return;
        }

        chance = plugin.getConfig().getDouble("ores.red_corundum.chance", 0.0001);
        if (CORUNDUM_TARGETS.contains(broken) && random.nextDouble() < chance) {
            dropOre(event, MoreOresItems.RED_CORUNDUM);
            return;
        }

        chance = plugin.getConfig().getDouble("ores.sphalerite.chance", 0.10);
        if (SPHALERITE_TARGETS.contains(broken) && random.nextDouble() < chance) {
            dropOre(event, MoreOresItems.SPHALERITE);
            return;
        }

        chance = plugin.getConfig().getDouble("ores.stacked_zinc_ore.chance", 0.20);
        if (ZINC_ORE_TARGETS.contains(broken) && random.nextDouble() < chance) {
            dropOre(event, MoreOresItems.STACKED_ZINC_ORE);
            return;
        }

        chance = plugin.getConfig().getDouble("ores.smithsonite.chance", 0.10);
        if (ZINC_ORE_TARGETS.contains(broken) && random.nextDouble() < chance) {
            dropOre(event, MoreOresItems.SMITHSONITE);
            return;
        }

        chance = plugin.getConfig().getDouble("ores.magnesium_zinc_ore.chance", 0.01);
        if (STONE_TARGETS.contains(broken) && random.nextDouble() < chance) {
            dropOre(event, MoreOresItems.MAGNESIUM_ZINC_ORE);
            return;
        }

        chance = plugin.getConfig().getDouble("ores.red_zincite.chance", 0.10);
        if (RED_ZINCITE_TARGETS.contains(broken) && random.nextDouble() < chance) {
            dropOre(event, MoreOresItems.RED_ZINCITE);
            return;
        }

        chance = plugin.getConfig().getDouble("ores.galena.chance", 0.08);
        if (COPPER_ORE_TARGETS.contains(broken) && random.nextDouble() < chance) {
            dropOre(event, MoreOresItems.GALENA);
            return;
        }

        chance = plugin.getConfig().getDouble("ores.cerussite.chance", 0.04);
        if (COPPER_ORE_TARGETS.contains(broken) && random.nextDouble() < chance) {
            dropOre(event, MoreOresItems.CERUSSITE);
            return;
        }

        chance = plugin.getConfig().getDouble("ores.anglesite.chance", 0.02);
        if (COPPER_ORE_TARGETS.contains(broken) && random.nextDouble() < chance) {
            dropOre(event, MoreOresItems.ANGLESITE);
            return;
        }

        chance = plugin.getConfig().getDouble("ores.crocoite.chance", 0.03);
        if (DEEPSLATE_IRON_TARGETS.contains(broken) && random.nextDouble() < chance) {
            dropOre(event, MoreOresItems.CROCOITE);
            return;
        }

        chance = plugin.getConfig().getDouble("ores.boulangerite.chance", 0.02);
        if (LAPIS_ORE_TARGETS.contains(broken) && random.nextDouble() < chance) {
            dropOre(event, MoreOresItems.BOULANGERITE);
            return;
        }

        chance = plugin.getConfig().getDouble("ores.jamesonite.chance", 0.10);
        if (EMERALD_ORE_TARGETS.contains(broken) && random.nextDouble() < chance) {
            dropOre(event, MoreOresItems.JAMESONITE);
            return;
        }

        chance = plugin.getConfig().getDouble("ores.vanadinite.chance", 0.05);
        if (DEEPSLATE_REDSTONE_TARGETS.contains(broken) && random.nextDouble() < chance) {
            dropOre(event, MoreOresItems.VANADINITE);
            return;
        }

        chance = plugin.getConfig().getDouble("ores.wulfenite.chance", 0.05);
        if (DIAMOND_ORE_TARGETS.contains(broken) && random.nextDouble() < chance) {
            dropOre(event, MoreOresItems.WULFENITE);
            return;
        }

        chance = plugin.getConfig().getDouble("ores.stannite.chance", 0.10);
        if (GOLD_ORE_TARGETS.contains(broken) && random.nextDouble() < chance) {
            dropOre(event, MoreOresItems.STANNITE);
            return;
        }

        chance = plugin.getConfig().getDouble("ores.canfieldite.chance", 0.03);
        if (STONE_TARGETS.contains(broken) && random.nextDouble() < chance) {
            dropOre(event, MoreOresItems.CANFIELDITE);
            return;
        }

        chance = plugin.getConfig().getDouble("ores.electrum.chance", 0.05);
        if (GOLD_ORE_ONLY.contains(broken) && random.nextDouble() < chance) {
            dropOre(event, MoreOresItems.ELECTRUM);
            return;
        }

        chance = plugin.getConfig().getDouble("ores.pyrargyrite.chance", 0.10);
        if (NETHER_GOLD_ORE_TARGETS.contains(broken) && random.nextDouble() < chance) {
            dropOre(event, MoreOresItems.PYRARGYRITE);
            return;
        }

        chance = plugin.getConfig().getDouble("ores.proustite.chance", 0.10);
        if (NETHER_QUARTZ_ORE_TARGETS.contains(broken) && random.nextDouble() < chance) {
            dropOre(event, MoreOresItems.PROUSTITE);
            return;
        }

        chance = plugin.getConfig().getDouble("ores.stephanite.chance", 0.05);
        if (NETHER_QUARTZ_ORE_TARGETS.contains(broken) && random.nextDouble() < chance) {
            dropOre(event, MoreOresItems.STEPHANITE);
            return;
        }

        chance = plugin.getConfig().getDouble("ores.dyscrasite.chance", 0.05);
        if (NETHER_GOLD_ORE_TARGETS.contains(broken) && random.nextDouble() < chance) {
            dropOre(event, MoreOresItems.DYSKRASITE);
            return;
        }

        chance = plugin.getConfig().getDouble("ores.hessite.chance", 0.05);
        if (DEEPSLATE_GOLD_ONLY.contains(broken) && random.nextDouble() < chance) {
            dropOre(event, MoreOresItems.HESSITE);
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
