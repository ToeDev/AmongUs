package org.toedev.amongus.tasks;

import org.toedev.amongus.map.Map;

import java.util.HashMap;
import java.util.Set;

public class TaskManager {

    private final java.util.Map<Map, Set<AbstractTask>> allTasks;

    public TaskManager() {
        this.allTasks = new HashMap<>();
    }

    public AbstractTask getTask(Map map, String taskName) {
        if(allTasks.get(map) != null) {
            for(AbstractTask abstractTask : allTasks.get(map)) {
                if(abstractTask.getTaskName().equalsIgnoreCase(taskName)) {
                    return abstractTask;
                }
            }
            return null;
        }
        return null;
    }
}
