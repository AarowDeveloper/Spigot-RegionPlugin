package dev.aarow.regions.menus;

import dev.aarow.regions.adapters.menu.Menu;
import dev.aarow.regions.adapters.menu.buttons.Button;
import dev.aarow.regions.adapters.menu.buttons.impl.CloseMenuButton;
import dev.aarow.regions.data.player.Profile;
import dev.aarow.regions.data.player.ProfileChatRegionEdit;
import dev.aarow.regions.data.player.ProfileWandData;
import dev.aarow.regions.data.region.Region;
import dev.aarow.regions.data.region.flag.RegionFlagState;
import dev.aarow.regions.data.region.flag.RegionFlag;
import dev.aarow.regions.utility.chat.CC;
import dev.aarow.regions.utility.data.CoordinatePair;
import dev.aarow.regions.utility.data.ItemBuilder;
import dev.aarow.regions.utility.general.LocationUtility;
import dev.aarow.regions.utility.general.PlayerUtility;
import dev.aarow.regions.utility.general.StringUtility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class RegionMenu extends Menu {

    {
        setAutoRefresh(true);
    }

    private Region region;

    public RegionMenu(Region region){
        this.region = region;
    }

    @Override
    public int getSize() {
        return 45;
    }

    @Override
    public String getName() {
        return "&8" + region.getName() + " Region";
    }

    @Override
    public Map<Integer, Button> getButtons() {
        Map<Integer, Button> buttons = new HashMap<>();

        buttons.put(14, new RenameButton());

        buttons.put(20, new WhitelistAddButton());
        buttons.put(22, new WhitelistRemoveButton());
        buttons.put(24, new RedefineLocationButton());
        buttons.put(26, new RegionFlagsButton());

        buttons.put(41, new CloseMenuButton());

        return buttons;
    }

    @Override
    public void onClose() {

    }

    private class RenameButton extends Button {

        @Override
        public ItemStack getItem(Player player) {
            ItemBuilder itemBuilder = new ItemBuilder(Material.NAME_TAG);

            itemBuilder.setName("&3&lChange Name");
            itemBuilder.addLoreLine(" &7&l• &fClick here to edit " + region.getName() + " region's name" + StringUtility.LOADING_DOTS);

            return itemBuilder.build();
        }

        @Override
        public void onClick(Player player) {
            player.closeInventory();

            Profile profile = regions.getProfileManager().get(player);

            profile.setProfileChatRegionEdit(new ProfileChatRegionEdit(region, ProfileChatRegionEdit.Action.RENAME));

            player.sendMessage(CC.translate("&7[&3&lRegions&7] &bEnter the new region name in chat."));
        }

        @Override
        public void onClickType(ClickType clickType) {

        }
    }

    private class WhitelistAddButton extends Button {


        @Override
        public ItemStack getItem(Player player) {
            ItemBuilder itemBuilder = new ItemBuilder(Material.GREEN_TERRACOTTA);

            itemBuilder.setName("&a&lWhitelist Add");
            itemBuilder.addLoreLine(" &7&l• &fClick here to add a player to the " + region.getName() + " region's whitelist" + StringUtility.LOADING_DOTS);

            return itemBuilder.build();
        }

        @Override
        public void onClick(Player player) {
            if(!PlayerUtility.checkPermission(player, "region.add")) return;

            player.closeInventory();

            Profile profile = regions.getProfileManager().get(player);

            profile.setProfileChatRegionEdit(new ProfileChatRegionEdit(region, ProfileChatRegionEdit.Action.WHITELIST_ADD));

            player.sendMessage(CC.translate("&7[&3&lRegions&7] &bEnter the player's name in the chat."));
        }

        @Override
        public void onClickType(ClickType clickType) {

        }
    }

    private class WhitelistRemoveButton extends Button {

        @Override
        public ItemStack getItem(Player player) {
            ItemBuilder itemBuilder = new ItemBuilder(Material.RED_TERRACOTTA);

            itemBuilder.setName("&c&lWhitelist Remove");
            itemBuilder.addLoreLine(" &7&l• &fClick here to remove a player from the " + region.getName() + " region's whitelist" + StringUtility.LOADING_DOTS);

            return itemBuilder.build();
        }

        @Override
        public void onClick(Player player) {
            if(!PlayerUtility.checkPermission(player, "region.remove")) return;

            player.closeInventory();

            Profile profile = regions.getProfileManager().get(player);

            profile.setProfileChatRegionEdit(new ProfileChatRegionEdit(region, ProfileChatRegionEdit.Action.WHITELIST_REMOVE));

            player.sendMessage(CC.translate("&7[&3&lRegions&7] &bEnter the player's name in the chat."));
        }

        @Override
        public void onClickType(ClickType clickType) {

        }
    }

    private class RedefineLocationButton extends Button {


        @Override
        public ItemStack getItem(Player player) {
            ItemBuilder itemBuilder = new ItemBuilder(Material.PAPER);

            itemBuilder.setName("&3&lRedefine Location");
            itemBuilder.addLoreLine(" &7&l• &fGet yourself a wand by using /region wand, select the two locations you want");
            itemBuilder.addLoreLine(" &7&l• &fto be the corners of this region, open this menu again and click here.");

            return itemBuilder.build();
        }

        @Override
        public void onClick(Player player) {
            if(!PlayerUtility.checkPermission(player, "region.create")) return;

            player.closeInventory();

            Profile profile = regions.getProfileManager().get(player);
            ProfileWandData profileWandData = profile.getProfileWandData();

            if(!profileWandData.isChangeValid(region)){
                player.sendMessage(CC.translate("&7[&3&lRegions&7] &cYou need to set both corners and make sure that they are less than 200 between each other."));
                return;
            }

            for(CoordinatePair coordinatePair : LocationUtility.getBetweenLocations(region.getFirstCornerLocation(), region.getSecondCornerLocation()).stream().map(CoordinatePair::getFrom).toList()){
                regions.getRegionManager().getCoordinates().remove(coordinatePair);
            }

            region.setFirstCornerLocation(profileWandData.getFirstCornerLocation());
            region.setSecondCornerLocation(profileWandData.getSecondCornerLocation());

            region.registerCoordinates();

            player.sendMessage(CC.translate("&7[&3&lRegions&7] &aYou successfully changed the " + region.getName() + " region's corners."));
        }

        @Override
        public void onClickType(ClickType clickType) {

        }
    }

    private class RegionFlagsButton extends Button {

        @Override
        public ItemStack getItem(Player player) {
            ItemBuilder itemBuilder = new ItemBuilder(Material.LEVER);

            itemBuilder.setName("&3&lFlags");

            itemBuilder.addLoreLine("&7&m--------------------------------------------");

            for(RegionFlag regionFlag : region.getRegionFlags().keySet()){
                RegionFlagState regionFlagState = region.getRegionFlags().get(regionFlag);

                itemBuilder.addLoreLine(" &7&l• &b" + regionFlag.getName() + "&7: &r" + regionFlagState.getChatColor() + StringUtility.getNiceEnumString(regionFlagState.name()));
            }

            itemBuilder.addLoreLine("&7&m--------------------------------------------");

            return itemBuilder.build();
        }

        @Override
        public void onClick(Player player) {
            if(!PlayerUtility.checkPermission(player, "region.flag")) return;

            new RegionFlagsMenu(player, region).open(0);
        }

        @Override
        public void onClickType(ClickType clickType) {

        }
    }
}
