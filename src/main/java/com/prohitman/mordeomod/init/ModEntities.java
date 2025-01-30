package com.prohitman.mordeomod.init;

import com.prohitman.mordeomod.MordeoMod;
import com.prohitman.mordeomod.common.entities.MordeoEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MordeoMod.MODID);

    public static final RegistryObject<EntityType<MordeoEntity>> MORDEO = ENTITY_TYPES.register("mordeo",
            () -> EntityType.Builder.of(MordeoEntity::new, MobCategory.MONSTER).sized(1F, 2F).clientTrackingRange(20).build("mordeo"));

}
