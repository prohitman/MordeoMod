package com.prohitman.mordeomod.util;

import com.prohitman.mordeomod.common.entities.AnimatedAttacker;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class EntityHelper {

    public static double getXZSize(AABB bounds){
        double xSize = bounds.getXsize();
        double zSize = bounds.getZsize();
        return (xSize + zSize) / 2.0;
    }

    public static <T extends Mob & AnimatedAttacker> List<LivingEntity> hitTargetsWithAOEAttack(T attacker, AABB attackBounds) {
        List<LivingEntity> hitTargets = new ArrayList<>();
        if(!attacker.level().isClientSide){
            List<LivingEntity> targets = attacker.level().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, attacker, attackBounds);
            targets.forEach(target -> {
                if (target.invulnerableTime <= 0) {
                    boolean hurtTarget = attacker.doHurtTarget(target);
                    if(hurtTarget){
                        hitTargets.add(target);
                    }
                }
            });
        }
        return hitTargets;
    }

    public static double getFollowRange(Mob mob){
        return mob.getAttributeValue(Attributes.FOLLOW_RANGE);
    }

    public static boolean hasBlocksAbove(PathfinderMob mob, BlockPos targetPos) {
        return !mob.level().canSeeSky(targetPos) && (double) mob.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, targetPos).getY() > mob.getY();
    }

    /*public static boolean isLookingAt(LivingEntity looker, LivingEntity target, double leniencyFactor, boolean requireLOS, boolean checkBody) {
        Vec3 viewVector = looker.getViewVector(1.0F).normalize();
        Vec3 vectorToTarget = looker.getEyePosition().vectorTo(target.getEyePosition());
        double distanceToTarget = vectorToTarget.length();
        vectorToTarget = vectorToTarget.normalize();
        double leniency = leniencyFactor / distanceToTarget;
        return sameDirection(viewVector, vectorToTarget, leniency)
                && (!requireLOS || looker.hasLineOfSight(target))
                && (!checkBody || sameDirection(getBodyViewVector(looker, 1.0F).normalize(), vectorToTarget, leniency));
    }

    private static boolean sameDirection(Vec3 a, Vec3 b, double leniency) {
        return a.dot(b) >= 1.0 - leniency;
    }

    public static Vec3 getBodyViewVector(LivingEntity looker, float partialTicks){
        return looker.calculateViewVector(looker.getViewXRot(partialTicks), partialTicks == 1.0F ? looker.yBodyRot : Mth.lerp(partialTicks, looker.yBodyRotO, looker.yBodyRot));
    }

    public static boolean isLookingAwayFrom(LivingEntity looker, Vec3 target, double leniencyFactor, boolean checkBody, boolean checkY) {
        Vec3 viewVector = looker.getViewVector(1.0F).multiply(1, checkY ? 1 : 0, 1).normalize();
        Vec3 vectorAwayFromTarget = target.vectorTo(looker.getEyePosition()).multiply(1, checkY ? 1 : 0, 1);
        double distanceAwayFromTarget = vectorAwayFromTarget.length();
        vectorAwayFromTarget = vectorAwayFromTarget.normalize();
        double leniency = leniencyFactor / distanceAwayFromTarget;
        return sameDirection(viewVector, vectorAwayFromTarget, leniency)
                && (!checkBody || sameDirection(getBodyViewVector(looker, 1.0F).multiply(1, checkY ? 1 : 0, 1).normalize(), vectorAwayFromTarget, leniency));
    }*/
}
