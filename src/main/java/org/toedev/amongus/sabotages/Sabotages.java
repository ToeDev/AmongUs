package org.toedev.amongus.sabotages;

import org.toedev.amongus.sabotages.sabotages.DoorSabotage;
import org.toedev.amongus.sabotages.sabotages.LightsSabotage;
import org.toedev.amongus.tasks.tasks.*;

import java.util.HashMap;
import java.util.Map;

public class Sabotages {

    public static final Map<Object, String> sabotageNames;
    static {
        sabotageNames = new HashMap<>();

        sabotageNames.put(LightsSabotage.class, "lights");
        sabotageNames.put(DoorSabotage.class, "door");
    }
}
