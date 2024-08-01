package dev.aarow.regions.plugin;

import dev.aarow.regions.Regions;
import org.bukkit.plugin.java.JavaPlugin;

public class RegionsPlugin extends JavaPlugin {

    private static RegionsPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        new Regions().init();

        this.registerConfiguration();
    }

    @Override
    public void onDisable(){
        Regions.getInstance().destroy();
    }

    protected void registerConfiguration(){
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }

    public static RegionsPlugin getInstance() {
        return instance;
    }
}
