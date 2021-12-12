package de.fyreum.drefilter;

import de.fyreum.drefilter.items.FilterItems;
import de.fyreum.drefilter.listener.Filter;
import de.fyreum.drefilter.commands.CommandManager;
import de.fyreum.drefilter.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class DREFilter extends JavaPlugin {

    private static DREFilter plugin;
    private ConfigManager manager;
    private FilterItems filterItems;
    private final AttributeModifier noDamageModifier = new AttributeModifier(UUID.fromString("cc7bfff8-4d39-11eb-ae93-0242ac130002"),
            "noDamage", -1, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlot.HAND);

    @Override
    public void onEnable() {
        plugin = this;
        // registers the Listener.
        Bukkit.getPluginManager().registerEvents(new Filter(plugin), this);
        // calls the load() function.
        load();
        // command executor:
        CommandManager commandManager = new CommandManager();
        getCommand("drefilter").setExecutor(commandManager);
        getCommand("drefilter").setTabCompleter(commandManager);
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

    public AttributeModifier getNoDamageModifier() {
        return noDamageModifier;
    }
}
