package com.kinomora.slimegolem.render.model;

import com.google.common.collect.ImmutableList;
import com.kinomora.slimegolem.entity.SlimeGolemEntity;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SlimeGolemModel extends ListModel<SlimeGolemEntity> {
    private final ModelPart slimeHead;
    private final ModelPart slimeMiddle;
    private final ModelPart slimeLower;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rockyHead;
    private final ModelPart rockyMiddle;
    private final ModelPart rockyLower;
    public SlimeGolemEntity slimeGolemEntity;

    public SlimeGolemModel(ModelPart part){
        this.slimeHead = part.getChild("slimeHead");
        this.slimeMiddle = part.getChild("slimeMiddle");
        this.slimeLower = part.getChild("slimeLower");

        this.rockyHead = part.getChild("rockyHead");
        this.rockyMiddle = part.getChild("rockyMiddle");
        this.rockyLower = part.getChild("rockyLower");

        this.leftArm = part.getChild("leftArm");
        this.rightArm = part.getChild("rightArm");
    }

    public static LayerDefinition createBodyLayer(){
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        CubeDeformation deform = new CubeDeformation(-0.5F);

        root.addOrReplaceChild("slimeHead", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -7.90F, -4.0F, 8.0F, 8.0F, 8.0F, deform), PartPose.offset(0.0F, 4.0F, 0.0F));
        root.addOrReplaceChild("slimeMiddle", CubeListBuilder.create().texOffs(0, 16).addBox(-5.0F, -9.95F, -5.0F, 10.0F, 10.0F, 10.0F, deform), PartPose.offset(0.0F, 13.0F, 0.0F));
        root.addOrReplaceChild("slimeLower", CubeListBuilder.create().texOffs(0, 36).addBox(-6.0F, -12.0F, -6.0F, 12.0F, 12.0F, 12.0F, deform), PartPose.offset(0.0F, 24.0F, 0.0F));

        root.addOrReplaceChild("rockyHead", CubeListBuilder.create().texOffs(0,60).addBox(-3.0F, -6.90F, -3.0F, 6.0F, 6.0F, 6.0F, deform), PartPose.offset(0.0F, 4.0F, 0.0F));
        root.addOrReplaceChild("rockyMiddle", CubeListBuilder.create().texOffs(0, 72).addBox(-4.0F, -8.95F, -4.0F, 8.0F, 8.0F, 8.0F, deform), PartPose.offset(0.0F, 13.0F, 0.0F));
        root.addOrReplaceChild("rockyLower", CubeListBuilder.create().texOffs(0, 88).addBox(-5.0F, -11.0F, -5.0F, 10.0F, 10.0F, 10.0F, deform), PartPose.offset(0.0F, 24.0F, 0.0F));

        root.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(32,0).addBox(-1.0F, 0.05F, -1.0F, 12.0F, 2.0F, 2.0F, deform), PartPose.offset(0.0F, 6.0F, 0.0F));
        root.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(32,0).addBox(-1.0F, 0.05F, -1.0F, 12.0F, 2.0F, 2.0F, deform), PartPose.offset(0.0F, 6.0F, 0.0F));

        return LayerDefinition.create(mesh, 64, 128);
    }

    //Old Constructor
    /*public SlimeGolemModel() {
        float f = 4.0F;
        float f1 = 0.0F;
        //Head - normal
        this.head = (new ModelPart(this, 0, 0)).setTexSize(64, 128);
        this.head.addBox(-4.0F, -7.90F, -4.0F, 8.0F, 8.0F, 8.0F, -0.5F);
        this.head.setPos(0.0F, 4.0F, 0.0F);
        //Head - rocky
        this.rockyHeadInner = (new ModelPart(this,0,60)).setTexSize(64,128);
        this.rockyHeadInner.addBox(-3.0F, -6.90F, -3.0F, 6.0F, 6.0F, 6.0F, -0.5F);
        this.rockyHeadInner.setPos(0.0F, 4.0F, 0.0F);

        //Middle - normal
        this.bodyMiddle = (new ModelPart(this, 0, 16)).setTexSize(64, 128);
        this.bodyMiddle.addBox(-5.0F, -9.95F, -5.0F, 10.0F, 10.0F, 10.0F, -0.5F);
        this.bodyMiddle.setPos(0.0F, 13.0F, 0.0F);
        //Middle - rocky
        this.rockyBodyMiddleInner = (new ModelPart(this, 0, 72)).setTexSize(64, 128);
        this.rockyBodyMiddleInner.addBox(-4.0F, -8.95F, -4.0F, 8.0F, 8.0F, 8.0F, -0.5F);
        this.rockyBodyMiddleInner.setPos(0.0F, 13.0F, 0.0F);

        //Lower - normal
        this.bottomBody = (new ModelPart(this, 0, 36)).setTexSize(64, 128);
        this.bottomBody.addBox(-6.0F, -12.0F, -6.0F, 12.0F, 12.0F, 12.0F, -0.5F);
        this.bottomBody.setPos(0.0F, 24.0F, 0.0F);
        //Lower - rocky
        this.rockyBodyBottomInner = (new ModelPart(this, 0, 88)).setTexSize(64, 128);
        this.rockyBodyBottomInner.addBox(-5.0F, -11.0F, -5.0F, 10.0F, 10.0F, 10.0F, -0.5F);
        this.rockyBodyBottomInner.setPos(0.0F, 24.0F, 0.0F);


        //Right arm
        this.rightArm = (new ModelPart(this, 32, 0)).setTexSize(64, 128);
        this.rightArm.addBox(-1.0F, 0.05F, -1.0F, 12.0F, 2.0F, 2.0F, -0.5F);
        this.rightArm.setPos(0.0F, 6.0F, 0.0F);
        //Left arm
        this.leftArm = (new ModelPart(this, 32, 0)).setTexSize(64, 128);
        this.leftArm.addBox(-1.0F, 0.05F, -1.0F, 12.0F, 2.0F, 2.0F, -0.5F);
        this.leftArm.setPos(0.0F, 6.0F, 0.0F);
    }*/

    @Override
    public void prepareMobModel(SlimeGolemEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.rockyHead.visible = entityIn.isRocky();
        this.rockyMiddle.visible = entityIn.isRocky();
        this.rockyLower.visible = entityIn.isRocky();
        super.prepareMobModel(entityIn, limbSwing, limbSwingAmount, partialTick);
    }

    /**
     * Sets this entity's model rotation angles
     */
    public void setupAnim(SlimeGolemEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //normal handling
        this.slimeHead.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.slimeHead.xRot = headPitch * ((float)Math.PI / 180F);
        this.slimeMiddle.yRot = netHeadYaw * ((float)Math.PI / 180F) * 0.25F;
        float normalSIN = Mth.sin(this.slimeMiddle.yRot);
        float normalCOS = Mth.cos(this.slimeMiddle.yRot);

        //rockyInner handling
        this.rockyHead.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.rockyHead.xRot = headPitch * ((float)Math.PI / 180F);
        this.rockyMiddle.yRot = netHeadYaw * ((float)Math.PI / 180F) * 0.25F;


        //Arm handling
        this.rightArm.zRot = 1.0F;
        this.leftArm.zRot = -1.0F;
        this.rightArm.yRot = 0.0F + this.slimeMiddle.yRot;
        this.leftArm.yRot = (float)Math.PI + this.slimeMiddle.yRot;
        this.rightArm.x = normalCOS * 5.0F;
        this.rightArm.z = -normalSIN * 5.0F;
        this.leftArm.x = -normalCOS * 5.0F;
        this.leftArm.z = normalSIN * 5.0F;
    }

    public Iterable<ModelPart> parts() {
        return ImmutableList.of(this.slimeMiddle, this.slimeLower, this.slimeHead, this.rightArm, this.leftArm, this.rockyLower, this.rockyMiddle, this.rockyHead);
    }

    public ModelPart getSlimeHead() {
        return this.slimeHead;
    }
}