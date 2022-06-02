package org.toedev.amongus.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.toedev.amongus.handlers.GameHandler;
import org.toedev.amongus.handlers.NPCHandler;
import org.toedev.amongus.map.MapManager;
import org.toedev.amongus.tasks.TaskManager;

import java.util.Objects;

public class TestCommand {

    private final GameHandler gameHandler;
    private final NPCHandler npcHandler;
    private final TaskManager taskManager;
    private final MapManager mapManager;

    public TestCommand(GameHandler gameHandler, NPCHandler npcHandler, TaskManager taskManager, MapManager mapManager) {
        this.gameHandler = gameHandler;
        this.npcHandler = npcHandler;
        this.taskManager = taskManager;
        this.mapManager = mapManager;
    }

    public void execute(final CommandSender sender, String[] args) {
        if(args[1].equals("spawn")) {
            npcHandler.spawnNPC(Objects.requireNonNull(Bukkit.getPlayer(sender.getName())));
        } else if(args[1].equals("despawn")) {
            npcHandler.despawnAllNPCs();
        } else if(args[1].equals("startmap")) {
            gameHandler.addPlayerToMap(mapManager.getMap("polus"), Bukkit.getPlayer(sender.getName()));
        } else if(args[1].equals("givetasks")) {
            gameHandler.givePlayerRandomTasks(mapManager.getMap("polus"), Bukkit.getPlayer(sender.getName()), 6);
        }
    }
}
