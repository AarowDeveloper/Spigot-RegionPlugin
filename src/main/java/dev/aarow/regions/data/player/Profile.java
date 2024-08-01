package dev.aarow.regions.data.player;

import dev.aarow.regions.data.region.Region;

import java.util.UUID;

public class Profile {

    private UUID uuid;

    private ProfileWandData profileWandData;

    private Region currentRegion;

    private ProfileChatRegionEdit profileChatRegionEdit;

    public Profile(UUID uuid){
        this.uuid = uuid;

        this.profileWandData = new ProfileWandData(null, null);
    }

    public ProfileWandData getProfileWandData() {
        return profileWandData;
    }

    public Region getCurrentRegion() {
        return currentRegion;
    }

    public void setCurrentRegion(Region currentRegion) {
        this.currentRegion = currentRegion;
    }

    public ProfileChatRegionEdit getProfileChatRegionEdit() {
        return profileChatRegionEdit;
    }

    public void setProfileChatRegionEdit(ProfileChatRegionEdit profileChatRegionEdit) {
        this.profileChatRegionEdit = profileChatRegionEdit;
    }
}
