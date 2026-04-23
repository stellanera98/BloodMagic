package wayoftime.bloodmagic.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import wayoftime.bloodmagic.common.block.base.BaseTileBlock;
import wayoftime.bloodmagic.common.blockentity.ARCTile;
import wayoftime.bloodmagic.common.blockentity.AlchemyTableTile;
import wayoftime.bloodmagic.common.blockentity.BMTiles;
import wayoftime.bloodmagic.util.BlockEntityHelper;
import wayoftime.bloodmagic.util.TablePart;

public class AlchemyTableBlock extends BaseTileBlock<AlchemyTableTile> implements EntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<TablePart> PART = EnumProperty.create("part", TablePart.class);

    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 12, 16);

    public AlchemyTableBlock() {
        super(Properties.of()
                .strength(2, 5)
                .noOcclusion()
                .isRedstoneConductor((state, level, pos) -> false)
                .isViewBlocking((state, level, pos) -> false)
                .requiresCorrectToolForDrops()
                .forceSolidOn(),
                BMTiles.ALCHEMY_TABLE_TYPE
        );
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            if (state.getValue(PART) == TablePart.RIGHT) {
                pos = pos.relative(state.getValue(FACING).getCounterClockWise());
            }
            serverPlayer.openMenu(state.getMenuProvider(level, pos), buf -> {
                buf.writeInt(AlchemyTableTile.SLOT_COUNT);
                buf.writeInt(AlchemyTableTile.DATA_COUNT);
            });
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected @Nullable MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        BlockEntity BE = level.getBlockEntity(pos);
        if (!(BE instanceof AlchemyTableTile tile)) {
            return null;
        }
        return tile;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getHorizontalDirection();
        BlockPos clickedPos = context.getClickedPos();
        BlockPos rightPos = clickedPos.relative(direction.getClockWise());
        Level level = context.getLevel();
        if (level.getBlockState(rightPos).canBeReplaced(context) && level.getWorldBorder().isWithinBounds(rightPos)) {
            return this.defaultBlockState().setValue(FACING, direction);
        }
        BlockPos leftPos = clickedPos.relative(direction.getCounterClockWise());
        return level.getBlockState(leftPos).canBeReplaced(context) && level.getWorldBorder().isWithinBounds(leftPos) ?
                this.defaultBlockState().setValue(FACING, direction).setValue(PART, TablePart.RIGHT)
                : null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (!level.isClientSide) {
            TablePart prev = state.getValue(PART);
            BlockPos blockpos = pos.relative(getNeighbourDirection(prev, state.getValue(FACING)));
            level.setBlockAndUpdate(blockpos, state.setValue(PART, prev.getOther()));
            level.blockUpdated(pos, Blocks.AIR);
            state.updateNeighbourShapes(level, pos, UPDATE_ALL);
        }
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && player.isCreative()) {
            TablePart part = state.getValue(PART);
            if (part == TablePart.LEFT) {
                BlockPos blockpos = pos.relative(getNeighbourDirection(part, state.getValue(FACING)));
                BlockState blockstate = level.getBlockState(blockpos);
                if (blockstate.is(this) && blockstate.getValue(PART) == TablePart.RIGHT) {
                    level.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
                    level.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
                }
            }
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (facing == getNeighbourDirection(state.getValue(PART), state.getValue(FACING))) {
            if (!(facingState.is(this) && facingState.getValue(PART) != state.getValue(PART))) {
                return Blocks.AIR.defaultBlockState();
            }
        }

        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    private static Direction getNeighbourDirection(TablePart part, Direction direction) {
        return part == TablePart.RIGHT ? direction.getCounterClockWise() : direction.getClockWise();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PART);
    }
}
