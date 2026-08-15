package cz.maxtechnik.sfox.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
public class FoxModel<T extends LivingEntity> extends HumanoidModel<T>{
	public static final ModelLayerLocation LAYER_LOCATION=new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("sfox","fox_model"),"main");
	public final ModelPart Head;
	public FoxModel(ModelPart root){
		super(root);
		this.Head=root.getChild("head");
	}
	public static LayerDefinition createBodyLayer(){
		MeshDefinition meshdefinition=new MeshDefinition();
		PartDefinition partdefinition=meshdefinition.getRoot();
		partdefinition.addOrReplaceChild("body",CubeListBuilder.create(),PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_arm",CubeListBuilder.create(),PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_arm",CubeListBuilder.create(),PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_leg",CubeListBuilder.create(),PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_leg",CubeListBuilder.create(),PartPose.ZERO);
		partdefinition.addOrReplaceChild("hat",CubeListBuilder.create(),PartPose.ZERO);
		PartDefinition head=partdefinition.addOrReplaceChild("head",CubeListBuilder.create().texOffs(17,16).addBox(-3.0F,-14.0F,-4.0F,6.0F,6.0F,11.0F,new CubeDeformation(0.0F))
				.texOffs(0,8).addBox(-4.0F,-13.0F,-10.0F,8.0F,6.0F,6.0F,new CubeDeformation(0.0F))
				.texOffs(0,20).addBox(-2.0F,-9.0F,-13.0F,4.0F,1.0F,3.0F,new CubeDeformation(0.0F)),PartPose.offset(0.0F,0.0F,0.0F));
		head.addOrReplaceChild("foot_4_r1",CubeListBuilder.create().texOffs(0,25).mirror().addBox(-1.0F,-5.0F,-1.0F,2.0F,6.0F,2.0F,new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0,25).addBox(5.5F,-5.0F,-1.0F,2.0F,6.0F,2.0F,new CubeDeformation(0.0F)),PartPose.offsetAndRotation(-3.25F,-5.0F,-5.0F,-0.4363F,0.0F,0.0F));
		head.addOrReplaceChild("foot_3_r1",CubeListBuilder.create().texOffs(0,25).mirror().addBox(-1.0F,-0.8F,-1.0F,2.0F,6.0F,2.0F,new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0,25).addBox(5.5F,-0.8F,-1.0F,2.0F,6.0F,2.0F,new CubeDeformation(0.0F)),PartPose.offsetAndRotation(-3.25F,-9.0F,4.0F,0.6545F,0.0F,0.0F));
		head.addOrReplaceChild("bottom_mouth_r1",CubeListBuilder.create().texOffs(14,20).addBox(-2.0F,-0.3F,-3.9F,4.0F,1.0F,3.0F,new CubeDeformation(0.0F)),PartPose.offsetAndRotation(0.0F,-8.0F,-9.0F,0.3054F,0.0F,0.0F));
		head.addOrReplaceChild("ear_2_r1",CubeListBuilder.create().texOffs(0,11).addBox(-1.0F,-1.8F,0.0F,2.0F,2.0F,1.0F,new CubeDeformation(0.0F)),PartPose.offsetAndRotation(-3.0F,-13.0F,-9.0F,-0.1309F,0.1745F,-0.0873F));
		head.addOrReplaceChild("ear_1_r1",CubeListBuilder.create().texOffs(0,11).mirror().addBox(-1.0F,-1.8F,0.0F,2.0F,2.0F,1.0F,new CubeDeformation(0.0F)).mirror(false),PartPose.offsetAndRotation(3.0F,-13.0F,-9.0F,-0.308F,-0.2161F,0.1007F));
		head.addOrReplaceChild("tail_r1",CubeListBuilder.create().texOffs(25,0).addBox(-2.0F,-4.2F,0.0F,4.0F,5.0F,9.0F,new CubeDeformation(0.0F)),PartPose.offsetAndRotation(0.0F,-10.0F,5.0F,-0.6545F,0.0F,0.0F));
		return LayerDefinition.create(meshdefinition,51,33);
	}
	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack,@NotNull VertexConsumer vertexConsumer,int packedLight,int packedOverlay,int color){
		this.head.render(poseStack,vertexConsumer,packedLight,packedOverlay,color);
	}
	@Override
	public void setupAnim(@NotNull T entity,float limbSwing,float limbSwingAmount,float ageInTicks,float netHeadYaw,float headPitch){
		super.setupAnim(entity,limbSwing,limbSwingAmount,ageInTicks,netHeadYaw,headPitch);
	}
}