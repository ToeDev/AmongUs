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
    private final GameHandler gameHandler;

    private final List<String> baseCommands;
    private final List<String> setLocCommands;

    private final TestCommand testCommand;
    private final StartCommand startCommand;
    private final StopCommand stopCommand;
    private final ListMapsCommand listMapsCommand;
    private final CreateMapCommand createMapCommand;
    private final SetLocationCommand setLocationCommand;
    private final SetMinimumCommand setMinimumCommand;
    private final SetMaximumCommand setMaximumCommand;

    public CommandHandler(AmongUs amongUs, MapManager mapManager, NPCHandler npcHandler, GameHandler gameHandler) {
        this.logger = amongUs.getLogger();

        this.mapManager = mapManager;
        this.npcHandler = npcHandler;
        this.gameHandler = gameHandler;

        this.baseCommands = new ArrayList<>();
        setLocCommands = new ArrayList<>();
        Objects.requireNonNull(amongUs.getCommand("amongus")).setExecutor(this);
        Objects.requireNonNull(amongUs.getCommand("amongus")).setTabCompleter(this);
        this.testCommand = new TestCommand(npcHandler);
        this.startCommand = new StartCommand(mapManager, gameHandler);
        this.stopCommand = new StopCommand(mapManager, gameHandler);
        this.listMapsCommand = new ListMapsCommand(mapManager);
        this.createMapCommand = new CreateMapCommand(mapManager);
        this.setLocationCommand = new SetLocationCommand(mapManager);
        this.setMinimumCommand = new SetMinimumCommand(mapManager, gameHandler);
        this.setMaximumCommand = new SetMaximumCommand(mapManager, gameHandler);

        this.baseCommands.add("test");
        this.baseCommands.add("start");
        this.baseCommands.add("stop");
        this.baseCommands.add("listmaps");
        this.baseCommands.add("createmap");
        this.baseCommands.add("setlocation");
        this.baseCommands.add("setminimum");
        this.baseCommands.add("setmaximum");

        this.setLocCommands.add("map");
        this.setLocCommands.add("meeting");
        this.setLocCommands.add("startsign");
        this.setLocCommands.add("mapspawn");
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
        if(args[0].equalsIgnoreCase("stop")) {
            stopCommand.execute(sender, args);
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
        if(args[0].equalsIgnoreCase("setminimum")) {
            try {
                setMinimumCommand.execute(sender, args);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        }
        if(args[0].equalsIgnoreCase("setmaximum")) {
            try {
                setMaximumCommand.execute(sender, args);
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
        final ArrayList<String> numberCompletions = new ArrayList<>();
        for(int i = 1; i <= 20; i++) {
            numberCompletions.add(String.valueOf(i));
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
                switch (arg0) {
                    case "start":
                    case "stop":
                        mapCompletions.removeIf(completion -> !completion.startsWith(arg1.toLowerCase()));
                        if (mapCompletions.isEmpty()) {
                            return null;
                        }
                        return mapCompletions;
                    case "setlocation":
                        setLocCompletions.removeIf(completion -> !completion.startsWith(arg1.toLowerCase()));
                        return setLocCompletions;
                    case "setminimum":
                    case "setmaximum":
                        if (arg1.isEmpty()) {
                            numberCompletions.removeIf(number -> Integer.parseInt(number) > 9);
                        }
                        numberCompletions.removeIf(completion -> !completion.startsWith(arg1.toLowerCase()));
                        return numberCompletions;
                    default:
                        return null;
                }
            }

            //ARG INDEX 2
            final String arg2 = argList.remove(0);
            if(argList.isEmpty()) {
                if((arg0.equals("setlocation") && setLocCompletions.contains(arg1)) || ((arg0.equals("setminimum") || arg0.equals("setmaximum")) && numberCompletions.contains(arg1))) {
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
