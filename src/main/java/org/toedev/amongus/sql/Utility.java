package org.toedev.amongus.sql;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.toedev.amongus.AmongUs;
import org.toedev.amongus.map.Map;

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
            "`mapMinCornerWorld` varchar(64) NOT NULL," +
            "`mapMinCornerX` BIGINT NOT NULL," +
            "`mapMinCornerY` BIGINT NOT NULL," +
            "`mapMinCornerZ` BIGINT NOT NULL," +
            "`mapMaxCornerWorld` varchar(64) NOT NULL," +
            "`mapMaxCornerX` BIGINT NOT NULL," +
            "`mapMaxCornerY` BIGINT NOT NULL," +
            "`mapMaxCornerZ` BIGINT NOT NULL," +
            "`startMinCornerWorld` varchar(64) NOT NULL," +
            "`startMinCornerX` BIGINT NOT NULL," +
            "`startMinCornerY` BIGINT NOT NULL," +
            "`startMinCornerZ` BIGINT NOT NULL," +
            "`startMaxCornerWorld` varchar(64) NOT NULL," +
            "`startMaxCornerX` BIGINT NOT NULL," +
            "`startMaxCornerY` BIGINT NOT NULL," +
            "`startMaxCornerZ` BIGINT NOT NULL," +
            "`meetingMinCornerWorld` varchar(64) NOT NULL," +
            "`meetingMinCornerX` BIGINT NOT NULL," +
            "`meetingMinCornerY` BIGINT NOT NULL," +
            "`meetingMinCornerZ` BIGINT NOT NULL," +
            "`meetingMaxCornerWorld` varchar(64) NOT NULL," +
            "`meetingMaxCornerX` BIGINT NOT NULL," +
            "`meetingMaxCornerY` BIGINT NOT NULL," +
            "`meetingMaxCornerZ` BIGINT NOT NULL" +
            ");";

    public void load() {
        connect();
        update(SQLiteCreateMapsTable); //TODO CREATE TASKS TABLE
    }

    public ResultSet getAllMaps() {
        return getResult("SELECT * FROM `maps`");
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
        Location mapMinCorner = map.getMapMinCorner();
        Location mapMaxCorner = map.getMapMaxCorner();
        Location startMinCorner = map.getStartMinCorner();
        Location startMaxCorner = map.getStartMaxCorner();
        Location meetingMinCorner = map.getMeetingMinCorner();
        Location meetingMaxCorner = map.getMeetingMaxCorner();
        update("INSERT INTO `maps` (mapName, mapMinCornerWorld, mapMinCornerX, mapMinCornerY, mapMinCornerZ, " +
                "mapMaxCornerWorld, mapMaxCornerX, mapMaxCornerY, mapMaxCornerZ, " +
                "startMinCornerWorld, startMinCornerX, startMinCornerY, startMinCornerZ, " +
                "startMaxCornerWorld, startMaxCornerX, startMaxCornerY, startMaxCornerZ, " +
                "meetingMinCornerWorld, meetingMinCornerX, meetingMinCornerY, meetingMinCornerZ, " +
                "meetingMaxCornerWorld, meetingMaxCornerX, meetingMaxCornerY, meetingMaxCornerZ) " +
                "VALUES(\"" + map.getName() + "\", " +
                "\"" + Objects.requireNonNull(mapMinCorner.getWorld()).getName() + "\", \"" + mapMinCorner.getX() + "\", \"" + mapMinCorner.getY() + "\", \"" + mapMinCorner.getZ() + "\", " +
                "\"" + Objects.requireNonNull(mapMaxCorner.getWorld()).getName() + "\", \"" + mapMaxCorner.getX() + "\", \"" + mapMaxCorner.getY() + "\", \"" + mapMaxCorner.getZ() + "\", " +
                "\"" + Objects.requireNonNull(startMinCorner.getWorld()).getName() + "\", \"" + startMinCorner.getX() + "\", \"" + startMinCorner.getY() + "\", \"" + startMinCorner.getZ() + "\", " +
                "\"" + Objects.requireNonNull(startMaxCorner.getWorld()).getName() + "\", \"" + startMaxCorner.getX() + "\", \"" + startMaxCorner.getY() + "\", \"" + startMaxCorner.getZ() + "\", " +
                "\"" + Objects.requireNonNull(meetingMinCorner.getWorld()).getName() + "\", \"" + meetingMinCorner.getX() + "\", \"" + meetingMinCorner.getY() + "\", \"" + meetingMinCorner.getZ() + "\", " +
                "\"" + Objects.requireNonNull(meetingMaxCorner.getWorld()).getName() + "\", \"" + meetingMaxCorner.getX() + "\", \"" + meetingMaxCorner.getY() + "\", \"" + meetingMaxCorner.getZ() + "\");"
        );
    }

    public void updateMap(Map map) {
        Location mapMinCorner = map.getMapMinCorner();
        Location mapMaxCorner = map.getMapMaxCorner();
        Location startMinCorner = map.getStartMinCorner();
        Location startMaxCorner = map.getStartMaxCorner();
        Location meetingMinCorner = map.getMeetingMinCorner();
        Location meetingMaxCorner = map.getMeetingMaxCorner();
        update("UPDATE `maps`" +
                " SET" +
                " mapMinCornerWorld = \"" + Objects.requireNonNull(mapMinCorner.getWorld()).getName() + "\"," +
                " mapMinCornerX = \"" + mapMinCorner.getX() + "\"," +
                " mapMinCornerY = \"" + mapMinCorner.getY() + "\"," +
                " mapMinCornerZ = \"" + mapMinCorner.getZ() + "\"," +
                " mapMaxCornerWorld = \"" + Objects.requireNonNull(mapMaxCorner.getWorld()).getName() + "\"," +
                " mapMaxCornerX = \"" + mapMaxCorner.getX() + "\"," +
                " mapMaxCornerY = \"" + mapMaxCorner.getY() + "\"," +
                " mapMaxCornerZ = \"" + mapMaxCorner.getZ() + "\"," +
                " startMinCornerWorld = \"" + Objects.requireNonNull(startMinCorner.getWorld()).getName() + "\"," +
                " startMinCornerX = \"" + startMinCorner.getX() + "\"," +
                " startMinCornerY = \"" + startMinCorner.getY() + "\"," +
                " startMinCornerZ = \"" + startMinCorner.getZ() + "\"," +
                " startMaxCornerWorld = \"" + Objects.requireNonNull(startMaxCorner.getWorld()).getName() + "\"," +
                " startMaxCornerX = \"" + startMaxCorner.getX() + "\"," +
                " startMaxCornerY = \"" + startMaxCorner.getY() + "\"," +
                " startMaxCornerZ = \"" + startMaxCorner.getZ() + "\"," +
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
}
