package com.prohitman.mordeomod.common;

import com.prohitman.mordeomod.MordeoMod;
import com.prohitman.mordeomod.common.entities.MordeoEntity;
import com.prohitman.mordeomod.init.ModEntities;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = MordeoMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.MORDEO.get(), MordeoEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawns(@NotNull SpawnPlacementRegisterEvent event) {
        event.register(ModEntities.MORDEO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE_WG, MordeoEntity::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
    }
}
