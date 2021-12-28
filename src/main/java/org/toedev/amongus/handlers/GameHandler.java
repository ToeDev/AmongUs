package org.toedev.amongus.handlers;

import java.util.HashSet;
import java.util.Set;

public class GameHandler {

    private Set<Boolean> allMaps;

    public GameHandler() {
        allMaps = new HashSet<>();
    }

    public void initializeMap(Boolean mapName) {
        allMaps.add(mapName);
    }

    //public void startMap()
}
