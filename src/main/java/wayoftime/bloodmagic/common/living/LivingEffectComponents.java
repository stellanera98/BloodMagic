package wayoftime.bloodmagic.common.living;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.util.Unit;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.living.effects.*;
import wayoftime.bloodmagic.common.registry.BMRegistries;

import java.util.List;

import static wayoftime.bloodmagic.common.registry.BMRegistries.LIVING_EFFECT_COMPONENTS_REGISTRY;

public class LivingEffectComponents {
    public static final Codec<DataComponentType<?>> COMPONENT_CODEC = Codec.lazyInitialized(LIVING_EFFECT_COMPONENTS_REGISTRY::byNameCodec);
    public static final Codec<DataComponentMap> CODEC = DataComponentMap.makeCodec(COMPONENT_CODEC);
    public static final DeferredRegister.DataComponents LIVING_EFFECT_COMPONENTS = DeferredRegister.createDataComponents(BMRegistries.Keys.LIVING_EFFECT_COMPONENTS, BloodMagic.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<AttributeEffect>>> ATTRIBUTES = LIVING_EFFECT_COMPONENTS.registerComponentType("attributes", builder -> builder.persistent(AttributeEffect.CODEC.codec().listOf()));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<LivingValueEffect>>>> TAKING_DAMAGE_PRE = LIVING_EFFECT_COMPONENTS.registerComponentType("taking_damage", builder -> builder.persistent(ConditionalEffect.codec(LivingValueEffect.CODEC, LivingContextParamSets.DAMAGE_BASED).listOf()));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<LivingValueEffect>>>> DEALING_DAMAGE = LIVING_EFFECT_COMPONENTS.registerComponentType("dealing_damage", builder -> builder.persistent(ConditionalEffect.codec(LivingValueEffect.CODEC, LivingContextParamSets.DAMAGE_BASED).listOf()));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<LivingValueEffect>>>> KNOCKBACK = LIVING_EFFECT_COMPONENTS.registerComponentType("knockback", builder -> builder.persistent(ConditionalEffect.codec(LivingValueEffect.CODEC, LivingContextParamSets.DAMAGE_BASED).listOf()));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<LivingValueEffect>>>> DAMAGE_TAKEN_EXP = LIVING_EFFECT_COMPONENTS.registerComponentType("damage_taken_exp", builder -> builder.persistent(ConditionalEffect.codec(LivingValueEffect.CODEC, LivingContextParamSets.DAMAGE_BASED).listOf()));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<LivingValueEffect>>>> DAMAGE_DEALT_EXP = LIVING_EFFECT_COMPONENTS.registerComponentType("damage_dealt_exp", builder -> builder.persistent(ConditionalEffect.codec(LivingValueEffect.CODEC, LivingContextParamSets.DAMAGE_BASED).listOf()));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<LivingEntityEffect>>>> BREAK_BLOCK = LIVING_EFFECT_COMPONENTS.registerComponentType("break_block", builder -> builder.persistent(ConditionalEffect.codec(LivingEntityEffect.CODEC, LivingContextParamSets.BREAK_BLOCK).listOf()));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<LivingEntityEffect>>>> TICK = LIVING_EFFECT_COMPONENTS.registerComponentType("tick", builder -> builder.persistent(ConditionalEffect.codec(LivingEntityEffect.CODEC, LivingContextParamSets.TICK).listOf()));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<LivingEntityEffect>>>> PROJECTILE_SHOT = LIVING_EFFECT_COMPONENTS.registerComponentType("eating", builder -> builder.persistent(ConditionalEffect.codec(LivingEntityEffect.CODEC, LivingContextParamSets.PROJECTILE).listOf()));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<LivingValueEffect>>>> EXP_PICKUP = LIVING_EFFECT_COMPONENTS.registerComponentType("exp_pickup", builder -> builder.persistent(ConditionalEffect.codec(LivingValueEffect.CODEC, LivingContextParamSets.HEALING).listOf()));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<LivingValueEffect>>>> HEALING = LIVING_EFFECT_COMPONENTS.registerComponentType("healing", builder -> builder.persistent(ConditionalEffect.codec(LivingValueEffect.CODEC, LivingContextParamSets.HEALING).listOf()));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> MAKE_PIGLIN_NEUTRAL = LIVING_EFFECT_COMPONENTS.registerComponentType("make_piglin_neutral", builder -> builder.persistent(Unit.CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> ENABLE_ELYTRA = LIVING_EFFECT_COMPONENTS.registerComponentType("enable_elytra", builder -> builder.persistent(Unit.CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> QUENCHED = LIVING_EFFECT_COMPONENTS.registerComponentType("quenched", builder -> builder.persistent(Unit.CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> CRIPPLED_ARM = LIVING_EFFECT_COMPONENTS.registerComponentType("crippled_arm", builder -> builder.persistent(Unit.CODEC));
}
