package com.kinomora.slimegolem.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public abstract class RangedSlimeGolemEntity extends BaseSlimeGolemEntity implements IRangedAttackMob {
    protected RangedSlimeGolemEntity(EntityType<? extends GolemEntity> type, World worldIn) {
        super(type, worldIn);
    }


    @Override
    public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
        SlimeballEntity SlimeballEntity = new SlimeballEntity(this.world, this, this::onImpact);
        double d0 = target.getPosYEye() - (double) 1.1F;
        double d1 = target.getPosX() - this.getPosX();
        double d2 = d0 - SlimeballEntity.getPosY();
        double d3 = target.getPosZ() - this.getPosZ();
        float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
        SlimeballEntity.shoot(d1, d2 + (double) f, d3, 1.6F, 12.0F);
        this.playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.world.addEntity(SlimeballEntity);
    }

    public abstract void onImpact(World world, SlimeballEntity projectile, RayTraceResult result);
}
