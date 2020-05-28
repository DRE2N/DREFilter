package de.fyreum.drefilter;

import de.fyreum.drefilter.Listener.Filter;
import de.fyreum.drefilter.commands.ReloadCommand;
import de.fyreum.drefilter.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class DREFilter extends JavaPlugin {

    private static DREFilter plugin;
    private ConfigManager manager;

    @Override
    public void onEnable() {
        plugin = this;
        Bukkit.getPluginManager().registerEvents(new Filter(), this);
        load();
        getCommand("drefilter").setExecutor(new ReloadCommand());
        System.out.println("[DREFilter] wurde geladen.");
    }

    @Override
    public void onDisable() {
    }

    public static DREFilter getInstance() {
        return plugin;
    }

    private void load() {
        saveDefaultConfig();
        manager = new ConfigManager();
        manager.load();
    }

    public ConfigManager getConfigManager() {
        return manager;
    }
}
