package net.vodculen.drogenmissbrauch.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.vodculen.drogenmissbrauch.Drogenmissbrauch;
import net.vodculen.drogenmissbrauch.enchantment.custom.SelfSacrificeEnchantment;

public class ModEnchantments {
	public static final Enchantment SELF_SACRIFICE = registerEnchantment("self_sacrifice", new SelfSacrificeEnchantment());

	// Below are helper classes that make defining Enchantments easier as well as making them accessible to the entry class
    private static Enchantment registerEnchantment(String name, Enchantment enchantment) {
		return Registry.register(Registries.ENCHANTMENT, Identifier.of(Drogenmissbrauch.MOD_ID, name), enchantment);
	}

	public static void registerModEnchantments() {
		Drogenmissbrauch.LOGGER.info("Registering Modded Enchantments");
	}
}
