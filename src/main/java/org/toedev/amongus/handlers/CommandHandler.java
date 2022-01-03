package org.toedev.amongus.handlers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.commands.*;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.map.MapManager;

import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

public class CommandHandler implements TabExecutor {

    private final Logger logger;

    private final MapManager mapManager;
    private final NPCHandler npcHandler;

    private final List<String> baseCommands;
    private final List<String> setLocCommands;

    private final TestCommand testCommand;
    private final StartCommand startCommand;
    private final ListMapsCommand listMapsCommand;
    private final CreateMapCommand createMapCommand;
    private final SetLocationCommand setLocationCommand;

    public CommandHandler(AmongUs amongUs, MapManager mapManager, NPCHandler npcHandler) {
        this.logger = amongUs.getLogger();

        this.mapManager = mapManager;
        this.npcHandler = npcHandler;

        this.baseCommands = new ArrayList<>();
        setLocCommands = new ArrayList<>();
        Objects.requireNonNull(amongUs.getCommand("amongus")).setExecutor(this);
        Objects.requireNonNull(amongUs.getCommand("amongus")).setTabCompleter(this);
        this.testCommand = new TestCommand(npcHandler);
        this.startCommand = new StartCommand();
        this.listMapsCommand = new ListMapsCommand(mapManager);
        this.createMapCommand = new CreateMapCommand(mapManager);
        this.setLocationCommand = new SetLocationCommand(mapManager);

        this.baseCommands.add("test");
        this.baseCommands.add("start");
        this.baseCommands.add("listmaps");
        this.baseCommands.add("createmap");
        this.baseCommands.add("setlocation");

        this.setLocCommands.add("map");
        this.setLocCommands.add("meeting");
        this.setLocCommands.add("startsign");
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

    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        final ArrayList<String> argList = new ArrayList<>(Arrays.asList(args));
        final ArrayList<String> baseCompletions = new ArrayList<>(baseCommands);
        final ArrayList<String> setLocCompletions = new ArrayList<>(setLocCommands);
        ArrayList<String> mapCompletions = new ArrayList<>();
        for(Map map : mapManager.getAllMaps()) {
            mapCompletions.add(map.getName());
        }

        if(sender.hasPermission("amongus.admin")) {

            //NO ARGS
            if(argList.isEmpty()) {
                return baseCompletions;
            }

            //ARG INDEX 0
            final String arg0 = argList.remove(0);
            if(argList.isEmpty()) {
                baseCompletions.removeIf(completion -> !completion.startsWith(arg0.toLowerCase()));
                return baseCompletions;
            }

            //ARG INDEX 1
            final String arg1 = argList.remove(0);
            if(argList.isEmpty()) {
                if(arg0.equals("start")) {
                    mapCompletions.removeIf(completion -> !completion.startsWith(arg1.toLowerCase()));
                    if(mapCompletions.isEmpty()) {
                        return null;
                    }
                    return mapCompletions;
                } else if(arg0.equals("setlocation")) {
                    setLocCompletions.removeIf(completion -> !completion.startsWith(arg1.toLowerCase()));
                    return setLocCompletions;
                } else {
                    return null;
                }
            }

            //ARG INDEX 2
            final String arg2 = argList.remove(0);
            if(argList.isEmpty()) {
                if(arg0.equals("setlocation") && setLocCompletions.contains(arg1)) {
                    mapCompletions.removeIf(completion -> !completion.startsWith(arg2.toLowerCase()));
                    if(mapCompletions.isEmpty()) {
                        return null;
                    }
                    return mapCompletions;
                } else {
                    return null;
                }
            }
        }
        return null;
    }
}
