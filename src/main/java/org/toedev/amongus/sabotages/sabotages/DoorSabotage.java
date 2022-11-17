package org.toedev.amongus.sabotages.sabotages;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.sabotages.AbstractSabotage;

public class DoorSabotage extends AbstractSabotage {

    public DoorSabotage(String name, Map map, Location location, Location optionalLocation, Location optionalLocation2, Location optionalLocation3) {
        super(name, map, location, optionalLocation, optionalLocation2, optionalLocation3);
    }

    public void execute(Player player) {
        BukkitPlayer bPlayer = BukkitAdapter.adapt(player);
        CuboidRegion templateSelection = new CuboidRegion(BlockVector3.at(getOptionalLocation2().getX(), getOptionalLocation2().getY(), getOptionalLocation2().getZ()),
                BlockVector3.at(getOptionalLocation3().getX(), getOptionalLocation3().getY(), getOptionalLocation3().getZ()));
        BlockArrayClipboard clipboard = new BlockArrayClipboard(templateSelection);
        LocalSession localSession = WorldEdit.getInstance().getSessionManager().get(bPlayer);
        try (EditSession editSession = localSession.createEditSession(bPlayer)) {
            ForwardExtentCopy forwardExtentCopy = new ForwardExtentCopy(editSession, templateSelection, clipboard, templateSelection.getMinimumPoint());
            forwardExtentCopy.setCopyingEntities(true);
            forwardExtentCopy.setCopyingBiomes(false);
            Operations.complete(forwardExtentCopy);
            localSession.setClipboard(new ClipboardHolder(clipboard));
            BlockVector3 min = BlockVector3.at(Math.min(getLocation().getX(), getOptionalLocation().getX()),
                    Math.min(getLocation().getY(), getOptionalLocation().getY()),
                    Math.min(getLocation().getZ(), getOptionalLocation().getZ()));
            Operation operation = localSession.getClipboard().createPaste(editSession).to(min).ignoreAirBlocks(false).build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            e.printStackTrace();
        }
    }

    public void cancel(Player player) {

    }
}
