package net.vodculen.drogenmissbrauch.item.consumable;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoodComponents {
	public static final FoodComponent LEVITARE = new FoodComponent.Builder().hunger(4).saturationModifier(1.2F).statusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 900, 1), 1.0F).alwaysEdible().build();
}
