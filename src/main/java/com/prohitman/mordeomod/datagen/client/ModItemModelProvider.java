package com.prohitman.mordeomod.datagen.client;

import com.prohitman.mordeomod.MordeoMod;
import com.prohitman.mordeomod.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MordeoMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //Items
        withExistingParent(ModItems.MORDEO_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }

    private void createBlockSingle(RegistryObject<Block> block){
        singleTexture((block.getId().getPath()),
                mcLoc("item/generated"),
                "layer0", modLoc("item/" + block.getId().getPath()));
    }

    private void createParentBlock(RegistryObject<Block> handler) {
        withExistingParent(handler.getId().getPath(), modLoc( "block/" + handler.getId().getPath()));
    }
}
