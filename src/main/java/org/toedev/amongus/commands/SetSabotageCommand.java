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
import org.bukkit.entity.Player;
import org.toedev.amongus.Prefix;
import org.toedev.amongus.map.MapManager;
import org.toedev.amongus.sabotages.SabotageManager;
import org.toedev.amongus.sabotages.Sabotages;
import org.toedev.amongus.tasks.Tasks;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SetSabotageCommand {

    private final MapManager mapManager;
    private final SabotageManager sabotageManager;

    private final ChatColor purple = ChatColor.LIGHT_PURPLE;
    private final ChatColor gold = ChatColor.GOLD;
    private final ChatColor red = ChatColor.RED;

    public SetSabotageCommand(MapManager mapManager, SabotageManager sabotageManager) {
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
        if(regionPoints == null) {
            sender.sendMessage(Prefix.prefix + red + "You haven't made a selection yet!");
            return;
        }
        if(!regionPoints.get(0).equals(regionPoints.get(1))) {
            sender.sendMessage(Prefix.prefix + red + "You can only select 1 block for this sabotage!");
            return;
        }
        if(args[3].equalsIgnoreCase("setoptionallocation")) {
            Location sabotageOptionalLoc = regionPoints.get(0);
            sabotageManager.setOptionalLocation(mapManager.getMap(args[2]), args[1], sabotageOptionalLoc);
            sender.sendMessage(Prefix.prefix + purple + "Sabotage optional location set at " + gold + sabotageOptionalLoc.getBlockX() + ", " + sabotageOptionalLoc.getBlockY() + ", " + sabotageOptionalLoc.getBlockZ());
            return;
        }
        if(args[3].equalsIgnoreCase("setoptionallocation2")) {
            Location sabotageOptionalLoc2 = regionPoints.get(0);
            sabotageManager.setOptionalLocation2(mapManager.getMap(args[2]), args[1], sabotageOptionalLoc2);
            sender.sendMessage(Prefix.prefix + purple + "Sabotage optional location 2 set at " + gold + sabotageOptionalLoc2.getBlockX() + ", " + sabotageOptionalLoc2.getBlockY() + ", " + sabotageOptionalLoc2.getBlockZ());
            return;
        }
        if(args[3].equalsIgnoreCase("setoptionallocation3")) {
            Location sabotageOptionalLoc3 = regionPoints.get(0);
            sabotageManager.setOptionalLocation3(mapManager.getMap(args[2]), args[1], sabotageOptionalLoc3);
            sender.sendMessage(Prefix.prefix + purple + "Sabotage optional location 3 set at " + gold + sabotageOptionalLoc3.getBlockX() + ", " + sabotageOptionalLoc3.getBlockY() + ", " + sabotageOptionalLoc3.getBlockZ());
            return;
        }
        sender.sendMessage(Prefix.prefix + red + "AHHH");
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
