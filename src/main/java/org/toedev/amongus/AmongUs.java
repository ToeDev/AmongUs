package org.toedev.amongus;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.toedev.amongus.handlers.CommandHandler;

import java.util.logging.Logger;

public class AmongUs extends JavaPlugin {

    private Logger logger;

    public void onEnable() {
        this.logger = getLogger();

        new CommandHandler(this);

        logger.info(ChatColor.LIGHT_PURPLE + "Plugin Enabled Successfully");
    }

    public void onDisable() {
        logger.info(ChatColor.LIGHT_PURPLE + "Plugin Disabled Successfully");
    }
}
