package gg.maiko.modsuite.handler;

import gg.maiko.modsuite.utils.CC;
import gg.maiko.modsuite.utils.ItemBuilder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.persistence.GeneratedValue;
import java.util.*;

/**
 * Created by Maiko
 * Date: 5/14/2021
 */
public class ModSuiteHandler {

    @Getter private final Map<UUID, ItemStack[]> inveotry = new HashMap<>();
    @Getter private final Map<UUID, ItemStack[]> armor = new HashMap<>();
    @Getter private final static Map<Player, ModSuiteHandler> staffMode = new HashMap<>();

    public ModSuiteHandler(Player player) {
        staffMode.put(player, this);
        toggleStaff(player);
    }

    public static boolean inModMode(Player player) {
        return staffMode.containsKey(player);
    }

    public void toggleStaff(Player player) {
        storeAndClearInventory(player);
        player.getInventory().clear();
        player.setGameMode(GameMode.CREATIVE);
        // Compass will work if you have worldedit on
        player.getInventory().setItem(0, new ItemBuilder(Material.COMPASS).setName("&6Compass").build());
        player.getInventory().setItem(1, new ItemBuilder(Material.BOOK).setName("&6Inspection Book").build());
        player.getInventory().setItem(2, new ItemBuilder(Material.CARPET).setName("&6").setDurability(1).build());
        player.getInventory().setItem(4, new ItemBuilder(Material.PACKED_ICE).setName("&6Freeze").build());
        player.getInventory().setItem(7, new ItemBuilder(Material.WATCH).setName("&6Random Teleport").build());
        player.getInventory().setItem(8, new ItemBuilder(Material.INK_SACK).setName("&6Become Visible").setDurability(10).build());
        player.sendMessage(ChatColor.GOLD + "Mod Mode: " + ChatColor.GREEN + "Enabled");
    }

    public void disableStaff(Player player) {
        player.getInventory().clear();
        restoreInventory(player);
        staffMode.remove(player);
        if (!player.hasPermission("modsuite.admin")) {
            player.setGameMode(GameMode.SURVIVAL);
        }
        player.sendMessage(ChatColor.GOLD + "Mod Mode: " + ChatColor.RED + "Disabled");

    }

    private void storeAndClearInventory(Player player){
        UUID uuid = player.getUniqueId();

        ItemStack[] cont = player.getInventory().getContents();
        ItemStack[] armcont = player.getInventory().getArmorContents();

        inveotry.put(uuid, cont);
        armor.put(uuid, armcont);

        player.getInventory().clear();

        remArmor(player);
    }

    private void restoreInventory(Player player){
        UUID uuid = player.getUniqueId();

        ItemStack[] contents = inveotry.get(uuid);
        ItemStack[] armorContents = armor.get(uuid);

        if(contents != null){
            player.getInventory().setContents(contents);
        }
        else{//if the player has no inventory contents, clear their inventory
            player.getInventory().clear();
        }

        if(armorContents != null){
            player.getInventory().setArmorContents(armorContents);
        }
        else{//if the player has no armor, set the armor to null
            remArmor(player);
        }
    }

    private void remArmor(Player player){
        player.getInventory().setArmorContents(null);

    }
}