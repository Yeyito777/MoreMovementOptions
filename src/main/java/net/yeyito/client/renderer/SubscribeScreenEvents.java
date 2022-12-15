package net.yeyito.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.gui.screen.Screen;

public class SubscribeScreenEvents {
    public static RenderListener renderScreenListener;
    public static HudRenderListener hudRenderListener;

    public static void subscribeScreenListener(Screen screen) {
        renderScreenListener = new RenderListener();

        RenderListener.onInit();
        ScreenEvents.afterRender(screen).register(renderScreenListener);
        ScreenEvents.beforeRender(screen).register(renderScreenListener);
        ScreenEvents.beforeTick(screen).register(renderScreenListener);
        ScreenEvents.afterTick(screen).register(renderScreenListener);
        ScreenEvents.remove(screen).register(renderScreenListener);
    }

    public static void subscribeHudRenderListener() {
        hudRenderListener = new HudRenderListener();
        HudRenderCallback.EVENT.register(hudRenderListener);
    }
}
