package net.yeyito.event.client;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class PlayerInfoEvent {
    public static boolean wasLastTickOnGround = true;
    public static int lastTickOnGround = -999;

    public static void clientTick() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) {return;}

            if (!client.player.isOnGround() && wasLastTickOnGround) {lastTickOnGround = KeyboardInputEvent.tickCounter;}
            wasLastTickOnGround = client.player.isOnGround();
        });
    }
    public static void register() {
        clientTick();
    }
}
