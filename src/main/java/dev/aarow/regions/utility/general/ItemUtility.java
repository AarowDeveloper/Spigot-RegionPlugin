package dev.aarow.regions.utility.general;

import dev.aarow.regions.utility.chat.CC;
import dev.aarow.regions.utility.data.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemUtility {

    public static ItemStack getWandItem(){
        return new ItemBuilder(Material.STICK)
                .setName("&3&lRegion Wand")
                .addLoreLine(" &7&l• &bLeft-Click the block to set the first corner.")
                .addLoreLine(" &7&l• &bRight-Click the block to set the second corner.")
                .build();
    }
}
