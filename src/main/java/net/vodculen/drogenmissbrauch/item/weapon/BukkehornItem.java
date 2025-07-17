// package net.vodculen.drogenmissbrauch.item.weapon;

// import net.minecraft.entity.LivingEntity;
// import net.minecraft.entity.player.PlayerEntity;
// import net.minecraft.item.Item;
// import net.minecraft.item.ItemStack;
// import net.minecraft.item.Vanishable;
// import net.minecraft.sound.SoundCategory;
// import net.minecraft.sound.SoundEvents;
// import net.minecraft.stat.Stats;
// import net.minecraft.util.Hand;
// import net.minecraft.util.TypedActionResult;
// import net.minecraft.util.UseAction;
// import net.minecraft.world.World;
// import net.vodculen.drogenmissbrauch.entity.ModEntities;
// import net.vodculen.drogenmissbrauch.entity.custom.ChimeraSpewProjectileEntity;

// public class BukkehornItem extends Item implements Vanishable {
// 	public static final float ATTACK_DAMAGE = 8.0F;

// 	public BukkehornItem(Settings settings) {
// 		super(settings);
// 	}

// 	@Override
// 	public UseAction getUseAction(ItemStack stack) {
// 		return UseAction.TOOT_HORN;
// 	}

// 	@Override
// 	public int getMaxUseTime(ItemStack stack) {
// 		return 37000;
// 	}

// 	@Override
// 	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
// 		if (!world.isClient && user instanceof PlayerEntity playerEntity) {
// 			int timeUsed = this.getMaxUseTime(stack) - remainingUseTicks;
// 			if (timeUsed >= 5) {
// 				ChimeraSpewProjectileEntity chimeraSpewEntity = new ChimeraSpewProjectileEntity(world, user);

// 				stack.damage(1, playerEntity, p -> p.sendToolBreakStatus(user.getActiveHand()));
				
// 				chimeraSpewEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 0.5F, 1.0F);

// 				world.spawnEntity(chimeraSpewEntity);
// 				world.playSoundFromEntity(null, chimeraSpewEntity, SoundEvents.BLOCK_SLIME_BLOCK_BREAK, SoundCategory.PLAYERS, 1.0F, 1.0F);

// 				playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
// 			}
// 		}
// 	}

// 	@Override
// 	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
// 		ItemStack itemStack = user.getStackInHand(hand);

// 		if (itemStack.getDamage() >= itemStack.getMaxDamage()) {
// 			return TypedActionResult.fail(itemStack);
// 		} else {
// 			user.setCurrentHand(hand);
// 			return TypedActionResult.consume(itemStack);
// 		}
// 	}

// 	@Override
// 	public int getEnchantability() {
// 		return 1;
// 	}
// }