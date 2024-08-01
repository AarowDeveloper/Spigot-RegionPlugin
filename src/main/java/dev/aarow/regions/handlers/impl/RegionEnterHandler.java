package dev.aarow.regions.handlers.impl;

import dev.aarow.regions.data.player.Profile;
import dev.aarow.regions.data.region.Region;
import dev.aarow.regions.handlers.BaseHandler;
import dev.aarow.regions.utility.chat.CC;
import dev.aarow.regions.utility.data.CoordinatePair;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class RegionEnterHandler extends BaseHandler {

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        Profile profile = regions.getProfileManager().get(player);
        Region region = regions.getRegionManager().getCoordinates().get(CoordinatePair.getFrom(event.getTo()));

        if(region == null){
            profile.setCurrentRegion(null);
            return;
        }
        if(profile.getCurrentRegion() != null){
            if(profile.getCurrentRegion().equals(region)) return;
        }

        profile.setCurrentRegion(region);

        player.sendTitle(CC.translate("&3&l" + region.getName() + " Region"), CC.translate("&bYou entered a new region."));
    }
}
