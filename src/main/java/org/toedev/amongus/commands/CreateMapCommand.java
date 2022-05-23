package org.toedev.amongus.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.toedev.amongus.Prefix;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.map.MapManager;


public class CreateMapCommand {

    private final MapManager mapManager;

    private final ChatColor purple = ChatColor.LIGHT_PURPLE;
    private final ChatColor gold = ChatColor.GOLD;
    private final ChatColor red = ChatColor.RED;

    public CreateMapCommand(MapManager mapManager) {
        this.mapManager = mapManager;
    }

    public void execute(final CommandSender sender, String[] args) {
        if(mapManager.getMap(args[1]) == null) {
            mapManager.addMap(new Map(args[1]));
            sender.sendMessage(Prefix.prefix + purple + "The map " + gold + args[1] + purple + " has been created");
        } else {
            sender.sendMessage(Prefix.prefix + red + "There is already a map by the name of " + gold + args[1] + red + "!");
        }
    }
}
