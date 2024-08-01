package dev.aarow.regions.adapters.menu.buttons;

import dev.aarow.regions.utility.data.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public abstract class Button {

    public abstract ItemStack getItem(Player player);

    public abstract void onClick(Player player);

    public abstract void onClickType(ClickType clickType);

    public static Button PLACEHOLDER = new Button() {
        @Override
        public ItemStack getItem(Player player) {
            return new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName("").build();
        }

        @Override
        public void onClick(Player player) {}

        @Override
        public void onClickType(ClickType clickType){}
    };

}