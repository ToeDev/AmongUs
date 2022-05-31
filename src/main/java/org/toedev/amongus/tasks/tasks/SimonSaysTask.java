package org.toedev.amongus.tasks.tasks;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitScheduler;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.tasks.AbstractTask;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class SimonSaysTask extends AbstractTask {

    private final AmongUs amongUs;
    private final BukkitScheduler scheduler;
    public List<Integer> taskIDs;
    public Inventory simonInv;
    public ItemStack redBlock;
    public ItemStack greenBlock;
    public List<Integer> slotOrder;

    public SimonSaysTask(AmongUs amongUs, String name, Map map, Location location) {
        super(name, map, location);
        this.amongUs = amongUs;
        this.scheduler = amongUs.getServer().getScheduler();

        this.taskIDs = new ArrayList<>();
        simonInv = Bukkit.createInventory(null, 27, "Simon Says");
        redBlock = createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWVhOWU4ODVlOTNmOTY0ZTAwNzVhNzVlOWFlMjVjZGFiZGEyZmZhNWQxMmZlZWRmYWIwZjg4OWIzZWRiYmU2YiJ9fX0=", " ");
        greenBlock = createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjcyYzA1ZGQ3NjI4OGY0MzI4YTI0MzkxYmY0NzI1ZmQyMjYwNTkyZGIzY2Y5YjJiYzIwMzJkZDA1OTZjZjQ0MCJ9fX0=", " ");
    }

    public void cancel() {
        for(Integer i : taskIDs) {
            scheduler.cancelTask(i);
        }
    }

    public void execute(Player player, Integer slots) {
        setInUse(true);
        slotOrder = new ArrayList<>();
        for(int i = 0; i <= simonInv.getSize() - 1; i++) {
            simonInv.setItem(i, redBlock);
        }
        player.openInventory(simonInv);
        //TODO DO SOMETHING HERE
    }

    public Integer getRandomSlot() {
        Random random = new Random();
        return random.ints(0, simonInv.getSize()).findFirst().getAsInt();
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
