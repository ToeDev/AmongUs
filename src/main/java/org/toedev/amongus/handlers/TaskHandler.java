package org.toedev.amongus.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.Prefix;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.map.MapManager;
import org.toedev.amongus.tasks.AbstractTask;
import org.toedev.amongus.tasks.TaskManager;
import org.toedev.amongus.tasks.tasks.WiresTask;

import java.util.Objects;

public class TaskHandler implements Listener {

    private final BukkitScheduler scheduler;

    private final AmongUs amongUs;
    private final MapManager mapManager;
    private final GameHandler gameHandler;
    private final TaskManager taskManager;

    private final ChatColor purple = ChatColor.LIGHT_PURPLE;
    private final ChatColor gold = ChatColor.GOLD;
    private final ChatColor red = ChatColor.RED;

    public TaskHandler(AmongUs amongUs, MapManager mapManager, GameHandler gameHandler, TaskManager taskManager) {
        scheduler = amongUs.getServer().getScheduler();
        this.amongUs = amongUs;
        this.mapManager = mapManager;
        this.gameHandler = gameHandler;
        this.taskManager = taskManager;
    }

    @EventHandler
    public void onTaskBlockInteract(PlayerInteractEvent event) {
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock() == null) return;
        if(Objects.equals(event.getHand(), EquipmentSlot.OFF_HAND)) return;
        Player player = event.getPlayer();
        if(!gameHandler.isPlayerInAnyMap(player)) return;
        Map map = gameHandler.getMapPlayerIsIn(player);
        if(map == null) return;
        AbstractTask task = taskManager.getTaskByLocation(event.getClickedBlock().getLocation());
        if(task == null) return;
        if(gameHandler.getPlayerTasks(player) == null || !gameHandler.getPlayerTasks(player).contains(task)) return;
        Bukkit.getConsoleSender().sendMessage(Prefix.prefix + gold + player.getName() + purple + " clicked a task block initiating task: " + gold + task.getName());
        if(task instanceof WiresTask) {
            ((WiresTask) task).execute(player, "yellow");
        }
    }

    @EventHandler
    public void onWiresPanelClick(InventoryClickEvent event) {
        if(!event.getView().getTitle().equalsIgnoreCase("Wires Panel")) return;
        event.setCancelled(true);
        if(event.getClickedInventory() == null) return;
        if(event.getSlot() == -999) return;
        if(event.getClickedInventory().getItem(event.getSlot()) == null) return;

        Inventory inv = event.getClickedInventory();
        int slot = event.getSlot();
        ItemStack block = null;
        Player player = (Player) event.getWhoClicked();
        assert  player != null;
        if(gameHandler.isPlayerInAnyMap(player));
        for(int i = 0; i < 9; i++) {
            if(!Objects.equals(inv.getItem(i), new ItemStack(Material.BLACK_STAINED_GLASS_PANE))) {
                block = inv.getItem(i);
                break;
            }
        }
        ItemStack left;
        try{
            left = inv.getItem(slot - 1);
            if(slot == 0 || slot == 9 || slot == 18 || slot == 27 || slot == 36 || slot == 45) left = null;
        } catch(IndexOutOfBoundsException ignored) {
            left = null;
        }
        ItemStack right;
        try{
            right = inv.getItem(slot + 1);
            if(slot == 8 || slot == 17 || slot == 26 || slot == 35 || slot == 44 || slot == 53) right = null;
        } catch(IndexOutOfBoundsException ignored) {
            right = null;
        }
        ItemStack up;
        try{
            up = inv.getItem(slot - 9);
            if(slot == 0 || slot == 1 || slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8) up = null;
        } catch(IndexOutOfBoundsException ignored) {
            up = null;
        }
        ItemStack down;
        try{
            down = inv.getItem(slot + 9);
            if(slot == 45 || slot == 46 || slot == 47 || slot == 48 || slot == 49 || slot == 50 || slot == 51 || slot == 52 || slot == 53) down = null;
        } catch(IndexOutOfBoundsException ignored) {
            down = null;
        }

        if(!Objects.equals(left, block) && !Objects.equals(right, block) && !Objects.equals(up, block) && !Objects.equals(down, block)) return;
        if(Objects.equals(inv.getItem(slot), new ItemStack(Material.BLACK_STAINED_GLASS_PANE))) {
            inv.setItem(slot, block);
            scheduler.runTaskLater(amongUs, () -> taskManager.getWiresTask(gameHandler.getMapPlayerIsIn(player)).execute(player, "yellow"), 5);
        } else {
            inv.setItem(slot, block);
            if(!inv.contains(new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE))) {
                if(Objects.equals(block, new ItemStack(Material.YELLOW_WOOL))) {
                    scheduler.runTaskLater(amongUs, () -> taskManager.getWiresTask(gameHandler.getMapPlayerIsIn(player)).execute(player, "blue"), 5);
                } else if(Objects.equals(block, new ItemStack(Material.BLUE_WOOL))) {
                    scheduler.runTaskLater(amongUs, () -> taskManager.getWiresTask(gameHandler.getMapPlayerIsIn(player)).execute(player, "red"), 5);
                } else if(Objects.equals(block, new ItemStack(Material.RED_WOOL))) {
                    scheduler.runTaskLater(amongUs, () -> {
                        gameHandler.completePlayerTask(player, taskManager.getWiresTask(gameHandler.getMapPlayerIsIn(player)));
                        player.closeInventory();
                        Bukkit.getConsoleSender().sendMessage(Prefix.prefix + gold + player.getName() + purple + " completed the task: " + gold + "wires");
                    }, 5);

                }
            }
        }
    }








}
