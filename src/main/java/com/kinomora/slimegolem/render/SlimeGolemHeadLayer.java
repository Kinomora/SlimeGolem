package com.kinomora.slimegolem.render;

import com.kinomora.slimegolem.RegistryHandler;
import com.kinomora.slimegolem.entities.SlimeGolemEntity;
import com.kinomora.slimegolem.models.SlimeGolemModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SlimeGolemHeadLayer extends LayerRenderer<SlimeGolemEntity, SlimeGolemModel> {
    public SlimeGolemHeadLayer(IEntityRenderer<SlimeGolemEntity, SlimeGolemModel> p_i50922_1_) {
        super(p_i50922_1_);
    }

    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, SlimeGolemEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entitylivingbaseIn.isInvisible() && entitylivingbaseIn.isMelonEquipped()) {
            matrixStackIn.push();
            this.getEntityModel().getHead().translateRotate(matrixStackIn);
            float f = 0.625F;
            matrixStackIn.translate(0.0D, -0.34375D, 0.0D);
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F));
            matrixStackIn.scale(0.625F, -0.625F, -0.625F);
            //Block that is rendered as the head for the Slime Golem --v
            ItemStack itemstack = new ItemStack(RegistryHandler.CARVED_MELON_BLOCK.getBlock());
            Minecraft.getInstance().getItemRenderer().renderItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.HEAD, false, matrixStackIn, bufferIn, entitylivingbaseIn.world, packedLightIn, LivingRenderer.getPackedOverlay(entitylivingbaseIn, 0.0F));
            matrixStackIn.pop();
        }
    }
}