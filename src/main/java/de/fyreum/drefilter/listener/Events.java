package de.fyreum.drefilter.listener;

import de.fyreum.drefilter.DREFilter;
import de.fyreum.drefilter.filter.EnchantmentFilter;
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

public class Events implements Listener {

	private EnchantmentFilter enchantmentFilter = new EnchantmentFilter();

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
		enchantmentFilter.patchItem(damager, damager.getInventory().getItemInMainHand());
		enchantmentFilter.patchItem(damager, damager.getInventory().getItemInOffHand());
		// continues if the damaged entity is a player.
		if (!(event.getEntity() instanceof Player)) {
			return;
		}
		HumanEntity damaged = (HumanEntity) event.getEntity();
		// calls the patchItem() method for the armor content.
		for (ItemStack armor : damaged.getInventory().getArmorContents()) {
			enchantmentFilter.patchItem(damaged, armor);
		}
	}
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		// calls the patchItem() method for both of the player hands.
		enchantmentFilter.patchItem(event.getPlayer(), event.getPlayer().getInventory().getItemInMainHand());
		enchantmentFilter.patchItem(event.getPlayer(), event.getPlayer().getInventory().getItemInOffHand());
		// test message
		event.getPlayer().sendMessage(event.getPlayer().getInventory().getItemInMainHand().toString());
	}
	@EventHandler
	public void onFish(PlayerFishEvent event) {
		// patches all fished items.
		if (event.getCaught() instanceof Item) {
			enchantmentFilter.patchItem(event.getPlayer(), ((Item) event.getCaught()).getItemStack());
		}
	}
	@EventHandler
    public void onClick(InventoryClickEvent event) {
		// test message
		event.getWhoClicked().sendMessage(event.getCurrentItem().toString());
		enchantmentFilter.patchItem(event.getWhoClicked(), event.getCurrentItem());
    }

}
