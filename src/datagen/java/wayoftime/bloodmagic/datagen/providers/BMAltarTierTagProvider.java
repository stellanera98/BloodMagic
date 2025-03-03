package wayoftime.bloodmagic.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.registry.AltarTier;
import wayoftime.bloodmagic.common.registry.BMRegistries;
import wayoftime.bloodmagic.common.tag.BMTags;
import wayoftime.bloodmagic.datagen.builders.AltarTierBuilder.Keys;

import java.util.concurrent.CompletableFuture;

public class BMAltarTierTagProvider extends TagsProvider<AltarTier> {
    public BMAltarTierTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, BMRegistries.ALTAR_TIER_KEY, lookupProvider, BloodMagic.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BMTags.Tiers.VALID_TIERS)
                .add(Keys.WEAK)
                .add(Keys.APPRENTICE)
                .add(Keys.MAGE)
                .add(Keys.MASTER)
                .add(Keys.ARCHMAGE)
                .add(Keys.TRANSCENDENT);
    }
}
