package wayoftime.bloodmagic.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import wayoftime.bloodmagic.common.blockentity.base.SingleTargetWillInteractor;
import wayoftime.bloodmagic.common.datacomponent.EnumWillType;
import wayoftime.bloodmagic.common.datamap.WillStack;

public class BellJarTile extends SingleTargetWillInteractor {

    public BellJarTile(BlockPos pos, BlockState blockState) {
        super(BMTiles.BELLJAR_TYPE.get(), pos, blockState, 16384); // same as Grand Tartartic Gem
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state) {
        pushWill(100);
    }

    public WillStack getStoredWill() {
        if (storedAmount <= 0 || storedType == null) {
            return new WillStack(EnumWillType.RAW, 0d);
        }

        return new WillStack(storedType, storedAmount);
    }
}
