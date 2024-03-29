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
import org.toedev.amongus.sabotages.SabotageManager;
import org.toedev.amongus.sabotages.Sabotages;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RemoveSabotageCommand {

    private final MapManager mapManager;
    private final SabotageManager sabotageManager;

    private final ChatColor purple = ChatColor.LIGHT_PURPLE;
    private final ChatColor gold = ChatColor.GOLD;
    private final ChatColor red = ChatColor.RED;

    public RemoveSabotageCommand(MapManager mapManager, SabotageManager sabotageManager) {
        this.mapManager = mapManager;
        this.sabotageManager = sabotageManager;
    }

    public void execute(final CommandSender sender, String[] args) throws SQLException {
        if(!Sabotages.sabotageNames.containsValue(args[1].toLowerCase())) {
            sender.sendMessage(Prefix.prefix + red + "The sabotage " + gold + args[1] + red + " isn't a valid option!");
            return;
        }
        if(mapManager.getMap(args[2]) == null) {
            sender.sendMessage(Prefix.prefix + red + "The map " + gold + args[2] + red + " doesn't exist!");
            return;
        }
        List<Location> regionPoints = getPoints(sender);
        Location loc;
        if(sabotageManager.countSabotageInMap(mapManager.getMap(args[2]), args[1].toLowerCase()) > 1) {
            if(regionPoints == null) {
                sender.sendMessage(Prefix.prefix + red + "The map " + gold + args[2] + red + " contains multiple " + gold + args[1] + red + " sabotages! Select the one you want in WorldEdit and run the command again.");
                return;
            } else if(!regionPoints.get(0).equals(regionPoints.get(1))) {
                sender.sendMessage(Prefix.prefix + red + "You can only select 1 block for this task!");
                return;
            } else {
                loc = regionPoints.get(0);
            }
        } else {
            if(sabotageManager.countSabotageInMap(mapManager.getMap(args[2]), args[1].toLowerCase()) > 0) {
                loc = sabotageManager.getSabotageByMap(mapManager.getMap(args[2]), args[1].toLowerCase()).getLocation();
            } else {
                sender.sendMessage(Prefix.prefix + red + "The map " + gold + args[2] + red + " doesn't currently have sabotage " + gold + args[1]);
                return;
            }
        }

        sabotageManager.removeSabotage(mapManager.getMap(args[2]), args[1].toLowerCase(), loc);
        String locString = loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ() + "," + loc.getWorld().getName();
        sender.sendMessage(Prefix.prefix + purple + "Sabotage " + gold + args[1] + purple + " At: " + gold + locString + purple + " removed from map " + gold + args[2]);
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
