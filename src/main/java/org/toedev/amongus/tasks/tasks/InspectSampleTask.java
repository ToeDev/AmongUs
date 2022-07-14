package org.toedev.amongus.tasks.tasks;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.tasks.AbstractTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class InspectSampleTask extends AbstractTask {

    private final AmongUs amongUs;
    private final BukkitScheduler scheduler;
    public List<Integer> taskIDs;
    public boolean finished;

    public InspectSampleTask(AmongUs amongus, String name, Map map, Location location, Location taskAreaMinLocation, Location taskAreaMaxLocation) {
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

    public boolean isFinished() {
        return finished;
    }

    public void execute(Player player) {
        setInUse(true);
        player.setLevel(0);
        player.setExp(0.0f);
        AtomicInteger i = new AtomicInteger(100);
        taskIDs.add(scheduler.runTaskTimer(amongUs, () -> {
            player.setLevel(i.get());
            player.setExp((float) ((100 - i.get()) * 0.01));
            i.getAndDecrement();
            if(i.get() == -1) {
                cancel();
                finished = true;
            }
        }, 0, 20).getTaskId());
    }
}
