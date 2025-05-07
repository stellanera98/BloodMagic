package wayoftime.bloodmagic.common.living.effects;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public record AddMobEffect(List<Pair<Integer, Integer>> power, Holder<MobEffect> effect) implements StandaloneEffect {
    public static final MapCodec<AddMobEffect> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            Codec.pair(
                    Codec.INT.fieldOf("duration").codec(),
                    Codec.INT.fieldOf("amplifier").codec()
            ).listOf().fieldOf("power").forGetter(AddMobEffect::power),
            BuiltInRegistries.MOB_EFFECT.holderByNameCodec().fieldOf("effect").forGetter(AddMobEffect::effect)
    ).apply(builder, AddMobEffect::new));

    @Override
    public void apply(int level, Player wearer) {
        wearer.addEffect(new MobEffectInstance(effect, power.get(level).getFirst(), power.get(level).getSecond()));
    }

    @Override
    public MapCodec<? extends StandaloneEffect> codec() {
        return CODEC;
    }
}
