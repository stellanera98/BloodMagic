package wayoftime.bloodmagic.common.living.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.world.entity.player.Player;
import wayoftime.bloodmagic.common.living.LivingHelper;
import wayoftime.bloodmagic.common.living.LivingUpgrade;
import wayoftime.bloodmagic.common.registry.BMRegistries;

public record StandaloneExpGain(Holder<LivingUpgrade> upgrade) implements StandaloneEffect {
    public static final MapCodec<StandaloneExpGain> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            RegistryFixedCodec.create(BMRegistries.Keys.LIVING_UPGRADES).fieldOf("upgrades").forGetter(StandaloneExpGain::upgrade)
    ).apply(builder, StandaloneExpGain::new));

    @Override
    public void apply(int level, Player wearer) {
        LivingHelper.applyExp(wearer, upgrade, 1);
    }

    @Override
    public MapCodec<? extends StandaloneEffect> codec() {
        return CODEC;
    }
}
