package wayoftime.bloodmagic.datagen.packs.tier6;

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
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.Datagen;
import wayoftime.bloodmagic.datagen.BlockGroups;
import wayoftime.bloodmagic.datagen.providers.*;
import wayoftime.bloodmagic.tag.BMTags;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class Tier6Gen {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> registries = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        PackOutput t6output = new PackOutput(output.getOutputFolder().resolve(Datagen.PACKS + "tier6"));

        generator.addProvider(true, new PackMetadataGenerator(t6output)
                .add(PackMetadataSection.TYPE, new PackMetadataSection(
                                Component.translatable("pack.bloodmagic.t6.description"),
                                DetectedVersion.BUILT_IN.getPackVersion(PackType.SERVER_DATA)
                        )
                )
        );

        generator.addProvider(event.includeServer(), new BlockTagsProvider(t6output, registries, "tier6", existingFileHelper) {
            @Override
            protected void addTags(HolderLookup.Provider provider) {
                tag(BMTags.Blocks.T6_CAP).addAll(BlockGroups.CRYSTAL_CLUSTER);
            }
        });
    }
}
