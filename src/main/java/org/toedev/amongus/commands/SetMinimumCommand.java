package org.toedev.amongus.commands;

import org.bukkit.command.CommandSender;
import org.toedev.amongus.map.MapManager;

import java.sql.SQLException;

public class SetMinimumCommand {

    private final MapManager mapManager;

    public SetMinimumCommand(MapManager mapManager) {
        this.mapManager = mapManager;
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
