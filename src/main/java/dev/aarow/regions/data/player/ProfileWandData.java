package dev.aarow.regions.data.player;

import dev.aarow.regions.Regions;
import dev.aarow.regions.data.region.Region;
import dev.aarow.regions.utility.data.CoordinatePair;
import dev.aarow.regions.utility.general.LocationUtility;
import org.bukkit.Location;

public class ProfileWandData {

    private Location firstCornerLocation;
    private Location secondCornerLocation;

    public ProfileWandData(Location firstCornerLocation, Location secondCornerLocation){
        this.firstCornerLocation = firstCornerLocation;
        this.secondCornerLocation = secondCornerLocation;
    }

    public boolean isNewRegionValid(){
        if(firstCornerLocation == null || secondCornerLocation == null) return false;

        if(firstCornerLocation.distance(secondCornerLocation) >= 200) return false;

        if(!firstCornerLocation.getWorld().getName().equals(secondCornerLocation.getWorld().getName())) return false;

        if(LocationUtility.getBetweenLocations(firstCornerLocation, secondCornerLocation).stream().map(CoordinatePair::getFrom).anyMatch(coordinatePair -> Regions.getInstance().getRegionManager().getCoordinates().containsKey(coordinatePair))) return false;

        return true;
    }

    public boolean isChangeValid(Region region){
        if(firstCornerLocation == null || secondCornerLocation == null) return false;

        if(firstCornerLocation.distance(secondCornerLocation) >= 200) return false;

        if(!firstCornerLocation.getWorld().getName().equals(secondCornerLocation.getWorld().getName())) return false;

        if(LocationUtility.getBetweenLocations(firstCornerLocation, secondCornerLocation).stream().map(CoordinatePair::getFrom).filter(coordinatePair -> Regions.getInstance().getRegionManager().getCoordinates().containsKey(coordinatePair)).anyMatch(coordinatePair -> !Regions.getInstance().getRegionManager().getCoordinates().get(coordinatePair).equals(region))) return false;

        return true;
    }

    public Location getFirstCornerLocation() {
        return firstCornerLocation;
    }

    public Location getSecondCornerLocation() {
        return secondCornerLocation;
    }

    public void setFirstCornerLocation(Location firstCornerLocation) {
        this.firstCornerLocation = firstCornerLocation;
    }

    public void setSecondCornerLocation(Location secondCornerLocation) {
        this.secondCornerLocation = secondCornerLocation;
    }
}
