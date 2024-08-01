package dev.aarow.regions.data.region;

import dev.aarow.regions.Regions;
import dev.aarow.regions.data.region.flag.RegionFlag;
import dev.aarow.regions.data.region.flag.RegionFlagState;
import dev.aarow.regions.plugin.RegionsPlugin;
import dev.aarow.regions.utility.data.CoordinatePair;
import dev.aarow.regions.utility.general.LocationUtility;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Stream;

public class Region {

    private String name;

    private Location firstCornerLocation;
    private Location secondCornerLocation;

    private List<UUID> whitelisted = new ArrayList<>();

    private Map<RegionFlag, RegionFlagState> regionFlags = new HashMap<>();

    private long created;

    public Region(String name, Location firstCornerLocation, Location secondCornerLocation, Map<RegionFlag, RegionFlagState> regionFlags, long created){
        this.name = name;
        this.firstCornerLocation = firstCornerLocation;
        this.secondCornerLocation = secondCornerLocation;

        this.registerCoordinates();

        this.regionFlags = regionFlags;
        this.created = created;
    }

    public void registerCoordinates(){
        LocationUtility.getBetweenLocations(firstCornerLocation, secondCornerLocation).stream().map(CoordinatePair::getFrom).forEach(coordinatePair -> {
            Regions.getInstance().getRegionManager().getCoordinates().put(coordinatePair, this);
        });
    }

    public void startParticleTask(){
        long finishes = System.currentTimeMillis() + 5000;

        List<Location> outline = LocationUtility.getBorderCuboID(firstCornerLocation, secondCornerLocation);

        new BukkitRunnable(){
            public void run(){
                if(System.currentTimeMillis() > finishes){
                    this.cancel();
                    return;
                }

                outline.forEach(location -> location.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, location.clone().add(0.5, 0.5, 0.5), 1));
            }
        }.runTaskTimer(RegionsPlugin.getInstance(), 0, 4L);
    }

    public void save(){
        new RegionSave(this).init();
    }

    public String getName() {
        return name;
    }

    public Location getFirstCornerLocation() {
        return firstCornerLocation;
    }

    public Location getSecondCornerLocation() {
        return secondCornerLocation;
    }

    public List<UUID> getWhitelisted() {
        return whitelisted;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFirstCornerLocation(Location firstCornerLocation) {
        this.firstCornerLocation = firstCornerLocation;
    }

    public void setSecondCornerLocation(Location secondCornerLocation) {
        this.secondCornerLocation = secondCornerLocation;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Region)) return false;

        return ((Region) obj).getName().equals(this.getName());
    }

    public Map<RegionFlag, RegionFlagState> getRegionFlags() {
        return regionFlags;
    }

    public RegionFlagState getFlagState(RegionFlag regionFlag){
        return this.regionFlags.get(regionFlag);
    }

    public boolean isWhitelisted(Player player){
        return this.whitelisted.contains(player.getUniqueId());
    }

    public long getCreated() {
        return created;
    }
}
