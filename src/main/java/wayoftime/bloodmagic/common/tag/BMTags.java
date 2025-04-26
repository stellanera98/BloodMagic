package wayoftime.bloodmagic.common.tag;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
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

        private static TagKey<Item> fromBlock(TagKey<Block> input) {
            return tag(input.location());
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
