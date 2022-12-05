package net.yeyito.sounds;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.yeyito.more_movement_options.MoreMovementOptions;

public class ModSounds {
    public static final Identifier DASH_ID = new Identifier("more_movement_options:dash");
    public static SoundEvent DASH = new SoundEvent(DASH_ID);

    public static void register() {
        Registry.register(Registry.SOUND_EVENT, DASH_ID, DASH);
    }
}
