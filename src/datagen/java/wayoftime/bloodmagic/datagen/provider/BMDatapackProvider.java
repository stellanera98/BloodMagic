package wayoftime.bloodmagic.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.registry.BMRegistries;
import wayoftime.bloodmagic.datagen.builders.AltarTierBuilder;
import wayoftime.bloodmagic.datagen.builders.DamageTypes;
import wayoftime.bloodmagic.datagen.builders.LivingUpgrades;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class BMDatapackProvider extends DatapackBuiltinEntriesProvider {
    public BMDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, new RegistrySetBuilder()
                        .add(BMRegistries.Keys.ALTAR_TIER_KEY, AltarTierBuilder::bootstrap)
                        .add(BMRegistries.Keys.LIVING_UPGRADES, LivingUpgrades::builder)
                        .add(Registries.DAMAGE_TYPE, DamageTypes::bootstrap),
                Set.of(BloodMagic.MODID)
        );
    }
}
