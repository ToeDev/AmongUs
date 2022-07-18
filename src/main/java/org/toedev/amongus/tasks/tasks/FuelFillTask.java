package org.toedev.amongus.tasks.tasks;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitScheduler;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.tasks.AbstractTask;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FuelFillTask extends AbstractTask {

    private final AmongUs amongUs;
    private final BukkitScheduler scheduler;

    private final Inventory fuelFillInv;
    private final ItemStack glassStage1_2;
    private final ItemStack glassStage3_4;
    private final ItemStack glassStage5_6;
    private final ItemStack gasCanHead;

    private List<Integer> taskIDs;

    public FuelFillTask(AmongUs amongUs, String name, Map map, Location location, Location taskAreaMinLocation, Location taskAreaMaxLocation, Location teleportLocation) {
        super(name, map, location, taskAreaMinLocation, taskAreaMaxLocation, teleportLocation);
        this.amongUs = amongUs;
        this.scheduler = amongUs.getServer().getScheduler();

        fuelFillInv = Bukkit.createInventory(null, 54, "Fuel Fill");
        glassStage1_2 = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE);
        ItemMeta glassStage1_2Meta = glassStage1_2.getItemMeta();
        assert glassStage1_2Meta != null;
        glassStage1_2Meta.setDisplayName(" ");
        glassStage1_2.setItemMeta(glassStage1_2Meta);
        glassStage3_4 = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
        ItemMeta glassStage3_4Meta = glassStage3_4.getItemMeta();
        assert glassStage3_4Meta != null;
        glassStage3_4Meta.setDisplayName(" ");
        glassStage3_4.setItemMeta(glassStage3_4Meta);
        glassStage5_6 = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        ItemMeta glassStage5_6Meta = glassStage5_6.getItemMeta();
        assert glassStage5_6Meta != null;
        glassStage5_6Meta.setDisplayName(" ");
        glassStage5_6.setItemMeta(glassStage5_6Meta);

        gasCanHead = createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmY5ZDlkZTYyZWNhZTliNzk4NTU1ZmQyM2U4Y2EzNWUyNjA1MjkxOTM5YzE4NjJmZTc5MDY2Njk4Yzk1MDhhNyJ9fX0=", "Click to grab the fuel can");
        taskIDs = new ArrayList<>();
    }

    public void cancel() {
        for(Integer i : taskIDs) {
            scheduler.cancelTask(i);
        }
        setInUse(false);
    }

    public void clearInventory() {
        fuelFillInv.clear();
    }

    public void execute(Player player) {
        setInUse(true);
        player.openInventory(fuelFillInv);
        int i = 1;
        taskIDs.add(scheduler.runTaskLater(amongUs, () -> {
            for(int s = 45; s <= 53; s++) {
                fuelFillInv.setItem(s, glassStage1_2);
            }}, 20 * i).getTaskId());
        i++;
        taskIDs.add(scheduler.runTaskLater(amongUs, () -> {
            for(int s = 36; s <= 53; s++) {
                fuelFillInv.setItem(s, glassStage1_2);
            }}, 20 * i).getTaskId());
        i++;
        taskIDs.add(scheduler.runTaskLater(amongUs, () -> {
            for(int s = 27; s <= 53; s++) {
                fuelFillInv.setItem(s, glassStage3_4);
            }
        }, 20 * i).getTaskId());
        i++;
        taskIDs.add(scheduler.runTaskLater(amongUs, () -> {
            for(int s = 18; s <= 53; s++) {
                fuelFillInv.setItem(s, glassStage3_4);
            }
        }, 20 * i).getTaskId());
        i++;
        taskIDs.add(scheduler.runTaskLater(amongUs, () -> {
            for(int s = 9; s <= 53; s++) {
                fuelFillInv.setItem(s, glassStage5_6);
            }
        }, 20 * i).getTaskId());
        i++;
        taskIDs.add(scheduler.runTaskLater(amongUs, () -> {
            for(int s = 0; s <= 53; s++) {
                if (s == 53) {
                    fuelFillInv.setItem(s, gasCanHead);
                } else {
                    fuelFillInv.setItem(s, glassStage5_6);
                }
            }
            this.taskIDs = new ArrayList<>();
        }, 20 * i).getTaskId());
    }

    private ItemStack createPlayerHead(String base64, String name) {
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta headMeta = (SkullMeta) playerHead.getItemMeta();
        assert headMeta != null;
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", base64));
        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        headMeta.setDisplayName(name);
        playerHead.setItemMeta(headMeta);
        return playerHead;
    }
}
