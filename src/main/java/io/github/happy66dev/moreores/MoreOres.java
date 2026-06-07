package io.github.happy66dev.moreores;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class MoreOres extends JavaPlugin implements SlimefunAddon {

    private static MoreOres instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        MoreOresItems.registerAll(this);
        MoreOresResources.registerAll();

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
