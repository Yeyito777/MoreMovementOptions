package net.yeyito.more_movement_options;

import net.fabricmc.api.ClientModInitializer;
import net.yeyito.event.KeyboardInputEvent;

public class MoreMovementOptionsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyboardInputEvent.register();
    }
}
