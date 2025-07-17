package net.vodculen.drogenmissbrauch.item.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Vanishable;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.vodculen.drogenmissbrauch.enchantment.ModEnchantmentHelper;
import net.vodculen.drogenmissbrauch.util.Ticks;

public class MarzipanClaymoreItem extends Item implements Vanishable {
	public double attackDamage = 9.0;
	public double attackSpeed = -2.9;
	public static final EntityAttributeModifier.Operation OPERATION = EntityAttributeModifier.Operation.ADDITION;

	public MarzipanClaymoreItem(Settings settings) {
		super(settings);
	}

	public Multimap<EntityAttribute, EntityAttributeModifier> buildAttributeModifiers(ItemStack stack) {
		double damageReduction = stack.getOrCreateNbt().getDouble("DamageReduction");
		double speedAddition= stack.getOrCreateNbt().getDouble("SpeedAddition");
		double modifiedDamage = Math.max(0, attackDamage - damageReduction);
		double modifiedSpeed = attackSpeed + speedAddition;

		Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE,
				new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", modifiedDamage, OPERATION));
		builder.put(EntityAttributes.GENERIC_ATTACK_SPEED,
				new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", modifiedSpeed, OPERATION));

		return builder.build();
	}

	// Override this to apply dynamic damage only in main hand
	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
		if (slot == EquipmentSlot.MAINHAND) {
			return buildAttributeModifiers(stack);
		}

		return super.getAttributeModifiers(stack, slot);
	}

	// Fallback when no ItemStack is available - provide base damage only
	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		if (slot == EquipmentSlot.MAINHAND) {

			Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();

			builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE,
				new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", attackDamage, OPERATION));
			builder.put(EntityAttributes.GENERIC_ATTACK_SPEED,
				new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", attackSpeed, OPERATION));

			return builder.build();
		}
		return super.getAttributeModifiers(slot);
	}

	@Override
	public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
		return !miner.isCreative();
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if (!world.isClient) {
			float reduction = 1.5F;
			float addition = 0.1F;
			int timesEaten = stack.getOrCreateNbt().getInt("TimesEaten");
			double currentReduction = stack.getOrCreateNbt().getDouble("DamageReduction");
			double newReduction = Math.min(currentReduction + reduction, attackDamage);
			double currentAddition = stack.getOrCreateNbt().getDouble("SpeedAddition");
			double newAddition = currentAddition + addition;

			stack.getOrCreateNbt().putInt("TimesEaten", timesEaten + 1);
			stack.getOrCreateNbt().putDouble("DamageReduction", newReduction);
			stack.getOrCreateNbt().putDouble("SpeedAddition", newAddition);

			user.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, Ticks.getMinutes(3), timesEaten, true, true));
		}

		return super.finishUsing(stack, world, user);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);
		int timesEaten = stack.getOrCreateNbt().getInt("TimesEaten");
		
		if (timesEaten >= 4) {
			return TypedActionResult.fail(stack);
		}

		user.setCurrentHand(hand);
		return TypedActionResult.consume(stack);
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.damage(1, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
		return true;
	}

	@Override
	public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		if (state.getHardness(world, pos) != 0.0) {
			stack.damage(2, miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
		}
		return true;
	}

	@Override
	public int getEnchantability() {
		return 1;
	}
}
