package wayoftime.bloodmagic.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import wayoftime.bloodmagic.api.capability.IWillHandler;
import wayoftime.bloodmagic.common.datacomponent.EnumWillType;

public class InfuserTile extends BaseTile implements IWillHandler {

    public InfuserTile(BlockPos pos, BlockState blockState) {
        super(BMTiles.INFUSER_TYPE.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, InfuserTile infuser) {

    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
    }

    public IWillHandler getWillHandler(Void unused) {
        return this;
    }

    @Override
    public double fill(EnumWillType type, double max, boolean doFill) {
    }

    @Override
    public double drain(EnumWillType type, double max, boolean doDrain) {
    }
}
