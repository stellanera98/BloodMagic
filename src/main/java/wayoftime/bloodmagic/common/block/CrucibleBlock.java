package wayoftime.bloodmagic.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import wayoftime.bloodmagic.common.block.base.WorldInteractionBlock;
import wayoftime.bloodmagic.common.blockentity.BMTiles;
import wayoftime.bloodmagic.common.blockentity.CrucibleTile;

public class CrucibleBlock extends WorldInteractionBlock<CrucibleTile> implements EntityBlock {

    public static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 12, 15);

    public CrucibleBlock() {
        super(Properties.of()
                .strength(2, 5)
                .requiresCorrectToolForDrops(),
                BMTiles.CRUCIBLE_TYPE.get()
        );
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
