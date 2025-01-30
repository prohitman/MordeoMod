package com.prohitman.mordeomod.common.entities.goals;

import com.prohitman.mordeomod.common.entities.AnimatedAttacker;
import com.prohitman.mordeomod.common.entities.MordeoEntity;
import com.prohitman.mordeomod.util.HitboxHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class AnimatedMeleeAttackGoal<T extends PathfinderMob & AnimatedAttacker> extends BaseMeleeAttackGoal {
    private final T attacker;
/*    private final int maxChaseTicks = ModConfiguration.ATTACK_CHASE_TICKS.get();
    private int chaseTicks = 0;*/
    public AnimatedMeleeAttackGoal(T pMob, boolean pFollowingTargetEvenIfNotSeen) {
        super(pMob, pFollowingTargetEvenIfNotSeen);
        this.attacker = pMob;
    }

/*    @Override
    public boolean canUse() {
        if(((MordeoEntity)attacker).getGoalID() != ActionType.ATTACK.getGoalID()){
            return false;
        }
        return super.canUse();
    }*/

/*    @Override
    public boolean canContinueToUse() {
        if(((GoatManEntity)attacker).getGoalID() != ActionType.ATTACK.getGoalID() || chaseTicks >= maxChaseTicks){
            return false;
        }
        return super.canContinueToUse();
    }*/

    @Override
    public void start() {
        this.speedModifier = 1.2f;
        super.start();
/*        System.out.println("Starting");
        ((GoatManEntity)attacker).setHasGoal(true);
        if(((GoatManEntity)attacker).isTransitioning()){
            ((GoatManEntity)attacker).getNavigation().stop();
        }*/
    }

    @Override
    public void tick() {
        //if(!((GoatManEntity)attacker).isTransitioning()){
        //    chaseTicks++;
            super.tick();
        //}
    }

    @Override
    public void stop() {
        super.stop();
        //((GoatManEntity)attacker).setHasGoal(false);
        //((GoatManEntity)attacker).setGoalID(ActionType.FLEE.getGoalID());
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity pEnemy, double distanceToEnemy) {
/*        if(this.isTimeToAttack() && this.attacker.getActiveAttackType() == null && this.selectedAttackType == null){
            this.selectedAttackType = this.attacker.selectAttackTypeForTarget(pEnemy);
            if(DebugFlags.DEBUG_ANIMATED_ATTACK)
                BlastFromThePast.LOGGER.info("{} selected attack type {}", this.attacker, this.selectedAttackType);
        }*/
        if(this.attacker.getTarget() != null && this.attacker.isAttacking() && !isTargetCloseEnoughToStart(attacker, attacker.getTarget())){
            this.attacker.setAttacking(false);
        }
        System.out.println("PerformAttack?" + this.isTimeToAttack()
                + this.isTargetCloseEnoughToStart(this.attacker, pEnemy)
                + this.mob.getSensing().hasLineOfSight(pEnemy)
                + this.isTargetCloseEnoughToContinue(this.attacker, pEnemy));
        if (this.canPerformAttack(pEnemy)) {
            this.attacker.setAttacking(true);
            this.resetAttackCooldown();
        }
    }

    protected boolean canPerformAttack(LivingEntity target) {
        return this.isTimeToAttack()
                && this.isTargetCloseEnoughToStart(this.attacker, target);
//                && this.mob.getSensing().hasLineOfSight(target);
    }
    public boolean isTargetCloseEnoughToStart(T attacker, LivingEntity target) {
        Vec3 startAttackSize = AnimatedAttacker.AttackTicker.DEFAULT_ATTACK_SIZE.scale(attacker.getScale()).multiply(1.5, 1, 1.5);
        AABB startAttackBounds = HitboxHelper.createHitboxRelativeToFront(attacker, startAttackSize.x(), startAttackSize.y(), startAttackSize.z());
        return startAttackBounds.intersects(target.getBoundingBox());
    }

    public boolean isTargetCloseEnoughToContinue(T attacker, LivingEntity target) {
        Vec3 startAttackSize = AnimatedAttacker.AttackTicker.DEFAULT_ATTACK_SIZE.scale(attacker.getScale()).multiply(4, 2.5, 4);
        AABB startAttackBounds = HitboxHelper.createHitboxRelativeToFront(attacker, startAttackSize.x(), startAttackSize.y(), startAttackSize.z());
        return startAttackBounds.intersects(target.getBoundingBox());
    }

    @Override
    protected void resetAttackCooldown() {
        int attackCooldown = 0;//20
        if(this.attacker.isAttacking()){
            attackCooldown = 10 - 5;//44
        }

        ticksUntilNextAttack = this.adjustedTickDelay(attackCooldown);
    }
}
