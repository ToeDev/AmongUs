package org.toedev.amongus.tasks;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public final class CompleteTask extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();
    private final AbstractTask task;

    public CompleteTask(Player player, AbstractTask task) {
        super(player);
        this.task = task;
    }

    public AbstractTask getTask() {
        return task;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
