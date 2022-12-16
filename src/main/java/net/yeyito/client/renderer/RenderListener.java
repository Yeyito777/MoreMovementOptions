package net.yeyito.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.yeyito.client.textures.Textures;
import net.yeyito.more_movement_options.MoreMovementOptions;

public class RenderListener implements ScreenEvents.AfterRender,ScreenEvents.BeforeRender,ScreenEvents.BeforeTick,ScreenEvents.AfterTick,ScreenEvents.Remove,AutoCloseable {
    public boolean isSubscribed = false;
    public boolean isClosed = false;

    @Override
    public void afterRender(Screen screen, MatrixStack matrices, int mouseX, int mouseY, float tickDelta) {
        if (isClosed) {return;}
    }

    @Override
    public void afterTick(Screen screen) {
        if (isClosed) {return;}
    }

    @Override
    public void beforeRender(Screen screen, MatrixStack matrices, int mouseX, int mouseY, float tickDelta) {
        if (isClosed) {return;}
    }

    @Override
    public void beforeTick(Screen screen) {
        if (isClosed) {return;}
    }

    @Override
    public void onRemove(Screen screen) {
        if (isClosed) {return;}
    }

    public void subscribe() {
        this.isSubscribed = true;
    }

    @Override
    public void close() throws Exception {
        this.isClosed = true;
    }
}
