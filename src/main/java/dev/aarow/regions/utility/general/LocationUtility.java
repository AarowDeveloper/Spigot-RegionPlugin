package dev.aarow.regions.utility.general;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class LocationUtility {

    public static List<Location> getBetweenLocations(Location firstCorner, Location secondCorner) {
        List<Location> blocks = new ArrayList<>();

        World world = firstCorner.getWorld();

        int minX = Math.min(firstCorner.getBlockX(), secondCorner.getBlockX());
        int minY = Math.min(firstCorner.getBlockY(), secondCorner.getBlockY());
        int minZ = Math.min(firstCorner.getBlockZ(), secondCorner.getBlockZ());

        int maxX = Math.max(firstCorner.getBlockX(), secondCorner.getBlockX());
        int maxY = Math.max(firstCorner.getBlockY(), secondCorner.getBlockY());
        int maxZ = Math.max(firstCorner.getBlockZ(), secondCorner.getBlockZ());

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    blocks.add(new Location(world, x, y, z));
                }
            }
        }

        return blocks;
    }


    public static List<Location> getBorderCuboID(Location firstCorner, Location secondCorner) {
        List<Location> outline = new ArrayList<>();

        World world = firstCorner.getWorld();

        int minX = Math.min(firstCorner.getBlockX(), secondCorner.getBlockX());
        int minY = Math.min(firstCorner.getBlockY(), secondCorner.getBlockY());
        int minZ = Math.min(firstCorner.getBlockZ(), secondCorner.getBlockZ());

        int maxX = Math.max(firstCorner.getBlockX(), secondCorner.getBlockX());
        int maxY = Math.max(firstCorner.getBlockY(), secondCorner.getBlockY());
        int maxZ = Math.max(firstCorner.getBlockZ(), secondCorner.getBlockZ());

        // Add edge coordinates
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    if (x == minX || x == maxX) {
                        outline.add(new Location(world, x, y, z));
                    }
                    else if (y == minY || y == maxY) {
                        outline.add(new Location(world, x, y, z));
                    }
                    else if (z == minZ || z == maxZ) {
                        outline.add(new Location(world, x, y, z));
                    }
                }
            }
        }

        return outline;
    }

    public static String serializeBlock(Location location){
        return location.getWorld().getName() + ";" + location.getBlockX() + ";" + location.getBlockY() + ";" + location.getBlockZ();
    }

    public static Location deserializeBlock(String input){
        String[] args = input.split(";");

        return new Location(Bukkit.getWorld(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
    }

    public static String getNiceString(Location location){
        return location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ();
    }
}
