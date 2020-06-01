package de.fyreum.drefilter.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.bukkit.ChatColor.*;

public class FilterItems {

    private HashMap<Material, ItemStack> filteredMaterials = new HashMap<>();
    private List<String> loreList = Arrays.asList(GRAY + "Schuppenr\u00fcstung", GRAY + "Plattenr\u00fcstung", GRAY + "Dolch", GRAY + "Katana", GRAY + "Langschwert",
            GRAY + "Ritterschwert", GRAY + "Rapier", GRAY + "");

    public void setup() {
        filteredMaterials.put(Material.DIAMOND_SWORD, new ItemBuilder(Material.IRON_SWORD).addLore(ChatColor.GRAY + "Geringer Spielerschaden")
                .create());
        filteredMaterials.put(Material.IRON_SWORD, new ItemBuilder(Material.IRON_SWORD).addLore(ChatColor.GRAY + "Geringer Spielerschaden")
                .create());
        filteredMaterials.put(Material.NETHER_STAR, null);
        filteredMaterials.put(Material.DIAMOND_HELMET, new ItemBuilder(Material.IRON_HELMET).setDisplayName(ChatColor.WHITE + "Schuppenr\u00fcstung")
                .setLore(Arrays.asList(ChatColor.GRAY + "Schuppenr\u00fcstung",
                        ChatColor.GREEN + "Qualit\u00e4t: " + ChatColor.GOLD + "\u2605\u2605\u2605\u2605",
                        ChatColor.GREEN + "Schmied: " + ChatColor.GOLD + "Schmiedemeister",
                        ChatColor.GREEN + "Herkunft: " + ChatColor.GOLD + "Gladeia"))
                .create());
        filteredMaterials.put(Material.DIAMOND_CHESTPLATE, new ItemBuilder(Material.IRON_CHESTPLATE).setDisplayName(ChatColor.WHITE + "Schuppenrüstung")
                .setLore(Arrays.asList(ChatColor.GRAY + "Schuppenr\u00fcstung",
                        ChatColor.GREEN + "Qualit\u00e4t: " + ChatColor.GOLD + "\u2605\u2605\u2605\u2605",
                        ChatColor.GREEN + "Schmied: " + ChatColor.GOLD + "Schmiedemeister",
                        ChatColor.GREEN + "Herkunft: " + ChatColor.GOLD + "Gladeia"))
                .create());
        filteredMaterials.put(Material.DIAMOND_LEGGINGS, new ItemBuilder(Material.IRON_LEGGINGS).setDisplayName(ChatColor.WHITE + "Schuppenrüstung")
                .setLore(Arrays.asList(ChatColor.GRAY + "Schuppenr\u00fcstung",
                        ChatColor.GREEN + "Qualit\u00e4t: " + ChatColor.GOLD + "\u2605\u2605\u2605\u2605",
                        ChatColor.GREEN + "Schmied: " + ChatColor.GOLD + "Schmiedemeister",
                        ChatColor.GREEN + "Herkunft: " + ChatColor.GOLD + "Gladeia"))
                .create());
        filteredMaterials.put(Material.DIAMOND_BOOTS, new ItemBuilder(Material.IRON_BOOTS).setDisplayName(ChatColor.WHITE + "Schuppenrüstung")
                .setLore(Arrays.asList(ChatColor.GRAY + "Schuppenr\u00fcstung",
                        ChatColor.GREEN + "Qualit\u00e4t: " + ChatColor.GOLD + "\u2605\u2605\u2605\u2605",
                        ChatColor.GREEN + "Schmied: " + ChatColor.GOLD + "Schmiedemeister",
                        ChatColor.GREEN + "Herkunft: " + ChatColor.GOLD + "Gladeia"))
                .create());
        filteredMaterials.put(Material.IRON_HELMET, new ItemBuilder(Material.IRON_HELMET).setDisplayName(ChatColor.WHITE + "Schuppenrüstung")
                .setLore(Arrays.asList(ChatColor.GRAY + "Schuppenr\u00fcstung",
                        ChatColor.GREEN + "Qualit\u00e4t: " + ChatColor.GOLD + "\u2605\u2605\u2605\u2605",
                        ChatColor.GREEN + "Schmied: " + ChatColor.GOLD + "Schmiedemeister",
                        ChatColor.GREEN + "Herkunft: " + ChatColor.GOLD + "Gladeia"))
                .create());
        filteredMaterials.put(Material.IRON_CHESTPLATE, new ItemBuilder(Material.IRON_CHESTPLATE).setDisplayName(ChatColor.WHITE + "Schuppenrüstung")
                .setLore(Arrays.asList(ChatColor.GRAY + "Schuppenr\u00fcstung",
                        ChatColor.GREEN + "Qualit\u00e4t: " + ChatColor.GOLD + "\u2605\u2605\u2605\u2605",
                        ChatColor.GREEN + "Schmied: " + ChatColor.GOLD + "Schmiedemeister",
                        ChatColor.GREEN + "Herkunft: " + ChatColor.GOLD + "Gladeia"))
                .create());
        filteredMaterials.put(Material.IRON_LEGGINGS, new ItemBuilder(Material.IRON_LEGGINGS).setDisplayName(ChatColor.WHITE + "Schuppenrüstung")
                .setLore(Arrays.asList(ChatColor.GRAY + "Schuppenr\u00fcstung",
                        ChatColor.GREEN + "Qualit\u00e4t: " + ChatColor.GOLD + "\u2605\u2605\u2605\u2605",
                        ChatColor.GREEN + "Schmied: " + ChatColor.GOLD + "Schmiedemeister",
                        ChatColor.GREEN + "Herkunft: " + ChatColor.GOLD + "Gladeia"))
                .create());
        filteredMaterials.put(Material.IRON_BOOTS, new ItemBuilder(Material.IRON_BOOTS).setDisplayName(ChatColor.WHITE + "Schuppenrüstung")
                .setLore(Arrays.asList(ChatColor.GRAY + "Schuppenr\u00fcstung",
                        ChatColor.GREEN + "Qualit\u00e4t: " + ChatColor.GOLD + "\u2605\u2605\u2605\u2605",
                        ChatColor.GREEN + "Schmied: " + ChatColor.GOLD + "Schmiedemeister",
                        ChatColor.GREEN + "Herkunft: " + ChatColor.GOLD + "Gladeia"))
                .create());
    }

    public HashMap<Material, ItemStack> getFilteredMaterials() {
        return filteredMaterials;
    }

    public List<String> getLoreList() {
        return loreList;
    }
}
