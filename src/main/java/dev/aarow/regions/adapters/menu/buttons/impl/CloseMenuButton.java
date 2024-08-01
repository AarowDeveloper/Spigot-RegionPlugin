package dev.aarow.regions.adapters.menu.buttons.impl;

import dev.aarow.regions.adapters.menu.buttons.Button;
import dev.aarow.regions.utility.data.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class CloseMenuButton extends Button {

    @Override
    public ItemStack getItem(Player player) {
        ItemBuilder item = new ItemBuilder(Material.REDSTONE);
        item.setName("&c&lClose Menu");

        return item.build();
    }

    @Override
    public void onClick(Player player) {
        player.closeInventory();
    }

    @Override
    public void onClickType(ClickType clickType) {

    }
}