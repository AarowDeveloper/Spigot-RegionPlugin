package dev.aarow.regions;

import dev.aarow.regions.commands.impl.RegionCommand;
import dev.aarow.regions.handlers.impl.MenuHandler;
import dev.aarow.regions.handlers.impl.ProfileRegionEditHandler;
import dev.aarow.regions.handlers.impl.RegionEnterHandler;
import dev.aarow.regions.handlers.impl.RegionWandHandler;
import dev.aarow.regions.managers.impl.DatabaseManager;
import dev.aarow.regions.managers.impl.MenuManager;
import dev.aarow.regions.managers.impl.ProfileManager;
import dev.aarow.regions.managers.impl.RegionManager;
import dev.aarow.regions.plugin.RegionsPlugin;
import dev.aarow.regions.utility.general.StringUtility;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.RegisteredListener;

public class Regions {

    private static Regions instance;

    private DatabaseManager databaseManager;
    private ProfileManager profileManager;
    private RegionManager regionManager;
    private MenuManager menuManager;

    public void init(){
        instance = this;

        this.databaseManager = new DatabaseManager();
        this.profileManager = new ProfileManager();
        this.regionManager = new RegionManager();
        this.menuManager = new MenuManager();

        this.registerCommands();
        this.registerHandlers();

        StringUtility.startLoadingDotsTask();
    }

    public void destroy(){

    }

    protected void registerCommands(){
        new RegionCommand();
    }

    protected void registerHandlers(){
        new RegionWandHandler();
        new RegionEnterHandler();
        new MenuHandler();
        new ProfileRegionEditHandler();
    }

    public static Regions getInstance() {
        return instance;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public ProfileManager getProfileManager() {
        return profileManager;
    }

    public RegionManager getRegionManager() {
        return regionManager;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

}
