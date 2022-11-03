package org.toedev.amongus.sabotages;

import org.bukkit.Location;
import org.toedev.amongus.map.Map;

import java.util.List;

public abstract class AbstractSabotage {

    private final String name;
    private final Map map;
    private final Location location;
    private Location optionalLocation;

    public AbstractSabotage(String name, Map map, Location location, Location optionalLocation) {
        this.name = name;
        this.map = map;
        this.location = location;
        this.optionalLocation = optionalLocation;
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

    public Location getOptionalLocation() {
        return optionalLocation;
    }

    public void setOptionalLocation(Location optionalLocation) {
        this.optionalLocation = optionalLocation;
    }
}
