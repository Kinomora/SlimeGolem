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


/*

@OnlyIn(Dist.CLIENT)
public class IronGolemCracksLayer extends LayerRenderer<IronGolemEntity, IronGolemModel<IronGolemEntity>> {
   private static final Map<IronGolemEntity.Cracks, ResourceLocation> field_229134_a_ = ImmutableMap.of(IronGolemEntity.Cracks.LOW, new ResourceLocation("textures/entity/iron_golem/iron_golem_crackiness_low.png"), IronGolemEntity.Cracks.MEDIUM, new ResourceLocation("textures/entity/iron_golem/iron_golem_crackiness_medium.png"), IronGolemEntity.Cracks.HIGH, new ResourceLocation("textures/entity/iron_golem/iron_golem_crackiness_high.png"));

   public IronGolemCracksLayer(IEntityRenderer<IronGolemEntity, IronGolemModel<IronGolemEntity>> p_i226040_1_) {
      super(p_i226040_1_);
   }

   public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, IronGolemEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      if (!entitylivingbaseIn.isInvisible()) {
         IronGolemEntity.Cracks irongolementity$cracks = entitylivingbaseIn.func_226512_l_();
         if (irongolementity$cracks != IronGolemEntity.Cracks.NONE) {
            ResourceLocation resourcelocation = field_229134_a_.get(irongolementity$cracks);
            renderCutoutModel(this.getEntityModel(), resourcelocation, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, 1.0F, 1.0F, 1.0F);
         }
      }
   }
}

 */