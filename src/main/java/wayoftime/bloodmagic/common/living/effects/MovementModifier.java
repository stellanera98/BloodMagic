package wayoftime.bloodmagic.common.living.effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public record MovementModifier(List<Double> amounts) implements EntityEffect {
    public static final MapCodec<MovementModifier> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            Codec.DOUBLE.listOf().fieldOf("amounts").forGetter(MovementModifier::amounts)
    ).apply(builder, MovementModifier::new));

    @Override
    public void apply(int level, Player wearer, Projectile projectile) {
        Vec3 mov = projectile.getDeltaMovement();
        double jiggle = amounts().get(level) * Math.sqrt(mov.x * mov.x + mov.y * mov.y + mov.z + mov.z);
        RandomSource source = wearer.level().random;
        projectile.setDeltaMovement(mov.add(rand(source, jiggle), rand(source, jiggle), rand(source, jiggle)));
    }

    private double rand(RandomSource source, double jiggle) {
        return 2 * (source.nextDouble() - 0.5) * jiggle;
    }

    @Override
    public MapCodec<? extends EntityEffect> codec() {
        return CODEC;
    }
}
