package org.toedev.amongus.map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.sql.Utility;
import org.toedev.amongus.tasks.AbstractTask;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MapManager {

    private final Logger logger;

    private final AmongUs amongUs;
    private final Utility utility;
    private final MapManager mapManager;

    private final Set<Map> maps;

    public MapManager(AmongUs amongUs, Utility utility) {
        this.logger = amongUs.getLogger();
        this.amongUs = amongUs;
        this.utility = utility;
        this.maps = new HashSet<>();
        importMapsFromDB(utility);
        this.mapManager = this;
    }

    public void importMapsFromDB(Utility utility) {
        try {
            ResultSet mapSet = utility.getAllMaps();
            if(mapSet == null) return;
            while(mapSet.next()) {
                Integer minPlayers = mapSet.getInt("minPlayers");
                Integer maxPlayers = mapSet.getInt("maxPlayers");
                Location mapStartSign = new Location(Bukkit.getWorld(mapSet.getString("mapStartSignWorld")), mapSet.getDouble("mapStartSignX"), mapSet.getDouble("mapStartSignY"), mapSet.getDouble("mapStartSignZ"));
                Location mapSpawn = new Location(Bukkit.getWorld(mapSet.getString("mapSpawnWorld")), mapSet.getDouble("mapSpawnX"), mapSet.getDouble("mapSpawnY"), mapSet.getDouble("mapSpawnZ"));
                Location mapMinCorner = new Location(Bukkit.getWorld(mapSet.getString("mapMinCornerWorld")), mapSet.getDouble("mapMinCornerX"), mapSet.getDouble("mapMinCornerY"), mapSet.getDouble("mapMinCornerZ"));
                Location mapMaxCorner = new Location(Bukkit.getWorld(mapSet.getString("mapMaxCornerWorld")), mapSet.getDouble("mapMaxCornerX"), mapSet.getDouble("mapMaxCornerY"), mapSet.getDouble("mapMaxCornerZ"));
                Location meetingMinCorner = new Location(Bukkit.getWorld(mapSet.getString("meetingMinCornerWorld")), mapSet.getDouble("meetingMinCornerX"), mapSet.getDouble("meetingMinCornerY"), mapSet.getDouble("meetingMinCornerZ"));
                Location meetingMaxCorner = new Location(Bukkit.getWorld(mapSet.getString("meetingMaxCornerWorld")), mapSet.getDouble("meetingMaxCornerX"), mapSet.getDouble("meetingMaxCornerY"), mapSet.getDouble("meetingMaxCornerZ"));
                HashMap<AbstractTask, Location> tasks = new HashMap<>(); //TODO IMPORT TASKS FROM SQL
                Map map = new Map(mapSet.getString("mapName"), minPlayers, maxPlayers, mapStartSign, mapSpawn, mapMinCorner, mapMaxCorner, meetingMinCorner, meetingMaxCorner, tasks);
                map.setMapQueueHologram(mapStartSign); //TODO THIS PROBABLY NEEDS CHANGED
                resetStartSign(map); //TODO THIS PROBABLY NEEDS CHANGED
                this.maps.add(map);
                logger.log(Level.INFO, ChatColor.LIGHT_PURPLE + "Map \"" + map.getName() + "\" imported from the DB");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setMinPlayers(String name, Integer minPlayers) throws SQLException {
        for(Map map : maps) {
            if(map.getName().equals(name)) {
                map.setMinPlayers(minPlayers);
                if(map.isMapSetup()) saveMap(map);
                return;
            }
        }
    }

    public void setMaxPlayers(String name, Integer maxPlayers) throws SQLException {
        for(Map map : maps) {
            if(map.getName().equals(name)) {
                map.setMaxPlayers(maxPlayers);
                if(map.isMapSetup()) saveMap(map);
                return;
            }
        }
    }

    private void resetStartSign(Map map) {
        Sign sign = (Sign) map.getMapStartSign().getBlock().getState();
        sign.setLine(0, "Among Us");
        sign.setLine(1, map.getName());
        sign.setLine(2, "Players in queue:");
        sign.setLine(3, "0");
        sign.update();
    }

    public void setStartSign(String name, Location location) throws SQLException {
        for(Map map : maps) {
            if(map.getName().equals(name)) {
                map.setMapStartSign(location);
                if(map.isMapSetup()) saveMap(map);
                return;
            }
        }
    }

    public Set<Map> getAllRunningMaps() {
        Set<Map> runningMaps = new HashSet<>();
        for(Map map : maps) {
            if(map.isMapRunning()) {
                runningMaps.add(map);
            }
        }
        if(runningMaps.isEmpty()) return null;
        return runningMaps;
    }

    public Set<Location> getAllMapStartSigns() {
        Set<Location> mapStartSigns = new HashSet<>();
        for(Map map : getAllMaps()) {
            mapStartSigns.add(map.getMapStartSign());
        }
        return mapStartSigns;
    }

    public Map getMapByStartSign(Location mapStartSign) {
        for(Map map : maps) {
            if(map.getMapStartSign().equals(mapStartSign)) {
                return map;
            }
        }
        return null;
    }

    public Map getMap(String name) {
        for(Map map : maps) {
            if(map.getName().equals(name)) {
                return map;
            }
        }
        return null;
    }

    public Set<Map> getAllMaps() {
        return maps;
    }

    public void addMap(Map map) {
        maps.add(map);
    }

    public void removeMap(String name) {
        maps.removeIf(map -> map.getName().equals(name));
    }

    public int countAllMaps() {
        return maps.size();
    }

    public void setMapSpawn(String name, Location mapSpawn) throws SQLException {
        for(Map map : maps) {
            if(map.getName().equals(name)) {
                map.setMapSpawn(mapSpawn);
                if(map.isMapSetup()) saveMap(map);
                return;
            }
        }
    }

    public void setMapCorners(String name, Location mapMinCorner, Location mapMaxCorner) throws SQLException {
        for(Map map : maps) {
            if(map.getName().equals(name)) {
                map.setMapMinCorner(mapMinCorner);
                map.setMapMaxCorner(mapMaxCorner);
                if(map.isMapSetup()) saveMap(map);
                return;
            }
        }
    }

    public void setMeetingCorners(String name, Location meetingMinCorner, Location meetingMaxCorner) throws SQLException {
        for(Map map : maps) {
            if(map.getName().equals(name)) {
                map.setMeetingMinCorner(meetingMinCorner);
                map.setMeetingMaxCorner(meetingMaxCorner);
                if(map.isMapSetup()) saveMap(map);
                return;
            }
        }
    }

    private void saveMap(Map map) throws SQLException {
        if(utility.isMapInDB(map)) {
            utility.updateMap(map);
            logger.log(Level.INFO, ChatColor.LIGHT_PURPLE + "Map: \"" + map.getName() + "\" updated in DB");
        } else {
            utility.addMap(map);
            logger.log(Level.INFO, ChatColor.LIGHT_PURPLE + "Map: \"" + map.getName() + "\" added to DB");
        }
    }

    public boolean isPlayerInMap(Player player, Map map) {
        if(!maps.contains(map)) return false;
        if(!player.getWorld().getName().equalsIgnoreCase(Objects.requireNonNull(map.getMapMinCorner().getWorld()).getName())) return false;
        Location mapMin = map.getMapMinCorner();
        Location mapMax = map.getMapMaxCorner();
        Location pLoc = player.getLocation();
        double minX = mapMin.getX();
        double minY = mapMin.getY();
        double minZ = mapMin.getZ();
        double maxX = mapMax.getX();
        double maxY = mapMax.getY();
        double maxZ = mapMax.getZ();
        double pX = pLoc.getX();
        double pY = pLoc.getY();
        double pZ = pLoc.getZ();
        return pX >= minX && pX <= maxX && pY >= minY && pY <= maxY && pZ >= minZ && pZ <= maxZ;
    }

    public boolean isPlayerInAnyMap(Player player) {
        for(Map map : maps) {
            if(isPlayerInMap(player, map)) {
                return true;
            }
        }
        return false;
    }

    public Set<Map> getMapsPlayerIsIn(Player player) {
        if(!isPlayerInAnyMap(player)) return null;
        Set<Map> mapsIn = new HashSet<>();
        for(Map map : maps) {
            if(isPlayerInMap(player, map)) {
                mapsIn.add(map);
            }
        }
        return mapsIn;
    }

    public boolean isPlayerInMeeting(Player player, Map map) {
        if(!maps.contains(map)) return false;
        if(!player.getWorld().getName().equalsIgnoreCase(Objects.requireNonNull(map.getMeetingMinCorner().getWorld()).getName())) return false;
        Location meetingMin = map.getMeetingMinCorner();
        Location meetingMax = map.getMeetingMaxCorner();
        Location pLoc = player.getLocation();
        double minX = meetingMin.getX();
        double minY = meetingMin.getY();
        double minZ = meetingMin.getZ();
        double maxX = meetingMax.getX();
        double maxY = meetingMax.getY();
        double maxZ = meetingMax.getZ();
        double pX = pLoc.getX();
        double pY = pLoc.getY();
        double pZ = pLoc.getZ();
        return pX >= minX && pX <= maxX && pY >= minY && pY <= maxY && pZ >= minZ && pZ <= maxZ;
    }

    public boolean isPlayerInAnyMeeting(Player player) {
        for(Map map : maps) {
            if(isPlayerInMeeting(player, map)) {
                return true;
            }
        }
        return false;
    }

    public Set<Map> getMeetingsPlayerIsIn(Player player) {
        if(!isPlayerInAnyMeeting(player)) return null;
        Set<Map> meetingsIn = new HashSet<>();
        for(Map map : maps) {
            if(isPlayerInMeeting(player, map)) {
                meetingsIn.add(map);
            }
        }
        return meetingsIn;
    }

    public void despawnAllHolograms() {
        for(Map map : maps) {
            if(map.getMapQueueHologram() != null) {
                logger.log(Level.INFO, ChatColor.LIGHT_PURPLE + "Queue hologram from map: \"" + map.getName() + "\" is currently spawned. Destroying hologram.");
                map.getMapQueueHologram().clear();
            }
        }
    }
}
