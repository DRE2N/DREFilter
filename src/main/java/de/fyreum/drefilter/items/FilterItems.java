package de.fyreum.drefilter.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class FilterItems {

    private HashMap<Integer, Material> filteredMaterials = new HashMap<>();
    private HashMap<Integer, ItemStack> replaceItems = new HashMap<>();

    public void setup() {
        filteredMaterials.put(0, Material.DIAMOND_SWORD);
        replaceItems.put(0, new ItemBuilder(Material.IRON_SWORD).addLore(ChatColor.DARK_PURPLE + "Der Schaden wurde entfernt").removeDamage().create());

        filteredMaterials.put(1, Material.NETHER_STAR);
        replaceItems.put(1, null);
    }

    public HashMap<Integer,Material> getFilteredMaterials() {
        return filteredMaterials;
    }

    public HashMap<Integer, ItemStack> getReplaceItems() {
        return replaceItems;
    }
}
