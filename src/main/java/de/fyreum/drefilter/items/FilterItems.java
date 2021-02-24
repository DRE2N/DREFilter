package de.fyreum.drefilter.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.bukkit.ChatColor.*;

public class FilterItems {

    private final String reducedPlayerDamageLore = GRAY + "Geringer Spielerschaden";
    private final HashMap<Material, ItemStack> filteredMaterials = new HashMap<>();
    private final List<String> loreList = Arrays.asList(GRAY + "Schuppenr\u00fcstung", GRAY + "Plattenr\u00fcstung", GRAY + "Dolch", GRAY + "Katana", GRAY + "Langschwert",
            GRAY + "Ritterschwert", GRAY + "Rapier", BLUE + "Questitem");
    private final ItemStack plateHelmet = new ItemBuilder(Material.IRON_HELMET).setDisplayName(WHITE + "Schuppenr\u00fcstung")
            .setLore(Arrays.asList(GRAY + "Schuppenr\u00fcstung",
                    GREEN + "Qualit\u00e4t: " + GOLD + "\u2605\u2605\u2605\u2605",
                    GREEN + "Schmied: " + GOLD + "Schmiedemeister",
                    GREEN + "Herkunft: " + GOLD + "Gladeia"))
            .create();
    private final ItemStack plateChestPlate = new ItemBuilder(Material.IRON_CHESTPLATE).setDisplayName(WHITE + "Schuppenr\u00fcstung")
            .setLore(Arrays.asList(GRAY + "Schuppenr\u00fcstung",
                    GREEN + "Qualit\u00e4t: " + GOLD + "\u2605\u2605\u2605\u2605",
                    GREEN + "Schmied: " + GOLD + "Schmiedemeister",
                    GREEN + "Herkunft: " + GOLD + "Gladeia"))
            .create();
    private final ItemStack plateLeggings = new ItemBuilder(Material.IRON_LEGGINGS).setDisplayName(WHITE + "Schuppenr\u00fcstung")
            .setLore(Arrays.asList(GRAY + "Schuppenr\u00fcstung",
                    GREEN + "Qualit\u00e4t: " + GOLD + "\u2605\u2605\u2605\u2605",
                    GREEN + "Schmied: " + GOLD + "Schmiedemeister",
                    GREEN + "Herkunft: " + GOLD + "Gladeia"))
            .create();
    private final ItemStack plateBoots = new ItemBuilder(Material.IRON_BOOTS).setDisplayName(WHITE + "Schuppenr\u00fcstung")
            .setLore(Arrays.asList(GRAY + "Schuppenr\u00fcstung",
                    GREEN + "Qualit\u00e4t: " + GOLD + "\u2605\u2605\u2605\u2605",
                    GREEN + "Schmied: " + GOLD + "Schmiedemeister",
                    GREEN + "Herkunft: " + GOLD + "Gladeia"))
            .create();

    public void setup() {
        // materials
        filteredMaterials.put(Material.WOODEN_SWORD, new ItemBuilder(Material.WOODEN_SWORD).addLore(reducedPlayerDamageLore).create());
        filteredMaterials.put(Material.STONE_SWORD, new ItemBuilder(Material.STONE_SWORD).addLore(reducedPlayerDamageLore).create());
        filteredMaterials.put(Material.DIAMOND_SWORD, new ItemBuilder(Material.IRON_SWORD).addLore(reducedPlayerDamageLore).create());
        filteredMaterials.put(Material.IRON_SWORD, new ItemBuilder(Material.IRON_SWORD).addLore(reducedPlayerDamageLore).create());
        filteredMaterials.put(Material.NETHER_STAR, null);
        filteredMaterials.put(Material.DIAMOND_HELMET, plateHelmet);
        filteredMaterials.put(Material.DIAMOND_CHESTPLATE, plateChestPlate);
        filteredMaterials.put(Material.DIAMOND_LEGGINGS, plateLeggings);
        filteredMaterials.put(Material.DIAMOND_BOOTS, plateBoots);
        filteredMaterials.put(Material.IRON_HELMET, plateHelmet);
        filteredMaterials.put(Material.IRON_CHESTPLATE, plateChestPlate);
        filteredMaterials.put(Material.IRON_LEGGINGS, plateLeggings);
        filteredMaterials.put(Material.IRON_BOOTS, plateBoots);

        // 1.16
        if (Bukkit.getVersion().contains("1.16")) {
            filteredMaterials.put(Material.NETHERITE_HELMET, plateHelmet);
            filteredMaterials.put(Material.NETHERITE_CHESTPLATE, plateChestPlate);
            filteredMaterials.put(Material.NETHERITE_LEGGINGS, plateLeggings);
            filteredMaterials.put(Material.NETHERITE_BOOTS, plateBoots);
            filteredMaterials.put(Material.NETHERITE_AXE, new ItemBuilder(Material.NETHERITE_AXE).addLore(reducedPlayerDamageLore).create());
            filteredMaterials.put(Material.NETHERITE_SWORD, new ItemBuilder(Material.NETHERITE_SWORD).addLore(reducedPlayerDamageLore).create());
        }
    }

    public String getReducedPlayerDamageLore() {
        return reducedPlayerDamageLore;
    }

    public HashMap<Material, ItemStack> getFilteredMaterials() {
        return filteredMaterials;
    }

    public List<String> getLoreList() {
        return loreList;
    }
}
