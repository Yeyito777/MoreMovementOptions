package net.yeyito.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

public class BleedParticle extends SpriteBillboardParticle {
    BleedParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        super(clientWorld, d, e, f, 0.0D, 0.0D, 0.0D);
        this.velocityMultiplier = 0.7F;
        this.gravityStrength = 0.5F;
        this.velocityX *= 0.10000000149011612D;
        this.velocityY *= 0.10000000149011612D;
        this.velocityZ *= 0.10000000149011612D;
        this.velocityX += g * 0.4D;
        this.velocityY += h * 0.4D;
        this.velocityZ += i * 0.4D;
        float j = (float)(Math.random() * 0.10000001192092896D + 0.3000000238418579D);
        this.red = 255-j;
        this.green = 0;
        this.blue = 0;
        this.scale *= 0.75F;
        this.maxAge = Math.max((int)(6.0D / (Math.random() * 0.8D + 0.6D)), 1);
        this.collidesWithWorld = false;
        this.tick();
    }

    public float getSize(float tickDelta) {
        return this.scale * MathHelper.clamp(((float)this.age + tickDelta) / (float)this.maxAge * 32.0F, 0.0F, 1.0F);
    }

    public void tick() {
        super.tick();
        this.green *= 0.96F;
        this.blue *= 0.9F;
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Environment(EnvType.CLIENT)
    public static class DefaultFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public DefaultFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            BleedParticle bleedParticle = new BleedParticle(clientWorld, d, e, f, g, h + 1.0D, i);
            bleedParticle.setMaxAge(20);
            bleedParticle.setSprite(this.spriteProvider);
            return bleedParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class EnchantedHitFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public EnchantedHitFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            BleedParticle bleedParticle = new BleedParticle(clientWorld, d, e, f, g, h, i);
            bleedParticle.red *= 0.3F;
            bleedParticle.green *= 0.8F;
            bleedParticle.setSprite(this.spriteProvider);
            return bleedParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            BleedParticle bleedParticle = new BleedParticle(clientWorld, d, e, f, g, h, i);
            bleedParticle.setSprite(this.spriteProvider);
            return bleedParticle;
        }
    }
}
