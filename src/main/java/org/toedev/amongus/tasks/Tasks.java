package org.toedev.amongus.tasks;

import org.toedev.amongus.tasks.tasks.*;

import java.util.HashMap;
import java.util.Map;

public class Tasks {

    public static final Map<Object, String> taskNames;
    static {
        taskNames = new HashMap<>();

        taskNames.put(WiresTask.class, "wires");
        taskNames.put(CalibrateTask.class, "calibrate");
        taskNames.put(ClearAsteroidsTask.class, "clearasteroids");
        taskNames.put(DownloadDataTask.class, "downloaddata");
        taskNames.put(FuelEmptyTask.class, "fuelempty");
        taskNames.put(FuelFillTask.class, "fuelfill");
        taskNames.put(InspectSampleTask.class, "inspectsample");
        taskNames.put(KeypadTask.class, "keypad");
        taskNames.put(MedbayScanTask.class, "medbayscan");
        taskNames.put(ShieldsTask.class, "shields");
        taskNames.put(SimonSaysTask.class, "simonsays");
        taskNames.put(UploadDataTask.class, "uploaddata");
    }
}
