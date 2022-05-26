package org.toedev.amongus.tasks.tasks;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.tasks.AbstractTask;

public class DownloadDataTask extends AbstractTask {

    private final AmongUs amongUs;
    private final BukkitScheduler scheduler;

    public DownloadDataTask(AmongUs amongUs, String name, Map map, Location location) {
        super(name, map, location);
        this.amongUs = amongUs;
        this.scheduler = amongUs.getServer().getScheduler();
    }

    public void execute(Player player) {
        scheduler.runTaskLater(amongUs, () -> {
            int i = 1;
            scheduler.runTaskLater(amongUs, () -> {
                player.setLevel(0);
                //player.setExp(0);
            }, 20 * i);
            i++;
            scheduler.runTaskLater(amongUs, () -> {
                player.setLevel(10);
                //player.setTotalExperience(2);
            }, 20 * i);
            i++;
            scheduler.runTaskLater(amongUs, () -> {
                player.setLevel(49);
                //player.setTotalExperience(2);
            }, 20 * i);
            i++;
            i++;
            scheduler.runTaskLater(amongUs, () -> {
                player.setLevel(51);
                //player.setTotalExperience(2);
            }, 20 * i);
            i++;
            i++;
            i++;
            scheduler.runTaskLater(amongUs, () -> {
                player.setLevel(86);
                //player.setTotalExperience(2);
            }, 20 * i);
            i++;
            scheduler.runTaskLater(amongUs, () -> {
                player.setLevel(96);
                //player.setTotalExperience(2);
            }, 20 * i);
            i++;
            i++;
            i++;
            scheduler.runTaskLater(amongUs, () -> {
                player.setLevel(99);
                //player.setTotalExperience(2);
            }, 20 * i);
            i++;
            i++;
            scheduler.runTaskLater(amongUs, () -> {
                player.setLevel(100);
                //player.setTotalExperience(2);
            }, 20 * i);
            i++;
            i++;
            i++;
            i++;
            scheduler.runTaskLater(amongUs, () -> {
                player.setLevel(0);
                player.sendMessage("done");
                //player.setTotalExperience(2);
            }, 20 * i);
        }, 10);
    }
}
