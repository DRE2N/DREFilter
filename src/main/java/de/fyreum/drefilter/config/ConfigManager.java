package de.fyreum.drefilter.config;

import de.fyreum.drefilter.DREFilter;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.HashMap;

public class ConfigManager {

    private String enchantmentPath = "enchantments.";
    private boolean villagerDisabled;

    private HashMap<NamespacedKey, Integer> enchantmentValues = new HashMap<>();
    private ArrayList<NamespacedKey> disabledEnchants = new ArrayList<>();

    public void load() {
        DREFilter plugin = DREFilter.getInstance();
        villagerDisabled = plugin.getConfig().getBoolean("villagerDisabled");
        for (Enchantment enchantment : Enchantment.values()) {
            if (plugin.getConfig().get(enchantmentPath + enchantment.getName()) == null) {
                enchantmentValues.put(enchantment.getKey(), Integer.MAX_VALUE);
                continue;
            }
            if (plugin.getConfig().getInt(enchantmentPath + enchantment.getName()) <= 0) {
                disabledEnchants.add(enchantment.getKey());
                continue;
            }
            enchantmentValues.put(enchantment.getKey(), plugin.getConfig().getInt(enchantmentPath + enchantment.getName()));
        }
    }

    public void reload() {
        enchantmentValues.clear();
        disabledEnchants.clear();
        load();
    }

    public String getEnchantmentPath() {
        return enchantmentPath;
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
