package com.kinomora.slimegolem.render;

import com.google.common.collect.ImmutableMap;
import com.kinomora.slimegolem.SlimeGolems;
import com.kinomora.slimegolem.entity.SlimyIronGolemEntity;
import com.kinomora.slimegolem.render.model.SlimyIronGolemModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class SlimyIronGolemCracksLayer extends LayerRenderer<SlimyIronGolemEntity, SlimyIronGolemModel<SlimyIronGolemEntity>> {

    private static final Map<SlimyIronGolemEntity.Cracks, ResourceLocation> crackMap = ImmutableMap.of(SlimyIronGolemEntity.Cracks.LOW, SlimeGolems.createRes("textures/entity/iron_slime_golem/iron_slime_golem_crackiness_low.png"), SlimyIronGolemEntity.Cracks.MEDIUM, SlimeGolems.createRes("textures/entity/iron_slime_golem/iron_slime_golem_crackiness_medium.png"), SlimyIronGolemEntity.Cracks.HIGH, SlimeGolems.createRes("textures/entity/iron_slime_golem/iron_slime_golem_crackiness_high.png"));

    public SlimyIronGolemCracksLayer(IEntityRenderer<SlimyIronGolemEntity, SlimyIronGolemModel<SlimyIronGolemEntity>> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, SlimyIronGolemEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entitylivingbaseIn.isInvisible()) {
            SlimyIronGolemEntity.Cracks irongolementity$cracks = entitylivingbaseIn.getCrackLevel();
            if (irongolementity$cracks != SlimyIronGolemEntity.Cracks.NONE) {
                ResourceLocation resourcelocation = crackMap.get(irongolementity$cracks);
                renderCutoutModel(this.getEntityModel(), resourcelocation, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, 1.0F, 1.0F, 1.0F);
            }
        }
    }
}