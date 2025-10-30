package cz.maxtechnik.sfox.client.model;
// Made with Blockbench 5.0.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class FoxModel<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("sfox", "fox_model"), "main");
    public final ModelPart Head;

    public FoxModel(ModelPart root) {
        this.Head = root.getChild("Head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(17, 16).addBox(-3.0F, -14.0F, -4.0F, 6.0F, 6.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(-4.0F, -13.0F, -10.0F, 8.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 20).addBox(-2.0F, -9.0F, -13.0F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition foot_4_r1 = Head.addOrReplaceChild("foot_4_r1", CubeListBuilder.create().texOffs(0, 25).mirror().addBox(-1.0F, -5.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 25).addBox(5.5F, -5.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.25F, -5.0F, -5.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition foot_3_r1 = Head.addOrReplaceChild("foot_3_r1", CubeListBuilder.create().texOffs(0, 25).mirror().addBox(-1.0F, -0.8F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 25).addBox(5.5F, -0.8F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.25F, -9.0F, 4.0F, 0.6545F, 0.0F, 0.0F));

        PartDefinition bottom_mouth_r1 = Head.addOrReplaceChild("bottom_mouth_r1", CubeListBuilder.create().texOffs(14, 20).addBox(-2.0F, -0.3F, -3.9F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, -9.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition ear_2_r1 = Head.addOrReplaceChild("ear_2_r1", CubeListBuilder.create().texOffs(0, 11).addBox(-1.0F, -1.8F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -13.0F, -9.0F, -0.1309F, 0.1745F, -0.0873F));

        PartDefinition ear_1_r1 = Head.addOrReplaceChild("ear_1_r1", CubeListBuilder.create().texOffs(0, 11).mirror().addBox(-1.0F, -1.8F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.0F, -13.0F, -9.0F, -0.308F, -0.2161F, 0.1007F));

        PartDefinition tail_r1 = Head.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(25, 0).addBox(-2.0F, -4.2F, 0.0F, 4.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.0F, 5.0F, -0.6545F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 51, 33);
    }
    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.Head.yRot = netHeadYaw / (180F / (float) Math.PI);
        this.Head.xRot = headPitch / (180F / (float) Math.PI);
    }

}