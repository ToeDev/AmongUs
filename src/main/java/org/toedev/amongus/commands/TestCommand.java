package org.toedev.amongus.commands;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.toedev.amongus.handlers.GameHandler;
import org.toedev.amongus.handlers.NPCHandler;
import org.toedev.amongus.map.MapManager;
import org.toedev.amongus.players.Imposter;
import org.toedev.amongus.sabotages.SabotageManager;
import org.toedev.amongus.sabotages.sabotages.LightsSabotage;
import org.toedev.amongus.tasks.TaskManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TestCommand {

    private final GameHandler gameHandler;
    private final NPCHandler npcHandler;
    private final TaskManager taskManager;
    private final SabotageManager sabotageManager;
    private final MapManager mapManager;

    public TestCommand(GameHandler gameHandler, NPCHandler npcHandler, TaskManager taskManager, SabotageManager sabotageManager, MapManager mapManager) {
        this.gameHandler = gameHandler;
        this.npcHandler = npcHandler;
        this.taskManager = taskManager;
        this.sabotageManager = sabotageManager;
        this.mapManager = mapManager;
    }

    public void execute(final CommandSender sender, String[] args) {
        if(args[1].equals("spawn")) {
            npcHandler.spawnNPC(Objects.requireNonNull(Bukkit.getPlayer(sender.getName())));
        } else if(args[1].equals("despawn")) {
            npcHandler.despawnAllNPCs();
        }
        /*else if(args[1].equals("startmap")) {
            gameHandler.addPlayerToMap(mapManager.getMap("skeld"), Bukkit.getPlayer(sender.getName()));
        } else if(args[1].equals("givetasks")) {
            gameHandler.givePlayerRandomTasks(mapManager.getMap("skeld"), Bukkit.getPlayer(sender.getName()), 12);
        }*/
        else if(args[1].equals("startmap")) {
            gameHandler.addPlayerToMap(mapManager.getMap("skeld"), new Imposter(Bukkit.getPlayer(sender.getName()), Color.AQUA, mapManager.getMap("skeld")));
        } else if(args[1].equals("startsab")) {
            //gameHandler.givePlayerRandomTasks(mapManager.getMap("skeld"), Bukkit.getPlayer(sender.getName()), 12);
            List<Player> p = new ArrayList<>();
            p.add((Player) sender);
            sabotageManager.getDoorSabotage(mapManager.getMap("skeld")).execute((Player) sender);
        }
    }
}
