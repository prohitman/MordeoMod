package com.prohitman.mordeomod.init;

import com.prohitman.mordeomod.MordeoMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MordeoMod.MODID);
    public static final RegistryObject<SoundEvent> MORDEO_IDLE = registerSoundEvents("mordeo_idle");
    public static final RegistryObject<SoundEvent> MORDEO_ANGRY = registerSoundEvents("mordeo_angry");
    public static final RegistryObject<SoundEvent> MORDEO_ATTACK = registerSoundEvents("mordeo_attack");
    public static final RegistryObject<SoundEvent> MORDEO_RUN = registerSoundEvents("mordeo_run");
    public static final RegistryObject<SoundEvent> MORDEO_WALK = registerSoundEvents("mordeo_walk");

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MordeoMod.MODID, name)));
    }
    private static RegistryObject<SoundEvent> registerRangedSoundEvents(String name, float range) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(MordeoMod.MODID, name), range));
    }
}
