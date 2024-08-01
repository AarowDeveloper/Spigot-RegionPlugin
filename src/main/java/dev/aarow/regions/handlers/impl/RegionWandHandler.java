package dev.aarow.regions.handlers.impl;

import dev.aarow.regions.data.player.Profile;
import dev.aarow.regions.handlers.BaseHandler;
import dev.aarow.regions.utility.chat.CC;
import dev.aarow.regions.utility.general.ItemUtility;
import dev.aarow.regions.utility.general.PlayerUtility;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public class RegionWandHandler extends BaseHandler {

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(!event.getAction().name().contains("BLOCK")) return;
        if(event.getItem() == null) return;

        if(!event.getItem().isSimilar(ItemUtility.getWandItem())) return;

        Player player = event.getPlayer();
        Profile profile = regions.getProfileManager().get(player);

        if(!PlayerUtility.checkPermission(player, "region.create")){
            player.getInventory().setItemInHand(null);
            return;
        }

        switch(event.getAction()){
            case LEFT_CLICK_BLOCK:
                event.setCancelled(true);
                profile.getProfileWandData().setFirstCornerLocation(event.getClickedBlock().getLocation());

                player.sendMessage(CC.translate("&7[&3&lRegions&7] &aYou successfully set the first corner location."));
                break;
            case RIGHT_CLICK_BLOCK:
                event.setCancelled(true);
                profile.getProfileWandData().setSecondCornerLocation(event.getClickedBlock().getLocation());

                player.sendMessage(CC.translate("&7[&3&lRegions&7] &aYou successfully set the second corner location."));
                break;
        }
    }
}
