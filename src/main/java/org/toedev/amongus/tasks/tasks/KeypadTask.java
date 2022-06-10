package org.toedev.amongus.tasks.tasks;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.tasks.AbstractTask;

import java.lang.reflect.Field;
import java.util.*;

public class KeypadTask extends AbstractTask {

    public Inventory keypadInv;
    public List<Integer> keypadOrder;
    public List<ItemStack> numberHeads;
    public List<ItemStack> numberHeadsDone;

    public KeypadTask(String name, Map map, Location location) {
        super(name, map, location);

        ItemStack number1 = createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDU1ZmMyYzFiYWU4ZTA4ZDNlNDI2YzE3YzQ1NWQyZmY5MzQyMjg2ZGZmYTNjN2MyM2Y0YmQzNjVlMGMzZmUifX19", " ");
        ItemStack number2 = createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGM2MWIwNGUxMmE4Nzk3NjdiM2I3MmQ2OTYyN2YyOWE4M2JkZWI2MjIwZjVkYzdiZWEyZWIyNTI5ZDViMDk3In19fQ==", " ");
        ItemStack number3 = createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjgyM2Y3NzU1OGNhNjA2MGI2ZGM2YTRkNGIxZDg2YzFhNWJlZTcwODE2NzdiYmMzMzZjY2I5MmZiZDNlZSJ9fX0=", " ");
        ItemStack number4 = createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTFiOWM0ZDZmNzIwOGIxNDI0Zjg1OTViZmMxYjg1Y2NhYWVlMmM1YjliNDFlMGY1NjRkNGUwYWNhOTU5In19fQ==", " ");
        ItemStack number5 = createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmMxNDE1OTczYjQyZjgyODZmOTQ4ZTIxNDA5OTJiOWEyOWQ4MDk2NTU5M2IxNDU1M2Q2NDRmNGZlYWZiNyJ9fX0=", " ");
        ItemStack number6 = createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTZmNWZiZmJjODk0NGE1MDc3NzExMzc5OGU5ZmUzYWVhYzJlMzk2NDg5NDdiNzBjYzEwM2RlYjZjOWU4NjYxIn19fQ==", " ");
        ItemStack number7 = createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGYzYWNkNmRmZDViY2JmYTlmYzg0ZGYzZDcwNGJiZDlkMTQ4N2VhODdjZjU2NDQyN2JiOGVjOTVjNjUyMjcifX19", " ");
        ItemStack number8 = createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGViMTE1ZmE0N2E5ZDJkNmQ0MWU1YWE3YmU0ZTEzNjdmZjkyYzRlOTQ2NTg5N2Q1ZDYyYTc2NWVmOTI0ZjQifX19", " ");
        ItemStack number9 = createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWFiOGEyN2FlZTQ3NzlmMDQzYTY2OGM5ZTkyN2EzNTNlNjNhYzc5MWVkM2Y1NmEyNGY2MWQ5NDM5Y2NmZDUxNSJ9fX0=", " ");
        numberHeads = new ArrayList<>();
        numberHeads.add(number1);
        numberHeads.add(number2);
        numberHeads.add(number3);
        numberHeads.add(number4);
        numberHeads.add(number5);
        numberHeads.add(number6);
        numberHeads.add(number7);
        numberHeads.add(number8);
        numberHeads.add(number9);
        ItemStack number1Done = createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWYzMDM0ZDI0YTg1ZGEzMWQ2NzkzMmMzM2U1ZjE4MjFlMjE5ZDVkY2Q5YzJiYTRmMjU1OWRmNDhkZWVhIn19fQ==", " ");
        ItemStack number2Done = createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2EyOGExMzUzODMzZjQwYTQyMWNhYmFhMzk2NWI5NzBhZDlmNjJjMWQ5NjJhY2E5ODQxNGQyZGVjNWMzMzgifX19", " ");
        ItemStack number3Done = createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTUxYzZkYTQ2Zjc1ODViZjJmZDVjYTQ0YzRhYmVjNTY4YzIzZWFmNjdjM2Y2ODFjZDJiYzFiM2ViMjc1YSJ9fX0=", " ");
        ItemStack number4Done = createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMGU5MzRjMGUwZTU2ZmI2NTkxY2E5OGQ3YzhhODNmZTM4Njg5OGNmNjRiZTMxNDMzMGJkODI3OGQ4MmEyIn19fQ==", " ");
        ItemStack number5Done = createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzQzNDNlZWIyM2QxOGVlMzdiYTZlZWRkOWRiMDYxYTQ3MDU3MzBlMzRkYzg1NTk2NDZmNzI5Zjk1ZTUwYTM3ZiJ9fX0=", " ");
        ItemStack number6Done = createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzJlNzFiOGY3ZTFiZjQ5ZDVkODkyZTQyMzYxYzlkNDA5M2NlZTY0MTM5NTJlNmE0ZGI2NDY0ZmY3OGFlZTgifX19", " ");
        ItemStack number7Done = createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDhhM2M1YjEyMmRmZjkzNjM2YmRiY2Q2NjY5YjU5Y2RhM2ZjZmFlOTM1Y2U3NjE1YTI3ZTVkYWIxN2M3NTU2MSJ9fX0=", " ");
        ItemStack number8Done = createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODU5ODM4MjM3Zjg4OWY1YmNhYWYyNmM3NTZmNjRlODE2YTEzNTBjM2M1NGFhN2EwZjcyODM5MjI2NTkwOGUzNSJ9fX0=", " ");
        ItemStack number9Done = createPlayerHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmFjM2E5NDk1NzUzMmYyODA3NjExYWMzMjViY2RlZTRlNGQ3Y2I0YTIyMTIyZTQyMDgwNmNlNmIwZDdjM2MxIn19fQ==", " ");
        numberHeadsDone = new ArrayList<>();
        numberHeadsDone.add(number1Done);
        numberHeadsDone.add(number2Done);
        numberHeadsDone.add(number3Done);
        numberHeadsDone.add(number4Done);
        numberHeadsDone.add(number5Done);
        numberHeadsDone.add(number6Done);
        numberHeadsDone.add(number7Done);
        numberHeadsDone.add(number8Done);
        numberHeadsDone.add(number9Done);
        keypadInv = Bukkit.createInventory(null, 9, "Keypad");
    }

    public void cancel() {
        keypadInv = Bukkit.createInventory(null, 9, "Keypad");
        keypadOrder = new ArrayList<>();
        setInUse(false);
    }

    public List<Integer> getKeypadOrder() {
        return keypadOrder;
    }

    public void execute(Player player) {
        setInUse(true);
        keypadOrder = new ArrayList<>();
        for(int i = 0; i <= keypadInv.getSize() - 1; i++) {
            int r = getRandomSlot();
            while(keypadOrder.contains(r)) {
                r = getRandomSlot();
            }
            keypadInv.setItem(i, numberHeads.get(r));
            keypadOrder.add(r);
        }
        player.openInventory(keypadInv);
    }

    public Integer getRandomSlot() {
        return new Random().ints(0, keypadInv.getSize()).findFirst().getAsInt();
    }

    public Integer getCompleted() {
        int i = 0;
        for(ItemStack item : keypadInv) {
            if(item != null) {
                for(ItemStack s : numberHeadsDone) {
                    if(Objects.equals(s.getItemMeta().toString(), item.getItemMeta().toString())) {
                        i++;
                        break;
                    }
                }
            }
        }
        return i;
    }

    public Inventory getKeypadInv() {
        return keypadInv;
    }

    public List<ItemStack> getNumberHeadsDone() {
        return numberHeadsDone;
    }

    private ItemStack createPlayerHead(String base64, String name) {
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta headMeta = (SkullMeta) playerHead.getItemMeta();
        assert headMeta != null;
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", base64));
        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        headMeta.setDisplayName(name);
        playerHead.setItemMeta(headMeta);
        return playerHead;
    }
}
