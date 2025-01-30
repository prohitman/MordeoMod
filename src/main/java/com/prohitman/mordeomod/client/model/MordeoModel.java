package com.prohitman.mordeomod.client.model;

import com.prohitman.mordeomod.MordeoMod;
import com.prohitman.mordeomod.common.entities.MordeoEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class MordeoModel extends DefaultedEntityGeoModel<MordeoEntity> {
    public MordeoModel() {
        super(new ResourceLocation(MordeoMod.MODID, "mordeo"), true);
    }
}
