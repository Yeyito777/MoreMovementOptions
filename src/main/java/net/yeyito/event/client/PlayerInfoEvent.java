package net.yeyito.event.client;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.yeyito.movement.Jumping;

public class PlayerInfoEvent {
    public static boolean wasLastTickOnGround = true;
    public static int lastTickOnGround = -999;

    public static void clientTick() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) {return;}

            Jumping.tick(client);

            if (!client.player.isOnGround() && wasLastTickOnGround) {
                lastTickOnGround = KeyboardInputEvent.tickCounter;
                Jumping.jumped();
            }
            wasLastTickOnGround = client.player.isOnGround();
        });
    }
    public static void register() {
        clientTick();
    }
}
