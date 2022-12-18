package net.yeyito.more_movement_options.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.yeyito.storage.client_and_server.DashingInfo.critIDs;

@Mixin(ClientPlayerEntity.class)
public class CritParticlesDisablerMixinClient {
    @Inject(method = "addCritParticles", at = @At(value = "HEAD"),cancellable = true)
    private void addCritParticlesInject(Entity target, CallbackInfo ci) {
        if (critIDs.contains(target.getId())) {
            critIDs.remove(target.getId());
            //this.client.particleManager.addEmitter(target, ParticleTypes.CRIT);
            ci.cancel();
        }
    }
}
