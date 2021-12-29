package org.toedev.amongus.handlers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.commands.*;
import org.toedev.amongus.map.MapManager;

import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Logger;

public class CommandHandler implements CommandExecutor {

    private final Logger logger;

    private final MapManager mapManager;
    private final NPCHandler npcHandler;

    private final TestCommand testCommand;
    private final StartCommand startCommand;
    private final ListMapsCommand listMapsCommand;
    private final CreateMapCommand createMapCommand;
    private final SetLocationCommand setLocationCommand;

    public CommandHandler(AmongUs amongUs, MapManager mapManager, NPCHandler npcHandler) {
        this.logger = amongUs.getLogger();

        this.mapManager = mapManager;
        this.npcHandler = npcHandler;

        Objects.requireNonNull(amongUs.getCommand("amongus")).setExecutor(this);
        this.testCommand = new TestCommand(npcHandler);
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
        if(args[0].equalsIgnoreCase("test")) {
            testCommand.execute(sender, args);
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
