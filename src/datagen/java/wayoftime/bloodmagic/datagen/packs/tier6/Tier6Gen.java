package wayoftime.bloodmagic;

import net.minecraft.DetectedVersion;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.util.InclusiveRange;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import wayoftime.bloodmagic.datagen.providers.*;
import wayoftime.bloodmagic.tag.BMTags;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class Datagen {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> registries = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new BMLanguageProvider(output, "en_us"));
        generator.addProvider(event.includeClient(), new BMBlockstateProvider(output, existingFileHelper));

        generator.addProvider(event.includeServer(), new BMRecipeProvider(output, registries));
        generator.addProvider(event.includeServer(), new BMBlockTagProvider(output, registries, existingFileHelper));
        generator.addProvider(event.includeServer(), new BMLootTableProvider(output, registries));
        generator.addProvider(event.includeServer(), new BMDataMapProvider(output, registries));

        BMDatapackProvider packProvider = new BMDatapackProvider(output, registries);
        generator.addProvider(event.includeServer(), packProvider);
        generator.addProvider(event.includeServer(), new BMAltarTierTagProvider(output, packProvider.getRegistryProvider(), existingFileHelper));

        PackOutput packsBase = new PackOutput(output.getOutputFolder().resolve("packs"));
        PackOutput t6output = new PackOutput(packsBase.getOutputFolder().resolve("tier6"));

        generator.addProvider(event.includeServer(), new BlockTagsProvider(t6output, registries, BloodMagic.MODID+"_t6", existingFileHelper) {
            @Override
            protected void addTags(HolderLookup.Provider provider) {
                tag(BMTags.Blocks.T6_CAP);
            }
        });

        generator.addProvider(true, new PackMetadataGenerator(t6output)
                .add(PackMetadataSection.TYPE, new PackMetadataSection(
                        Component.translatable("pack.bloodmagic.t6.description"),
                        DetectedVersion.BUILT_IN.getPackVersion(PackType.SERVER_DATA),
                        Optional.of(new InclusiveRange<>(0, Integer.MAX_VALUE)))));
    }
}
