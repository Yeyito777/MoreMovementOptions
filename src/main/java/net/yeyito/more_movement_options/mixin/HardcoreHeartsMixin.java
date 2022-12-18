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
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import static net.yeyito.event.client.PlayerInfoEvent.player;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class HardcoreHeartsMixin {
    @ModifyArgs(method = "renderHealthBar",at = @At(value = "INVOKE",target = "Lnet/minecraft/client/gui/hud/InGameHud;drawHeart(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/gui/hud/InGameHud$HeartType;IIIZZ)V"))
    private void modifyArgs(Args args) {
        if (player != null && player.hasStatusEffect(StatusEffects.BLEEDING) && args.get(4).equals(0)) {
            args.set(4,9*5);
        }
    }
}
