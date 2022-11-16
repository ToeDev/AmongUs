package org.toedev.amongus.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitScheduler;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.Prefix;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.map.MapManager;
import org.toedev.amongus.sabotages.AbstractSabotage;
import org.toedev.amongus.sabotages.SabotageManager;
import org.toedev.amongus.sabotages.sabotages.LightsSabotage;

import java.util.Objects;

public class SabotageHandler implements Listener {

    private final BukkitScheduler scheduler;

    private final AmongUs amongUs;
    private final MapManager mapManager;
    private final GameHandler gameHandler;
    private final SabotageManager sabotageManager;

    private final ChatColor purple = ChatColor.LIGHT_PURPLE;
    private final ChatColor gold = ChatColor.GOLD;
    private final ChatColor red = ChatColor.RED;

    public SabotageHandler(AmongUs amongUs, MapManager mapManager, GameHandler gameHandler, SabotageManager sabotageManager) {
        scheduler = amongUs.getServer().getScheduler();
        this.amongUs = amongUs;
        this.mapManager = mapManager;
        this.gameHandler = gameHandler;
        this.sabotageManager = sabotageManager;
    }

    @EventHandler
    public void onSabotageBlockInteract(PlayerInteractEvent event) {
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock() == null) return;
        if(Objects.equals(event.getHand(), EquipmentSlot.OFF_HAND)) return;
        Player player = event.getPlayer();
        if(!gameHandler.isPlayerInAnyMap(player)) return;
        Map map = gameHandler.getMapPlayerIsIn(player);
        if(map == null) return;
        AbstractSabotage sabotage = sabotageManager.getSabotageByLocation(event.getClickedBlock().getLocation());
        if(sabotage == null) return;
        //if(gameHandler.getPlayerTasks(player) == null || !gameHandler.getPlayerTasks(player).contains(task)) return;
        //if(task.isInUse()) return;
        //if(task instanceof ClearAsteroidsTask) return;
        //if(task.getLocation().distance(player.getLocation()) > amongUs.getDistanceFromTask()) {
        //    player.sendMessage(Prefix.prefix + red + "You must be closer to the task to start it!");
        //    return;
        //}
        Location loc = sabotage.getLocation();
        Bukkit.getConsoleSender().sendMessage(Prefix.prefix + gold + player.getName() + purple + " clicked a sabotage block initiating task: " + gold + sabotage.getName() + purple + " At: " + gold + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ() + "," + Objects.requireNonNull(loc.getWorld()).getName());
        /*if(sabotage instanceof LightsSabotage) {
            ((LightsSabotage) sabotage).execute(player);
        }*/
    }
}
