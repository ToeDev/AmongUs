package org.toedev.amongus.map;

import org.bukkit.Location;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.sql.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class MapManager {

    private final AmongUs amongUs;
    private final Utility utility;
    private final MapManager mapManager;

    private Set<Map> maps;

    public MapManager(AmongUs amongUs, Utility utility) {
        this.amongUs = amongUs;
        this.utility = utility;
        this.maps = new HashSet<>();
        this.mapManager = this;
    }

    public void importMapsFromDB(Utility utility) {
        try {
            ResultSet mapSet = utility.getAllMaps();
            if(mapSet == null) return;
            while(mapSet.next()) {
                Map map = new Map(mapSet.getString("world"), );
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

    public void setMapCorners(String name, Location mapMinCorner, Location mapMaxCorner) {
        for(Map map : maps) {
            if(map.getName().equals(name)) {
                map.setMapMinCorner(mapMinCorner);
                map.setMapMaxCorner(mapMaxCorner);
                return;
            }
        }
    }

    public void setStartCorners(String name, Location startMinCorner, Location startMaxCorner) {
        for(Map map : maps) {
            if(map.getName().equals(name)) {
                map.setStartMinCorner(startMinCorner);
                map.setStartMaxCorner(startMaxCorner);
                return;
            }
        }
    }

    public void setMeetingCorners(String name, Location meetingMinCorner, Location meetingMaxCorner) {
        for(Map map : maps) {
            if(map.getName().equals(name)) {
                map.setMeetingMinCorner(meetingMinCorner);
                map.setMeetingMaxCorner(meetingMaxCorner);
                return;
            }
        }
    }
}
