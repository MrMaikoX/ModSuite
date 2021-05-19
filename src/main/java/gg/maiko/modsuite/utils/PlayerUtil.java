package gg.maiko.modsuite.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by Maiko
 * Date: 5/18/2021
 */

public class PlayerUtil {

    public static void onlineStaff(String message) {
        for (Player staff : Bukkit.getOnlinePlayers()) {
            if (staff.hasPermission("modsuite.staff")) {
                staff.sendMessage(CC.translate(message));
            }
        }
    }
}
