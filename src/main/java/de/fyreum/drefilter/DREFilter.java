package de.fyreum.drefilter;

import de.fyreum.drefilter.items.FilterItems;
import de.fyreum.drefilter.listener.Filter;
import de.fyreum.drefilter.commands.CommandManager;
import de.fyreum.drefilter.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class DREFilter extends JavaPlugin {

    private static DREFilter plugin;
    private ConfigManager manager;
    private FilterItems filterItems;

    @Override
    public void onEnable() {
        plugin = this;
        // registers the Listener.
        Bukkit.getPluginManager().registerEvents(new Filter(plugin), this);
        // calls the load() function.
        load();
        // command executor:
        getCommand("drefilter").setExecutor(new CommandManager());
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
        filterItems = new FilterItems();
        filterItems.setup();
    }

    public ConfigManager getConfigManager() {
        return manager;
    }

    public FilterItems getFilterItems() {
        return filterItems;
    }

}
