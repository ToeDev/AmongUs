package org.toedev.amongus.commands;

import org.bukkit.command.CommandSender;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.map.MapManager;

import java.util.Set;

public class ListMapsCommand {

    private final MapManager mapManager;

    public ListMapsCommand(MapManager mapManager) {
        this.mapManager = mapManager;
    }

    public void execute(final CommandSender sender, String[] args) {
        Set<Map> maps = mapManager.getAllMaps();
        if(maps == null || maps.size() <= 0) {
            sender.sendMessage("no maps");
        } else {
            sender.sendMessage("Here are all maps:");
            for(Map map : maps) {
                sender.sendMessage(map.getName());
            }
        }
    }
}
