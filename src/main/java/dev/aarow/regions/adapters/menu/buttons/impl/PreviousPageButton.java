package dev.aarow.regions.adapters.menu.buttons.impl;

import dev.aarow.regions.adapters.menu.PaginatedMenu;
import dev.aarow.regions.adapters.menu.buttons.Button;
import dev.aarow.regions.utility.data.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class PreviousPageButton extends Button {

    private PaginatedMenu paginatedMenu;

    public PreviousPageButton(PaginatedMenu paginatedMenu){
        this.paginatedMenu = paginatedMenu;
    }

    @Override
    public ItemStack getItem(Player player) {
        ItemBuilder item = new ItemBuilder(Material.PAPER);
        item.setName("&b&lPrevious Page");

        return item.build();
    }

    @Override
    public void onClick(Player player) {
        if(paginatedMenu.getForPage(paginatedMenu.getPage()-1).isEmpty()) return;

        paginatedMenu.open(paginatedMenu.getPage()-1);
    }

    @Override
    public void onClickType(ClickType clickType) {

    }
}