package net.vodculen.drogenmissbrauch.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.vodculen.drogenmissbrauch.Drogenmissbrauch;
import net.vodculen.drogenmissbrauch.entity.custom.ChimeraSpewProjectileEntity;

public class ModEntities {
	public static final EntityType<ChimeraSpewProjectileEntity> CHIMERA_SPEW = registerEntity(
		"chimera_spew",
		EntityType.Builder.<ChimeraSpewProjectileEntity>create(ChimeraSpewProjectileEntity::new, SpawnGroup.MISC)
			.setDimensions(0.1F, 0.1F)
			.build("chimera_spew")
	);


	// Below are helper classes that make defining Items easier as well as making them accessible to the entry class
	private static <T extends Entity> EntityType<T> registerEntity(String name, EntityType<T> entity) {
		return Registry.register(Registries.ENTITY_TYPE, Identifier.of(Drogenmissbrauch.MOD_ID, name), entity);
	}

	public static void registerModEntityTypes() {
		Drogenmissbrauch.LOGGER.info("Registering Modded Entities");
	}
}
