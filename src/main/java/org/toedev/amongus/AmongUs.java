package org.toedev.amongus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.toedev.amongus.handlers.*;
import org.toedev.amongus.map.MapManager;
import org.toedev.amongus.sql.Utility;
import org.toedev.amongus.tasks.TaskManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AmongUs extends JavaPlugin {

    //TODO CLEANUP NPC'S ONENABLE INSTEAD OF DISABLE

    private Utility utility;
    private KitHandler kitHandler;
    private MapManager mapManager;
    private GameHandler gameHandler;
    private TaskHandler taskHandler;
    private NPCHandler npcHandler;
    private TaskManager taskManager;

    private Integer distanceFromTask;

    private final ChatColor purple = ChatColor.LIGHT_PURPLE;
    private final ChatColor gold = ChatColor.GOLD;
    private final ChatColor red = ChatColor.RED;

    public void onEnable() {

        //Generate config file and core db
        final File dataDir = getDataFolder();
        if(!dataDir.exists()) {
            dataDir.mkdirs();
        }
        File configFile = new File(dataDir, "config.yml");
        if(!configFile.exists()) {
            generateFile(configFile);
        }
        utility = new Utility(this);
        try {
            utility.createBackup(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        utility.load();

        //read config file
        YamlConfiguration mainConfig = new YamlConfiguration();
        try {
            mainConfig.load(configFile);

            distanceFromTask = mainConfig.getInt("max-distance-from-tasks");
            Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Max distance from tasks set at: " + distanceFromTask);
        } catch(IOException | InvalidConfigurationException e) {
            Bukkit.getConsoleSender().sendMessage(Prefix.prefix + red + "Unable to load config file!");
            Bukkit.getConsoleSender().sendMessage(Prefix.prefix + e);
        }

        //Start handlers and managers
        kitHandler = new KitHandler();
        mapManager = new MapManager(this, utility);
        npcHandler = new NPCHandler(this);
        taskManager = new TaskManager(this, mapManager, utility);
        gameHandler = new GameHandler(this, mapManager, taskManager);
        new CommandHandler(this, mapManager, npcHandler, gameHandler, taskManager);
        getServer().getPluginManager().registerEvents(new AbstractEventHandler(mapManager, gameHandler, taskManager), this);
        getServer().getPluginManager().registerEvents(new TaskHandler(this, mapManager, gameHandler, taskManager), this);

        Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Plugin Enabled Successfully");
    }

    public Integer getDistanceFromTask() {
        return this.distanceFromTask;
    }

    private void generateFile(File file) {
        try {
            file.createNewFile();
            final InputStream inputStream = this.getResource(file.getName());
            final FileOutputStream fileOutputStream = new FileOutputStream(file);
            final byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = Objects.requireNonNull(inputStream).read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch(IOException e) {
            Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "Unable to generate file: " + file.getName());
            throw new RuntimeException(purple + "Unable to generate file: " + file.getName(), e);
        }
    }

    public void onDisable() {
        this.npcHandler.despawnAllNPCs();
        this.utility.disconnect();
        Bukkit.getConsoleSender().sendMessage(Prefix.prefix + ChatColor.LIGHT_PURPLE + "Plugin Disabled Successfully");
    }
}
