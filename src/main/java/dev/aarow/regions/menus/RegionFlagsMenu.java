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
import dev.aarow.regions.utility.general.StringUtility;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class RegionFlagsMenu extends PaginatedMenu {

    {
        setAutoRefresh(true);
    }

    private Region region;

    public RegionFlagsMenu(Player player, Region region){
        super(player);

        this.region = region;
    }

    @Override
    public String getName() {
        return "&8" + region.getName() + " Region -> Flags";
    }

    @Override
    public Map<Integer, Button> getButtons() {
        Map<Integer, Button> buttons = new HashMap<>();

        int index = 1;

        for(RegionFlag regionFlag : region.getRegionFlags().keySet()){
            buttons.put(index, new RegionFlagButton(regionFlag));
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

    private class RegionFlagButton extends Button {

        private RegionFlag regionFlag;

        public RegionFlagButton(RegionFlag regionFlag){
            this.regionFlag = regionFlag;
        }

        @Override
        public ItemStack getItem(Player player) {
            ItemBuilder itemBuilder = new ItemBuilder(regionFlag.getMenuMaterial());

            itemBuilder.setName("&3&l" + regionFlag.getName());

            RegionFlagState regionFlagState = region.getRegionFlags().get(regionFlag);

            itemBuilder.addLoreLine("&7&m--------------------------------------------");
            itemBuilder.addLoreLine("&b&lStatus");
            itemBuilder.addLoreLine(" &7&l• " + StringUtility.getChatColorStatus(regionFlagState == RegionFlagState.EVERYONE) + "Everyone");
            itemBuilder.addLoreLine(" &7&l• " + StringUtility.getChatColorStatus(regionFlagState == RegionFlagState.WHITELIST) + "Whitelist");
            itemBuilder.addLoreLine(" &7&l• " + StringUtility.getChatColorStatus(regionFlagState == RegionFlagState.NONE) + "None");
            itemBuilder.addLoreLine("");
            itemBuilder.addLoreLine("&7&oClick here to switch the region flag status" + StringUtility.LOADING_DOTS);
            itemBuilder.addLoreLine("&7&m--------------------------------------------");

            return itemBuilder.build();
        }

        @Override
        public void onClick(Player player) {
            switch(region.getRegionFlags().get(regionFlag)){
                case EVERYONE:
                    region.getRegionFlags().put(regionFlag, RegionFlagState.WHITELIST);
                    break;
                case WHITELIST:
                    region.getRegionFlags().put(regionFlag, RegionFlagState.NONE);
                    break;
                case NONE:
                    region.getRegionFlags().put(regionFlag, RegionFlagState.EVERYONE);
                    break;
            }

            region.save();
        }

        @Override
        public void onClickType(ClickType clickType) {
            if(clickType != ClickType.RIGHT) return;

            switch(region.getRegionFlags().get(regionFlag)){
                case NONE:
                    region.getRegionFlags().put(regionFlag, RegionFlagState.WHITELIST);
                    break;
                case WHITELIST:
                    region.getRegionFlags().put(regionFlag, RegionFlagState.EVERYONE);
                    break;
                case EVERYONE:
                    region.getRegionFlags().put(regionFlag, RegionFlagState.NONE);
                    break;
            }

            region.save();
        }
    }
}
