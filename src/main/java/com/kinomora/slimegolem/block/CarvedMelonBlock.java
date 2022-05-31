package com.kinomora.slimegolem.block;

import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class CarvedMelonBlock extends HorizontalDirectionalBlock {
    //public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public CarvedMelonBlock() {
        super(Properties.of(Material.VEGETABLE, MaterialColor.COLOR_LIGHT_GREEN).strength(1.0F).sound(SoundType.WOOD));
        this.registerDefaultState(this.stateDefinition.any());
    }/*


    protected CarvedMelonBlock(Block.Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }*/
}
