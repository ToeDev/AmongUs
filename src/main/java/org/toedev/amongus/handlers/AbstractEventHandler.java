package org.toedev.amongus.handlers;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.Materials;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.map.MapManager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
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

    /*@EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if(event.isCancelled() || event.getTo() == null) return;
        if(Math.abs(event.getFrom().getBlockX() - event.getTo().getBlockX()) < 1 &&
                Math.abs(event.getFrom().getBlockY() - event.getTo().getBlockY()) < 1 &&
                Math.abs(event.getFrom().getBlockZ() - event.getTo().getBlockZ()) < 1) return;
        if(!mapManager.isPlayerInAnyStart(event.getPlayer())) return;
        for(Map map : mapManager.getStartsPlayerIsIn(event.getPlayer())) {
            if(map.isMapRunning()) {
                return;
            } else {
                mapManager.startMap(map);
            }
        }
    }*/

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
                subtractFromSign((Sign) event.getClickedBlock().getState());
            }
        } else {
            if(!gameHandler.isPlayerInAnyMapQueue(player)) {
                gameHandler.addPlayerToMapQueue(map, player);
                addToSign((Sign) event.getClickedBlock().getState());
            }
        }
    }

    private void addToSign(Sign sign) {
        int number = Integer.parseInt(sign.getLine(3));
        sign.setLine(3, String.valueOf(number + 1));
        sign.update();
    }

    private void subtractFromSign(Sign sign) {
        int number = Integer.parseInt(sign.getLine(3));
        sign.setLine(3, String.valueOf(number - 1));
        sign.update();
    }
}
