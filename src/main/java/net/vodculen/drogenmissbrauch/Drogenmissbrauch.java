package net.vodculen.drogenmissbrauch;

import net.fabricmc.api.ModInitializer;
import net.vodculen.drogenmissbrauch.block.ModBlocks;
import net.vodculen.drogenmissbrauch.enchantment.ModEnchantments;
import net.vodculen.drogenmissbrauch.item.ModItems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Drogenmissbrauch implements ModInitializer {
	public static final String MOD_ID = "drogenmissbrauch";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModEnchantments.registerModEnchantments();
	}
}