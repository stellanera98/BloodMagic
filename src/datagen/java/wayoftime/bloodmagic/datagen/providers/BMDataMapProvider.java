package wayoftime.bloodmagic.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import wayoftime.bloodmagic.common.block.BMBlocks;
import wayoftime.bloodmagic.common.datamap.BMDataMaps;
import wayoftime.bloodmagic.common.datamap.BloodOrb;
import wayoftime.bloodmagic.common.datamap.BloodRune;
import wayoftime.bloodmagic.common.item.BMItems;
import wayoftime.bloodmagic.util.RuneType;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BMDataMapProvider extends DataMapProvider {
    public BMDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        this.builder(BMDataMaps.BLOOD_RUNES)
                .add(BMBlocks.RUNE_ACCELERATION.block(), List.of(new BloodRune(RuneType.ACCELERATION, 1)), false)
                .add(BMBlocks.RUNE_SPEED.block(), List.of(new BloodRune(RuneType.SPEED, 1)), false)
                .add(BMBlocks.RUNE_CAPACITY.block(), List.of(new BloodRune(RuneType.CAPACITY, 1)), false)
                .add(BMBlocks.RUNE_CAPACITY_AUGMENTED.block(), List.of(new BloodRune(RuneType.AUGMENTED_CAPACITY, 1)), false)
                .add(BMBlocks.RUNE_SACRIFICE.block(), List.of(new BloodRune(RuneType.SACRIFICE, 1)), false)
                .add(BMBlocks.RUNE_SELF_SACRIFICE.block(), List.of(new BloodRune(RuneType.SELF_SACRIFICE, 1)), false)
                .add(BMBlocks.RUNE_ORB.block(), List.of(new BloodRune(RuneType.ORB, 1)), false)
                .add(BMBlocks.RUNE_CHARGING.block(), List.of(new BloodRune(RuneType.CHARGING, 1)), false)
                .add(BMBlocks.RUNE_DISLOCATION.block(), List.of(new BloodRune(RuneType.DISPLACEMENT, 1)), false)
                .add(BMBlocks.RUNE_EFFICIENCY.block(), List.of(new BloodRune(RuneType.EFFICIENCY, 1)), false)

                .add(BMBlocks.RUNE_2_ACCELERATION.block(), List.of(new BloodRune(RuneType.ACCELERATION, 2)), false)
                .add(BMBlocks.RUNE_2_SPEED.block(), List.of(new BloodRune(RuneType.SPEED, 2)), false)
                .add(BMBlocks.RUNE_2_CAPACITY.block(), List.of(new BloodRune(RuneType.CAPACITY, 2)), false)
                .add(BMBlocks.RUNE_2_CAPACITY_AUGMENTED.block(), List.of(new BloodRune(RuneType.AUGMENTED_CAPACITY, 2)), false)
                .add(BMBlocks.RUNE_2_SACRIFICE.block(), List.of(new BloodRune(RuneType.SACRIFICE, 2)), false)
                .add(BMBlocks.RUNE_2_SELF_SACRIFICE.block(), List.of(new BloodRune(RuneType.SELF_SACRIFICE, 2)), false)
                .add(BMBlocks.RUNE_2_ORB.block(), List.of(new BloodRune(RuneType.ORB, 2)), false)
                .add(BMBlocks.RUNE_2_CHARGING.block(), List.of(new BloodRune(RuneType.CHARGING, 2)), false)
                .add(BMBlocks.RUNE_2_DISLOCATION.block(), List.of(new BloodRune(RuneType.DISPLACEMENT, 2)), false)
                .add(BMBlocks.RUNE_2_EFFICIENCY.block(), List.of(new BloodRune(RuneType.EFFICIENCY, 2)), false);

        this.builder(BMDataMaps.BLOOD_ORBS)
                .add(BMItems.ORB_WEAK, new BloodOrb(0, 5_000, 2), false)
                .add(BMItems.ORB_APPRENTICE, new BloodOrb(1, 25_000, 5), false)
                .add(BMItems.ORB_MAGICIAN, new BloodOrb(2, 150_000, 15), false)
                .add(BMItems.ORB_MASTER, new BloodOrb(3, 1_000_000, 25), false)
                .add(BMItems.ORB_ARCHMAGE, new BloodOrb(4, 5_000_000, 50), false)
                .add(BMItems.ORB_TRANSCENDENT, new BloodOrb(5, 10_000_000, 50), false);

        this.builder(BMDataMaps.SOUL_GEM_AMOUNTS)
                .add(BMItems.SOUL_GEM_PETTY, 64D, false)
                .add(BMItems.SOUL_GEM_LESSER, 256D, false)
                .add(BMItems.SOUL_GEM_COMMON, 1024D, false)
                .add(BMItems.SOUL_GEM_GREATER, 4096D, false)
                .add(BMItems.SOUL_GEM_GRAND, 16384D, false);
    }
}
