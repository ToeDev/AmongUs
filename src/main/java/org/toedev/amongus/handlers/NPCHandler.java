package org.toedev.amongus.handlers;

import com.denizenscript.denizen.npc.traits.SleepingTrait;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.Prefix;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NPCHandler {

    //TODO ADD CUSTOM REGISTRY

    private final Set<Integer> spawnedNPCs;

    private final ChatColor purple = ChatColor.LIGHT_PURPLE;
    private final ChatColor gold = ChatColor.GOLD;
    private final ChatColor red = ChatColor.RED;

    public NPCHandler(AmongUs amongUs) {
        this.spawnedNPCs = new HashSet<>();
    }

    public void spawnNPC(Player player) {
        //NPCRegistry customRegistry = CitizensAPI.createNamedNPCRegistry("AmongUs", ) //TODO ADD CUSTOM REGISTRY
        //NPC npc = CitizensAPI.getNamedNPCRegistry().createNPC(EntityType.PLAYER, args[1]);
        String playerName = player.getName();
        Location playerLocation = player.getLocation();
        String playerLocationFormatted = Objects.requireNonNull(playerLocation.getWorld()).getName() + ";" + playerLocation.getBlockX() + ";" + playerLocation.getBlockY() + ";" + playerLocation.getBlockZ();
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, playerName);
        SkinTrait skin = npc.getOrAddTrait(SkinTrait.class);
        skin.setSkinName(playerName);
        npc.addTrait(skin);
        npc.data().setPersistent(NPC.NAMEPLATE_VISIBLE_METADATA, false);
        npc.spawn(playerLocation);
        if(npc.isSpawned()) {
            Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "NPC " + npc.getId() + " has been spawned at " + playerLocationFormatted + " with the skin of player: " + playerName);
            SleepingTrait sleep = npc.getOrAddTrait(SleepingTrait.class);
            sleep.toSleep();
            npc.addTrait(sleep);
            spawnedNPCs.add(npc.getId());
            if(npc.getOrAddTrait(SleepingTrait.class).isSleeping()) {
                Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "NPC " + npc.getId() + " is now sleeping.");
            } else {
                Bukkit.getConsoleSender().sendMessage(Prefix.prefix + red + "NPC " + npc.getId() + " failed to be put to sleep!");
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(Prefix.prefix + red + "NPC failed to spawn at " + playerLocationFormatted + " with the skin of player: " + playerName);
        }
    }

    public void despawnAllNPCs() {
        if(spawnedNPCs.size() <= 0) return;
        Iterator<Integer> iter = spawnedNPCs.iterator();
        while(iter.hasNext()) {
            Integer id = iter.next();
            iter.remove();
            NPC npc = CitizensAPI.getNPCRegistry().getById(id);
            if(npc.isSpawned()) {
                Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "NPC " + id + " is currently spawned. Attempting to destroy.");
                npc.destroy();
                if(!npc.isSpawned()) {
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + purple + "NPC " + id + " has been destroyed.");
                    spawnedNPCs.remove(id);
                } else {
                    Bukkit.getConsoleSender().sendMessage(Prefix.prefix + red + "NPC " + id + " was unable to be destroyed!");
                }
            } else {
                Bukkit.getConsoleSender().sendMessage(Prefix.prefix + red + "NPC " + id + " was in the NPCHandler Instance, but it wasn't currently spawned!");
            }
        }
    }

}
