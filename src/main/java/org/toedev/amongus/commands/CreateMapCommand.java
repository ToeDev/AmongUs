package org.toedev.amongus.commands;

import org.bukkit.command.CommandSender;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.map.MapManager;


public class CreateMapCommand {

    private final MapManager mapManager;

    public CreateMapCommand(MapManager mapManager) {
        this.mapManager = mapManager;
    }

    public void execute(final CommandSender sender, String[] args) {
        if(mapManager.getMap(args[1]) == null) {
            mapManager.addMap(new Map(args[1]));
            sender.sendMessage(args[1] + " map created");
        } else {
            sender.sendMessage("already a map by the name " + args[1]);
        }
    }
}
