package org.toedev.amongus.sql;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.map.Map;
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
            "`taskAreaMaxZ` BIGINT NOT NULL" +
            ");";

    public void load() {
        connect();
        update(SQLiteCreateMapsTable);
        update(SQLiteCreateTasksTable);
    }

    public ResultSet getAllMaps() {
        return getResult("SELECT * FROM `maps`");
    }

    public ResultSet getAllTasks() {
        return getResult("SELECT * FROM `tasks`");
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
        World taskAreaMinWorld = task.getTaskAreaMinLocation().getWorld();
        double taskAreaMinX = task.getTaskAreaMinLocation().getX();
        double taskAreaMinY = task.getTaskAreaMinLocation().getY();
        double taskAreaMinZ = task.getTaskAreaMinLocation().getZ();
        World taskAreaMaxWorld = task.getTaskAreaMaxLocation().getWorld();
        double taskAreaMaxX = task.getTaskAreaMaxLocation().getX();
        double taskAreaMaxY = task.getTaskAreaMaxLocation().getY();
        double taskAreaMaxZ = task.getTaskAreaMaxLocation().getZ();
        update("INSERT INTO `tasks` (mapName, taskName, " +
                "taskWorld, taskX, taskY, taskZ, " +
                "taskAreaMinWorld, taskAreaMinX, taskAreaMinY, taskAreaMinZ, " +
                "taskAreaMaxWorld, taskAreaMaxX, taskAreaMaxY, taskAreaMaxZ) " +
                "VALUES(\"" + map.getName() + "\", \"" + task.getName() + "\", " +
                "\"" + Objects.requireNonNull(taskWorld).getName() + "\", \"" + taskX + "\", \"" + taskY + "\", \"" + taskZ + "\", " +
                "\"" + Objects.requireNonNull(taskAreaMinWorld).getName() + "\", \"" + taskAreaMinX + "\", \"" + taskAreaMinY + "\", \"" + taskAreaMinZ + "\", " +
                "\"" + Objects.requireNonNull(taskAreaMaxWorld).getName() + "\", \"" + taskAreaMaxX + "\", \"" + taskAreaMaxY + "\", \"" + taskAreaMaxZ + "\");"
        );
    }

    /*public void updateTask(Map map, AbstractTask task) {
        World taskWorld = task.getLocation().getWorld();
        double taskX = task.getLocation().getX();
        double taskY = task.getLocation().getY();
        double taskZ = task.getLocation().getZ();
        update("UPDATE `tasks`" +
                " SET" +
                " taskWorld = \"" + Objects.requireNonNull(taskWorld).getName() + "\"," +
                " taskX = \"" + taskX + "\"," +
                " taskY = \"" + taskY + "\"," +
                " taskZ = \"" + taskZ + "\"" +
                " WHERE mapName = \"" + map.getName() + "\"" +
                " AND taskName = \"" + task.getName() + "\";"
        );
    }*/
}
