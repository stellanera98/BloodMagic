package wayoftime.bloodmagic.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.registry.AltarTier;
import wayoftime.bloodmagic.common.registry.BMRegistries;
import wayoftime.bloodmagic.datagen.builders.AltarTierBuilder;
import wayoftime.bloodmagic.datagen.builders.AltarTierBuilder.Keys;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class BMDatapackProvider extends DatapackBuiltinEntriesProvider {
    public BMDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, new RegistrySetBuilder()
                        .add(BMRegistries.ALTAR_TIER_KEY, builder -> {
                            builder.register(Keys.WEAK, new AltarTier(0, AltarTierBuilder.WEAK));
                            builder.register(Keys.APPRENTICE, new AltarTier(1, AltarTierBuilder.APPRENTICE));
                            builder.register(Keys.MAGE, new AltarTier(2, AltarTierBuilder.MAGE));
                            builder.register(Keys.MASTER, new AltarTier(3, AltarTierBuilder.MASTER));
                            builder.register(Keys.ARCHMAGE, new AltarTier(4, AltarTierBuilder.ARCHMAGE));
                            builder.register(Keys.TRANSCENDENT, new AltarTier(5, AltarTierBuilder.TRANSCENDENT));
                        })
                        .add(Registries.DAMAGE_TYPE, builder -> {
                            builder.register(BMRegistries.SACRIFICE_DAMAGE_KEY, new DamageType(
                                    BMRegistries.SACRIFICE_DAMAGE_KEY.location().getPath(),
                                    DamageScaling.NEVER,
                                    0F
                                    )
                            );
                        }),
                Set.of(BloodMagic.MODID));
    }
}
