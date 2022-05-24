package org.toedev.amongus.tasks.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.toedev.amongus.tasks.AbstractTask;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class WiresTask extends AbstractTask {

    private List<Inventory> wirePanels;

    public WiresTask(String name, Location location) {
        super(name, location);
        Inventory wiresPanel1 = Bukkit.createInventory(null, 54, "Wires Panel");
        Inventory wiresPanel2 = Bukkit.createInventory(null, 54, "Wires Panel");
        Inventory wiresPanel3 = Bukkit.createInventory(null, 54, "Wires Panel");
        Inventory wiresPanel4 = Bukkit.createInventory(null, 54, "Wires Panel");
        Inventory wiresPanel5 = Bukkit.createInventory(null, 54, "Wires Panel");
        Inventory wiresPanel6 = Bukkit.createInventory(null, 54, "Wires Panel");
        Inventory wiresPanel7 = Bukkit.createInventory(null, 54, "Wires Panel");
        Inventory wiresPanel8 = Bukkit.createInventory(null, 54, "Wires Panel");
        Inventory wiresPanel9 = Bukkit.createInventory(null, 54, "Wires Panel");

        wiresPanel1.setItem(0, new ItemStack(Material.SPONGE));
        wiresPanel1.setItem(9, new ItemStack(Material.SPONGE));
        wiresPanel1.setItem(18, new ItemStack(Material.SPONGE));
        wiresPanel1.setItem(19, new ItemStack(Material.SPONGE));
        wiresPanel1.setItem(20, new ItemStack(Material.SPONGE));
        wiresPanel1.setItem(21, new ItemStack(Material.SPONGE));
        wiresPanel1.setItem(22, new ItemStack(Material.SPONGE));
        wiresPanel1.setItem(31, new ItemStack(Material.SPONGE));
        wiresPanel1.setItem(40, new ItemStack(Material.SPONGE));
        wiresPanel1.setItem(39, new ItemStack(Material.SPONGE));
        wiresPanel1.setItem(48, new ItemStack(Material.SPONGE));

        wiresPanel2.setItem(1, new ItemStack(Material.SPONGE));
        wiresPanel2.setItem(10, new ItemStack(Material.SPONGE));
        wiresPanel2.setItem(11, new ItemStack(Material.SPONGE));
        wiresPanel2.setItem(12, new ItemStack(Material.SPONGE));
        wiresPanel2.setItem(13, new ItemStack(Material.SPONGE));
        wiresPanel2.setItem(14, new ItemStack(Material.SPONGE));
        wiresPanel2.setItem(23, new ItemStack(Material.SPONGE));
        wiresPanel2.setItem(31, new ItemStack(Material.SPONGE));
        wiresPanel2.setItem(32, new ItemStack(Material.SPONGE));
        wiresPanel2.setItem(33, new ItemStack(Material.SPONGE));
        wiresPanel2.setItem(42, new ItemStack(Material.SPONGE));
        wiresPanel2.setItem(51, new ItemStack(Material.SPONGE));

        wiresPanel3.setItem(2, new ItemStack(Material.SPONGE));
        wiresPanel3.setItem(11, new ItemStack(Material.SPONGE));
        wiresPanel3.setItem(20, new ItemStack(Material.SPONGE));
        wiresPanel3.setItem(19, new ItemStack(Material.SPONGE));
        wiresPanel3.setItem(18, new ItemStack(Material.SPONGE));
        wiresPanel3.setItem(27, new ItemStack(Material.SPONGE));
        wiresPanel3.setItem(36, new ItemStack(Material.SPONGE));
        wiresPanel3.setItem(37, new ItemStack(Material.SPONGE));
        wiresPanel3.setItem(38, new ItemStack(Material.SPONGE));
        wiresPanel3.setItem(39, new ItemStack(Material.SPONGE));
        wiresPanel3.setItem(40, new ItemStack(Material.SPONGE));
        wiresPanel3.setItem(49, new ItemStack(Material.SPONGE));

        wiresPanel4.setItem(3, new ItemStack(Material.SPONGE));
        wiresPanel4.setItem(12, new ItemStack(Material.SPONGE));
        wiresPanel4.setItem(21, new ItemStack(Material.SPONGE));
        wiresPanel4.setItem(30, new ItemStack(Material.SPONGE));
        wiresPanel4.setItem(31, new ItemStack(Material.SPONGE));
        wiresPanel4.setItem(32, new ItemStack(Material.SPONGE));
        wiresPanel4.setItem(33, new ItemStack(Material.SPONGE));
        wiresPanel4.setItem(34, new ItemStack(Material.SPONGE));
        wiresPanel4.setItem(35, new ItemStack(Material.SPONGE));
        wiresPanel4.setItem(44, new ItemStack(Material.SPONGE));
        wiresPanel4.setItem(53, new ItemStack(Material.SPONGE));

        wiresPanel5.setItem(4, new ItemStack(Material.SPONGE));
        wiresPanel5.setItem(13, new ItemStack(Material.SPONGE));
        wiresPanel5.setItem(12, new ItemStack(Material.SPONGE));
        wiresPanel5.setItem(21, new ItemStack(Material.SPONGE));
        wiresPanel5.setItem(30, new ItemStack(Material.SPONGE));
        wiresPanel5.setItem(31, new ItemStack(Material.SPONGE));
        wiresPanel5.setItem(40, new ItemStack(Material.SPONGE));
        wiresPanel5.setItem(41, new ItemStack(Material.SPONGE));
        wiresPanel5.setItem(42, new ItemStack(Material.SPONGE));
        wiresPanel5.setItem(43, new ItemStack(Material.SPONGE));
        wiresPanel5.setItem(52, new ItemStack(Material.SPONGE));

        wiresPanel6.setItem(5, new ItemStack(Material.SPONGE));
        wiresPanel6.setItem(14, new ItemStack(Material.SPONGE));
        wiresPanel6.setItem(15, new ItemStack(Material.SPONGE));
        wiresPanel6.setItem(16, new ItemStack(Material.SPONGE));
        wiresPanel6.setItem(25, new ItemStack(Material.SPONGE));
        wiresPanel6.setItem(34, new ItemStack(Material.SPONGE));
        wiresPanel6.setItem(33, new ItemStack(Material.SPONGE));
        wiresPanel6.setItem(32, new ItemStack(Material.SPONGE));
        wiresPanel6.setItem(31, new ItemStack(Material.SPONGE));
        wiresPanel6.setItem(30, new ItemStack(Material.SPONGE));
        wiresPanel6.setItem(39, new ItemStack(Material.SPONGE));
        wiresPanel6.setItem(38, new ItemStack(Material.SPONGE));
        wiresPanel6.setItem(47, new ItemStack(Material.SPONGE));

        wiresPanel7.setItem(6, new ItemStack(Material.SPONGE));
        wiresPanel7.setItem(15, new ItemStack(Material.SPONGE));
        wiresPanel7.setItem(14, new ItemStack(Material.SPONGE));
        wiresPanel7.setItem(23, new ItemStack(Material.SPONGE));
        wiresPanel7.setItem(32, new ItemStack(Material.SPONGE));
        wiresPanel7.setItem(33, new ItemStack(Material.SPONGE));
        wiresPanel7.setItem(34, new ItemStack(Material.SPONGE));
        wiresPanel7.setItem(35, new ItemStack(Material.SPONGE));
        wiresPanel7.setItem(44, new ItemStack(Material.SPONGE));
        wiresPanel7.setItem(53, new ItemStack(Material.SPONGE));

        wiresPanel8.setItem(7, new ItemStack(Material.SPONGE));
        wiresPanel8.setItem(16, new ItemStack(Material.SPONGE));
        wiresPanel8.setItem(15, new ItemStack(Material.SPONGE));
        wiresPanel8.setItem(14, new ItemStack(Material.SPONGE));
        wiresPanel8.setItem(13, new ItemStack(Material.SPONGE));
        wiresPanel8.setItem(22, new ItemStack(Material.SPONGE));
        wiresPanel8.setItem(21, new ItemStack(Material.SPONGE));
        wiresPanel8.setItem(30, new ItemStack(Material.SPONGE));
        wiresPanel8.setItem(29, new ItemStack(Material.SPONGE));
        wiresPanel8.setItem(38, new ItemStack(Material.SPONGE));
        wiresPanel8.setItem(37, new ItemStack(Material.SPONGE));
        wiresPanel8.setItem(46, new ItemStack(Material.SPONGE));

        wiresPanel9.setItem(8, new ItemStack(Material.SPONGE));
        wiresPanel9.setItem(17, new ItemStack(Material.SPONGE));
        wiresPanel9.setItem(26, new ItemStack(Material.SPONGE));
        wiresPanel9.setItem(35, new ItemStack(Material.SPONGE));
        wiresPanel9.setItem(34, new ItemStack(Material.SPONGE));
        wiresPanel9.setItem(33, new ItemStack(Material.SPONGE));
        wiresPanel9.setItem(32, new ItemStack(Material.SPONGE));
        wiresPanel9.setItem(31, new ItemStack(Material.SPONGE));
        wiresPanel9.setItem(30, new ItemStack(Material.SPONGE));
        wiresPanel9.setItem(29, new ItemStack(Material.SPONGE));
        wiresPanel9.setItem(38, new ItemStack(Material.SPONGE));
        wiresPanel9.setItem(37, new ItemStack(Material.SPONGE));
        wiresPanel9.setItem(36, new ItemStack(Material.SPONGE));
        wiresPanel9.setItem(45, new ItemStack(Material.SPONGE));

        wirePanels.add(wiresPanel1);
        wirePanels.add(wiresPanel2);
        wirePanels.add(wiresPanel3);
        wirePanels.add(wiresPanel4);
        wirePanels.add(wiresPanel5);
        wirePanels.add(wiresPanel6);
        wirePanels.add(wiresPanel7);
        wirePanels.add(wiresPanel8);
        wirePanels.add(wiresPanel9);

        for(Inventory inv : wirePanels) {
            for(int i = 0; i < inv.getSize(); i++) {
                if(!Objects.equals(inv.getItem(i), new ItemStack(Material.SPONGE))) {
                    inv.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
                }
            }
        }
    }

    public Inventory getRandomWiresPanel(String color) {
        ItemStack wool = null;
        if(color.equalsIgnoreCase("yellow")) wool = new ItemStack(Material.YELLOW_WOOL);
        if(color.equalsIgnoreCase("blue")) wool = new ItemStack(Material.BLUE_WOOL);
        if(color.equalsIgnoreCase("red")) wool = new ItemStack(Material.RED_WOOL);

        Random random = new Random();
        int r = random.ints(0, 8).findFirst().getAsInt();
        Inventory wiresPanel = wirePanels.get(r);

        for(int i = 0; i < wiresPanel.getSize(); i++) {
            if(Objects.equals(wiresPanel.getItem(i), new ItemStack(Material.SPONGE))) {
                if(i <= 8 || i >= 45) {
                    wiresPanel.setItem(i, wool);
                } else {
                    wiresPanel.setItem(i, new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE));
                }
            }
        }

        return wiresPanel;
    }
}
