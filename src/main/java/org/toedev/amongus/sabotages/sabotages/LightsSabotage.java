package org.toedev.amongus.sabotages.sabotages;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.sabotages.AbstractSabotage;

import java.util.List;

public class LightsSabotage extends AbstractSabotage {

    public LightsSabotage(String name, Map map, Location location, Location optionalLocation) {
        super(name, map, location, optionalLocation);
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
