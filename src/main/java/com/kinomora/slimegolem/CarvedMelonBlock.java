package com.kinomora.slimegolem;

import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class CarvedMelonBlock extends HorizontalBlock {
    //public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public CarvedMelonBlock() {
        super(Properties.create(Material.GOURD, MaterialColor.LIME).hardnessAndResistance(1.0F).sound(SoundType.WOOD));
        this.setDefaultState(this.stateContainer.getBaseState());
    }/*


    protected CarvedMelonBlock(Block.Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }*/
}
