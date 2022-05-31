package com.kinomora.slimegolem.entity;

import com.kinomora.slimegolem.RegistryHandler;
import com.kinomora.slimegolem.config.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.IForgeShearable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SlimeGolemEntity extends AbstractGolem implements RangedAttackMob, IForgeShearable {
    private static final EntityDataAccessor<Byte> MELON_EQUIPPED = SynchedEntityData.defineId(SlimeGolemEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Boolean> ROCKY = SynchedEntityData.defineId(SlimeGolemEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IRONY = SynchedEntityData.defineId(SlimeGolemEntity.class, EntityDataSerializers.BOOLEAN);

    public SlimeGolemEntity(EntityType<? extends SlimeGolemEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.25D, 20, 10.0F));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D, 1.0000001E-5F));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Mob.class, 10, true, false, (p_213621_0_) -> {
            return p_213621_0_ instanceof Enemy;
        }));
    }

    public static AttributeSupplier.Builder attributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0D).add(Attributes.MOVEMENT_SPEED, 0.2F);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MELON_EQUIPPED, (byte) 16);
        this.entityData.define(ROCKY, false);
        this.entityData.define(IRONY, false);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Melon", this.isMelonEquipped());
        compound.putBoolean("Rocky", this.isRocky());
        //compound.putBoolean("Irony", this.isIrony());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
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
    public void aiStep() {
        super.aiStep();
        if (!this.level.isClientSide) {
            int i, j, k;
            //Slime melts in the rain
            if (this.isInWaterRainOrBubble() && ModConfig.get().enableRainDamage.get()) {
                // Does the mob take damage from water or rain
                this.hurt(DamageSource.DROWN, 1.0F);
            }

            //Mob griefing = false; Exits the method before executing the slime layers code
            if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this)) {
                return;
            }

            //alternateLayers = true; Slime layers can only be laid in slime chunks or swamps
            if (ModConfig.get().alternateLayers.get()) {
                if(!isSlimeSpawnable()){
                    return;
                }
            }

            //lay slime everywhere it walks, can change this to only slime chunks if wanted
            BlockState blockstate = RegistryHandler.SLIME_LAYER.defaultBlockState();

            for (int l = 0; l < 4; ++l) {
                i = Mth.floor(this.getX() + (double) ((float) (l % 2 * 2 - 1) * 0.25F));
                j = Mth.floor(this.getY());
                k = Mth.floor(this.getZ() + (double) ((float) (l / 2 % 2 * 2 - 1) * 0.25F));
                BlockPos blockpos = new BlockPos(i, j, k);
                if (this.level.isEmptyBlock(blockpos) && blockstate.canSurvive(this.level, blockpos)) {
                    this.level.setBlockAndUpdate(blockpos, blockstate);
                }
            }
        }
    }

    /**
     * Attack the specified entity using a ranged attack.
     */
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        SlimeballEntity SlimeballEntity = new SlimeballEntity(this.level, this);
        double d0 = target.getEyeY() - (double) 1.1F;
        double d1 = target.getX() - this.getX();
        double d2 = d0 - SlimeballEntity.getY();
        double d3 = target.getZ() - this.getZ();
        float f = Mth.sqrt((float)(d1 * d1 + d3 * d3)) * 0.2F;
        SlimeballEntity.shoot(d1, d2 + (double) f, d3, 1.6F, 12.0F);
        this.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level.addFreshEntity(SlimeballEntity);
    }

    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return 1.7F;
    }

    public boolean isMelonEquipped() {
        return (this.entityData.get(MELON_EQUIPPED) & 16) != 0;
    }

    public void setMelonEquipped(boolean melonEquipped) {
        byte b0 = this.entityData.get(MELON_EQUIPPED);
        if (melonEquipped) {
            this.entityData.set(MELON_EQUIPPED, (byte) (b0 | 16));
        } else {
            this.entityData.set(MELON_EQUIPPED, (byte) (b0 & -17));
        }

    }

    public boolean isRocky() {
        return this.entityData.get(ROCKY);
    }

    public void setRocky(boolean rocky) {
        this.entityData.set(ROCKY, rocky);
    }

    /*public boolean isIrony() {
        return this.dataManager.get(IRONY);
    }

    public void setIrony(boolean irony) {
        this.dataManager.set(IRONY, irony);
    }*/

    @Nullable
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SLIME_SQUISH;
    }

    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.SLIME_HURT;
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.SLIME_DEATH;
    }

    @Override
    public boolean isShearable(ItemStack item, Level world, BlockPos pos) {
        return this.isMelonEquipped();
    }

    @Override
    public List<ItemStack> onSheared(Player player, ItemStack item, Level world, BlockPos pos, int fortune) {
        List<ItemStack> drops = new ArrayList<>();
        ItemStack melons = new ItemStack(Items.MELON_SLICE, 3 + world.getRandom().nextInt(5));
        drops.add(melons);

        this.setMelonEquipped(false);
        return drops;
    }

    //This lets you add cobblestone to a slime golem to make it rocky
    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {
        //Setting Rocky if it's not already rocky or irony
        if (!isRocky() && /*!isIrony() &&*/ player.getItemInHand(hand).getItem() == Items.COBBLESTONE && ModConfig.get().enableRockyGolem.get()) {
            setRocky(true);
            if (!player.isCreative()) {
                player.getItemInHand(hand).shrink(1);
            }
            return InteractionResult.CONSUME;
        /*}
        //Setting Irony if it's not already irony or rocky
        else if (!isRocky() && !isIrony() && player.getHeldItem(hand).getItem() == Items.IRON_INGOT && ModConfig.get().enableIronGolem.get()) {
            setIrony(true);
            player.getHeldItem(hand).shrink(1);
            return ActionResultType.CONSUME;*/
        } else {
            return InteractionResult.FAIL;
        }
    }

    private static boolean isSlimeChunk(Level world, double x, double z) {
        return isSlimeChunk(world, new BlockPos(x, 0, z));
    }

    public static boolean isSlimeChunk(Level world, BlockPos pos) {
        ChunkPos chunkpos = new ChunkPos(pos);
        return WorldgenRandom.seedSlimeChunk(chunkpos.x, chunkpos.z, ((ServerLevel) world).getSeed(), 987234911L).nextInt(10) == 0;
    }

    private boolean isSlimeSpawnable() {
        if (this.level.getBiome(this.blockPosition()).value().getRegistryName().toString().equals("minecraft:swamp") || this.level.getBiome(this.blockPosition()).value().getRegistryName().toString().equals("minecraft:swamp_hills")) {
            return true;
        }

        if (isSlimeChunk(this.level, this.getX(), this.getZ())) {
            return true;
        }
        return false;
    }
}