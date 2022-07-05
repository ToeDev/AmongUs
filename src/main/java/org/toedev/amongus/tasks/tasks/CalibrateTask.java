package org.toedev.amongus.tasks.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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

    public List<Material> blocks;

    public CalibrateTask(AmongUs amongus, String name, Map map, Location location, Location taskAreaMinLocation, Location taskAreaMaxLocation) {
        super(name, map, location, taskAreaMinLocation, taskAreaMaxLocation);
        this.amongUs = amongus;
        this.scheduler = amongus.getServer().getScheduler();

        this.taskIDs = new ArrayList<>();
        this.blocks = new ArrayList<>();
        this.blocks.add(Material.RED_WOOL);
        this.blocks.add(Material.BLUE_WOOL);
        this.blocks.add(Material.LIME_WOOL);
        this.blocks.add(Material.YELLOW_WOOL);
    }

    public void cancel() {
        for(Integer i : taskIDs) {
            scheduler.cancelTask(i);
        }
        setInUse(false);
    }

    public void execute(Player player) {
        setInUse(true);
        List<Location> locs = new ArrayList<>(getTaskAreaLocs());
        for(Location loc : locs) {
            for(Material mat : blocks) {
                loc.getBlock().setType(mat);
            }
        }
    }
}
