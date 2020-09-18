package com.kinomora.slimegolem;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SlimeGolem.ID)
public class EventHandler {

    @SubscribeEvent
    public static void summonSlimeGolem(BlockEvent.EntityPlaceEvent event) {

        //Change melon to carved melon later
        if (event.getPlacedBlock().getBlock() == Blocks.MELON) {
            BlockPos pos = event.getPos();

            //Check if both blocks below are slime block
            if (event.getWorld().getBlockState(pos.down()).getBlock() == Blocks.SLIME_BLOCK) {
                if (event.getWorld().getBlockState(pos.down(2)).getBlock() == Blocks.SLIME_BLOCK) {

                    //cancel the (head) block place event
                    for (int i = 0; i < 3; i++) {
                        event.getWorld().setBlockState(pos.down(i),Blocks.AIR.getDefaultState(), 3);
                    }

                    //summon the entity
                    SlimeGolemEntity entity = RegistryHandler.SLIME_GOLEM.create(event.getWorld().getWorld());
                    entity.setLocationAndAngles(pos.getX()+0.5, pos.getY() - 1.5, pos.getZ()+0.5, 0f, 0f);
                    event.getWorld().addEntity(entity);
                }
            }
        }
    }
}
