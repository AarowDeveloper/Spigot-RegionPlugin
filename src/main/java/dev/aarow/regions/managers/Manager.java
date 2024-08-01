package dev.aarow.regions.managers;

import dev.aarow.regions.Regions;
import dev.aarow.regions.plugin.RegionsPlugin;
import dev.aarow.regions.utility.other.Task;

public abstract class Manager {

    public Regions regions = Regions.getInstance();
    public RegionsPlugin plugin = RegionsPlugin.getInstance();

    public Manager(){
        Task.runASync(this::setup);
    }

    public abstract void setup();
}
