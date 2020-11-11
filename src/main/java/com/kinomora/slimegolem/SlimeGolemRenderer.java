package com.kinomora.slimegolem;


import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

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

    @Nullable
    @Override
    protected RenderType func_230042_a_(SlimeGolemEntity p_230042_1_, boolean p_230042_2_, boolean p_230042_3_) {
        return RenderType.getEntityTranslucent(SLIME_GOLEM_TEXTURES);
    }

    @Override
    public void render(SlimeGolemEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        if (!entityIn.isInvisible() && entityIn.isRocky()) {
            matrixStackIn.push();
            this.getEntityModel().func_205070_a().translateRotate(matrixStackIn);
            float f = 0.625F;
            matrixStackIn.translate(0.0D, -0.5D, 0.0D);
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F));
            matrixStackIn.scale(0.625F, -0.625F, -0.625F);
            //Block that is rendered -----------------------v
            ItemStack itemstack = new ItemStack(Blocks.COBBLESTONE.getBlock());
            Minecraft.getInstance().getItemRenderer().renderItem(entityIn, itemstack, ItemCameraTransforms.TransformType.FIXED, false, matrixStackIn, bufferIn, entityIn.world, packedLightIn, LivingRenderer.getPackedOverlay(entityIn, 0.0F));
            matrixStackIn.pop();
        }
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

    }
}
