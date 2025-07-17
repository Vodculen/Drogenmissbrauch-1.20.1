package net.vodculen.drogenmissbrauch.entity.client;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.vodculen.drogenmissbrauch.Drogenmissbrauch;
import net.vodculen.drogenmissbrauch.entity.custom.ChimeraSpewProjectileEntity;

public class ChimeraSpewProjectileModel extends EntityModel<ChimeraSpewProjectileEntity> {
    public static final EntityModelLayer CHIMERA_SPEW = new EntityModelLayer(Identifier.of(Drogenmissbrauch.MOD_ID, "chimera_spew"), "main");
    private final ModelPart chimera_spew;

	public ChimeraSpewProjectileModel(ModelPart root) {
		this.chimera_spew = root.getChild("chimera_spew");
	}
	
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData chimera_spew = modelPartData.addChild("chimera_spew", ModelPartBuilder.create().uv(10, 8).cuboid(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.1F))
		.uv(10, 11).cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, 0.0F, 1.5F, 0.0F, 1.5708F, -1.5708F));

		ModelPartData cube_r1 = chimera_spew.addChild("cube_r1", ModelPartBuilder.create().uv(0, 14).cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.2F)), ModelTransform.of(0.0F, 0.0F, -3.0F, 0.0F, -0.7854F, 0.0F));

		ModelPartData cube_r2 = chimera_spew.addChild("cube_r2", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -0.5F, -3.8F, 1.0F, 2.0F, 4.0F, new Dilation(-0.5F)), ModelTransform.of(-0.5F, -1.0F, -0.5F, 0.0F, 0.3491F, 0.0F));

		ModelPartData cube_r3 = chimera_spew.addChild("cube_r3", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -0.5F, -1.5F, 8.0F, 0.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void setAngles(ChimeraSpewProjectileEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		chimera_spew.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}