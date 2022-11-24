package net.yeyito.server.server_storage;

import net.minecraft.server.MinecraftServer;

import java.util.*;

public class PlayerInfo {
    public static HashMap<String, Integer> playerToTimeSinceLastDash = new HashMap<String,Integer>();

    public static void playerMapUpdate(MinecraftServer server) {
        for (String playerName: server.getPlayerNames()) {
            if (!playerToTimeSinceLastDash.containsKey(playerName)) {
                playerToTimeSinceLastDash.put(playerName,0);
            }
        }
    }
}
