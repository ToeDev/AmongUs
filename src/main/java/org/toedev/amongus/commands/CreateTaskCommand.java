package org.toedev.amongus.commands;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.toedev.amongus.Prefix;
import org.toedev.amongus.map.MapManager;
import org.toedev.amongus.tasks.TaskManager;
import org.toedev.amongus.tasks.Tasks;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateTaskCommand {

    private final MapManager mapManager;
    private final TaskManager taskManager;

    private final ChatColor purple = ChatColor.LIGHT_PURPLE;
    private final ChatColor gold = ChatColor.GOLD;
    private final ChatColor red = ChatColor.RED;

    public CreateTaskCommand(MapManager mapManager, TaskManager taskManager) {
        this.mapManager = mapManager;
        this.taskManager = taskManager;
    }

    public void execute(final CommandSender sender, String[] args) throws SQLException {
        if(!Tasks.taskNames.containsValue(args[1].toLowerCase())) {
            sender.sendMessage(Prefix.prefix + red + "The task " + gold + args[1] + red + " isn't a valid option!");
            return;
        }
        if(mapManager.getMap(args[2]) == null) {
            sender.sendMessage(Prefix.prefix + red + "The map " + gold + args[2] + red + " doesn't exist!");
            return;
        }
        List<Location> regionPoints = getPoints(sender);
        if(regionPoints == null) {
            sender.sendMessage(Prefix.prefix + red + "You haven't made a selection yet!");
            return;
        }
        if(!regionPoints.get(0).equals(regionPoints.get(1))) {
            sender.sendMessage(Prefix.prefix + red + "You can only select 1 block for this task!");
            return;
        }
        if(args[1].equalsIgnoreCase("wires")) {
            Location loc = regionPoints.get(0);
            taskManager.addWiresTask(mapManager.getMap(args[2]), loc);
            sender.sendMessage(Prefix.prefix + purple + "Wires task created at " + gold + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ());
        } else if(args[1].equalsIgnoreCase("downloaddata")) {
            Location loc = regionPoints.get(0);
            taskManager.addDownloadDataTask(mapManager.getMap(args[2]), loc);
            sender.sendMessage(Prefix.prefix + purple + "Download Data task created at " + gold + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ());
        } else if(args[1].equalsIgnoreCase("uploaddata")) {
            Location loc = regionPoints.get(0);
            taskManager.addUploadDataTask(mapManager.getMap(args[2]), loc);
            sender.sendMessage(Prefix.prefix + purple + "Upload Data task created at " + gold + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ());
        }

    }

    private List<Location> getPoints(CommandSender sender) {
        BukkitPlayer bPlayer = BukkitAdapter.adapt(Bukkit.getPlayer(sender.getName()));
        LocalSession localSession = WorldEdit.getInstance().getSessionManager().get(bPlayer);
        Region playerSelection;
        try {
            playerSelection = localSession.getSelection(bPlayer.getWorld());
        } catch (IncompleteRegionException e) {
            return null;
        }
        Location minPoint = new Location(Objects.requireNonNull(Bukkit.getPlayer(sender.getName())).getWorld(), playerSelection.getMinimumPoint().getX(), playerSelection.getMinimumPoint().getY(), playerSelection.getMinimumPoint().getZ());
        Location maxPoint = new Location(Objects.requireNonNull(Bukkit.getPlayer(sender.getName())).getWorld(), playerSelection.getMaximumPoint().getX(), playerSelection.getMaximumPoint().getY(), playerSelection.getMaximumPoint().getZ());
        List<Location> regionPoints = new ArrayList<>();
        regionPoints.add(minPoint);
        regionPoints.add(maxPoint);
        return regionPoints;
    }
}