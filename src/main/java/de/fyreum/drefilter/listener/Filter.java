package de.fyreum.drefilter.listener;

import de.fyreum.drefilter.DREFilter;
import de.fyreum.drefilter.items.FilterItems;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Filter implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
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
		assert damager != null;
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
		ItemStack item = event.getCurrentItem();
		patchItem(event.getWhoClicked(), item);
    }

	public void patchItem(HumanEntity entity, ItemStack item) {
		if (item == null) {
			return;
		}
		DREFilter plugin = DREFilter.getInstance();
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
		runItemFilter(item);
	}

	public void runItemFilter(ItemStack item) {
		FilterItems filterItems = DREFilter.getInstance().getFilterItems();

		filterItems.getFilteredMaterials().forEach((filteredMaterial, itemStack) -> {
			if (item.getType().equals(filteredMaterial)) {
				if (itemStack == null) {
					item.setAmount(0);
					return;
				}
				if (item.getItemMeta().getLore() != null && filterItems.getLoreList().contains(item.getItemMeta().getLore().get(0))) {
					return;
				}
				Map<Enchantment, Integer> enchantmentMap = new HashMap<>();
				item.getEnchantments().forEach(enchantmentMap::put);
				item.setItemMeta(itemStack.getItemMeta());
				if (!enchantmentMap.isEmpty()) {
					item.addEnchantments(enchantmentMap);
				}
				item.setType(itemStack.getType());
				return;
			}
		});
	}
}
