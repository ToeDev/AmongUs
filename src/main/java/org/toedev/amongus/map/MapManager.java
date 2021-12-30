package org.toedev.amongus.map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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
                Location mapStartSign = new Location(Bukkit.getWorld(mapSet.getString("mapStartSignWorld")), mapSet.getDouble("mapStartSignX"), mapSet.getDouble("mapStartSignY"), mapSet.getDouble("mapStartSignZ"));
                Location mapMinCorner = new Location(Bukkit.getWorld(mapSet.getString("mapMinCornerWorld")), mapSet.getDouble("mapMinCornerX"), mapSet.getDouble("mapMinCornerY"), mapSet.getDouble("mapMinCornerZ"));
                Location mapMaxCorner = new Location(Bukkit.getWorld(mapSet.getString("mapMaxCornerWorld")), mapSet.getDouble("mapMaxCornerX"), mapSet.getDouble("mapMaxCornerY"), mapSet.getDouble("mapMaxCornerZ"));
                Location meetingMinCorner = new Location(Bukkit.getWorld(mapSet.getString("meetingMinCornerWorld")), mapSet.getDouble("meetingMinCornerX"), mapSet.getDouble("meetingMinCornerY"), mapSet.getDouble("meetingMinCornerZ"));
                Location meetingMaxCorner = new Location(Bukkit.getWorld(mapSet.getString("meetingMaxCornerWorld")), mapSet.getDouble("meetingMaxCornerX"), mapSet.getDouble("meetingMaxCornerY"), mapSet.getDouble("meetingMaxCornerZ"));
                HashMap<AbstractTask, Location> tasks = new HashMap<>(); //TODO IMPORT TASKS FROM SQL
                Map map = new Map(mapSet.getString("mapName"), mapStartSign, mapMinCorner, mapMaxCorner, meetingMinCorner, meetingMaxCorner, tasks);
                this.maps.add(map);
                logger.log(Level.INFO, ChatColor.LIGHT_PURPLE + "Map \"" + map.getName() + "\" imported from the DB");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public void startMap(Map map) {
        getMap(map.getName()).setMapRunning(true);
    }

    /*public Set<Player> getAllPlayersInStart(Map map) {
        Set<Player> playersInStart = new HashSet<>();
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(isPlayerInStart(player, map)) {
                playersInStart.add(player);
            }
        }
        if(playersInStart.isEmpty()) return null;
        return playersInStart;
    }*/

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

    /*public void setStartCorners(String name, Location startMinCorner, Location startMaxCorner) throws SQLException {
        for(Map map : maps) {
            if(map.getName().equals(name)) {
                map.setStartMinCorner(startMinCorner);
                map.setStartMaxCorner(startMaxCorner);
                if(map.isMapSetup()) saveMap(map);
                return;
            }
        }
    }*/

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
        } else {
            utility.addMap(map);
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

    /*public boolean isPlayerInStart(Player player, Map map) {
        if(!maps.contains(map)) return false;
        if(!player.getWorld().getName().equalsIgnoreCase(Objects.requireNonNull(map.getStartMinCorner().getWorld()).getName())) return false;
        Location startMin = map.getStartMinCorner();
        Location startMax = map.getStartMaxCorner();
        Location pLoc = player.getLocation();
        double minX = startMin.getX();
        double minY = startMin.getY();
        double minZ = startMin.getZ();
        double maxX = startMax.getX();
        double maxY = startMax.getY();
        double maxZ = startMax.getZ();
        double pX = pLoc.getX();
        double pY = pLoc.getY();
        double pZ = pLoc.getZ();
        return pX >= minX && pX <= maxX && pY >= minY && pY <= maxY && pZ >= minZ && pZ <= maxZ;
    }*/

    /*public boolean isPlayerInAnyStart(Player player) {
        for(Map map : maps) {
            if(isPlayerInStart(player, map)) {
                return true;
            }
        }
        return false;
    }*/

    /*public Set<Map> getStartsPlayerIsIn(Player player) {
        if(!isPlayerInAnyStart(player)) return null;
        Set<Map> startsIn = new HashSet<>();
        for(Map map : maps) {
            if(isPlayerInStart(player, map)) {
                startsIn.add(map);
            }
        }
        return startsIn;
    }*/
}
