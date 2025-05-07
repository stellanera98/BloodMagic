package wayoftime.bloodmagic.common.living.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.damagesource.DamageContainer;

public record DamageBasedDelegateEffect(StandaloneEffect effect) implements DamageBasedEffect {
    public static final MapCodec<DamageBasedDelegateEffect> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            StandaloneEffect.CODEC.fieldOf("delegate").forGetter(DamageBasedDelegateEffect::effect)
    ).apply(builder, DamageBasedDelegateEffect::new));

    @Override
    public void apply(int level, Player wearer, DamageContainer damage) {
        effect.apply(level, wearer);
    }

    @Override
    public MapCodec<? extends DamageBasedEffect> codec() {
        return CODEC;
    }
}
