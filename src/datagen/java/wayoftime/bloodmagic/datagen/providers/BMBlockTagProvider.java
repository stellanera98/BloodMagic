package wayoftime.bloodmagic.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.block.BMBlocks;
import wayoftime.bloodmagic.datagen.BlockGroups;
import wayoftime.bloodmagic.tag.BMTags;

import java.util.concurrent.CompletableFuture;

public class BMBlockTagProvider extends BlockTagsProvider {
    public BMBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, BloodMagic.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(BMTags.Blocks.BLOODRUNE)
                .addAll(BlockGroups.RUNE_T1)
                .addAll(BlockGroups.RUNE_T2);

        this.tag(BMTags.Blocks.T3_CAP)
                .add(Blocks.GLOWSTONE, Blocks.SHROOMLIGHT, Blocks.SEA_LANTERN)
                .add(Blocks.OCHRE_FROGLIGHT, Blocks.PEARLESCENT_FROGLIGHT, Blocks.VERDANT_FROGLIGHT);

        this.tag(BMTags.Blocks.T4_CAP)
                .addAll(BlockGroups.BLOODSTONE);

        this.tag(BMTags.Blocks.T5_CAP)
                .add(Blocks.DIAMOND_BLOCK);

        this.tag(BMTags.Blocks.T6_CAP);

        this.tag(BMTags.Blocks.PILLAR);


        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(BMBlocks.BLOOD_TANK.block().get(), BMBlocks.BLOOD_ALTAR.block().get());

        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(BMBlocks.BLOOD_TANK.block().get(), BMBlocks.BLOOD_ALTAR.block().get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addAll(BlockGroups.BLOODSTONE)
                .addAll(BlockGroups.CRYSTAL_CLUSTER)
                .addAll(BlockGroups.RUNE_T1)
                .addAll(BlockGroups.RUNE_T2);

        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .addAll(BlockGroups.BLOODSTONE)
                .addAll(BlockGroups.CRYSTAL_CLUSTER)
                .addAll(BlockGroups.RUNE_T1);

        this.tag(Tags.Blocks.NEEDS_NETHERITE_TOOL)
                .addAll(BlockGroups.RUNE_T2);
    }
}
