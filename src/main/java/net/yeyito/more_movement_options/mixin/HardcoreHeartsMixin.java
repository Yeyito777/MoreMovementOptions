package net.yeyito.more_movement_options.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.yeyito.status_effect.StatusEffects;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.yeyito.event.client.PlayerInfoEvent.player;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class HardcoreHeartsMixin {
    @ModifyVariable(method = "renderHealthBar",at = @At("STORE"),name = "i")
    private int injected(int value) {
        if (player.hasStatusEffect(StatusEffects.BLEEDING)) {
            return 9*5;
        }
        return 9 * (player.world.getLevelProperties().isHardcore() ? 5 : 0);
    }
}
