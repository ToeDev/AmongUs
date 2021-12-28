package org.toedev.amongus.commands;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.toedev.amongus.map.MapManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SetLocationCommand {

    private final MapManager mapManager;

    public SetLocationCommand(MapManager mapManager) {
        this.mapManager = mapManager;
    }

    public void execute(final CommandSender sender, String[] args) throws SQLException {
        if(args.length != 3) {
            sender.sendMessage("invalid usage");
            return;
        }
        if(mapManager.getMap(args[2]) == null) {
            sender.sendMessage("map doesn't exist");
            return;
        }

        if(args[1].equalsIgnoreCase("map")) {
            List<Location> regionPoints = getPoints(sender);
            if(regionPoints == null) {
                sender.sendMessage("You haven't made a selection yet!");
            } else {
                mapManager.setMapCorners(args[2].toLowerCase(), regionPoints.get(0), regionPoints.get(1));
                sender.sendMessage("map region set");
            }
            return;
        }
        if(args[1].equalsIgnoreCase("start")) {
            List<Location> regionPoints = getPoints(sender);
            if(regionPoints == null) {
                sender.sendMessage("You haven't made a selection yet!");
            } else {
                mapManager.setStartCorners(args[2].toLowerCase(), regionPoints.get(0), regionPoints.get(1));
                sender.sendMessage("start region set");
            }
            return;
        }
        if(args[1].equalsIgnoreCase("meeting")) {
            List<Location> regionPoints = getPoints(sender);
            if(regionPoints == null) {
                sender.sendMessage("You haven't made a selection yet!");
            } else {
                mapManager.setMeetingCorners(args[2].toLowerCase(), regionPoints.get(0), regionPoints.get(1));
                sender.sendMessage("meeting region set");
            }
            return;
        }

        sender.sendMessage("invalid usage");
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
