package gg.maiko.modsuite.listener;

import gg.maiko.modsuite.utils.PlayerUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;

import java.util.Arrays;

/**
 * Created by Maiko
 * Date: 5/18/2021
 */
public class FreezeListener implements Listener {

    private final String FROZEN_MESSAGE = ChatColor.RED + "You cannot do this while frozen";

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (event.getPlayer().hasMetadata("frozen")) {
            PlayerUtil.onlineStaff("");
            PlayerUtil.onlineStaff("&4&l" + event.getPlayer().getName() + " &chas join while frozen!");
            PlayerUtil.onlineStaff("");
        }
    }


    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (event.getPlayer().hasMetadata("frozen")) {
            PlayerUtil.onlineStaff("");
            PlayerUtil.onlineStaff("&4&l" + event.getPlayer().getName() + " &chas quit while frozen!");
            PlayerUtil.onlineStaff("");
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerKick(PlayerKickEvent event) {
        if (event.getPlayer().hasMetadata("frozen")) {
            PlayerUtil.onlineStaff("");
            PlayerUtil.onlineStaff("&4&l" + event.getPlayer().getName() + " &chas quit while frozen!");
            PlayerUtil.onlineStaff("");
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerMove(PlayerMoveEvent event){
        if(event.getPlayer().hasMetadata("frozen")){
            if((event.getTo().getBlockX() - event.getFrom().getBlockX()) != 0 || (event.getTo().getBlockZ() - event.getFrom().getBlockZ()) != 0){
                event.getPlayer().teleport(event.getFrom());
                event.getPlayer().sendMessage(FROZEN_MESSAGE);
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getPlayer().hasMetadata("frozen")) {
            event.getPlayer().sendMessage(FROZEN_MESSAGE);
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerDrop(PlayerDropItemEvent event) {
        if(event.getPlayer().hasMetadata("frozen")) {
            event.getPlayer().sendMessage(FROZEN_MESSAGE);
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onBlockPlace(BlockBreakEvent event) {
        if(event.getPlayer().hasMetadata("frozen")) {
            event.getPlayer().sendMessage(FROZEN_MESSAGE);
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onBuild(BlockPlaceEvent event) {
        if(event.getPlayer().hasMetadata("frozen")) {
            event.getPlayer().sendMessage(FROZEN_MESSAGE);
            event.setCancelled(true);

        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getEntity().hasMetadata("frozen")) {
                event.getEntity().sendMessage(FROZEN_MESSAGE);
                event.setCancelled(true);
            }
        }
    }
}