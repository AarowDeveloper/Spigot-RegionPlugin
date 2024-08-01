package dev.aarow.regions.utility.general;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class BukkitUtility {

    public static boolean isInteractable(Block block)
    {
        Material type = block.getType();
        boolean interactable = type.isInteractable();
        if (!interactable)
            return false;

        switch (type)
        {
            case ACACIA_STAIRS:
            case ANDESITE_STAIRS:
            case BIRCH_STAIRS:
            case BLACKSTONE_STAIRS:
            case BRICK_STAIRS:
            case COBBLESTONE_STAIRS:
            case CRIMSON_STAIRS:
            case DARK_OAK_STAIRS:
            case DARK_PRISMARINE_STAIRS:
            case DIORITE_STAIRS:
            case END_STONE_BRICK_STAIRS:
            case GRANITE_STAIRS:
            case JUNGLE_STAIRS:
            case MOSSY_COBBLESTONE_STAIRS:
            case MOSSY_STONE_BRICK_STAIRS:
            case NETHER_BRICK_STAIRS:
            case OAK_STAIRS:
            case POLISHED_ANDESITE_STAIRS:
            case POLISHED_BLACKSTONE_BRICK_STAIRS:
            case POLISHED_BLACKSTONE_STAIRS:
            case POLISHED_DIORITE_STAIRS:
            case POLISHED_GRANITE_STAIRS:
            case PRISMARINE_BRICK_STAIRS:
            case PRISMARINE_STAIRS:
            case PURPUR_STAIRS:
            case QUARTZ_STAIRS:
            case RED_NETHER_BRICK_STAIRS:
            case RED_SANDSTONE_STAIRS:
            case SANDSTONE_STAIRS:
            case SMOOTH_QUARTZ_STAIRS:
            case SMOOTH_RED_SANDSTONE_STAIRS:
            case SMOOTH_SANDSTONE_STAIRS:
            case SPRUCE_STAIRS:
            case STONE_BRICK_STAIRS:
            case STONE_STAIRS:
            case WARPED_STAIRS:

            case ACACIA_FENCE:
            case BIRCH_FENCE:
            case CRIMSON_FENCE:
            case DARK_OAK_FENCE:
            case JUNGLE_FENCE:
            case MOVING_PISTON:
            case NETHER_BRICK_FENCE:
            case OAK_FENCE:
            case PUMPKIN:
            case REDSTONE_ORE:
            case REDSTONE_WIRE:
            case SPRUCE_FENCE:
            case WARPED_FENCE:
                return false;
            default:
                return true;
        }

    }
}
