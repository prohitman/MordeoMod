package com.prohitman.mordeomod.datagen.client;

import com.prohitman.mordeomod.MordeoMod;
import com.prohitman.mordeomod.init.ModEntities;
import com.prohitman.mordeomod.init.ModItems;
import com.prohitman.mordeomod.init.ModSounds;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;
import org.codehaus.plexus.util.StringUtils;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output, String locale) {
        super(output, MordeoMod.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        addItem(ModItems.MORDEO_SPAWN_EGG);

        addSound(ModSounds.MORDEO_ANGRY);
        addSound(ModSounds.MORDEO_ATTACK);
        addSound(ModSounds.MORDEO_IDLE);
        addSound(ModSounds.MORDEO_RUN);
        addSound(ModSounds.MORDEO_WALK);

        addEntityType(ModEntities.MORDEO, "Mordeo");
    }

    public void addAdvTitle(String name, String translation){
        add("advancements.mordeomod." + name + ".title", translation);
    }

    public void addAdvDescription(String name, String translation){
        add("advancements.mordeomod." + name + ".description", translation);
    }

    public void addEffect(RegistryObject<MobEffect> key){
        add(key.get().getDescriptionId(), StringUtils.capitaliseAllWords(key.getId().getPath().replaceAll("_", " ")));
    }

    public void addBlock(RegistryObject<Block> key) {
        add(key.get().getDescriptionId(), StringUtils.capitaliseAllWords(key.getId().getPath().replaceAll("_", " ")));
    }

    public void addItem(RegistryObject<Item> key){
        add(key.get().getDescriptionId(), StringUtils.capitaliseAllWords(key.getId().getPath().replaceAll("_", " ")));
    }

    public void addSound(RegistryObject<SoundEvent> key){
        add("sounds." + key.getId().toLanguageKey(), StringUtils.capitaliseAllWords(key.getId().getPath().replaceAll("_", " ")));
    }
}
