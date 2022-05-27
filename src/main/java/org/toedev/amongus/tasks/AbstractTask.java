package org.toedev.amongus.tasks;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.toedev.amongus.map.Map;

public abstract class AbstractTask {

    private final String name;
    private final Map map;
    private final Location location;
    private boolean inUse;

    public AbstractTask(String name, Map map, Location location) {
        this.name = name;
        this.map = map;
        this.location = location;
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

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean status) {
        inUse = status;
    }
}
