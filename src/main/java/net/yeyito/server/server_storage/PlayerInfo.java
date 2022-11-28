package net.yeyito.server.server_storage;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;

import java.util.*;

public class PlayerInfo {
    public static HashMap<String, Integer> playerToTimeSinceLastDash = new HashMap<String,Integer>();
    public static HashMap<String, Integer> playerToTimeSinceLastOnGround = new HashMap<String,Integer>();
    public static HashMap<String, String> playerToLastDashType = new HashMap<>();

    public static void playerMapUpdate(MinecraftServer server) {
        for (String playerName: server.getPlayerNames()) {
            if (!playerToTimeSinceLastDash.containsKey(playerName)) {
                playerToTimeSinceLastDash.put(playerName,0);
            }

            if (!playerToTimeSinceLastOnGround.containsKey(playerName)) {
                playerToTimeSinceLastOnGround.put(playerName,0);
            }

            if (!playerToLastDashType.containsKey(playerName)) {
                playerToLastDashType.put(playerName,"null");
            }
        }
    }
}
