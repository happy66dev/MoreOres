package io.github.happy66dev.moreores;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.api.researches.Research;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import org.bstats.bukkit.Metrics;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class MoreOres extends JavaPlugin implements SlimefunAddon {

    private static MoreOres instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        MoreOresItems.registerAll(this);
        MoreOresResources.registerAll();

        HeatedReactionChamber machine = new HeatedReactionChamber(
                MoreOresItems.MORE_ORES,
                MoreOresItems.HEATED_REACTION_CHAMBER_ITEM,
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[]{
                        new ItemStack(Material.GLASS),  SlimefunItems.HEATING_COIL,     new ItemStack(Material.GLASS),
                        SlimefunItems.HEATING_COIL,     SlimefunItems.ELECTRIC_FURNACE, SlimefunItems.HEATING_COIL,
                        new ItemStack(Material.CHEST),  SlimefunItems.STEEL_PLATE,      new ItemStack(Material.CHEST)
                });
        machine.setCapacity(256).setEnergyConsumption(15).setProcessingSpeed(1);
        machine.registerRecipes();
        machine.register(this);
        new Research(new NamespacedKey(this, "heated_reaction_chamber"), 9950, "普通加热反应室", 5)
                .addItems(MoreOresItems.HEATED_REACTION_CHAMBER_ITEM).register();

        getServer().getPluginManager().registerEvents(new OreMiningListener(this), this);

        new Metrics(this, 24992);

        getLogger().info("MoreOres v" + getDescription().getVersion() + " 已加载");
    }

    @Override
    public void onDisable() {
        getLogger().info("MoreOres 已卸载");
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/happy66dev/MoreOres/issues";
    }

    public static MoreOres getInstance() {
        return instance;
    }
}
