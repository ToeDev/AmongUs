package org.toedev.amongus.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.toedev.amongus.Prefix;
import org.toedev.amongus.map.MapManager;
import org.toedev.amongus.tasks.TaskManager;
import org.toedev.amongus.tasks.Tasks;

import java.sql.SQLException;
import java.util.List;

public class RemoveTaskCommand {

    private final MapManager mapManager;
    private final TaskManager taskManager;

    private final ChatColor purple = ChatColor.LIGHT_PURPLE;
    private final ChatColor gold = ChatColor.GOLD;
    private final ChatColor red = ChatColor.RED;

    public RemoveTaskCommand(MapManager mapManager, TaskManager taskManager) {
        this.mapManager = mapManager;
        this.taskManager = taskManager;
    }

    public void execute(final CommandSender sender, String[] args) throws SQLException {
        if(!Tasks.taskNames.containsValue(args[1].toLowerCase())) {
            sender.sendMessage(Prefix.prefix + red + "The task " + gold + args[1] + red + " isn't a valid option!");
            return;
        }
        if(mapManager.getMap(args[2]) == null) {
            sender.sendMessage(Prefix.prefix + red + "The map " + gold + args[2] + red + " doesn't exist!");
            return;
        }
        if(!taskManager.doesTaskExistInMap(mapManager.getMap(args[2]), args[1].toLowerCase())) {
            sender.sendMessage(Prefix.prefix + red + "The map " + gold + args[2] + red + " doesn't currently have task " + gold + args[1]);
            return;
        }

        taskManager.removeTask(mapManager.getMap(args[2]), args[1].toLowerCase());
        sender.sendMessage(Prefix.prefix + purple + "Task " + gold + args[1] + purple + " removed from map " + gold + args[2]);
    }
}
