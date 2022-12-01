package net.yeyito.status_effect;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.client.particle.BlockDustParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.event.GameEvent;
import net.yeyito.damage_source.DamageSources;
import net.yeyito.event.server.ServerTickEvent;

import java.awt.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class Bleeding extends StatusEffect {
    int tickStart = 0;

    public Bleeding() {
        super(StatusEffectCategory.HARMFUL, new Color(255,0,0).getRGB());
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        // Sound queue?
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        if (amplifier > 10 || amplifier < 0) {amplifier = 10;}

        return (ServerTickEvent.getServerTickCounter() - tickStart) % (20 / (amplifier + 1)) == 0;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!entity.getWorld().isClient) {
            tickStart = ServerTickEvent.getServerTickCounter();
            entity.getWorld().addBlockBreakParticles(new BlockPos(entity.getPos().add(0,1,0)), Blocks.REDSTONE_BLOCK.getDefaultState());

            entity.damage(DamageSources.BLEED, 1.0F);
        } else {
            entity.playSound(SoundEvents.BLOCK_SCULK_BREAK, 2, -1);
        }
    }
}
