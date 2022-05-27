package org.toedev.amongus.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitScheduler;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.Prefix;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.map.MapManager;
import org.toedev.amongus.tasks.AbstractTask;
import org.toedev.amongus.tasks.TaskManager;
import org.toedev.amongus.tasks.tasks.DownloadDataTask;
import org.toedev.amongus.tasks.tasks.UploadDataTask;
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
        if(task.isInUse()) return;
        Bukkit.getConsoleSender().sendMessage(Prefix.prefix + gold + player.getName() + purple + " clicked a task block initiating task: " + gold + task.getName());
        if(task instanceof WiresTask) {
            ((WiresTask) task).execute(player, "yellow");
            task.setInUse(true);
        } else if(task instanceof DownloadDataTask) {
            ((DownloadDataTask) task).execute(player);
            task.setInUse(true);
        } else if(task instanceof UploadDataTask) {
            if(gameHandler.getPlayerTasks(player).contains(taskManager.getDownloadDataTask(gameHandler.getMapPlayerIsIn(player)))) {
                player.sendMessage(Prefix.prefix + red + "You must first complete the Download Data task before uploading the data here!");
            } else {
                ((UploadDataTask) task).execute(player);
                task.setInUse(true);
            }
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
        if(!gameHandler.isPlayerInAnyMap(player)) return;
        ItemStack bPane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = bPane.getItemMeta();
        assert meta != null;
        meta.setDisplayName(" ");
        bPane.setItemMeta(meta);
        for(int i = 0; i < 9; i++) {
            if(!Objects.equals(inv.getItem(i), bPane)) {
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
        if(Objects.equals(inv.getItem(slot), bPane)) {
            inv.setItem(slot, block);
            scheduler.runTaskLater(amongUs, () -> taskManager.getWiresTask(gameHandler.getMapPlayerIsIn(player)).execute(player, "yellow"), 5);
        } else {
            inv.setItem(slot, block);
            ItemStack gPane = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
            ItemMeta gMeta = gPane.getItemMeta();
            assert gMeta != null;
            gMeta.setDisplayName(" ");
            gPane.setItemMeta(gMeta);
            if(!inv.contains(gPane)) {
                ItemStack yWool = new ItemStack(Material.YELLOW_WOOL);
                ItemMeta yMeta = yWool.getItemMeta();
                assert yMeta != null;
                yMeta.setDisplayName(" ");
                yWool.setItemMeta(yMeta);
                ItemStack bWool = new ItemStack(Material.BLUE_WOOL);
                ItemMeta bMeta = bWool.getItemMeta();
                assert bMeta != null;
                bMeta.setDisplayName(" ");
                bWool.setItemMeta(bMeta);
                ItemStack rWool = new ItemStack(Material.RED_WOOL);
                ItemMeta rMeta = rWool.getItemMeta();
                assert rMeta != null;
                rMeta.setDisplayName(" ");
                rWool.setItemMeta(rMeta);
                if(Objects.equals(block, yWool)) {
                    scheduler.runTaskLater(amongUs, () -> taskManager.getWiresTask(gameHandler.getMapPlayerIsIn(player)).execute(player, "blue"), 5);
                } else if(Objects.equals(block, bWool)) {
                    scheduler.runTaskLater(amongUs, () -> taskManager.getWiresTask(gameHandler.getMapPlayerIsIn(player)).execute(player, "red"), 5);
                } else if(Objects.equals(block, rWool)) {
                    scheduler.runTaskLater(amongUs, () -> {
                        player.closeInventory();
                        gameHandler.completePlayerTask(player, taskManager.getWiresTask(gameHandler.getMapPlayerIsIn(player)));
                    }, 5);

                }
            }
        }
    }

    @EventHandler
    public void onWiresPanelClose(InventoryCloseEvent event) {
        if(!event.getView().getTitle().equalsIgnoreCase("Wires Panel")) return;
        Player player = (Player) event.getPlayer();
        if(!gameHandler.isPlayerInAnyMap(player)) return;
        if(gameHandler.getPlayerTasks(player) == null) return;
        for(AbstractTask task : gameHandler.getPlayerTasks(player)) {
            if(task instanceof WiresTask) {
                task.setInUse(false);
            }
        }
    }

    @EventHandler
    public void onPlayerLeaveTask(PlayerMoveEvent event) { //this is only for cancelling tasks that are in the middle of a scheduler
        Player player = event.getPlayer();
        if(!gameHandler.isPlayerInAnyMap(player)) return;
        if(gameHandler.getPlayerTasks(player) == null) return;
        for(AbstractTask task : gameHandler.getPlayerTasks(player)) {
            if(task instanceof DownloadDataTask) {
                if(task.isInUse() && player.getLocation().distance(task.getLocation()) > 2) {
                    ((DownloadDataTask) task).cancel();
                    task.setInUse(false);
                    player.sendMessage(Prefix.prefix + red + "You left the task area! Please return and try again!");
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + gold + player.getName() + red + " has left/cancelled the DownloadData task!");
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onDataTaskComplete(PlayerLevelChangeEvent event) {
        if(event.getNewLevel() != 100) return;
        Player player = event.getPlayer();
        if(!gameHandler.isPlayerInAnyMap(player)) return;
        if(gameHandler.getPlayerTasks(player).contains(taskManager.getDownloadDataTask(gameHandler.getMapPlayerIsIn(player)))) {
            scheduler.runTaskLater(amongUs, () -> {
                player.setLevel(0);
                player.setExp(0.0f);
                gameHandler.completePlayerTask(player, taskManager.getDownloadDataTask(gameHandler.getMapPlayerIsIn(player)));
            }, 20);
        } else if(gameHandler.getPlayerTasks(player).contains(taskManager.getUploadDataTask(gameHandler.getMapPlayerIsIn(player)))) {
            scheduler.runTaskLater(amongUs, () -> {
                player.setLevel(0);
                player.setExp(0.0f);
                gameHandler.completePlayerTask(player, taskManager.getUploadDataTask(gameHandler.getMapPlayerIsIn(player)));
            }, 20);
        }
    }











}
