package org.toedev.amongus.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.toedev.amongus.Prefix;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.map.MapManager;

import java.util.Set;

public class ListMapsCommand {

    private final MapManager mapManager;

    private final ChatColor purple = ChatColor.LIGHT_PURPLE;
    private final ChatColor gold = ChatColor.GOLD;
    private final ChatColor red = ChatColor.RED;

    public ListMapsCommand(MapManager mapManager) {
        this.mapManager = mapManager;
    }

    public void execute(final CommandSender sender, String[] args) {
        Set<Map> maps = mapManager.getAllMaps();
        if(maps == null || maps.size() <= 0) {
            sender.sendMessage(Prefix.prefix + red + "No maps found!");
        } else {
            sender.sendMessage(Prefix.prefix + purple + "---------------" + gold + "Maps" + purple + "---------------");
            for(Map map : maps) {
                TextComponent out = new TextComponent(Prefix.prefix + purple + " - ");
                TextComponent mapClick = new TextComponent(gold + map.getName());
                mapClick.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/au mapinfo " + map.getName()));
                mapClick.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("More info")));
                out.addExtra(mapClick);
                sender.spigot().sendMessage(out);
            }
        }
    }
}
