package de.fyreum.drefilter.Listener;

import de.fyreum.drefilter.DREFilter;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Filter implements Listener {
	// cancels the interaction between Player and Villager
	@EventHandler(priority = EventPriority.LOWEST)
	public void onInteract(PlayerInteractEntityEvent event) {
		if (event.getRightClicked() instanceof Villager && DREFilter.getInstance().getConfigManager().isVillagerDisabled()) {
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		HumanEntity damager;
		if (event.getDamager() instanceof Player) {
			damager = (HumanEntity) event.getDamager();
		} else {
			if (!(event.getDamager() instanceof Arrow) || !(((Arrow) event.getDamager()).getShooter() instanceof HumanEntity)) {
				return;
			}
			damager = (HumanEntity) ((Arrow) event.getDamager()).getShooter();
		}
		assert damager != null;
		patchItem(damager, damager.getInventory().getItemInMainHand());
		patchItem(damager, damager.getInventory().getItemInOffHand());

		if (!(event.getEntity() instanceof Player)) {
			return;
		}
		HumanEntity damaged = (HumanEntity) event.getEntity();
		for (ItemStack armor : damaged.getInventory().getArmorContents()) {
			patchItem(damaged, armor);
		}
	}
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		patchItem(event.getPlayer(), event.getPlayer().getInventory().getItemInMainHand());
		patchItem(event.getPlayer(), event.getPlayer().getInventory().getItemInOffHand());
		event.getPlayer().sendMessage(event.getPlayer().getInventory().getItemInMainHand().toString());
	}
	@EventHandler
	public void onFish(PlayerFishEvent event) {
		if (event.getCaught() instanceof Item) {
			patchItem(event.getPlayer(), ((Item) event.getCaught()).getItemStack());
		}
	}
	@EventHandler
    public void onClick(InventoryClickEvent event) {
		event.getWhoClicked().sendMessage(event.getCurrentItem().toString());
		patchItem(event.getWhoClicked(), event.getCurrentItem());
    }

	public void patchItem(HumanEntity entity, ItemStack item) {
		if (item == null) {
			return;
		}
		if (!entity.getWorld().getName().equalsIgnoreCase("Saragandes")) {
			return;
		}
		if (item.hasItemMeta() && item.getItemMeta().hasEnchants()) {
			DREFilter plugin = DREFilter.getInstance();
			ArrayList<NamespacedKey> disabledEnchants = plugin.getConfigManager().getDisabledEnchants();
			HashMap<NamespacedKey, Integer> enchantmentValues = plugin.getConfigManager().getEnchantmentValues();

			for(Map.Entry<Enchantment, Integer> enchant : item.getEnchantments().entrySet()) {
                if (disabledEnchants.contains(enchant.getKey().getKey())) {
                    item.removeEnchantment(enchant.getKey());
                    continue;
                }
                if (enchant.getValue() > enchantmentValues.get(enchant.getKey().getKey())) {
                    item.addEnchantment(enchant.getKey(), enchantmentValues.get(enchant.getKey().getKey()));
                }
            }
		}
	}
}
