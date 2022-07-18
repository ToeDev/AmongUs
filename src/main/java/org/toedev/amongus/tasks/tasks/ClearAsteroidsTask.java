package org.toedev.amongus.tasks.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.tasks.AbstractTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClearAsteroidsTask extends AbstractTask {

    private final AmongUs amongUs;
    private final BukkitScheduler scheduler;
    public List<Integer> taskIDs;
    public int totalHit;

    public ClearAsteroidsTask(AmongUs amongUs, String name, Map map, Location location, Location taskAreaMinLocation, Location taskAreaMaxLocation, Location teleportLocation) {
        super(name, map, location, taskAreaMinLocation, taskAreaMaxLocation, teleportLocation);
        this.amongUs = amongUs;
        this.scheduler = amongUs.getServer().getScheduler();

        taskIDs = new ArrayList<>();
        totalHit = 0;
    }

    public void cancel() {
        for(Integer i : taskIDs) {
            scheduler.cancelTask(i);
        }
        clearAsteroids();
    }

    public boolean isRunning() {
        for(Location loc : getTaskAreaLocs()) {
            if(!loc.getBlock().getType().equals(Material.AIR)) {
                return true;
            }
        }
        return false;
    }

    public void recordHit() {
        totalHit++;
    }

    public int getTotalHit() {
        return totalHit;
    }

    public void execute(Player player) {
        ItemStack bow = new ItemStack(Material.BOW);
        bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
        bow.addEnchantment(Enchantment.DURABILITY, 3);
        ItemStack arrow = new ItemStack(Material.ARROW);
        player.getInventory().addItem(bow);
        player.getInventory().addItem(arrow);
        if(!isRunning()) startAsteroids();
        player.teleport(getTeleportLocation());
    }

    public void playerLeave(Player player) {
        player.teleport(getLocation().clone().add(0, 0.25, 0));
        player.getInventory().remove(Material.ARROW);
        player.getInventory().remove(Material.BOW);
        int i = 0;
        for(Entity entity : getLocation().getWorld().getNearbyEntities(getTeleportLocation(), 2, 2, 2)) {
            if(entity instanceof Player) {
                i++;
            }
        }
        if(i <= 0) {
            cancel();
        }
    }

    private void startAsteroids() {
        List<Location> locs = getTaskAreaLocs();
        taskIDs.add(scheduler.runTaskTimer(amongUs, () -> {
            clearAsteroids();
            int i = 0;
            while(i <= locs.size() / 4) {
                locs.get(getRandomAsteroid()).getBlock().setType(Material.GILDED_BLACKSTONE);
                i++;
            }
        }, 0, 40).getTaskId());
    }

    private void clearAsteroids() {
        for(Location loc : getTaskAreaLocs()) {
            loc.getBlock().setType(Material.AIR);
        }
    }

    private Integer getRandomAsteroid() {
        return new Random().ints(0, getTaskAreaLocs().size()).findFirst().getAsInt();
    }
}
