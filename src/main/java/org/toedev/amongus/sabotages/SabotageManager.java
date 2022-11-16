package org.toedev.amongus.sabotages;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.Prefix;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.map.MapManager;
import org.toedev.amongus.sabotages.sabotages.DoorSabotage;
import org.toedev.amongus.sabotages.sabotages.LightsSabotage;
import org.toedev.amongus.sql.Utility;
import org.toedev.amongus.tasks.AbstractTask;
import org.toedev.amongus.tasks.Tasks;
import org.toedev.amongus.tasks.tasks.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SabotageManager {

    private final AmongUs amongUs;

    private final Utility utility;
    private final MapManager mapManager;

    private final java.util.Map<Map, List<AbstractSabotage>> allSabotages;

    private final ChatColor purple = ChatColor.LIGHT_PURPLE;
    private final ChatColor gold = ChatColor.GOLD;
    private final ChatColor red = ChatColor.RED;

    public SabotageManager(AmongUs amongUs, MapManager mapManager, Utility utility) {
        this.amongUs = amongUs;
        this.utility = utility;
        this.mapManager = mapManager;
        this.allSabotages = new HashMap<>();
        importSabotagesFromDB(utility);
    }

    public void importSabotagesFromDB(Utility utility) {
        try {
            ResultSet sabotageSet = utility.getAllSabotages();
            if(sabotageSet == null) return;
            while(sabotageSet.next()) {
                Location sabotageLocation = new Location(Bukkit.getWorld(sabotageSet.getString("sabotageWorld")), sabotageSet.getDouble("sabotageX"), sabotageSet.getDouble("sabotageY"), sabotageSet.getDouble("sabotageZ"));
                Location sabotageOptionalLocation = new Location(Bukkit.getWorld(sabotageSet.getString("sabotageOptionalWorld")), sabotageSet.getDouble("sabotageOptionalX"), sabotageSet.getDouble("sabotageOptionalY"), sabotageSet.getDouble("sabotageOptionalZ"));
                Location sabotageOptionalLocation2 = new Location(Bukkit.getWorld(sabotageSet.getString("sabotageOptionalWorld2")), sabotageSet.getDouble("sabotageOptionalX2"), sabotageSet.getDouble("sabotageOptionalY2"), sabotageSet.getDouble("sabotageOptionalZ2"));
                Location sabotageOptionalLocation3 = new Location(Bukkit.getWorld(sabotageSet.getString("sabotageOptionalWorld3")), sabotageSet.getDouble("sabotageOptionalX3"), sabotageSet.getDouble("sabotageOptionalY3"), sabotageSet.getDouble("sabotageOptionalZ3"));
                String sabotageName = sabotageSet.getString("sabotageName");
                Map map = mapManager.getMap(sabotageSet.getString("mapName"));
                if(sabotageName.equalsIgnoreCase("lights")) {
                    LightsSabotage lSabotage = new LightsSabotage(Sabotages.sabotageNames.get(LightsSabotage.class), map, sabotageLocation, sabotageOptionalLocation, sabotageOptionalLocation2, sabotageOptionalLocation3);
                    if(allSabotages.get(map) != null) {
                        allSabotages.get(map).add(lSabotage);
                    } else {
                        ArrayList<AbstractSabotage> sabotages = new ArrayList<>();
                        sabotages.add(lSabotage);
                        allSabotages.put(map, sabotages);
                    }
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Sabotage: " + gold + sabotageName + purple + " for Map: " + gold + map.getName() + purple + " imported from the DB");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<AbstractSabotage> getAllSabotages(Map map) {
        if(allSabotages.get(map) != null) {
            return allSabotages.get(map);
        }
        return null;
    }

    public LightsSabotage getLightsSabotage(Map map) {
        if(allSabotages.get(map) != null) {
            for(AbstractSabotage sabotage : allSabotages.get(map)) {
                if(sabotage instanceof LightsSabotage) {
                    return (LightsSabotage) sabotage;
                }
            }
            return null;
        }
        return null;
    }

    public DoorSabotage getDoorSabotage(Map map) {
        if(allSabotages.get(map) != null) {
            for(AbstractSabotage sabotage : allSabotages.get(map)) {
                if(sabotage instanceof DoorSabotage) {
                    return (DoorSabotage) sabotage;
                }
            }
            return null;
        }
        return null;
    }

    public void addLightsSabotage(Map map, Location location) throws SQLException {
        LightsSabotage lSabotage = new LightsSabotage(Sabotages.sabotageNames.get(LightsSabotage.class), map, location, null, null, null);
        if(allSabotages.get(map) != null) {
            allSabotages.get(map).add(lSabotage);
        } else {
            ArrayList<AbstractSabotage> sabotages = new ArrayList<>();
            sabotages.add(lSabotage);
            allSabotages.put(map, sabotages);
        }
        addSabotageToDB(map, lSabotage);
    }

    public void addDoorSabotage(Map map, Location location) throws SQLException {
        DoorSabotage dSabotage = new DoorSabotage(Sabotages.sabotageNames.get(DoorSabotage.class), map, location, null, null, null);
        if(allSabotages.get(map) != null) {
            allSabotages.get(map).add(dSabotage);
        } else {
            ArrayList<AbstractSabotage> sabotages = new ArrayList<>();
            sabotages.add(dSabotage);
            allSabotages.put(map, sabotages);
        }
        addSabotageToDB(map, dSabotage);
    }

    public AbstractSabotage getSabotageByLocation(Location location) {
        for(java.util.Map.Entry<Map, List<AbstractSabotage>> entry : allSabotages.entrySet()) {
            for(AbstractSabotage sabotage : getAllSabotages(entry.getKey())) {
                if(sabotage.getLocation().equals(location)) {
                    return sabotage;
                }
            }
        }
        return null;
    }

    public AbstractSabotage getTaskByOptionalLocation(Location optionalLocation) {
        for(java.util.Map.Entry<Map, List<AbstractSabotage>> entry : allSabotages.entrySet()) {
            for(AbstractSabotage sabotage : getAllSabotages(entry.getKey())) {
                if(sabotage.getOptionalLocation().equals(optionalLocation)) {
                    return sabotage;
                }
            }
        }
        return null;
    }

    public AbstractSabotage getSabotageByMap(Map map, String name) {
        if(allSabotages.get(map) != null) {
            for(AbstractSabotage sabotage : allSabotages.get(map)) {
                if(sabotage.getName().equalsIgnoreCase(name)) {
                    return sabotage;
                }
            }
        }
        return null;
    }

    public void setOptionalLocation2(Map map, String name, Location optionalLocation2) throws SQLException {
        if(getSabotageByMap(map, name) != null) {
            getSabotageByMap(map, name).setOptionalLocation2(optionalLocation2);
            updateSabotageInDB(map, getSabotageByMap(map, name));
        }
    }

    public void setOptionalLocation3(Map map, String name, Location optionalLocation3) throws SQLException {
        if(getSabotageByMap(map, name) != null) {
            getSabotageByMap(map, name).setOptionalLocation3(optionalLocation3);
            updateSabotageInDB(map, getSabotageByMap(map, name));
        }
    }

    public void setOptionalLocation(Map map, String name, Location optionalLocation) throws SQLException {
        if(getSabotageByMap(map, name) != null) {
            getSabotageByMap(map, name).setOptionalLocation(optionalLocation);
            updateSabotageInDB(map, getSabotageByMap(map, name));
        }
    }

    public void removeSabotage(Map map, String name, Location location) throws SQLException {
        if(doesSabotageExistInMap(map, name, location)) {
            allSabotages.get(map).removeIf(s -> s.getLocation().equals(location));
            deleteSabotageFromDB(map, name, location);
        }
    }

    public boolean doesSabotageExistInMap(Map map, String name, Location location) {
        if(allSabotages.get(map) != null) {
            for(AbstractSabotage sabotage : allSabotages.get(map)) {
                if(sabotage.getName().equalsIgnoreCase(name) && sabotage.getLocation().equals(location)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int countSabotageInMap(Map map, String name) {
        int i = 0;
        if(allSabotages.get(map) != null) {
            for(AbstractSabotage sabotage : allSabotages.get(map)) {
                if(sabotage.getName().equalsIgnoreCase(name)) {
                    i++;
                }
            }
        }
        return i;
    }

    private void addSabotageToDB(Map map, AbstractSabotage sabotage) throws SQLException {
        utility.addSabotage(map, sabotage);
        Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Sabotage: \"" + sabotage.getName() + "\" for Map: \"" + map.getName() + "\" added to DB");
    }

    private void deleteSabotageFromDB(Map map, String name, Location location) throws SQLException {
        utility.removeSabotage(map, name, location);
        String loc = location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ() + "," + location.getWorld().getName();
        Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Sabotage: \"" + name + "\" At: \"" + loc + "\" for Map: \"" + map.getName() + "\" deleted from the DB");
    }

    private void updateSabotageInDB(Map map, AbstractSabotage sabotage) throws SQLException {
        utility.updateSabotage(map, sabotage);
        Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Sabotage: \"" + sabotage.getName() + "\" for Map: \"" + map.getName() + "\" updated in the DB");
    }
}
