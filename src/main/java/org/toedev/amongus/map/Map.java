package org.toedev.amongus.map;

import org.bukkit.Location;
import org.toedev.amongus.hologram.Hologram;
import org.toedev.amongus.tasks.AbstractTask;

import java.util.HashMap;
import java.util.List;

public class Map {

    private String name;

    private Integer minPlayers;
    private Integer maxPlayers;

    private boolean mapRunning;

    private Location mapStartSign;
    private Hologram mapQueueHologram;

    private Location mapSpawn;

    private Location mapMinCorner;
    private Location mapMaxCorner;

    private Location meetingMinCorner;
    private Location meetingMaxCorner;

    private HashMap<AbstractTask, Location> tasks;

    public Map(String name) {
        this.name = name;
        this.minPlayers = 4;
        this.maxPlayers = 10;
        this.mapRunning = false;
        this.tasks = new HashMap<>();
    }

    //For DB Import
    public Map(String name, Integer minPlayers, Integer maxPlayers, Location mapStartSign, Location mapSpawn, Location mapMinCorner, Location mapMaxCorner, Location meetingMinCorner, Location meetingMaxCorner, HashMap<AbstractTask, Location> tasks) {
        this.name = name;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.mapRunning = false;
        this.mapStartSign = mapStartSign;
        this.mapSpawn = mapSpawn;
        this.mapMinCorner = mapMinCorner;
        this.mapMaxCorner = mapMaxCorner;
        this.meetingMinCorner = meetingMinCorner;
        this.meetingMaxCorner = meetingMaxCorner;
        this.tasks = tasks;
    }

    public String getName() {
        return name;
    }

    public Integer getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(Integer minPlayers) {
        this.minPlayers = minPlayers;
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(Integer maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public Location getMapStartSign() {
        return mapStartSign;
    }

    public void setMapStartSign(Location mapStartSign) {
        this.mapStartSign = mapStartSign;
        setMapQueueHologram(mapStartSign);
    }

    public Hologram getMapQueueHologram() {
        return mapQueueHologram;
    }

    public void setMapQueueHologram(Location mapStartSign) {
        Location holoLoc = mapStartSign.clone();
        holoLoc.setX(mapStartSign.getX() + .5);
        holoLoc.setZ(mapStartSign.getZ() + .5);
        holoLoc.setY(mapStartSign.getY() + 3);
        if(getMapQueueHologram() == null) {
            mapQueueHologram = new Hologram(holoLoc, this);
        } else {
            mapQueueHologram.setLocation(holoLoc);
        }
    }

    public void updateMapQueueHologram(List<String> lines) {
        if(mapQueueHologram != null) {
            mapQueueHologram.setDisplay(lines);
        }
    }

    public boolean isMapRunning() {
        return mapRunning;
    }

    public void setMapRunning(boolean status) {
        mapRunning = status;
    }

    public Location getMapSpawn() {
        return mapSpawn;
    }

    public void setMapSpawn(Location location) {
        mapSpawn = location;
    }

    public Location getMapMinCorner() {
        return mapMinCorner;
    }

    public Location getMapMaxCorner() {
        return mapMaxCorner;
    }


    public Location getMeetingMinCorner() {
        return meetingMinCorner;
    }

    public Location getMeetingMaxCorner() {
        return meetingMaxCorner;
    }

    public HashMap<AbstractTask, Location> getTasks() {
        return tasks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMapMinCorner(Location mapMinCorner) {
        this.mapMinCorner = mapMinCorner;
    }

    public void setMapMaxCorner(Location mapMaxCorner) {
        this.mapMaxCorner = mapMaxCorner;
    }

    /*public void setStartMinCorner(Location startMinCorner) {
        this.startMinCorner = startMinCorner;
    }

    public void setStartMaxCorner(Location startMaxCorner) {
        this.startMaxCorner = startMaxCorner;
    }*/

    public void setMeetingMinCorner(Location meetingMinCorner) {
        this.meetingMinCorner = meetingMinCorner;
    }

    public void setMeetingMaxCorner(Location meetingMaxCorner) {
        this.meetingMaxCorner = meetingMaxCorner;
    }

    public void addTask(AbstractTask task, Location location) {
        tasks.put(task, location);
    }

    public void removeTask(AbstractTask task) {
        tasks.remove(task);
    }

    public boolean isMapSetup() { //TODO ADD TASKS HASHMAP
        return mapStartSign != null && mapSpawn != null && mapMinCorner != null && mapMaxCorner != null && meetingMinCorner != null && meetingMaxCorner != null;
    }
}
