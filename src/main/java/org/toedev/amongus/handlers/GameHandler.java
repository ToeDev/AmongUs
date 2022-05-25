package org.toedev.amongus.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.Prefix;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.map.MapManager;
import org.toedev.amongus.tasks.AbstractTask;
import org.toedev.amongus.tasks.TaskManager;

import java.util.*;


public class GameHandler {

    private final Location lobbySpawn;
    private final Integer mapQueueCountdown;
    private final HashMap<Map, List<Integer>> mapQueueCountdownTaskIds;

    private final BukkitScheduler scheduler;
    private final AmongUs amongUs;
    private final MapManager mapManager;
    private final TaskManager taskManager;

    private final java.util.Map<Map, Set<Player>> playersInMap;
    private final java.util.Map<Map, Set<Player>> playersInMapQueue;

    private final java.util.Map<Player, List<AbstractTask>> playerTasks;

    private final ChatColor purple = ChatColor.LIGHT_PURPLE;
    private final ChatColor gold = ChatColor.GOLD;
    private final ChatColor red = ChatColor.RED;

    public GameHandler(AmongUs amongUs, MapManager mapManager, TaskManager taskManager) {
        this.lobbySpawn = new Location(Bukkit.getWorld("amongus_skeld"), 2, 65, 36); //TODO THIS SHOULD BE A CONFIG ITEM
        this.mapQueueCountdown = 30; //TODO THIS SHOULD BE A CONFIG ITEM
        this.mapQueueCountdownTaskIds = new HashMap<>();

        this.scheduler = amongUs.getServer().getScheduler();
        this.amongUs = amongUs;
        this.mapManager = mapManager;
        this.taskManager = taskManager;
        this.playersInMap = new HashMap<>();
        this.playersInMapQueue = new HashMap<>();

        this.playerTasks = new HashMap<>();
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

    public Map getMapPlayerIsIn(Player player) {
        for(Map map : mapManager.getAllMaps()) {
            if(playersInMap.get(map) != null && playersInMap.get(map).contains(player)) {
                return map;
            }
        }
        return null;
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
        updateSignCount(map, (Sign) map.getMapStartSign().getBlock().getState());
        updateMapQueueHologram(map);
    }

    public void removePlayerFromMapQueue(Map map, Player player) {
        if(playersInMapQueue.get(map) != null) {
            playersInMapQueue.get(map).remove(player);
            updateSignCount(map, (Sign) map.getMapStartSign().getBlock().getState());
            updateMapQueueHologram(map);
            if(isMapQueueEmpty(map)) stopMapQueueTimer(map);
            if(map.isMapRunning()) mapQueueGameRunning(map);
        }
    }

    public void removePlayerFromAllMapQueues(Player player) {
        for(Map map : mapManager.getAllMaps()) {
            if(isPlayerInMapQueue(map, player)) {
                removePlayerFromMapQueue(map, player);
            }
        }
    }

    public void updateSignCount(Map map, Sign sign) {
        sign.setLine(3, playersInMapQueue.get(map).size() + " / " + map.getMaxPlayers());
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

    public void mapQueueGameRunning(Map map) {
        List<String> lines = new ArrayList<>();
        String[] mapName = map.getName().split(" ");
        StringBuilder mapNameFinal = new StringBuilder("");
        for(String split : mapName) {
            mapNameFinal.append(split.substring(0, 1).toUpperCase()).append(split.substring(1).toLowerCase());
        }
        lines.add(ChatColor.BLUE + mapNameFinal.toString() + " Queue: " + ChatColor.GOLD + "Game In-Progress");
        map.updateMapQueueHologram(lines);
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
                    player.sendMessage(Prefix.prefix + purple + "Among Us on " + gold + mapNameFinal + purple + " starts in " + seconds + " second" + (seconds != 1 ? "s" : "") + "!");
                }
            }
            if(seconds == 1) {
                scheduler.runTaskLater(amongUs, () -> {
                    if(playersInMapQueue.get(map).size() >= map.getMinPlayers() && playersInMapQueue.get(map).size() <= map.getMaxPlayers()) {
                        startGame(map);
                    } else if(playersInMapQueue.get(map).size() < map.getMinPlayers()) {
                        for(Player player : playersInMapQueue.get(map)) {
                            if(player.isOnline() && isPlayerInMapQueue(map, player)) {
                                player.sendMessage(Prefix.prefix + red + "Not enough players! " + gold + map.getName() + " has a minimum of " + gold + map.getMinPlayers() + red + " players needed to start a game!");
                            }
                            removePlayerFromMapQueue(map, player);
                        }

                    } else if(playersInMapQueue.get(map).size() > map.getMaxPlayers()) {
                        for(Player player : playersInMapQueue.get(map)) {
                            if(player.isOnline() && isPlayerInMapQueue(map, player)) {
                                player.sendMessage(Prefix.prefix + red + "Too many players! " + gold + map.getName() + " has a maximum of " + gold + map.getMaxPlayers() + red + " players in a game!");
                            }
                            removePlayerFromMapQueue(map, player);
                        }
                    }
                }, 20);
            }
        }
    }

    public void startGame(Map map) {
        map.setMapRunning(true);
        String[] mapName = map.getName().split(" ");
        StringBuilder mapNameFinal = new StringBuilder();
        for(String split : mapName) {
            mapNameFinal.append(split.substring(0, 1).toUpperCase()).append(split.substring(1).toLowerCase());
        }
        for(Player player : playersInMapQueue.get(map)) {
            addPlayerToMap(map, player);
            givePlayerRandomTasks(map, player, 1);
            player.teleport(map.getMapSpawn());
            player.sendMessage(Prefix.prefix + purple + "Among Us game started on " + gold + mapNameFinal + purple + "!");
            removePlayerFromMapQueue(map, player);
        }
    }

    public void stopGame(Map map) {
        for(Player player : playersInMap.get(map)) {
            player.teleport(lobbySpawn);
        }
        map.setMapRunning(false);
        updateMapQueueHologram(map);
    }

    public void givePlayerRandomTasks(Map map, Player player, int tasks) {
        while(tasks > 0) {
            Random random = new Random();
            int r = random.ints(0, taskManager.getAllTasks(map).size()).findFirst().getAsInt();
            AbstractTask task = taskManager.getAllTasks(map).get(r);
            List<AbstractTask> pTasks;
            if(playerTasks.get(player) == null) {
                pTasks = new ArrayList<>();
            } else {
                pTasks = playerTasks.get(player);
            }
            pTasks.add(task);
            playerTasks.put(player, pTasks);
            tasks--;
        }
    }

    public List<AbstractTask> getPlayerTasks(Player player) {
        return playerTasks.get(player);
    }

    public void completePlayerTask(Player player, AbstractTask task) {
        if(playerTasks.get(player) != null) {
            playerTasks.get(player).remove(task);
        }
    }

    public int totalTasksLeft(Map map) {
        int i = 0;
        for(java.util.Map.Entry<Player, List<AbstractTask>> entry : playerTasks.entrySet()) {
            if(isPlayerInMap(map, entry.getKey())) {
                i = i + playerTasks.get(entry.getKey()).size();
            }
        }
        return i;
    }

    public int playerTasksLeft(Player player) {
        int i = 0;
        if(playerTasks.get(player) != null) {
            i = i + playerTasks.get(player).size();
        }
        return i;
    }
}
