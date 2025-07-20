package net.vodculen.drogenmissbrauch.enchantment;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

public class ModEnchantmentHelper extends EnchantmentHelper {
	public static int getSelfSacrifice(ItemStack stack) {
		return getLevel(ModEnchantments.SELF_SACRIFICE, stack);
	}

	public static int getBukkehorn(ItemStack stack) {
		return getLevel(ModEnchantments.BUKKEHORN, stack);
	}
}
