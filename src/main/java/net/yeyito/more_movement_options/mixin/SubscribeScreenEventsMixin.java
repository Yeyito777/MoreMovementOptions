package net.yeyito.more_movement_options.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.yeyito.client.renderer.SubscribeScreenEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class SubscribeScreenEventsMixin {
    @Inject(method = "setScreen",at = @At("TAIL"))
    private void setScreenInject(Screen screen, CallbackInfo ci) {
        if (screen == null) {return;}
        SubscribeScreenEvents.subscribe(screen);
    }
}
