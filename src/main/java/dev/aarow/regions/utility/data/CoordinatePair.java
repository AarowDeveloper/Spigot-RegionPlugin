package dev.aarow.regions.utility.data;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.Objects;

public class CoordinatePair {

    private World world;
    private int x;
    private int y;
    private int z;

    public CoordinatePair(World world, int x, int y, int z){
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static CoordinatePair getFrom(Location location){
        return new CoordinatePair(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    @Override
    public boolean equals(Object object){
        return object.hashCode() == this.hashCode();
    }

    @Override
    public int hashCode(){
        return Objects.hash(world, x, y, z);
    }
}
