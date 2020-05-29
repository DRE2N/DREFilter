package de.fyreum.drefilter.filter;

import de.fyreum.drefilter.DREFilter;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EnchantmentFilter {

    public void patchItem(HumanEntity entity, ItemStack item) {
        if (item == null) {
            return;
        }
        DREFilter plugin = DREFilter.getInstance();
        // if the players current world isn't contained by the affectedWorldList the method stops.
        if (!plugin.getConfigManager().getAffectedWorldList().contains(entity.getWorld().getName())) {
            return;
        }
        // looking for the itemMeta and enchantsments.
        if (item.hasItemMeta() && item.getItemMeta().hasEnchants()) {
            // gets the enchantment values and disabled enchants.
            ArrayList<NamespacedKey> disabledEnchants = plugin.getConfigManager().getDisabledEnchants();
            HashMap<NamespacedKey, Integer> enchantmentValues = plugin.getConfigManager().getEnchantmentValues();
            // patches the item.
            for(Map.Entry<Enchantment, Integer> enchant : item.getEnchantments().entrySet()) {
                // removes the enchantment, if its on the disabled list.
                if (disabledEnchants.contains(enchant.getKey().getKey())) {
                    item.removeEnchantment(enchant.getKey());
                    continue;
                }
                // downgrades the enchant, if the level is higher then the maximum value.
                if (enchant.getValue() > enchantmentValues.get(enchant.getKey().getKey())) {
                    item.addEnchantment(enchant.getKey(), enchantmentValues.get(enchant.getKey().getKey()));
                }
            }
        }
    }
}
