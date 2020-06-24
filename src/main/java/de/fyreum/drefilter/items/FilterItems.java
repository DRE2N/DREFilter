package de.fyreum.drefilter.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.bukkit.ChatColor.*;

public class FilterItems {

    private final HashMap<Material, ItemStack> filteredMaterials = new HashMap<>();
    private final List<String> loreList = Arrays.asList(GRAY + "Schuppenr\u00fcstung", GRAY + "Plattenr\u00fcstung", GRAY + "Dolch", GRAY + "Katana", GRAY + "Langschwert",
            GRAY + "Ritterschwert", GRAY + "Rapier", GRAY + "Geringer Spielerschaden");

    public void setup() {
        filteredMaterials.put(Material.DIAMOND_SWORD, new ItemBuilder(Material.IRON_SWORD).addLore(GRAY + "Geringer Spielerschaden")
                .create());
        filteredMaterials.put(Material.IRON_SWORD, new ItemBuilder(Material.IRON_SWORD).addLore(GRAY + "Geringer Spielerschaden")
                .create());
        filteredMaterials.put(Material.NETHER_STAR, null);
        filteredMaterials.put(Material.DIAMOND_HELMET, new ItemBuilder(Material.IRON_HELMET).setDisplayName(WHITE + "Schuppenr\u00fcstung")
                .setLore(Arrays.asList(GRAY + "Schuppenr\u00fcstung",
                        GREEN + "Qualit\u00e4t: " + GOLD + "\u2605\u2605\u2605\u2605",
                        GREEN + "Schmied: " + GOLD + "Schmiedemeister",
                        GREEN + "Herkunft: " + GOLD + "Gladeia"))
                .create());
        filteredMaterials.put(Material.DIAMOND_CHESTPLATE, new ItemBuilder(Material.IRON_CHESTPLATE).setDisplayName(WHITE + "Schuppenr\u00fcstung")
                .setLore(Arrays.asList(GRAY + "Schuppenr\u00fcstung",
                        GREEN + "Qualit\u00e4t: " + GOLD + "\u2605\u2605\u2605\u2605",
                        GREEN + "Schmied: " + GOLD + "Schmiedemeister",
                        GREEN + "Herkunft: " + GOLD + "Gladeia"))
                .create());
        filteredMaterials.put(Material.DIAMOND_LEGGINGS, new ItemBuilder(Material.IRON_LEGGINGS).setDisplayName(WHITE + "Schuppenr\u00fcstung")
                .setLore(Arrays.asList(GRAY + "Schuppenr\u00fcstung",
                        GREEN + "Qualit\u00e4t: " + GOLD + "\u2605\u2605\u2605\u2605",
                        GREEN + "Schmied: " + GOLD + "Schmiedemeister",
                        GREEN + "Herkunft: " + GOLD + "Gladeia"))
                .create());
        filteredMaterials.put(Material.DIAMOND_BOOTS, new ItemBuilder(Material.IRON_BOOTS).setDisplayName(WHITE + "Schuppenr\u00fcstung")
                .setLore(Arrays.asList(GRAY + "Schuppenr\u00fcstung",
                        GREEN + "Qualit\u00e4t: " + GOLD + "\u2605\u2605\u2605\u2605",
                        GREEN + "Schmied: " + GOLD + "Schmiedemeister",
                        GREEN + "Herkunft: " + GOLD + "Gladeia"))
                .create());
        filteredMaterials.put(Material.IRON_HELMET, new ItemBuilder(Material.IRON_HELMET).setDisplayName(WHITE + "Schuppenr\u00fcstung")
                .setLore(Arrays.asList(GRAY + "Schuppenr\u00fcstung",
                        GREEN + "Qualit\u00e4t: " + GOLD + "\u2605\u2605\u2605\u2605",
                        GREEN + "Schmied: " + GOLD + "Schmiedemeister",
                        GREEN + "Herkunft: " + GOLD + "Gladeia"))
                .create());
        filteredMaterials.put(Material.IRON_CHESTPLATE, new ItemBuilder(Material.IRON_CHESTPLATE).setDisplayName(WHITE + "Schuppenr\u00fcstung")
                .setLore(Arrays.asList(GRAY + "Schuppenr\u00fcstung",
                        GREEN + "Qualit\u00e4t: " + GOLD + "\u2605\u2605\u2605\u2605",
                        GREEN + "Schmied: " + GOLD + "Schmiedemeister",
                        GREEN + "Herkunft: " + GOLD + "Gladeia"))
                .create());
        filteredMaterials.put(Material.IRON_LEGGINGS, new ItemBuilder(Material.IRON_LEGGINGS).setDisplayName(WHITE + "Schuppenr\u00fcstung")
                .setLore(Arrays.asList(GRAY + "Schuppenr\u00fcstung",
                        GREEN + "Qualit\u00e4t: " + GOLD + "\u2605\u2605\u2605\u2605",
                        GREEN + "Schmied: " + GOLD + "Schmiedemeister",
                        GREEN + "Herkunft: " + GOLD + "Gladeia"))
                .create());
        filteredMaterials.put(Material.IRON_BOOTS, new ItemBuilder(Material.IRON_BOOTS).setDisplayName(WHITE + "Schuppenr\u00fcstung")
                .setLore(Arrays.asList(GRAY + "Schuppenr\u00fcstung",
                        GREEN + "Qualit\u00e4t: " + GOLD + "\u2605\u2605\u2605\u2605",
                        GREEN + "Schmied: " + GOLD + "Schmiedemeister",
                        GREEN + "Herkunft: " + GOLD + "Gladeia"))
                .create());
    }

    public HashMap<Material, ItemStack> getFilteredMaterials() {
        return filteredMaterials;
    }

    public List<String> getLoreList() {
        return loreList;
    }
}
