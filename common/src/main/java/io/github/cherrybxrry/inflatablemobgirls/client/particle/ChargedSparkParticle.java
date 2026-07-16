package io.github.cherrybxrry.inflatablemobgirls.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.util.RandomSource;
import org.jspecify.annotations.NonNull;

public class ChargedSparkParticle extends SimpleAnimatedParticle {
    protected ChargedSparkParticle(ClientLevel level, double x, double y, double z, double xa, double ya, double za, SpriteSet sprites) {
        super(level, x, y, z, sprites, 0.0125F);
        this.xd = xa;
        this.yd = ya;
        this.zd = za;
        this.quadSize *= 2F + (level.getRandom().nextFloat() * 0.5F);
        this.lifetime = 24 + this.random.nextInt(3);
        this.setFadeColor(15916745);
        this.setSpriteFromAge(sprites);
    }

    public void move(double xa, double ya, double za) {
        this.setBoundingBox(this.getBoundingBox().move(xa, ya, za));
        this.setLocationFromBoundingbox();
    }

    public int getLightCoords(float a) {
        return LightCoordsUtil.addSmoothBlockEmission(super.getLightCoords(a), ((float)this.age + a) / (float)this.lifetime);
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        public Particle createParticle(@NonNull SimpleParticleType options, @NonNull ClientLevel level, double x, double y, double z, double xAux, double yAux, double zAux, @NonNull RandomSource random) {
            return new ChargedSparkParticle(level, x, y, z, xAux, yAux, zAux, this.sprites);
        }
    }
}
