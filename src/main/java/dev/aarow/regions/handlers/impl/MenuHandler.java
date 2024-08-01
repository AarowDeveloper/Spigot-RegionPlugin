package dev.aarow.regions.handlers.impl;

import dev.aarow.regions.adapters.menu.Menu;
import dev.aarow.regions.adapters.menu.PaginatedMenu;
import dev.aarow.regions.handlers.BaseHandler;

import dev.aarow.regions.utility.other.Task;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MenuHandler extends BaseHandler {

    @EventHandler
    public void onClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();

        if(!regions.getMenuManager().getMenuCache().containsKey(player.getUniqueId())) return;

        Menu menu = regions.getMenuManager().getMenuCache().get(player.getUniqueId());

        event.setCancelled(true);

        if(!menu.getButtons().containsKey((event.getSlot()+1))) return;

        menu.getButtons().get(event.getSlot()+1).onClick(player);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();

        if(!regions.getMenuManager().getMenuCache().containsKey(player.getUniqueId())) return;

        Menu menu = regions.getMenuManager().getMenuCache().get(player.getUniqueId());

        regions.getMenuManager().getMenuCache().remove(player.getUniqueId());

        menu.onClose();
    }

    @EventHandler
    public void onPaginatedClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();

        if(!regions.getMenuManager().getPaginatedMenuCache().containsKey(player.getUniqueId())) return;

        PaginatedMenu menu = regions.getMenuManager().getPaginatedMenuCache().get(player.getUniqueId());

        event.setCancelled(true);

        int slot = event.getSlot()+3;

        if(slot > 45){
            if(!menu.getAlways().containsKey(slot - 45)) return;

            if(event.getClick() == ClickType.LEFT){
                menu.getAlways().get(slot - 45).onClick(player);
            }else{
                menu.getAlways().get(slot - 45).onClickType(event.getClick());
            }
            return;
        }
        if(Arrays.stream(PaginatedMenu.paginatedSlots).noneMatch(paginatedSlot -> paginatedSlot == slot)) return;

        int itemIndex = (menu.getPage() * PaginatedMenu.paginatedSlots.length) + indexFromPaginatedSlot(slot);

        if(!menu.getButtons().containsKey(itemIndex)) return;

        if(event.getClick() == ClickType.LEFT){
            menu.getButtons().get(itemIndex).onClick(player);
        }else{
            menu.getButtons().get(itemIndex).onClickType(event.getClick());
        }
    }

    @EventHandler
    public void onPaginatedClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();

        if(!regions.getMenuManager().getPaginatedMenuCache().containsKey(player.getUniqueId())) return;

        PaginatedMenu menu = regions.getMenuManager().getPaginatedMenuCache().get(player.getUniqueId());

        Task.runASync(() -> {
            menu.onClose();

            regions.getMenuManager().getPaginatedMenuCache().remove(player.getUniqueId());
        });
    }

    private int indexFromPaginatedSlot(int paginatedSlot){
        List<Integer> paginatedSlots = Arrays.stream(PaginatedMenu.paginatedSlots).boxed().collect(Collectors.toList());

        return paginatedSlots.indexOf(paginatedSlot);
    }
}