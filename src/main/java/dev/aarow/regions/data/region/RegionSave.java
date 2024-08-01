package dev.aarow.regions.data.region;

import dev.aarow.regions.Regions;
import dev.aarow.regions.utility.general.GeneralSerializer;
import dev.aarow.regions.utility.general.LocationUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegionSave {

    private Connection connection = Regions.getInstance().getDatabaseManager().getConnection();
    private Region region;

    public RegionSave(Region region){
        this.region = region;
    }

    public void init(){
        if(exists()){
            this.update();
        }else{
            this.create();
        }
    }

    public void create() {
        try {
            PreparedStatement insert = connection
                        .prepareStatement("INSERT INTO regionsplugin (NAME,FIRSTCORNER,SECONDCORNER,WHITELISTED,FLAGS,CREATED) VALUES (?,?,?,?,?,?)");

            insert.setString(1, region.getName());
            insert.setString(2, LocationUtility.serializeBlock(region.getFirstCornerLocation()));
            insert.setString(3, LocationUtility.serializeBlock(region.getSecondCornerLocation()));
            insert.setString(4, GeneralSerializer.serializeUUIDs(region.getWhitelisted()));
            insert.setString(5, GeneralSerializer.serializeFlagStates(region.getRegionFlags()));
            insert.setLong(6, region.getCreated());

            insert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        try {
            PreparedStatement update = connection.prepareStatement(
                    "UPDATE regionsplugin SET FIRSTCORNER = ?, SECONDCORNER = ?, WHITELISTED = ?, FLAGS = ?, CREATED = ? WHERE NAME = ?"
            );

            update.setString(1, LocationUtility.serializeBlock(region.getFirstCornerLocation()));
            update.setString(2, LocationUtility.serializeBlock(region.getSecondCornerLocation()));
            update.setString(3, GeneralSerializer.serializeUUIDs(region.getWhitelisted()));
            update.setString(4, GeneralSerializer.serializeFlagStates(region.getRegionFlags()));
            update.setLong(5, region.getCreated());
            update.setString(6, region.getName());

            update.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean exists() {
        try {
            PreparedStatement statement = connection
                    .prepareStatement("SELECT * FROM regionsplugin WHERE NAME=?");
            statement.setString(1, region.getName());

            ResultSet results = statement.executeQuery();
            if (results.next()) return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
