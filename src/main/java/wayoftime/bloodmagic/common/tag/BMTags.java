package wayoftime.bloodmagic.common.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.registry.AltarTier;
import wayoftime.bloodmagic.common.registry.BMRegistries;

public class BMTags {
    public static class Blocks {
        private static TagKey<Block> tag(ResourceLocation id) {
            return TagKey.create(Registries.BLOCK, id);
        }

        public static final TagKey<Block> BLOODRUNE = tag(bm("altar/runes"));
        public static final TagKey<Block> PILLAR = tag(bm("altar/pillars"));
        public static final TagKey<Block> T3_CAP = tag(bm("altar/t3_caps"));
        public static final TagKey<Block> T4_CAP = tag(bm("altar/t4_caps"));
        public static final TagKey<Block> T5_CAP = tag(bm("altar/t5_caps"));
        public static final TagKey<Block> T6_CAP = tag(bm("altar/t6_caps"));

        public static final TagKey<Block> STORAGE_BLOCKS_HELLFORGED = tag(c("storage_blocks/hellforged"));
    }

    public static class Items {
        public static final TagKey<Item> SOUL_GEM = tag(bm("soul_gems"));
        public static final TagKey<Item> CRYSTAL_CLUSTER = tag(bm("crystal_cluster"));

        public static final TagKey<Item> STORAGE_BLOCKS_HELLFORGED = fromBlock(Blocks.STORAGE_BLOCKS_HELLFORGED);

        public static final TagKey<Item> ARC_TOOL = tag(bm("arc_tool"));

        public static final TagKey<Item> ARC_TOOL_FURNACE = withParent(ARC_TOOL, bm("furnace"));
        public static final TagKey<Item> ARC_TOOL_SMELTING = withParent(ARC_TOOL_FURNACE, bm("smelting"));
        public static final TagKey<Item> ARC_TOOL_BLASTING = withParent(ARC_TOOL_FURNACE, bm("blasting"));
        public static final TagKey<Item> ARC_TOOL_SMOKING = withParent(ARC_TOOL_FURNACE, bm("smoking"));

        public static final TagKey<Item> ARC_TOOL_REVERTER = withParent(ARC_TOOL, bm("reverter"));
        public static final TagKey<Item> ARC_TOOL_HYDRATE = withParent(ARC_TOOL, bm("hydrate"));

        public static final TagKey<Item> ARC_TOOL_EXPLOSIVE = withParent(ARC_TOOL, bm("explosive"));
        public static final TagKey<Item> ARC_TOOL_RESONATOR = withParent(ARC_TOOL, bm("resonator"));
        public static final TagKey<Item> ARC_TOOL_CUTTING_FLUID = withParent(ARC_TOOL, bm("cutting_fluid"));

        private static TagKey<Item> fromBlock(TagKey<Block> input) {
            return tag(input.location());
        }

        private static TagKey<Item> withParent(TagKey<Item> parent, ResourceLocation location) {
            return TagKey.create(Registries.ITEM, location.withPrefix(parent.location().getPath()+"/"));
        }

        private static TagKey<Item> tag(ResourceLocation id) {
            return TagKey.create(Registries.ITEM, id);
        }
    }

    public static class Tiers {
        public static final TagKey<AltarTier> VALID_TIERS = TagKey.create(BMRegistries.ALTAR_TIER_KEY, bm("valid_tiers"));
    }

    private static ResourceLocation bm(String path) {
        return ResourceLocation.fromNamespaceAndPath(BloodMagic.MODID, path);
    }

    private static ResourceLocation c(String path) {
        return ResourceLocation.fromNamespaceAndPath("c", path);
    }
}
