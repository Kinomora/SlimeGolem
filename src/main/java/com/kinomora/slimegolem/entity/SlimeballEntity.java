package com.kinomora.slimegolem.entity;

import com.kinomora.slimegolem.RegistryHandler;
import com.kinomora.slimegolem.config.ModConfig;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;

public class SlimeballEntity extends ThrowableItemProjectile{
    public SlimeballEntity(EntityType<? extends SlimeballEntity> entityType, Level world) {
        super(entityType, world);
    }

    public SlimeballEntity(Level worldIn, LivingEntity throwerIn) {
        super(RegistryHandler.Entities.SLIMEBALL_ENTITY.get(), throwerIn, worldIn);
    }

    public SlimeballEntity(Level worldIn, double x, double y, double z) {
        super(RegistryHandler.Entities.SLIMEBALL_ENTITY.get(), x, y, z, worldIn);
    }

    protected Item getDefaultItem() {
        return Items.SLIME_BALL;
    }

    @OnlyIn(Dist.CLIENT)
    private ParticleOptions makeParticle() {
        ItemStack itemstack = this.getItemRaw();
        return (ParticleOptions) (itemstack.isEmpty() ? ParticleTypes.ITEM_SLIME : new ItemParticleOption(ParticleTypes.ITEM, itemstack));
    }

    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            ParticleOptions iparticledata = this.makeParticle();

            for (int i = 0; i < 8; ++i) {
                this.level.addParticle(iparticledata, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onHit(HitResult result) {
        if (result.getType() == HitResult.Type.ENTITY) {
            Entity entity = ((EntityHitResult) result).getEntity();

            //Set damage based on if the golem is rocky or not
            float damage = 0;
            Entity shooter = this.getOwner();
            if(shooter instanceof SlimeGolemEntity && ((SlimeGolemEntity) shooter).isRocky() && ModConfig.get().enableRockyGolem.get()){
                damage = 3;
            }

            //Attack hostile mobs
            entity.hurt(DamageSource.thrown(this, shooter), damage);

            //apply Slowness potion effect
            if(entity instanceof LivingEntity){
                int j = 20 + ((LivingEntity) entity).getRandom().nextInt(10 * 3);
                ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, j, 3));
            }
        }

        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }

    }

    @Nonnull
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}