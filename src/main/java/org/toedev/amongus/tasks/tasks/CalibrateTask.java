package org.toedev.amongus.tasks.tasks;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.tasks.AbstractTask;

import java.util.ArrayList;
import java.util.List;

public class CalibrateTask extends AbstractTask {

    private final AmongUs amongUs;
    private final BukkitScheduler scheduler;
    public List<Integer> taskIDs;
    public Location taskAreaMin;
    public Location taskAreaMax;
    public List<Location> taskAreaLocs;

    public CalibrateTask(AmongUs amongus, String name, Map map, Location location, Location taskAreaMinLocation, Location taskAreaMaxLocation) {
        super(name, map, location, taskAreaMinLocation, taskAreaMaxLocation);
        this.amongUs = amongus;
        this.scheduler = amongus.getServer().getScheduler();

        this.taskIDs = new ArrayList<>();
        this.taskAreaMin = getTaskAreaMinLocation();
        this.taskAreaMax = getTaskAreaMaxLocation();

        this.taskAreaLocs = new ArrayList<>();
        if(taskAreaMin != null && taskAreaMax != null) {
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
    }

    public void cancel() {
        for(Integer i : taskIDs) {
            scheduler.cancelTask(i);
        }
        setInUse(false);
    }

    public void execute(Player player) {
        for(Location l : taskAreaLocs) {
            System.out.println(l.toString());
        }
    }
}
