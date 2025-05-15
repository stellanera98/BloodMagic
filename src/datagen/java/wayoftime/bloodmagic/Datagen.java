package wayoftime.bloodmagic;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import wayoftime.bloodmagic.common.registry.BMRegistries;
import wayoftime.bloodmagic.datagen.builders.AltarTierBuilder;
import wayoftime.bloodmagic.datagen.builders.DamageTypes;
import wayoftime.bloodmagic.datagen.providers.*;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class Datagen {

    public static final String PACKS = "packs/";

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> registries = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new BMLanguageProvider(output, "en_us"));

        generator.addProvider(event.includeClient(), new BMBlockstateProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new BMItemModelProvider(output, existingFileHelper));

        BlockTagsProvider blockTagsProvider = generator.addProvider(event.includeServer(), new BMBlockTagProvider(output, registries, existingFileHelper));
        generator.addProvider(event.includeServer(), new BMItemTagProvider(output, registries, blockTagsProvider.contentsGetter(), existingFileHelper));

        generator.addProvider(event.includeServer(), new BMRecipeProvider(output, registries));

        generator.addProvider(event.includeServer(), new BMLootTableProvider(output, registries));
        generator.addProvider(event.includeServer(), new BMDataMapProvider(output, registries));

        BMDatapackProvider packProvider = generator.addProvider(event.includeServer(), new BMDatapackProvider(output, registries));
        CompletableFuture<HolderLookup.Provider> provider = packProvider.getRegistryProvider();
        BMTagsProvider tags = new BMTagsProvider(output, existingFileHelper);
        generator.addProvider(event.includeServer(), tags.setup(provider, BMRegistries.Keys.ALTAR_TIER_KEY, AltarTierBuilder::tags));
        generator.addProvider(event.includeServer(), tags.setup(provider, Registries.DAMAGE_TYPE, DamageTypes::tags));
    }
}
