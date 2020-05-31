package de.fyreum.drefilter.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class FilterItems {

    private HashMap<Material, ItemStack> filteredMaterials = new HashMap<>();

    public void setup() {
        filteredMaterials.put(Material.DIAMOND_SWORD, new ItemBuilder(Material.IRON_SWORD).addLore(ChatColor.DARK_PURPLE + "Der Schaden wurde entfernt")
                .removeDamageHidden().create());
        filteredMaterials.put(Material.NETHER_STAR, null);
    }

    public HashMap<Material, ItemStack> getFilteredMaterials() {
        return filteredMaterials;
    }
}
