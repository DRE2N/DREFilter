package de.fyreum.drefilter.listener;

import de.fyreum.drefilter.DREFilter;
import de.fyreum.drefilter.items.FilterItems;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Filter implements Listener {

	private final DREFilter plugin;

	public Filter(DREFilter plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onInteract(PlayerInteractEntityEvent event) {
		// cancels the interaction between Player and Villager
		if (event.getRightClicked() instanceof Villager && DREFilter.getInstance().getConfigManager().isVillagerDisabled()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		HumanEntity damager;
		// sets the player who did the damage in this event.
		if (event.getDamager() instanceof Player) {
			damager = (HumanEntity) event.getDamager();
		} else {
			if (!(event.getDamager() instanceof Arrow) || !(((Arrow) event.getDamager()).getShooter() instanceof HumanEntity)) {
				return;
			}
			// sets the damager to the shooter if the event.getDamager() is an arrow.
			damager = (HumanEntity) ((Arrow) event.getDamager()).getShooter();
		}
		if (damager == null) {
			return;
		}
		// reduces the damage output of certain weapons
		if (damager.getInventory().getItemInMainHand().getItemMeta() != null && damager.getInventory().getItemInMainHand().getItemMeta().getLore() != null &&
				damager.getInventory().getItemInMainHand().getItemMeta().getLore().contains(plugin.getFilterItems().getReducedPlayerDamageLore())) {
			if (event.getEntity() instanceof Player) {
				event.setDamage(event.getDamage()*plugin.getConfigManager().getReducedDamageMultiplier());
			}
		}
		// calls the patchItem() method for both hands of the player.
		patchItem(damager, damager.getInventory().getItemInMainHand());
		patchItem(damager, damager.getInventory().getItemInOffHand());
		// continues if the damaged entity is a player.
		if (!(event.getEntity() instanceof Player)) {
			return;
		}
		HumanEntity damaged = (HumanEntity) event.getEntity();
		// calls the patchItem() method for the armor content.
		for (ItemStack armor : damaged.getInventory().getArmorContents()) {
			patchItem(damaged, armor);
		}
	}
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		// calls the patchItem() method for both of the player hands.
		patchItem(event.getPlayer(), event.getPlayer().getInventory().getItemInMainHand());
		patchItem(event.getPlayer(), event.getPlayer().getInventory().getItemInOffHand());
	}
	@EventHandler
	public void onFish(PlayerFishEvent event) {
		// patches all fished items.
		if (event.getCaught() instanceof Item) {
			patchItem(event.getPlayer(), ((Item) event.getCaught()).getItemStack());
		}
	}
	@EventHandler
    public void onClick(InventoryClickEvent event) {
		if (event.getClickedInventory() != null && !event.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
			return;
		}
		patchItem(event.getWhoClicked(), event.getCurrentItem());
    }

	public void patchItem(HumanEntity entity, ItemStack item) {
		if (item == null) {
			return;
		}
		if (entity.hasPermission("drefilter.bypass")) {
			return;
		}
		// if the players current world isn't contained by the affectedWorldList the method stops.
		if (!plugin.getConfigManager().getAffectedWorldList().contains(entity.getWorld().getName())) {
			return;
		}
		// looking for the itemMeta and enchantments.
		if (item.hasItemMeta() && item.getItemMeta().hasEnchants()) {
			// gets the enchantment values and disabled enchants.
			ArrayList<NamespacedKey> disabledEnchants = plugin.getConfigManager().getDisabledEnchants();
			HashMap<NamespacedKey, Integer> enchantmentValues = plugin.getConfigManager().getEnchantmentValues();
			// patches the item.
			for(Map.Entry<Enchantment, Integer> enchant : item.getEnchantments().entrySet()) {
				// removes the enchantment, if it's on the disabled list.
				if (disabledEnchants.contains(enchant.getKey().getKey())) {
					item.removeEnchantment(enchant.getKey());
					continue;
				}
				// downgrades the enchantment, if the level is higher than the maximum value.
				if (enchant.getValue() > enchantmentValues.get(enchant.getKey().getKey())) {
					item.addUnsafeEnchantment(enchant.getKey(), enchantmentValues.get(enchant.getKey().getKey()));
				}
			}
		}
		runItemFilter(item);
	}

	public void runItemFilter(ItemStack item) {
		FilterItems filterItems = plugin.getFilterItems();

		ItemMeta meta = item.getItemMeta();
		if (!(meta != null && meta.getLore() != null && meta.getLore().size() != 0 && filterItems.getLoreList().contains(meta.getLore().get(0)))) {
			filterItems.getFilteredMaterials().forEach((filteredMaterial, itemStack) -> {
				if (item.getType().equals(filteredMaterial)) {
					if (itemStack == null) {
						item.setAmount(0);
						return;
					}
					// gets the enchantments to add
					Map<Enchantment, Integer> enchantmentMap = new HashMap<>(item.getEnchantments());
					// merges the item meta
					ItemMeta itemMeta = itemStack.getItemMeta();
					if (meta != null) {
						if (meta.getLore() == null) {
							meta.setLore(new ArrayList<>());
						} else {
							if (itemMeta.getLore() == null) {
								itemMeta.setLore(new ArrayList<>());
							}
							List<String> lore = itemMeta.getLore();
							meta.getLore().forEach(s -> {
								if (!s.equals(filterItems.getReducedPlayerDamageLore())) {
									lore.add(s);
								}
							});
							itemMeta.setLore(lore);
						}
					}
					item.setItemMeta(itemMeta);
					// adds the enchantments
					if (!enchantmentMap.isEmpty()) {
						HashMap<NamespacedKey, Integer> enchantmentValues = plugin.getConfigManager().getEnchantmentValues();
						enchantmentMap.forEach((enchantment, integer) -> {
							if (integer > enchantmentValues.get(enchantment.getKey())) {
								item.addUnsafeEnchantment(enchantment, enchantmentValues.get(enchantment.getKey()));
								return;
							}
							item.addUnsafeEnchantment(enchantment, integer);
						});
					}
					item.setType(itemStack.getType());
				}
			});
		}
		if (meta != null && meta.getAttributeModifiers() != null) {
			for (AttributeModifier attributeModifier : meta.getAttributeModifiers().get(Attribute.GENERIC_ATTACK_DAMAGE)) {
				if (attributeModifier == null) {
					continue;
				}
				if (attributeModifier.getUniqueId().equals(plugin.getNoDamageModifier().getUniqueId())) {
					return;
				}
			}
		}
		handleNoItemDamage(item);
	}

	private void handleNoItemDamage(ItemStack item) {
		if (item.getItemMeta() != null && item.getItemMeta().getLore() != null) {
			for (String s : plugin.getConfigManager().getIgnoreDamageFilterLore()) {
				if (item.getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', s))) {
					return;
				}
			}
		}
		for (String noDamageItem : plugin.getConfigManager().getNoDamageItems()) {
			if (item.getType().name().contains(noDamageItem)) {
				ItemMeta meta = item.getItemMeta();
				try {
					meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, plugin.getNoDamageModifier());
					if (!meta.hasItemFlag(ItemFlag.HIDE_ATTRIBUTES)) {
						meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
					}
					List<String> lore = meta.getLore();
					if (lore == null) {
						lore = new ArrayList<>();
					}
					if (!lore.contains(plugin.getConfigManager().getNoDamageItemLore())) {
						lore.add(plugin.getConfigManager().getNoDamageItemLore());
					}
					meta.setLore(lore);
				} catch (IllegalArgumentException i) {
					i.printStackTrace();
				}
				item.setItemMeta(meta);
			}
		}
	}
}
