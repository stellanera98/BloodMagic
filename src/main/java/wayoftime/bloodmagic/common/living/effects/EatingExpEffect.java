package wayoftime.bloodmagic.common.living.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import wayoftime.bloodmagic.common.living.LivingEntityEffect;
import wayoftime.bloodmagic.common.living.LivingHelper;
import wayoftime.bloodmagic.common.living.LivingUpgrade;

public record EatingExpEffect(Holder<LivingUpgrade> upgrade) implements LivingEntityEffect {
    public static final MapCodec<EatingExpEffect> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            LivingUpgrade.HOLDER_CODEC.fieldOf("upgrade").forGetter(EatingExpEffect::upgrade)
    ).apply(builder, EatingExpEffect::new));

    @Override
    public void apply(ServerLevel level, int upgradeLevel, Entity entity) {
        Player wearer = (Player) entity;
        int last = wearer.getFoodData().getLastFoodLevel();
        int current = wearer.getFoodData().getFoodLevel();
        LivingHelper.applyExp(wearer, upgrade, Math.max(current - last, 0));
    }

    @Override
    public MapCodec<? extends LivingEntityEffect> codec() {
        return CODEC;
    }
}
