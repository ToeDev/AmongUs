package org.toedev.amongus.handlers;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.map.MapManager;

import java.util.*;
import java.util.logging.Logger;


public class GameHandler {

    private final Logger logger;
    private final MapManager mapManager;

    private final java.util.Map<Map, Set<Player>> playersInMap;
    private final java.util.Map<Map, Set<Player>> playersInMapQueue;

    public GameHandler(AmongUs amongUs, MapManager mapManager) {
        this.logger = amongUs.getLogger();
        this.mapManager = mapManager;
        this.playersInMap = new HashMap<>();
        this.playersInMapQueue = new HashMap<>();
    }

    public Set<Player> getPlayersInMap(Map map) {
        if(playersInMap.isEmpty() || playersInMap.get(map) == null || playersInMap.get(map).isEmpty()) return null;
        return playersInMap.get(map);
    }

    public boolean isPlayerInAnyMap(Player player) {
        for(Map map : mapManager.getAllMaps()) {
            if(isPlayerInMap(map, player)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPlayerInMap(Map map, Player player) {
        return playersInMap.get(map) != null && playersInMap.get(map).contains(player);
    }

    public void addPlayerToMap(Map map, Player player) {
        if(playersInMap.get(map) == null) {
            Set<Player> players = new HashSet<>();
            players.add(player);
            playersInMap.put(map, players);
        } else {
            playersInMap.get(map).add(player);
        }
    }

    public void removePlayerFromMap(Map map, Player player) {
        if(playersInMap.get(map) != null) {
            playersInMap.get(map).remove(player);
        }
    }

    public Set<Player> getPlayersInMapQueue(Map map) {
        if(playersInMapQueue.isEmpty() || playersInMapQueue.get(map) == null || playersInMapQueue.get(map).isEmpty()) return null;
        return playersInMapQueue.get(map);
    }

    public boolean isPlayerInAnyMapQueue(Player player) {
        for(Map map : mapManager.getAllMaps()) {
            if(isPlayerInMapQueue(map, player)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPlayerInMapQueue(Map map, Player player) {
        return playersInMapQueue.get(map) != null && playersInMapQueue.get(map).contains(player);
    }

    public void addPlayerToMapQueue(Map map, Player player) {
        if(playersInMapQueue.get(map) == null) {
            Set<Player> players = new HashSet<>();
            players.add(player);
            playersInMapQueue.put(map, players);
        } else {
            playersInMapQueue.get(map).add(player);
        }
        addToSign((Sign) map.getMapStartSign().getBlock().getState());
        updateMapQueueHologram(map);
    }

    public void removePlayerFromMapQueue(Map map, Player player) {
        if(playersInMapQueue.get(map) != null) {
            playersInMapQueue.get(map).remove(player);
            subtractFromSign((Sign) map.getMapStartSign().getBlock().getState());
            updateMapQueueHologram(map);
        }
    }

    public void removePlayerFromAllMapQueues(Player player) {
        for(Map map : mapManager.getAllMaps()) {
            if(isPlayerInMapQueue(map, player)) {
                removePlayerFromMapQueue(map, player);
            }
        }
    }

    public void addToSign(Sign sign) {
        int number = Integer.parseInt(sign.getLine(3));
        sign.setLine(3, String.valueOf(number + 1));
        sign.update();
    }

    public void subtractFromSign(Sign sign) {
        int number = Integer.parseInt(sign.getLine(3));
        sign.setLine(3, String.valueOf(number - 1));
        sign.update();
    }

    public void updateMapQueueHologram(Map map) {
        List<String> lines = new ArrayList<>();
        for(Player player : playersInMapQueue.get(map)) {
            lines.add(ChatColor.GOLD + player.getName());
        }
        map.updateMapQueueHologram(lines);
    }
}
