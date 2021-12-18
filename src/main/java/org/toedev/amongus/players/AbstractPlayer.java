package org.toedev.amongus.players;

import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.toedev.amongus.map.Map;

public abstract class AbstractPlayer implements Comparable<AbstractPlayer> {

    private final Player player;
    private final Color color;
    private final Map map;
    private boolean isDead;
    private int votes;

    public AbstractPlayer(Player player, Color color, Map map) {
        this.player = player;
        this.color = color;
        this.map = map;
        this.isDead = false;
        this.votes = 0;
    }

    public Player getPlayer() {
        return player;
    }

    public Color getColor() {
        return color;
    }

    public Map getMap() {
        return map;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead() {
        isDead = true;
    }

    public void setAlive() {
        isDead = false;
    }

    public int getVotes() {
        return votes;
    }

    public void addVote() {
        votes++;
    }

    public void resetVotes() {
        votes = 0;
    }

    public int compareTo(AbstractPlayer otherAbstractPlayer) {
        return Integer.compare(this.votes, otherAbstractPlayer.votes);
    }
}
