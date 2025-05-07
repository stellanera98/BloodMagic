package wayoftime.bloodmagic.common.living.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.world.entity.player.Player;
import wayoftime.bloodmagic.common.living.LivingHelper;
import wayoftime.bloodmagic.common.living.LivingUpgrade;
import wayoftime.bloodmagic.common.registry.BMRegistries;

public record EatingExpEffect(Holder<LivingUpgrade> upgrade) implements StandaloneEffect {
    public static final MapCodec<EatingExpEffect> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            RegistryFixedCodec.create(BMRegistries.Keys.LIVING_UPGRADES).fieldOf("upgrade").forGetter(EatingExpEffect::upgrade)
    ).apply(builder, EatingExpEffect::new));

    @Override
    public void apply(int level, Player wearer) {
        int last = wearer.getFoodData().getLastFoodLevel();
        int current = wearer.getFoodData().getFoodLevel();
        LivingHelper.applyExp(wearer, upgrade, Math.max(current - last, 0));
    }

    @Override
    public MapCodec<? extends StandaloneEffect> codec() {
        return CODEC;
    }
}
