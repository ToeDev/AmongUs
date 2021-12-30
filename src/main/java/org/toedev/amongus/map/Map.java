package org.toedev.amongus.map;

import org.bukkit.Location;
import org.toedev.amongus.tasks.AbstractTask;

import java.util.HashMap;

public class Map {

    private String name;

    private boolean mapRunning;
    private Location mapStartSign;

    private Location mapMinCorner;
    private Location mapMaxCorner;

    /*private Location startMinCorner;
    private Location startMaxCorner;*/

    private Location meetingMinCorner;
    private Location meetingMaxCorner;

    private HashMap<AbstractTask, Location> tasks;

    public Map(String name) {
        this.name = name;
        this.mapRunning = false;
        this.tasks = new HashMap<>();
    }

    //For DB Import
    public Map(String name, Location mapStartSign, Location mapMinCorner, Location mapMaxCorner, Location meetingMinCorner, Location meetingMaxCorner, HashMap<AbstractTask, Location> tasks) {
        this.name = name;
        this.mapStartSign = mapStartSign;
        this.mapRunning = false;
        this.mapMinCorner = mapMinCorner;
        this.mapMaxCorner = mapMaxCorner;
        /*this.startMinCorner = startMinCorner;
        this.startMaxCorner = startMaxCorner;*/
        this.meetingMinCorner = meetingMinCorner;
        this.meetingMaxCorner = meetingMaxCorner;
        this.tasks = tasks;
    }

    public String getName() {
        return name;
    }

    public Location getMapStartSign() {
        return mapStartSign;
    }

    public void setMapStartSign(Location mapStartSign) {
        this.mapStartSign = mapStartSign;
    }

    public boolean isMapRunning() {
        return mapRunning;
    }

    public void setMapRunning(boolean status) {
        mapRunning = status;
    }

    public Location getMapMinCorner() {
        return mapMinCorner;
    }

    public Location getMapMaxCorner() {
        return mapMaxCorner;
    }

    /*public Location getStartMinCorner() {
        return startMinCorner;
    }

    public Location getStartMaxCorner() {
        return startMaxCorner;
    }*/

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
        return mapStartSign != null && mapMinCorner != null && mapMaxCorner != null && meetingMinCorner != null && meetingMaxCorner != null;
    }
}
