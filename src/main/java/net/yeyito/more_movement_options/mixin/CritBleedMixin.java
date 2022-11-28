package net.yeyito.more_movement_options.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.yeyito.server.server_storage.ServerPlayerInfo;
import net.yeyito.status_effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class CritBleedMixin {
    private static final int BLEED_DURATION = 5;

    @Shadow public abstract Text getName();

    public int dashTickForgiveness = 3;

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;addCritParticles(Lnet/minecraft/entity/Entity;)V"))
    public void attackInject(Entity target, CallbackInfo ci) {
        if (!target.getWorld().isClient && ServerPlayerInfo.playerToTimeSinceLastOnGround.get(this.getName().getString()) - ServerPlayerInfo.playerToTimeSinceLastDash.get(this.getName().getString()) <= dashTickForgiveness) {
            System.out.println(ServerPlayerInfo.playerToTimeSinceLastOnGround.get(this.getName().getString()));
            System.out.println("CritBleedMixin");
            // Adding bleed
            if (target instanceof LivingEntity) {
                ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.BLEEDING,BLEED_DURATION*20,0));
                ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(net.minecraft.entity.effect.StatusEffects.SLOWNESS,BLEED_DURATION*20,0));
            }
        }
    }
}