package net.yeyito.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import net.yeyito.event.server.ServerTickEvent;
import net.yeyito.more_movement_options.MoreMovementOptions;
import net.yeyito.server.server_storage.ServerPlayerInfo;

public class DashC2SPacket {
    private static final float DASHING_PENALTY = 0.2F;

    public static World getPlayerWorld(MinecraftServer server, ServerPlayerEntity player) {
        return server.getWorld(player.world.getRegistryKey());
    }

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        // run on the server
        player.getHungerManager().addExhaustion(DASHING_PENALTY);

        // Storing the tick this player dashed
        if (ServerPlayerInfo.playerToTimeSinceLastDash.containsKey(player.getName().getString())) {
            ServerPlayerInfo.playerToTimeSinceLastDash.remove(player.getName().getString());
            ServerPlayerInfo.playerToTimeSinceLastDash.put(player.getName().getString(),ServerTickEvent.getServerTickCounter());

        } else {MoreMovementOptions.LOGGER.error("player key not found in playerToTimeSinceLastDash");}

        // Storing the dash type
        if (ServerPlayerInfo.playerToLastDashType.containsKey(player.getName().getString())) {
            ServerPlayerInfo.playerToLastDashType.remove(player.getName().getString());
            ServerPlayerInfo.playerToLastDashType.put(player.getName().getString(), buf.readString());
        } else {
            MoreMovementOptions.LOGGER.error("player key not found in playerToLastDashType");}
    }
}
