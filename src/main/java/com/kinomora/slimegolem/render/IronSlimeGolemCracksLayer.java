package com.kinomora.slimegolem.render;

import com.google.common.collect.ImmutableMap;
import com.kinomora.slimegolem.SlimeGolems;
import com.kinomora.slimegolem.entities.IronSlimeGolemEntity;
import com.kinomora.slimegolem.models.IronSlimeGolemModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class IronSlimeGolemCracksLayer extends LayerRenderer<IronSlimeGolemEntity, IronSlimeGolemModel<IronSlimeGolemEntity>> {

    private static final Map<IronSlimeGolemEntity.Cracks, ResourceLocation> crackMap = ImmutableMap.of(IronSlimeGolemEntity.Cracks.LOW, SlimeGolems.createRes("textures/entity/iron_slime_golem/iron_slime_golem_crackiness_low.png"), IronSlimeGolemEntity.Cracks.MEDIUM, SlimeGolems.createRes("textures/entity/iron_slime_golem/iron_slime_golem_crackiness_medium.png"), IronSlimeGolemEntity.Cracks.HIGH, SlimeGolems.createRes("textures/entity/iron_slime_golem/iron_slime_golem_crackiness_high.png"));

    public IronSlimeGolemCracksLayer(IEntityRenderer<IronSlimeGolemEntity, IronSlimeGolemModel<IronSlimeGolemEntity>> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, IronSlimeGolemEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entitylivingbaseIn.isInvisible()) {
            IronSlimeGolemEntity.Cracks irongolementity$cracks = entitylivingbaseIn.getCrackLevel();
            if (irongolementity$cracks != IronSlimeGolemEntity.Cracks.NONE) {
                ResourceLocation resourcelocation = crackMap.get(irongolementity$cracks);
                renderCutoutModel(this.getEntityModel(), resourcelocation, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, 1.0F, 1.0F, 1.0F);
            }
        }
    }
}