package org.toedev.amongus.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.toedev.amongus.Prefix;
import org.toedev.amongus.handlers.GameHandler;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.map.MapManager;

public class StopCommand {

    private final MapManager mapManager;
    private final GameHandler gameHandler;

    private final ChatColor purple = ChatColor.LIGHT_PURPLE;
    private final ChatColor gold = ChatColor.GOLD;
    private final ChatColor red = ChatColor.RED;

    public StopCommand(MapManager mapManager, GameHandler gameHandler) {
        this.mapManager = mapManager;
        this.gameHandler = gameHandler;
    }

    public void execute(final CommandSender sender, String[] args) {
        if(args.length < 2) {
            sender.sendMessage(Prefix.prefix + red + "Invalid usage!" + gold + "Try /au stop <mapname>");
            return;
        }
        if(mapManager.getMap(args[1]) == null) {
            sender.sendMessage(Prefix.prefix + red + "The map " + gold + args[1] + red + " doesn't exist!");
            return;
        }
        Map map = mapManager.getMap(args[1]);
        if(!map.isMapRunning()) {
            sender.sendMessage(Prefix.prefix + red + "The map " + gold + map.getName() + red + " has no game in-progress!");
            return;
        }

        gameHandler.stopGame(mapManager.getMap(args[1]));
        sender.sendMessage(Prefix.prefix + purple + "Game stopped on map " + gold + map.getName());
    }
}
