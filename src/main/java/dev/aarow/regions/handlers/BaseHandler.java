package dev.aarow.regions.handlers;

import dev.aarow.regions.Regions;
import dev.aarow.regions.plugin.RegionsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class BaseHandler implements Listener {

    public Regions regions = Regions.getInstance();
    public RegionsPlugin plugin = RegionsPlugin.getInstance();

    public BaseHandler(){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
}
