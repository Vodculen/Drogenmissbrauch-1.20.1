package net.vodculen.drogenmissbrauch.item.consumable;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Vanishable;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


public class Nebula extends Item implements Vanishable {
	public Nebula(Item.Settings settings) {
		super(settings);
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.TOOT_HORN;
	}

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 36000;
	}

	@Override
	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		if (!world.isClient && user instanceof PlayerEntity player) {
			int i = this.getMaxUseTime(stack) - remainingUseTicks;
			
			if (i >= 10) {
				Vec3d look = user.getRotationVec(1.0F);
				double x = user.getX() + look.x * 0.6F;
				double y = user.getEyeY() + look.y;
				double z = user.getZ() + look.z * 0.6F;

				stack.damage(1, user, p -> p.sendToolBreakStatus(user.getActiveHand()));

				((ServerWorld) world).spawnParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z, 3, 0.01, 0.05, 0.01, 0.01);
				
				player.getItemCooldownManager().set(this, 40);
			}

			player.incrementStat(Stats.USED.getOrCreateStat(this));
		}
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		
		if (itemStack.getDamage() >= itemStack.getMaxDamage()) {
			return TypedActionResult.fail(itemStack);
		} else {
			user.setCurrentHand(hand);
			return TypedActionResult.consume(itemStack);
		}
	}
}
