package org.toedev.amongus.tasks.tasks;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.tasks.AbstractTask;

import java.util.ArrayList;
import java.util.List;

public class MedbayScanTask extends AbstractTask {

    private final AmongUs amongUs;
    private final BukkitScheduler scheduler;
    private List<Integer> taskIDs;

    public MedbayScanTask(AmongUs amongUs, String name, Map map, Location location, Location taskAreaMinLocation, Location taskAreaMaxLocation) {
        super(name, map, location, taskAreaMinLocation, taskAreaMaxLocation);
        this.amongUs = amongUs;
        this.scheduler = amongUs.getServer().getScheduler();
        this.taskIDs = new ArrayList<>();
    }

    public void cancel() {
        for(Integer i : taskIDs) {
            scheduler.cancelTask(i);
        }
        setInUse(false);
    }

    public void execute(Player player) {
        setInUse(true);
        List<Location> locs = getTaskAreaLocs();
        taskIDs.add(scheduler.runTaskTimer(amongUs, () -> {
            for(Location loc : locs) {
                loc.getWorld().playEffect(loc, Effect.VILLAGER_PLANT_GROW, 1, 1);
            }
        }, 0, 20).getTaskId());
    }
}
