package wayoftime.bloodmagic.common.living.effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.registry.BMRegistries;

import java.util.function.Function;

import static wayoftime.bloodmagic.common.registry.BMRegistries.VALUE_BASED_EFFECT_TYPE_REGISTRY;

public interface ValueBasedEffect {
    Codec<ValueBasedEffect> CODEC = VALUE_BASED_EFFECT_TYPE_REGISTRY.byNameCodec().dispatch(ValueBasedEffect::codec, Function.identity());

    DeferredRegister<MapCodec<? extends ValueBasedEffect>> VALUE_BASED_EFFECT_TYPE = DeferredRegister.create(BMRegistries.Keys.VALUE_BASED_EFFECT_TYPE, BloodMagic.MODID);

    DeferredHolder<MapCodec<? extends ValueBasedEffect>, MapCodec<ReductionMultiplyValueEffect>> VALUE_REDUCTION_EFFECT = VALUE_BASED_EFFECT_TYPE.register("value_reduction_effect", () -> ReductionMultiplyValueEffect.CODEC);
    DeferredHolder<MapCodec<? extends ValueBasedEffect>, MapCodec<MultiplyValueEffect>> MULTIPLY_VALUE_EFFECT = VALUE_BASED_EFFECT_TYPE.register("multiply_value_effect", () -> MultiplyValueEffect.CODEC);

    DeferredHolder<MapCodec<? extends ValueBasedEffect>, MapCodec<ValueBasedExpGain>> LIVING_EXP = VALUE_BASED_EFFECT_TYPE.register("living_exp", () -> ValueBasedExpGain.CODEC);

    float process(int level, Player wearer, float in);

    MapCodec<? extends ValueBasedEffect> codec();
}
