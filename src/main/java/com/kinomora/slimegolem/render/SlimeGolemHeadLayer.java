package com.kinomora.slimegolem.render;

import com.kinomora.slimegolem.RegistryHandler;
import com.kinomora.slimegolem.entity.SlimeGolemEntity;
import com.kinomora.slimegolem.render.model.SlimeGolemModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SlimeGolemHeadLayer extends RenderLayer<SlimeGolemEntity, SlimeGolemModel> {
    public SlimeGolemHeadLayer(RenderLayerParent<SlimeGolemEntity, SlimeGolemModel> p_i50922_1_) {
        super(p_i50922_1_);
    }

    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, SlimeGolemEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entitylivingbaseIn.isInvisible() && entitylivingbaseIn.isMelonEquipped()) {
            matrixStackIn.pushPose();
            this.getParentModel().getSlimeHead().translateAndRotate(matrixStackIn);
            float f = 0.625F;
            matrixStackIn.translate(0.0D, -0.34375D, 0.0D);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F));
            matrixStackIn.scale(0.625F, -0.625F, -0.625F);
            //Block that is rendered as the head for the Slime Golem --v
            ItemStack itemstack = new ItemStack(RegistryHandler.Blocks.CARVED_MELON.get());
            Minecraft.getInstance().getItemRenderer().renderStatic(entitylivingbaseIn, itemstack, ItemTransforms.TransformType.HEAD, false, matrixStackIn, bufferIn, entitylivingbaseIn.level, packedLightIn, LivingEntityRenderer.getOverlayCoords(entitylivingbaseIn, 0.0F), entitylivingbaseIn.getId());
            matrixStackIn.popPose();
        }
    }
}