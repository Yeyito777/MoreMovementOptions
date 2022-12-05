package net.yeyito.more_movement_options.mixin;

import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;
import net.yeyito.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(GameRenderer.class)
public abstract class GameRendererRotationMixin {
    @Inject(method = "renderWorld",at = @At("HEAD"))
    private void renderWorldInject(float tickDelta, long limitTime, MatrixStack matrices, CallbackInfo ci) {
        Camera.worldRenderTick();

        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(Camera.getXRotation()));
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(Camera.getYRotation()));
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(Camera.getZRotation()));
    }
}
