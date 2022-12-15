package net.yeyito.movement;

import net.minecraft.client.MinecraftClient;
import net.yeyito.client.renderer.HudRenderListener;
import net.yeyito.event.client.KeyboardInputEvent;
import net.yeyito.event.client.PlayerInfoEvent;
import net.yeyito.storage.client_and_server.DashingInfo;

import static net.yeyito.storage.client_and_server.DashingInfo.dashTickForgiveness;

public class Jumping {
    public static double systemTimeLastCritBleedSeconds = -1;

    public static void jumped() {
        if ((PlayerInfoEvent.lastTickOnGround - Dashing.timeOfLastDash) <= dashTickForgiveness) {
            HudRenderListener.shouldRenderCritBleedCrosshair = true;
            systemTimeLastCritBleedSeconds = System.nanoTime()/1e+9;
        }
    }
    public static void tick(MinecraftClient client) {
        assert client.player != null;

        if (client.player.isOnGround()) {HudRenderListener.shouldRenderCritBleedCrosshair = false;}
        if (client.player.isSwimming()) {HudRenderListener.shouldRenderCritBleedCrosshair = false;}
        if (client.player.isTouchingWater()) {HudRenderListener.shouldRenderCritBleedCrosshair = false;}
        if (client.player.isFallFlying()) {HudRenderListener.shouldRenderCritBleedCrosshair = false;}
        if (client.player.isRiding()) {HudRenderListener.shouldRenderCritBleedCrosshair = false;}
        if (client.player.isClimbing()) {HudRenderListener.shouldRenderCritBleedCrosshair = false;}
        if ((System.nanoTime()/1e+9) - systemTimeLastCritBleedSeconds >= (double) DashingInfo.dashTickMaxTime/20) {HudRenderListener.shouldRenderCritBleedCrosshair = false;}
    }
}
