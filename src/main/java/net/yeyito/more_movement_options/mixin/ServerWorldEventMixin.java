package net.yeyito.more_movement_options.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.GameEvent;
import net.yeyito.damage_source.DamageSources;
import net.yeyito.status_effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public abstract class ServerWorldEventMixin {
    @Shadow public abstract <T extends ParticleEffect> int spawnParticles(T particle, double x, double y, double z, int count, double deltaX, double deltaY, double deltaZ, double speed);

    @Inject(method = "emitGameEvent",at = @At("HEAD"))
    private void emitGameEventInject(GameEvent event, Vec3d emitterPos, GameEvent.Emitter emitter, CallbackInfo ci) {
        if (event == GameEvent.ENTITY_DAMAGE) {
            assert emitter.sourceEntity() != null;
            System.out.println(emitter.sourceEntity().getName().getString() + " was damaged");
        }
        if (event == GameEvent.ENTITY_DAMAGE && emitter.sourceEntity() instanceof LivingEntity entity && entity.getActiveStatusEffects().get(StatusEffects.BLEEDING) != null) {
            System.out.println(entity.getName().getString() + " bleed hit");

            this.spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.REDSTONE_BLOCK.getDefaultState()),entity.getX(),entity.getY()+1,entity.getZ(),25,0,0,0,0);
            entity.playSound(SoundEvents.BLOCK_SCULK_BREAK, 2, -1);
        }
    }
}
