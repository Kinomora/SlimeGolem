package com.kinomora.slimegolem.entities;

import com.kinomora.slimegolem.RegistryHandler;
import com.kinomora.slimegolem.config.ModConfig;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IForgeShearable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SlimeGolemEntity extends GolemEntity implements IRangedAttackMob, IForgeShearable {
    private static final DataParameter<Byte> MELON_EQUIPPED = EntityDataManager.createKey(SlimeGolemEntity.class, DataSerializers.BYTE);
    private static final DataParameter<Boolean> ROCKY = EntityDataManager.createKey(SlimeGolemEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IRONY = EntityDataManager.createKey(SlimeGolemEntity.class, DataSerializers.BOOLEAN);

    public SlimeGolemEntity(EntityType<? extends SlimeGolemEntity> type, World worldIn) {
        super(type, worldIn);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.25D, 20, 10.0F));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 1.0D, 1.0000001E-5F));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, MobEntity.class, 10, true, false, (p_213621_0_) -> {
            return p_213621_0_ instanceof IMob;
        }));
    }

    public static AttributeModifierMap.MutableAttribute attributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 4.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2F);
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(MELON_EQUIPPED, (byte) 16);
        this.dataManager.register(ROCKY, false);
        this.dataManager.register(IRONY, false);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("Melon", this.isMelonEquipped());
        compound.putBoolean("Rocky", this.isRocky());
        //compound.putBoolean("Irony", this.isIrony());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("Melon")) {
            this.setMelonEquipped(compound.getBoolean("Melon"));
        }
        if (compound.contains("Rocky")) {
            this.setRocky(compound.getBoolean("Rocky"));
        }

    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote) {
            int i, j, k;
            //Slime melts in the rain
            if (this.isInWaterRainOrBubbleColumn() && ModConfig.get().enableRainDamage.get()) {
                // Does the mob take damage from water or rain
                this.attackEntityFrom(DamageSource.DROWN, 1.0F);
            }

            //Mob griefing = false; Exits the method before executing the slime layers code
            if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this)) {
                return;
            }

            //alternateLayers = true; Slime layers can only be laid in slime chunks or swamps
            if (ModConfig.get().alternateLayers.get()) {
                if(!isSlimeSpawnable()){
                    return;
                }
            }

            //lay slime everywhere it walks, can change this to only slime chunks if wanted
            BlockState blockstate = RegistryHandler.SLIME_LAYER.getDefaultState();

            for (int l = 0; l < 4; ++l) {
                i = MathHelper.floor(this.getPosX() + (double) ((float) (l % 2 * 2 - 1) * 0.25F));
                j = MathHelper.floor(this.getPosY());
                k = MathHelper.floor(this.getPosZ() + (double) ((float) (l / 2 % 2 * 2 - 1) * 0.25F));
                BlockPos blockpos = new BlockPos(i, j, k);
                if (this.world.isAirBlock(blockpos) && blockstate.isValidPosition(this.world, blockpos)) {
                    this.world.setBlockState(blockpos, blockstate);
                }
            }
        }
    }

    /**
     * Attack the specified entity using a ranged attack.
     */
    public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
        SlimeballEntity SlimeballEntity = new SlimeballEntity(this.world, this, (world,projectile, result) -> {
            if (result.getType() == RayTraceResult.Type.ENTITY) {
                Entity t = ((EntityRayTraceResult) result).getEntity();

                //Set damage based on if the golem is rocky or not
                float damage = 0;
                if (ModConfig.get().enableRockyGolem.get()) {
                    damage = 3;
                }

                //Attack hostile mobs
                t.attackEntityFrom(DamageSource.causeThrownDamage(projectile, this), damage);

                //apply Slowness potion effect
                if (t instanceof LivingEntity) {
                    int j = 20 + ((LivingEntity) t).getRNG().nextInt(10 * 3);
                    ((LivingEntity) t).addPotionEffect(new EffectInstance(Effects.SLOWNESS, j, 3));
                }
            }
        });
        double d0 = target.getPosYEye() - (double) 1.1F;
        double d1 = target.getPosX() - this.getPosX();
        double d2 = d0 - SlimeballEntity.getPosY();
        double d3 = target.getPosZ() - this.getPosZ();
        float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
        SlimeballEntity.shoot(d1, d2 + (double) f, d3, 1.6F, 12.0F);
        this.playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.world.addEntity(SlimeballEntity);
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 1.7F;
    }

    public boolean isMelonEquipped() {
        return (this.dataManager.get(MELON_EQUIPPED) & 16) != 0;
    }

    public void setMelonEquipped(boolean melonEquipped) {
        byte b0 = this.dataManager.get(MELON_EQUIPPED);
        if (melonEquipped) {
            this.dataManager.set(MELON_EQUIPPED, (byte) (b0 | 16));
        } else {
            this.dataManager.set(MELON_EQUIPPED, (byte) (b0 & -17));
        }

    }

    public boolean isRocky() {
        return this.dataManager.get(ROCKY);
    }

    public void setRocky(boolean rocky) {
        this.dataManager.set(ROCKY, rocky);
    }

    /*public boolean isIrony() {
        return this.dataManager.get(IRONY);
    }

    public void setIrony(boolean irony) {
        this.dataManager.set(IRONY, irony);
    }*/

    @Nullable
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SLIME_SQUISH;
    }

    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_SLIME_HURT;
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SLIME_DEATH;
    }

    @Override
    public boolean isShearable(ItemStack item, World world, BlockPos pos) {
        return this.isMelonEquipped();
    }

    @Override
    public List<ItemStack> onSheared(PlayerEntity player, ItemStack item, World world, BlockPos pos, int fortune) {
        List<ItemStack> drops = new ArrayList<>();
        ItemStack melons = new ItemStack(Items.MELON_SLICE, 3 + world.getRandom().nextInt(5));
        drops.add(melons);

        this.setMelonEquipped(false);
        return drops;
    }

    //This lets you add cobblestone to a slime golem to make it rocky
    @Override
    public ActionResultType applyPlayerInteraction(PlayerEntity player, Vector3d vec, Hand hand) {
        //Setting Rocky if it's not already rocky or irony
        if (!isRocky() && /*!isIrony() &&*/ player.getHeldItem(hand).getItem() == Items.COBBLESTONE && ModConfig.get().enableRockyGolem.get()) {
            setRocky(true);
            if (!player.isCreative()) {
                player.getHeldItem(hand).shrink(1);
            }
            return ActionResultType.CONSUME;
        /*}
        //Setting Irony if it's not already irony or rocky
        else if (!isRocky() && !isIrony() && player.getHeldItem(hand).getItem() == Items.IRON_INGOT && ModConfig.get().enableIronGolem.get()) {
            setIrony(true);
            player.getHeldItem(hand).shrink(1);
            return ActionResultType.CONSUME;*/
        } else {
            return ActionResultType.FAIL;
        }
    }

    private static boolean isSlimeChunk(World world, double x, double z) {
        return isSlimeChunk(world, new BlockPos(x, 0, z));
    }

    public static boolean isSlimeChunk(World world, BlockPos pos) {
        ChunkPos chunkpos = new ChunkPos(pos);
        return SharedSeedRandom.seedSlimeChunk(chunkpos.x, chunkpos.z, ((ServerWorld) world).getSeed(), 987234911L).nextInt(10) == 0;
    }

    private boolean isSlimeSpawnable() {
        if (this.world.getBiome(this.getPosition()).getRegistryName().toString().equals("minecraft:swamp") || this.world.getBiome(this.getPosition()).getRegistryName().toString().equals("minecraft:swamp_hills")) {
            return true;
        }

        if (isSlimeChunk(this.world, this.getPosX(), this.getPosZ())) {
            return true;
        }
        return false;
    }
}