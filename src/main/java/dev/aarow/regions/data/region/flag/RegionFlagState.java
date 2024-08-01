package dev.aarow.regions.data.region.flag;

import org.bukkit.ChatColor;

public enum RegionFlagState {
    EVERYONE(ChatColor.GREEN), WHITELIST(ChatColor.YELLOW), NONE(ChatColor.RED);

    ChatColor chatColor;

    RegionFlagState(ChatColor chatColor){
        this.chatColor = chatColor;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }
}
