package net.yeyito.particle;

import com.mojang.serialization.Codec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.*;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.yeyito.more_movement_options.MoreMovementOptions;

import java.util.function.Function;

public class Particles {
    public static final DefaultParticleType BLEED_CRIT = FabricParticleTypes.simple(true);

    public static void registerParticles() {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(MoreMovementOptions.MOD_ID,"bleed_crit"),BLEED_CRIT);
    }

    @Environment(EnvType.CLIENT)
    public static void clientRegisterParticles() {
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier(MoreMovementOptions.MOD_ID,"particle/bleed_crit"));
        }));

        ParticleFactoryRegistry.getInstance().register(BLEED_CRIT, BleedParticle.Factory::new);
    }
}
