package org.toedev.amongus.tasks.tasks;

import org.bukkit.Location;
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

    public CalibrateTask(AmongUs amongus, String name, Map map, Location location, Location taskAreaMinLocation, Location taskAreaMaxLocation) {
        super(name, map, location, taskAreaMinLocation, taskAreaMaxLocation);
        this.amongUs = amongus;
        this.scheduler = amongus.getServer().getScheduler();

        this.taskIDs = new ArrayList<>();
        this.taskAreaMin = getTaskAreaMinLocation();
        this.taskAreaMax = getTaskAreaMaxLocation();
    }

    public void cancel() {
        for(Integer i : taskIDs) {
            scheduler.cancelTask(i);
        }
        setInUse(false);
    }

    public void execute(Player player) {
        System.out.println(getTaskAreaMinLocation());
        System.out.println(getTaskAreaMaxLocation());
    }
}
