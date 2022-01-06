package org.toedev.amongus.commands;

import org.bukkit.command.CommandSender;
import org.toedev.amongus.handlers.GameHandler;
import org.toedev.amongus.map.MapManager;

import java.sql.SQLException;

public class SetMinimumCommand {

    private final MapManager mapManager;
    private final GameHandler gameHandler;

    public SetMinimumCommand(MapManager mapManager, GameHandler gameHandler) {
        this.mapManager = mapManager;
        this.gameHandler = gameHandler;
    }

    public void execute(final CommandSender sender, String[] args) throws SQLException {
        if(args.length != 3 || !isArgInt(args[1]) || Integer.parseInt(args[1]) > 20 || Integer.parseInt(args[1]) < 4) {
            sender.sendMessage("syntax this");
            return;
        }
        if(mapManager.getMap(args[2]) == null) {
            sender.sendMessage("map doesn't exist");
            return;
        }
        /*if(gameHandler.getPlayersInMapQueue(mapManager.getMap(args[2])).size() > Integer.parseInt(args[1])) {
            sender.sendMessage("You cannot lower the minimum players beneath the amount of players already in the queue!!");
            return;
        }*/

        mapManager.setMinPlayers(args[2].toLowerCase(), Integer.parseInt(args[1]));
        sender.sendMessage(args[2].toLowerCase() + " minimum players set to " + args[1]);
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
