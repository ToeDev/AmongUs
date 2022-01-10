package org.toedev.amongus.tasks;

import org.toedev.amongus.tasks.tasks.WiresTask;

import java.util.HashMap;
import java.util.Map;

public class Tasks {

    public static final Map<Object, String> taskNames = new HashMap<>();

    public Tasks() {
        taskNames.put(WiresTask.class, "wires");
    }
}
