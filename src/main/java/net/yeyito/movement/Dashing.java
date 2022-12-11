package net.yeyito.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.yeyito.client.AnimationTypes;
import net.yeyito.client.Camera;
import net.yeyito.client.renderer.Renderer;
import net.yeyito.event.client.KeyboardInputEvent;
import net.yeyito.networking.PacketHandler;
import net.yeyito.sounds.ModSounds;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class Dashing {
    private static int timeOfLastDash = -9999;
    public static int tickQuicknessAcceptance = 5;

    public static void dashPlayer(MinecraftClient client, String dashType) {
        if (client.player != null && !client.player.isSneaking() && client.player.isOnGround() && !isScreenFocused(client) && KeyboardInputEvent.getTickDifferenceToCurrentTick(timeOfLastDash) > tickQuicknessAcceptance) {
            timeOfLastDash = KeyboardInputEvent.tickCounter;
            switch (dashType) {
                case "Left" -> {dashDirection(client,1, new Vec3d(1, 0, 0),dashType); Camera.setZRotation(-2.5F,0.1,true, AnimationTypes.SINUSOIDAL);}
                case "Right" -> {dashDirection(client,1, new Vec3d(-1, 0, 0),dashType); Camera.setZRotation(2.5F,0.1,true, AnimationTypes.SINUSOIDAL);}
                case "Forward" -> {dashDirection(client,1, new Vec3d(0, 0, 1),dashType); Camera.setXRotation(-3.5F,0.2,true, AnimationTypes.SINUSOIDAL);}
                case "Backward" -> {dashDirection(client,1, new Vec3d(0, 0, -1),dashType); Camera.setXRotation(5F,0.2,true, AnimationTypes.SINUSOIDAL);}
            }
        }
    }

    public static void dashDirection(MinecraftClient client, int speed, Vec3d direction,String dashType) {
        // We already tested for client.player previously
        assert client.player != null;

        // Updating the velocity (client) and sending a dashing packet
        client.player.updateVelocity(speed,direction);
        ClientPlayNetworking.send(PacketHandler.DASHING_ID, PacketByteBufs.create().writeString(dashType));
    }
    public static boolean isScreenFocused(MinecraftClient client) {
        if (client.currentScreen != null) {
            return client.currentScreen.getFocused() != null;
        } else {return false;}
    }
}
