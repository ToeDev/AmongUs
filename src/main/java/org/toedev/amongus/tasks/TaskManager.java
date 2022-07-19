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
import java.util.UUID;

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
                Location teleportLocation = new Location(Bukkit.getWorld(taskSet.getString("taskTeleportWorld")), taskSet.getDouble("taskTeleportX"), taskSet.getDouble("taskTeleportY"), taskSet.getDouble("taskTeleportZ"));
                String taskName = taskSet.getString("taskName");
                Map map = mapManager.getMap(taskSet.getString("mapName"));
                if(taskName.equalsIgnoreCase("wires")) {
                    WiresTask wTask = new WiresTask(Tasks.taskNames.get(WiresTask.class), map, taskLocation, taskAreaMinLocation, taskAreaMaxLocation, teleportLocation);
                    if(allTasks.get(map) != null) {
                        allTasks.get(map).add(wTask);
                    } else {
                        ArrayList<AbstractTask> tasks = new ArrayList<>();
                        tasks.add(wTask);
                        allTasks.put(map, tasks);
                    }
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Task: \"" + taskName + "\" for Map: \"" + map.getName() + "\" imported from the DB");
                } else if(taskName.equalsIgnoreCase("downloaddata")) {
                    DownloadDataTask dTask = new DownloadDataTask(amongUs, Tasks.taskNames.get(DownloadDataTask.class), map, taskLocation, taskAreaMinLocation, taskAreaMaxLocation, teleportLocation);
                    if(allTasks.get(map) != null) {
                        allTasks.get(map).add(dTask);
                    } else {
                        ArrayList<AbstractTask> tasks = new ArrayList<>();
                        tasks.add(dTask);
                        allTasks.put(map, tasks);
                    }
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Task: \"" + taskName + "\" for Map: \"" + map.getName() + "\" imported from the DB");
                } else if(taskName.equalsIgnoreCase("uploaddata")) {
                    UploadDataTask dTask = new UploadDataTask(amongUs, Tasks.taskNames.get(UploadDataTask.class), map, taskLocation, taskAreaMinLocation, taskAreaMaxLocation, teleportLocation);
                    if(allTasks.get(map) != null) {
                        allTasks.get(map).add(dTask);
                    } else {
                        ArrayList<AbstractTask> tasks = new ArrayList<>();
                        tasks.add(dTask);
                        allTasks.put(map, tasks);
                    }
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Task: \"" + taskName + "\" for Map: \"" + map.getName() + "\" imported from the DB");
                } else if(taskName.equalsIgnoreCase("fuelfill")) {
                    FuelFillTask fTask = new FuelFillTask(amongUs, Tasks.taskNames.get(FuelFillTask.class), map, taskLocation, taskAreaMinLocation, taskAreaMaxLocation, teleportLocation);
                    if(allTasks.get(map) != null) {
                        allTasks.get(map).add(fTask);
                    } else {
                        ArrayList<AbstractTask> tasks = new ArrayList<>();
                        tasks.add(fTask);
                        allTasks.put(map, tasks);
                    }
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Task: \"" + taskName + "\" for Map: \"" + map.getName() + "\" imported from the DB");
                } else if(taskName.equalsIgnoreCase("fuelempty")) {
                    FuelEmptyTask fTask = new FuelEmptyTask(amongUs, Tasks.taskNames.get(FuelEmptyTask.class), map, taskLocation, taskAreaMinLocation, taskAreaMaxLocation, teleportLocation);
                    if(allTasks.get(map) != null) {
                        allTasks.get(map).add(fTask);
                    } else {
                        ArrayList<AbstractTask> tasks = new ArrayList<>();
                        tasks.add(fTask);
                        allTasks.put(map, tasks);
                    }
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Task: \"" + taskName + "\" for Map: \"" + map.getName() + "\" imported from the DB");
                } else if(taskName.equalsIgnoreCase("simonsays")) {
                    SimonSaysTask sTask = new SimonSaysTask(amongUs, Tasks.taskNames.get(SimonSaysTask.class), map, taskLocation, taskAreaMinLocation, taskAreaMaxLocation, teleportLocation);
                    if(allTasks.get(map) != null) {
                        allTasks.get(map).add(sTask);
                    } else {
                        ArrayList<AbstractTask> tasks = new ArrayList<>();
                        tasks.add(sTask);
                        allTasks.put(map, tasks);
                    }
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Task: \"" + taskName + "\" for Map: \"" + map.getName() + "\" imported from the DB");
                } else if(taskName.equalsIgnoreCase("keypad")) {
                    KeypadTask tTask = new KeypadTask(Tasks.taskNames.get(KeypadTask.class), map, taskLocation, taskAreaMinLocation, taskAreaMaxLocation, teleportLocation);
                    if(allTasks.get(map) != null) {
                        allTasks.get(map).add(tTask);
                    } else {
                        ArrayList<AbstractTask> tasks = new ArrayList<>();
                        tasks.add(tTask);
                        allTasks.put(map, tasks);
                    }
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Task: \"" + taskName + "\" for Map: \"" + map.getName() + "\" imported from the DB");
                } else if(taskName.equalsIgnoreCase("calibrate")) {
                    CalibrateTask cTask = new CalibrateTask(amongUs, Tasks.taskNames.get(CalibrateTask.class), map, taskLocation, taskAreaMinLocation, taskAreaMaxLocation, teleportLocation);
                    if(allTasks.get(map) != null) {
                        allTasks.get(map).add(cTask);
                    } else {
                        ArrayList<AbstractTask> tasks = new ArrayList<>();
                        tasks.add(cTask);
                        allTasks.put(map, tasks);
                    }
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Task: \"" + taskName + "\" for Map: \"" + map.getName() + "\" imported from the DB");
                } else if(taskName.equalsIgnoreCase("medbayscan")) {
                    MedbayScanTask mTask = new MedbayScanTask(amongUs, Tasks.taskNames.get(MedbayScanTask.class), map, taskLocation, taskAreaMinLocation, taskAreaMaxLocation, teleportLocation);
                    if(allTasks.get(map) != null) {
                        allTasks.get(map).add(mTask);
                    } else {
                        ArrayList<AbstractTask> tasks = new ArrayList<>();
                        tasks.add(mTask);
                        allTasks.put(map, tasks);
                    }
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Task: \"" + taskName + "\" for Map: \"" + map.getName() + "\" imported from the DB");
                } else if(taskName.equalsIgnoreCase("inspectsample")) {
                    InspectSampleTask iTask = new InspectSampleTask(amongUs, Tasks.taskNames.get(InspectSampleTask.class), map, taskLocation, taskAreaMinLocation, taskAreaMaxLocation, teleportLocation);
                    if(allTasks.get(map) != null) {
                        allTasks.get(map).add(iTask);
                    } else {
                        ArrayList<AbstractTask> tasks = new ArrayList<>();
                        tasks.add(iTask);
                        allTasks.put(map, tasks);
                    }
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Task: \"" + taskName + "\" for Map: \"" + map.getName() + "\" imported from the DB");
                } else if(taskName.equalsIgnoreCase("shields")) {
                    ShieldsTask sTask = new ShieldsTask(amongUs, Tasks.taskNames.get(ShieldsTask.class), map, taskLocation, taskAreaMinLocation, taskAreaMaxLocation, teleportLocation);
                    if(allTasks.get(map) != null) {
                        allTasks.get(map).add(sTask);
                    } else {
                        ArrayList<AbstractTask> tasks = new ArrayList<>();
                        tasks.add(sTask);
                        allTasks.put(map, tasks);
                    }
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Task: \"" + taskName + "\" for Map: \"" + map.getName() + "\" imported from the DB");
                } else if(taskName.equalsIgnoreCase("clearasteroids")) {
                    ClearAsteroidsTask cTask = new ClearAsteroidsTask(amongUs, Tasks.taskNames.get(ClearAsteroidsTask.class), map, taskLocation, taskAreaMinLocation, taskAreaMaxLocation, teleportLocation);
                    if(allTasks.get(map) != null) {
                        allTasks.get(map).add(cTask);
                    } else {
                        ArrayList<AbstractTask> tasks = new ArrayList<>();
                        tasks.add(cTask);
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

    public InspectSampleTask getInspectSampleTask(Map map) {
        if(allTasks.get(map) != null) {
            for(AbstractTask task : allTasks.get(map)) {
                if(task instanceof InspectSampleTask) {
                    return (InspectSampleTask) task;
                }
            }
            return null;
        }
        return null;
    }

    public ShieldsTask getShieldsTask(Map map) {
        if(allTasks.get(map) != null) {
            for(AbstractTask task : allTasks.get(map)) {
                if(task instanceof ShieldsTask) {
                    return (ShieldsTask) task;
                }
            }
            return null;
        }
        return null;
    }

    public ClearAsteroidsTask getClearAsteroidsTask(Map map) {
        if(allTasks.get(map) != null) {
            for(AbstractTask task : allTasks.get(map)) {
                if(task instanceof ClearAsteroidsTask) {
                    return (ClearAsteroidsTask) task;
                }
            }
            return null;
        }
        return null;
    }

    public void addWiresTask(Map map, Location location) throws SQLException {
        WiresTask wTask = new WiresTask(Tasks.taskNames.get(WiresTask.class), map, location, null, null, null);
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
        DownloadDataTask dTask = new DownloadDataTask(amongUs, Tasks.taskNames.get(DownloadDataTask.class), map, location, null, null, null);
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
        UploadDataTask uTask = new UploadDataTask(amongUs, Tasks.taskNames.get(UploadDataTask.class), map, location, null, null, null);
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
        FuelFillTask fTask = new FuelFillTask(amongUs, Tasks.taskNames.get(FuelFillTask.class), map, location, null, null, null);
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
        FuelEmptyTask fTask = new FuelEmptyTask(amongUs, Tasks.taskNames.get(FuelEmptyTask.class), map, location, null, null, null);
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
        SimonSaysTask sTask = new SimonSaysTask(amongUs, Tasks.taskNames.get(SimonSaysTask.class), map, location, null, null, null);
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
        KeypadTask kTask = new KeypadTask(Tasks.taskNames.get(KeypadTask.class), map, location, null, null, null);
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
        CalibrateTask cTask = new CalibrateTask(amongUs, Tasks.taskNames.get(CalibrateTask.class), map, location, null, null, null);
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
        MedbayScanTask mTask = new MedbayScanTask(amongUs, Tasks.taskNames.get(MedbayScanTask.class), map, location, null, null, null);
        if(allTasks.get(map) != null) {
            allTasks.get(map).add(mTask);
        } else {
            ArrayList<AbstractTask> tasks = new ArrayList<>();
            tasks.add(mTask);
            allTasks.put(map, tasks);
        }
        addTaskToDB(map, mTask);
    }

    public void addInspectSampleTask(Map map, Location location) throws SQLException {
        InspectSampleTask iTask = new InspectSampleTask(amongUs, Tasks.taskNames.get(InspectSampleTask.class), map, location, null, null, null);
        if(allTasks.get(map) != null) {
            allTasks.get(map).add(iTask);
        } else {
            ArrayList<AbstractTask> tasks = new ArrayList<>();
            tasks.add(iTask);
            allTasks.put(map, tasks);
        }
        addTaskToDB(map, iTask);
    }

    public void addShieldsTask(Map map, Location location) throws SQLException {
        ShieldsTask sTask = new ShieldsTask(amongUs, Tasks.taskNames.get(ShieldsTask.class), map, location, null, null, null);
        if(allTasks.get(map) != null) {
            allTasks.get(map).add(sTask);
        } else {
            ArrayList<AbstractTask> tasks = new ArrayList<>();
            tasks.add(sTask);
            allTasks.put(map, tasks);
        }
        addTaskToDB(map, sTask);
    }

    public void addClearAsteroidsTask(Map map, Location location) throws SQLException {
        ClearAsteroidsTask cTask = new ClearAsteroidsTask(amongUs, Tasks.taskNames.get(ClearAsteroidsTask.class), map, location, null, null, null);
        if(allTasks.get(map) != null) {
            allTasks.get(map).add(cTask);
        } else {
            ArrayList<AbstractTask> tasks = new ArrayList<>();
            tasks.add(cTask);
            allTasks.put(map, tasks);
        }
        addTaskToDB(map, cTask);
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

    public void setTeleport(Map map, String name, Location location) throws SQLException {
        if(getTaskByMap(map, name) != null) {
            getTaskByMap(map, name).setTeleport(location);
            updateTaskInDB(map, getTaskByMap(map, name));
        }
    }

    public void removeTask(Map map, String name, Location location) throws SQLException {
        if(doesTaskExistInMap(map, name, location)) {
            allTasks.get(map).removeIf(t -> t.getLocation().equals(location));
            deleteTaskFromDB(map, name, location);
        }
    }

    public boolean doesTaskExistInMap(Map map, String name, Location location) {
        if(allTasks.get(map) != null) {
            for(AbstractTask task : allTasks.get(map)) {
                if(task.getName().equalsIgnoreCase(name) && task.getLocation().equals(location)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int countTaskInMap(Map map, String name) {
        int i = 0;
        if(allTasks.get(map) != null) {
            for(AbstractTask task : allTasks.get(map)) {
                if(task.getName().equalsIgnoreCase(name)) {
                    i++;
                }
            }
        }
        return i;
    }

    private void addTaskToDB(Map map, AbstractTask task) throws SQLException {
        utility.addTask(map, task);
        Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Task: \"" + task.getName() + "\" for Map: \"" + map.getName() + "\" added to DB");
    }

    private void deleteTaskFromDB(Map map, String name, Location location) throws SQLException {
        utility.removeTask(map, name, location);
        String loc = location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ() + "," + location.getWorld().getName();
        Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Task: \"" + name + "\" At: \"" + loc + "\" for Map: \"" + map.getName() + "\" deleted from the DB");
    }

    private void updateTaskInDB(Map map, AbstractTask task) throws SQLException {
        utility.updateTask(map, task);
        Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Task: \"" + task.getName() + "\" for Map: \"" + map.getName() + "\" updated in the DB");
    }
}
