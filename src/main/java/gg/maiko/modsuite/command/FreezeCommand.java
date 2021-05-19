package gg.maiko.modsuite.command;

import gg.maiko.modsuite.ModSuite;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * Created by Maiko
 * Date: 5/18/2021
 */
public class FreezeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println("You cannot use this command in console");
            return true;
        }

        if(!sender.hasPermission("modsuite.staff")) {
            sender.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /freeze <player>");
            return false;
        }

        Player player = (Player) sender;
        Player target = Bukkit.getServer().getPlayer(args[0]);

        if(target == null) {
            player.sendMessage(ChatColor.RED + args[0] + " is not online!");
            return true;
        }

        if (target.equals(player) && target.hasPermission("modsuite.staff")) {
            player.sendMessage(ChatColor.RED + "You cannot freeze yourself.");
            return true;
        }

        target.setMetadata("frozen", new FixedMetadataValue(ModSuite.getInstance(), true));
        target.sendMessage(ChatColor.RED + "You have been frozen!");
        return false;
    }
}
