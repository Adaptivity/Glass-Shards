package ljfa.glassshards.handlers;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BreakSpeedHandler {

    @SubscribeEvent
    public void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        Block block = event.state.getBlock();
        if(block.getMaterial() == Material.glass && block.getHarvestTool(event.state) == null) {
            ItemStack stack = event.entityPlayer.getCurrentEquippedItem();
            if(stack == null)
                return;
            Item tool = stack.getItem();
            if(tool instanceof ItemTool && tool.getHarvestLevel(stack, "pickaxe") >= 0) {
                float factor = 0.5f * ((ItemTool)tool).getToolMaterial().getEfficiencyOnProperMaterial();
                if(factor > 1.0f)
                    event.newSpeed = factor * event.originalSpeed;
            }
        }
    }
}