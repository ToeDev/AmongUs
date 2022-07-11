package org.toedev.amongus.tasks.tasks;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.tasks.AbstractTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MedbayScanTask extends AbstractTask {

    private final AmongUs amongUs;
    private final BukkitScheduler scheduler;
    private final List<Integer> taskIDs;

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

    public boolean isWithinTaskArea(Player player) {
        Location minLoc = getTaskAreaMinLocation();
        Location maxLoc = getTaskAreaMaxLocation();
        Location pLoc = player.getLocation();
        boolean x = minLoc.getBlockX() <= pLoc.getBlockX() && maxLoc.getBlockX() >= pLoc.getBlockX();
        boolean y = minLoc.getBlockY() <= pLoc.getBlockY() && maxLoc.getBlockY() >= pLoc.getBlockY();
        boolean z = minLoc.getBlockZ() <= pLoc.getBlockZ() && maxLoc.getBlockZ() >= pLoc.getBlockZ();
        return (x && y && z);
    }

    public void execute(Player player) {
        setInUse(true);
        List<Location> locs = getTaskAreaLocs();
        long start = System.currentTimeMillis() / 1000L;
        player.setLevel(0);
        player.setExp(0.0f);
        AtomicInteger i = new AtomicInteger(1);
        taskIDs.add(scheduler.runTaskTimer(amongUs, () -> {
            for(Location loc : locs) {
                loc.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, loc, 1);
            }
            if((System.currentTimeMillis() / 1000L) - start == i.get() && i.get() < 6) {
                player.setLevel(20 * i.get());
                player.setExp(0.20f * i.get());
                i.getAndIncrement();
            }
        }, 0, 20).getTaskId());
    }
}
