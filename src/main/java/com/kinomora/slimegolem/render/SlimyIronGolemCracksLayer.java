package com.kinomora.slimegolem.render;

import com.google.common.collect.ImmutableMap;
import com.kinomora.slimegolem.entity.SlimyIronGolemEntity;
import com.kinomora.slimegolem.render.model.SlimyIronGolemModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class SlimyIronGolemCracksLayer extends  RenderLayer<SlimyIronGolemEntity,SlimyIronGolemModel<SlimyIronGolemEntity>> {
    private static final Map<SlimyIronGolemEntity.Crackiness, ResourceLocation> resourceLocations = ImmutableMap.of(SlimyIronGolemEntity.Crackiness.LOW, new ResourceLocation("textures/entity/iron_golem/iron_golem_crackiness_low.png"), SlimyIronGolemEntity.Crackiness.MEDIUM, new ResourceLocation("textures/entity/iron_golem/iron_golem_crackiness_medium.png"), SlimyIronGolemEntity.Crackiness.HIGH, new ResourceLocation("textures/entity/iron_golem/iron_golem_crackiness_high.png"));

    public SlimyIronGolemCracksLayer(RenderLayerParent<SlimyIronGolemEntity, SlimyIronGolemModel<SlimyIronGolemEntity>> p_117135_) {
        super(p_117135_);
    }

    public void render(PoseStack p_117148_, MultiBufferSource p_117149_, int p_117150_, SlimyIronGolemEntity p_117151_, float p_117152_, float p_117153_, float p_117154_, float p_117155_, float p_117156_, float p_117157_) {
        if (!p_117151_.isInvisible()) {
            SlimyIronGolemEntity.Crackiness irongolem$crackiness = p_117151_.getCrackiness();
            if (irongolem$crackiness != SlimyIronGolemEntity.Crackiness.NONE) {
                ResourceLocation resourcelocation = resourceLocations.get(irongolem$crackiness);
                renderColoredCutoutModel(this.getParentModel(), resourcelocation, p_117148_, p_117149_, p_117150_, p_117151_, 1.0F, 1.0F, 1.0F);
            }
        }
    }
}