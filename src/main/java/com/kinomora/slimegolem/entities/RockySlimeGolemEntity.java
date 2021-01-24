package com.kinomora.slimegolem.entities;

import com.kinomora.slimegolem.config.ModConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class RockySlimeGolemEntity extends RangedSlimeGolemEntity{
    protected RockySlimeGolemEntity(EntityType<? extends GolemEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public void onImpact(World world, SlimeballEntity projectile, RayTraceResult result) {
        if (result.getType() == RayTraceResult.Type.ENTITY) {
            Entity target = ((EntityRayTraceResult) result).getEntity();

            //Set damage based on if the golem is rocky or not
            float damage = 0;
            if (ModConfig.get().enableRockyGolem.get()) {
                damage = 3;
            }

            //Attack hostile mobs
            target.attackEntityFrom(DamageSource.causeThrownDamage(projectile, this), damage);

            //apply Slowness potion effect
            if (target instanceof LivingEntity) {
                int j = 20 + ((LivingEntity) target).getRNG().nextInt(10 * 3);
                ((LivingEntity) target).addPotionEffect(new EffectInstance(Effects.SLOWNESS, j, 3));
            }
        }
    }
}
