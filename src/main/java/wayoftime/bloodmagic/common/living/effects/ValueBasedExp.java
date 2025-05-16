package wayoftime.bloodmagic.common.living.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import wayoftime.bloodmagic.common.living.LivingHelper;
import wayoftime.bloodmagic.common.living.LivingUpgrade;
import wayoftime.bloodmagic.common.living.LivingValueEffect;

public record ValueBasedExp(Holder<LivingUpgrade> upgrade, LevelBasedValue multiplier) implements LivingValueEffect {
    public static final MapCodec<ValueBasedExp> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            LivingUpgrade.HOLDER_CODEC.fieldOf("upgrade").forGetter(ValueBasedExp::upgrade),
            LevelBasedValue.CODEC.fieldOf("multiplier").forGetter(ValueBasedExp::multiplier)
    ).apply(builder, ValueBasedExp::new));

    @Override
    public float process(int level, RandomSource random, LootContext lootContext, float value) {
        Player player = (Player) lootContext.getParam(LootContextParams.THIS_ENTITY);
        LivingHelper.applyExp(player, upgrade, value * multiplier.calculate(level));
        return 0;
    }

    @Override
    public MapCodec<? extends LivingValueEffect> codec() {
        return CODEC;
    }
}
