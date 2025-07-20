package net.vodculen.drogenmissbrauch.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.vodculen.drogenmissbrauch.Drogenmissbrauch;
import net.vodculen.drogenmissbrauch.item.consumable.GeschwindRubble;
import net.vodculen.drogenmissbrauch.item.consumable.Incantationum;
import net.vodculen.drogenmissbrauch.item.consumable.ModFoodComponents;
import net.vodculen.drogenmissbrauch.item.consumable.Nebula;
import net.vodculen.drogenmissbrauch.item.custom.MarzipanClaymoreItem;

public class ModItems {
	public static final Item NEBULA = registerItem("nebula", new Nebula(new Item.Settings().maxDamage(128)));
	public static final Item LEVITARE = registerItem("levitare", new Item(new FabricItemSettings().food(ModFoodComponents.LEVITARE)));
	public static final Item INCANTATIONUM = registerItem("incantationum", new Incantationum(new Item.Settings().food(
		new FoodComponent.Builder().hunger(4).saturationModifier(1.2F).alwaysEdible().build())));
	public static final Item GESCHWIND_RUBBLE = registerItem("geschwind_rubble", new GeschwindRubble(new Item.Settings().food(
		new FoodComponent.Builder().hunger(2).saturationModifier(1.2F).alwaysEdible().build())));
	public static final Item MARZIPAN_CLAYMORE = registerItem("marzipan_claymore", new MarzipanClaymoreItem(new Item.Settings().maxDamage(256).food(new FoodComponent.Builder().alwaysEdible().build())));
	

	private static Item registerItem(String name, Item item) {
		return Registry.register(Registries.ITEM, Identifier.of(Drogenmissbrauch.MOD_ID, name), item);
	}

	public static void registerModItems() {
		Drogenmissbrauch.LOGGER.info("Registering Mod Items for " + Drogenmissbrauch.MOD_ID);
	}
}
