package net.yeyito.client.renderer;

import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.gui.screen.Screen;

public class SubscribeScreenEvents {
    public static void subscribe(Screen screen) {
        RenderListener.onInit();
        ScreenEvents.afterRender(screen).register(new RenderListener());
        ScreenEvents.beforeRender(screen).register(new RenderListener());
        ScreenEvents.beforeTick(screen).register(new RenderListener());
        ScreenEvents.afterTick(screen).register(new RenderListener());
        ScreenEvents.remove(screen).register(new RenderListener());
    }
}
