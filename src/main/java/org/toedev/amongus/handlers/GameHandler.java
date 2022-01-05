package org.toedev.amongus.handlers;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.map.MapManager;

import java.util.*;
import java.util.logging.Logger;


public class GameHandler {

    private final Integer mapQueueCountdown;
    private final HashMap<Map, List<Integer>> mapQueueCountdownTaskIds;

    private final BukkitScheduler scheduler;
    private final AmongUs amongUs;
    private final Logger logger;
    private final MapManager mapManager;

    private final java.util.Map<Map, Set<Player>> playersInMap;
    private final java.util.Map<Map, Set<Player>> playersInMapQueue;

    public GameHandler(AmongUs amongUs, MapManager mapManager) {
        this.mapQueueCountdown = 30;
        this.mapQueueCountdownTaskIds = new HashMap<>();

        this.scheduler = amongUs.getServer().getScheduler();
        this.amongUs = amongUs;
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

    public boolean isMapQueueEmpty(Map map) {
        return playersInMapQueue.get(map) == null || playersInMapQueue.get(map).isEmpty();
    }

    public void addPlayerToMapQueue(Map map, Player player) {
        if(isMapQueueEmpty(map)) startMapQueueTimer(map);
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
            if(isMapQueueEmpty(map)) stopMapQueueTimer(map);
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
        String[] mapName = map.getName().split(" ");
        StringBuilder mapNameFinal = new StringBuilder("");
        for(String split : mapName) {
            mapNameFinal.append(split.substring(0, 1).toUpperCase()).append(split.substring(1).toLowerCase());
        }
        lines.add(ChatColor.BLUE + mapNameFinal.toString() + " Queue:");
        for(Player player : playersInMapQueue.get(map)) {
            lines.add(ChatColor.GOLD + player.getName());
        }
        map.updateMapQueueHologram(lines);
    }

    public void startMapQueueTimer(Map map) {
        String[] mapName = map.getName().split(" ");
        StringBuilder mapNameFinal = new StringBuilder();
        for(String split : mapName) {
            mapNameFinal.append(split.substring(0, 1).toUpperCase()).append(split.substring(1).toLowerCase());
        }
        List<Integer> taskIds = new ArrayList<>();
        for(int i = 0; i < mapQueueCountdown; i++) {
            int countdown = mapQueueCountdown - i;
            taskIds.add(scheduler.runTaskLater(amongUs, () -> mapQueueTimerMessage(map, mapNameFinal.toString(), countdown), 20L * (i + 1)).getTaskId());
        }
        mapQueueCountdownTaskIds.put(map, taskIds);
    }

    public void stopMapQueueTimer(Map map) {
        for(Integer taskId : mapQueueCountdownTaskIds.get(map)) {
            scheduler.cancelTask(taskId);
        }
    }

    private void mapQueueTimerMessage(Map map, String mapNameFinal, int seconds) {
        List<String> lines = new ArrayList<>();
        lines.add(ChatColor.BLUE + mapNameFinal + " Queue: " + seconds);
        for(Player player : playersInMapQueue.get(map)) {
            lines.add(ChatColor.GOLD + player.getName());
        }
        map.updateMapQueueHologram(lines);
        if((seconds % 10 == 0) || seconds <= 5) {
            for(Player player : playersInMapQueue.get(map)) {
                if(player.isOnline() && isPlayerInMapQueue(map, player)) {
                    player.sendMessage("Among Us on " + mapNameFinal + " starts in " + seconds + " second" + (seconds != 1 ? "s" : "") + "!");
                    if(seconds == 1) {
                        scheduler.runTaskLater(amongUs, () -> {
                            if(player.isOnline() && isPlayerInMapQueue(map, player)) {
                                player.teleport(map.getMapSpawn());
                                player.sendMessage("Among Us game started on " + mapNameFinal + "!");
                                removePlayerFromMapQueue(map, player);
                            }
                        }, 20);
                    }
                }
            }
        }
    }
}
