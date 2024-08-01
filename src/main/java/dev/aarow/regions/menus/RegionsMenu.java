package dev.aarow.regions.menus;

import dev.aarow.regions.adapters.menu.PaginatedMenu;
import dev.aarow.regions.adapters.menu.buttons.Button;
import dev.aarow.regions.adapters.menu.buttons.impl.CloseMenuButton;
import dev.aarow.regions.adapters.menu.buttons.impl.NextPageButton;
import dev.aarow.regions.adapters.menu.buttons.impl.PreviousPageButton;
import dev.aarow.regions.data.region.Region;
import dev.aarow.regions.data.region.flag.RegionFlagState;
import dev.aarow.regions.data.region.flag.RegionFlag;
import dev.aarow.regions.utility.data.ItemBuilder;
import dev.aarow.regions.utility.general.LocationUtility;
import dev.aarow.regions.utility.general.StringUtility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class RegionsMenu extends PaginatedMenu {

    {
        setAutoRefresh(true);
    }

    public RegionsMenu(Player player) {
        super(player);
    }

    @Override
    public String getName() {
        return "&8Regions";
    }

    @Override
    public Map<Integer, Button> getButtons() {
        Map<Integer, Button> buttons = new HashMap<>();

        int index = 1;

        for(Region region : regions.getRegionManager().getRegions()){
            buttons.put(index, new RegionButton(region));
            index++;
        }

        return buttons;
    }

    @Override
    public void onClose() {

    }

    @Override
    public Map<Integer, Button> getAlways() {
        Map<Integer, Button> buttons = new HashMap<>();

        buttons.put(1, new PreviousPageButton(this));
        buttons.put(5, new CloseMenuButton());
        buttons.put(9, new NextPageButton(this));

        return buttons;
    }

    private class RegionButton extends Button {

        private Region region;

        public RegionButton(Region region){
            this.region = region;
        }

        @Override
        public ItemStack getItem(Player player) {
            ItemBuilder itemBuilder = new ItemBuilder(Material.HOPPER);

            itemBuilder.setName("&3&l" + region.getName() + " Region");

            itemBuilder.addLoreLine("&7&m--------------------------------------------");
            itemBuilder.addLoreLine(" &7&l• &bWorld&7: &f" + region.getFirstCornerLocation().getWorld().getName());
            itemBuilder.addLoreLine(" &7&l• &bFirst Corner&7: &f" + LocationUtility.getNiceString(region.getFirstCornerLocation()));
            itemBuilder.addLoreLine(" &7&l• &bSecond Corner&7: &f" + LocationUtility.getNiceString(region.getSecondCornerLocation()));
            itemBuilder.addLoreLine(" &7&l• &bWhitelisted&7: &f" + region.getWhitelisted().size());
            itemBuilder.addLoreLine("");
            itemBuilder.addLoreLine("&3&lFlags");
            for(RegionFlag regionFlag : region.getRegionFlags().keySet()){
                RegionFlagState regionFlagState = region.getRegionFlags().get(regionFlag);

                itemBuilder.addLoreLine(" &7* &b" + regionFlag.getName() + "&7: &r" + regionFlagState.getChatColor() + StringUtility.getNiceEnumString(regionFlagState.name()));
            }

            itemBuilder.addLoreLine("");
            itemBuilder.addLoreLine(" &7&l• &bCreated&7: &f" + StringUtility.getNiceDate(region.getCreated()));
            itemBuilder.addLoreLine("");
            itemBuilder.addLoreLine("&7&oClick here to open the " + region.getName() + "'s management menu" + StringUtility.LOADING_DOTS);
            itemBuilder.addLoreLine("&7&m--------------------------------------------");

            return itemBuilder.build();
        }

        @Override
        public void onClick(Player player) {
            new RegionMenu(region).open(player);
        }

        @Override
        public void onClickType(ClickType clickType) {

        }
    }

}
