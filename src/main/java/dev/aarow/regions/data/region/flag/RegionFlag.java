package dev.aarow.regions.data.region.flag;

import dev.aarow.regions.Regions;
import dev.aarow.regions.data.player.Profile;
import dev.aarow.regions.data.region.Region;
import dev.aarow.regions.plugin.RegionsPlugin;
import dev.aarow.regions.utility.data.CoordinatePair;
import dev.aarow.regions.utility.general.ReflectionUtility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.lang.reflect.InvocationTargetException;

public abstract class RegionFlag implements Listener {

    public abstract String getName();
    public abstract Material getMenuMaterial();

    public RegionFlag(){
        Bukkit.getPluginManager().registerEvents(this, RegionsPlugin.getInstance());
    }

    public Region getRegionForLocation(Location location){
        return Regions.getInstance().getRegionManager().getCoordinates().get(CoordinatePair.getFrom(location));
    }

    public boolean doesBypass(Player player){
        return player.hasPermission("region.bypass");
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof RegionFlag)) return false;

        return ((RegionFlag) obj).getName().equals(this.getName());
    }
}
