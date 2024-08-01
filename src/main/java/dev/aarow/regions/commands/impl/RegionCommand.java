package dev.aarow.regions.commands.impl;

import dev.aarow.regions.commands.BaseCommand;
import dev.aarow.regions.commands.CommandInfo;
import dev.aarow.regions.data.player.Profile;
import dev.aarow.regions.data.player.ProfileWandData;
import dev.aarow.regions.data.region.Region;
import dev.aarow.regions.data.region.flag.RegionFlagState;
import dev.aarow.regions.data.region.flag.RegionFlag;
import dev.aarow.regions.managers.impl.RegionManager;
import dev.aarow.regions.menus.RegionMenu;
import dev.aarow.regions.menus.RegionsMenu;
import dev.aarow.regions.utility.chat.CC;
import dev.aarow.regions.utility.general.ItemUtility;
import dev.aarow.regions.utility.general.PlayerUtility;
import dev.aarow.regions.utility.general.StringUtility;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@CommandInfo(name = "region", playerOnly = true)
public class RegionCommand extends BaseCommand {

    private RegionManager regionManager = regions.getRegionManager();

    @Override
    public void execute(Player player, String[] args) {
        Profile profile = regions.getProfileManager().get(player);

        if(args.length == 4) {
            if (args[0].equalsIgnoreCase("flag")) {
                if(!PlayerUtility.checkPermission(player, "region.flag")) return;

                Region region = regionManager.getByName(args[1]);

                if (region == null) {
                    player.sendMessage(CC.translate("&7[&3&lRegions&7] &cThat region doesn't exist."));
                    return;
                }

                RegionFlag regionFlag = regions.getRegionManager().getFlagByName(StringUtility.getNiceEnumString(args[2]));
                if (regionFlag == null) {
                    player.sendMessage(CC.translate("&7[&3&lRegions&7] &cThat flag doesn't exist."));
                    return;
                }

                RegionFlagState regionFlagState = regions.getRegionManager().getFlagStateByName(args[3]);
                if (regionFlagState == null) {
                    player.sendMessage(CC.translate("&7[&3&lRegions&7] &cThat flag condition doesn't exist."));
                    return;
                }

                region.getRegionFlags().put(regionFlag, regionFlagState);
                region.save();
                player.sendMessage(CC.translate("&7[&3&lRegions&7] &aYou successfully set the " + regionFlag.getName() + " to " + StringUtility.getNiceEnumString(regionFlagState.name()) + " for the " + region.getName() + " region."));
                return;
            }
        }
        if(args.length == 3){
            if (args[0].equalsIgnoreCase("add")) {
                if(!PlayerUtility.checkPermission(player, "region.add")) return;
                Region region = regionManager.getByName(args[1]);

                if(region == null){
                    player.sendMessage(CC.translate("&7[&3&lRegions&7] &cThat region doesn't exist."));
                    return;
                }

                Player target = Bukkit.getPlayer(args[2]);

                if(target == null){
                    player.sendMessage(CC.translate("&7[&3&lRegions&7] &cPlayer is offline."));
                    return;
                }
                if(region.getWhitelisted().contains(target.getUniqueId())){
                    player.sendMessage(CC.translate("&7[&3&lRegions&7] &cThat player is already whitelisted."));
                    return;
                }

                region.getWhitelisted().add(target.getUniqueId());
                region.save();

                player.sendMessage(CC.translate("&7[&3&lRegions&7] &aYou successfully added " + target.getName() + " to the " + region.getName() + " region's whitelist."));
                return;
            }
            if (args[0].equalsIgnoreCase("remove")) {
                if(!PlayerUtility.checkPermission(player, "region.remove")) return;

                Region region = regionManager.getByName(args[1]);

                if(region == null){
                    player.sendMessage(CC.translate("&7[&3&lRegions&7] &cThat region doesn't exist."));
                    return;
                }

                Player target = Bukkit.getPlayer(args[2]);

                if(target == null){
                    player.sendMessage(CC.translate("&7[&3&lRegions&7] &cPlayer is offline."));
                    return;
                }
                if(!region.getWhitelisted().contains(target.getUniqueId())){
                    player.sendMessage(CC.translate("&7[&3&lRegions&7] &cThat player isn't whitelisted."));
                    return;
                }

                region.getWhitelisted().remove(target.getUniqueId());
                region.save();

                player.sendMessage(CC.translate("&7[&3&lRegions&7] &cYou successfully removed " + target.getName() + " from the " + region.getName() + " region's whitelist."));
                return;
            }
        }
        if(args.length == 2){
            if(args[0].equalsIgnoreCase("create")){
                if(!PlayerUtility.checkPermission(player, "region.create")) return;

                String name = args[1];

                if(regionManager.getByName(name) != null){
                    player.sendMessage(CC.translate("&7[&3&lRegions&7] &cThat region already exists."));
                    return;
                }

                ProfileWandData profileWandData = profile.getProfileWandData();

                if(!profileWandData.isNewRegionValid()){
                    player.sendMessage(CC.translate("&7[&3&lRegions&7] &cYou need to set both corners and make sure that they are less than 200 between each other."));
                    return;
                }

                Region region = new Region(name, profileWandData.getFirstCornerLocation(), profileWandData.getSecondCornerLocation(), regionManager.generateRegionFlags(), System.currentTimeMillis());

                regionManager.addRegion(region);

                player.sendMessage(CC.translate("&7[&3&lRegions&7] &aYou successfully created the " + name + " region."));
                return;
            }
            if(args[0].equalsIgnoreCase("whitelist")){
                if(!PlayerUtility.checkPermission(player, "region.whitelist")) return;

                Region region = regionManager.getByName(args[1]);

                if(region == null){
                    player.sendMessage(CC.translate("&7[&3&lRegions&7] &cThat region doesn't exist."));
                    return;
                }

                if(region.getWhitelisted().isEmpty()){
                    player.sendMessage(CC.translate("&cThere is no whitelisted players currently."));
                }else{
                    player.sendMessage(CC.translate("&7&m----------------------------------"));
                    player.sendMessage(CC.translate("&3&l" + region.getName() + " Region's Whitelist"));

                    region.getWhitelisted().forEach(uuid -> {
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);

                        player.sendMessage(CC.translate(" &7&l• &b" + offlinePlayer.getName()));
                    });
                    player.sendMessage(CC.translate("&7&m----------------------------------"));
                }
                return;
            }
        }
        if(args.length == 1){
            if(args[0].equalsIgnoreCase("wand")){
                if(!PlayerUtility.checkPermission(player, "region.create")) return;
                PlayerUtility.giveItem(player, ItemUtility.getWandItem());

                player.sendMessage(CC.translate("&7[&3&lRegions&7] &bUse right-click and left-click to set the corners."));
                return;
            }

            Region region = regionManager.getByName(args[0]);

            if(region == null){
                player.sendMessage(CC.translate("&7[&3&lRegions&7] &cThat region doesn't exist."));
                return;
            }

            new RegionMenu(region).open(player);
            return;
        }
        if(args.length == 0){
            if(!PlayerUtility.checkPermission(player, "region.menu")) return;

            new RegionsMenu(player).open(0);
            return;
        }

        player.sendMessage(CC.translate("&7[&7&m----------------&7] &3&lREGION MANAGER &7[&7&m----------------&7]"));
        player.sendMessage(CC.translate("&b/region &7- &fOpens the regions menu."));
        player.sendMessage(CC.translate("&b/region create <name> &7- &fCreates a new region."));
        player.sendMessage(CC.translate("&b/region wand &7- &fGives the user a stick with a custom name to select locations to create a region."));
        player.sendMessage(CC.translate("&b/region add <name> <username> &7- &fWhitelist a user to a region."));
        player.sendMessage(CC.translate("&b/region remove <name> <username> &7- &fRemoves a user from the region whitelist."));
        player.sendMessage(CC.translate("&b/region whitelist <name> &7- &fLists the users in the region whitelist."));
        player.sendMessage(CC.translate("&b/region <name> &7- &fOpens the region menu."));
        player.sendMessage(CC.translate("&b/region flag <name> <flag> <state> &7- &fEdit a region flag."));
        player.sendMessage(CC.translate("&7[&7&m----------------&7] &3&lREGION MANAGER &7[&7&m----------------&7]"));

    }
}
