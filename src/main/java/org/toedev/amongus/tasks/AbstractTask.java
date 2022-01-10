package org.toedev.amongus.tasks;

import org.bukkit.Location;

public abstract class AbstractTask {

    private final String taskName;
    private final Location location;

    public AbstractTask(String taskName, Location location) {
        this.taskName = taskName;
        this.location = location;
    }

    public String getTaskName() {
        return taskName;
    }

    public Location getLocation() {
        return location;
    }
}
