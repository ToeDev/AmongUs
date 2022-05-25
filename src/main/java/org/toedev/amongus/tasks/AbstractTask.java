package org.toedev.amongus.tasks;

import org.bukkit.Location;
import org.toedev.amongus.map.Map;

public abstract class AbstractTask {

    private final String name;
    private final Map map;
    private final Location location;

    public AbstractTask(String name, Map map, Location location) {
        this.name = name;
        this.map = map;
        this.location = location;
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
}
