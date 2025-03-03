package wayoftime.bloodmagic.common.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.registry.AltarTier;
import wayoftime.bloodmagic.common.registry.BMRegistries;

public class BMTags {
    private static ResourceLocation bm(String path) {
        return ResourceLocation.fromNamespaceAndPath(BloodMagic.MODID, path);
    }

    public static class Blocks {
        public static final TagKey<Block> BLOODRUNE = TagKey.create(Registries.BLOCK, bm("altar/runes"));
        public static final TagKey<Block> PILLAR = TagKey.create(Registries.BLOCK, bm("altar/pillars"));
        public static final TagKey<Block> T3_CAP = TagKey.create(Registries.BLOCK, bm("altar/t3_caps"));
        public static final TagKey<Block> T4_CAP = TagKey.create(Registries.BLOCK, bm("altar/t4_caps"));
        public static final TagKey<Block> T5_CAP = TagKey.create(Registries.BLOCK, bm("altar/t5_caps"));
        public static final TagKey<Block> T6_CAP = TagKey.create(Registries.BLOCK, bm("altar/t6_caps"));
    }

    public static class Tiers {
        public static final TagKey<AltarTier> VALID_TIERS = TagKey.create(BMRegistries.ALTAR_TIER_KEY, bm("valid_tiers"));
    }

    public static class Items {}
}
