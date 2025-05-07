package wayoftime.bloodmagic.common.living.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.world.entity.player.Player;
import wayoftime.bloodmagic.common.living.LivingHelper;
import wayoftime.bloodmagic.common.living.LivingUpgrade;
import wayoftime.bloodmagic.common.registry.BMRegistries;

public record ValueBasedExpGain(Holder<LivingUpgrade> upgrade) implements ValueBasedEffect {
    public static final MapCodec<ValueBasedExpGain> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            RegistryFixedCodec.create(BMRegistries.Keys.LIVING_UPGRADES).fieldOf("upgrades").forGetter(ValueBasedExpGain::upgrade)
    ).apply(builder, ValueBasedExpGain::new));

    @Override
    public float process(int level, Player wearer, float in) {
        LivingHelper.applyExp(wearer, upgrade, in);
        return in;
    }

    @Override
    public MapCodec<? extends ValueBasedEffect> codec() {
        return CODEC;
    }
}
