package net.vodculen.drogenmissbrauch.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.UndergroundConfiguredFeatures;
import net.vodculen.drogenmissbrauch.entity.ModEntities;


public class ChimeraSpewProjectileEntity extends ThrownEntity {
	public ChimeraSpewProjectileEntity(EntityType<? extends ThrownEntity> entityType, World world) {
		super(entityType, world);
	}

	public ChimeraSpewProjectileEntity(World world, LivingEntity owner) {
		super(ModEntities.CHIMERA_SPEW, owner, world);
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		World world = this.getWorld();
		Random random = this.getWorld().random;
		BlockPos blockPos = this.getBlockPos();

		if (world instanceof ServerWorld serverWorld) {
			serverWorld.getRegistryManager().getOptional(RegistryKeys.CONFIGURED_FEATURE).flatMap((registry) -> {
				return registry.getEntry(UndergroundConfiguredFeatures.MOSS_PATCH_BONEMEAL);
			}).ifPresent((reference) -> {
				reference.value().generate(serverWorld, serverWorld.getChunkManager().getChunkGenerator(), random, blockPos.up());
			});
		}

		this.discard();

		super.onBlockHit(blockHitResult);
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		this.discard();

		World world = this.getWorld();
		Random random = this.getWorld().random;
		BlockPos blockPos = this.getBlockPos();

		if (world instanceof ServerWorld serverWorld) {
			serverWorld.getRegistryManager().getOptional(RegistryKeys.CONFIGURED_FEATURE).flatMap((registry) -> {
				return registry.getEntry(UndergroundConfiguredFeatures.MOSS_PATCH_BONEMEAL);
			}).ifPresent((reference) -> {
				reference.value().generate(serverWorld, serverWorld.getChunkManager().getChunkGenerator(), random, blockPos.up());
			});
		}

		super.onEntityHit(entityHitResult);
	}


	@Override
	protected void initDataTracker() { }
}
