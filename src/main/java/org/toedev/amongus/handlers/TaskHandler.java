package org.toedev.amongus.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.toedev.amongus.Prefix;
import org.toedev.amongus.map.MapManager;
import org.toedev.amongus.tasks.AbstractTask;
import org.toedev.amongus.tasks.TaskManager;
import org.toedev.amongus.tasks.tasks.WiresTask;

import java.util.Objects;

public class TaskHandler implements Listener {

    private final MapManager mapManager;
    private final GameHandler gameHandler;
    private final TaskManager taskManager;

    private final ChatColor purple = ChatColor.LIGHT_PURPLE;
    private final ChatColor gold = ChatColor.GOLD;
    private final ChatColor red = ChatColor.RED;

    public TaskHandler(MapManager mapManager, GameHandler gameHandler, TaskManager taskManager) {
        this.mapManager = mapManager;
        this.gameHandler = gameHandler;
        this.taskManager = taskManager;
    }

    @EventHandler
    public void onTaskBlockInteract(PlayerInteractEvent event) {
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock() == null) return;
        if(Objects.equals(event.getHand(), EquipmentSlot.OFF_HAND)) return;
        AbstractTask task = taskManager.getTaskByLocation(event.getClickedBlock().getLocation());
        if(task == null) return;
        Bukkit.getConsoleSender().sendMessage(Prefix.prefix + gold + event.getPlayer().getName() + purple + " clicked a task block");
        if(task instanceof WiresTask) {
            event.getPlayer().openInventory(taskManager.getWiresTask(task.getMap()).getRandomWiresPanel("yellow"));
        }
    }
}
