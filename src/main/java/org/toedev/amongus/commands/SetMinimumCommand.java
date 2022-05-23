package org.toedev.amongus.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.toedev.amongus.Prefix;
import org.toedev.amongus.handlers.GameHandler;
import org.toedev.amongus.map.MapManager;

import java.sql.SQLException;

public class SetMinimumCommand {

    private final MapManager mapManager;
    private final GameHandler gameHandler;

    private final ChatColor purple = ChatColor.LIGHT_PURPLE;
    private final ChatColor gold = ChatColor.GOLD;
    private final ChatColor red = ChatColor.RED;

    public SetMinimumCommand(MapManager mapManager, GameHandler gameHandler) {
        this.mapManager = mapManager;
        this.gameHandler = gameHandler;
    }

    public void execute(final CommandSender sender, String[] args) throws SQLException {
        if(args.length != 3 || !isArgInt(args[1]) || Integer.parseInt(args[1]) > 20 || Integer.parseInt(args[1]) < 4) {
            sender.sendMessage(Prefix.prefix + red + "Invalid usage!" + gold + " Try /au setmaximum <4-20> <mapname>");
            return;
        }
        if(mapManager.getMap(args[2]) == null) {
            sender.sendMessage(Prefix.prefix + red + "The map " + gold + args[2] + red + " doesn't exist!");
            return;
        }
        /*if(gameHandler.getPlayersInMapQueue(mapManager.getMap(args[2])) != null || gameHandler.getPlayersInMapQueue(mapManager.getMap(args[2])).size() > Integer.parseInt(args[1])) {
            sender.sendMessage("You cannot lower the minimum players beneath the amount of players already in the queue!!");
            return;
        }*/

        mapManager.setMinPlayers(args[2].toLowerCase(), Integer.parseInt(args[1]));
        sender.sendMessage(Prefix.prefix + purple + "Minimum players set to " + gold + args[1] + purple + " on map " + gold + args[2]);
    }

    private boolean isArgInt(String arg1) {
        try {
            Integer.parseInt(arg1);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
