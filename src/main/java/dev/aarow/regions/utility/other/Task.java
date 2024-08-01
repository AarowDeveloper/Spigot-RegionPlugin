package dev.aarow.regions.utility.other;

import dev.aarow.regions.plugin.RegionsPlugin;
import org.bukkit.Bukkit;

public class Task {

    public static void runASync(Call call){
        Bukkit.getScheduler().runTaskLater(RegionsPlugin.getInstance(), call::call, 1L);
    }


    public interface Call {
        void call();
    }
}
