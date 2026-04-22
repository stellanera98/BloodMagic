package wayoftime.bloodmagic.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import wayoftime.bloodmagic.common.block.base.BaseTileBlock;
import wayoftime.bloodmagic.common.blockentity.BMTiles;
import wayoftime.bloodmagic.common.blockentity.InfuserTile;

// WillInfuser is harder to read and its not like we'll have some other infusion
public class InfuserBlock extends BaseTileBlock<InfuserTile> implements EntityBlock {

    public static final VoxelShape SHAPE = Block.box(2, 2, 2, 14, 16, 14);

    public InfuserBlock() {
        super(Properties.of()
                .strength(2, 5)
                .requiresCorrectToolForDrops(),
                BMTiles.INFUSER_TYPE
        );
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
