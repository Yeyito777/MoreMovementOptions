package net.yeyito.more_movement_options.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityDamageMixin {
    @Shadow public abstract ServerWorld getWorld();

    @Inject(method = "damage", at = @At(value = "INVOKE",target = "Lnet/minecraft/entity/player/PlayerEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private void damageInject(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        this.getWorld().emitGameEvent(GameEvent.ENTITY_DAMAGE,((ServerPlayerEntity) ((Object) this)).getPos(),new GameEvent.Emitter(((ServerPlayerEntity) ((Object) this)),null));
    }
}
