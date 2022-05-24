package org.toedev.amongus.tasks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.toedev.amongus.Prefix;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.map.MapManager;
import org.toedev.amongus.sql.Utility;
import org.toedev.amongus.tasks.tasks.WiresTask;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {

    private final Utility utility;
    private final MapManager mapManager;

    private final java.util.Map<Map, List<AbstractTask>> allTasks;

    private final ChatColor purple = ChatColor.LIGHT_PURPLE;
    private final ChatColor gold = ChatColor.GOLD;
    private final ChatColor red = ChatColor.RED;

    public TaskManager(MapManager mapManager, Utility utility) {
        this.utility = utility;
        this.mapManager = mapManager;
        this.allTasks = new HashMap<>();
        importTasksFromDB(utility);
    }

    public void importTasksFromDB(Utility utility) {
        try {
            ResultSet taskSet = utility.getAllTasks();
            if(taskSet == null) return;
            while(taskSet.next()) {
                Location taskLocation = new Location(Bukkit.getWorld(taskSet.getString("taskWorld")), taskSet.getDouble("taskX"), taskSet.getDouble("taskY"), taskSet.getDouble("taskZ"));
                String taskName = taskSet.getString("taskName");
                Map map = mapManager.getMap(taskSet.getString("mapName"));
                if(taskName.equalsIgnoreCase("wires")) {
                    WiresTask wTask = new WiresTask(Tasks.taskNames.get(WiresTask.class), taskLocation);
                    if(allTasks.get(map) != null) {
                        allTasks.get(map).add(wTask);
                    } else {
                        ArrayList<AbstractTask> tasks = new ArrayList<>();
                        tasks.add(wTask);
                        allTasks.put(map, tasks);
                    }
                }
                Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Task: \"" + taskName + "\" for Map: \"" + map.getName() + "\" imported from the DB");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<AbstractTask> getAllTasks(Map map) {
        if(allTasks.get(map) != null) {
            return allTasks.get(map);
        }
        return null;
    }

    public WiresTask getWiresTask(Map map) {
        if(allTasks.get(map) != null) {
            for(AbstractTask task : allTasks.get(map)) {
                if(task instanceof WiresTask) {
                    return (WiresTask) task;
                }
            }
            return null;
        }
        return null;
    }

    public void addWiresTask(Map map, Location location) throws SQLException {
        WiresTask wTask = new WiresTask(Tasks.taskNames.get(WiresTask.class), location);
        if(allTasks.get(map) != null) {
            allTasks.get(map).add(wTask);
        } else {
            ArrayList<AbstractTask> tasks = new ArrayList<>();
            tasks.add(wTask);
            allTasks.put(map, tasks);
        }
        saveTask(map, wTask);
    }

    public boolean isLocationTaskLocation(Location location) {
        for(java.util.Map.Entry<Map, List<AbstractTask>> entry : allTasks.entrySet()) {
            for(AbstractTask task : getAllTasks(entry.getKey())) {
                if(task.getLocation().equals(location)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void saveTask(Map map, AbstractTask task) throws SQLException {
        utility.addTask(map, task);
        Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Task: \"" + task.getName() + "\" for Map: \"" + map.getName() + "\" added to DB");
    }
}
