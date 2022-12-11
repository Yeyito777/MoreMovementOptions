package net.yeyito.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.Blocks;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;
import net.yeyito.event.server.ServerTickEvent;
import net.yeyito.more_movement_options.MoreMovementOptions;
import net.yeyito.server.server_storage.ServerPlayerInfo;
import net.yeyito.sounds.ModSounds;

import java.util.Objects;
import java.util.Random;

public class DashC2SPacket {
    private static final float DASHING_PENALTY = 0.2F;

    public static World getPlayerWorld(MinecraftServer server, ServerPlayerEntity player) {
        return server.getWorld(player.world.getRegistryKey());
    }

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        // run on the server
        if (!player.isCreative() && !player.isSpectator()) {
            player.getHungerManager().addExhaustion(DASHING_PENALTY);
        }

        Objects.requireNonNull(server.getWorld(player.getWorld().getRegistryKey())).playSound(null,new BlockPos(player.getPos()),ModSounds.DASH,SoundCategory.PLAYERS,1,1);
        Objects.requireNonNull(server.getWorld(player.getWorld().getRegistryKey())).spawnParticles(ParticleTypes.POOF,player.getX(),player.getY(),player.getZ(),15,0,0,0,0.1);

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
