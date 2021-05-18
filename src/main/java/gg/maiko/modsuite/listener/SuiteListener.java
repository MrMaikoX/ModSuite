package gg.maiko.modsuite.listener;

import gg.maiko.modsuite.ModSuite;
import gg.maiko.modsuite.handler.ModSuiteHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Created by Maiko
 * Date: 5/14/2021
 */
public class SuiteListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("modsuite.staff")) {
            ModSuiteHandler.getStaffMode().put(player, new ModSuiteHandler(player));
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (ModSuiteHandler.inModMode(player)) {
            ModSuiteHandler.getStaffMode().put(player, new ModSuiteHandler(player));
        }
    }


    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        // will allow people with op or this permission to break blocks.
        if (player.hasPermission("modsuite.admin") || player.isOp()) {
            return;
        }

        if (ModSuiteHandler.inModMode(player)) {
            e.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You cannot break blocks in mod mode.");

        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        // will allow people with op or this permission to place blocks.
        if (player.hasPermission("modsuite.admin") || player.isOp()) {
            return;
        }

        if (ModSuiteHandler.inModMode(player)) {
            e.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You cannot place blocks in mod mode.");

        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if (ModSuiteHandler.inModMode(player)) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You cannot do this while in mod mode.");
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        event.setCancelled(ModSuiteHandler.inModMode(event.getPlayer()));
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        event.setCancelled(ModSuiteHandler.inModMode(event.getPlayer()));
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (ModSuiteHandler.inModMode(e.getEntity())) {
            e.setDeathMessage(null);
            e.setKeepInventory(true);
            e.setKeepLevel(true);
            e.getDrops().clear();
        }
    }

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity rightClicked = event.getRightClicked();
        if (rightClicked instanceof Player && (ModSuiteHandler.inModMode(player))) {
            ItemStack inHand = player.getItemInHand();
            // We will use performCommand until we make proper methods.
            if (inHand != null && inHand.getType() == Material.BOOK) {
                player.performCommand("invsee " + rightClicked.getName());
            }

            if (inHand != null && inHand.getType() == Material.PACKED_ICE) {
                player.performCommand("freeze " + rightClicked.getName());
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (ModSuiteHandler.inModMode(p)) {
            if (p.getItemInHand().getType() == Material.WATCH && event.getAction().toString().contains("RIGHT")) {
                event.setCancelled(true);
                List<Player> onlinePlayers = Bukkit.getOnlinePlayers().stream().filter(other -> !ModSuiteHandler.getStaffMode().containsKey(other)).collect(Collectors.toList());
                Player tp;

                if (onlinePlayers.isEmpty()) {
                    p.sendMessage(ChatColor.RED + "Currently no players online.");
                    return;
                }

                if (onlinePlayers.size() == 1) {
                    tp = onlinePlayers.iterator().next();
                } else {
                    int randInt = ThreadLocalRandom.current().nextInt(onlinePlayers.size());
                    tp = onlinePlayers.get(randInt);
                }
                Bukkit.dispatchCommand(p, "teleport " + tp.getName());
            }
        }
    }
}
