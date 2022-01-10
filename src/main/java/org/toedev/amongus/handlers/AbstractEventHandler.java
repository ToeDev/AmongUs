package org.toedev.amongus.handlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.Materials;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.map.MapManager;

import java.util.logging.Logger;

public class AbstractEventHandler implements Listener {

    private final Logger logger;

    private final MapManager mapManager;
    private final GameHandler gameHandler;

    public AbstractEventHandler(AmongUs amongUs, MapManager mapManager, GameHandler gameHandler) {
        this.logger = amongUs.getLogger();
        this.mapManager = mapManager;
        this.gameHandler = gameHandler;
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
                event.getPlayer().sendMessage(map.getName() + " queue is full!");
                return;
            }
            if(!gameHandler.isPlayerInMapQueue(map, player)) {
                gameHandler.removePlayerFromAllMapQueues(player);
                gameHandler.addPlayerToMapQueue(map, player);
            }
        }
    }

    /*@EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) { //TODO ADD CHECK TO TP PLAYERS OUT OF MAP

    }*/

    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent event) {
        gameHandler.removePlayerFromAllMapQueues(event.getPlayer());
    }
}
