package org.toedev.amongus.hologram;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Hologram {

    private final double LINE_SPACE;
    private Location location;
    private List<ArmorStand> armorStands;

    public Hologram(Location location) {
        this.location = location;
        this.LINE_SPACE = .3;
        this.armorStands = new ArrayList<>();
        setDisplay(Collections.singletonList("Test maybe take this line out?"));
    }

    private ArmorStand spawnArmorStand(Location loc, String text) {
        ArmorStand armorStand = (ArmorStand) Objects.requireNonNull(loc.getWorld()).spawnEntity(loc, EntityType.ARMOR_STAND);
        armorStand.setGravity(false);
        armorStand.setInvulnerable(true);
        armorStand.setInvisible(true);
        armorStand.setCanPickupItems(false);
        armorStand.setMarker(true);
        armorStand.setCustomName(text);
        armorStand.setCustomNameVisible(true);
        armorStand.addScoreboardTag("AmongUs-Hologram");
        return armorStand;
    }

    public void setDisplay(List<String> lines) {
        clear();
        Location loc = location.clone();
        for(String line : lines) {
            armorStands.add(spawnArmorStand(loc, line));
            loc.setY(loc.getY() - LINE_SPACE);
        }
    }

    public void clear() {
        List<Entity> nearbyEntities = (List<Entity>) Objects.requireNonNull(location.getWorld()).getNearbyEntities(location, 1, 5, 1);
        for(Entity entity : nearbyEntities) {
            if(entity.getScoreboardTags().contains("AmongUs-Hologram")) {
                entity.remove();
            }
        }
        armorStands.clear();
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location loc) {
        Location newLoc = loc.clone();
        for(ArmorStand armorStand : armorStands) {
            newLoc.setY(loc.getY() - LINE_SPACE);
            armorStand.teleport(newLoc);
        }
        location = loc;
    }
}
