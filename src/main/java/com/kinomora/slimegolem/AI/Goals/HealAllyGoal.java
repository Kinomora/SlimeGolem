package com.kinomora.slimegolem.AI.Goals;

import com.kinomora.slimegolem.entity.SlimyIronGolemEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.RangedAttackMob;

import java.util.EnumSet;

public class HealAllyGoal extends Goal {
    private final Mob entityHost;
    private final RangedAttackMob rangedAttackEntityHost;
    private LivingEntity attackTarget;
    private int rangedAttackTime = -1;
    private final double entityMoveSpeed;
    private int seeTime;
    private final int attackIntervalMin;
    private final int maxRangedAttackTime;
    private final float attackRadius;
    private final float maxAttackDistance;

    public HealAllyGoal(RangedAttackMob attacker, double movespeed, int maxAttackTime, float maxAttackDistanceIn) {
        this(attacker, movespeed, maxAttackTime, maxAttackTime, maxAttackDistanceIn);
    }

    public HealAllyGoal(RangedAttackMob attacker, double movespeed, int attackIntervalMin, int maxAttackTime, float maxAttackDistanceIn) {
        if (!(attacker instanceof LivingEntity)) {
            throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
        } else {
            this.rangedAttackEntityHost = attacker;
            this.entityHost = (Mob)attacker;
            this.entityMoveSpeed = movespeed;
            this.attackIntervalMin = attackIntervalMin;
            this.maxRangedAttackTime = maxAttackTime;
            this.attackRadius = maxAttackDistanceIn;
            this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean shouldExecute() {
        LivingEntity livingentity = this.entityHost.getTarget();
        if (livingentity != null && livingentity.isAlive() && livingentity instanceof SlimyIronGolemEntity) {
            System.out.println("True");
            this.attackTarget = livingentity;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        return this.shouldExecute() || !this.entityHost.getNavigation().isStuck();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        this.attackTarget = null;
        this.seeTime = 0;
        this.rangedAttackTime = -1;
    }

    @Override
    public boolean canUse() {
        return false;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        double d0 = this.entityHost.distanceToSqr(this.attackTarget.getX(), this.attackTarget.getY(), this.attackTarget.getZ());
        boolean flag = this.entityHost.getSensing().hasLineOfSight(this.attackTarget);
        if (flag) {
            ++this.seeTime;
        } else {
            this.seeTime = 0;
        }

        if (!(d0 > (double)this.maxAttackDistance) && this.seeTime >= 5) {
            this.entityHost.getNavigation().stop();
        } else {
            this.entityHost.getNavigation().moveTo(this.attackTarget, this.entityMoveSpeed);
        }

        this.entityHost.getLookControl().setLookAt(this.attackTarget, 30.0F, 30.0F);
        if (--this.rangedAttackTime == 0) {
            if (!flag) {
                return;
            }

            float f = Mth.sqrt(0f) / this.attackRadius;
            float lvt_5_1_ = Mth.clamp(f, 0.1F, 1.0F);
            this.rangedAttackEntityHost.performRangedAttack(this.attackTarget,lvt_5_1_);
            this.rangedAttackTime = Mth.floor(f * (float)(this.maxRangedAttackTime - this.attackIntervalMin) + (float)this.attackIntervalMin);
        } else if (this.rangedAttackTime < 0) {
            float f2 = Mth.sqrt(0f) / this.attackRadius;
            this.rangedAttackTime = Mth.floor(f2 * (float)(this.maxRangedAttackTime - this.attackIntervalMin) + (float)this.attackIntervalMin);
        }

    }
}