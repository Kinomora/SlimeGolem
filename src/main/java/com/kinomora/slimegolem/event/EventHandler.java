package com.kinomora.slimegolem.event;

import com.kinomora.slimegolem.RegistryHandler;
import com.kinomora.slimegolem.SlimeGolems;
import com.kinomora.slimegolem.entity.SlimeGolemEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SlimeGolems.ID)
public class EventHandler {

    @SubscribeEvent
    public static void summonSlimeGolem(BlockEvent.EntityPlaceEvent event) {

        //Change melon to carved melon later
        if (event.getPlacedBlock().getBlock() == Blocks.MELON) {
            BlockPos pos = event.getPos();

            //Check if both blocks below are slime block
            if (event.getWorld().getBlockState(pos.below()).getBlock() == Blocks.SLIME_BLOCK) {
                if (event.getWorld().getBlockState(pos.below(2)).getBlock() == Blocks.SLIME_BLOCK) {

                    //cancel the (head) block place event
                    for (int i = 0; i < 3; i++) {
                        event.getWorld().setBlock(pos.below(i),Blocks.AIR.defaultBlockState(), 3);
                    }

                    //summon the entity
                    SlimeGolemEntity entity = RegistryHandler.SLIME_GOLEM.create((Level)event.getWorld());
                    entity.moveTo(pos.getX()+0.5, pos.getY() - 1.5, pos.getZ()+0.5, 0f, 0f);
                    event.getWorld().addFreshEntity(entity);
                }
            }
        }
    }
}
