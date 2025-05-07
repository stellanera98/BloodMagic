package wayoftime.bloodmagic.common.living.effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public record CauseExhaustionEffect(List<Float> amounts) implements StandaloneEffect {
    public static final MapCodec<CauseExhaustionEffect> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            Codec.FLOAT.listOf().fieldOf("exhaustion").forGetter(CauseExhaustionEffect::amounts)
    ).apply(builder, CauseExhaustionEffect::new));

    @Override
    public void apply(int level, Player wearer) {
        wearer.causeFoodExhaustion(amounts.get(level));
    }

    @Override
    public MapCodec<? extends StandaloneEffect> codec() {
        return CODEC;
    }
}
