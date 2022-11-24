package net.yeyito.movement;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.yeyito.networking.PacketHandler;

public class Dashing {
    public static void dashPlayer(MinecraftClient client, String dashType) {
        if (client.player != null && !client.player.isSneaking() && client.player.isOnGround() && !isScreenFocused(client)) {
            switch (dashType) {
                case "Left" -> dashDirection(client,2, new Vec3d(15, 0, 0),dashType);
                case "Right" -> dashDirection(client,2, new Vec3d(-15, 0, 0),dashType);
                case "Forward" -> dashDirection(client,1, new Vec3d(0, 0, 15),dashType);
                case "Backward" -> dashDirection(client,2, new Vec3d(0, 0, -15),dashType);
            }
        }
    }
    public static void dashDirection(MinecraftClient client, int speed, Vec3d direction,String dashType) {
        assert client.player != null;
        client.player.updateVelocity(speed,direction);
        ClientPlayNetworking.send(PacketHandler.DASHING_ID, PacketByteBufs.create().writeString(dashType));
    }
    public static boolean isScreenFocused(MinecraftClient client) {
        if (client.currentScreen != null) {
            return client.currentScreen.getFocused() != null;
        } else {return false;}
    }
}
