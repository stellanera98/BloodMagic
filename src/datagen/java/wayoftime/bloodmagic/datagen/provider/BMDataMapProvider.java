package wayoftime.bloodmagic.datagen.provider;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import wayoftime.bloodmagic.datagen.content.ImperfectRitualData;
import wayoftime.bloodmagic.datagen.content.datamap.*;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class BMDataMapProvider extends DataMapProvider {
    public BMDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        TartaricGemMax.bootstrap(this::builder);
        BloodOrbStats.bootstrap(this::builder);
        BloodRuneData.bootstrap(this::builder);
        LivingData.bootstrap(this::builder);
        ImperfectRitualData.dataMap(this::builder);
        CrucibleData.bootstrap(this::builder);
        InfusionData.bootstrap(this::builder);
        SentientData.bootstrap(this::builder);
    }
}
