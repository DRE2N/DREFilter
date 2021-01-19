package de.fyreum.drefilter.commands;

import de.fyreum.drefilter.DREFilter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {

    DREFilter plugin = DREFilter.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Benutze: /drefilter reload | /drefilter item | drefilter removeDamage");
            return false;
        }
        // reloads if the args are correct and the player has the right permission.
        if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("r")) {
            if (!sender.hasPermission("drefilter.reload")) {
                sender.sendMessage(ChatColor.RED + "Du hast keine Berechtigung, um diesen Befehl ausf\u00fchren zu d\u00fcrfen.");
                return false;
            }
            plugin.getConfigManager().reload();
            plugin.saveDefaultConfig();
            sender.sendMessage(ChatColor.GREEN + "DREFilter wurde neugeladen");
            return true;
        }
        // sends the current ItemStack.toString() to the player.
        if (args[0].equalsIgnoreCase("item")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Du musst ein Spieler sein, um diesen Befehl ausf\u00fchren zu d\u00fcrfen.");
                return false;
            }
            Player player = (Player) sender;
            if (!(player.hasPermission("drefilter.item"))) {
                player.sendMessage(ChatColor.RED + "Du hast keine Berechtigung, um diesen Befehl ausf\u00fchren zu d\u00fcrfen.");
                return false;
            }
            player.sendMessage(player.getInventory().getItemInMainHand().toString());
            return true;
        }
        // removes any damage while in the main hand
        if (args[0].equalsIgnoreCase("removedamage")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Du musst ein Spieler sein, um diesen Befehl ausf\u00fchren zu d\u00fcrfen.");
                return false;
            }
            Player player = (Player) sender;
            if (!(player.hasPermission("drefilter.removedamage"))) {
                player.sendMessage(ChatColor.RED + "Du hast keine Berechtigung, um diesen Befehl ausf\u00fchren zu d\u00fcrfen.");
                return false;
            }
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getType().equals(Material.AIR)) {
                player.sendMessage(ChatColor.RED + "Du musst ein Item in der Haupthand haben.");
                return false;
            }
            ItemMeta meta = item.getItemMeta();
            List<String> lore = meta.getLore();
            if (lore == null) {
                lore = new ArrayList<>();
            }

            lore.remove(plugin.getConfigManager().getNoDamageItemLore());
            lore.add(plugin.getConfigManager().getNoDamageItemLore());

            if (meta.hasAttributeModifiers() && meta.getAttributeModifiers() != null) {
                meta.getAttributeModifiers().forEach((attribute, attributeModifier) -> {
                    if (attribute.equals(Attribute.GENERIC_ATTACK_DAMAGE) && attributeModifier.getName().equalsIgnoreCase("noDamage")) {
                        meta.removeAttributeModifier(attribute, attributeModifier);
                    }
                });
            }
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, plugin.getNoDamageModifier());
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

            meta.setLore(lore);
            item.setItemMeta(meta);
            player.sendMessage(ChatColor.GREEN + "Schaden in der Haupthand wurde auf 0 gesetzt.");
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> complete = new ArrayList<>();
        if (args.length == 1) {
            if ("reload".toLowerCase().startsWith(args[0].toLowerCase())) complete.add("reload");
            if ("item".toLowerCase().startsWith(args[0].toLowerCase())) complete.add("item");
            if ("removeDamage".toLowerCase().startsWith(args[0].toLowerCase())) complete.add("removeDamage");
        }
        return complete;
    }
}
