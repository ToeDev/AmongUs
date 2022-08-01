package org.toedev.amongus.commands;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.toedev.amongus.Prefix;
import org.toedev.amongus.handlers.GameHandler;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.map.MapManager;
import org.toedev.amongus.tasks.AbstractTask;
import org.toedev.amongus.tasks.TaskManager;

public class PlayerInfoCommand {

    private final MapManager mapManager;
    private final TaskManager taskManager;
    private final GameHandler gameHandler;

    private final ChatColor purple = ChatColor.LIGHT_PURPLE;
    private final ChatColor gold = ChatColor.GOLD;
    private final ChatColor red = ChatColor.RED;

    public PlayerInfoCommand(MapManager mapManager, TaskManager taskManager, GameHandler gameHandler) {
        this.mapManager = mapManager;
        this.taskManager = taskManager;
        this.gameHandler = gameHandler;
    }

    public void execute(final CommandSender sender, String[] args) {
        if(args.length < 2) return;
        Player player = Bukkit.getPlayer(args[1]);
        if(player == null) {
            sender.sendMessage(Prefix.prefix + red + "Player not found! Are they online?");
            return;
        }
        boolean isInMap = gameHandler.isPlayerInAnyMap(player);
        boolean isPlayerCrewmate = isInMap && gameHandler.isPlayerCrewmate(gameHandler.getPlayerInMap(gameHandler.getMapPlayerIsIn(player), player));
        sender.sendMessage(Prefix.prefix + purple + "---------------" + gold + player.getName() + purple + "---------------");
        sender.sendMessage(Prefix.prefix + purple + "In Map: " + gold + (isInMap ? gameHandler.getMapPlayerIsIn(player).getName() : "none"));
        sender.sendMessage(Prefix.prefix + purple + "In Queue: " + gold + (gameHandler.isPlayerInAnyMapQueue(player) ? gameHandler.getMapQueuePlayerIsIn(player).getName() : "none"));
        if(isInMap) {
            sender.sendMessage(Prefix.prefix + purple + "Player is: " + gold + (isPlayerCrewmate ? "Crewmate" : "Imposter"));
            if(isPlayerCrewmate) {
                sender.sendMessage(Prefix.prefix + purple + "---Tasks---");
                if(gameHandler.getPlayerTasks(player) == null) {
                    sender.sendMessage(Prefix.prefix + purple + " - " + gold + "none");
                } else {
                    for(AbstractTask task : gameHandler.getPlayerTasks(player)) {
                        sender.sendMessage(Prefix.prefix + purple + " - " + gold + task.getName());
                    }
                }
            } else {
                //TODO add sabotages?
            }
        }
    }
}
