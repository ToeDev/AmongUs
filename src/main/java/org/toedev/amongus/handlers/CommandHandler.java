package org.toedev.amongus.handlers;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.commands.*;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.map.MapManager;
import org.toedev.amongus.sabotages.SabotageManager;
import org.toedev.amongus.sabotages.Sabotages;
import org.toedev.amongus.tasks.TaskManager;
import org.toedev.amongus.tasks.Tasks;

import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

public class CommandHandler implements TabExecutor {

    private final MapManager mapManager;
    private final NPCHandler npcHandler;
    private final GameHandler gameHandler;
    private final TaskManager taskManager;
    private final SabotageManager sabotageManager;

    private final List<String> baseCommands;
    private final List<String> setLocCommands;
    private final List<String> taskListCommands;
    private final List<String> sabotageListCommands;
    private final List<String> setTaskCommands;
    private final List<String> setSabotageCommands;

    private final TestCommand testCommand;
    private final StartCommand startCommand;
    private final StopCommand stopCommand;
    private final ListMapsCommand listMapsCommand;
    private final MapInfoCommand mapInfoCommand;
    private final PlayerInfoCommand playerInfoCommand;
    private final CreateMapCommand createMapCommand;
    private final SetLocationCommand setLocationCommand;
    private final SetMinimumCommand setMinimumCommand;
    private final SetMaximumCommand setMaximumCommand;
    private final CreateTaskCommand createTaskCommand;
    private final RemoveTaskCommand removeTaskCommand;
    private final SetTaskCommand setTaskCommand;
    private final CreateSabotageCommand createSabotageCommand;
    private final RemoveSabotageCommand removeSabotageCommand;
    private final SetSabotageCommand setSabotageCommand;

    public CommandHandler(AmongUs amongUs, MapManager mapManager, NPCHandler npcHandler, GameHandler gameHandler, TaskManager taskManager, SabotageManager sabotageManager) {
        this.mapManager = mapManager;
        this.npcHandler = npcHandler;
        this.gameHandler = gameHandler;
        this.taskManager = taskManager;
        this.sabotageManager = sabotageManager;

        this.baseCommands = new ArrayList<>();
        this.setLocCommands = new ArrayList<>();
        this.taskListCommands = new ArrayList<>();
        this.sabotageListCommands = new ArrayList<>();
        this.setTaskCommands = new ArrayList<>();
        this.setSabotageCommands = new ArrayList<>();
        Objects.requireNonNull(amongUs.getCommand("amongus")).setExecutor(this);
        Objects.requireNonNull(amongUs.getCommand("amongus")).setTabCompleter(this);
        this.testCommand = new TestCommand(gameHandler, npcHandler, taskManager, sabotageManager, mapManager);
        this.startCommand = new StartCommand(mapManager, gameHandler);
        this.stopCommand = new StopCommand(mapManager, gameHandler);
        this.listMapsCommand = new ListMapsCommand(mapManager);
        this.mapInfoCommand = new MapInfoCommand(mapManager, taskManager);
        this.playerInfoCommand = new PlayerInfoCommand(mapManager, taskManager, gameHandler);
        this.createMapCommand = new CreateMapCommand(mapManager);
        this.setLocationCommand = new SetLocationCommand(mapManager);
        this.setMinimumCommand = new SetMinimumCommand(mapManager, gameHandler);
        this.setMaximumCommand = new SetMaximumCommand(mapManager, gameHandler);
        this.createTaskCommand = new CreateTaskCommand(mapManager, taskManager);
        this.removeTaskCommand = new RemoveTaskCommand(mapManager, taskManager);
        this.setTaskCommand = new SetTaskCommand(mapManager, taskManager);
        this.createSabotageCommand = new CreateSabotageCommand(mapManager, sabotageManager);
        this.removeSabotageCommand = new RemoveSabotageCommand(mapManager, sabotageManager);
        this.setSabotageCommand = new SetSabotageCommand(mapManager, sabotageManager);

        this.baseCommands.add("test");
        this.baseCommands.add("start");
        this.baseCommands.add("stop");
        this.baseCommands.add("listmaps");
        this.baseCommands.add("mapinfo");
        this.baseCommands.add("playerinfo");
        this.baseCommands.add("createmap");
        this.baseCommands.add("setlocation");
        this.baseCommands.add("setminimum");
        this.baseCommands.add("setmaximum");
        this.baseCommands.add("createtask");
        this.baseCommands.add("removetask");
        this.baseCommands.add("settask");
        this.baseCommands.add("createsabotage");
        this.baseCommands.add("removesabotage");
        this.baseCommands.add("setsabotage");

        this.setLocCommands.add("map");
        this.setLocCommands.add("meeting");
        this.setLocCommands.add("startsign");
        this.setLocCommands.add("mapspawn");

        for(java.util.Map.Entry<Object, String> entry : Tasks.taskNames.entrySet()) {
            this.taskListCommands.add(entry.getValue());
        }

        for(java.util.Map.Entry<Object, String> entry : Sabotages.sabotageNames.entrySet()) {
            this.sabotageListCommands.add(entry.getValue());
        }

        this.setTaskCommands.add("setarea");
        this.setSabotageCommands.add("setoptionallocation");
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
        if(args[0].equalsIgnoreCase("mapinfo")) {
            mapInfoCommand.execute(sender, args);
            return true;
        }
        if(args[0].equalsIgnoreCase("playerinfo")) {
            playerInfoCommand.execute(sender, args);
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
        if(args[0].equalsIgnoreCase("createtask")) {
            try {
                createTaskCommand.execute(sender, args);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        if(args[0].equalsIgnoreCase("removetask")) {
            try {
                removeTaskCommand.execute(sender, args);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        if(args[0].equalsIgnoreCase("settask")) {
            try {
                setTaskCommand.execute(sender, args);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        }
        if(args[0].equalsIgnoreCase("createsabotage")) {
            try {
                createSabotageCommand.execute(sender, args);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        if(args[0].equalsIgnoreCase("removesabotage")) {
            try {
                removeSabotageCommand.execute(sender, args);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        if(args[0].equalsIgnoreCase("setsabotage")) {
            try {
                setSabotageCommand.execute(sender, args);
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
        final ArrayList<String> taskListCompletions = new ArrayList<>(taskListCommands);
        final ArrayList<String> sabotageListCompletions = new ArrayList<>(sabotageListCommands);
        final ArrayList<String> setTaskCompletions = new ArrayList<>(setTaskCommands);
        final ArrayList<String> setSabotageCompletions = new ArrayList<>(setSabotageCommands);
        ArrayList<String> mapCompletions = new ArrayList<>();
        for(Map map : mapManager.getAllMaps()) {
            mapCompletions.add(map.getName());
        }
        final ArrayList<String> numberCompletions = new ArrayList<>();
        for(int i = 1; i <= 20; i++) {
            numberCompletions.add(String.valueOf(i));
        }
        final ArrayList<String> playerNameCompletions = new ArrayList<>();
        for(Player p : Bukkit.getOnlinePlayers()) {
            playerNameCompletions.add(p.getName());
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
                    case "mapinfo":
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
                    case "createtask":
                    case "removetask":
                    case "settask":
                        taskListCompletions.removeIf(completion -> !completion.startsWith(arg1.toLowerCase()));
                        return taskListCompletions;
                    case "createsabotage":
                    case "removesabotage":
                    case "setsabotage":
                        sabotageListCompletions.removeIf(completion -> !completion.startsWith(arg1.toLowerCase()));
                        return sabotageListCompletions;
                    case "playerinfo":
                        playerNameCompletions.removeIf(completion -> !completion.startsWith(arg1.toLowerCase()));
                        return playerNameCompletions;
                    default:
                        return null;
                }
            }

            //ARG INDEX 2
            final String arg2 = argList.remove(0);
            if(argList.isEmpty()) {
                if((arg0.equals("setlocation") && setLocCompletions.contains(arg1)) || ((arg0.equals("setminimum") || arg0.equals("setmaximum")) && numberCompletions.contains(arg1)) || (arg0.equals("createtask") && taskListCompletions.contains(arg1)) || (arg0.equals("removetask") && taskListCompletions.contains(arg1)) || (arg0.equals("settask") && taskListCompletions.contains(arg1))) {
                    mapCompletions.removeIf(completion -> !completion.startsWith(arg2.toLowerCase()));
                    if(mapCompletions.isEmpty()) {
                        return null;
                    }
                    return mapCompletions;
                } else {
                    return null;
                }
            }

            //ARG INDEX 3
            final String arg3 = argList.remove(0);
            if(argList.isEmpty()) {
                if(arg0.equals("settask") && taskListCompletions.contains(arg1) && mapCompletions.contains(arg2)) {
                    setTaskCompletions.removeIf(completion -> !completion.startsWith(arg3.toLowerCase()));
                    if(setTaskCompletions.isEmpty()) {
                        return null;
                    }
                    return setTaskCompletions;
                } else if(arg0.equals("setsabotage") && sabotageListCompletions.contains(arg1) && mapCompletions.contains(arg2)) {
                    setSabotageCompletions.removeIf(completion -> !completion.startsWith(arg3.toLowerCase()));
                    if(setSabotageCompletions.isEmpty()) {
                        return null;
                    }
                    return setSabotageCompletions;
                } else {
                    return null;
                }
            }
        }
        return null;
    }
}
