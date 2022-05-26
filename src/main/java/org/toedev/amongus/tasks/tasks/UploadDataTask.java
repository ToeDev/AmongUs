package org.toedev.amongus.tasks.tasks;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitScheduler;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.tasks.AbstractTask;

public class UploadDataTask extends AbstractTask {

    private final AmongUs amongUs;
    private final BukkitScheduler scheduler;

    public UploadDataTask(AmongUs amongUs, String name, Map map, Location location) {
        super(name, map, location);
        this.amongUs = amongUs;
        this.scheduler = amongUs.getServer().getScheduler();
    }
}
