package org.toedev.amongus.sabotages;

import org.bukkit.Location;
import org.toedev.amongus.map.Map;

import java.util.List;

public abstract class AbstractSabotage {

    private final String name;
    private final Map map;
    private final Location location;
    private Location optionalLocation;
    private Location optionalLocation2;
    private Location optionalLocation3;

    public AbstractSabotage(String name, Map map, Location location, Location optionalLocation, Location optionalLocation2, Location optionalLocation3) {
        this.name = name;
        this.map = map;
        this.location = location;
        this.optionalLocation = optionalLocation;
        this.optionalLocation2 = optionalLocation2;
        this.optionalLocation3 = optionalLocation3;
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

    public Location getOptionalLocation2() {
        return optionalLocation2;
    }

    public Location getOptionalLocation3() {
        return optionalLocation3;
    }

    public void setOptionalLocation(Location optionalLocation) {
        this.optionalLocation = optionalLocation;
    }

    public void setOptionalLocation2(Location optionalLocation2) {
        this.optionalLocation2 = optionalLocation2;
    }

    public void setOptionalLocation3(Location optionalLocation3) {
        this.optionalLocation3 = optionalLocation3;
    }
}
