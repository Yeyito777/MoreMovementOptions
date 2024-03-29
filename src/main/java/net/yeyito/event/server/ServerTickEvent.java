package net.yeyito.event.server;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.yeyito.more_movement_options.MoreMovementOptions;
import net.yeyito.server.server_storage.ServerPlayerInfo;


public class ServerTickEvent {
    private static int serverTickCounter = 0;
    private static MinecraftServer currentServer = null;

    public static void serverTick() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            serverTickCounter++;
            ServerPlayerInfo.playerMapUpdate(server);
            currentServer = server;
            updatePlayerInGroundHashMap(server.getPlayerManager().getPlayerList().toArray(new ServerPlayerEntity[0]));
        });
    }

    public static void updatePlayerInGroundHashMap(ServerPlayerEntity[] playerTable) {
        for (ServerPlayerEntity player: playerTable) {
            if (!player.isOnGround()) {return;}

            if (ServerPlayerInfo.playerToTimeSinceLastOnGround.containsKey(player.getName().getString())) {
                ServerPlayerInfo.playerToTimeSinceLastOnGround.remove(player.getName().getString());
                ServerPlayerInfo.playerToTimeSinceLastOnGround.put(player.getName().getString(),ServerTickEvent.getServerTickCounter());

            } else {MoreMovementOptions.LOGGER.error("player key not found in playerToTimeSinceLastOnGround");}
        }
    }

    public static int getServerTickCounter() {
        return serverTickCounter;
    }
    public static MinecraftServer getCurrentServer() {
        return currentServer;
    }

    public static void register() {
        serverTick();
    }
}
