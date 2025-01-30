package com.prohitman.mordeomod.common.entities;

import com.prohitman.mordeomod.common.entities.goals.AnimatedMeleeAttackGoal;
import com.prohitman.mordeomod.init.ModSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MordeoEntity extends PathfinderMob implements Enemy, GeoEntity, AnimatedAttacker {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    private static final EntityDataAccessor<Boolean> ENRAGED = SynchedEntityData.defineId(MordeoEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> RUNNING = SynchedEntityData.defineId(MordeoEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(MordeoEntity.class, EntityDataSerializers.BOOLEAN);
    private final AnimatedAttacker.AttackTicker<MordeoEntity> attackTicker = new AnimatedAttacker.AttackTicker<>(this);

    private final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("mordeo.idle");
    private final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("mordeo.walk");
    private final RawAnimation RUN_ANIM = RawAnimation.begin().thenLoop("mordeo.run");
    private final RawAnimation ATTACK_ANIM = RawAnimation.begin().thenLoop("mordeo.attack");
    private final RawAnimation ANGRY_ANIM = RawAnimation.begin().thenLoop("mordeo.angry");

    public MordeoEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.xpReward = 5;
        this.noCulling = true;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes().add(Attributes.MAX_HEALTH, 1D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FOLLOW_RANGE, 40D)
                .add(Attributes.ARMOR, 4f)
                .add(Attributes.ATTACK_DAMAGE, 8f)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6f);
    }

    @Override
    public float getStepHeight() {
        return 1.5f;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(5, new AnimatedMeleeAttackGoal<>(this, true));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 45));

        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Camel.class, false, true));
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if(ATTACKING.equals(pKey)){
            this.attackTicker.reset();
        }

        super.onSyncedDataUpdated(pKey);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(RUNNING, false);
        this.entityData.define(ENRAGED, false);
        this.entityData.define(ATTACKING, false);
    }

    public boolean isAttacking(){
        return this.entityData.get(ATTACKING);
    }

    public void setAttacking(boolean isAttacking){
        this.entityData.set(ATTACKING, isAttacking);
    }

    public boolean isRunning(){
        return this.entityData.get(RUNNING);
    }

    public void setRunning(boolean isRunning){
        this.entityData.set(RUNNING, isRunning);
    }

    public boolean isEnraged(){
        return this.entityData.get(ENRAGED);
    }

    public void setEnraged(boolean isEnraged){
        this.entityData.set(ENRAGED, isEnraged);
    }

    @Override
    public void tick() {
        super.tick();
        this.attackTicker.tick();
        this.setRunning(this.getSpeed() != 0 && this.moveControl.getSpeedModifier() >= 1);
    }

    public void stopMoving(){//may need rework
        this.setZza(0);
        this.setSpeed(0);
        this.setXxa(0);
        this.setDeltaMovement(this.getDeltaMovement().multiply(0, 1, 0));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("enraged", this.isEnraged());
        pCompound.putBoolean("attacking", this.isAttacking());

    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setEnraged(pCompound.getBoolean("enraged"));
        this.setAttacking(pCompound.getBoolean("attacking"));
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.MORDEO_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return super.getHurtSound(pDamageSource);
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.MORDEO_ANGRY.get();
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "Mordeo", 4, this::walkAnimController));

    }

    private PlayState walkAnimController(AnimationState<MordeoEntity> state) {
        if(this.isAttacking()){
            return state.setAndContinue(ATTACK_ANIM);
        } else if(state.isMoving() && this.isRunning()){
            return state.setAndContinue(RUN_ANIM);
        } else if(state.isMoving()){
            return state.setAndContinue(WALK_ANIM);
        }

        return state.setAndContinue(IDLE_ANIM);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }
}
