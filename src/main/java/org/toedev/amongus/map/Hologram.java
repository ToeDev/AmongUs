package org.toedev.amongus.map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Hologram {

    private final double LINE_SPACE;
    private Location location;
    private List<ArmorStand> armorStands;

    public Hologram(Location location, Map map) {
        this.location = location;
        this.LINE_SPACE = .28;
        this.armorStands = new ArrayList<>();
        String[] mapName = map.getName().split(" ");
        StringBuilder mapNameFinal = new StringBuilder("");
        for(String split : mapName) {
            mapNameFinal.append(split.substring(0, 1).toUpperCase()).append(split.substring(1).toLowerCase());
        }
        setDisplay(Collections.singletonList(ChatColor.BLUE + mapNameFinal.toString() + " Queue:"));
    }

    private ArmorStand spawnArmorStand(Location loc, String text) {
        return Objects.requireNonNull(loc.getWorld()).spawn(loc, ArmorStand.class, armorStand -> {
            armorStand.setInvisible(true);
            armorStand.setGravity(false);
            armorStand.setInvulnerable(true);
            armorStand.setCanPickupItems(false);
            armorStand.setMarker(true);
            armorStand.setCustomName(text);
            armorStand.setCustomNameVisible(true);
            armorStand.addScoreboardTag("AmongUs-Hologram");
        });
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
