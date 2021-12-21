package org.toedev.amongus.handlers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.commands.StartCommand;

import java.util.Objects;
import java.util.logging.Logger;

public class CommandHandler implements CommandExecutor {

    private final Logger logger;

    private final StartCommand startCommand;

    public CommandHandler(AmongUs amongUs) {
        this.logger = amongUs.getLogger();

        Objects.requireNonNull(amongUs.getCommand("amongus")).setExecutor(this);
        this.startCommand = new StartCommand();
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, String[] args) {
        if(args.length == 0) {
            sender.sendMessage("here are argument options");
            return true;
        }
        if(args[0].equalsIgnoreCase("start")) {
            startCommand.start(sender, args);
            return true;
        }
        sender.sendMessage("here are argument options");
        return true;
    }
}
