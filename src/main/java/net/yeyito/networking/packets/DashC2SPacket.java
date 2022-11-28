package net.yeyito.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.yeyito.event.server.ServerTickEvent;
import net.yeyito.more_movement_options.MoreMovementOptions;
import net.yeyito.networking.PacketHandler;
import net.yeyito.server.server_storage.PlayerInfo;

import java.util.Objects;
import java.util.Random;

public class DashC2SPacket {
    private static final float DASHING_PENALTY = 0.2F;

    public static World getPlayerWorld(MinecraftServer server, ServerPlayerEntity player) {
        return server.getWorld(player.world.getRegistryKey());
    }

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        // run on the server
        player.getHungerManager().addExhaustion(DASHING_PENALTY);

        // Storing the tick this player dashed
        if (PlayerInfo.playerToTimeSinceLastDash.containsKey(player.getName().getString())) {
            PlayerInfo.playerToTimeSinceLastDash.remove(player.getName().getString());
            PlayerInfo.playerToTimeSinceLastDash.put(player.getName().getString(),ServerTickEvent.getServerTickCounter());

        } else {MoreMovementOptions.LOGGER.error("player key not found in playerToTimeSinceLastDash");}

        // Storing the dash type
        if (PlayerInfo.playerToLastDashType.containsKey(player.getName().getString())) {
            PlayerInfo.playerToLastDashType.remove(player.getName().getString());
            PlayerInfo.playerToLastDashType.put(player.getName().getString(), buf.readString());
        } else {
            MoreMovementOptions.LOGGER.error("player key not found in playerToLastDashType");}
    }
}
