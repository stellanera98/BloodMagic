package wayoftime.bloodmagic.common.living.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.level.storage.loot.LootContext;
import wayoftime.bloodmagic.common.living.LivingValueEffect;

public record MultiplyReduceValue(LevelBasedValue amounts) implements LivingValueEffect {
    public static final MapCodec<MultiplyReduceValue> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            LevelBasedValue.CODEC.fieldOf("amounts").forGetter(MultiplyReduceValue::amounts)
    ).apply(builder, MultiplyReduceValue::new));

    @Override
    public float process(int level, LootContext lootContext, float value) {
        return (1 - amounts.calculate(level));
    }

    @Override
    public MapCodec<? extends LivingValueEffect> codec() {
        return CODEC;
    }
}
