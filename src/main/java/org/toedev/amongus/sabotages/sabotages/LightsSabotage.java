package org.toedev.amongus.sabotages.sabotages;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.sabotages.AbstractSabotage;

import java.util.List;

public class LightsSabotage extends AbstractSabotage { //todo event handler cancel on head click

    public LightsSabotage(String name, Map map, Location location, Location optionalLocation, Location optionalLocation2, Location optionalLocation3) {
        super(name, map, location, optionalLocation, optionalLocation2, optionalLocation3);
    }

    public void execute(List<Player> crewmates) {
        for(Player player : crewmates) {
            player.addPotionEffect(PotionEffectType.BLINDNESS.createEffect(7200, 0));
        }
    }

    public void cancel(List<Player> crewmates) {
        for(Player player : crewmates) {
            player.removePotionEffect(PotionEffectType.BLINDNESS);
        }
    }
}
