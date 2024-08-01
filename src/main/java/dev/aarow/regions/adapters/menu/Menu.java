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

import java.util.Map;

public abstract class Menu {

    public abstract int getSize();
    public abstract String getName();
    public abstract Map<Integer, Button> getButtons();
    public abstract void onClose();

    public Regions regions = Regions.getInstance();
    public RegionsPlugin plugin = RegionsPlugin.getInstance();

    private Player player;

    private boolean autoRefresh;

    private Inventory inventory;

    public void open(Player player) {
        this.player = player;

        Task.runASync(() -> {
            player.closeInventory();

            Task.runASync(() -> {
                inventory = Bukkit.createInventory(player, getSize(), CC.translate(getName()));

                getButtons().keySet().forEach(slot -> {
                    Button button = getButtons().get(slot);

                    inventory.setItem(slot - 1, button.getItem(player));
                });

                for (int i = 0; i < getSize(); i++) {
                    if (getButtons().containsKey(i + 1)) continue;

                    inventory.setItem(i, Button.PLACEHOLDER.getItem(player));
                }

                regions.getMenuManager().getMenuCache().put(player.getUniqueId(), this);

                player.openInventory(inventory);

                if (autoRefresh) {
                    new BukkitRunnable() {
                        public void run() {
                            if (!regions.getMenuManager().getMenuCache().containsKey(player.getUniqueId())) {
                                this.cancel();
                                return;
                            }
                            if (!regions.getMenuManager().getMenuCache().get(player.getUniqueId()).getName().equals(getName())) {
                                this.cancel();
                                return;
                            }

                            refresh();
                        }
                    }.runTaskTimer(plugin, 0L, 2L);
                }
            });
        });


    }

    private void refresh(){
        Task.runASync(() -> {
            inventory.clear();

            getButtons().keySet().forEach(slot -> {
                Button button = getButtons().get(slot);

                inventory.setItem(slot - 1, button.getItem(player));
            });

            for (int i = 0; i < getSize(); i++) {
                if (getButtons().containsKey(i + 1)) continue;

                inventory.setItem(i, Button.PLACEHOLDER.getItem(player));
            }
        });
    }

    public void setAutoRefresh(boolean autoRefresh) {
        this.autoRefresh = autoRefresh;
    }
}