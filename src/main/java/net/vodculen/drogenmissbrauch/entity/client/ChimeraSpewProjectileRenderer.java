package net.vodculen.drogenmissbrauch.entity.client;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.vodculen.drogenmissbrauch.Drogenmissbrauch;
import net.vodculen.drogenmissbrauch.entity.custom.ChimeraSpewProjectileEntity;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;


public class ChimeraSpewProjectileRenderer extends EntityRenderer<ChimeraSpewProjectileEntity> {
    protected ChimeraSpewProjectileModel model;

    public ChimeraSpewProjectileRenderer(Context ctx) {
        super(ctx);
        this.model = new ChimeraSpewProjectileModel(ctx.getPart(ChimeraSpewProjectileModel.CHIMERA_SPEW));
    }

    @Override
    public void render(ChimeraSpewProjectileEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

		matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()) - 90.0F));
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch()) + 90.0F));
		VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(
			vertexConsumers, this.model.getLayer(this.getTexture(entity)), false, false
		);
		this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(ChimeraSpewProjectileEntity entity) {
        return Identifier.of(Drogenmissbrauch.MOD_ID, "textures/entity/kunai/kunai.png");
    }
}
