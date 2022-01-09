package org.toedev.amongus.handlers;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

public class KitHandler {

    private final Inventory white;
    //private final Inventory silver;
    //private final Inventory gray;
    private final Inventory black;
    private final Inventory red;
    //private final Inventory maroon;
    private final Inventory yellow;
    //private final Inventory olive;
    private final Inventory lime;
    private final Inventory green;
    private final Inventory aqua;
    //private final Inventory teal;
    private final Inventory blue;
    //private final Inventory navy;
    //private final Inventory fuchsia;
    private final Inventory purple;
    private final Inventory orange;

    private final Map<Color, Inventory> kits;

    public KitHandler() {
        this.white = Bukkit.createInventory(null, 9, "White Kit");
        //this.silver = Bukkit.createInventory(null, 9, "Silver Kit");
        //this.gray = Bukkit.createInventory(null, 9, "Gray Kit");
        this.black = Bukkit.createInventory(null, 9, "Black Kit");
        this.red = Bukkit.createInventory(null, 9, "Red Kit");
        //this.maroon = Bukkit.createInventory(null, 9, "Maroon Kit");
        this.yellow = Bukkit.createInventory(null, 9, "Yellow Kit");
        //this.olive = Bukkit.createInventory(null, 9, "Olive Kit");
        this.lime = Bukkit.createInventory(null, 9, "Lime Kit");
        this.green = Bukkit.createInventory(null, 9, "Green Kit");
        this.aqua = Bukkit.createInventory(null, 9, "Aqua Kit");
        //this.teal = Bukkit.createInventory(null, 9, "Teal Kit");
        this.blue = Bukkit.createInventory(null, 9, "Blue Kit");
        //this.navy = Bukkit.createInventory(null, 9, "Navy Kit");
        //this.fuchsia = Bukkit.createInventory(null, 9, "Fuchsia Kit");
        this.purple = Bukkit.createInventory(null, 9, "Purple Kit");
        this.orange = Bukkit.createInventory(null, 9, "Orange Kit");

        //amongus heads
        this.white.setItem(0, createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTk5NGY3YjMwMjYxMmFjMzIzMWQ0MWYwZTZkNzhhMzA4MmRiM2JkNjY3ZDBhOWM1YmNmMTJjZWQ4Zjk0MDViYyJ9fX0="));
        this.black.setItem(0, createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjRlOTViZGQ1MTUxMjIyNTYxMzcwYmI2N2FkNGJiMDM2NjQxMGY5MTg2ZGQwMGNhNGQ0NWM2ZmViODQxOWVhYyJ9fX0="));
        this.red.setItem(0, createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTdkNWViMGFlYTVkNjFiYTNmZjQ5OTY0MTZhOTAwOTZhOWQ3NzYwOWViY2QzYjMwOGY5MDZhZTg4OGE0NWY2ZCJ9fX0="));
        this.yellow.setItem(0, createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWI2YjEyYzFiODYyYjY4OTM2ZThhZWU3YTI0OGMzZTI1MmU4OGIxZmNmZjA1NzAwZmNlMWM5NTkxMjBhMjI5ZCJ9fX0="));
        this.lime.setItem(0, createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTU4ZTU2Yzc2NWUzNDQyM2FkMjg3Nzg0MGFiN2M1Njg4YjQ0OTM5YzUzN2MyMDIzNjNhNGYxYjViNzU4MGRjOCJ9fX0="));
        this.green.setItem(0, createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGU2MzM0ODBkNGJmYmVhYTA0OWQwMTNlZDU3NDZkOWY1ZGY5NDk1ZDBiYWUxZDlhNzBkNWUyNjQ5YmMyNjRmIn19fQ=="));
        this.aqua.setItem(0, createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2QzZWYxNTY0NjM2ODg5ZmUzYWNkM2JiMjY0ZWZkNzUyYzkwZDRjNmIyM2IwMGEzZWQ2YzJkN2Y1ZTgyMjc3NSJ9fX0="));
        this.blue.setItem(0, createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjY3MGJjNWYwNDU4MzAwOTQwNTRhZWJjNzViMmVkMzdmYzU1ZjUyNGQ5NzlkODFlZjYxZjNkZTVjMjE3ZDBjYSJ9fX0="));
        this.purple.setItem(0, createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjhiODE4Njc3YmUzYzIwNzk5MzcxMzdmNTBkNTU1YzE2MTcwM2QwN2U5OWNjNzA4YjhiNWY0MTEyOTM4MjgxIn19fQ=="));
        this.orange.setItem(0, createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDkxMGUzMDQ0MWNiODI5YjRlZThjYTFjMDQ0NGMxZmFjNmQ5NGFjZTVhNWMxN2NlNDZkNGVmNmNkOTNiMjNhOSJ9fX0="));

        //amongus chestplates
        this.white.setItem(1, createDyedArmor(Material.LEATHER_CHESTPLATE, Color.WHITE));
        this.black.setItem(1, createDyedArmor(Material.LEATHER_CHESTPLATE, Color.BLACK));
        this.red.setItem(1, createDyedArmor(Material.LEATHER_CHESTPLATE, Color.RED));
        this.yellow.setItem(1, createDyedArmor(Material.LEATHER_CHESTPLATE, Color.YELLOW));
        this.lime.setItem(1, createDyedArmor(Material.LEATHER_CHESTPLATE, Color.LIME));
        this.green.setItem(1, createDyedArmor(Material.LEATHER_CHESTPLATE, Color.GREEN));
        this.aqua.setItem(1, createDyedArmor(Material.LEATHER_CHESTPLATE, Color.AQUA));
        this.blue.setItem(1, createDyedArmor(Material.LEATHER_CHESTPLATE, Color.BLUE));
        this.purple.setItem(1, createDyedArmor(Material.LEATHER_CHESTPLATE, Color.PURPLE));
        this.orange.setItem(1, createDyedArmor(Material.LEATHER_CHESTPLATE, Color.ORANGE));

        //amongus leggings
        this.white.setItem(2, createDyedArmor(Material.LEATHER_LEGGINGS, Color.WHITE));
        this.black.setItem(2, createDyedArmor(Material.LEATHER_LEGGINGS, Color.BLACK));
        this.red.setItem(2, createDyedArmor(Material.LEATHER_LEGGINGS, Color.RED));
        this.yellow.setItem(2, createDyedArmor(Material.LEATHER_LEGGINGS, Color.YELLOW));
        this.lime.setItem(2, createDyedArmor(Material.LEATHER_LEGGINGS, Color.LIME));
        this.green.setItem(2, createDyedArmor(Material.LEATHER_LEGGINGS, Color.GREEN));
        this.aqua.setItem(2, createDyedArmor(Material.LEATHER_LEGGINGS, Color.AQUA));
        this.blue.setItem(2, createDyedArmor(Material.LEATHER_LEGGINGS, Color.BLUE));
        this.purple.setItem(2, createDyedArmor(Material.LEATHER_LEGGINGS, Color.PURPLE));
        this.orange.setItem(2, createDyedArmor(Material.LEATHER_LEGGINGS, Color.ORANGE));

        //amongus boots
        this.white.setItem(3, createDyedArmor(Material.LEATHER_BOOTS, Color.WHITE));
        this.black.setItem(3, createDyedArmor(Material.LEATHER_BOOTS, Color.BLACK));
        this.red.setItem(3, createDyedArmor(Material.LEATHER_BOOTS, Color.RED));
        this.yellow.setItem(3, createDyedArmor(Material.LEATHER_BOOTS, Color.YELLOW));
        this.lime.setItem(3, createDyedArmor(Material.LEATHER_BOOTS, Color.LIME));
        this.green.setItem(3, createDyedArmor(Material.LEATHER_BOOTS, Color.GREEN));
        this.aqua.setItem(3, createDyedArmor(Material.LEATHER_BOOTS, Color.AQUA));
        this.blue.setItem(3, createDyedArmor(Material.LEATHER_BOOTS, Color.BLUE));
        this.purple.setItem(3, createDyedArmor(Material.LEATHER_BOOTS, Color.PURPLE));
        this.orange.setItem(3, createDyedArmor(Material.LEATHER_BOOTS, Color.ORANGE));

        //Set of kits
        this.kits = new HashMap<>();
        this.kits.put(Color.WHITE, white);
        this.kits.put(Color.BLACK, black);
        this.kits.put(Color.RED, red);
        this.kits.put(Color.YELLOW, yellow);
        this.kits.put(Color.LIME, lime);
        this.kits.put(Color.GREEN, green);
        this.kits.put(Color.AQUA, aqua);
        this.kits.put(Color.BLUE, blue);
        this.kits.put(Color.PURPLE, purple);
        this.kits.put(Color.ORANGE, orange);
    }

    private ItemStack createPlayerHead(String base64) {
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
        playerHead.setItemMeta(headMeta);
        return playerHead;
    }

    private ItemStack createDyedArmor(Material armor, Color color) {
        ItemStack dyedArmor = new ItemStack(armor);
        LeatherArmorMeta meta = (LeatherArmorMeta) dyedArmor.getItemMeta();
        assert meta != null;
        meta.setColor(color);
        dyedArmor.setItemMeta(meta);
        return dyedArmor;
    }

    public void applyWhiteKit(Player player) {
        ItemStack[] kit = white.getContents();
        player.getInventory().setArmorContents(kit);
    }

    public void applyBlackKit(Player player) {
        ItemStack[] kit = black.getContents();
        player.getInventory().setArmorContents(kit);
    }

    public void applyRedKit(Player player) {
        ItemStack[] kit = red.getContents();
        player.getInventory().setArmorContents(kit);
    }

    public void applyYellowKit(Player player) {
        ItemStack[] kit = yellow.getContents();
        player.getInventory().setArmorContents(kit);
    }

    public void applyLimeKit(Player player) {
        ItemStack[] kit = lime.getContents();
        player.getInventory().setArmorContents(kit);
    }

    public void applyGreenKit(Player player) {
        ItemStack[] kit = green.getContents();
        player.getInventory().setArmorContents(kit);
    }

    public void applyCyanKit(Player player) {
        ItemStack[] kit = aqua.getContents();
        player.getInventory().setArmorContents(kit);
    }

    public void applyBlueKit(Player player) {
        ItemStack[] kit = blue.getContents();
        player.getInventory().setArmorContents(kit);
    }

    public void applyPurpleKit(Player player) {
        ItemStack[] kit = purple.getContents();
        player.getInventory().setArmorContents(kit);
    }

    public void applyOrangeKit(Player player) {
        ItemStack[] kit = orange.getContents();
        player.getInventory().setArmorContents(kit);
    }

    public Inventory getWhiteKit() {
        return white;
    }

    public Inventory getBlackKit() {
        return black;
    }

    public Inventory getRedKit() {
        return red;
    }

    public Inventory getYellowKit() {
        return yellow;
    }

    public Inventory getLimeKit() {
        return lime;
    }

    public Inventory getGreenKit() {
        return green;
    }

    public Inventory getCyanKit() {
        return aqua;
    }

    public Inventory getBlueKit() {
        return blue;
    }

    public Inventory getPurpleKit() {
        return purple;
    }

    public Inventory getOrangeKit() {
        return orange;
    }

    public void applyKit(Player player, Color color) {
        if(kits.get(color) != null) {
            ItemStack[] kit = kits.get(color).getContents();
            player.getInventory().setArmorContents(kit);
        }
    }

    public Inventory getKit(Color color) {
        return kits.get(color); //TODO VERIFY THIS DOESN'T THROW NULL POINTER
    }
}
