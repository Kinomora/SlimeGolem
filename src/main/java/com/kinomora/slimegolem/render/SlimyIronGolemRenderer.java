package com.kinomora.slimegolem.render;

import com.kinomora.slimegolem.SlimeGolems;
import com.kinomora.slimegolem.entity.SlimyIronGolemEntity;
import com.kinomora.slimegolem.render.model.SlimyIronGolemModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SlimyIronGolemRenderer extends MobRenderer<SlimyIronGolemEntity, SlimyIronGolemModel<SlimyIronGolemEntity>> {
    private static final ResourceLocation SLIMY_IRON_GOLEM_TEXTURES = SlimeGolems.createRes("textures/entity/iron_slime_golem/iron_slime_golem.png");
    public static final ModelLayerLocation MODEL_RES = new ModelLayerLocation(SlimeGolems.createRes("model"), "main");

    public SlimyIronGolemRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new SlimyIronGolemModel<>(), 0.7F);
        //this.addLayer(new SlimyIronGolemCracksLayer(this));
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getTextureLocation(SlimyIronGolemEntity entity) {
        return SLIMY_IRON_GOLEM_TEXTURES;
    }

    /*protected void applyRotations(SlimyIronGolemEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        if (!((double)entityLiving.limbSwingAmount < 0.01D)) {
            float f = 13.0F;
            float f1 = entityLiving.limbSwing - entityLiving.limbSwingAmount * (1.0F - partialTicks) + 6.0F;
            float f2 = (Math.abs(f1 % 13.0F - 6.5F) - 3.25F) / 3.25F;
            matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(6.5F * f2));
        }
    }

    @Override
    protected RenderType func_230496_a_(SlimyIronGolemEntity entity, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
        return RenderType.getEntityTranslucent(getEntityTexture(entity));
    }*/
}
