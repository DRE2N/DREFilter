package de.fyreum.drefilter.config;

import de.fyreum.drefilter.DREFilter;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigManager {

    private boolean villagerDisabled;

    private final HashMap<NamespacedKey, Integer> enchantmentValues = new HashMap<>();
    private final ArrayList<NamespacedKey> disabledEnchants = new ArrayList<>();
    private List<String> affectedWorldList = new ArrayList<>();

    // loads all data out of the config.
    public void load() {
        DREFilter plugin = DREFilter.getInstance();
        FileConfiguration config = plugin.getConfig();
        villagerDisabled = config.getBoolean("villagerDisabled");
        affectedWorldList = config.getStringList("affectedWorlds");
        // gets the value for each enchantment that exist out of the config.
        for (Enchantment enchantment : Enchantment.values()) {
            if (config.get("enchantments." + enchantment.getName()) == null) {
                // sets the maximum level of the given enchantment to the maximum Integer value.
                enchantmentValues.put(enchantment.getKey(), Integer.MAX_VALUE);
                continue;
            }
            if (config.getInt("enchantments." + enchantment.getName()) <= 0) {
                // adds the enchant to the list of enchantments that will be removed.
                disabledEnchants.add(enchantment.getKey());
                continue;
            }
            // sets the maximum level of the given enchantment to the loaded level.
            enchantmentValues.put(enchantment.getKey(), config.getInt("enchantments." + enchantment.getName()));
        }
    }

    public void reload() {
        // clears all the loaded data.
        enchantmentValues.clear();
        disabledEnchants.clear();
        affectedWorldList.clear();
        // loads the config data again.
        load();
    }

    // getter

    public List<String> getAffectedWorldList() {
        return affectedWorldList;
    }

    public HashMap<NamespacedKey, Integer> getEnchantmentValues() {
        return enchantmentValues;
    }

    public ArrayList<NamespacedKey> getDisabledEnchants() {
        return disabledEnchants;
    }

    public boolean isVillagerDisabled() {
        return villagerDisabled;
    }
}
