package org.toedev.amongus.commands;

import org.bukkit.command.CommandSender;
import org.toedev.amongus.handlers.GameHandler;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.map.MapManager;

public class StartCommand {

    private final MapManager mapManager;
    private final GameHandler gameHandler;

    public StartCommand(MapManager mapManager, GameHandler gameHandler) {
        this.mapManager = mapManager;
        this.gameHandler = gameHandler;
    }

    public void execute(final CommandSender sender, String[] args) {
        if(args.length < 2) {
            sender.sendMessage("syntax this");
            return;
        }
        if(mapManager.getMap(args[1]) == null) {
            sender.sendMessage("map doesn't exist");
            return;
        }
        Map map = mapManager.getMap(args[1]);
        if(map.isMapRunning()) {
            sender.sendMessage("map is already running!");
            return;
        }
        if(gameHandler.getPlayersInMapQueue(map) == null || gameHandler.getPlayersInMapQueue(map).size() < map.getMinPlayers()) {
            sender.sendMessage("not enough players in map queue!");
            return;
        }

        gameHandler.startGame(mapManager.getMap(args[1]));
        sender.sendMessage("map started");
    }
}
