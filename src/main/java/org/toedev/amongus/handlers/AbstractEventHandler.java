package org.toedev.amongus.handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.toedev.amongus.map.MapManager;

public class AbstractEventHandler {

    private final MapManager mapManager;

    public AbstractEventHandler(MapManager mapManager) {
        this.mapManager = mapManager;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if()
    }
}
