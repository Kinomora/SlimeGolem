package com.kinomora.slimegolem.block;

import com.kinomora.slimegolem.SlimeGolems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SlimeGolems.ID)
public class SlimeBlock extends HalfTransparentBlock {

    public SlimeBlock() {
        super(Properties.of(Material.DECORATION, MaterialColor.GRASS).friction(0.8f).sound(SoundType.SLIME_BLOCK).noOcclusion());
        this.registerDefaultState(this.stateDefinition.any());
    }

    /**
     * Block's chance to react to a living entity falling on it.
     */
    public void fallOn(Level worldIn, BlockState blockState, BlockPos pos, Entity entityIn, float fallDistance) {
        if (entityIn.isSuppressingBounce()) {
            super.fallOn(worldIn, blockState, pos, entityIn, fallDistance);
        }
        else {
            entityIn.causeFallDamage(fallDistance, 0.0F, DamageSource.FALL);
        }

    }

    /**
     * Called when an Entity lands on this Block. This method *must* update motionY because the entity will not do that
     * on its own
     */
    public void updateEntityAfterFallOn(BlockGetter worldIn, Entity entityIn) {
        if (entityIn.isSuppressingBounce()) {
            super.updateEntityAfterFallOn(worldIn, entityIn);
        } else {
            this.bounceEntity(entityIn);
        }

    }

    private void bounceEntity(Entity entity) {
        Vec3 vector3d = entity.getDeltaMovement();
        if (vector3d.y < 0.0D) {
            double d0 = entity instanceof LivingEntity ? 1.0D : 0.8D;
            entity.setDeltaMovement(vector3d.x, -vector3d.y * d0, vector3d.z);
        }

    }

    /**
     * Called when the given entity walks on this Block
     */
    public void stepOn(Level worldIn, BlockPos pos, BlockState blockState, Entity entityIn) {
        double d0 = Math.abs(entityIn.getDeltaMovement().y);
        if (d0 < 0.1D && !entityIn.isSteppingCarefully()) {
            double d1 = 0.4D + d0 * 0.2D;
            entityIn.setDeltaMovement(entityIn.getDeltaMovement().multiply(d1, 1.0D, d1));
        }

        super.stepOn(worldIn, pos, blockState, entityIn);
    }
}