package net.vodculen.drogenmissbrauch.block;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.vodculen.drogenmissbrauch.Drogenmissbrauch;
import net.vodculen.drogenmissbrauch.block.custom.ChimeraSlewBlock;
import net.vodculen.drogenmissbrauch.block.custom.FrostedFrothBlock;

public class ModBlocks {
	public static final Block CHIMERA_SLEW = registerBlock("chimera_slew", new ChimeraSlewBlock(Settings.create().strength(0.5F).sounds(BlockSoundGroup.POWDER_SNOW).dynamicBounds().solidBlock(Blocks::never).blockVision((state, world, pos) -> {
        return (Integer)state.get(ChimeraSlewBlock.LAYERS) >= 8;
    })));
	public static final Block FROSTED_FROTH = registerBlock("frosted_froth", new FrostedFrothBlock(Settings.create()));
	public static final Block SUGAR_BLOCK = registerBlock("sugar_block", new Block(Settings.create()));
		

	// Below are helper classes that make defining Blocks easier as well as making them accessible to the entry class
	private static Block registerBlock(String name, Block block) {
		registerModBlockItem(name, block);
		return Registry.register(Registries.BLOCK, Identifier.of(Drogenmissbrauch.MOD_ID, name), block);
	}

	private static void registerModBlockItem(String name, Block block) {
		Registry.register(Registries.ITEM, Identifier.of(Drogenmissbrauch.MOD_ID, name), new BlockItem(block, new Item.Settings()));
	}

	public static void registerModBlocks() {
		Drogenmissbrauch.LOGGER.info("Registering Mod Block for " + Drogenmissbrauch.MOD_ID);
	}
}
