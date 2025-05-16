package wayoftime.bloodmagic.common.living;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.storage.loot.LootContext;
import net.neoforged.neoforge.registries.DeferredRegister;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.living.effects.*;
import wayoftime.bloodmagic.common.registry.BMRegistries;

import java.util.function.Function;
import java.util.function.Supplier;

public interface LivingValueEffect {
    Codec<LivingValueEffect> CODEC = BMRegistries.VALUE_BASED_EFFECT_TYPE_REGISTRY
        .byNameCodec()
        .dispatch(LivingValueEffect::codec, Function.identity());

    DeferredRegister<MapCodec<? extends LivingValueEffect>> VALUE_BASED_EFFECT_TYPE = DeferredRegister.create(BMRegistries.Keys.VALUE_BASED_EFFECT_TYPE, BloodMagic.MODID);

    Supplier<MapCodec<DelegateEffect>> DELEGATE = VALUE_BASED_EFFECT_TYPE.register("delegate", () -> DelegateEffect.CODEC);
    Supplier<MapCodec<MultiplyReduceValue>> MULTIPLY_REDUCTION = VALUE_BASED_EFFECT_TYPE.register("multiply_reduce", () -> MultiplyReduceValue.CODEC);
    Supplier<MapCodec<MultiplyIncreaseValue>> MULTIPLY = VALUE_BASED_EFFECT_TYPE.register("multiply_increase", () -> MultiplyIncreaseValue.CODEC);
    Supplier<MapCodec<AddValue>> ADD = VALUE_BASED_EFFECT_TYPE.register("add_value", () -> AddValue.CODEC);

    Supplier<MapCodec<ValueBasedExp>> VALUE_BASED_EXP = VALUE_BASED_EFFECT_TYPE.register("living_exp", () -> ValueBasedExp.CODEC);

    float process(int level, RandomSource random, LootContext lootContext, float value);

    MapCodec<? extends LivingValueEffect> codec();
}
