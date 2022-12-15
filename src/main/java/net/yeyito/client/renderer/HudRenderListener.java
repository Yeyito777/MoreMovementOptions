package net.yeyito.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.yeyito.client.AnimationTypes;
import net.yeyito.client.Animations;
import net.yeyito.client.textures.Textures;

public class HudRenderListener implements HudRenderCallback {
    public static boolean shouldRenderCritBleedCrosshair = false;
    public static boolean shouldRenderCriBleedCrosshairLastTick = false;
    public static double timeWhenShouldRenderCritBleedCrosshairUpdate = -1;
    public static float transparencyGoal = 1.0f;
    public static float transparencyStart = 0.0f;
    public static double animationTime = 0.15;
    public static boolean started = false;

    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        renderCritBleedCrosshair(matrixStack,tickDelta);
    }

    public static void renderCritBleedCrosshair(MatrixStack matrixStack, float tickDelta) {
        if (shouldRenderCritBleedCrosshair && !shouldRenderCriBleedCrosshairLastTick) {
            // Start Anim
            shouldRenderCriBleedCrosshairLastTick = true;
            timeWhenShouldRenderCritBleedCrosshairUpdate = System.nanoTime()/1e+9;
            transparencyGoal = 1.0f;
            transparencyStart = 0.0f;
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

        if (!started) {return;}
        // Enabling transparency
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        // Matrix allowing
        matrixStack.push();
        // Actual Code
        System.out.println(tickDelta);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f,1.0f,1.0f, Animations.animFloat(transparencyStart,transparencyGoal,(System.nanoTime()/1e+9)-timeWhenShouldRenderCritBleedCrosshairUpdate,animationTime, AnimationTypes.LINEAR));
        RenderSystem.setShaderTexture(0, Textures.BLEED_CROSSHAIR_ID);
        DrawableHelper.drawTexture(matrixStack,(MinecraftClient.getInstance().getWindow().getScaledWidth()/2)-4,(MinecraftClient.getInstance().getWindow().getScaledHeight()/2)-4,0,0,16,16,16,16);
        // Matrix disallowing
        matrixStack.pop();
        // Disabling transparency
        RenderSystem.disableBlend();
    }
}
