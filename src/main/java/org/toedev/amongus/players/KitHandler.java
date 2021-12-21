package org.toedev.amongus.players;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class KitHandler {

    private final Inventory white;
    private final Inventory silver;
    private final Inventory gray;
    private final Inventory black
    private final Inventory red;
    private final Inventory maroon;
    private final Inventory yellow;
    private final Inventory olive;
    private final Inventory lime;
    private final Inventory green;
    private final Inventory aqua;
    private final Inventory teal;
    private final Inventory blue;
    private final Inventory navy;
    private final Inventory fuchsia;
    private final Inventory purple;
    private final Inventory orange;

    public KitHandler() {
        this.white = Bukkit.createInventory(null, 54, "White Kit");
        this.silver = Bukkit.createInventory(null, 54, "Silver Kit");
        this.gray = Bukkit.createInventory(null, 54, "Gray Kit");
        this.black = Bukkit.createInventory(null, 54, "Black Kit");
        this.red = Bukkit.createInventory(null, 54, "Red Kit");
        this.maroon = Bukkit.createInventory(null, 54, "Maroon Kit");
        this.yellow = Bukkit.createInventory(null, 54, "Yellow Kit");
        this.olive = Bukkit.createInventory(null, 54, "Olive Kit");
        this.lime = Bukkit.createInventory(null, 54, "Lime Kit");
        this.green = Bukkit.createInventory(null, 54, "Green Kit");
        this.aqua = Bukkit.createInventory(null, 54, "Aqua Kit");
        this.teal = Bukkit.createInventory(null, 54, "Teal Kit");
        this.blue = Bukkit.createInventory(null, 54, "Blue Kit");
        this.navy = Bukkit.createInventory(null, 54, "Navy Kit");
        this.fuchsia = Bukkit.createInventory(null, 54, "Fuchsia Kit");
        this.purple = Bukkit.createInventory(null, 54, "Purple Kit");
        this.orange = Bukkit.createInventory(null, 54, "Orange Kit");

        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD, 1);
        ItemMeta headMeta = playerHead.getItemMeta();
        playerHead

        this.white.addItem((SkullMeta) playerHead.getItemMeta().setO)
    }

    private ItemStack createPlayerHead() {
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
        GameProfile
    }
}
