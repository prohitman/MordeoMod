package com.prohitman.mordeomod.client.renderer;

import com.prohitman.mordeomod.client.model.MordeoModel;
import com.prohitman.mordeomod.common.entities.MordeoEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MordeoRenderer extends GeoEntityRenderer<MordeoEntity> {
    public MordeoRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MordeoModel());
    }
}
