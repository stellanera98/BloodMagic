package wayoftime.bloodmagic;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import wayoftime.bloodmagic.api.BMIdentifiers;
import wayoftime.bloodmagic.common.registry.BMRegistries;
import wayoftime.bloodmagic.datagen.content.*;
import wayoftime.bloodmagic.datagen.provider.*;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class Datagen {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new BMItemModelProvider(output, fileHelper));
        generator.addProvider(event.includeClient(), new BMBlockstateProvider(output, fileHelper));

        event.createProvider(BMLanguageProvider::new);

        event.createDatapackRegistryObjects(new RegistrySetBuilder()
                .add(Registries.DAMAGE_TYPE, BloodyDamageSources::damageSources)
                .add(BMRegistries.Keys.ALTAR_TIER_KEY, AltarTiers::tiers)
                .add(BMRegistries.Keys.LIVING_UPGRADES, LivingUpgrades::upgrades)
                .add(BMRegistries.Keys.IMPERFECT_RITUALS, ImperfectRitualData::effects)
                .add(BMIdentifiers.RegistryKeys.SIGIL_EFFECTS, SigilData::effects)
        );

        ProviderHelper helper = new ProviderHelper(fileHelper);

        event.createProvider(helper.tagsFor(BMRegistries.Keys.ALTAR_TIER_KEY, AltarTiers::tags));
        event.createProvider(helper.tagsFor(BMRegistries.Keys.LIVING_UPGRADES, LivingUpgrades::tags));
        event.createProvider(helper.tagsFor(Registries.DAMAGE_TYPE, BloodyDamageSources::tags));
        event.createBlockAndItemTags(BMBlockTagProvider::new, BMItemTagProvider::new);

        event.createProvider(BMDataMapProvider::new);

        event.createProvider(BMLootTableProvider::new);
    }
}
