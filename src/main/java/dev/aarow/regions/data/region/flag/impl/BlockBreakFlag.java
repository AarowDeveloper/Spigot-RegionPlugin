package dev.aarow.regions.data.region.flag.impl;

import dev.aarow.regions.data.region.Region;
import dev.aarow.regions.data.region.flag.RegionFlag;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakFlag extends RegionFlag {

    @Override
    public String getName() {
        return "Block Break Flag";
    }

    @Override
    public Material getMenuMaterial() {
        return Material.STONE_PICKAXE;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Player player = event.getPlayer();

        if(doesBypass(player)) return;

        Block block = event.getBlock();

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
    }
}
