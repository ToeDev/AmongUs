package org.toedev.amongus.tasks;

import org.bukkit.Location;
import org.toedev.amongus.map.Map;

public abstract class AbstractTask {

    private final String name;
    private final Map map;
    private final Location location;
    private Location taskAreaMinLocation;
    private Location taskAreaMaxLocation;
    private boolean inUse;

    public AbstractTask(String name, Map map, Location location, Location taskAreaMinLocation, Location taskAreaMaxLocation) {
        this.name = name;
        this.map = map;
        this.location = location;
        this.taskAreaMinLocation = taskAreaMinLocation;
        this.taskAreaMaxLocation = taskAreaMaxLocation;
        this.inUse = false;
    }

    public String getName() {
        return name;
    }

    public Map getMap() {
        return map;
    }

    public Location getLocation() {
        return location;
    }

    public Location getTaskAreaMinLocation() {
        if(taskAreaMinLocation == null || taskAreaMinLocation.getWorld() == null) {
            return null;
        }
        return taskAreaMinLocation;
    }

    public Location getTaskAreaMaxLocation() {
        if(taskAreaMaxLocation == null || taskAreaMaxLocation.getWorld() == null) {
            return null;
        }
        return taskAreaMaxLocation;
    }

    public void setTaskAreaMinLocation(Location location) {
        taskAreaMinLocation = location;
    }

    public void setTaskAreaMaxLocation(Location location) {
        taskAreaMaxLocation = location;
    }

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean status) {
        inUse = status;
    }
}
