package net.vodculen.drogenmissbrauch.item.consumable;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public class Incantationum extends Item {
	public Incantationum(Settings settings) {
		super(settings);
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if (!world.isClient) {
			double roll = Math.random();

			if (roll < 0.3) {
				user.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 500, 1));
			} else {
				user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 400, 1));
				user.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 6000, 0));
				user.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 6000, 0));
				user.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 3));
			}
		}

		return super.finishUsing(stack, world, user);
	}
}
