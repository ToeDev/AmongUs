package org.toedev.amongus.tasks;

import org.bukkit.Location;
import org.bukkit.World;
import org.toedev.amongus.map.Map;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTask {

    private final String name;
    private final Map map;
    private final Location location;
    private final List<Location> taskAreaLocs;
    private Location teleportLocation;
    private boolean inUse;

    public AbstractTask(String name, Map map, Location location, Location taskAreaMinLocation, Location taskAreaMaxLocation, Location teleportLocation) {
        this.name = name;
        this.map = map;
        this.location = location;
        this.taskAreaLocs = new ArrayList<>();
        if(teleportLocation != null) this.teleportLocation = teleportLocation;
        if(taskAreaMinLocation != null) this.setTaskAreaCorners(taskAreaMinLocation, taskAreaMaxLocation);
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

    public List<Location> getTaskAreaLocs() {
        return taskAreaLocs;
    }

    public Location getTaskAreaMinLocation() {
        return findExtremeLoc(true);
    }

    public Location getTaskAreaMaxLocation() {
        return findExtremeLoc(false);
    }

    public Location getTeleportLocation() {
        return teleportLocation;
    }

    public void setTaskAreaCorners(Location taskAreaMinLocation, Location taskAreaMaxLocation) {
        taskAreaLocs.clear();
        taskAreaLocs.add(taskAreaMinLocation);
        taskAreaLocs.add(taskAreaMaxLocation);
        iterateTaskArea();
    }

    public void setTeleport(Location location) {
        teleportLocation = location;
    }

    private Location findExtremeLoc(boolean min) {
        if(taskAreaLocs.size() < 2) return null;

        Location out = taskAreaLocs.get(0);
        double extreme = out.getX() + out.getY() + out.getZ();
        for(Location l : taskAreaLocs) {
            double current = l.getX() + l.getY() + l.getZ();
            if(min ? current < extreme : current > extreme) {
                out = l;
                extreme = current;
            }
        }
        return out;
    }

    private void iterateTaskArea() {
        Location taskAreaMin = taskAreaLocs.remove(0);
        Location taskAreaMax = taskAreaLocs.remove(0);

        World w = taskAreaMin.getWorld();
        int xMin = taskAreaMin.getBlockX();
        int yMin = taskAreaMin.getBlockY();
        int zMin = taskAreaMin.getBlockZ();
        int xMax = taskAreaMax.getBlockX();
        int yMax = taskAreaMax.getBlockY();
        int zMax = taskAreaMax.getBlockZ();
        int xMinFin, yMinFin, zMinFin;
        int xMaxFin, yMaxFin, zMaxFin;
        int x, y, z;
        if(xMin > xMax) {
            xMinFin = xMax;
            xMaxFin = xMin;
        } else {
            xMinFin = xMin;
            xMaxFin = xMax;
        }
        if(yMin > yMax) {
            yMinFin = yMax;
            yMaxFin = yMin;
        } else {
            yMinFin = yMin;
            yMaxFin = yMax;
        }
        if(zMin > zMax) {
            zMinFin = zMax;
            zMaxFin = zMin;
        } else {
            zMinFin = zMin;
            zMaxFin = zMax;
        }
        for(x = xMinFin; x <= xMaxFin; x++) {
            for(y = yMinFin; y <= yMaxFin; y++) {
                for(z = zMinFin; z <= zMaxFin; z++) {
                    Location l = new Location(w, x, y, z);
                    taskAreaLocs.add(l);
                }
            }
        }
    }

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean status) {
        inUse = status;
    }
}
