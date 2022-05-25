package org.toedev.amongus.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.toedev.amongus.Materials;
import org.toedev.amongus.Prefix;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.map.MapManager;
import org.toedev.amongus.tasks.TaskManager;

import java.util.Objects;

public class AbstractEventHandler implements Listener {

    private final MapManager mapManager;
    private final GameHandler gameHandler;
    private final TaskManager taskManager;

    private final ChatColor purple = ChatColor.LIGHT_PURPLE;
    private final ChatColor gold = ChatColor.GOLD;
    private final ChatColor red = ChatColor.RED;

    public AbstractEventHandler(MapManager mapManager, GameHandler gameHandler, TaskManager taskManager) {
        this.mapManager = mapManager;
        this.gameHandler = gameHandler;
        this.taskManager = taskManager;
    }

    @EventHandler
    public void onSignInteract(PlayerInteractEvent event) {
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock() == null) return;
        if(!Materials.signMaterials.contains(event.getClickedBlock().getType())) return;
        if(!mapManager.getAllMapStartSigns().contains(event.getClickedBlock().getLocation())) return;
        Map map = mapManager.getMapByStartSign(event.getClickedBlock().getLocation());
        Player player = event.getPlayer();
        if(player.isSneaking()) {
            if(gameHandler.isPlayerInMapQueue(map, player)) {
                gameHandler.removePlayerFromMapQueue(map, player);
            }
        } else {
            if(gameHandler.getPlayersInMapQueue(map) != null && gameHandler.getPlayersInMapQueue(map).size() + 1 > map.getMaxPlayers()) {
                event.getPlayer().sendMessage(Prefix.prefix + gold + map.getName() + purple + " queue is full!");
                return;
            }
            if(!gameHandler.isPlayerInMapQueue(map, player)) {
                gameHandler.removePlayerFromAllMapQueues(player);
                gameHandler.addPlayerToMapQueue(map, player);
            }
        }
    }

    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(mapManager.isPlayerInAnyMap(player)) {
            for(Map map : mapManager.getMapsPlayerIsIn(player)) {
                if(!gameHandler.isPlayerInMap(map, player)) {
                    mapManager.removePlayerFromMap(player, map);
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + gold + player.getName() + red + " logged in at map " + gold + map.getName() + red + ", but they aren't playing. Proceeding to remove from map.");
                }
            }
        }
    }

    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent event) {
        gameHandler.removePlayerFromAllMapQueues(event.getPlayer());
    }
}
