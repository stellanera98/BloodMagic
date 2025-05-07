package wayoftime.bloodmagic.common.living.effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.registry.BMRegistries;

import java.util.Map;
import java.util.function.Function;

public interface DamageBasedEffect {
    Codec<DamageBasedEffect> CODEC = BMRegistries.DAMAGE_BASED_EFFECT_TYPE_REGISTRY.byNameCodec().dispatch(DamageBasedEffect::codec, Function.identity());
    DeferredRegister<MapCodec<? extends DamageBasedEffect>> DAMAGE_BASED_EFFECT_TYPE = DeferredRegister.create(BMRegistries.DAMAGE_BASED_EFFECT_TYPE_REGISTRY, BloodMagic.MODID);

    DeferredHolder<MapCodec<? extends DamageBasedEffect>, MapCodec<DamageReductionEffect>> DAMAGE_REDUCTION_EFFECT = DAMAGE_BASED_EFFECT_TYPE.register("damage_reduction_effect", () -> DamageReductionEffect.CODEC);
    DeferredHolder<MapCodec<? extends DamageBasedEffect>, MapCodec<DamageIncreaseEffect>> DAMAGE_INCREASE_EFFECT = DAMAGE_BASED_EFFECT_TYPE.register("damage_increase_effect", () -> DamageIncreaseEffect.CODEC);
    DeferredHolder<MapCodec<? extends DamageBasedEffect>, MapCodec<DamageFlatIncreaseEffect>> DAMAGE_FLAT_INCREASE_EFFECT = DAMAGE_BASED_EFFECT_TYPE.register("damage_flat_increase_effect", () -> DamageFlatIncreaseEffect.CODEC);
    DeferredHolder<MapCodec<? extends DamageBasedEffect>, MapCodec<DamageBasedDelegateEffect>> DELEGATE_TO_STANDALONE = DAMAGE_BASED_EFFECT_TYPE.register("delegate_to_standalone", () -> DamageBasedDelegateEffect.CODEC);

    DeferredHolder<MapCodec<? extends DamageBasedEffect>, MapCodec<DamageBasedExpGain>> LIVING_EXP = DAMAGE_BASED_EFFECT_TYPE.register("damage_based_exp_gain", () -> DamageBasedExpGain.CODEC);

    void apply(int level, Player wearer, DamageContainer damage);

    MapCodec<? extends DamageBasedEffect> codec();
}
