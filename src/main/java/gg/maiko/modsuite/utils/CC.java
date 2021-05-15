package gg.maiko.modsuite.utils;

import org.bukkit.ChatColor;

/**
 * Created by Maiko
 * Date: 5/14/2021
 */
public class CC {

    public static String translate(String line) {
        return ChatColor.translateAlternateColorCodes('&', line);
    }

    public static String strip(String line) {
        return ChatColor.stripColor(line);
    }
}