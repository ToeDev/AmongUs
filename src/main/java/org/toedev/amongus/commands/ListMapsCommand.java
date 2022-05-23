package org.toedev.amongus.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.map.MapManager;

import java.util.Set;

public class ListMapsCommand {

    private final MapManager mapManager;

    private final ChatColor purple = ChatColor.LIGHT_PURPLE;
    private final ChatColor gold = ChatColor.GOLD;
    private final ChatColor red = ChatColor.RED;

    public ListMapsCommand(MapManager mapManager) {
        this.mapManager = mapManager;
    }

    public void execute(final CommandSender sender, String[] args) {
        Set<Map> maps = mapManager.getAllMaps();
        if(maps == null || maps.size() <= 0) {
            sender.sendMessage(red + "No maps found!");
        } else {
            sender.sendMessage(purple + "----------" + gold + "Maps" + purple + "----------");
            for(Map map : maps) {
                sender.sendMessage(purple + " - " + gold + map.getName());
            }
        }
    }
}
