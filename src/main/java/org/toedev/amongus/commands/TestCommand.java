package org.toedev.amongus.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.toedev.amongus.handlers.NPCHandler;

import java.util.Objects;

public class TestCommand {

    private final NPCHandler npcHandler;

    public TestCommand(NPCHandler npcHandler) {
        this.npcHandler = npcHandler;
    }

    public void execute(final CommandSender sender, String[] args) {
        if(args[1].equals("spawn")) {
            npcHandler.spawnNPC(Objects.requireNonNull(Bukkit.getPlayer(sender.getName())));
        } else if(args[1].equals("despawn")) {
            npcHandler.despawnAllNPCs();
        }
    }
}
