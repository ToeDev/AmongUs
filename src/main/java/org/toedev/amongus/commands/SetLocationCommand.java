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
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.toedev.amongus.Materials;
import org.toedev.amongus.map.MapManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SetLocationCommand {

    private final MapManager mapManager;

    private final ChatColor purple = ChatColor.LIGHT_PURPLE;
    private final ChatColor gold = ChatColor.GOLD;
    private final ChatColor red = ChatColor.RED;

    public SetLocationCommand(MapManager mapManager) {
        this.mapManager = mapManager;
    }

    public void execute(final CommandSender sender, String[] args) throws SQLException {
        if(args.length != 3) {
            sender.sendMessage(red + "Invalid usage!" + gold + "Try /au setlocation <startsign/mapspawn/map/meeting> <mapname>");
            return;
        }
        if(mapManager.getMap(args[2]) == null) {
            sender.sendMessage(red + "The map " + gold + args[2] + red + " doesn't exist!");
            return;
        }

        if(args[1].equalsIgnoreCase("map")) {
            List<Location> regionPoints = getPoints(sender);
            if(regionPoints == null) {
                sender.sendMessage(red + "You haven't made a selection yet!");
            } else {
                mapManager.setMapCorners(args[2].toLowerCase(), regionPoints.get(0), regionPoints.get(1));
                sender.sendMessage(purple + "Map area defined");
            }
            return;
        }
        if(args[1].equalsIgnoreCase("startsign")) {
            List<Location> regionPoints = getPoints(sender);
            if(regionPoints == null) {
                sender.sendMessage(red + "You haven't made a selection yet!");
            } else if(!isSignSelectionValid(regionPoints)) {
                sender.sendMessage(red + "Invalid selection or not a sign!");
            } else {
                mapManager.setStartSign(args[2].toLowerCase(), regionPoints.get(0));
                String[] mapName = args[2].toLowerCase().split(" ");
                StringBuilder mapNameFinal = new StringBuilder();
                for(String split : mapName) {
                    mapNameFinal.append(split.substring(0, 1).toUpperCase()).append(split.substring(1).toLowerCase());
                }
                Sign sign = (Sign) regionPoints.get(0).getBlock().getState();
                sign.setLine(0, "Among Us");
                sign.setLine(1, mapNameFinal.toString());
                sign.setLine(2, "Players in queue:");
                sign.setLine(3, "0 / " + mapManager.getMap(args[2].toLowerCase()).getMaxPlayers());
                sign.update();
                sender.sendMessage(purple + "Start sign and player queue hologram defined");
            }
            return;
        }
        if(args[1].equalsIgnoreCase("meeting")) {
            List<Location> regionPoints = getPoints(sender);
            if(regionPoints == null) {
                sender.sendMessage(red + "You haven't made a selection yet!");
            } else {
                mapManager.setMeetingCorners(args[2].toLowerCase(), regionPoints.get(0), regionPoints.get(1));
                sender.sendMessage(purple + "Meeting area defined");
            }
            return;
        }
        if(args[1].equalsIgnoreCase("mapspawn")) {
            Player player = (Player) sender;
            mapManager.setMapSpawn(args[2].toLowerCase(), player.getLocation());
            sender.sendMessage(purple + "Map spawn area defined at current location");
            return;
        }

        sender.sendMessage(red + "Invalid usage!" + gold + "Try /au setlocation <startsign/mapspawn/map/meeting> <mapname>");
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

    private boolean isSignSelectionValid(List<Location> selection) {
        return selection.get(0).equals(selection.get(1)) && Materials.signMaterials.contains(selection.get(0).getBlock().getType());
    }
}
