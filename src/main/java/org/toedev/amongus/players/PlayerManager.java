package org.toedev.amongus.players;

import org.bukkit.entity.Player;

import java.util.*;

public class PlayerManager {

    public Set<AbstractPlayer> abstractPlayers;

    public PlayerManager() {
        this.abstractPlayers = new HashSet<>();
    }

    public AbstractPlayer getPlayer(Player player) {
        for(AbstractPlayer abstractPlayer : abstractPlayers) {
            if(abstractPlayer.getPlayer().equals(player)) return abstractPlayer;
        }
        return null;
    }

    public Set<AbstractPlayer> getAllPlayers() {
        return abstractPlayers;
    }

    public void addPlayer(AbstractPlayer abstractPlayer) {
        abstractPlayers.add(abstractPlayer);
    }

    public void removePlayer(Player player) {
        abstractPlayers.removeIf(abstractPlayer -> abstractPlayer.getPlayer().equals(player));
    }

    public int countAllPlayers() {
        return abstractPlayers.size();
    }

    public int countDeadPlayers() {
        int i = 0;
        for(AbstractPlayer abstractPlayer : abstractPlayers) {
            if(abstractPlayer.isDead()) i++;
        }
        return i;
    }

    public int countAlivePlayers() {
        int i = 0;
        for(AbstractPlayer abstractPlayer : abstractPlayers) {
            if(!abstractPlayer.isDead()) i++;
        }
        return i;
    }

    public void setPlayerDead(Player player) {
        for(AbstractPlayer abstractPlayer : abstractPlayers) {
            if(abstractPlayer.getPlayer().equals(player)) abstractPlayer.setDead();
        }
    }

    public void setPlayerAlive(Player player) {
        for(AbstractPlayer abstractPlayer : abstractPlayers) {
            if(abstractPlayer.getPlayer().equals(player)) abstractPlayer.setAlive();
        }
    }

    public int getPlayerVotes(Player player) {
        for(AbstractPlayer abstractPlayer : abstractPlayers) {
            if(abstractPlayer.getPlayer().equals(player)) return abstractPlayer.getVotes();
        }
        return -1;
    }

    public void addPlayerVote(Player player) {
        for(AbstractPlayer abstractPlayer : abstractPlayers) {
            if(abstractPlayer.getPlayer().equals(player)) abstractPlayer.addVote();
        }
    }

    public void resetPlayerVotes(Player player) {
        for(AbstractPlayer abstractPlayer : abstractPlayers) {
            if(abstractPlayer.getPlayer().equals(player)) abstractPlayer.resetVotes();
        }
    }

    public AbstractPlayer getPlayerWithMostVotes() {
        List<AbstractPlayer> sortedAbstractPlayers = new ArrayList<>(abstractPlayers);
        Collections.sort(sortedAbstractPlayers);
        Collections.reverse(sortedAbstractPlayers);
        if(sortedAbstractPlayers.get(0).getVotes() > sortedAbstractPlayers.get(1).getVotes()) {
            return sortedAbstractPlayers.get(0);
        } else {
            return null;
        }
    }
}
