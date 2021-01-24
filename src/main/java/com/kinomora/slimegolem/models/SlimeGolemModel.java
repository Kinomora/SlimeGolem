package com.kinomora.slimegolem.models;

import com.google.common.collect.ImmutableList;
import com.kinomora.slimegolem.entities.SlimeGolemEntity;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SlimeGolemModel extends SegmentedModel<SlimeGolemEntity> {
    private final ModelRenderer head;
    private final ModelRenderer bodyMiddle;
    private final ModelRenderer bottomBody;
    private final ModelRenderer rightArm;
    private final ModelRenderer leftArm;
    private final ModelRenderer rockyHeadInner;
    private final ModelRenderer rockyBodyMiddleInner;
    private final ModelRenderer rockyBodyBottomInner;
    public SlimeGolemEntity slimeGolemEntity;

    public SlimeGolemModel() {
        float f = 4.0F;
        float f1 = 0.0F;
        //Head - normal
        this.head = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 128);
        this.head.addBox(-4.0F, -7.90F, -4.0F, 8.0F, 8.0F, 8.0F, -0.5F);
        this.head.setRotationPoint(0.0F, 4.0F, 0.0F);
        //Head - rocky
        this.rockyHeadInner = (new ModelRenderer(this,0,60)).setTextureSize(64,128);
        this.rockyHeadInner.addBox(-3.0F, -6.90F, -3.0F, 6.0F, 6.0F, 6.0F, -0.5F);
        this.rockyHeadInner.setRotationPoint(0.0F, 4.0F, 0.0F);

        //Middle - normal
        this.bodyMiddle = (new ModelRenderer(this, 0, 16)).setTextureSize(64, 128);
        this.bodyMiddle.addBox(-5.0F, -9.95F, -5.0F, 10.0F, 10.0F, 10.0F, -0.5F);
        this.bodyMiddle.setRotationPoint(0.0F, 13.0F, 0.0F);
        //Middle - rocky
        this.rockyBodyMiddleInner = (new ModelRenderer(this, 0, 72)).setTextureSize(64, 128);
        this.rockyBodyMiddleInner.addBox(-4.0F, -8.95F, -4.0F, 8.0F, 8.0F, 8.0F, -0.5F);
        this.rockyBodyMiddleInner.setRotationPoint(0.0F, 13.0F, 0.0F);

        //Lower - normal
        this.bottomBody = (new ModelRenderer(this, 0, 36)).setTextureSize(64, 128);
        this.bottomBody.addBox(-6.0F, -12.0F, -6.0F, 12.0F, 12.0F, 12.0F, -0.5F);
        this.bottomBody.setRotationPoint(0.0F, 24.0F, 0.0F);
        //Lower - rocky
        this.rockyBodyBottomInner = (new ModelRenderer(this, 0, 88)).setTextureSize(64, 128);
        this.rockyBodyBottomInner.addBox(-5.0F, -11.0F, -5.0F, 10.0F, 10.0F, 10.0F, -0.5F);
        this.rockyBodyBottomInner.setRotationPoint(0.0F, 24.0F, 0.0F);


        //Right arm
        this.rightArm = (new ModelRenderer(this, 32, 0)).setTextureSize(64, 128);
        this.rightArm.addBox(-1.0F, 0.05F, -1.0F, 12.0F, 2.0F, 2.0F, -0.5F);
        this.rightArm.setRotationPoint(0.0F, 6.0F, 0.0F);
        //Left arm
        this.leftArm = (new ModelRenderer(this, 32, 0)).setTextureSize(64, 128);
        this.leftArm.addBox(-1.0F, 0.05F, -1.0F, 12.0F, 2.0F, 2.0F, -0.5F);
        this.leftArm.setRotationPoint(0.0F, 6.0F, 0.0F);
    }

    @Override
    public void setLivingAnimations(SlimeGolemEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.rockyHeadInner.showModel = entityIn.isRocky();
        this.rockyBodyMiddleInner.showModel = entityIn.isRocky();
        this.rockyBodyBottomInner.showModel = entityIn.isRocky();
        super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
    }

    /**
     * Sets this entity's model rotation angles
     */
    public void setRotationAngles(SlimeGolemEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //normal handling
        this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
        this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
        this.bodyMiddle.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F) * 0.25F;
        float normalSIN = MathHelper.sin(this.bodyMiddle.rotateAngleY);
        float normalCOS = MathHelper.cos(this.bodyMiddle.rotateAngleY);

        //rockyInner handling
        this.rockyHeadInner.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
        this.rockyHeadInner.rotateAngleX = headPitch * ((float)Math.PI / 180F);
        this.rockyBodyMiddleInner.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F) * 0.25F;


        //Arm handling
        this.rightArm.rotateAngleZ = 1.0F;
        this.leftArm.rotateAngleZ = -1.0F;
        this.rightArm.rotateAngleY = 0.0F + this.bodyMiddle.rotateAngleY;
        this.leftArm.rotateAngleY = (float)Math.PI + this.bodyMiddle.rotateAngleY;
        this.rightArm.rotationPointX = normalCOS * 5.0F;
        this.rightArm.rotationPointZ = -normalSIN * 5.0F;
        this.leftArm.rotationPointX = -normalCOS * 5.0F;
        this.leftArm.rotationPointZ = normalSIN * 5.0F;
    }

    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.bodyMiddle, this.bottomBody, this.head, this.rightArm, this.leftArm, this.rockyBodyBottomInner, this.rockyBodyMiddleInner, this.rockyHeadInner);
    }

    public ModelRenderer func_205070_a() {
        return this.head;
    }
}