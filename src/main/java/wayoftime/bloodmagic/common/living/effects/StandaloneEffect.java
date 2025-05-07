package wayoftime.bloodmagic.common.living.effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.registry.BMRegistries;

import java.util.function.Function;

public interface StandaloneEffect {
    Codec<StandaloneEffect> CODEC = BMRegistries.STANDALONE_EFFECT_TYPE_REGISTRY.byNameCodec().dispatch(StandaloneEffect::codec, Function.identity());
    DeferredRegister<MapCodec<? extends StandaloneEffect>> STANDALONE_EFFECT_TYPE = DeferredRegister.create(BMRegistries.Keys.STANDALONE_EFFECT_TYPE, BloodMagic.MODID);

    DeferredHolder<MapCodec<? extends StandaloneEffect>, MapCodec<AddMobEffect>> ADD_MOB_EFFECT = STANDALONE_EFFECT_TYPE.register("add_mob_effect", () -> AddMobEffect.CODEC);
    DeferredHolder<MapCodec<? extends StandaloneEffect>, MapCodec<RemoveMobEffect>> REMOVE_MOB_EFFECT = STANDALONE_EFFECT_TYPE.register("remove_mob_effect", () -> RemoveMobEffect.CODEC);
    DeferredHolder<MapCodec<? extends StandaloneEffect>, MapCodec<CooldownEffect>> COOLDOWN_EFFECT = STANDALONE_EFFECT_TYPE.register("cooldown_effect", () -> CooldownEffect.CODEC);
    DeferredHolder<MapCodec<? extends StandaloneEffect>, MapCodec<ResetCooldownEffect>> RESET_COOLDOWN_EFFECT = STANDALONE_EFFECT_TYPE.register("reset_cooldown_effect", () -> ResetCooldownEffect.CODEC);
    DeferredHolder<MapCodec<? extends StandaloneEffect>, MapCodec<CauseExhaustionEffect>> CAUSE_EXHAUSTION_EFFECT = STANDALONE_EFFECT_TYPE.register("cause_exhaustion_effect", () -> CauseExhaustionEffect.CODEC);
    DeferredHolder<MapCodec<? extends StandaloneEffect>, MapCodec<RepairRandomArmourItemEffect>> REPAIR_ARMOUR_EFFECT = STANDALONE_EFFECT_TYPE.register("repair_armour_effect", () -> RepairRandomArmourItemEffect.CODEC);

    DeferredHolder<MapCodec<? extends StandaloneEffect>, MapCodec<StandaloneExpGain>> LIVING_EXP = STANDALONE_EFFECT_TYPE.register("living_exp", () -> StandaloneExpGain.CODEC);
    DeferredHolder<MapCodec<? extends StandaloneEffect>, MapCodec<EatingExpEffect>> EATING_EXP = STANDALONE_EFFECT_TYPE.register("eating_exp", () -> EatingExpEffect.CODEC);
    DeferredHolder<MapCodec<? extends StandaloneEffect>, MapCodec<ItemDamageBasedExpGain>> REPAIRING_EXP = STANDALONE_EFFECT_TYPE.register("repairing_exp", () -> ItemDamageBasedExpGain.CODEC);
    DeferredHolder<MapCodec<? extends StandaloneEffect>, MapCodec<DistanceExpGain>> DISTANCE_EXP = STANDALONE_EFFECT_TYPE.register("distance_exp", () -> DistanceExpGain.CODEC);

    void apply(int level, Player wearer);

    MapCodec<? extends StandaloneEffect> codec();
}
