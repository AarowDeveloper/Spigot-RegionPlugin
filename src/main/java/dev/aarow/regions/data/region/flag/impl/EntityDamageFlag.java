package dev.aarow.regions.data.region.flag.impl;

import dev.aarow.regions.data.region.Region;
import dev.aarow.regions.data.region.flag.RegionFlag;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageFlag extends RegionFlag {

    @Override
    public String getName() {
        return "Entity Damage Flag";
    }

    @Override
    public Material getMenuMaterial() {
        return Material.STONE_SWORD;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event){
        if(event.getDamager().getType() != EntityType.PLAYER) return;

        Player player = (Player) event.getDamager();

        if(doesBypass(player)) return;

        Region region = getRegionForLocation(event.getEntity().getLocation());

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
