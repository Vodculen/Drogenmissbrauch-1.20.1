package net.vodculen.drogenmissbrauch.item.consumable;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.vodculen.drogenmissbrauch.util.Ticks;
import net.vodculen.drogenmissbrauch.util.UneasyableEntity;


public class GeschwindRubble extends Item {
	public GeschwindRubble(Settings settings) {
		super(settings);
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if (!world.isClient && user instanceof PlayerEntity player && player instanceof UneasyableEntity uneasy && !uneasy.isUneasy()) {
			player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, Ticks.getMinutes(2), 1));
			player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, Ticks.getMinutes(2), 1));

			uneasy.setUneasy(Ticks.getMinutes(5));

			player.getItemCooldownManager().set(this, Ticks.getSeconds(5));
		}

		return super.finishUsing(stack, world, user);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		
		if (itemStack.getDamage() >= itemStack.getMaxDamage() && user instanceof UneasyableEntity uneasy && uneasy.isUneasy() && user.hasStatusEffect(StatusEffects.HUNGER)) {
			return TypedActionResult.fail(itemStack);
		} else {
			user.setCurrentHand(hand);
			return TypedActionResult.consume(itemStack);
		}
	}
}
