package net.yeyito.more_movement_options.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3f;
import net.yeyito.event.server.ServerTickEvent;
import net.yeyito.server.server_storage.ServerPlayerInfo;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(LivingEntity.class)
public abstract class PlayerAttackMixin {
    @Shadow @Nullable public abstract LivingEntity getAttacker();

    @Shadow @Nullable private LivingEntity attacker;

    @ModifyArgs(method = "takeKnockback", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setVelocity(DDD)V"))
    private void Injected(Args args,double strength, double x, double z) {
        if (getAttacker() != null && !getAttacker().world.isClient && getAttacker() != null && getAttacker() instanceof PlayerEntity) {
            double knockbackTimeForgiveness = 1.33333;

            System.out.println(ServerPlayerInfo.playerToTimeSinceLastDash.get(getAttacker().getName().getString()));
            System.out.println("PlayerAttackMixin");
            int ticksSinceLastDash = ServerTickEvent.getServerTickCounter() - ServerPlayerInfo.playerToTimeSinceLastDash.get(getAttacker().getName().getString());
            ticksSinceLastDash = (int) Math.floor((double) ticksSinceLastDash/knockbackTimeForgiveness);
            if (ticksSinceLastDash > 10) {return;}
            if (ticksSinceLastDash == 0) {ticksSinceLastDash = 1;}

            String dashType = ServerPlayerInfo.playerToLastDashType.get(getAttacker().getName().getString());

            Vec3f forward = new Vec3f((float) getAttacker().getRotationVecClient().getX(),0F,(float) getAttacker().getRotationVecClient().getZ());
            Vec3f left = new Vec3f(forward.getZ(),forward.getY(),-forward.getX());
            Vec3f right = new Vec3f(-forward.getZ(),-forward.getY(),forward.getX());
            Vec3f backward = new Vec3f((float) -getAttacker().getRotationVecClient().getX(),0F,(float) -getAttacker().getRotationVecClient().getZ());

            switch (dashType) {
                case "Left" -> args.setAll((double) args.get(0) + (left.getX()/ticksSinceLastDash),args.get(1),(double) args.get(2) + (left.getZ()/ticksSinceLastDash)); // go left relative to the attacker's camera position, TIME FOR SOME VECTOR CALCULATIONS WOOO!
                case "Right" -> args.setAll((double) args.get(0) + (right.getX()/ticksSinceLastDash),args.get(1),(double) args.get(2) + (right.getZ()/ticksSinceLastDash)); // go right relative to the attacker's camera position, TIME FOR SOME VECTOR CALCULATIONS WOOO!
                case "Backward" -> args.setAll((double) args.get(0) + (backward.getX()*2/ticksSinceLastDash),args.get(1),(double) args.get(2) + (backward.getZ()*2/ticksSinceLastDash)); // go backward relative to the attacker's camera position, TIME FOR SOME VECTOR CALCULATIONS WOOO!
                case "Forward" -> args.setAll((double) args.get(0) + (forward.getX()/ticksSinceLastDash),args.get(1),(double) args.get(2) + (forward.getZ()/ticksSinceLastDash)); // go forward relative to the attacker's camera position, TIME FOR SOME VECTOR CALCULATIONS WOOO!
            }
        }
    }
}
