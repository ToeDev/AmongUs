package org.toedev.amongus.tasks;

import org.bukkit.Location;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.tasks.tasks.WiresTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {

    private final java.util.Map<Map, List<AbstractTask>> allTasks;

    public TaskManager() {
        this.allTasks = new HashMap<>();
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

    public void addWiresTask(Map map, Location location) {
        if(allTasks.get(map) != null) {
            allTasks.get(map).add(new WiresTask(Tasks.taskNames.get(WiresTask.class), location));
        } else {
            ArrayList<AbstractTask> tasks = new ArrayList<>();
            tasks.add(new WiresTask(Tasks.taskNames.get(WiresTask.class), location));
            allTasks.put(map, tasks);
        }
    }
}
