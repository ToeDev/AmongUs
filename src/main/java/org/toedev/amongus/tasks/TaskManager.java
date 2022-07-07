package org.toedev.amongus.tasks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitScheduler;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.Prefix;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.map.MapManager;
import org.toedev.amongus.sql.Utility;
import org.toedev.amongus.tasks.tasks.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {

    private final AmongUs amongUs;

    private final Utility utility;
    private final MapManager mapManager;

    private final java.util.Map<Map, List<AbstractTask>> allTasks;

    private final ChatColor purple = ChatColor.LIGHT_PURPLE;
    private final ChatColor gold = ChatColor.GOLD;
    private final ChatColor red = ChatColor.RED;

    public TaskManager(AmongUs amongUs, MapManager mapManager, Utility utility) {
        this.amongUs = amongUs;
        this.utility = utility;
        this.mapManager = mapManager;
        this.allTasks = new HashMap<>();
        importTasksFromDB(utility);
    }

    public void importTasksFromDB(Utility utility) {
        try {
            ResultSet taskSet = utility.getAllTasks();
            if(taskSet == null) return;
            while(taskSet.next()) {
                Location taskLocation = new Location(Bukkit.getWorld(taskSet.getString("taskWorld")), taskSet.getDouble("taskX"), taskSet.getDouble("taskY"), taskSet.getDouble("taskZ"));
                Location taskAreaMinLocation = new Location(Bukkit.getWorld(taskSet.getString("taskAreaMinWorld")), taskSet.getDouble("taskAreaMinX"), taskSet.getDouble("taskAreaMinY"), taskSet.getDouble("taskAreaMinZ"));
                Location taskAreaMaxLocation = new Location(Bukkit.getWorld(taskSet.getString("taskAreaMaxWorld")), taskSet.getDouble("taskAreaMaxX"), taskSet.getDouble("taskAreaMaxY"), taskSet.getDouble("taskAreaMaxZ"));
                String taskName = taskSet.getString("taskName");
                Map map = mapManager.getMap(taskSet.getString("mapName"));
                if(taskName.equalsIgnoreCase("wires")) {
                    WiresTask wTask = new WiresTask(Tasks.taskNames.get(WiresTask.class), map, taskLocation, taskAreaMinLocation, taskAreaMaxLocation);
                    if(allTasks.get(map) != null) {
                        allTasks.get(map).add(wTask);
                    } else {
                        ArrayList<AbstractTask> tasks = new ArrayList<>();
                        tasks.add(wTask);
                        allTasks.put(map, tasks);
                    }
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Task: \"" + taskName + "\" for Map: \"" + map.getName() + "\" imported from the DB");
                } else if(taskName.equalsIgnoreCase("downloaddata")) {
                    DownloadDataTask dTask = new DownloadDataTask(amongUs, Tasks.taskNames.get(DownloadDataTask.class), map, taskLocation, taskAreaMinLocation, taskAreaMaxLocation);
                    if(allTasks.get(map) != null) {
                        allTasks.get(map).add(dTask);
                    } else {
                        ArrayList<AbstractTask> tasks = new ArrayList<>();
                        tasks.add(dTask);
                        allTasks.put(map, tasks);
                    }
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Task: \"" + taskName + "\" for Map: \"" + map.getName() + "\" imported from the DB");
                } else if(taskName.equalsIgnoreCase("uploaddata")) {
                    UploadDataTask dTask = new UploadDataTask(amongUs, Tasks.taskNames.get(UploadDataTask.class), map, taskLocation, taskAreaMinLocation, taskAreaMaxLocation);
                    if(allTasks.get(map) != null) {
                        allTasks.get(map).add(dTask);
                    } else {
                        ArrayList<AbstractTask> tasks = new ArrayList<>();
                        tasks.add(dTask);
                        allTasks.put(map, tasks);
                    }
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Task: \"" + taskName + "\" for Map: \"" + map.getName() + "\" imported from the DB");
                } else if(taskName.equalsIgnoreCase("fuelfill")) {
                    FuelFillTask fTask = new FuelFillTask(amongUs, Tasks.taskNames.get(FuelFillTask.class), map, taskLocation, taskAreaMinLocation, taskAreaMaxLocation);
                    if(allTasks.get(map) != null) {
                        allTasks.get(map).add(fTask);
                    } else {
                        ArrayList<AbstractTask> tasks = new ArrayList<>();
                        tasks.add(fTask);
                        allTasks.put(map, tasks);
                    }
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Task: \"" + taskName + "\" for Map: \"" + map.getName() + "\" imported from the DB");
                } else if(taskName.equalsIgnoreCase("fuelempty")) {
                    FuelEmptyTask fTask = new FuelEmptyTask(amongUs, Tasks.taskNames.get(FuelEmptyTask.class), map, taskLocation, taskAreaMinLocation, taskAreaMaxLocation);
                    if(allTasks.get(map) != null) {
                        allTasks.get(map).add(fTask);
                    } else {
                        ArrayList<AbstractTask> tasks = new ArrayList<>();
                        tasks.add(fTask);
                        allTasks.put(map, tasks);
                    }
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Task: \"" + taskName + "\" for Map: \"" + map.getName() + "\" imported from the DB");
                } else if(taskName.equalsIgnoreCase("simonsays")) {
                    SimonSaysTask sTask = new SimonSaysTask(amongUs, Tasks.taskNames.get(SimonSaysTask.class), map, taskLocation, taskAreaMinLocation, taskAreaMaxLocation);
                    if(allTasks.get(map) != null) {
                        allTasks.get(map).add(sTask);
                    } else {
                        ArrayList<AbstractTask> tasks = new ArrayList<>();
                        tasks.add(sTask);
                        allTasks.put(map, tasks);
                    }
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Task: \"" + taskName + "\" for Map: \"" + map.getName() + "\" imported from the DB");
                } else if(taskName.equalsIgnoreCase("keypad")) {
                    KeypadTask tTask = new KeypadTask(Tasks.taskNames.get(KeypadTask.class), map, taskLocation, taskAreaMinLocation, taskAreaMaxLocation);
                    if(allTasks.get(map) != null) {
                        allTasks.get(map).add(tTask);
                    } else {
                        ArrayList<AbstractTask> tasks = new ArrayList<>();
                        tasks.add(tTask);
                        allTasks.put(map, tasks);
                    }
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Task: \"" + taskName + "\" for Map: \"" + map.getName() + "\" imported from the DB");
                } else if(taskName.equalsIgnoreCase("calibrate")) {
                    CalibrateTask cTask = new CalibrateTask(amongUs, Tasks.taskNames.get(CalibrateTask.class), map, taskLocation, taskAreaMinLocation, taskAreaMaxLocation);
                    if(allTasks.get(map) != null) {
                        allTasks.get(map).add(cTask);
                    } else {
                        ArrayList<AbstractTask> tasks = new ArrayList<>();
                        tasks.add(cTask);
                        allTasks.put(map, tasks);
                    }
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Task: \"" + taskName + "\" for Map: \"" + map.getName() + "\" imported from the DB");
                } else if(taskName.equalsIgnoreCase("medbayscan")) {
                    MedbayScanTask mTask = new MedbayScanTask(amongUs, Tasks.taskNames.get(MedbayScanTask.class), map, taskLocation, taskAreaMinLocation, taskAreaMaxLocation);
                    if(allTasks.get(map) != null) {
                        allTasks.get(map).add(mTask);
                    } else {
                        ArrayList<AbstractTask> tasks = new ArrayList<>();
                        tasks.add(mTask);
                        allTasks.put(map, tasks);
                    }
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Task: \"" + taskName + "\" for Map: \"" + map.getName() + "\" imported from the DB");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<AbstractTask> getAllTasks(Map map) {
        if(allTasks.get(map) != null) {
            return allTasks.get(map);
        }
        return null;
    }

    public WiresTask getWiresTask(Map map) {
        if(allTasks.get(map) != null) {
            for(AbstractTask task : allTasks.get(map)) {
                if(task instanceof WiresTask) {
                    return (WiresTask) task;
                }
            }
            return null;
        }
        return null;
    }

    public DownloadDataTask getDownloadDataTask(Map map) {
        if(allTasks.get(map) != null) {
            for(AbstractTask task : allTasks.get(map)) {
                if(task instanceof DownloadDataTask) {
                    return (DownloadDataTask) task;
                }
            }
            return null;
        }
        return null;
    }

    public UploadDataTask getUploadDataTask(Map map) {
        if(allTasks.get(map) != null) {
            for(AbstractTask task : allTasks.get(map)) {
                if(task instanceof UploadDataTask) {
                    return (UploadDataTask) task;
                }
            }
            return null;
        }
        return null;
    }

    public FuelFillTask getFuelFillTask(Map map) {
        if(allTasks.get(map) != null) {
            for(AbstractTask task : allTasks.get(map)) {
                if(task instanceof FuelFillTask) {
                    return (FuelFillTask) task;
                }
            }
            return null;
        }
        return null;
    }

    public FuelEmptyTask getFuelEmptyTask(Map map) {
        if(allTasks.get(map) != null) {
            for(AbstractTask task : allTasks.get(map)) {
                if(task instanceof FuelEmptyTask) {
                    return (FuelEmptyTask) task;
                }
            }
            return null;
        }
        return null;
    }

    public SimonSaysTask getSimonSaysTask(Map map) {
        if(allTasks.get(map) != null) {
            for(AbstractTask task : allTasks.get(map)) {
                if(task instanceof SimonSaysTask) {
                    return (SimonSaysTask) task;
                }
            }
            return null;
        }
        return null;
    }

    public KeypadTask getKeypadTask(Map map) {
        if(allTasks.get(map) != null) {
            for(AbstractTask task : allTasks.get(map)) {
                if(task instanceof KeypadTask) {
                    return (KeypadTask) task;
                }
            }
            return null;
        }
        return null;
    }

    public CalibrateTask getCalibrateTask(Map map) {
        if(allTasks.get(map) != null) {
            for(AbstractTask task : allTasks.get(map)) {
                if(task instanceof CalibrateTask) {
                    return (CalibrateTask) task;
                }
            }
            return null;
        }
        return null;
    }

    public MedbayScanTask getMedbayScanTask(Map map) {
        if(allTasks.get(map) != null) {
            for(AbstractTask task : allTasks.get(map)) {
                if(task instanceof MedbayScanTask) {
                    return (MedbayScanTask) task;
                }
            }
            return null;
        }
        return null;
    }

    public void addWiresTask(Map map, Location location) throws SQLException {
        WiresTask wTask = new WiresTask(Tasks.taskNames.get(WiresTask.class), map, location, null, null);
        if(allTasks.get(map) != null) {
            allTasks.get(map).add(wTask);
        } else {
            ArrayList<AbstractTask> tasks = new ArrayList<>();
            tasks.add(wTask);
            allTasks.put(map, tasks);
        }
        addTaskToDB(map, wTask);
    }

    public void addDownloadDataTask(Map map, Location location) throws SQLException {
        DownloadDataTask dTask = new DownloadDataTask(amongUs, Tasks.taskNames.get(DownloadDataTask.class), map, location, null, null);
        if(allTasks.get(map) != null) {
            allTasks.get(map).add(dTask);
        } else {
            ArrayList<AbstractTask> tasks = new ArrayList<>();
            tasks.add(dTask);
            allTasks.put(map, tasks);
        }
        addTaskToDB(map, dTask);
    }

    public void addUploadDataTask(Map map, Location location) throws SQLException {
        UploadDataTask uTask = new UploadDataTask(amongUs, Tasks.taskNames.get(UploadDataTask.class), map, location, null, null);
        if(allTasks.get(map) != null) {
            allTasks.get(map).add(uTask);
        } else {
            ArrayList<AbstractTask> tasks = new ArrayList<>();
            tasks.add(uTask);
            allTasks.put(map, tasks);
        }
        addTaskToDB(map, uTask);
    }

    public void addFuelFillTask(Map map, Location location) throws SQLException {
        FuelFillTask fTask = new FuelFillTask(amongUs, Tasks.taskNames.get(FuelFillTask.class), map, location, null, null);
        if(allTasks.get(map) != null) {
            allTasks.get(map).add(fTask);
        } else {
            ArrayList<AbstractTask> tasks = new ArrayList<>();
            tasks.add(fTask);
            allTasks.put(map, tasks);
        }
        addTaskToDB(map, fTask);
    }

    public void addFuelEmptyTask(Map map, Location location) throws SQLException {
        FuelEmptyTask fTask = new FuelEmptyTask(amongUs, Tasks.taskNames.get(FuelEmptyTask.class), map, location, null, null);
        if(allTasks.get(map) != null) {
            allTasks.get(map).add(fTask);
        } else {
            ArrayList<AbstractTask> tasks = new ArrayList<>();
            tasks.add(fTask);
            allTasks.put(map, tasks);
        }
        addTaskToDB(map, fTask);
    }

    public void addSimonSaysTask(Map map, Location location) throws SQLException {
        SimonSaysTask sTask = new SimonSaysTask(amongUs, Tasks.taskNames.get(SimonSaysTask.class), map, location, null, null);
        if(allTasks.get(map) != null) {
            allTasks.get(map).add(sTask);
        } else {
            ArrayList<AbstractTask> tasks = new ArrayList<>();
            tasks.add(sTask);
            allTasks.put(map, tasks);
        }
        addTaskToDB(map, sTask);
    }

    public void addKeypadTask(Map map, Location location) throws SQLException {
        KeypadTask kTask = new KeypadTask(Tasks.taskNames.get(KeypadTask.class), map, location, null, null);
        if(allTasks.get(map) != null) {
            allTasks.get(map).add(kTask);
        } else {
            ArrayList<AbstractTask> tasks = new ArrayList<>();
            tasks.add(kTask);
            allTasks.put(map, tasks);
        }
        addTaskToDB(map, kTask);
    }

    public void addCalibrateTask(Map map, Location location) throws SQLException {
        CalibrateTask cTask = new CalibrateTask(amongUs, Tasks.taskNames.get(CalibrateTask.class), map, location, null, null);
        if(allTasks.get(map) != null) {
            allTasks.get(map).add(cTask);
        } else {
            ArrayList<AbstractTask> tasks = new ArrayList<>();
            tasks.add(cTask);
            allTasks.put(map, tasks);
        }
        addTaskToDB(map, cTask);
    }

    public void addMedbayScanTask(Map map, Location location) throws SQLException {
        MedbayScanTask mTask = new MedbayScanTask(amongUs, Tasks.taskNames.get(MedbayScanTask.class), map, location, null, null);
        if(allTasks.get(map) != null) {
            allTasks.get(map).add(mTask);
        } else {
            ArrayList<AbstractTask> tasks = new ArrayList<>();
            tasks.add(mTask);
            allTasks.put(map, tasks);
        }
        addTaskToDB(map, mTask);
    }

    public AbstractTask getTaskByLocation(Location location) {
        for(java.util.Map.Entry<Map, List<AbstractTask>> entry : allTasks.entrySet()) {
            for(AbstractTask task : getAllTasks(entry.getKey())) {
                if(task.getLocation().equals(location)) {
                    return task;
                }
            }
        }
        return null;
    }

    public AbstractTask getTaskByTaskAreaLocation(Location location) {
        for(java.util.Map.Entry<Map, List<AbstractTask>> entry : allTasks.entrySet()) {
            for(AbstractTask task : getAllTasks(entry.getKey())) {
                if(task.getTaskAreaLocs().contains(location)) {
                    return task;
                }
            }
        }
        return null;
    }

    public AbstractTask getTaskByName(String name) {
        for(java.util.Map.Entry<Map, List<AbstractTask>> entry : allTasks.entrySet()) {
            for(AbstractTask task : getAllTasks(entry.getKey())) {
                if(task.getName().equalsIgnoreCase(name)) {
                    return task;
                }
            }
        }
        return null;
    }

    public AbstractTask getTaskByMap(Map map, String name) {
        if(allTasks.get(map) != null) {
            for(AbstractTask task : allTasks.get(map)) {
                if(task.getName().equalsIgnoreCase(name)) {
                    return task;
                }
            }
        }
        return null;
    }

    public void setTaskArea(Map map, String name, Location taskAreaMin, Location taskAreaMax) throws SQLException {
        if(getTaskByMap(map, name) != null) {
            getTaskByMap(map, name).setTaskAreaCorners(taskAreaMin, taskAreaMax);
            updateTaskInDB(map, getTaskByMap(map, name));
        }
    }

    public void removeTask(Map map, String name) throws SQLException {
        if(doesTaskExistInMap(map, name)) {
            allTasks.get(map).remove(getTaskByName(name));
            deleteTaskFromDB(map, name);
        }
    }

    public boolean doesTaskExistInMap(Map map, String name) {
        if(allTasks.get(map) != null) {
            for(AbstractTask task : allTasks.get(map)) {
                if(task.getName().equalsIgnoreCase(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void addTaskToDB(Map map, AbstractTask task) throws SQLException {
        utility.addTask(map, task);
        Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Task: \"" + task.getName() + "\" for Map: \"" + map.getName() + "\" added to DB");
    }

    private void deleteTaskFromDB(Map map, String name) throws SQLException {
        utility.removeTask(map, name);
        Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Task: \"" + name + "\" for Map: \"" + map.getName() + "\" deleted from the DB");
    }

    private void updateTaskInDB(Map map, AbstractTask task) throws SQLException {
        utility.updateTask(map, task);
        Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Task: \"" + task.getName() + "\" for Map: \"" + map.getName() + "\" updated in the DB");
    }
}
