package gg.maiko.modsuite.command;

import gg.maiko.modsuite.ModSuite;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Maiko
 * Date: 5/14/2021
 */
public class SuiteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println("You cannot use this command in console");
            return true;
        }

        Player player = (Player) sender;
        if(!player.hasPermission("modsuite.staff")) {
            player.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }

        boolean newState = !ModSuite.getInstance().getSuiteHandler().inModMode(player);
        ModSuite.getInstance().getSuiteHandler().toggleStaff(player);
        player.sendMessage(ChatColor.GOLD + "Staff Mode: " + (newState ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled"));
        return false;
    }
}
