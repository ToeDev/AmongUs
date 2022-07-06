package org.toedev.amongus.tasks.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.tasks.AbstractTask;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CalibrateTask extends AbstractTask {

    private final AmongUs amongUs;
    private final BukkitScheduler scheduler;
    public java.util.Map<Location, Integer> taskIDs;

    public List<Material> blocks;
    public Material currentBlock;

    public CalibrateTask(AmongUs amongus, String name, Map map, Location location, Location taskAreaMinLocation, Location taskAreaMaxLocation) {
        super(name, map, location, taskAreaMinLocation, taskAreaMaxLocation);
        this.amongUs = amongus;
        this.scheduler = amongus.getServer().getScheduler();

        this.taskIDs = new HashMap<>();
        this.blocks = new ArrayList<>();
        this.blocks.add(Material.BLUE_WOOL);
        this.blocks.add(Material.RED_WOOL);
        this.blocks.add(Material.LIME_WOOL);
        this.blocks.add(Material.GRAY_WOOL);
        this.blocks.add(Material.ORANGE_WOOL);
    }

    public void cancel() {
        for(Location l : taskIDs.keySet()) {
            scheduler.cancelTask(taskIDs.get(l));
        }
        setInUse(false);
    }

    public void stopBlock(Location location) {
        scheduler.cancelTask(taskIDs.get(location));
        taskIDs.remove(location);
    }

    public boolean isBlockMoving(Location location) {
        return taskIDs.containsKey(location);
    }

    public boolean areAllBlocksStopped() {
        return taskIDs.size() <= 0;
    }

    public Material getCurrentBlock() {
        return currentBlock;
    }

    public void execute(Player player) {
        setInUse(true);
        currentBlock = blocks.get(new Random().ints(0, blocks.size()).findFirst().getAsInt());
        player.getInventory().addItem(new ItemStack(currentBlock));
        List<Location> locs = new ArrayList<>(getTaskAreaLocs());
        int delay = 5;
        for(Location loc : locs) {
            List<Material> random = new ArrayList<>(blocks);
            Collections.shuffle(random);
            AtomicInteger i = new AtomicInteger();
            taskIDs.put(loc, scheduler.runTaskTimer(amongUs, () -> {
                loc.getBlock().setType(random.get(i.get()));
                i.getAndIncrement();
                if(i.get() >= random.size()) {
                    i.set(0);
                }
            }, delay, 20).getTaskId());
            delay = delay + 5;
        }
    }
}
