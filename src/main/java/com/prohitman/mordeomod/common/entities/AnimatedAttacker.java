package com.prohitman.mordeomod.common.entities;

import com.prohitman.mordeomod.init.ModEntities;
import com.prohitman.mordeomod.util.EntityHelper;
import com.prohitman.mordeomod.util.HitboxHelper;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public interface AnimatedAttacker {

    void setAttacking(boolean attacking);

    boolean isAttacking();

    class AttackTicker<T extends Mob & AnimatedAttacker>{
        // 1.37208242536
        public static final double MINIMUM_ATTACK_SIZE = HitboxHelper.calculateMinimumAttackHitboxWidth(ModEntities.MORDEO.get().getWidth());
        // Adding 1 to the minimum attack size allows targets whose hitboxes are up to 0.5F blocks away from one of the attackers hitbox's corners to be hit
        public static final Vec3 DEFAULT_ATTACK_SIZE = new Vec3(MINIMUM_ATTACK_SIZE + 1, MINIMUM_ATTACK_SIZE + 1, MINIMUM_ATTACK_SIZE + 1);// 1 1 1

        private final T attacker;
        private int tick;

        public AttackTicker(T attacker) {
            this.attacker = attacker;
        }

        public void reset(){
            this.tick = 0;
        }

        public void tick(){
            //System.out.println("Tick!!!::");
            if(this.attacker.isAttacking()){
                if(this.attacker.getTarget() != null){
                    attacker.getLookControl().setLookAt(attacker.getTarget());
                }
                if(attacker instanceof MordeoEntity mordeoEntity){
                    mordeoEntity.stopMoving();
                }
                //System.out.println("Attacking...");
                if(this.tick == 5){
                    //System.out.println("Execute attack");

                    this.executeAttackPoint(this.attacker);
                }
                if(!this.attacker.level().isClientSide && this.tick >= 10) {
                    this.attacker.setAttacking(false);
                }
            }

            if(this.attacker.isAttacking() && !(this.tick >= 10)){
                //System.out.println("Ticking...");
                this.tick++;
            }
        }

        public void executeAttackPoint(T attacker){
            Vec3 attackSize = DEFAULT_ATTACK_SIZE.scale(attacker.getScale()).multiply(1, 1, 1);
            AABB attackBounds = HitboxHelper.createHitboxRelativeToFront(attacker, attackSize.x(), attackSize.y(), attackSize.z());
            EntityHelper.hitTargetsWithAOEAttack(attacker, attackBounds);
        }

        public int get() {
            return this.tick;
        }
    }
}
