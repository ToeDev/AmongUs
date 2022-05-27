package org.toedev.amongus.tasks.tasks;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.tasks.AbstractTask;

import java.util.ArrayList;
import java.util.List;

public class DownloadDataTask extends AbstractTask {

    private final AmongUs amongUs;
    private final BukkitScheduler scheduler;
    private List<Integer> taskIDs;

    public DownloadDataTask(AmongUs amongUs, String name, Map map, Location location) {
        super(name, map, location);
        this.amongUs = amongUs;
        this.scheduler = amongUs.getServer().getScheduler();
        this.taskIDs = new ArrayList<>();
    }

    public List<Integer> getTaskID() {
        return this.taskIDs;
    }

    public boolean isTaskRunning() { //todo not tested (maybe won't use?)
        for(Integer i : taskIDs) {
            if(scheduler.isQueued(i) || scheduler.isCurrentlyRunning(i)) {
                return true;
            }
        }
        return false;
    }

    public void cancel() {
        for(Integer i : taskIDs) {
            scheduler.cancelTask(i);
        }
    }

    public void execute(Player player) {
        setInUse(true);
        scheduler.runTaskLater(amongUs, () -> {
            int i = 1;
            taskIDs.add(scheduler.runTaskLater(amongUs, () -> {
                player.setLevel(0);
                player.setExp(0.0f);
            }, 20 * i).getTaskId());
            i++;
            taskIDs.add(scheduler.runTaskLater(amongUs, () -> {
                player.setLevel(10);
                player.setExp(0.1f);
            }, 20 * i).getTaskId());
            i++;
            taskIDs.add(scheduler.runTaskLater(amongUs, () -> {
                player.setLevel(49);
                player.setExp(0.49f);
            }, 20 * i).getTaskId());
            i++;
            i++;
            taskIDs.add(scheduler.runTaskLater(amongUs, () -> {
                player.setLevel(51);
                player.setExp(0.51f);
            }, 20 * i).getTaskId());
            i++;
            i++;
            i++;
            taskIDs.add(scheduler.runTaskLater(amongUs, () -> {
                player.setLevel(86);
                player.setExp(0.86f);
            }, 20 * i).getTaskId());
            i++;
            taskIDs.add(scheduler.runTaskLater(amongUs, () -> {
                player.setLevel(96);
                player.setExp(0.96f);
            }, 20 * i).getTaskId());
            i++;
            i++;
            i++;
            taskIDs.add(scheduler.runTaskLater(amongUs, () -> {
                player.setLevel(99);
                player.setExp(0.99f);
            }, 20 * i).getTaskId());
            i++;
            i++;
            taskIDs.add(scheduler.runTaskLater(amongUs, () -> {
                player.setLevel(100);
                player.setExp(1.0f);
                this.taskIDs = new ArrayList<>();
            }, 20 * i).getTaskId());
        }, 10);
    }
}
