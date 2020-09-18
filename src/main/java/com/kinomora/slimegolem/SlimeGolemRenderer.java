package com.kinomora.slimegolem;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class SlimeGolemRenderer extends MobRenderer<SlimeGolemEntity, SlimeGolemModel<SlimeGolemEntity>> {
    private static final ResourceLocation SLIME_GOLEM_TEXTURES = SlimeGolem.createRes("textures/entity/slimeman_wacko.png");

    public SlimeGolemRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new SlimeGolemModel<>(), 0.5F);
        this.addLayer(new SlimeGolemHeadLayer(this));
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getEntityTexture(SlimeGolemEntity entity) {
        return SLIME_GOLEM_TEXTURES;
    }

    @Nullable
    @Override
    protected RenderType func_230042_a_(SlimeGolemEntity p_230042_1_, boolean p_230042_2_, boolean p_230042_3_) {
        return RenderType.getEntityTranslucent(SLIME_GOLEM_TEXTURES);
    }
}
