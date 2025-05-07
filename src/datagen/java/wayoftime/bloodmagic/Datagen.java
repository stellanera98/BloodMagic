package wayoftime.bloodmagic;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import wayoftime.bloodmagic.common.registry.BMRegistries;
import wayoftime.bloodmagic.common.tag.BMTags;
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

        generator.addProvider(event.includeServer(), new BMLootTableProvider(output, registries));
        generator.addProvider(event.includeServer(), new BMDataMapProvider(output, registries));

        BMDatapackProvider packProvider = new BMDatapackProvider(output, registries);
        generator.addProvider(event.includeServer(), packProvider);
        // TODO should be relatively simple to condense to something like BMTagsProvider.tags(key, output, provider, exFiHe, Consumer)
        generator.addProvider(event.includeServer(), new BMAltarTierTagProvider(output, packProvider.getRegistryProvider(), existingFileHelper));
        generator.addProvider(event.includeServer(), new TagsProvider<DamageType>(output, Registries.DAMAGE_TYPE, packProvider.getRegistryProvider(), BloodMagic.MODID, existingFileHelper) {
            @Override
            protected void addTags(HolderLookup.Provider provider) {
                tag(BMTags.Damage.SELF_SACRIFICE).add(BMRegistries.Keys.SACRIFICE_DAMAGE_KEY);
            }
        });

        generator.addProvider(event.includeServer(), new BMRecipeProvider(output, registries));
    }
}
