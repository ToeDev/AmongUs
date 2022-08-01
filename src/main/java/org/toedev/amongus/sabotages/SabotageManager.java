package org.toedev.amongus.sabotages;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.Prefix;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.map.MapManager;
import org.toedev.amongus.sql.Utility;
import org.toedev.amongus.tasks.AbstractTask;
import org.toedev.amongus.tasks.Tasks;
import org.toedev.amongus.tasks.tasks.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SabotageManager {

    private final AmongUs amongUs;

    private final Utility utility;
    private final MapManager mapManager;

    private final java.util.Map<Map, List<AbstractSabotage>> allSabotages;

    private final ChatColor purple = ChatColor.LIGHT_PURPLE;
    private final ChatColor gold = ChatColor.GOLD;
    private final ChatColor red = ChatColor.RED;

    public SabotageManager(AmongUs amongUs, MapManager mapManager, Utility utility) {
        this.amongUs = amongUs;
        this.utility = utility;
        this.mapManager = mapManager;
        this.allSabotages = new HashMap<>();
        //importSabotagesFromDB(utility);
    }

    /*public void importTasksFromDB(Utility utility) {
        try {
            ResultSet taskSet = utility.getAllTasks();
            if(taskSet == null) return;
            while(taskSet.next()) {
                Location taskLocation = new Location(Bukkit.getWorld(taskSet.getString("taskWorld")), taskSet.getDouble("taskX"), taskSet.getDouble("taskY"), taskSet.getDouble("taskZ"));
                Location taskAreaMinLocation = new Location(Bukkit.getWorld(taskSet.getString("taskAreaMinWorld")), taskSet.getDouble("taskAreaMinX"), taskSet.getDouble("taskAreaMinY"), taskSet.getDouble("taskAreaMinZ"));
                Location taskAreaMaxLocation = new Location(Bukkit.getWorld(taskSet.getString("taskAreaMaxWorld")), taskSet.getDouble("taskAreaMaxX"), taskSet.getDouble("taskAreaMaxY"), taskSet.getDouble("taskAreaMaxZ"));
                Location teleportLocation = new Location(Bukkit.getWorld(taskSet.getString("taskTeleportWorld")), taskSet.getDouble("taskTeleportX"), taskSet.getDouble("taskTeleportY"), taskSet.getDouble("taskTeleportZ"));
                String taskName = taskSet.getString("taskName");
                Map map = mapManager.getMap(taskSet.getString("mapName"));
                if(taskName.equalsIgnoreCase("lights")) {
                    WiresTask wTask = new WiresTask(Tasks.taskNames.get(WiresTask.class), map, taskLocation, taskAreaMinLocation, taskAreaMaxLocation, teleportLocation);
                    if(allTasks.get(map) != null) {
                        allTasks.get(map).add(wTask);
                    } else {
                        ArrayList<AbstractTask> tasks = new ArrayList<>();
                        tasks.add(wTask);
                        allTasks.put(map, tasks);
                    }
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Task: " + gold + taskName + purple + " for Map: " + gold + map.getName() + purple + " imported from the DB");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
}
