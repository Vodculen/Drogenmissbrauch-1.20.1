package net.vodculen.drogenmissbrauch.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.vodculen.drogenmissbrauch.block.ModBlocks;
import net.vodculen.drogenmissbrauch.block.custom.ChimeraSlewBlock;
import net.vodculen.drogenmissbrauch.entity.ModEntities;
import net.vodculen.drogenmissbrauch.util.PerlinNoise;


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
		BlockPos blockPos = this.getBlockPos();

		placeChimeraSpewPile(world, blockPos, 2);
		
		this.discard();

		super.onBlockHit(blockHitResult);
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		World world = this.getWorld();
		BlockPos blockPos = this.getBlockPos();

		placeChimeraSpewPile(world, blockPos, 2);

		this.discard();

		super.onEntityHit(entityHitResult);
	}


	@Override
	protected void initDataTracker() { }

	public void placeChimeraSpewPile(World world, BlockPos center, int radius) {
		long seed = 12345L;
		PerlinNoise noise = new PerlinNoise(seed);

		for (int dx = -radius; dx <= radius; dx++) {
			for (int dz = -radius; dz <= radius; dz++) {
				double distance = Math.sqrt(dx * dx + dz * dz);
				if (distance <= radius + 0.5) {
					double nx = (center.getX() + dx) * 0.4;
					double nz = (center.getZ() + dz) * 0.4;
					double noiseValue = noise.octaveNoise(nx, nz, 6, 0.5);

					double normalized = (noiseValue + 1) / 2;
					int layersToPlace = (int) (normalized * 4);

					if (layersToPlace == 0) {
						continue;
					}

					BlockPos basePos = center.add(dx, 0, dz);
					BlockPos currentPos = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, basePos);

					while (world.getBlockState(currentPos).isOf(ModBlocks.CHIMERA_SLEW)) {
						currentPos = currentPos.up();
					}

					currentPos = currentPos.down();

					while (layersToPlace > 0) {
						BlockState state = world.getBlockState(currentPos);

						if (state.isOf(ModBlocks.CHIMERA_SLEW)) {
							int currentLayers = state.get(ChimeraSlewBlock.LAYERS);
							if (currentLayers < 8) {
								int addable = Math.min(8 - currentLayers, layersToPlace);
								world.setBlockState(currentPos, state.with(ChimeraSlewBlock.LAYERS, currentLayers + addable));
								layersToPlace -= addable;
								break;
							} else {
								currentPos = currentPos.up();
							}
						} 
						else if (state.isAir() || state.isReplaceable()) {
							int addable = Math.min(8, layersToPlace);
							world.setBlockState(currentPos, ModBlocks.CHIMERA_SLEW.getDefaultState().with(ChimeraSlewBlock.LAYERS, addable));
							layersToPlace -= addable;
							break;
						} else {
							currentPos = currentPos.up();
						}
					}
				}
			}
		}
	}
}
