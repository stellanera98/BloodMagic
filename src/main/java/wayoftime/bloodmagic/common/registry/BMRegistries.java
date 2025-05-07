package wayoftime.bloodmagic.common.registry;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.living.LivingUpgrade;
import wayoftime.bloodmagic.common.living.effects.DamageBasedEffect;
import wayoftime.bloodmagic.common.living.effects.EntityEffect;
import wayoftime.bloodmagic.common.living.effects.StandaloneEffect;
import wayoftime.bloodmagic.common.living.effects.ValueBasedEffect;
import wayoftime.bloodmagic.util.AltarUtil;

import java.security.Key;
import java.util.Map;

public class BMRegistries {
    public static class Keys {
        public static final ResourceKey<Registry<DataComponentType<?>>> LIVING_EFFECT_COMPONENTS = ResourceKey.createRegistryKey(bm("living_effect_component"));
        public static final ResourceKey<Registry<MapCodec<? extends StandaloneEffect>>> STANDALONE_EFFECT_TYPE = ResourceKey.createRegistryKey(bm("standalone_effect"));
        public static final ResourceKey<Registry<MapCodec<? extends DamageBasedEffect>>> DAMAGE_BASED_EFFECT_TYPE = ResourceKey.createRegistryKey(bm("damage_based_effect_type"));
        public static final ResourceKey<Registry<MapCodec<? extends ValueBasedEffect>>> VALUE_BASED_EFFECT_TYPE = ResourceKey.createRegistryKey(bm("value_based_effect_type"));
        public static final ResourceKey<Registry<MapCodec<? extends EntityEffect>>> ENTITY_EFFECT_TYPE = ResourceKey.createRegistryKey(bm("entity_effect_type"));

        public static final ResourceKey<Registry<LivingUpgrade>> LIVING_UPGRADES = ResourceKey.createRegistryKey(bm("living_upgrades"));
        public static final ResourceKey<LivingUpgrade> LIVING_EXP = ResourceKey.create(Keys.LIVING_UPGRADES, bm("living_exp"));
        public static final ResourceKey<Registry<AltarTier>> ALTAR_TIER_KEY = ResourceKey.createRegistryKey(bm("altar_tier"));

        public static final ResourceKey<DamageType> SACRIFICE_DAMAGE_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, bm("sacrifice_damage"));
    }

    public static final Registry<DataComponentType<?>> LIVING_EFFECT_COMPONENTS_REGISTRY = new RegistryBuilder<>(Keys.LIVING_EFFECT_COMPONENTS).create();
    public static final Registry<MapCodec<? extends StandaloneEffect>> STANDALONE_EFFECT_TYPE_REGISTRY = new RegistryBuilder<>(Keys.STANDALONE_EFFECT_TYPE).create();
    public static final Registry<MapCodec<? extends DamageBasedEffect>> DAMAGE_BASED_EFFECT_TYPE_REGISTRY = new RegistryBuilder<>(Keys.DAMAGE_BASED_EFFECT_TYPE).create();
    public static final Registry<MapCodec<? extends ValueBasedEffect>> VALUE_BASED_EFFECT_TYPE_REGISTRY = new RegistryBuilder<>(Keys.VALUE_BASED_EFFECT_TYPE).create();
    public static final Registry<MapCodec<? extends EntityEffect>> ENTITY_EFFECT_TYPE_REGISTRY = new RegistryBuilder<>(Keys.ENTITY_EFFECT_TYPE).create();

    private static void registerPack(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(Keys.ALTAR_TIER_KEY, AltarTier.CODEC);
        event.dataPackRegistry(Keys.LIVING_UPGRADES, LivingUpgrade.CODEC, LivingUpgrade.CODEC);
    }

    private static void registerBuiltIn(NewRegistryEvent event) {
        event.register(LIVING_EFFECT_COMPONENTS_REGISTRY);
        event.register(STANDALONE_EFFECT_TYPE_REGISTRY);
        event.register(DAMAGE_BASED_EFFECT_TYPE_REGISTRY);
        event.register(VALUE_BASED_EFFECT_TYPE_REGISTRY);
        event.register(ENTITY_EFFECT_TYPE_REGISTRY);
    }

    public static void register(IEventBus modBus) {
        modBus.addListener(BMRegistries::registerPack);
        modBus.addListener(BMRegistries::registerBuiltIn);
    }

    private static ResourceLocation bm(String path) {
        return ResourceLocation.fromNamespaceAndPath(BloodMagic.MODID, path);
    }
}
