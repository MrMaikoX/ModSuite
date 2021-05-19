package gg.maiko.modsuite.handler;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maiko
 * Date: 5/18/2021
 */

public class VanishHandler {

    @Getter private final static Map<Player, VanishHandler> vanish = new HashMap<>();

    public static boolean inVanish(Player player) {
        return vanish.containsKey(player);
    }

    public VanishHandler(Player player) {
        vanish.put(player, this);
        toggleVanish(player);
    }

    public void toggleVanish(Player player) {
        if (!inVanish(player)) {
            vanish.remove(player);
            player.sendMessage(ChatColor.GOLD + "Vanish Mode: " + ChatColor.GREEN + "Disabled");

            for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                online.showPlayer(player);
            }
        } else {
            vanish.put(player, this);
            player.sendMessage(ChatColor.GOLD + "Vanish Mode: " + ChatColor.GREEN + "Enabled");
            for (Player staff : Bukkit.getServer().getOnlinePlayers()) {
                if (!staff.hasPermission("modsuite.vanish")) {
                    staff.hidePlayer(player);
                }
            }
        }
    }
}