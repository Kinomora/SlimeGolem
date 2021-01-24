package com.kinomora.slimegolem.models;

import com.google.common.collect.ImmutableList;
import com.kinomora.slimegolem.entities.IronSlimeGolemEntity;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class IronSlimeGolemModel<T extends IronSlimeGolemEntity> extends SegmentedModel<T> {
    private final ModelRenderer ironSlimeGolemHead;
    private final ModelRenderer ironSlimeGolemBody;
    private final ModelRenderer ironSlimeGolemRightArm;
    private final ModelRenderer ironSlimeGolemLeftArm;
    private final ModelRenderer ironSlimeGolemLeftLeg;
    private final ModelRenderer ironSlimeGolemRightLeg;

    public IronSlimeGolemModel() {
        int i = 128;
        int j = 128;
        this.ironSlimeGolemHead = (new ModelRenderer(this)).setTextureSize(128, 128);
        this.ironSlimeGolemHead.setRotationPoint(0.0F, -7.0F, -2.0F);
        this.ironSlimeGolemHead.setTextureOffset(0, 0).addBox(-4.0F, -12.0F, -5.5F, 8.0F, 10.0F, 8.0F, 0.0F);
        this.ironSlimeGolemHead.setTextureOffset(24, 0).addBox(-1.0F, -5.0F, -7.5F, 2.0F, 4.0F, 2.0F, 0.0F);
        this.ironSlimeGolemBody = (new ModelRenderer(this)).setTextureSize(128, 128);
        this.ironSlimeGolemBody.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.ironSlimeGolemBody.setTextureOffset(0, 40).addBox(-9.0F, -2.0F, -6.0F, 18.0F, 12.0F, 11.0F, 0.0F);
        this.ironSlimeGolemBody.setTextureOffset(0, 70).addBox(-4.5F, 10.0F, -3.0F, 9.0F, 5.0F, 6.0F, 0.5F);
        this.ironSlimeGolemRightArm = (new ModelRenderer(this)).setTextureSize(128, 128);
        this.ironSlimeGolemRightArm.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.ironSlimeGolemRightArm.setTextureOffset(60, 21).addBox(-13.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F, 0.0F);
        this.ironSlimeGolemLeftArm = (new ModelRenderer(this)).setTextureSize(128, 128);
        this.ironSlimeGolemLeftArm.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.ironSlimeGolemLeftArm.setTextureOffset(60, 58).addBox(9.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F, 0.0F);
        this.ironSlimeGolemLeftLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(128, 128);
        this.ironSlimeGolemLeftLeg.setRotationPoint(-4.0F, 11.0F, 0.0F);
        this.ironSlimeGolemLeftLeg.setTextureOffset(37, 0).addBox(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F, 0.0F);
        this.ironSlimeGolemRightLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(128, 128);
        this.ironSlimeGolemRightLeg.mirror = true;
        this.ironSlimeGolemRightLeg.setTextureOffset(60, 0).setRotationPoint(5.0F, 11.0F, 0.0F);
        this.ironSlimeGolemRightLeg.addBox(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F, 0.0F);
    }

    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.ironSlimeGolemHead, this.ironSlimeGolemBody, this.ironSlimeGolemLeftLeg, this.ironSlimeGolemRightLeg, this.ironSlimeGolemRightArm, this.ironSlimeGolemLeftArm);
    }

    /**
     * Sets this entity's model rotation angles
     */
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.ironSlimeGolemHead.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        this.ironSlimeGolemHead.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        this.ironSlimeGolemLeftLeg.rotateAngleX = -1.5F * MathHelper.func_233021_e_(limbSwing, 13.0F) * limbSwingAmount;
        this.ironSlimeGolemRightLeg.rotateAngleX = 1.5F * MathHelper.func_233021_e_(limbSwing, 13.0F) * limbSwingAmount;
        this.ironSlimeGolemLeftLeg.rotateAngleY = 0.0F;
        this.ironSlimeGolemRightLeg.rotateAngleY = 0.0F;
    }

    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        int i = entityIn.getAttackTimer();
        if (i > 0) {
            this.ironSlimeGolemRightArm.rotateAngleX = -2.0F + 1.5F * MathHelper.func_233021_e_((float) i - partialTick, 10.0F);
            this.ironSlimeGolemLeftArm.rotateAngleX = -2.0F + 1.5F * MathHelper.func_233021_e_((float) i - partialTick, 10.0F);
        } else {
            this.ironSlimeGolemRightArm.rotateAngleX = (-0.2F + 1.5F * MathHelper.func_233021_e_(limbSwing, 13.0F)) * limbSwingAmount;
            this.ironSlimeGolemLeftArm.rotateAngleX = (-0.2F - 1.5F * MathHelper.func_233021_e_(limbSwing, 13.0F)) * limbSwingAmount;

        }

    }
}
