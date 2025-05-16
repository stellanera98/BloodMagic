package wayoftime.bloodmagic.common.living.effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import wayoftime.bloodmagic.common.living.LivingEntityEffect;
import wayoftime.bloodmagic.common.living.LivingHelper;
import wayoftime.bloodmagic.common.living.LivingUpgrade;

public record DistanceExpGain(Holder<LivingUpgrade> upgrade, Movement movement) implements LivingEntityEffect {
    public static final MapCodec<DistanceExpGain> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            LivingUpgrade.HOLDER_CODEC.fieldOf("upgrade").forGetter(DistanceExpGain::upgrade),
            Movement.CODEC.fieldOf("movement").forGetter(DistanceExpGain::movement)
    ).apply(builder, DistanceExpGain::new));

    @Override
    public void apply(ServerLevel level, int upgradeLevel, Entity entity) {
        Player wearer = (Player) entity;
        double x = wearer.getDeltaMovement().x;
        double y = wearer.getDeltaMovement().y;
        double z = wearer.getDeltaMovement().z;
        double amount = switch (movement) {
            case VERTICAL -> Math.sqrt(x*x + z*z);
            case HORIZONTAL -> y;
        };
        if (amount > 0 && amount < 50) {
            LivingHelper.applyExp(wearer, upgrade, (float) amount);
        }
    }

    @Override
    public MapCodec<? extends LivingEntityEffect> codec() {
        return CODEC;
    }

    public enum Movement {
        HORIZONTAL,
        VERTICAL;
        /*
        X,
        Y,
        Z,
        ALL
         */

        public static final Codec<Movement> CODEC = Codec.stringResolver(Movement::toString, Movement::valueOf);
    }
}
