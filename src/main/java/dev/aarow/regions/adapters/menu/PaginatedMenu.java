package dev.aarow.regions.adapters.menu;

import dev.aarow.regions.Regions;
import dev.aarow.regions.adapters.menu.buttons.Button;
import dev.aarow.regions.plugin.RegionsPlugin;
import dev.aarow.regions.utility.chat.CC;
import dev.aarow.regions.utility.other.Task;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public abstract class PaginatedMenu {

    public Regions regions = Regions.getInstance();
    public RegionsPlugin plugin = RegionsPlugin.getInstance();


    private boolean autoRefresh = false;

    public abstract String getName();
    public abstract Map<Integer, Button> getButtons();
    public abstract void onClose();
    public abstract Map<Integer, Button> getAlways();

    private int page = 0;

    public Player player;

    private Inventory inventory;

    public static int[] paginatedSlots = new int[]{12, 13, 14, 15, 16, 17,18,21, 22, 23, 24, 25, 26,27, 30, 31, 32, 33, 34, 35, 36, 39, 40, 41, 42, 43, 44, 45};

    public PaginatedMenu(Player player){
        this.player = player;
    }

    public void open(int newPage){
        this.page = newPage;

        this.inventory = Bukkit.createInventory(player, 54, CC.translate(getName()));

        Map<Integer, Button> pageItems = getForPage(newPage);

        this.setBorders();

        pageItems.keySet().forEach(index -> inventory.addItem(pageItems.get(index).getItem(player)));

        getAlways().keySet().forEach(index -> {
            int slot = index + 44;

            inventory.setItem(slot, getAlways().get(index).getItem(player));
        });


        Task.runASync(() -> {
            player.openInventory(inventory);

            regions.getMenuManager().getPaginatedMenuCache().put(player.getUniqueId(), this);

            if(autoRefresh) {
                new BukkitRunnable(){
                    public void run(){
                        if(!regions.getMenuManager().getPaginatedMenuCache().containsKey(player.getUniqueId())){
                            this.cancel();
                            return;
                        }
                        if(!regions.getMenuManager().getPaginatedMenuCache().get(player.getUniqueId()).getName().equals(getName())){
                            this.cancel();
                            return;
                        }

                        refresh();
                    }
                }.runTaskTimer(plugin, 0L, 2L);
            }
        });
    }

    public void refresh(){
        Task.runASync(() -> {
            inventory.clear();

            this.setBorders();

            Map<Integer, Button> pageItems = getForPage(getPage());

            if(pageItems.isEmpty()){
                if(page > 0) this.open(page-1);
            }

            pageItems.keySet().forEach(index -> {
                inventory.addItem(pageItems.get(index).getItem(player));
            });

            getAlways().keySet().forEach(index -> {
                int slot = index + 44;

                inventory.setItem(slot, getAlways().get(index).getItem(player));
            });

            player.updateInventory();
        });

    }

    public Map<Integer, Button> getForPage(int page){
        Map<Integer, Button> forPage = new HashMap<>();
        Map<Integer, Button> buttons = getButtons();

        int start = (page * paginatedSlots.length) + 1;
        int end = (page * paginatedSlots.length) + paginatedSlots.length;
        for(int i = start; i <= end; i++){
            if(!buttons.containsKey(i)) break;

            forPage.put(i, buttons.get(i));
        }

        return forPage;
    }

    private void setBorders(){
        for(int x = 0; x < 54; x++){
            if(x < 9 || x > 44){
                inventory.setItem(x, Button.PLACEHOLDER.getItem(player));
                continue;
            }
            if(x % 9 == 0){
                inventory.setItem(x, Button.PLACEHOLDER.getItem(player));
                inventory.setItem(x-1, Button.PLACEHOLDER.getItem(player));
                continue;
            }
        }
        inventory.setItem(44, Button.PLACEHOLDER.getItem(player));
    }

    public int getPage(){
        return page;
    }

    public void setPage(int page){
        this.page = page;
    }

    public void setAutoRefresh(boolean autoRefresh) {
        this.autoRefresh = autoRefresh;
    }
}