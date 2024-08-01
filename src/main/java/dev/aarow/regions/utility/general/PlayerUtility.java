package dev.aarow.regions.utility.general;

import dev.aarow.regions.utility.chat.CC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerUtility {

    public static boolean checkPermission(CommandSender commandSender, String name){
        if(!commandSender.hasPermission(name)){
            commandSender.sendMessage(CC.translate("&cNo permission."));
            return false;
        }

        return true;
    }

    public static void giveItem(Player player, ItemStack itemStack){
        if(player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
            return;
        }

        player.getInventory().addItem(itemStack);
    }
}
