package net.vodculen.drogenmissbrauch.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
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
	public static final Item MARZIPAN_AXEBLADE = registerItem("marzipan_axeblade", new MarzipanClaymoreItem(new Item.Settings().maxDamage(256).rarity(Rarity.UNCOMMON)));
	
	// Below are helper classes that make defining Items easier as well as making them accessible to the entry class
	private static Item registerItem(String name, Item item) {
		return Registry.register(Registries.ITEM, Identifier.of(Drogenmissbrauch.MOD_ID, name), item);
	}

	public static void registerModItems() {
		Drogenmissbrauch.LOGGER.info("Registering Mod Items for " + Drogenmissbrauch.MOD_ID);

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(ModItems::foodItemGroup);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(ModItems::combatItemGroup);
	}


	// Item Groups
	private static void foodItemGroup(FabricItemGroupEntries entires) {
		entires.addAfter(Items.GOLDEN_APPLE, ModItems.INCANTATIONUM);
		entires.addAfter(Items.CHORUS_FRUIT, ModItems.LEVITARE);
		entires.addAfter(Items.PUMPKIN_PIE, ModItems.GESCHWIND_RUBBLE);
		entires.addAfter(ModItems.GESCHWIND_RUBBLE, ModItems.NEBULA);
		entires.addAfter(ModItems.NEBULA, ModItems.MARZIPAN_AXEBLADE);
	}

	private static void combatItemGroup(FabricItemGroupEntries entires) {
		entires.addAfter(Items.TRIDENT, ModItems.MARZIPAN_AXEBLADE);
	}

}
