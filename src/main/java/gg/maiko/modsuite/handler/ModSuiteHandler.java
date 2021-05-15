package gg.maiko.modsuite.handler;

import gg.maiko.modsuite.utils.CC;
import gg.maiko.modsuite.utils.ItemBuilder;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Created by Maiko
 * Date: 5/14/2021
 */
public class ModSuiteHandler {

    public Map<Player, ItemStack[]> modInventories;
    public static final Set<UUID> staffMode = new HashSet<>();;

    public ModSuiteHandler() {
        modInventories = new HashMap<>();
    }

    public boolean inModMode(Player player) {
        return staffMode.contains(player.getUniqueId());
    }

    public void toggleStaff(Player player) {
        if(inModMode(player)) {
            modInventories.put(player, player.getInventory().getContents());
            player.setGameMode(GameMode.CREATIVE);
            // Compass will work if you have worldedit on
            player.getInventory().setItem(0, new ItemBuilder(Material.COMPASS).setName("&6Compass").build());
            player.getInventory().setItem(1, new ItemBuilder(Material.BOOK).setName("&6Inspection Book").build());
            player.getInventory().setItem(2, new ItemBuilder(Material.CARPET).setDurability(1).build());
            player.getInventory().setItem(4, new ItemBuilder(Material.PACKED_ICE).setName("&6Freeze").build());
            player.getInventory().setItem(7, new ItemBuilder(Material.WATCH).setName("&6Random Teleport").build());
            player.getInventory().setItem(8, new ItemBuilder(Material.INK_SACK).setName("&6Become Visible").setDurability(10).build());
        } else {
            if (modInventories.containsKey(player)) {
                player.getInventory().setContents(modInventories.get(player));
            }
        }
    }
}
