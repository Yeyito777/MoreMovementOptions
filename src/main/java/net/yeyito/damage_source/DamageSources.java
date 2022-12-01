package net.yeyito.damage_source;

import net.minecraft.entity.damage.DamageSource;

public class DamageSources {
    public static final DamageSource BLEED = new DamageSource("bleed")
            .setBypassesProtection()
            .setBypassesArmor()
            .setUnblockable();
}
