package net.yeyito.more_movement_options;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.yeyito.event.client.KeyboardInputEvent;
import net.yeyito.event.client.PlayerInfoEvent;
import net.yeyito.networking.PacketHandler;

import static net.yeyito.client.renderer.SubscribeScreenEvents.hudRenderListener;

public class MoreMovementOptionsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyboardInputEvent.register();
        PlayerInfoEvent.register();

        PacketHandler.registerS2CPackets();
    }
}
