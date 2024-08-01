package dev.aarow.regions.handlers.impl;

import dev.aarow.regions.data.player.Profile;
import dev.aarow.regions.data.player.ProfileChatRegionEdit;
import dev.aarow.regions.data.region.Region;
import dev.aarow.regions.handlers.BaseHandler;
import dev.aarow.regions.menus.RegionMenu;
import dev.aarow.regions.utility.chat.CC;
import dev.aarow.regions.utility.general.StringUtility;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ProfileRegionEditHandler extends BaseHandler {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        Profile profile = regions.getProfileManager().get(player);
        ProfileChatRegionEdit regionEdit = profile.getProfileChatRegionEdit();

        if(regionEdit == null) return;

        event.setCancelled(true);

        if(event.getMessage().equalsIgnoreCase("cancel")){
            profile.setProfileChatRegionEdit(null);

            player.sendMessage(CC.translate("&7[&3&lRegions&7] &cYou stopped the " + StringUtility.getNiceEnumString(regionEdit.getAction().name()) + " action."));
            return;
        }

        Region region = regionEdit.getRegion();

        switch(regionEdit.getAction()){
            case RENAME:
                String name = event.getMessage();

                if(regions.getRegionManager().getByName(name) != null){
                    player.sendMessage(CC.translate("&7[&3&lRegions&7] &cThat region already exists."));
                    return;
                }

                region.setName(name);
                region.save();

                player.sendMessage(CC.translate("&7[&3&lRegions&7] &aYou successfully changed this region's name to " + name + "."));

                new RegionMenu(region).open(player);

                profile.setProfileChatRegionEdit(null);
                break;
            case WHITELIST_ADD:
                Player addToWhitelist = Bukkit.getPlayer(event.getMessage());

                if(addToWhitelist == null){
                    player.sendMessage(CC.translate("&7[&3&lRegions&7] &cPlayer is offline."));
                    return;
                }
                if(region.getWhitelisted().contains(addToWhitelist.getUniqueId())){
                    player.sendMessage(CC.translate("&7[&3&lRegions&7] &cThat player is already whitelisted."));
                    return;
                }

                region.getWhitelisted().add(addToWhitelist.getUniqueId());

                region.save();

                player.sendMessage(CC.translate("&7[&3&lRegions&7] &aYou successfully added " + addToWhitelist.getName() + " to the " + region.getName() + " region's whitelist."));
                break;
            case WHITELIST_REMOVE:
                Player removeFromWhitelist = Bukkit.getPlayer(event.getMessage());

                if(removeFromWhitelist == null){
                    player.sendMessage(CC.translate("&7[&3&lRegions&7] &cPlayer is offline."));
                    return;
                }
                if(!region.getWhitelisted().contains(removeFromWhitelist.getUniqueId())){
                    player.sendMessage(CC.translate("&7[&3&lRegions&7] &cThat player isn't whitelisted."));
                    return;
                }

                region.getWhitelisted().remove(removeFromWhitelist.getUniqueId());

                region.save();

                player.sendMessage(CC.translate("&7[&3&lRegions&7] &cYou successfully removed " + removeFromWhitelist.getName() + " from the " + region.getName() + " region's whitelist."));
                break;
        }
    }
}
