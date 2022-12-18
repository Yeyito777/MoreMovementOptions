package net.yeyito.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.GameMode;
import net.yeyito.client.AnimationTypes;
import net.yeyito.client.Animations;
import net.yeyito.client.textures.Textures;

import static net.yeyito.client.renderer.HudRenderListener.crosshairColor;
import static net.yeyito.client.renderer.HudRenderListener.shouldRenderCritBleedCrosshair;
import static net.yeyito.client.renderer.RenderCrosshair.renderCritBleedCrosshair;

public class HudRenderListener implements HudRenderCallback,AutoCloseable {
    public static boolean shouldRenderCritBleedCrosshair = false;
    public static Vec3f crosshairColor = new Vec3f(1.0f,1.0f,1.0f);
    // In-Object
    public boolean isSubscribed = false;
    public boolean isClosed = false;

    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        if (isClosed) {return;}
        renderCritBleedCrosshair(matrixStack,tickDelta);
    }
    public void subscribe() {
        this.isSubscribed = true;
    }

    @Override
    public void close() throws Exception {
        this.isClosed = true;
    }
}

// Crit Bleed Crosshair Rendering Madness
class RenderCrosshair {
    public static boolean shouldRenderCriBleedCrosshairLastTick = false;
    public static double timeWhenShouldRenderCritBleedCrosshairUpdate = -1;
    public static float transparencyGoal = 1.0f;
    public static float transparencyStart = 0.0f;
    public static double animationTime = 0.15D;
    public static boolean started = false;

    public static void renderCritBleedCrosshair(MatrixStack matrixStack, float tickDelta) {
        if (shouldRenderCritBleedCrosshair && !shouldRenderCriBleedCrosshairLastTick) {
            // Start Anim
            shouldRenderCriBleedCrosshairLastTick = true;
            timeWhenShouldRenderCritBleedCrosshairUpdate = System.nanoTime()/1e+9;
            transparencyGoal = 1.0f;
            transparencyStart = 0.0f;
            crosshairColor = new Vec3f(1.0f,1.0f,1.0f);
            started = true;
        }
        if (!shouldRenderCritBleedCrosshair && shouldRenderCriBleedCrosshairLastTick) {
            // End Anim
            shouldRenderCriBleedCrosshairLastTick = false;
            timeWhenShouldRenderCritBleedCrosshairUpdate = System.nanoTime()/1e+9;
            transparencyGoal = 0.0f;
            transparencyStart = 1.0f;
            started = true;
        }

        if (!started || MinecraftClient.getInstance().interactionManager == null || MinecraftClient.getInstance().interactionManager.getCurrentGameMode() == GameMode.SPECTATOR ||
                !MinecraftClient.getInstance().options.getPerspective().isFirstPerson()) {return;}
        int texSize = 16;

        int x = (int) Math.round(((double) MinecraftClient.getInstance().getWindow().getScaledWidth()/2)-4);
        int y = (int) Math.round(((double) MinecraftClient.getInstance().getWindow().getScaledHeight()/2)-4);

        // Enabling transparency
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        // Matrix allowing
        matrixStack.push();
        // Actual Code
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(crosshairColor.getX(),crosshairColor.getY(),crosshairColor.getZ(), Animations.animFloat(transparencyStart,transparencyGoal,(System.nanoTime()/1e+9)-timeWhenShouldRenderCritBleedCrosshairUpdate,animationTime, AnimationTypes.LINEAR));
        RenderSystem.setShaderTexture(0, Textures.BLEED_CROSSHAIR_ID);
        DrawableHelper.drawTexture(matrixStack,x,y,0,0,texSize,texSize,texSize,texSize);
        // Matrix disallowing
        matrixStack.pop();
        // Disabling transparency
        RenderSystem.disableBlend();
    }

}
