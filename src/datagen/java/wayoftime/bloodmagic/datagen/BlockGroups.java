package wayoftime.bloodmagic.datagen;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import wayoftime.bloodmagic.common.block.BMBlocks;

import java.util.List;

public class BlockGroups {
    public static List<ResourceKey<Block>> RUNE_T1 = List.of(
            BMBlocks.RUNE_BLANK.block().getKey(), BMBlocks.RUNE_EFFICIENCY.block().getKey(),
            BMBlocks.RUNE_SACRIFICE.block().getKey(), BMBlocks.RUNE_SELF_SACRIFICE.block().getKey(),
            BMBlocks.RUNE_SPEED.block().getKey(), BMBlocks.RUNE_ACCELERATION.block().getKey(), BMBlocks.RUNE_DISLOCATION.block().getKey(),
            BMBlocks.RUNE_CAPACITY.block().getKey(), BMBlocks.RUNE_CAPACITY_AUGMENTED.block().getKey(),
            BMBlocks.RUNE_ORB.block().getKey(), BMBlocks.RUNE_CHARGING.block().getKey()
    );

    public static List<ResourceKey<Block>> RUNE_T2 = List.of(
            BMBlocks.RUNE_2_EFFICIENCY.block().getKey(),
            BMBlocks.RUNE_2_SACRIFICE.block().getKey(), BMBlocks.RUNE_2_SELF_SACRIFICE.block().getKey(),
            BMBlocks.RUNE_2_SPEED.block().getKey(), BMBlocks.RUNE_2_ACCELERATION.block().getKey(), BMBlocks.RUNE_2_DISLOCATION.block().getKey(),
            BMBlocks.RUNE_2_CAPACITY.block().getKey(), BMBlocks.RUNE_2_CAPACITY_AUGMENTED.block().getKey(),
            BMBlocks.RUNE_2_ORB.block().getKey(), BMBlocks.RUNE_2_CHARGING.block().getKey()
    );

    public static List<ResourceKey<Block>> BLOODSTONE = List.of(
            BMBlocks.BLOODSTONE.block().getKey(), BMBlocks.BLOODSTONE_BRICK.block().getKey()
    );

    public static List<ResourceKey<Block>> HELLFORGED_BLOCK = List.of( // theres textures for the other types for it
            BMBlocks.HELLFORGED_BLOCK.block().getKey()
    );

    public static List<ResourceKey<Block>> CRYSTAL_CLUSTER = List.of(
            BMBlocks.CRYSTAL_CLUSTER.block().getKey(), BMBlocks.CRYSTAL_CLUSTER_BRICK.block().getKey()
    );

    public static List<ResourceKey<Block>> BUDS_RAW = List.of(
            BMBlocks.WILL_BUD_SMALL_RAW.block().getKey(), BMBlocks.WILL_BUD_MEDIUM_RAW.block().getKey(), BMBlocks.WILL_BUD_LARGE_RAW.block().getKey()
    );

    public static List<ResourceKey<Block>> BUDS_CORROSIVE = List.of(
            BMBlocks.WILL_BUD_SMALL_CORROSIVE.block().getKey(), BMBlocks.WILL_BUD_MEDIUM_CORROSIVE.block().getKey(), BMBlocks.WILL_BUD_LARGE_CORROSIVE.block().getKey()
    );

    public static List<ResourceKey<Block>> BUDS_DESTRUCTIVE = List.of(
            BMBlocks.WILL_BUD_SMALL_DESTRUCTIVE.block().getKey(), BMBlocks.WILL_BUD_MEDIUM_DESTRUCTIVE.block().getKey(), BMBlocks.WILL_BUD_LARGE_DESTRUCTIVE.block().getKey()
    );

    public static List<ResourceKey<Block>> BUDS_STEADFAST = List.of(
            BMBlocks.WILL_BUD_SMALL_STEADFAST.block().getKey(), BMBlocks.WILL_BUD_MEDIUM_STEADFAST.block().getKey(), BMBlocks.WILL_BUD_LARGE_STEADFAST.block().getKey()
    );

    public static List<ResourceKey<Block>> BUDS_VENGEFUL = List.of(
            BMBlocks.WILL_BUD_SMALL_VENGEFUL.block().getKey(), BMBlocks.WILL_BUD_MEDIUM_VENGEFUL.block().getKey(), BMBlocks.WILL_BUD_LARGE_VENGEFUL.block().getKey()
    );

    public static List<ResourceKey<Block>> CLUSTERS = List.of(
            BMBlocks.WILL_CLUSTER_RAW.block().getKey(), BMBlocks.WILL_CLUSTER_CORROSIVE.block().getKey(), BMBlocks.WILL_CLUSTER_DESTRUCTIVE.block().getKey(), BMBlocks.WILL_CLUSTER_STEADFAST.block().getKey(), BMBlocks.WILL_CLUSTER_VENGEFUL.block().getKey()
    );

    public static List<ResourceKey<Block>> BUDDING_BLOCKS = List.of(
            BMBlocks.BUDDING_WILL_RAW.block().getKey(), BMBlocks.BUDDING_WILL_CORROSIVE.block().getKey(), BMBlocks.BUDDING_WILL_DESTRUCTIVE.block().getKey(), BMBlocks.BUDDING_WILL_STEADFAST.block().getKey(), BMBlocks.BUDDING_WILL_VENGEFUL.block().getKey()
    );
}
