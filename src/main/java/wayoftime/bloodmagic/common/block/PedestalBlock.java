package wayoftime.bloodmagic.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import wayoftime.bloodmagic.common.block.base.WorldInteractionBlock;
import wayoftime.bloodmagic.common.blockentity.BMTiles;
import wayoftime.bloodmagic.common.blockentity.PedestalTile;

public class PedestalBlock extends WorldInteractionBlock<PedestalTile> {

    public static final VoxelShape SHAPE = Shapes.or(
            // base
            box(0, 0, 0, 16, 1, 16),

            // pillar
            box(3, 1, 3, 13, 11, 13),

            // vessel ground
            box(5, 11, 5, 11, 12, 11),

            // layer 1
            box(4, 12, 4, 12, 13, 5),
            box(4, 12, 11, 12, 13, 12),
            box(4, 12, 5, 5, 13, 11),
            box(11, 12, 5, 12, 13, 11),

            // layer 2
            box(3, 13, 3, 13, 14, 4),
            box(3, 13, 12, 13, 14, 13),
            box(3, 13, 4, 4, 14, 12),
            box(12, 13, 4, 13, 14, 12),

            // layer 3
            box(2, 14, 2, 14, 15, 3),
            box(2, 14, 13, 14, 15, 14),
            box(2, 14, 3, 3, 15, 13),
            box(13, 14, 3, 14, 15, 13),

            // layer 4
            box(1, 15, 1, 15, 16, 2),
            box(1, 15, 14, 15, 16, 15),
            box(1, 15, 2, 2, 16, 14),
            box(14, 15, 2, 15, 16, 14)
    );

    public PedestalBlock() {
        super(
                Properties.of()
                        .forceSolidOn()
                        .noOcclusion()
                        .strength(5, 2),
                BMTiles.PEDESTAL_TYPE
        );
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
