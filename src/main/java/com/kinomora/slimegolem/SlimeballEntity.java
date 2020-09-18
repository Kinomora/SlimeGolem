package com.kinomora.slimegolem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SlimeballEntity extends ProjectileItemEntity implements IRendersAsItem {
    public SlimeballEntity(EntityType<? extends SlimeballEntity> entityType, World world) {
        super(entityType, world);
    }

    public SlimeballEntity(World worldIn, LivingEntity throwerIn) {
        super(RegistryHandler.SLIMEBALL_ENTITY, throwerIn, worldIn);
    }

    public SlimeballEntity(World worldIn, double x, double y, double z) {
        super(RegistryHandler.SLIMEBALL_ENTITY, x, y, z, worldIn);
    }

    protected Item getDefaultItem() {
        return Items.SLIME_BALL;
    }

    @OnlyIn(Dist.CLIENT)
    private IParticleData makeParticle() {
        ItemStack itemstack = this.func_213882_k();
        return (IParticleData) (itemstack.isEmpty() ? ParticleTypes.ITEM_SLIME : new ItemParticleData(ParticleTypes.ITEM, itemstack));
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 3) {
            IParticleData iparticledata = this.makeParticle();

            for (int i = 0; i < 8; ++i) {
                this.world.addParticle(iparticledata, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onImpact(RayTraceResult result) {
        if (result.getType() == RayTraceResult.Type.ENTITY) {
            Entity entity = ((EntityRayTraceResult) result).getEntity();
            int i = entity instanceof SlimeEntity ? 3 : 0;
            entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float) i);
        }

        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte) 3);
            this.remove();
        }

    }
}