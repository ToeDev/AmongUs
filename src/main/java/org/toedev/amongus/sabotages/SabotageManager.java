package org.toedev.amongus.sabotages;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.Prefix;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.map.MapManager;
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
                String sabotageName = sabotageSet.getString("sabotageName");
                Map map = mapManager.getMap(sabotageSet.getString("mapName"));
                if(sabotageName.equalsIgnoreCase("lights")) {
                    LightsSabotage lSabotage = new LightsSabotage(Sabotages.sabotageNames.get(LightsSabotage.class), map, sabotageLocation, sabotageOptionalLocation);
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
}
