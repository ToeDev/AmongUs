package org.toedev.amongus.commands;

import org.bukkit.command.CommandSender;
import org.toedev.amongus.handlers.GameHandler;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.map.MapManager;

public class StopCommand {

    private final MapManager mapManager;
    private final GameHandler gameHandler;

    public StopCommand(MapManager mapManager, GameHandler gameHandler) {
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
        if(!map.isMapRunning()) {
            sender.sendMessage("map is not running!");
            return;
        }

        gameHandler.stopGame(mapManager.getMap(args[1]));
        sender.sendMessage("map started");
    }
}
