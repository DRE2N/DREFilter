package de.fyreum.drefilter.commands;

import de.fyreum.drefilter.DREFilter;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // reloads if the args are correct and the player has the right permission.
        if (!sender.hasPermission("drefilter.reload")) {
            sender.sendMessage(ChatColor.RED + "Du hast keine Berechtigung, um diesen Befehl ausführen zu dürfen.");
            return false;
        }
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "/drefilter reload");
            return false;
        }
        if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("r")) {
            DREFilter.getInstance().getConfigManager().reload();
            sender.sendMessage(ChatColor.GREEN + "DREFilter wurde neugeladen");
            return true;
        }
        return false;
    }
}
