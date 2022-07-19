package org.toedev.amongus.commands;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.toedev.amongus.Prefix;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.map.MapManager;
import org.toedev.amongus.tasks.AbstractTask;
import org.toedev.amongus.tasks.TaskManager;

public class MapInfoCommand {

    private final MapManager mapManager;
    private final TaskManager taskManager;

    private final ChatColor purple = ChatColor.LIGHT_PURPLE;
    private final ChatColor gold = ChatColor.GOLD;
    private final ChatColor red = ChatColor.RED;

    public MapInfoCommand(MapManager mapManager, TaskManager taskManager) {
        this.mapManager = mapManager;
        this.taskManager = taskManager;
    }

    public void execute(final CommandSender sender, String[] args) {
        if(args.length < 2) return;
        Map map = mapManager.getMap(args[1]);
        if(map == null) {
            sender.sendMessage(Prefix.prefix + red + "The map " + gold + args[1] + red + " doesn't exist!");
        } else {
            sender.sendMessage(Prefix.prefix + purple + "---------------" + gold + map.getName() + purple + "---------------");
            sender.sendMessage(Prefix.prefix + purple + "World: " + gold + map.getMapSpawn().getWorld().getName());
            TextComponent mapLoc = new TextComponent(Prefix.prefix + purple + "Map Location: ");
            Location mapMin = map.getMapMinCorner();
            Location mapMax = map.getMapMaxCorner();
            String mapMinString = mapMin.getBlockX() + "," + mapMin.getBlockY() + "," + mapMin.getBlockZ();
            String mapMaxString = mapMax.getBlockX() + "," + mapMax.getBlockY() + "," + mapMax.getBlockZ();
            TextComponent mapLocClick = new TextComponent(gold + mapMinString + purple + " - " + gold + mapMaxString);
            mapLoc.addExtra(mapLocClick);
            sender.spigot().sendMessage(mapLoc);
            TextComponent meetingLoc = new TextComponent(Prefix.prefix + purple + "Meeting Location: ");
            Location meetingMin = map.getMeetingMinCorner();
            Location meetingMax = map.getMeetingMaxCorner();
            String meetingMinString = meetingMin.getBlockX() + "," + meetingMin.getBlockY() + "," + meetingMin.getBlockZ();
            String meetingMaxString = meetingMax.getBlockX() + "," + meetingMax.getBlockY() + "," + meetingMax.getBlockZ();
            TextComponent meetingLocClick = new TextComponent(gold + meetingMinString + purple + " - " + gold + meetingMaxString);
            meetingLoc.addExtra(meetingLocClick);
            sender.spigot().sendMessage(meetingLoc);
            TextComponent mapSpawn = new TextComponent(Prefix.prefix + purple + "Map Spawn: ");
            Location mapSpawnLoc = map.getMapSpawn();
            String mapSpawnLocString = mapSpawnLoc.getBlockX() + "," + mapSpawnLoc.getBlockY() + "," + mapSpawnLoc.getBlockZ();
            TextComponent mapSpawnClick = new TextComponent(gold + mapSpawnLocString);
            mapSpawn.addExtra(mapSpawnClick);
            sender.spigot().sendMessage(mapSpawn);

            sender.sendMessage(Prefix.prefix + purple + "---Tasks---");
            for(AbstractTask task : taskManager.getAllTasks(map)) {
                sender.sendMessage(Prefix.prefix + purple + " - " + gold + task.getName());
            }
        }
    }
}
