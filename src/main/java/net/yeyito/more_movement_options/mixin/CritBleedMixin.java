package net.yeyito.more_movement_options.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.yeyito.client.renderer.HudRenderListener;
import net.yeyito.event.client.PlayerInfoEvent;
import net.yeyito.event.server.ServerTickEvent;
import net.yeyito.movement.Dashing;
import net.yeyito.server.server_storage.ServerPlayerInfo;
import net.yeyito.status_effect.StatusEffects;
import net.yeyito.storage.client_and_server.DashingInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.yeyito.storage.client_and_server.DashingInfo.dashTickForgiveness;
import static net.yeyito.storage.client_and_server.DashingInfo.dashTickMaxTime;

@Mixin(PlayerEntity.class)
public abstract class CritBleedMixin {
    private static final int BLEED_DURATION = 7;
    private static final int BLEED_AMPLIFIER = 0;

    @Shadow public abstract Text getName();

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;addCritParticles(Lnet/minecraft/entity/Entity;)V"))
    public void attackInject(Entity target, CallbackInfo ci) {
        boolean addOnClient = false;
        if (!target.getWorld().isClient && ServerPlayerInfo.playerToTimeSinceLastOnGround.get(this.getName().getString()) - ServerPlayerInfo.playerToTimeSinceLastDash.get(this.getName().getString()) <= dashTickForgiveness &&
                ServerTickEvent.getServerTickCounter() - ServerPlayerInfo.playerToTimeSinceLastOnGround.get(this.getName().getString()) <= dashTickMaxTime) {
            // Adding bleed
            if (target instanceof LivingEntity) {
                addOnClient = true;
                ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.BLEEDING,BLEED_DURATION*20,BLEED_AMPLIFIER,false,false,true));
                ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(net.minecraft.entity.effect.StatusEffects.SLOWNESS,BLEED_DURATION*20,BLEED_AMPLIFIER,false,false,false));
                ((ServerWorld) target.getWorld()).playSound(null,target.getX(),target.getY()+1,target.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, SoundCategory.PLAYERS,1,0);
                //((ServerWorld) target.getWorld()).spawnParticles(ParticleTypes.LARGE_SMOKE,target.getX(),target.getY()+1,target.getZ(),7,0,0,0,0.1);
            }
        }

        if (addOnClient && target.getWorld().isClient) {
            ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.BLEEDING,BLEED_DURATION*20,BLEED_AMPLIFIER,false,false,true));
            ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(net.minecraft.entity.effect.StatusEffects.SLOWNESS,BLEED_DURATION*20,BLEED_AMPLIFIER,false,false,false));
            HudRenderListener.shouldRenderCritBleedCrosshair = false;
            // prevent from adding normal crit particles
        }
    }
}
