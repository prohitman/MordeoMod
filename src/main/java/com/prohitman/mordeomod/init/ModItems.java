package com.prohitman.mordeomod.init;

import com.prohitman.mordeomod.MordeoMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MordeoMod.MODID);

    public static final RegistryObject<Item> MORDEO_SPAWN_EGG = ITEMS.register("mordeo_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.MORDEO, 0x5D3A2F, 0xEFDCCF, new Item.Properties()));
}
