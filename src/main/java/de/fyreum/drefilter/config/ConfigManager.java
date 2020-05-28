package de.fyreum.drefilter.config;

import de.fyreum.drefilter.DREFilter;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigManager {

    private boolean villagerDisabled;

    private HashMap<NamespacedKey, Integer> enchantmentValues = new HashMap<>();
    private ArrayList<NamespacedKey> disabledEnchants = new ArrayList<>();
    private List<String> affectedWorldList = new ArrayList<>();

    public void load() {
        DREFilter plugin = DREFilter.getInstance();
        villagerDisabled = plugin.getConfig().getBoolean("villagerDisabled");
        affectedWorldList = plugin.getConfig().getStringList("affectedWorlds");
        for (Enchantment enchantment : Enchantment.values()) {
            if (plugin.getConfig().get("enchantments." + enchantment.getName()) == null) {
                enchantmentValues.put(enchantment.getKey(), Integer.MAX_VALUE);
                continue;
            }
            if (plugin.getConfig().getInt("enchantments." + enchantment.getName()) <= 0) {
                disabledEnchants.add(enchantment.getKey());
                continue;
            }
            enchantmentValues.put(enchantment.getKey(), plugin.getConfig().getInt("enchantments." + enchantment.getName()));
        }
    }

    public void reload() {
        enchantmentValues.clear();
        disabledEnchants.clear();
        load();
    }

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
