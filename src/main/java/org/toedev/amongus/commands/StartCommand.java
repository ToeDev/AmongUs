package org.toedev.amongus.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.toedev.amongus.Prefix;
import org.toedev.amongus.handlers.GameHandler;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.map.MapManager;

public class StartCommand {

    private final MapManager mapManager;
    private final GameHandler gameHandler;

    private final ChatColor purple = ChatColor.LIGHT_PURPLE;
    private final ChatColor gold = ChatColor.GOLD;
    private final ChatColor red = ChatColor.RED;

    public StartCommand(MapManager mapManager, GameHandler gameHandler) {
        this.mapManager = mapManager;
        this.gameHandler = gameHandler;
    }

    public void execute(final CommandSender sender, String[] args) {
        if(args.length < 2) {
            sender.sendMessage(Prefix.prefix + red + "Invalid usage!" + gold + "Try /au start <mapname>");
            return;
        }
        if(mapManager.getMap(args[1]) == null) {
            sender.sendMessage(Prefix.prefix + red + "The map " + gold + args[1] + red + " doesn't exist!");
            return;
        }
        Map map = mapManager.getMap(args[1]);
        if(map.isMapRunning()) {
            sender.sendMessage(Prefix.prefix + red + "The map " + gold + map.getName() + red + " is already running a game!");
            return;
        }
        if(gameHandler.getPlayersInMapQueue(map) == null || gameHandler.getPlayersInMapQueue(map).size() < map.getMinPlayers()) {
            sender.sendMessage(Prefix.prefix + red + "The map " + gold + map.getName() + red + " doesn't have enough players in the queue to start a game!");
            return;
        }

        gameHandler.startGame(mapManager.getMap(args[1]));
        sender.sendMessage(Prefix.prefix + purple + "Game started on map " + gold + map.getName());
    }
}
