package dev.aarow.regions.managers.impl;

import dev.aarow.regions.data.player.Profile;
import dev.aarow.regions.managers.Manager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProfileManager extends Manager {

    private Map<UUID, Profile> profiles = new HashMap<>();

    @Override
    public void setup() {}

    public Profile get(Player player){
        this.profiles.putIfAbsent(player.getUniqueId(), new Profile(player.getUniqueId()));

        return this.profiles.get(player.getUniqueId());
    }

    public Profile get(UUID uuid){
        this.profiles.putIfAbsent(uuid, new Profile(uuid));
        return this.profiles.get(uuid);
    }
}
