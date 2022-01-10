package org.toedev.amongus.tasks;

import org.bukkit.Location;

public abstract class AbstractTask {

    private final String name;
    private final Location location;

    public AbstractTask(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }
}
