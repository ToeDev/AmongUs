package org.toedev.amongus.tasks.tasks;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.tasks.AbstractTask;

import java.util.ArrayList;
import java.util.List;

public class ShieldsTask extends AbstractTask {

    private final AmongUs amongUs;
    private final BukkitScheduler scheduler;
    public List<Integer> taskIDs;

    public ShieldsTask(AmongUs amongus, String name, Map map, Location location, Location taskAreaMinLocation, Location taskAreaMaxLocation) {
        super(name, map, location, taskAreaMinLocation, taskAreaMaxLocation);
        this.amongUs = amongus;
        this.scheduler = amongus.getServer().getScheduler();

        this.taskIDs = new ArrayList<>();
    }

    public void cancel() {
        for(Integer i : taskIDs) {
            scheduler.cancelTask(i);
        }
    }

    public void execute(Player player) {
        setInUse(true);
        List<Location> tempLocs = new ArrayList<>(getTaskAreaLocs());
        List<List<Location>> locs = new ArrayList<>();
        for(Location loc : getTaskAreaLocs()) {
            List<Location> l = new ArrayList<>();
            l.add(loc);
            for(Location tempLoc : tempLocs) {
                if(tempLoc.getY() < l.get(0).getY()) {
                    l.clear();
                    l.add(tempLoc);
                } else if(tempLoc.getY() == l.get(0).getY()) {
                    l.add(tempLoc);
                }
            }
            tempLocs.removeAll(l);
            locs.add(l);
        }
        int i = 0;
        for(List<Location> list : locs) {
            i++;
            System.out.println("y level " + i);
            for(Location out : list) {
                System.out.println(out.toString());
            }
        }
        taskIDs.add(scheduler.runTaskTimer(amongUs, () -> {

        }, 20, 100).getTaskId());
    }
}
