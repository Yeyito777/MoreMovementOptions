package net.yeyito.more_movement_options.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.yeyito.storage.client_and_server.DashingInfo.critIDs;

@Mixin(ServerPlayerEntity.class)
public class CritParticlesDisablerMixinServer {
    @Shadow public ServerWorld getWorld() {return null;}

    @Inject(method = "addCritParticles", at = @At(value = "HEAD"),cancellable = true)
    private void addCritParticlesInject(Entity target, CallbackInfo ci) {
        if (critIDs.contains(target.getId())) {
            critIDs.remove(target.getId());
            this.getWorld().getChunkManager().sendToNearbyPlayers(((ServerPlayerEntity)((Object) this)), new EntityAnimationS2CPacket(target, 6));
            ci.cancel();
        }
    }
}
