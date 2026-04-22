package wayoftime.bloodmagic.common.block;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.api.capability.IWillHandler;
import wayoftime.bloodmagic.common.datacomponent.EnumWillType;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

import static wayoftime.bloodmagic.common.block.WillClusterBlock.FACING;

public class BuddingWillBlock extends Block {

    private final Holder<Block> small;
    private final int foreignCost;
    private final TagKey<Block> sameType;

    public static final Map<Holder<Block>, Holder<Block>> GROWTH_MAP = new ImmutableMap.Builder<Holder<Block>, Holder<Block>>()
            .put(BMBlocks.WILL_BUD_SMALL_RAW.block(), BMBlocks.WILL_BUD_MEDIUM_RAW.block())
            .put(BMBlocks.WILL_BUD_MEDIUM_RAW.block(), BMBlocks.WILL_BUD_LARGE_RAW.block())
            .put(BMBlocks.WILL_BUD_LARGE_RAW.block(), BMBlocks.BUDDING_WILL_RAW.block())

            .put(BMBlocks.WILL_BUD_SMALL_CORROSIVE.block(), BMBlocks.WILL_BUD_MEDIUM_CORROSIVE.block())
            .put(BMBlocks.WILL_BUD_MEDIUM_CORROSIVE.block(), BMBlocks.WILL_BUD_LARGE_CORROSIVE.block())
            .put(BMBlocks.WILL_BUD_LARGE_CORROSIVE.block(), BMBlocks.BUDDING_WILL_CORROSIVE.block())

            .put(BMBlocks.WILL_BUD_SMALL_DESTRUCTIVE.block(), BMBlocks.WILL_BUD_MEDIUM_DESTRUCTIVE.block())
            .put(BMBlocks.WILL_BUD_MEDIUM_DESTRUCTIVE.block(), BMBlocks.WILL_BUD_LARGE_DESTRUCTIVE.block())
            .put(BMBlocks.WILL_BUD_LARGE_DESTRUCTIVE.block(), BMBlocks.BUDDING_WILL_DESTRUCTIVE.block())

            .put(BMBlocks.WILL_BUD_SMALL_STEADFAST.block(), BMBlocks.WILL_BUD_MEDIUM_STEADFAST.block())
            .put(BMBlocks.WILL_BUD_MEDIUM_STEADFAST.block(), BMBlocks.WILL_BUD_LARGE_STEADFAST.block())
            .put(BMBlocks.WILL_BUD_LARGE_STEADFAST.block(), BMBlocks.BUDDING_WILL_STEADFAST.block())

            .put(BMBlocks.WILL_BUD_SMALL_VENGEFUL.block(), BMBlocks.WILL_BUD_MEDIUM_VENGEFUL.block())
            .put(BMBlocks.WILL_BUD_MEDIUM_VENGEFUL.block(), BMBlocks.WILL_BUD_LARGE_VENGEFUL.block())
            .put(BMBlocks.WILL_BUD_LARGE_VENGEFUL.block(), BMBlocks.BUDDING_WILL_VENGEFUL.block())
            .build();

    public static final IntegerProperty STORED_WILL = IntegerProperty.create("stored_will", 0, 4 * 6); // store enough will to grow a full cluster on each side

    public BuddingWillBlock(Holder<Block> small, int foreignCost, TagKey<Block> sameType, MapColor color) {
        super(Properties.of()
                .requiresCorrectToolForDrops()
                .mapColor(color)
                .sound(SoundType.AMETHYST)
                .strength(1.5f)
                .randomTicks()
                // returns 16 for 23 and 24. not sure if this is an issue
                .lightLevel(state -> (int) Math.ceil((float) state.getValue(STORED_WILL) / 3f * 2f))
        );
        this.small = small;
        this.foreignCost = foreignCost;
        this.sameType = sameType;
        registerDefaultState(getStateDefinition().any().setValue(STORED_WILL, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STORED_WILL);
    }

    public static IWillHandler getWillHandler(Level level, BlockPos pos, BlockState state, EnumWillType req) {
        return new IWillHandler() {
            @Override
            public double fill(EnumWillType type, double max, boolean doFill) {
                if (type != req) {
                    return 0;
                }
                int has = state.getValue(STORED_WILL);
                if (has == 24) {
                    return 0;
                }
                if (max >= 10) {
                    level.setBlockAndUpdate(pos, state.setValue(STORED_WILL, has + 1));
                    return 10;
                }

                return 0;
            }

            @Override
            public double drain(EnumWillType type, double max, boolean doDrain) {
                return 0;
            }
        };
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {

    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        growthTick(state, level, pos, random);
        level.scheduleTick(pos, state.getBlock(), 20 * (45 + random.nextInt(3)));
    }

    public void growthTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextInt(BloodMagic.SERVER_CONFIG.BUDDING_CHANCE.get()) != 0) {
            return;
        }
        Block block = null;
        BlockPos growPos = null;
        BlockState growState = null;
        Direction growSide = null;
        Collection<Direction> sides = Direction.allShuffled(random);
        for (Direction side : sides) { // pick random side that can grow, if available
            growPos = pos.relative(side);
            growState = level.getBlockState(growPos);
            growSide = side;
            if (canClusterGrowAtState(growState)) {
                block = small.value();
                break;
            } else if (growState.getValue(FACING) == side) {
                block = GROWTH_MAP.get(growState.getBlockHolder()).value();
                break;
            }
        }

        if (block != null) {
            BlockState newState = block.defaultBlockState()
                    .setValue(FACING, growSide)
                    .setValue(WillClusterBlock.WATERLOGGED, growState.getFluidState().getType() == Fluids.WATER);
            int cost = newState.is(sameType) ? 1 : foreignCost;
            int available = state.getValue(STORED_WILL);
            if (cost <= available) {
                level.setBlockAndUpdate(growPos, newState);
                level.sendBlockUpdated(pos, state, state.setValue(STORED_WILL, available - cost), UPDATE_ALL_IMMEDIATE);
            }
        }
    }

    public static boolean canClusterGrowAtState(BlockState state) {
        return state.isAir() || state.is(Blocks.WATER) && state.getFluidState().getAmount() == 8;
    }
}
