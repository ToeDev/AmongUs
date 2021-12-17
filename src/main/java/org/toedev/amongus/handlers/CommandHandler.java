package org.toedev.amongus.handlers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.toedev.amongus.AmongUs;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandHandler implements CommandExecutor {

    private final Logger logger;

    public CommandHandler(AmongUs amongUs) {
        this.logger = amongUs.getLogger();
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, String[] args) {
        logger.log(Level.INFO, "test");
        return false;
    }
}
