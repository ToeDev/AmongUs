package org.toedev.amongus.handlers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.commands.CreateMapCommand;
import org.toedev.amongus.commands.ListMapsCommand;
import org.toedev.amongus.commands.SetLocationCommand;
import org.toedev.amongus.commands.StartCommand;
import org.toedev.amongus.map.MapManager;

import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Logger;

public class CommandHandler implements CommandExecutor {

    private final Logger logger;

    private final MapManager mapManager;

    private final StartCommand startCommand;
    private final ListMapsCommand listMapsCommand;
    private final CreateMapCommand createMapCommand;
    private final SetLocationCommand setLocationCommand;

    public CommandHandler(AmongUs amongUs, MapManager mapManager) {
        this.logger = amongUs.getLogger();

        this.mapManager = mapManager;

        Objects.requireNonNull(amongUs.getCommand("amongus")).setExecutor(this);
        this.startCommand = new StartCommand();
        this.listMapsCommand = new ListMapsCommand(mapManager);
        this.createMapCommand = new CreateMapCommand(mapManager);
        this.setLocationCommand = new SetLocationCommand(mapManager);
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, String[] args) {
        if(args.length == 0) {
            sender.sendMessage("here are argument options");
            return true;
        }
        if(args[0].equalsIgnoreCase("start")) {
            startCommand.execute(sender, args);
            return true;
        }
        if(args[0].equalsIgnoreCase("listmaps")) {
            listMapsCommand.execute(sender, args);
            return true;
        }
        if(args[0].equalsIgnoreCase("createmap")) {
            createMapCommand.execute(sender, args);
            return true;
        }
        if(args[0].equalsIgnoreCase("setlocation")) {
            try {
                setLocationCommand.execute(sender, args);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        }
        sender.sendMessage("here are argument options");
        return true;
    }
}
