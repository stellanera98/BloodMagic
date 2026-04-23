package wayoftime.bloodmagic.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import wayoftime.bloodmagic.common.blockentity.base.SingleTargetWillInteractor;
import wayoftime.bloodmagic.common.datacomponent.EnumWillType;
import wayoftime.bloodmagic.common.datamap.WillStack;

public class BellJarTile extends SingleTargetWillInteractor {

    public BellJarTile(BlockPos pos, BlockState blockState) {
        super(BMTiles.BELLJAR_TYPE.get(), pos, blockState, 16384); // same as Grand Tartartic Gem
    }

    public WillStack getStoredWill() {
        if (storedAmount <= 0 || storedType == null) {
            return new WillStack(EnumWillType.RAW, 0d);
        }

        return new WillStack(storedType, storedAmount);
    }

    @Override
    public void toggleSelectedState(boolean selected) {
        // TODO implement line rendering
    }
}
