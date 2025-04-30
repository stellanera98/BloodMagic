package wayoftime.bloodmagic.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.block.BMBlocks;
import wayoftime.bloodmagic.common.item.BMItems;
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

        tag(BMTags.Items.ARC_TOOL_REVERTER)
                .add(BMItems.SANGUINE_REVERTER.get());

        tag(BMTags.Items.ARC_TOOL_HYDRATE)
                .add(BMItems.HYDRATION_CELL.get(), BMItems.PRIMITIVE_HYDRATION_CELL.get());

        tag(BMTags.Items.ARC_TOOL_EXPLOSIVE)
                .add(BMItems.EXPLOSIVE_POWDER.get(), BMItems.PRIMITIVE_EXPLOSIVE_CELL.get(), BMItems.HELLFORGED_EXPLOSIVE_CELL.get());

        tag(BMTags.Items.ARC_TOOL_RESONATOR)
                .add(BMItems.RESONATOR.get(), BMItems.PRIMITIVE_RESONATOR.get(), BMItems.HELLFORGED_RESONATOR.get());

        tag(BMTags.Items.ARC_TOOL_CUTTING_FLUID)
                .add(BMItems.CUTTING_FLUID.get(), BMItems.PRIMITIVE_CUTTING_FLUID.get(), BMItems.HELLFORGED_CUTTING_FLUID.get());

        tag(BMTags.Items.ARC_TOOL_SMELTING)
                .add(BMItems.PRIMITIVE_FURNACE_CELL.get());

        tag(BMTags.Items.ARC_TOOL_BLASTING);

        tag(BMTags.Items.ARC_TOOL_SMOKING);

        tag(BMTags.Items.ARC_TOOL)
                .addTags(BMTags.Items.ARC_TOOL_SMELTING, BMTags.Items.ARC_TOOL_SMOKING, BMTags.Items.ARC_TOOL_BLASTING)
                .addTags(BMTags.Items.ARC_TOOL_HYDRATE, BMTags.Items.ARC_TOOL_REVERTER)
                .addTags(BMTags.Items.ARC_TOOL_EXPLOSIVE, BMTags.Items.ARC_TOOL_RESONATOR, BMTags.Items.ARC_TOOL_CUTTING_FLUID);

        tag(BMTags.Items.ARC_TOOL_FURNACE)
                .addTags(BMTags.Items.ARC_TOOL_SMELTING, BMTags.Items.ARC_TOOL_SMOKING, BMTags.Items.ARC_TOOL_BLASTING);
    }
}
