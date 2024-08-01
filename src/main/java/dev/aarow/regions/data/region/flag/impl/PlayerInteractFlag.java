package dev.aarow.regions.data.region.flag.impl;

import dev.aarow.regions.data.region.Region;
import dev.aarow.regions.data.region.flag.RegionFlag;
import dev.aarow.regions.utility.general.BukkitUtility;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractFlag extends RegionFlag {

    @Override
    public String getName() {
        return "Player Interact Flag";
    }

    @Override
    public Material getMenuMaterial() {
        return Material.LEVER;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();

        if(doesBypass(player)) return;

        if(event.getAction().name().contains("BLOCK")){
            Block block = event.getClickedBlock();

            if(!BukkitUtility.isInteractable(block)) return;

            Region region = getRegionForLocation(block.getLocation());

            if(region == null) return;

            switch(region.getFlagState(this)){
                case WHITELIST:
                    if(!region.isWhitelisted(player)) event.setCancelled(true);
                    break;
                case NONE:
                    event.setCancelled(true);
                    break;
            }
        }else{
            Region region = getRegionForLocation(player.getLocation());

            if(region == null) return;

            switch(region.getFlagState(this)){
                case WHITELIST:
                    if(!region.isWhitelisted(player)) event.setCancelled(true);
                    break;
                case NONE:
                    event.setCancelled(true);
                    break;
            }
        }
    }
}
