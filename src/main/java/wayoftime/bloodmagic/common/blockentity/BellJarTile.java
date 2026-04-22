package wayoftime.bloodmagic.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import wayoftime.bloodmagic.common.blockentity.base.SingleTargetWillInteractor;

public class BellJarTile extends SingleTargetWillInteractor {

    public BellJarTile(BlockPos pos, BlockState blockState) {
        super(BMTiles.BELLJAR_TYPE.get(), pos, blockState, 16384); // same as Grand Tartartic Gem
    }

    @Override
    public void toggleSelectedState(boolean selected) {
        // TODO implement line rendering
    }
}
