package de.fyreum.drefilter;

import de.fyreum.drefilter.listener.Events;
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
        // registers the Listener.
        Bukkit.getPluginManager().registerEvents(new Events(), this);
        // calls the load() function.
        load();
        // command executor:
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
        // saved the default config and calls the lod() method of the ConfigManager.
        saveDefaultConfig();
        manager = new ConfigManager();
        manager.load();
    }

    public ConfigManager getConfigManager() {
        return manager;
    }
}
