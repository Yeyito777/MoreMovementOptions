package net.yeyito.more_movement_options.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.yeyito.particle.Particles;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class BleedCritParticlesMixin {
    @Shadow private ClientWorld world;
    @Final @Shadow private MinecraftClient client;

    @Inject(method = "onEntityAnimation",at = @At("TAIL"))
    private void onEntityAnimationInject(EntityAnimationS2CPacket packet, CallbackInfo ci) {
        Entity entity = this.world.getEntityById(packet.getId());
        if (entity !=null && packet.getAnimationId() == 6) {
            this.client.particleManager.addEmitter(entity, Particles.BLEED_CRIT);
        }
    }
}
