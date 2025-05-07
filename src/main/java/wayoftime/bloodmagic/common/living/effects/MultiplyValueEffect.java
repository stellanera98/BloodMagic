package wayoftime.bloodmagic.common.living.effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public record MultiplyValueEffect(List<Float> amounts) implements ValueBasedEffect {
    public static final MapCodec<MultiplyValueEffect> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            Codec.FLOAT.listOf().fieldOf("amounts").forGetter(MultiplyValueEffect::amounts)
    ).apply(builder, MultiplyValueEffect::new));

    @Override
    public float process(int level, Player wearer, float in) {
        return amounts.get(level) * (1 + in);
    }

    @Override
    public MapCodec<? extends ValueBasedEffect> codec() {
        return CODEC;
    }
}
