package net.vodculen.drogenmissbrauch.util;

import com.google.common.base.Predicate;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.vodculen.drogenmissbrauch.block.ModBlocks;

public class WeaponUtils {
	public static ItemStack getProjectileTypeForWeapon(ItemStack weapon, PlayerEntity player) {
		Predicate<ItemStack> isValidAmmo;

		if (weapon.isOf(Items.GOAT_HORN)) {
			isValidAmmo = stack -> stack.isOf(ModBlocks.SUGAR_BLOCK.asItem());
		} else {
			return ItemStack.EMPTY;
		}

		for (int i = 0; i < player.getInventory().size(); i++) {
			ItemStack stack = player.getInventory().getStack(i);

			if (!stack.isEmpty() && isValidAmmo.test(stack)) {
				return stack;
			}
		}

		return ItemStack.EMPTY;
}
}
