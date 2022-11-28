package net.yeyito.status_effect;

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
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.yeyito.event.server.ServerTickEvent;

import java.awt.*;
import java.util.Iterator;
import java.util.Map;

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
        if (amplifier > 10) {amplifier = 10;}

        return (ServerTickEvent.getServerTickCounter() - tickStart) % (20 / (amplifier + 1)) == 0;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.getWorld().isClient) {return;}
        tickStart = ServerTickEvent.getServerTickCounter();

        entity.damage(new DamageSource("Bleed"),0.001F);
        entity.setHealth(entity.getHealth()-1.0F);

        entity.playSound(SoundEvents.BLOCK_SCULK_BREAK,1,0);
        //entity.world.playSound(entity.getX(),entity.getY()+1,entity.getZ(), SoundEvents.BLOCK_SCULK_BREAK, SoundCategory.PLAYERS,1,1,true);
    }
}
