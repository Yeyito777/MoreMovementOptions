package net.yeyito.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.yeyito.client.textures.Textures;

public class RenderListener implements ScreenEvents.AfterRender,ScreenEvents.BeforeRender,ScreenEvents.BeforeTick,ScreenEvents.AfterTick,ScreenEvents.Remove {

    @Override
    public void afterRender(Screen screen, MatrixStack matrices, int mouseX, int mouseY, float tickDelta) {

    }

    @Override
    public void afterTick(Screen screen) {

    }

    @Override
    public void beforeRender(Screen screen, MatrixStack matrices, int mouseX, int mouseY, float tickDelta) {

    }

    @Override
    public void beforeTick(Screen screen) {

    }

    @Override
    public void onRemove(Screen screen) {

    }
    public static void onInit() {

    }
}
