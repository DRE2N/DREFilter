package de.fyreum.drefilter.items;

import de.fyreum.drefilter.exceptions.ItemBuilderException;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {

    private ItemStack item;
    private ItemMeta meta;
    private PotionMeta potionMeta;
    private boolean isPotionMeta = false;

    public ItemBuilder(Material material) {
        this.item = new ItemStack(material);
        if ( material.equals(Material.POTION) || material.equals(Material.LINGERING_POTION) || material.equals(Material.SPLASH_POTION) ) {
            this.potionMeta = (PotionMeta) item.getItemMeta();
            isPotionMeta = true;
            return;
        }
        this.meta = item.getItemMeta();
    }

    public ItemBuilder setDisplayName(String name) {
        if (isPotionMeta) {
            potionMeta.setDisplayName(name);
            item.setItemMeta(potionMeta);
            return this;
        }
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLocalName(String name) {
        if (isPotionMeta) {
            potionMeta.setLocalizedName(name);
            item.setItemMeta(potionMeta);
            return this;
        }
        meta.setLocalizedName(name);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder addLore(String s) {
        if (isPotionMeta) {
            if (potionMeta.getLore() == null) {
                potionMeta.setLore(new ArrayList<>());
            }
            List<String> lore = potionMeta.getLore();
            lore.add(s);
            potionMeta.setLore(lore);
            item.setItemMeta(potionMeta);
            return this;
        }
        if (meta.getLore() == null) {
            meta.setLore(new ArrayList<>());
        }
        List<String> lore = meta.getLore();
        lore.add(s);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        if (isPotionMeta) {
            potionMeta.setLore(lore);
            item.setItemMeta(potionMeta);
            return this;
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setBasePotionData(PotionData data) {
        if (!isPotionMeta) {
            throw new ItemBuilderException("Couldn't set the basePotionData of item. The Item isn't a Potion.");
        }
        potionMeta.setBasePotionData(data);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        item.getItemMeta().setUnbreakable(unbreakable);
        return this;
    }

    public ItemBuilder removeDamage() {
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "noDamage", -1, AttributeModifier.Operation.MULTIPLY_SCALAR_1));
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder removeDamageHidden() {
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "noDamage", -1, AttributeModifier.Operation.MULTIPLY_SCALAR_1));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return this;
    }

    public ItemStack create() {
        return item;
    }

}
