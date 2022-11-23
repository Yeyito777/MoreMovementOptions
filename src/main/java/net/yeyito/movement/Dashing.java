package net.yeyito.movement;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

public class Dashing {
    public static void dashPlayer(MinecraftClient client, String dashType) {
        assert client.player != null;
        switch (dashType) {
            case "Left" -> client.player.updateVelocity(2, new Vec3d(15, 0, 0));
            case "Right" -> client.player.updateVelocity(2, new Vec3d(-15, 0, 0));
            case "Forward" -> client.player.updateVelocity(2, new Vec3d(0, 0, 15));
            case "Backward" -> client.player.updateVelocity(2, new Vec3d(0, 0, -15));
        }
    }
}
