package wayoftime.bloodmagic.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wayoftime.bloodmagic.common.block.base.WorldInteractionBlock;
import wayoftime.bloodmagic.common.blockentity.BMTiles;
import wayoftime.bloodmagic.common.blockentity.BloodAltarTile;

public class BloodAltarBlock extends WorldInteractionBlock<BloodAltarTile> implements EntityBlock {
    public BloodAltarBlock() {
        super(Properties.of()
                .forceSolidOn()
                .requiresCorrectToolForDrops()
                .strength(2.0F, 5.0F)
                .sound(SoundType.STONE),
                BMTiles.BLOOD_ALTAR_TYPE.get()
        );
    }

    public static final VoxelShape BOX = box(0, 0, 0, 16, 12, 16);

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return BOX;
    }

    @Override
    protected boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        BlockEntity tile = level.getBlockEntity(pos);
        if (!(tile instanceof BloodAltarTile altar)) {
            return 0;
        }
        return altar.analogSignal();
    }

    @Override
    protected int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        BlockEntity tile = level.getBlockEntity(pos);
        if (!(tile instanceof BloodAltarTile altar)) {
            return 0;
        }
        return altar.getIsSignaling() ? 15 : 0;
    }
}
