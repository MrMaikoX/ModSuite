package gg.maiko.modsuite.listener;

import gg.maiko.modsuite.handler.VanishHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

/**
 * Created by Maiko
 * Date: 5/18/2021
 */
public class VanishListener implements Listener {

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if (VanishHandler.inVanish(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        event.setCancelled(VanishHandler.inVanish(event.getPlayer()));
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        event.setCancelled(VanishHandler.inVanish(event.getPlayer()));
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        // will allow people with op or this permission to break blocks.
        if (player.hasPermission("modsuite.admin") || player.isOp()) {
            return;
        }

        if (VanishHandler.inVanish(player)) {
            e.setCancelled(true);

        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        // will allow people with op or this permission to place blocks.
        if (player.hasPermission("modsuite.admin") || player.isOp()) {
            return;
        }

        if (VanishHandler.inVanish(player)) {
            e.setCancelled(true);

        }
    }
}
