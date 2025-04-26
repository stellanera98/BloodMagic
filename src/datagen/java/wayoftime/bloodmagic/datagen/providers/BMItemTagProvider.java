package wayoftime.bloodmagic.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.block.BMBlocks;
import wayoftime.bloodmagic.common.tag.BMTags;
import wayoftime.bloodmagic.datagen.ItemGroups;

import java.util.concurrent.CompletableFuture;

public class BMItemTagProvider extends ItemTagsProvider {
    public BMItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, BloodMagic.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BMTags.Items.SOUL_GEM)
                .addAll(ItemGroups.SOUL_GEMS);

        tag(BMTags.Items.CRYSTAL_CLUSTER)
                .add(BMBlocks.CRYSTAL_CLUSTER.item().getKey())
                .add(BMBlocks.CRYSTAL_BRICK.item().getKey());

        copy(BMTags.Blocks.STORAGE_BLOCKS_HELLFORGED, BMTags.Items.STORAGE_BLOCKS_HELLFORGED);
    }
}
