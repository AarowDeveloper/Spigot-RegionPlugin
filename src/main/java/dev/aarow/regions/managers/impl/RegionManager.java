package dev.aarow.regions.managers.impl;

import dev.aarow.regions.data.region.Region;
import dev.aarow.regions.data.region.flag.RegionFlagState;
import dev.aarow.regions.data.region.flag.RegionFlag;
import dev.aarow.regions.data.region.flag.impl.BlockBreakFlag;
import dev.aarow.regions.data.region.flag.impl.BlockPlaceFlag;
import dev.aarow.regions.data.region.flag.impl.EntityDamageFlag;
import dev.aarow.regions.data.region.flag.impl.PlayerInteractFlag;
import dev.aarow.regions.managers.Manager;
import dev.aarow.regions.utility.data.CoordinatePair;
import dev.aarow.regions.utility.general.GeneralSerializer;
import dev.aarow.regions.utility.general.LocationUtility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Stream;

public class RegionManager extends Manager {

    private List<Region> regions = new ArrayList<>();
    private List<RegionFlag> regionFlags = new ArrayList<>();

    private Map<CoordinatePair, Region> coordinates = new HashMap<>();

    @Override
    public void setup() {
        this.registerFlags();

        this.importRegions();
    }

    private void registerFlags(){
        this.regionFlags.add(new BlockBreakFlag());
        this.regionFlags.add(new BlockPlaceFlag());
        this.regionFlags.add(new PlayerInteractFlag());
        this.regionFlags.add(new EntityDamageFlag());
    }

    public void addCustomFlag(RegionFlag regionFlag){
        this.regionFlags.add(regionFlag);
        this.regions.forEach(region -> {
            region.getRegionFlags().put(regionFlag, RegionFlagState.WHITELIST);
            region.save();
        });
    }

    public void addRegion(Region region){
        this.regions.add(region);

        region.startParticleTask();

        region.save();
    }

    public void importRegions(){
        String query = "SELECT NAME, FIRSTCORNER, SECONDCORNER, WHITELISTED, FLAGS, CREATED FROM regionsplugin";

        try (PreparedStatement stmt = super.regions.getDatabaseManager().getConnection().prepareStatement(query);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                String name = resultSet.getString("NAME");
                Location firstCorner = LocationUtility.deserializeBlock(resultSet.getString("FIRSTCORNER"));
                Location secondCorner = LocationUtility.deserializeBlock(resultSet.getString("SECONDCORNER"));
                List<UUID> whitelisted = GeneralSerializer.deserializeUUIDs(resultSet.getString("WHITELISTED"));
                Map<RegionFlag, RegionFlagState> flags = GeneralSerializer.deserializeFlags(resultSet.getString("FLAGS"));
                long created = resultSet.getLong("CREATED");

                Region region = new Region(name, firstCorner, secondCorner, flags, created);

                whitelisted.forEach(uuid -> region.getWhitelisted().add(uuid));

                regions.add(region);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<RegionFlag, RegionFlagState> generateRegionFlags(){
        Map<RegionFlag, RegionFlagState> regionFlagStates = new HashMap<>();

        this.regionFlags.forEach(regionFlag -> regionFlagStates.put(regionFlag, RegionFlagState.WHITELIST));

        return regionFlagStates;
    }

    public Region getByName(String name){
        return this.regions.stream().filter(region -> region.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public RegionFlag getFlagByName(String name){
        return regionFlags.stream().filter(regionFlag -> regionFlag.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public RegionFlagState getFlagStateByName(String name){
        return Stream.of(RegionFlagState.values()).filter(regionFlagState -> regionFlagState.name().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public Map<CoordinatePair, Region> getCoordinates() {
        return coordinates;
    }

    public List<Region> getRegions() {
        return regions;
    }
}
