package org.toedev.amongus.sql;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.map.Map;
import org.toedev.amongus.sabotages.AbstractSabotage;
import org.toedev.amongus.tasks.AbstractTask;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Utility extends Database {

    public Utility(AmongUs amongUs) {
        super(amongUs);
    }

    public String SQLiteCreateMapsTable = "CREATE TABLE IF NOT EXISTS maps (" +
            "`mapID` INTEGER PRIMARY KEY AUTOINCREMENT," +
            "`mapName` varchar(32) NOT NULL," +
            "`minPlayers` TINYINT NOT NULL," +
            "`maxPlayers` TINYINT NOT NULL," +
            "`mapStartSignWorld` varchar(64) NOT NULL," +
            "`mapStartSignX` BIGINT NOT NULL," +
            "`mapStartSignY` BIGINT NOT NULL," +
            "`mapStartSignZ` BIGINT NOT NULL," +
            "`mapSpawnWorld` varchar(64) NOT NULL," +
            "`mapSpawnX` BIGINT NOT NULL," +
            "`mapSpawnY` BIGINT NOT NULL," +
            "`mapSpawnZ` BIGINT NOT NULL," +
            "`mapMinCornerWorld` varchar(64) NOT NULL," +
            "`mapMinCornerX` BIGINT NOT NULL," +
            "`mapMinCornerY` BIGINT NOT NULL," +
            "`mapMinCornerZ` BIGINT NOT NULL," +
            "`mapMaxCornerWorld` varchar(64) NOT NULL," +
            "`mapMaxCornerX` BIGINT NOT NULL," +
            "`mapMaxCornerY` BIGINT NOT NULL," +
            "`mapMaxCornerZ` BIGINT NOT NULL," +
            "`meetingMinCornerWorld` varchar(64) NOT NULL," +
            "`meetingMinCornerX` BIGINT NOT NULL," +
            "`meetingMinCornerY` BIGINT NOT NULL," +
            "`meetingMinCornerZ` BIGINT NOT NULL," +
            "`meetingMaxCornerWorld` varchar(64) NOT NULL," +
            "`meetingMaxCornerX` BIGINT NOT NULL," +
            "`meetingMaxCornerY` BIGINT NOT NULL," +
            "`meetingMaxCornerZ` BIGINT NOT NULL" +
            ");";

    public String SQLiteCreateTasksTable = "CREATE TABLE IF NOT EXISTS tasks (" +
            "`taskID` INTEGER PRIMARY KEY AUTOINCREMENT," +
            "`mapName` varchar(32) NOT NULL," +
            "`taskName` varchar(32) NOT NULL," +
            "`taskWorld` varchar(64) NOT NULL," +
            "`taskX` BIGINT NOT NULL," +
            "`taskY` BIGINT NOT NULL," +
            "`taskZ` BIGINT NOT NULL," +
            "`taskAreaMinWorld` varchar(64) NOT NULL," +
            "`taskAreaMinX` BIGINT NOT NULL," +
            "`taskAreaMinY` BIGINT NOT NULL," +
            "`taskAreaMinZ` BIGINT NOT NULL," +
            "`taskAreaMaxWorld` varchar(64) NOT NULL," +
            "`taskAreaMaxX` BIGINT NOT NULL," +
            "`taskAreaMaxY` BIGINT NOT NULL," +
            "`taskAreaMaxZ` BIGINT NOT NULL," +
            "`taskTeleportWorld` varchar(64) NOT NULL," +
            "`taskTeleportX` BIGINT NOT NULL," +
            "`taskTeleportY` BIGINT NOT NULL," +
            "`taskTeleportZ` BIGINT NOT NULL" +
            ");";

    public String SQLiteCreateSabotagesTable = "CREATE TABLE IF NOT EXISTS sabotages (" +
            "`sabotageID` INTEGER PRIMARY KEY AUTOINCREMENT," +
            "`mapName` varchar(32) NOT NULL," +
            "`sabotageName` varchar(32) NOT NULL," +
            "`sabotageWorld` varchar(64) NOT NULL," +
            "`sabotageX` BIGINT NOT NULL," +
            "`sabotageY` BIGINT NOT NULL," +
            "`sabotageZ` BIGINT NOT NULL," +
            "`sabotageOptionalWorld` varchar(64) NOT NULL," +
            "`sabotageOptionalX` BIGINT NOT NULL," +
            "`sabotageOptionalY` BIGINT NOT NULL," +
            "`sabotageOptionalZ` BIGINT NOT NULL," +
            "`sabotageOptionalWorld2` varchar(64) NOT NULL," +
            "`sabotageOptionalX2` BIGINT NOT NULL," +
            "`sabotageOptionalY2` BIGINT NOT NULL," +
            "`sabotageOptionalZ2` BIGINT NOT NULL," +
            "`sabotageOptionalWorld3` varchar(64) NOT NULL," +
            "`sabotageOptionalX3` BIGINT NOT NULL," +
            "`sabotageOptionalY3` BIGINT NOT NULL," +
            "`sabotageOptionalZ3` BIGINT NOT NULL" +
            ");";

    public void load() {
        connect();
        update(SQLiteCreateMapsTable);
        update(SQLiteCreateTasksTable);
        update(SQLiteCreateSabotagesTable);
    }

    public ResultSet getAllMaps() {
        return getResult("SELECT * FROM `maps`");
    }

    public ResultSet getAllTasks() {
        return getResult("SELECT * FROM `tasks`");
    }

    public ResultSet getAllSabotages() {
        return getResult("SELECT * FROM `sabotages`");
    }

    public boolean isMapInDB(Map map) throws SQLException {
        int rows = getResult("SELECT COUNT(*) FROM `maps` WHERE mapName = \"" + map.getName() + "\";").getInt(1);
        return rows > 0;
    }

    public void createBackup(JavaPlugin plugin) throws IOException {
        File dbFile = new File(plugin.getDataFolder(), "core.db");
        if(dbFile.exists()) {
            Path source = dbFile.toPath();
            Path target = plugin.getDataFolder().toPath();
            Files.copy(source, target.resolve("core-backup.db"), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public void addMap(Map map) {
        Integer minPlayers = map.getMinPlayers();
        Integer maxPlayers = map.getMaxPlayers();
        Location mapStartSign = map.getMapStartSign();
        Location mapSpawn = map.getMapSpawn();
        Location mapMinCorner = map.getMapMinCorner();
        Location mapMaxCorner = map.getMapMaxCorner();
        Location meetingMinCorner = map.getMeetingMinCorner();
        Location meetingMaxCorner = map.getMeetingMaxCorner();
        update("INSERT INTO `maps` (mapName, minPlayers, maxPlayers, " +
                "mapStartSignWorld, mapStartSignX, mapStartSignY, mapStartSignZ, " +
                "mapSpawnWorld, mapSpawnX, mapSpawnY, mapSpawnZ, " +
                "mapMinCornerWorld, mapMinCornerX, mapMinCornerY, mapMinCornerZ, " +
                "mapMaxCornerWorld, mapMaxCornerX, mapMaxCornerY, mapMaxCornerZ, " +
                "meetingMinCornerWorld, meetingMinCornerX, meetingMinCornerY, meetingMinCornerZ, " +
                "meetingMaxCornerWorld, meetingMaxCornerX, meetingMaxCornerY, meetingMaxCornerZ) " +
                "VALUES(\"" + map.getName() + "\", \"" + minPlayers + "\", \"" + maxPlayers + "\", " +
                "\"" + Objects.requireNonNull(mapStartSign.getWorld()).getName() + "\", \"" + mapStartSign.getX() + "\", \"" + mapStartSign.getY() + "\", \"" + mapStartSign.getZ() + "\", " +
                "\"" + Objects.requireNonNull(mapSpawn.getWorld()).getName() + "\", \"" + mapSpawn.getX() + "\", \"" + mapSpawn.getY() + "\", \"" + mapSpawn.getZ() + "\", " +
                "\"" + Objects.requireNonNull(mapMinCorner.getWorld()).getName() + "\", \"" + mapMinCorner.getX() + "\", \"" + mapMinCorner.getY() + "\", \"" + mapMinCorner.getZ() + "\", " +
                "\"" + Objects.requireNonNull(mapMaxCorner.getWorld()).getName() + "\", \"" + mapMaxCorner.getX() + "\", \"" + mapMaxCorner.getY() + "\", \"" + mapMaxCorner.getZ() + "\", " +
                "\"" + Objects.requireNonNull(meetingMinCorner.getWorld()).getName() + "\", \"" + meetingMinCorner.getX() + "\", \"" + meetingMinCorner.getY() + "\", \"" + meetingMinCorner.getZ() + "\", " +
                "\"" + Objects.requireNonNull(meetingMaxCorner.getWorld()).getName() + "\", \"" + meetingMaxCorner.getX() + "\", \"" + meetingMaxCorner.getY() + "\", \"" + meetingMaxCorner.getZ() + "\");"
        );
    }

    public void updateMap(Map map) {
        Integer minPlayers = map.getMinPlayers();
        Integer maxPlayers = map.getMaxPlayers();
        Location mapStartSign = map.getMapStartSign();
        Location mapSpawn = map.getMapSpawn();
        Location mapMinCorner = map.getMapMinCorner();
        Location mapMaxCorner = map.getMapMaxCorner();
        Location meetingMinCorner = map.getMeetingMinCorner();
        Location meetingMaxCorner = map.getMeetingMaxCorner();
        update("UPDATE `maps`" +
                " SET" +
                " minPlayers = \"" + minPlayers + "\"," +
                " maxPlayers = \"" + maxPlayers + "\"," +
                " mapStartSignWorld = \"" + Objects.requireNonNull(mapStartSign.getWorld()).getName() + "\"," +
                " mapStartSignX = \"" + mapStartSign.getX() + "\"," +
                " mapStartSignY = \"" + mapStartSign.getY() + "\"," +
                " mapStartSignZ = \"" + mapStartSign.getZ() + "\"," +
                " mapSpawnWorld = \"" + Objects.requireNonNull(mapSpawn.getWorld()).getName() + "\"," +
                " mapSpawnX = \"" + mapSpawn.getX() + "\"," +
                " mapSpawnY = \"" + mapSpawn.getY() + "\"," +
                " mapSpawnZ = \"" + mapSpawn.getZ() + "\"," +
                " mapMinCornerWorld = \"" + Objects.requireNonNull(mapMinCorner.getWorld()).getName() + "\"," +
                " mapMinCornerX = \"" + mapMinCorner.getX() + "\"," +
                " mapMinCornerY = \"" + mapMinCorner.getY() + "\"," +
                " mapMinCornerZ = \"" + mapMinCorner.getZ() + "\"," +
                " mapMaxCornerWorld = \"" + Objects.requireNonNull(mapMaxCorner.getWorld()).getName() + "\"," +
                " mapMaxCornerX = \"" + mapMaxCorner.getX() + "\"," +
                " mapMaxCornerY = \"" + mapMaxCorner.getY() + "\"," +
                " mapMaxCornerZ = \"" + mapMaxCorner.getZ() + "\"," +
                " meetingMinCornerWorld = \"" + Objects.requireNonNull(meetingMinCorner.getWorld()).getName() + "\"," +
                " meetingMinCornerX = \"" + meetingMinCorner.getX() + "\"," +
                " meetingMinCornerY = \"" + meetingMinCorner.getY() + "\"," +
                " meetingMinCornerZ = \"" + meetingMinCorner.getZ() + "\"," +
                " meetingMaxCornerWorld = \"" + Objects.requireNonNull(meetingMaxCorner.getWorld()).getName() + "\"," +
                " meetingMaxCornerX = \"" + meetingMaxCorner.getX() + "\"," +
                " meetingMaxCornerY = \"" + meetingMaxCorner.getY() + "\"," +
                " meetingMaxCornerZ = \"" + meetingMaxCorner.getZ() + "\"" +
                " WHERE mapName = \"" + map.getName() + "\";"
        );
    }

    public void addTask(Map map, AbstractTask task) {
        World taskWorld = task.getLocation().getWorld();
        double taskX = task.getLocation().getX();
        double taskY = task.getLocation().getY();
        double taskZ = task.getLocation().getZ();
        World taskAreaMinWorld = task.getTaskAreaMinLocation() == null ? null : task.getTaskAreaMinLocation().getWorld();
        double taskAreaMinX = task.getTaskAreaMinLocation() == null ? 0 : task.getTaskAreaMinLocation().getX();
        double taskAreaMinY = task.getTaskAreaMinLocation() == null ? 0 : task.getTaskAreaMinLocation().getY();
        double taskAreaMinZ = task.getTaskAreaMinLocation() == null ? 0 : task.getTaskAreaMinLocation().getZ();
        World taskAreaMaxWorld = task.getTaskAreaMaxLocation() == null ? null : task.getTaskAreaMaxLocation().getWorld();
        double taskAreaMaxX = task.getTaskAreaMaxLocation() == null ? 0 : task.getTaskAreaMaxLocation().getX();
        double taskAreaMaxY = task.getTaskAreaMaxLocation() == null ? 0 : task.getTaskAreaMaxLocation().getY();
        double taskAreaMaxZ = task.getTaskAreaMaxLocation() == null ? 0 : task.getTaskAreaMaxLocation().getZ();
        World taskTeleportWorld = task.getTeleportLocation() == null ? null : task.getTeleportLocation().getWorld();
        double taskTeleportX = task.getTeleportLocation() == null ? 0 : task.getTeleportLocation().getX();
        double taskTeleportY = task.getTeleportLocation() == null ? 0 : task.getTeleportLocation().getY();
        double taskTeleportZ = task.getTeleportLocation() == null ? 0 : task.getTeleportLocation().getZ();
        update("INSERT INTO `tasks` (mapName, taskName, " +
                "taskWorld, taskX, taskY, taskZ, " +
                "taskAreaMinWorld, taskAreaMinX, taskAreaMinY, taskAreaMinZ, " +
                "taskAreaMaxWorld, taskAreaMaxX, taskAreaMaxY, taskAreaMaxZ, " +
                "taskTeleportWorld, taskTeleportX, taskTeleportY, taskTeleportZ) " +
                "VALUES(\"" + map.getName() + "\", \"" + task.getName() + "\", " +
                "\"" + Objects.requireNonNull(taskWorld).getName() + "\", \"" + taskX + "\", \"" + taskY + "\", \"" + taskZ + "\", " +
                "\"" + (taskAreaMinWorld == null ? null : taskAreaMinWorld.getName()) + "\", \"" + taskAreaMinX + "\", \"" + taskAreaMinY + "\", \"" + taskAreaMinZ + "\", " +
                "\"" + (taskAreaMaxWorld == null ? null : taskAreaMaxWorld.getName()) + "\", \"" + taskAreaMaxX + "\", \"" + taskAreaMaxY + "\", \"" + taskAreaMaxZ + "\", " +
                "\"" + (taskTeleportWorld == null ? null : taskTeleportWorld.getName()) + "\", \"" + taskTeleportX + "\", \"" + taskTeleportY + "\", \"" + taskTeleportZ + "\");"
        );
    }

    public void removeTask(Map map, String name, Location location) {
        World taskWorld = location.getWorld();
        double taskX = location.getX();
        double taskY = location.getY();
        double taskZ = location.getZ();
        update("DELETE FROM `tasks`" +
                " WHERE mapName = \"" + map.getName() + "\"" +
                " AND taskName = \"" + name + "\"" +
                " AND taskWorld = \"" + Objects.requireNonNull(taskWorld).getName() + "\"" +
                " AND taskX = \"" + taskX + "\"" +
                " AND taskY = \"" + taskY + "\"" +
                " AND taskZ = \"" + taskZ + "\";"
        );
    }

    public void updateTask(Map map, AbstractTask task) {
        World taskWorld = task.getLocation().getWorld();
        double taskX = task.getLocation().getX();
        double taskY = task.getLocation().getY();
        double taskZ = task.getLocation().getZ();
        World taskAreaMinWorld = task.getTaskAreaMinLocation() == null ? null : task.getTaskAreaMinLocation().getWorld();
        double taskAreaMinX = task.getTaskAreaMinLocation() == null ? 0 : task.getTaskAreaMinLocation().getX();
        double taskAreaMinY = task.getTaskAreaMinLocation() == null ? 0 : task.getTaskAreaMinLocation().getY();
        double taskAreaMinZ = task.getTaskAreaMinLocation() == null ? 0 : task.getTaskAreaMinLocation().getZ();
        World taskAreaMaxWorld = task.getTaskAreaMaxLocation() == null ? null : task.getTaskAreaMaxLocation().getWorld();
        double taskAreaMaxX = task.getTaskAreaMaxLocation() == null ? 0 : task.getTaskAreaMaxLocation().getX();
        double taskAreaMaxY = task.getTaskAreaMaxLocation() == null ? 0 : task.getTaskAreaMaxLocation().getY();
        double taskAreaMaxZ = task.getTaskAreaMaxLocation() == null ? 0 : task.getTaskAreaMaxLocation().getZ();
        World taskTeleportWorld = task.getTeleportLocation() == null ? null : task.getTeleportLocation().getWorld();
        double taskTeleportX = task.getTeleportLocation() == null ? 0 : task.getTeleportLocation().getX();
        double taskTeleportY = task.getTeleportLocation() == null ? 0 : task.getTeleportLocation().getY();
        double taskTeleportZ = task.getTeleportLocation() == null ? 0 : task.getTeleportLocation().getZ();
        update("UPDATE `tasks`" +
                " SET" +
                " taskWorld = \"" + Objects.requireNonNull(taskWorld).getName() + "\"," +
                " taskX = \"" + taskX + "\"," +
                " taskY = \"" + taskY + "\"," +
                " taskZ = \"" + taskZ + "\"," +
                " taskAreaMinWorld = \"" + (taskAreaMinWorld == null ? null : taskAreaMinWorld.getName()) + "\"," +
                " taskAreaMinX = \"" + taskAreaMinX + "\"," +
                " taskAreaMinY = \"" + taskAreaMinY + "\"," +
                " taskAreaMinZ = \"" + taskAreaMinZ + "\"," +
                " taskAreaMaxWorld = \"" + (taskAreaMaxWorld == null ? null : taskAreaMaxWorld.getName()) + "\"," +
                " taskAreaMaxX = \"" + taskAreaMaxX + "\"," +
                " taskAreaMaxY = \"" + taskAreaMaxY + "\"," +
                " taskAreaMaxZ = \"" + taskAreaMaxZ + "\"," +
                " taskTeleportWorld = \"" + (taskTeleportWorld == null ? null : taskTeleportWorld.getName()) + "\"," +
                " taskTeleportX = \"" + taskTeleportX + "\"," +
                " taskTeleportY = \"" + taskTeleportY + "\"," +
                " taskTeleportZ = \"" + taskTeleportZ + "\"" +
                " WHERE mapName = \"" + map.getName() + "\"" +
                " AND taskName = \"" + task.getName() + "\";"
        );
    }

    public void addSabotage(Map map, AbstractSabotage sabotage) {
        World sabotageWorld = sabotage.getLocation().getWorld();
        double sabotageX = sabotage.getLocation().getX();
        double sabotageY = sabotage.getLocation().getY();
        double sabotageZ = sabotage.getLocation().getZ();
        World sabotageOptionalWorld = sabotage.getOptionalLocation() == null ? null : sabotage.getOptionalLocation().getWorld();
        double sabotageOptionalX = sabotage.getOptionalLocation() == null ? 0 : sabotage.getOptionalLocation().getX();
        double sabotageOptionalY = sabotage.getOptionalLocation() == null ? 0 : sabotage.getOptionalLocation().getY();
        double sabotageOptionalZ = sabotage.getOptionalLocation() == null ? 0 : sabotage.getOptionalLocation().getZ();
        World sabotageOptionalWorld2 = sabotage.getOptionalLocation2() == null ? null : sabotage.getOptionalLocation2().getWorld();
        double sabotageOptionalX2 = sabotage.getOptionalLocation2() == null ? 0 : sabotage.getOptionalLocation2().getX();
        double sabotageOptionalY2 = sabotage.getOptionalLocation2() == null ? 0 : sabotage.getOptionalLocation2().getY();
        double sabotageOptionalZ2 = sabotage.getOptionalLocation2() == null ? 0 : sabotage.getOptionalLocation2().getZ();
        World sabotageOptionalWorld3 = sabotage.getOptionalLocation3() == null ? null : sabotage.getOptionalLocation3().getWorld();
        double sabotageOptionalX3 = sabotage.getOptionalLocation3() == null ? 0 : sabotage.getOptionalLocation3().getX();
        double sabotageOptionalY3 = sabotage.getOptionalLocation3() == null ? 0 : sabotage.getOptionalLocation3().getY();
        double sabotageOptionalZ3 = sabotage.getOptionalLocation3() == null ? 0 : sabotage.getOptionalLocation3().getZ();
        update("INSERT INTO `sabotages` (mapName, sabotageName, " +
                "sabotageWorld, sabotageX, sabotageY, sabotageZ, " +
                "sabotageOptionalWorld, sabotageOptionalX, sabotageOptionalY, sabotageOptionalZ, " +
                "sabotageOptionalWorld2, sabotageOptionalX2, sabotageOptionalY2, sabotageOptionalZ2, " +
                "sabotageOptionalWorld3, sabotageOptionalX3, sabotageOptionalY3, sabotageOptionalZ3) " +
                "VALUES(\"" + map.getName() + "\", \"" + sabotage.getName() + "\", " +
                "\"" + Objects.requireNonNull(sabotageWorld).getName() + "\", \"" + sabotageX + "\", \"" + sabotageY + "\", \"" + sabotageZ + "\", " +
                "\"" + (sabotageOptionalWorld == null ? null : sabotageOptionalWorld.getName()) + "\", \"" + sabotageOptionalX + "\", \"" + sabotageOptionalY + "\", \"" + sabotageOptionalZ + "\", " +
                "\"" + (sabotageOptionalWorld2 == null ? null : sabotageOptionalWorld2.getName()) + "\", \"" + sabotageOptionalX2 + "\", \"" + sabotageOptionalY2 + "\", \"" + sabotageOptionalZ2 + "\", " +
                "\"" + (sabotageOptionalWorld3 == null ? null : sabotageOptionalWorld3.getName()) + "\", \"" + sabotageOptionalX3 + "\", \"" + sabotageOptionalY3 + "\", \"" + sabotageOptionalZ3 + "\");"
        );
    }

    public void removeSabotage(Map map, String name, Location location) {
        World sabotageWorld = location.getWorld();
        double sabotageX = location.getX();
        double sabotageY = location.getY();
        double sabotageZ = location.getZ();
        update("DELETE FROM `sabotages`" +
                " WHERE mapName = \"" + map.getName() + "\"" +
                " AND sabotageName = \"" + name + "\"" +
                " AND sabotageWorld = \"" + Objects.requireNonNull(sabotageWorld).getName() + "\"" +
                " AND sabotageX = \"" + sabotageX + "\"" +
                " AND sabotageY = \"" + sabotageY + "\"" +
                " AND sabotageZ = \"" + sabotageZ + "\";"
        );
    }

    public void updateSabotage(Map map, AbstractSabotage sabotage) {
        World sabotageWorld = sabotage.getLocation().getWorld();
        double sabotageX = sabotage.getLocation().getX();
        double sabotageY = sabotage.getLocation().getY();
        double sabotageZ = sabotage.getLocation().getZ();
        World sabotageOptionalWorld = sabotage.getOptionalLocation() == null ? null : sabotage.getOptionalLocation().getWorld();
        double sabotageOptionalX = sabotage.getOptionalLocation() == null ? 0 : sabotage.getOptionalLocation().getX();
        double sabotageOptionalY = sabotage.getOptionalLocation() == null ? 0 : sabotage.getOptionalLocation().getY();
        double sabotageOptionalZ = sabotage.getOptionalLocation() == null ? 0 : sabotage.getOptionalLocation().getZ();
        World sabotageOptionalWorld2 = sabotage.getOptionalLocation2() == null ? null : sabotage.getOptionalLocation2().getWorld();
        double sabotageOptionalX2 = sabotage.getOptionalLocation2() == null ? 0 : sabotage.getOptionalLocation2().getX();
        double sabotageOptionalY2 = sabotage.getOptionalLocation2() == null ? 0 : sabotage.getOptionalLocation2().getY();
        double sabotageOptionalZ2 = sabotage.getOptionalLocation2() == null ? 0 : sabotage.getOptionalLocation2().getZ();
        World sabotageOptionalWorld3 = sabotage.getOptionalLocation3() == null ? null : sabotage.getOptionalLocation3().getWorld();
        double sabotageOptionalX3 = sabotage.getOptionalLocation3() == null ? 0 : sabotage.getOptionalLocation3().getX();
        double sabotageOptionalY3 = sabotage.getOptionalLocation3() == null ? 0 : sabotage.getOptionalLocation3().getY();
        double sabotageOptionalZ3 = sabotage.getOptionalLocation3() == null ? 0 : sabotage.getOptionalLocation3().getZ();
        update("UPDATE `sabotages`" +
                " SET" +
                " sabotageWorld = \"" + Objects.requireNonNull(sabotageWorld).getName() + "\"," +
                " sabotageX = \"" + sabotageX + "\"," +
                " sabotageY = \"" + sabotageY + "\"," +
                " sabotageZ = \"" + sabotageZ + "\"," +
                " sabotageOptionalWorld = \"" + (sabotageOptionalWorld == null ? null : sabotageOptionalWorld.getName()) + "\"," +
                " sabotageOptionalX = \"" + sabotageOptionalX + "\"," +
                " sabotageOptionalY = \"" + sabotageOptionalY + "\"," +
                " sabotageOptionalZ = \"" + sabotageOptionalZ + "\"," +
                " sabotageOptionalWorld2 = \"" + (sabotageOptionalWorld2 == null ? null : sabotageOptionalWorld2.getName()) + "\"," +
                " sabotageOptionalX2 = \"" + sabotageOptionalX2 + "\"," +
                " sabotageOptionalY2 = \"" + sabotageOptionalY2 + "\"," +
                " sabotageOptionalZ2 = \"" + sabotageOptionalZ2 + "\"," +
                " sabotageOptionalWorld3 = \"" + (sabotageOptionalWorld3 == null ? null : sabotageOptionalWorld3.getName()) + "\"," +
                " sabotageOptionalX3 = \"" + sabotageOptionalX3 + "\"," +
                " sabotageOptionalY3 = \"" + sabotageOptionalY3 + "\"," +
                " sabotageOptionalZ3 = \"" + sabotageOptionalZ3 + "\"" +
                " WHERE mapName = \"" + map.getName() + "\"" +
                " AND sabotageName = \"" + sabotage.getName() + "\";"
        );
    }
}
