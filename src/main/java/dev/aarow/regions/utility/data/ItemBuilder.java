package dev.aarow.regions.utility.data;

import dev.aarow.regions.utility.chat.CC;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    private ItemStack itemStack;

    public ItemBuilder(Material material){
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder setName(String name){
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(CC.translate(name));

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addLoreLine(String line){
        ItemMeta itemMeta = itemStack.getItemMeta();

        List<String> newLore = new ArrayList<>();

        if(itemMeta.hasLore()) newLore = new ArrayList<>(itemMeta.getLore());

        newLore.add(CC.translate(line));

        itemMeta.setLore(newLore);

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStack build(){
        return itemStack;
    }
}
