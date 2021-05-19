package gg.maiko.modsuite.command;

import gg.maiko.modsuite.handler.ModSuiteHandler;
import gg.maiko.modsuite.handler.VanishHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Maiko
 * Date: 5/18/2021
 */
public class VanishCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println("You cannot use this command in console");
            return true;
        }

        Player player = (Player) sender;
        VanishHandler vanish = VanishHandler.getVanish().get(player);

        if(!player.hasPermission("modsuite.staff")) {
            player.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }

        if (VanishHandler.inVanish(player)) {
            VanishHandler.getVanish().remove(player);
            return true;
        }

        VanishHandler.getVanish().put(player, new VanishHandler(player));
        return false;
    }
}
