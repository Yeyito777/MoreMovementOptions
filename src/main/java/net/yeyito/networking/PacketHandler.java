package net.yeyito.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.yeyito.more_movement_options.MoreMovementOptions;
import net.yeyito.networking.packets.DashC2SPacket;

public class PacketHandler {
    public static final Identifier DASHING_ID = new Identifier(MoreMovementOptions.MOD_ID, "dashing");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(DASHING_ID, DashC2SPacket::receive);
    }

    public static void registerS2CPackets() {

    }
}
