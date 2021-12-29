package org.toedev.amongus;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.toedev.amongus.handlers.CommandHandler;
import org.toedev.amongus.handlers.EventHandler;
import org.toedev.amongus.handlers.KitHandler;
import org.toedev.amongus.map.MapManager;
import org.toedev.amongus.sql.Database;
import org.toedev.amongus.sql.Utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AmongUs extends JavaPlugin {

    private Logger logger;

    private Utility utility;
    private KitHandler kitHandler;
    private MapManager mapManager;

    public void onEnable() {
        this.logger = getLogger();

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

        //Start handlers
        kitHandler = new KitHandler();
        mapManager = new MapManager(this, utility);
        new EventHandler();
        new CommandHandler(this, mapManager);

        logger.info(ChatColor.LIGHT_PURPLE + "Plugin Enabled Successfully");
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
            logger.log(Level.WARNING, ChatColor.LIGHT_PURPLE + "Unable to generate file: " + file.getName(), e);
            throw new RuntimeException(ChatColor.LIGHT_PURPLE + "Unable to generate file: " + file.getName(), e);
        }
    }

    public void onDisable() {
        this.utility.disconnect();
        logger.info(ChatColor.LIGHT_PURPLE + "Plugin Disabled Successfully");
    }
}
