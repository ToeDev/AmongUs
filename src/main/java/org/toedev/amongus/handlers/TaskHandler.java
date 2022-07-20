package org.toedev.amongus.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
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
import org.toedev.amongus.tasks.CompleteTask;
import org.toedev.amongus.tasks.TaskManager;
import org.toedev.amongus.tasks.Tasks;
import org.toedev.amongus.tasks.tasks.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
        if(task instanceof ClearAsteroidsTask) return;
        if(task.getLocation().distance(player.getLocation()) > amongUs.getDistanceFromTask()) {
            player.sendMessage(Prefix.prefix + red + "You must be closer to the task to start it!");
            return;
        }
        Location loc = task.getLocation();
        Bukkit.getConsoleSender().sendMessage(Prefix.prefix + gold + player.getName() + purple + " clicked a task block initiating task: " + gold + task.getName() + purple + " At: " + gold + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ() + "," + Objects.requireNonNull(loc.getWorld()).getName());
        if(task instanceof WiresTask) {
            ((WiresTask) task).execute(player, "yellow");
        } else if(task instanceof DownloadDataTask) {
            ((DownloadDataTask) task).execute(player);
        } else if(task instanceof UploadDataTask) {
            if(gameHandler.getPlayerTasks(player).contains(taskManager.getDownloadDataTask(gameHandler.getMapPlayerIsIn(player)))) {
                player.sendMessage(Prefix.prefix + red + "You must first complete the Download Data task before uploading the data here!");
            } else {
                ((UploadDataTask) task).execute(player);
            }
        } else if(task instanceof FuelFillTask) {
            ((FuelFillTask) task).execute(player);
        } else if(task instanceof FuelEmptyTask) {
            if(gameHandler.getPlayerTasks(player).contains(taskManager.getFuelFillTask(gameHandler.getMapPlayerIsIn(player)))) {
                player.sendMessage(Prefix.prefix + red + "You must first complete the Fuel Fill task before emptying the gas here!");
            } else {
                ((FuelEmptyTask) task).execute(player);
            }
        } else if(task instanceof SimonSaysTask) {
            ((SimonSaysTask) task).execute(player);
        } else if(task instanceof KeypadTask) {
            ((KeypadTask) task).execute(player);
        } else if(task instanceof CalibrateTask) {
            ((CalibrateTask) task).execute(player);
        } else if(task instanceof MedbayScanTask) {
            if(((MedbayScanTask) task).isWithinTaskArea(player)) {
                ((MedbayScanTask) task).execute(player);
            } else {
                player.sendMessage(Prefix.prefix + red + "You must be standing in the scan area to start the task!");
            }
        } else if(task instanceof InspectSampleTask) {
            ((InspectSampleTask) task).execute(player);
        } else if(task instanceof ShieldsTask) {
            ((ShieldsTask) task).execute(player);
        }
    }

    @EventHandler
    public void onClearAsteroidsEnter(PlayerInteractEvent event) {
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock() == null) return;
        if(Objects.equals(event.getHand(), EquipmentSlot.OFF_HAND)) return;
        Player player = event.getPlayer();
        if(!gameHandler.isPlayerInAnyMap(player)) return;
        Map map = gameHandler.getMapPlayerIsIn(player);
        if(map == null) return;
        AbstractTask task = taskManager.getTaskByLocation(event.getClickedBlock().getLocation());
        if(task == null) return;
        if(task instanceof ClearAsteroidsTask) {
            if(task.getLocation().distance(player.getLocation()) > amongUs.getDistanceFromTask()) {
                player.sendMessage(Prefix.prefix + red + "You must be closer to the task to start it!");
                return;
            }
            Location loc = task.getLocation();
            Bukkit.getConsoleSender().sendMessage(Prefix.prefix + gold + player.getName() + purple + " clicked a task block initiating task: " + gold + task.getName() + purple + " At: " + gold + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ() + "," + Objects.requireNonNull(loc.getWorld()).getName());
            ((ClearAsteroidsTask) task).execute(player);
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

        UUID uuid = null;
        for(int i = 53; i > 0; i--) {
            if(inv.getItem(i).getType().equals(Material.BLACK_STAINED_GLASS_PANE)) {
                uuid = UUID.fromString(inv.getItem(i).getItemMeta().getDisplayName().substring(2));
                break;
            }
        }
        WiresTask task = null;
        for(AbstractTask t : gameHandler.getPlayerTasks(player)) {
            if(t instanceof WiresTask && ((WiresTask) t).getUnique() != null) {
                if(((WiresTask) t).getUnique().equals(uuid)) {
                    task = (WiresTask) t;
                    break;
                }
            }
        }
        if(!Objects.equals(left, block) && !Objects.equals(right, block) && !Objects.equals(up, block) && !Objects.equals(down, block)) return;
        if(Objects.equals(inv.getItem(slot), bPane)) {
            inv.setItem(slot, block);
            WiresTask finalTask = task;
            scheduler.runTaskLater(amongUs, () -> finalTask.execute(player, "yellow"), 5);
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
                    WiresTask finalTask1 = task;
                    scheduler.runTaskLater(amongUs, () -> finalTask1.execute(player, "blue"), 5);
                } else if(Objects.equals(block, bWool)) {
                    WiresTask finalTask2 = task;
                    scheduler.runTaskLater(amongUs, () -> finalTask2.execute(player, "red"), 5);
                } else if(Objects.equals(block, rWool)) {
                    WiresTask finalTask3 = task;
                    scheduler.runTaskLater(amongUs, () -> {
                        player.closeInventory();
                        gameHandler.completePlayerTask(player, finalTask3);
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
                    player.sendMessage(Prefix.prefix + red + "You left the task area! Please return and try again!");
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + gold + player.getName() + red + " has left/cancelled the DownloadData task!");
                    break;
                }
            } else if(task instanceof UploadDataTask) {
                if(task.isInUse() && player.getLocation().distance(task.getLocation()) > 2) {
                    ((UploadDataTask) task).cancel();
                    player.sendMessage(Prefix.prefix + red + "You left the task area! Please return and try again!");
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + gold + player.getName() + red + " has left/cancelled the UploadData task!");
                    break;
                }
            } else if(task instanceof FuelFillTask) {
                if(task.isInUse() && player.getLocation().distance(task.getLocation()) > 2) {
                    ((FuelFillTask) task).cancel();
                    ((FuelFillTask) task).clearInventory();
                    player.closeInventory();
                    player.sendMessage(Prefix.prefix + red + "You left the task area! Please return and try again!");
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + gold + player.getName() + red + " has left/cancelled the FuelFill task!");
                    break;
                }
            } else if(task instanceof CalibrateTask) {
                if(task.isInUse() && player.getLocation().distance(task.getLocation()) > 2) {
                    ((CalibrateTask) task).cancel();
                    player.getInventory().clear();
                    player.sendMessage(Prefix.prefix + red + "You left the task area! Please return and try again!");
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + gold + player.getName() + red + " has left/cancelled the Calibrate task!");
                    break;
                }
            } else if(task instanceof MedbayScanTask) {
                if(task.isInUse() && player.getLocation().distance(task.getLocation()) > 2) {
                    ((MedbayScanTask) task).cancel();
                    player.sendMessage(Prefix.prefix + red + "You left the task area! Please return and try again!");
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + gold + player.getName() + red + " has left/cancelled the MedbayScan task!");
                    break;
                }
            } else if(task instanceof ShieldsTask) {
                if(task.isInUse() && player.getLocation().distance(task.getLocation()) > 2) {
                    ((ShieldsTask) task).cancel();
                    player.sendMessage(Prefix.prefix + red + "You left the task area! Please return and try again!");
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + gold + player.getName() + red + " has left/cancelled the Shields task!");
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
            if(gameHandler.getPlayerTask(player, taskManager.getDownloadDataTask(gameHandler.getMapPlayerIsIn(player)).getName()).isInUse()) {
                scheduler.runTaskLater(amongUs, () -> {
                    player.setLevel(0);
                    player.setExp(0.0f);
                    gameHandler.completePlayerTask(player, taskManager.getDownloadDataTask(gameHandler.getMapPlayerIsIn(player)));
                }, 20);
            }
        } else if(gameHandler.getPlayerTasks(player).contains(taskManager.getUploadDataTask(gameHandler.getMapPlayerIsIn(player)))) {
            if(gameHandler.getPlayerTask(player, taskManager.getUploadDataTask(gameHandler.getMapPlayerIsIn(player)).getName()).isInUse()) {
                scheduler.runTaskLater(amongUs, () -> {
                    player.setLevel(0);
                    player.setExp(0.0f);
                    gameHandler.completePlayerTask(player, taskManager.getUploadDataTask(gameHandler.getMapPlayerIsIn(player)));
                }, 20);
            }
        }
    }

    @EventHandler
    public void onFuelClick(InventoryClickEvent event) {
        if(!event.getView().getTitle().equalsIgnoreCase("Fuel Fill")) return;
        event.setCancelled(true);
        if(event.getClickedInventory() == null) return;
        if(event.getSlot() == -999) return;
        if(event.getClickedInventory().getItem(event.getSlot()) == null) return;
        if(event.getSlot() != 53) return;
        ItemStack bluePane = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        ItemMeta bluePaneMeta = bluePane.getItemMeta();
        assert bluePaneMeta != null;
        bluePaneMeta.setDisplayName(" ");
        bluePane.setItemMeta(bluePaneMeta);
        Player player = (Player) event.getWhoClicked();
        for(AbstractTask task : gameHandler.getPlayerTasks(player)) {
            if(task instanceof FuelFillTask) {
                for(int i = 0; i <= 52; i++) {
                    if(!Objects.equals(event.getClickedInventory().getItem(i), bluePane)) {
                        return;
                    }
                }
                scheduler.runTaskLater(amongUs, () -> {
                    player.closeInventory();
                    gameHandler.completePlayerTask(player, taskManager.getFuelFillTask(gameHandler.getMapPlayerIsIn(player)));
                }, 5);
                return;
            }
        }
        for(int i = 0; i <= 52; i++) {
            if(!Objects.equals(event.getClickedInventory().getItem(i), null)) {
                return;
            }
        }
        scheduler.runTaskLater(amongUs, () -> {
            player.closeInventory();
            gameHandler.completePlayerTask(player, taskManager.getFuelEmptyTask(gameHandler.getMapPlayerIsIn(player)));
        }, 5);
    }

    @EventHandler
    public void onFuelClose(InventoryCloseEvent event) {
        if(!event.getView().getTitle().equalsIgnoreCase("Fuel Fill")) return;
        Player player = (Player) event.getPlayer();
        if(!gameHandler.isPlayerInAnyMap(player)) return;
        if(gameHandler.getPlayerTasks(player) == null) return;
        for(AbstractTask task : gameHandler.getPlayerTasks(player)) {
            if(task instanceof FuelFillTask) {
                ((FuelFillTask) task).cancel();
                task.setInUse(false);
                ((FuelFillTask) task).clearInventory();
            } else if(task instanceof FuelEmptyTask) {
                ((FuelEmptyTask) task).cancel();
                task.setInUse(false);
                ((FuelEmptyTask) task).clearInventory();
            }
        }
    }

    @EventHandler
    public void onSimonSaysClick(InventoryClickEvent event) {
        if(!event.getView().getTitle().equalsIgnoreCase("Simon Says")) return;
        event.setCancelled(true);
        if(event.getClickedInventory() == null) return;
        if(event.getSlot() == -999) return;
        if(event.getClickedInventory().getItem(event.getSlot()) == null) return;
        Inventory inv = event.getClickedInventory();
        List<Integer> slotOrder = null;
        int currentGreen = 0;
        Player player = (Player) event.getWhoClicked();

        SimonSaysTask t = null;
        for(AbstractTask task : gameHandler.getPlayerTasks(player)) {
            if(task instanceof SimonSaysTask) {
                slotOrder = ((SimonSaysTask) task).getSlotOrder();
                for(int i = 0; i <= inv.getSize() - 1; i++) {
                    if(Objects.equals(inv.getItem(i).toString(), ((SimonSaysTask) task).getGreenBlock().toString())) {
                        currentGreen++;
                    }
                }
                t = (SimonSaysTask) task;
                break;
            }
        }
        SimonSaysTask finalT = t;
        if(slotOrder.size() > currentGreen && event.getSlot() == slotOrder.get(currentGreen)) {
            t.getSimonInv().setItem(event.getSlot(), t.getGreenBlock());
            if(slotOrder.size() == currentGreen + 1) {
                if(slotOrder.size() == 5) {
                    scheduler.runTaskLater(amongUs, () -> {
                        player.closeInventory();
                        gameHandler.completePlayerTask(player, finalT);
                    }, 20);
                } else {
                    finalT.execute(player);
                }
            }
        } else {
            t.getSimonInv().setItem(event.getSlot(), t.getGreenBlock());
            scheduler.runTaskLater(amongUs, () -> {
                finalT.cancel();
                player.closeInventory();
            }, 20);
        }
    }

    @EventHandler
    public void onSimonSaysClose(InventoryCloseEvent event) {
        if(!event.getView().getTitle().equalsIgnoreCase("Simon Says")) return;
        Player player = (Player) event.getPlayer();
        if(!gameHandler.isPlayerInAnyMap(player)) return;
        if(gameHandler.getPlayerTasks(player) == null) return;
        for(AbstractTask task : gameHandler.getPlayerTasks(player)) {
            if(task instanceof SimonSaysTask) {
                ((SimonSaysTask) task).cancel();
            }
        }
    }

    @EventHandler
    public void onKeypadClick(InventoryClickEvent event) {
        if(!event.getView().getTitle().equalsIgnoreCase("Keypad")) return;
        event.setCancelled(true);
        if(event.getClickedInventory() == null) return;
        if(event.getSlot() == -999) return;
        if(event.getClickedInventory().getItem(event.getSlot()) == null) return;
        Player player = (Player) event.getWhoClicked();
        KeypadTask t = null;
        List<Integer> keypadOrder = null;
        for(AbstractTask task : gameHandler.getPlayerTasks(player)) {
            if(task instanceof KeypadTask) {
                keypadOrder = ((KeypadTask) task).getKeypadOrder();
                t = (KeypadTask) task;
                break;
            }
        }
        if(keypadOrder.get(event.getSlot()) == t.getCompleted()) {
            t.getKeypadInv().setItem(event.getSlot(), t.getNumberHeadsDone().get(keypadOrder.get(event.getSlot())));
        } else {
            t.cancel();
            player.closeInventory();
        }
        if(t.getCompleted() == t.getKeypadInv().getSize()) {
            player.closeInventory();
            gameHandler.completePlayerTask(player, t);
        }
    }

    @EventHandler
    public void onCalibrateBlockInteract(PlayerInteractEvent event) {
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock() == null) return;
        if(Objects.equals(event.getHand(), EquipmentSlot.OFF_HAND)) return;
        Player player = event.getPlayer();
        if(!gameHandler.isPlayerInAnyMap(player)) return;
        Map map = gameHandler.getMapPlayerIsIn(player);
        if(map == null) return;
        AbstractTask task = taskManager.getTaskByTaskAreaLocation(event.getClickedBlock().getLocation());
        if(task == null) return;
        if(gameHandler.getPlayerTasks(player) == null || !gameHandler.getPlayerTasks(player).contains(task)) return;
        if(!task.isInUse()) return;
        Bukkit.getConsoleSender().sendMessage(Prefix.prefix + gold + player.getName() + purple + " clicked a task area block which is part of task: " + gold + task.getName());
        Location location = event.getClickedBlock().getLocation();
        if(!(task instanceof CalibrateTask)) return;
        if(((CalibrateTask) task).isBlockMoving(location)) {
            if(location.getBlock().getType().equals(((CalibrateTask) task).getCurrentBlock())) {
                ((CalibrateTask) task).stopBlock(location);
                if(((CalibrateTask) task).areAllBlocksStopped()) {
                    player.getInventory().clear();
                    gameHandler.completePlayerTask(player, task);
                }
            } else {
                ((CalibrateTask) task).cancel();
                player.getInventory().clear();
                player.sendMessage(Prefix.prefix + red + "Wrong block! Try again!");
                Bukkit.getConsoleSender().sendMessage(Prefix.prefix + gold + player.getName() + red + " has left/cancelled the Calibrate task!");
            }
        } else {
            ((CalibrateTask) task).cancel();
            player.getInventory().clear();
            player.sendMessage(Prefix.prefix + red + "You already stopped this block! Try again!");
            Bukkit.getConsoleSender().sendMessage(Prefix.prefix + gold + player.getName() + red + " has left/cancelled the Calibrate task!");
        }
    }

    @EventHandler
    public void onMedbayScanCompleteTask(PlayerLevelChangeEvent event) {
        if(event.getNewLevel() != 100) return;
        Player player = event.getPlayer();
        if(!gameHandler.isPlayerInAnyMap(player)) return;
        MedbayScanTask task = taskManager.getMedbayScanTask(gameHandler.getMapPlayerIsIn(player));
        if(task == null) return;
        if(gameHandler.getPlayerTasks(player).contains(task)) {
            if(gameHandler.getPlayerTask(player, task.getName()).isInUse()) {
                scheduler.runTaskLater(amongUs, () -> {
                    player.setLevel(0);
                    player.setExp(0.0f);
                    task.cancel();
                    gameHandler.completePlayerTask(player, task);
                }, 20);
            }
        }
    }

    @EventHandler
    public void onInspectSampleCompleteTask(PlayerInteractEvent event) {
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock() == null) return;
        if(Objects.equals(event.getHand(), EquipmentSlot.OFF_HAND)) return;
        Player player = event.getPlayer();
        if(!gameHandler.isPlayerInAnyMap(player)) return;
        Map map = gameHandler.getMapPlayerIsIn(player);
        if(map == null) return;
        for(AbstractTask t : gameHandler.getPlayerTasks(player)) {
            if(t instanceof InspectSampleTask && t.getLocation().equals(event.getClickedBlock().getLocation())) {
                if(t.isInUse() && ((InspectSampleTask) t).isFinished()) {
                    scheduler.runTaskLater(amongUs, () -> {
                        gameHandler.completePlayerTask(player, t);
                    }, 0);
                }
            }
        }
    }

    @EventHandler
    public void onShieldsCompleteTask(CompleteTask event) {
        ShieldsTask task = null;
        for(AbstractTask t : gameHandler.getPlayerTasks(event.getPlayer())) {
            if(event.getTask() instanceof ShieldsTask && event.getTask().getLocation().equals(t.getLocation())) {
                task = (ShieldsTask) t;
            }
        }
        if(task != null) {
            task.cancel();
            gameHandler.completePlayerTask(event.getPlayer(), task);
        }
    }

    @EventHandler
    public void onTargetHit(ProjectileHitEvent event) {
        if(!event.getEntity().getType().equals(EntityType.ARROW)) return;
        if(event.getHitEntity() != null) return;
        if(event.getHitBlock() == null || !event.getHitBlock().getType().equals(Material.GILDED_BLACKSTONE)) return;
        if(!(event.getEntity().getShooter() instanceof Player)) return;
        Player player = (Player) event.getEntity().getShooter();
        if(!gameHandler.isPlayerInAnyMap(player)) return;
        Map map = gameHandler.getMapPlayerIsIn(player);
        if(map == null) return;
        AbstractTask task = taskManager.getTaskByTaskAreaLocation(event.getHitBlock().getLocation());
        if(task == null) return;
        if(gameHandler.getPlayerTasks(player) == null || !gameHandler.getPlayerTasks(player).contains(task)) return;
        if(!(task instanceof ClearAsteroidsTask)) return;
        ((ClearAsteroidsTask) task).recordHit();
        player.sendMessage(Prefix.prefix + purple + "Hit! Total hit: " + gold + ((ClearAsteroidsTask) task).getTotalHit() + purple + "/" + gold + "10");
        if(((ClearAsteroidsTask) task).getTotalHit() >= 10) {
            gameHandler.completePlayerTask(player, task);
        }
    }

    @EventHandler
    public void onClearAsteroidsLeave(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if(!gameHandler.isPlayerInAnyMap(player)) return;
        if(gameHandler.getPlayerTasks(player) == null) return;
        if(!gameHandler.getPlayerTasks(player).contains(taskManager.getClearAsteroidsTask(gameHandler.getMapPlayerIsIn(player)))) {
            ClearAsteroidsTask task = taskManager.getClearAsteroidsTask(gameHandler.getMapPlayerIsIn(player));
            if(task.getTeleportLocation().distance(event.getTo()) > 2 && task.getTeleportLocation().distance(event.getFrom()) < 2) {
                task.playerLeave(player);
            }
        } else {
            for(AbstractTask task : gameHandler.getPlayerTasks(player)) {
                if(task instanceof ClearAsteroidsTask) {
                    if(task.getTeleportLocation().distance(event.getTo()) > 2 && task.getTeleportLocation().distance(event.getFrom()) < 2) {
                        ((ClearAsteroidsTask) task).playerLeave(player);
                    }
                }
            }
        }
    }













}
