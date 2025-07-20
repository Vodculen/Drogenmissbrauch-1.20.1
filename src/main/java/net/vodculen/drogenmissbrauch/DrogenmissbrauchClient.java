package net.vodculen.drogenmissbrauch;

import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.vodculen.drogenmissbrauch.block.ModBlocks;
import net.vodculen.drogenmissbrauch.entity.ModEntities;
import net.vodculen.drogenmissbrauch.entity.client.ChimeraSpewProjectileModel;
import net.vodculen.drogenmissbrauch.entity.client.ChimeraSpewProjectileRenderer;
import net.vodculen.drogenmissbrauch.item.ModItems;

public class DrogenmissbrauchClient implements ClientModInitializer {
	private static final ManagedShaderEffect INVERT_SHADER = ShaderEffectManager.getInstance().manage(Identifier.of(Drogenmissbrauch.MOD_ID, "shaders/post/invert.json"));
	private static boolean enabled = true;
	
	public static void registerModelPredicateProviders() {
		ModelPredicateProviderRegistry.register(ModItems.NEBULA, new Identifier("smoking"), (itemStack, clientWorld, livingEntity, seed) -> {
			if (livingEntity == null) {
				return 0.0F;
			}
			
			return livingEntity.getActiveItem() != itemStack ? 0.0F : 1.0F;
		});

		ModelPredicateProviderRegistry.register(ModItems.MARZIPAN_AXEBLADE, new Identifier("chewed"), (itemStack, clientWorld, livingEntity, seed) -> {
			return itemStack.getOrCreateNbt().getInt("TimesEaten") / 4.0F;
		});
	}

	@Override
	public void onInitializeClient() {
		registerModelPredicateProviders();

		EntityModelLayerRegistry.registerModelLayer(ChimeraSpewProjectileModel.CHIMERA_SPEW, ChimeraSpewProjectileModel::getTexturedModelData);
		EntityRendererRegistry.register(ModEntities.CHIMERA_SPEW, ChimeraSpewProjectileRenderer::new);

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player == null || client.world == null) {
				enabled = false;

				return;
			}

			Vec3d head = client.player.getEyePos();

			BlockPos pos = client.player.getBlockPos();
			BlockPos upperPos = BlockPos.ofFloored(head);
			if (client.world.getBlockState(pos).isOf(ModBlocks.CHIMERA_SLEW) || client.world.getBlockState(upperPos).isOf(ModBlocks.CHIMERA_SLEW)) {
				enabled = true;
			} else {
				enabled = false;
			}
		});

		ShaderEffectRenderCallback.EVENT.register(tickDelta -> {
			if (enabled) {
				INVERT_SHADER.render(tickDelta);
			}
		});
	}
}
