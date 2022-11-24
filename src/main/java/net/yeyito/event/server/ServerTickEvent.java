package net.yeyito.event.server;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.yeyito.server.server_storage.PlayerInfo;


public class ServerTickEvent {
    private static int serverTickCounter = 0;

    public static void serverTick() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            serverTickCounter++;
            PlayerInfo.playerMapUpdate(server);
        });
    }

    public static int getServerTickCounter() {
        return serverTickCounter;
    }

    public static void register() {
        serverTick();
    }
}
