package org.toedev.amongus.players;

import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.tasks.AbstractTask;

import java.util.HashSet;
import java.util.Set;

public class Crewmate extends AbstractPlayer {

    private Set<AbstractTask> tasks;

    public Crewmate(Player player, Color color, Map map) {
        super(player, color, map);
        this.tasks = new HashSet<>();
    }

    public Set<AbstractTask> getTasks() {
        return tasks;
    }

    public void addTask(AbstractTask abstractTask) {
        tasks.add(abstractTask);
    }

    public void removeTask(AbstractTask abstractTask) {
        tasks.remove(abstractTask);
    }

}
