package org.toedev.amongus.tasks.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Lightable;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.tasks.AbstractTask;
import org.toedev.amongus.tasks.CompleteTask;

import java.util.ArrayList;
import java.util.List;

public class ShieldsTask extends AbstractTask {

    private final AmongUs amongUs;
    private final BukkitScheduler scheduler;
    public List<Integer> taskIDs;

    public ShieldsTask(AmongUs amongus, String name, Map map, Location location, Location taskAreaMinLocation, Location taskAreaMaxLocation, Location teleportLocation) {
        super(name, map, location, taskAreaMinLocation, taskAreaMaxLocation, teleportLocation);
        this.amongUs = amongus;
        this.scheduler = amongus.getServer().getScheduler();

        this.taskIDs = new ArrayList<>();
    }

    public void cancel() {
        setInUse(false);
        for(Integer i : taskIDs) {
            scheduler.cancelTask(i);
        }
        for(Location out : getTaskAreaLocs()) {
            Block block = out.getBlock();
            if(!block.getType().equals(Material.AIR)) {
                BlockData data = block.getBlockData();
                if(data instanceof Lightable) {
                    ((Lightable) data).setLit(false);
                    block.setBlockData(data, false);
                }
            }
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
                    if(!tempLoc.equals(l.get(0))) l.add(tempLoc);
                }
            }
            if(locs.isEmpty() || locs.get(locs.size() - 1).get(0).getY() < l.get(0).getY()) {
                tempLocs.removeAll(l);
                locs.add(l);
            }
        }
        taskIDs.add(scheduler.runTaskTimer(amongUs, () -> {
            for(Location out : locs.get(0)) {
                Block block = out.getBlock();
                if(!block.getType().equals(Material.AIR)) {
                    BlockData data = block.getBlockData();
                    if(data instanceof Lightable) {
                        ((Lightable) data).setLit(true);
                        block.setBlockData(data, false);
                    }
                }
            }
            locs.remove(0);
            if(locs.isEmpty()) {
                scheduler.runTaskLater(amongUs, () -> Bukkit.getPluginManager().callEvent(new CompleteTask(player, this)), 20);
            }
        }, 20, 100).getTaskId());
    }
}
