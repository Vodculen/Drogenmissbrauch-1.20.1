package net.vodculen.drogenmissbrauch.mixin;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.vodculen.drogenmissbrauch.util.Ticks;
import net.vodculen.drogenmissbrauch.util.UneasyableEntity;
import net.vodculen.drogenmissbrauch.util.WithdrawableEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements UneasyableEntity, WithdrawableEntity {
	@Unique
	private int uneasyTicks = 0;
	private int withdrawalTicks = 0;

	@Inject(method = "tick", at = @At("HEAD"))
	private void uneasyTicks(CallbackInfo callbackInfo) {
		PlayerEntity player = (PlayerEntity) (Object) this;

		if (uneasyTicks > 0) {
			uneasyTicks--;

			if (uneasyTicks == 0 && player != null) {
				player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, Ticks.getMinutes(4), 1, true, true));
			}
		}
	}

	public void setUneasy(int ticks) {
		this.uneasyTicks = ticks;
	}

	public boolean isUneasy() {
		return uneasyTicks > 0;
	}

	// Withdrawable Entity ----------------------------------------------------------------------------------------------------------------------
	
	@Inject(method = "tick", at = @At("HEAD"))
	private void withdrawalTicks(CallbackInfo callbackInfo) {
		PlayerEntity player = (PlayerEntity) (Object) this;

		if (withdrawalTicks > 0) {
			withdrawalTicks--;

			if (withdrawalTicks == 0 && player != null) {
				player.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, Ticks.getSeconds(30), 0, true, true));
			}
		}
	}

	public void setWithdrawal(int ticks) {
		this.withdrawalTicks = ticks;
	}

	public boolean isWithdrawal() {
		return withdrawalTicks > 0;
	}
}