package com.kinomora.slimegolem.render;

import com.kinomora.slimegolem.SlimeGolems;
import com.kinomora.slimegolem.entities.IronSlimeGolemEntity;
import com.kinomora.slimegolem.models.IronSlimeGolemModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IronSlimeGolemRenderer extends MobRenderer<IronSlimeGolemEntity, IronSlimeGolemModel<IronSlimeGolemEntity>> {

    private static final ResourceLocation IRON_SLIME_GOLEM_TEXTURES = SlimeGolems.createRes("textures/entity/iron_slime_golem/iron_slime_golem.png");

    public IronSlimeGolemRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new IronSlimeGolemModel<>(), 0.7F);
        this.addLayer(new IronSlimeGolemCracksLayer(this));
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getEntityTexture(IronSlimeGolemEntity entity) {
        return IRON_SLIME_GOLEM_TEXTURES;
    }

    protected void applyRotations(IronSlimeGolemEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        if (!((double)entityLiving.limbSwingAmount < 0.01D)) {
            float f = 13.0F;
            float f1 = entityLiving.limbSwing - entityLiving.limbSwingAmount * (1.0F - partialTicks) + 6.0F;
            float f2 = (Math.abs(f1 % 13.0F - 6.5F) - 3.25F) / 3.25F;
            matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(6.5F * f2));
        }
    }

    @Override
    protected RenderType func_230496_a_(IronSlimeGolemEntity entity, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
        return RenderType.getEntityTranslucent(getEntityTexture(entity));
    }
}
