package com.kinomora.slimegolem;


import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SlimeGolemRenderer extends MobRenderer<SlimeGolemEntity, SlimeGolemModel> {
    private static final ResourceLocation SLIME_GOLEM_TEXTURES = SlimeGolem.createRes("textures/entity/slimeman_wacko.png");

    public SlimeGolemRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new SlimeGolemModel(), 0.5F);
        this.addLayer(new SlimeGolemHeadLayer(this));
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getEntityTexture(SlimeGolemEntity entity) {
        return SLIME_GOLEM_TEXTURES;
    }

    @Override
    protected RenderType func_230496_a_(SlimeGolemEntity p_230496_1_, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
        return RenderType.getEntityTranslucent(getEntityTexture(p_230496_1_));
    }
}
