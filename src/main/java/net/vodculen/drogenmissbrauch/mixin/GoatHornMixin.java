package net.vodculen.drogenmissbrauch.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.GoatHornItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.vodculen.drogenmissbrauch.block.ModBlocks;
import net.vodculen.drogenmissbrauch.enchantment.ModEnchantmentHelper;
import net.vodculen.drogenmissbrauch.entity.custom.ChimeraSpewProjectileEntity;
import net.vodculen.drogenmissbrauch.util.Ticks;
import net.vodculen.drogenmissbrauch.util.WeaponUtils;


@Mixin(GoatHornItem.class)
public class GoatHornMixin {
	@Inject(method = "use", at = @At("HEAD"), cancellable = true)
	private void bukkehornEnchantment(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> callbackInfo) {
		ItemStack itemStack = user.getStackInHand(hand);
		int level = ModEnchantmentHelper.getBukkehorn(itemStack);

		ItemStack fuel = WeaponUtils.getProjectileTypeForWeapon(itemStack, user);
		boolean inCreativeMode = user.getAbilities().creativeMode;
		boolean canUseAbility = inCreativeMode && fuel.isOf(ModBlocks.SUGAR_BLOCK.asItem());

		if (level >= 1 && (!itemStack.isEmpty() || inCreativeMode)) {
			if (fuel.isEmpty()) {
				fuel = new ItemStack(Items.GUNPOWDER);
			}

			ChimeraSpewProjectileEntity chimeraSpewEntity = new ChimeraSpewProjectileEntity(world, user);
			
			chimeraSpewEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 0.5F, 1.0F);

			world.spawnEntity(chimeraSpewEntity);
			world.playSoundFromEntity(null, chimeraSpewEntity, SoundEvents.BLOCK_SAND_BREAK, SoundCategory.PLAYERS, 1.0F, 1.0F);
		
			user.getItemCooldownManager().set(itemStack.getItem(), Ticks.getSeconds(10));
			user.incrementStat(Stats.USED.getOrCreateStat(itemStack.getItem()));
	
			if (!canUseAbility) {
				fuel.decrement(2);
						
				if (fuel.isEmpty()) {
					user.getInventory().removeOne(fuel);
				}
			}
			
			callbackInfo.setReturnValue(TypedActionResult.consume(itemStack));
			callbackInfo.cancel();
		}
	}
}
