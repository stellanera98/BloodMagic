package wayoftime.bloodmagic.common.living.effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public record RemoveMobEffect(Holder<MobEffect> effect, List<Integer> maxCure) implements StandaloneEffect {
    public static final MapCodec<RemoveMobEffect> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            BuiltInRegistries.MOB_EFFECT.holderByNameCodec().fieldOf("effect").forGetter(RemoveMobEffect::effect),
            Codec.INT.listOf().fieldOf("max_cure").forGetter(RemoveMobEffect::maxCure)
    ).apply(builder, RemoveMobEffect::new));

    @Override
    public void apply(int level, Player wearer) {
        MobEffectInstance instance = wearer.getEffect(effect);
        if (instance.getAmplifier() <= maxCure().get(level)) {
            wearer.removeEffect(effect);
        }
    }

    @Override
    public MapCodec<? extends StandaloneEffect> codec() {
        return CODEC;
    }
}
