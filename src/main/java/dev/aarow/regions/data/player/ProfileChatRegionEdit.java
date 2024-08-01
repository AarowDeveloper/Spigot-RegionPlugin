package dev.aarow.regions.data.player;

import dev.aarow.regions.data.region.Region;

public class ProfileChatRegionEdit {

    private Region region;
    private Action action;

    public enum Action {
        RENAME, WHITELIST_ADD, WHITELIST_REMOVE;
    }

    public ProfileChatRegionEdit(Region region, Action action){
        this.region = region;
        this.action = action;
    }

    public Region getRegion() {
        return region;
    }

    public Action getAction() {
        return action;
    }
}
