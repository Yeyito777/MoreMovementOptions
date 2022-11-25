package net.yeyito.more_movement_options.mixin;

import net.minecraft.client.sound.Sound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.world.event.GameEvent;
import net.yeyito.server.server_storage.PlayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class CritBleedMixin {
    @Shadow public abstract Text getName();
    public int dashTickForgiveness = 3;

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;addCritParticles(Lnet/minecraft/entity/Entity;)V"))
    public void attackInject(Entity target, CallbackInfo ci) {
        System.out.println(PlayerInfo.playerToTimeSinceLastOnGround.get(this.getName().getString()));
        System.out.println(PlayerInfo.playerToTimeSinceLastDash.get(this.getName().getString()));

        if (PlayerInfo.playerToTimeSinceLastOnGround.get(this.getName().getString()) - PlayerInfo.playerToTimeSinceLastDash.get(this.getName().getString()) <= dashTickForgiveness) {
            target.setFireTicks(40);
            target.playSound(SoundEvents.BLOCK_SCULK_BREAK,10.0F,1.0F);
        }
    }
}
