package org.toedev.amongus.handlers;

import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.toedev.amongus.map.MapManager;
import org.toedev.amongus.tasks.TaskManager;

public class TaskHandler implements Listener {

    private final MapManager mapManager;
    private final GameHandler gameHandler;
    private final TaskManager taskManager;

    private final ChatColor purple = ChatColor.LIGHT_PURPLE;
    private final ChatColor gold = ChatColor.GOLD;
    private final ChatColor red = ChatColor.RED;

    public TaskHandler(MapManager mapManager, GameHandler gameHandler, TaskManager taskManager) {
        this.mapManager = mapManager;
        this.gameHandler = gameHandler;
        this.taskManager = taskManager;
    }
}
