package wayoftime.bloodmagic.common.living.effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.world.entity.player.Player;
import wayoftime.bloodmagic.common.living.LivingHelper;
import wayoftime.bloodmagic.common.living.LivingUpgrade;
import wayoftime.bloodmagic.common.registry.BMRegistries;

public record DistanceExpGain(Holder<LivingUpgrade> upgrade, Movement movement) implements StandaloneEffect {
    public static final MapCodec<DistanceExpGain> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            RegistryFixedCodec.create(BMRegistries.Keys.LIVING_UPGRADES).fieldOf("upgrade").forGetter(DistanceExpGain::upgrade),
            Movement.CODEC.fieldOf("movement").forGetter(DistanceExpGain::movement)
    ).apply(builder, DistanceExpGain::new));

    @Override
    public void apply(int level, Player wearer) {
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
    public MapCodec<? extends StandaloneEffect> codec() {
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
