package de.fyreum.drefilter.filter;

import de.fyreum.drefilter.DREFilter;
import de.fyreum.drefilter.config.ConfigManager;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;

public class ItemFilter {

	// test feature will be added soon.
	public void patchItem(HumanEntity entity, ItemStack item) {
		DREFilter plugin = DREFilter.getInstance();
		ConfigManager manager = plugin.getConfigManager();

		if (!manager.getAffectedWorldList().contains(entity.getWorld())) {
			return;
		}
	}
}
