package com.kinomora.slimegolem.render;


import com.kinomora.slimegolem.SlimeGolems;
import com.kinomora.slimegolem.entity.SlimeGolemEntity;
import com.kinomora.slimegolem.render.model.SlimeGolemModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SlimeGolemRenderer extends MobRenderer<SlimeGolemEntity, SlimeGolemModel> {
    private static final ResourceLocation SLIME_GOLEM_TEXTURES = SlimeGolems.createRes("textures/entity/slimeman_wacko.png");
    public static final ModelLayerLocation MODEL_RES = new ModelLayerLocation(SlimeGolems.createRes("slimeGolemModel"), "main");

    public SlimeGolemRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new SlimeGolemModel(ctx.bakeLayer(MODEL_RES)), 0.5F);
        this.addLayer(new SlimeGolemHeadLayer(this));
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getTextureLocation(SlimeGolemEntity entity) {
        return SLIME_GOLEM_TEXTURES;
    }

    @Override
    protected RenderType getRenderType(SlimeGolemEntity p_230496_1_, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
        return RenderType.entityTranslucent(getTextureLocation(p_230496_1_));
    }
}
