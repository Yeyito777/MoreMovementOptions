package net.yeyito.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.gui.screen.Screen;

public class SubscribeScreenEvents {
    public static RenderListener renderScreenListener  = new RenderListener();
    public static HudRenderListener hudRenderListener = new HudRenderListener();

    public static void subscribeScreenListener(Screen screen) {
        if (renderScreenListener.isSubscribed) {
            try {
                renderScreenListener.close();
                renderScreenListener = new RenderListener();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Avoiding possible memory leak!");
                System.exit(-1);
            }
        }

        ScreenEvents.afterRender(screen).register(renderScreenListener);
        ScreenEvents.beforeRender(screen).register(renderScreenListener);
        ScreenEvents.beforeTick(screen).register(renderScreenListener);
        ScreenEvents.afterTick(screen).register(renderScreenListener);
        ScreenEvents.remove(screen).register(renderScreenListener);
        renderScreenListener.subscribe();
    }

    public static void subscribeHudRenderListener() {
        if (hudRenderListener.isSubscribed) {
            try {
                hudRenderListener.close();
                hudRenderListener = new HudRenderListener();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Avoiding possible memory leak!");
                System.exit(-1);
            }
        }

        HudRenderCallback.EVENT.register(hudRenderListener);
        hudRenderListener.subscribe();
    }
}
