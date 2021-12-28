package org.toedev.amongus.map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.sql.Utility;
import org.toedev.amongus.tasks.AbstractTask;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MapManager {

    private final AmongUs amongUs;
    private final Utility utility;
    private final MapManager mapManager;

    private final Set<Map> maps;

    public MapManager(AmongUs amongUs, Utility utility) {
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
                Location mapMinCorner = new Location(Bukkit.getWorld(mapSet.getString("mapMinCornerWorld")), mapSet.getDouble("mapMinCornerX"), mapSet.getDouble("mapMinCornerY"), mapSet.getDouble("mapMinCornerZ"));
                Location mapMaxCorner = new Location(Bukkit.getWorld(mapSet.getString("mapMaxCornerWorld")), mapSet.getDouble("mapMaxCornerX"), mapSet.getDouble("mapMaxCornerY"), mapSet.getDouble("mapMaxCornerZ"));
                Location startMinCorner = new Location(Bukkit.getWorld(mapSet.getString("startMinCornerWorld")), mapSet.getDouble("startMinCornerX"), mapSet.getDouble("startMinCornerY"), mapSet.getDouble("startMinCornerZ"));
                Location startMaxCorner = new Location(Bukkit.getWorld(mapSet.getString("startMaxCornerWorld")), mapSet.getDouble("startMaxCornerX"), mapSet.getDouble("startMaxCornerY"), mapSet.getDouble("startMaxCornerZ"));
                Location meetingMinCorner = new Location(Bukkit.getWorld(mapSet.getString("meetingMinCornerWorld")), mapSet.getDouble("meetingMinCornerX"), mapSet.getDouble("meetingMinCornerY"), mapSet.getDouble("meetingMinCornerZ"));
                Location meetingMaxCorner = new Location(Bukkit.getWorld(mapSet.getString("meetingMaxCornerWorld")), mapSet.getDouble("meetingMaxCornerX"), mapSet.getDouble("meetingMaxCornerY"), mapSet.getDouble("meetingMaxCornerZ"));
                HashMap<AbstractTask, Location> tasks = new HashMap<>(); //TODO IMPORT TASKS FROM SQL
                Map map = new Map(mapSet.getString("mapName"), mapMinCorner, mapMaxCorner, startMinCorner, startMaxCorner, meetingMinCorner, meetingMaxCorner, tasks);
                this.maps.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public void setStartCorners(String name, Location startMinCorner, Location startMaxCorner) throws SQLException {
        for(Map map : maps) {
            if(map.getName().equals(name)) {
                map.setStartMinCorner(startMinCorner);
                map.setStartMaxCorner(startMaxCorner);
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
            System.out.println("updating map");
        } else {
            System.out.println("adding map");
            utility.addMap(map);
        }
    }
}
