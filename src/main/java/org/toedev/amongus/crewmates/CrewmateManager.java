package org.toedev.amongus.crewmates;

import org.bukkit.entity.Player;

import java.util.*;

public class CrewmateManager {

    public Set<Crewmate> crewmates;

    public CrewmateManager() {
        this.crewmates = new HashSet<>();
    }

    public Crewmate getCrewmate(Player player) {
        for(Crewmate crewmate : crewmates) {
            if(crewmate.getPlayer().equals(player)) return crewmate;
        }
        return null;
    }

    public Set<Crewmate> getAllCrewmates() {
        return crewmates;
    }

    public void addCrewmate(Crewmate crewmate) {
        crewmates.add(crewmate);
    }

    public void removeCrewmate(Player player) {
        crewmates.removeIf(crewmate -> crewmate.getPlayer().equals(player));
    }

    public int countAllCrewmates() {
        return crewmates.size();
    }

    public int countDeadCrewmates() {
        int i = 0;
        for(Crewmate crewmate : crewmates) {
            if(crewmate.isDead()) i++;
        }
        return i;
    }

    public int countAliveCrewmates() {
        int i = 0;
        for(Crewmate crewmate : crewmates) {
            if(!crewmate.isDead()) i++;
        }
        return i;
    }

    public void setCrewmateDead(Player player) {
        for(Crewmate crewmate : crewmates) {
            if(crewmate.getPlayer().equals(player)) crewmate.setDead();
        }
    }

    public void setCrewmateAlive(Player player) {
        for(Crewmate crewmate : crewmates) {
            if(crewmate.getPlayer().equals(player)) crewmate.setAlive();
        }
    }

    public int getCrewmateVotes(Player player) {
        for(Crewmate crewmate : crewmates) {
            if(crewmate.getPlayer().equals(player)) return crewmate.getVotes();
        }
        return -1;
    }

    public void addCrewmateVote(Player player) {
        for(Crewmate crewmate : crewmates) {
            if(crewmate.getPlayer().equals(player)) crewmate.addVote();
        }
    }

    public void resetCrewmateVotes(Player player) {
        for(Crewmate crewmate : crewmates) {
            if(crewmate.getPlayer().equals(player)) crewmate.resetVotes();
        }
    }

    public Crewmate getCrewmateWithMostVotes() {
        /*List<Integer> crewmateVotes = new ArrayList<>();
        for(Crewmate crewmate : crewmates) {
            crewmateVotes.add(crewmate.getVotes());
        }
        Collections.sort(crewmateVotes);
        if(crewmateVotes.get(crewmateVotes.size() - 1) > crewmateVotes.get(crewmateVotes.size() - 2)) {
            return
        }*/

        fo
    }
}
