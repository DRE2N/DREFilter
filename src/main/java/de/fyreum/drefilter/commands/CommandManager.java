package de.fyreum.drefilter.commands;

import de.fyreum.drefilter.DREFilter;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Benutze: /drefilter reload | /drefilter item");
            return false;
        }
        // reloads if the args are correct and the player has the right permission.
        if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("r")) {
            if (!sender.hasPermission("drefilter.reload")) {
                sender.sendMessage(ChatColor.RED + "Du hast keine Berechtigung, um diesen Befehl ausführen zu dürfen.");
                return false;
            }
            DREFilter.getInstance().getConfigManager().reload();
            sender.sendMessage(ChatColor.GREEN + "DREFilter wurde neugeladen");
            return true;
        }
        // sends the current ItemStack.toString() to the player.
        if (args[0].equalsIgnoreCase("item")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Du musst ein Spieler sein, um diesen Befehl ausführen zu dürfen.");
                return false;
            }
            Player player = (Player) sender;
            if (!(player.hasPermission("drefilter.item"))) {
                player.sendMessage(ChatColor.RED + "Du hast keine Berechtigung, um diesen Befehl ausführen zu dürfen.");
                return false;
            }
            player.sendMessage(player.getInventory().getItemInMainHand().toString());
            return true;
        }
        return false;
    }
}
