package org.toedev.amongus.tasks.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.toedev.amongus.tasks.AbstractTask;

public class WiresTask extends AbstractTask {

    private final Inventory wiresPanel;

    public WiresTask(Location location) {
        super(location);
        this.wiresPanel = Bukkit.createInventory(null, 54, "");
        for(int i = 0; i <= wiresPanel.getSize(); i++) {
            wiresPanel.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        }
    }

    public Inventory getWiresPanel() {
        return wiresPanel;
    }
}
