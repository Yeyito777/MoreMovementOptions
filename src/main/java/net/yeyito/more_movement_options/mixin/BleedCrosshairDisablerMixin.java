package net.yeyito.more_movement_options.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3f;
import net.yeyito.client.renderer.HudRenderListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntity.class)
public class BleedCrosshairDisablerMixin {
    @Inject(method = "attack", at = @At("HEAD"))
    private void attackInject(Entity target, CallbackInfo ci) {
        HudRenderListener.shouldRenderCritBleedCrosshair = false;
        HudRenderListener.crosshairColor = new Vec3f(0.0f,0.0f,0.0f);
    }
}
