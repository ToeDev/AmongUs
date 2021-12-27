package org.toedev.amongus.commands;

import org.bukkit.command.CommandSender;

public class StartCommand {


    public StartCommand() {
    }

    public void start(final CommandSender sender, String[] args) {
        if(args.length < 2) {
            sender.sendMessage("syntax this");
            return;
        }

        sender.sendMessage("good job");
    }
}
