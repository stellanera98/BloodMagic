package wayoftime.bloodmagic.common.block.base;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wayoftime.bloodmagic.common.blockentity.base.BaseTile;
import wayoftime.bloodmagic.util.BlockEntityHelper;

import java.util.function.Supplier;

public abstract class BaseTileBlock<W extends BaseTile> extends Block implements EntityBlock {

    protected final Supplier<BlockEntityType<W>> type;
    public BaseTileBlock(Properties properties, Supplier<BlockEntityType<W>> type) {
        super(properties);
        this.type = type;
    }

    @Override
    protected void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean movedByPiston) {
        W tile = type.get().getBlockEntity(level, pos);
        if (tile != null) {
            BlockEntityHelper.dropContents(level, pos, tile.getItemHandler(null));
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return type.get().create(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return BlockEntityHelper.getTicker(blockEntityType, type.get(), ((level1, pos, state1, blockEntity) -> blockEntity.tick(level1, pos, state1)));
    }
}
