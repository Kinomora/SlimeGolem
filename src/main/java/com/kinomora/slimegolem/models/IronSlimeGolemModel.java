package com.kinomora.slimegolem.models;

import com.google.common.collect.ImmutableList;
import com.kinomora.slimegolem.entities.IronSlimeGolemEntity;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;

public class IronSlimeGolemModel<T extends IronSlimeGolemEntity> extends SegmentedModel<T> {
    private final ModelRenderer head;
    private final ModelRenderer headIn;
    private final ModelRenderer body;
    private final ModelRenderer bodyIn;
    private final ModelRenderer rArm;
    private final ModelRenderer rArmIn;
    private final ModelRenderer lArm;
    private final ModelRenderer lArmIn;
    private final ModelRenderer lLeg;
    private final ModelRenderer lLegIn;
    private final ModelRenderer rLeg;
    private final ModelRenderer rLegIn;
    private static final Field CUBELIST = ObfuscationReflectionHelper.findField(ModelRenderer.class, "field_78804_l");

    public IronSlimeGolemModel() {
        int texHight = 128;
        int texWidth = 128;

        /**HEAD*/
        //Slime
        this.head = (new ModelRenderer(this)).setTextureSize(texHight, texWidth);
        this.head.setRotationPoint(0.0F, -7.0F, -2.0F);
        this.head.setTextureOffset(0, 0).addBox(-4.0F, -11.9F, -5.5F, 8.0F, 10.0F, 8.0F, 0.5F);
        //Nose - Slime
        this.head.setTextureOffset(24, 0).addBox(-1.0F, -4.9F, -7.49F, 2.0F, 4.0F, 2.0F, 0.5F);
        //Candy filling
        this.headIn = (new ModelRenderer(this)).setTextureSize(texHight, texWidth);
        this.headIn.setRotationPoint(0.0F, -7.0F, -2.0F);
        this.headIn.setTextureOffset(0, 18).addBox(-4.0F, -11.9F, -5.5F, 8.0F, 10.0F, 8.0F, 0.0F);
        //Nose - Candy Filling
        this.headIn.setTextureOffset(24, 0).addBox(-1.0F, -4.9F, -7.49F, 2.0F, 4.0F, 2.0F, 0.0F);

        /**Body*/
        //Slime
        this.body = (new ModelRenderer(this)).setTextureSize(texHight, texWidth);
        this.body.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.body.setTextureOffset(0, 40).addBox(-9.0F, -2.0F, -6.0F, 18.0F, 12.0F, 11.0F, 0.6F);
        this.body.setTextureOffset(0, 70).addBox(-4.5F, 10.0F, -3.0F, 9.0F, 5.0F, 6.0F, 1.0F);
        //Candy filling
        this.bodyIn = (new ModelRenderer(this)).setTextureSize(texHight, texWidth);
        this.bodyIn.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.bodyIn.setTextureOffset(0, 86).addBox(-9.0F, -2.0F, -6.0F, 18.0F, 12.0F, 11.0F, 0.0F);
        this.bodyIn.setTextureOffset(0, 110).addBox(-4.5F, 10.0F, -3.0F, 9.0F, 5.0F, 6.0F, 0.6F);


        /**Right Arm*/
        //Slime
        this.rArm = (new ModelRenderer(this)).setTextureSize(texHight, texWidth);
        this.rArm.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.rArm.setTextureOffset(60, 21).addBox(-12.99F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F, 0.5F);
        //Candy Filling
        this.rArmIn = (new ModelRenderer(this)).setTextureSize(texHight, texWidth);
        this.rArmIn.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.rArmIn.setTextureOffset(106, 21).addBox(-12.99F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F, 0.0F);

        /**Left Arm*/
        //Slime
        this.lArm = (new ModelRenderer(this)).setTextureSize(texHight, texWidth);
        this.lArm.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.lArm.setTextureOffset(60, 58).addBox(8.99F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F, 0.5F);
        //Candy Filling
        this.lArmIn = (new ModelRenderer(this)).setTextureSize(texHight, texWidth);
        this.lArmIn.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.lArmIn.setTextureOffset(106, 58).addBox(8.99F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F, 0.0F);


        /**Right Leg*/
        //Slime
        this.rLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(texHight, texWidth);
        this.rLeg.mirror = true;
        this.rLeg.setTextureOffset(60, 0).setRotationPoint(5.0F, 11.0F, 0.0F);
        this.rLeg.addBox(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F, 0.5F);
        //Candy Filling
        this.rLegIn = (new ModelRenderer(this, 0, 22)).setTextureSize(texHight, texWidth);
        this.rLegIn.mirror = true;
        this.rLegIn.setTextureOffset(106, 0).setRotationPoint(5.0F, 11.0F, 0.0F);
        this.rLegIn.addBox(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F, 0.0F);

        /**Left Leg*/
        //Slime
        this.lLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(texHight, texWidth);
        this.lLeg.setRotationPoint(-4.0F, 11.0F, 0.0F);
        this.lLeg.setTextureOffset(37, 0).addBox(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F, 0.5F);
        //CandyFilling
        this.lLegIn = (new ModelRenderer(this, 0, 22)).setTextureSize(texHight, texWidth);
        this.lLegIn.setRotationPoint(-4.0F, 11.0F, 0.0F);
        this.lLegIn.setTextureOffset(83, 0).addBox(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F, 0.0F);
    }

    public Iterable<ModelRenderer> getCandyFilling() {
        return ImmutableList.of(this.headIn, this.bodyIn, this.lArmIn, this.rArmIn, this.lLegIn, this.rLegIn);
    }

    public Iterable<ModelRenderer> getJellyCoating() {
        return ImmutableList.of(this.head, this.body, this.lLeg, this.rLeg, this.rArm, this.lArm);
    }

    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.head, this.headIn, this.body, this.bodyIn, this.lLeg, this.lLegIn, this.rLeg, this.rLegIn, this.rArm, this.rArmIn, this.lArm, this.lArmIn);
    }

    /**
     * Sets this entity's model rotation angles
     */
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        this.head.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        this.lLeg.rotateAngleX = -1.5F * MathHelper.func_233021_e_(limbSwing, 13.0F) * limbSwingAmount;
        this.rLeg.rotateAngleX = 1.5F * MathHelper.func_233021_e_(limbSwing, 13.0F) * limbSwingAmount;
        this.lLeg.rotateAngleY = 0.0F;
        this.rLeg.rotateAngleY = 0.0F;
    }

    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        int i = entityIn.getAttackTimer();
        if (i > 0) {
            this.rArm.rotateAngleX = -2.0F + 1.5F * MathHelper.func_233021_e_((float) i - partialTick, 10.0F);
            this.lArm.rotateAngleX = -2.0F + 1.5F * MathHelper.func_233021_e_((float) i - partialTick, 10.0F);
        } else {
            this.rArm.rotateAngleX = (-0.2F + 1.5F * MathHelper.func_233021_e_(limbSwing, 13.0F)) * limbSwingAmount;
            this.lArm.rotateAngleX = (-0.2F - 1.5F * MathHelper.func_233021_e_(limbSwing, 13.0F)) * limbSwingAmount;

        }

    }
}
