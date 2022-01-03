package org.toedev.amongus.hologram;

import org.bukkit.Location;
import org.toedev.amongus.AmongUs;

import java.util.List;
import java.util.logging.Logger;

public class HologramManager {

    private final Logger logger;

    private List<Hologram> holograms;

    public HologramManager(AmongUs amongUs) {
        this.logger = amongUs.getLogger();
    }

    public List<Hologram> getAllHolograms() {
        return holograms;
    }

    public void addHologram(Location location) {

    }
}
