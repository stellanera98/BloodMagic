package wayoftime.bloodmagic.common.blockentity.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import wayoftime.bloodmagic.api.capability.IWandConfigurable;
import wayoftime.bloodmagic.api.capability.IWillHandler;
import wayoftime.bloodmagic.common.datacomponent.EnumWillType;
import wayoftime.bloodmagic.util.NBTHelper;

public abstract class SingleTargetWillInteractor extends BaseTile implements IWandConfigurable, IWillHandler {

    protected EnumWillType storedType;
    protected double storedAmount;
    protected final double MAX_STORED;

    protected EnumWillType lockedType = null;
    protected BlockPos target;

    public SingleTargetWillInteractor(BlockEntityType<?> type, BlockPos pos, BlockState blockState, double maxStored) {
        super(type, pos, blockState);
        this.MAX_STORED = maxStored;
    }

    public IWillHandler getWillHandler(Void unused) {
        return this;
    }

    public IWandConfigurable getWandConfigurable(Void unused) {
        return this;
    }

    @Override
    public void addConnection(BlockPos target, @Nullable EnumWillType type) {
        this.target = target;
        this.lockedType = type;
    }

    @Override
    public void removeConnection(BlockPos target) {
        if (this.target.equals(target)) {
            this.target = null;
        }
    }

    @Override
    public void toggleWillType(EnumWillType type) {
        lockedType = type;
    }

    @Override
    public double fill(EnumWillType type, double max, boolean doFill) {
        if (lockedType != null && lockedType != type) { // if locked is set, locked and type need to match
            return 0;
        }
        if (storedType != null && storedType != type) { // if stored is set, stored and type need to match
            return 0;
        }
        double maxFill = Math.clamp(max, 0, MAX_STORED - storedAmount);
        if (doFill) {
            storedAmount += maxFill;
            if (storedType == null) { // if stored was null, set it to incoming
                storedType = type;
            }
        }
        return maxFill;
    }

    @Override
    public double drain(EnumWillType type, double max, boolean doDrain) {
        if (type != storedType) { // dont have that type, you get 0
            return 0;
        }

        double maxDrain = Math.clamp(max, 0, storedAmount);
        if (doDrain) {
            storedAmount -= maxDrain;
            if (storedAmount <= 0) { // reset to no type when empty
                storedType = null;
            }
        }
        return maxDrain;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        storedAmount = tag.contains("stored_amount") ? tag.getDouble("stored_amount") : 0;
        lockedType = tag.contains("locked_type") ? EnumWillType.valueOf(tag.getString("locked_type").toUpperCase()) : null;
        storedType = tag.contains("stored_type") ? EnumWillType.valueOf(tag.getString("stored_type").toUpperCase()) : null;
        if (storedType == null && lockedType != null) {
            storedType = lockedType;
        }
        target = tag.contains("pos") ? NBTHelper.getBlockPos(tag.getCompound("pos")) : null;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (storedAmount > 0) {
            tag.putDouble("stored_amount", storedAmount);
        }
        if (storedType != null) {
            tag.putString("stored_type", storedType.getSerializedName());
        }
        if (lockedType != null) {
            tag.putString("locked_type", lockedType.getSerializedName());
        }
        if (target != null) {
            tag.put("pos", NBTHelper.getPosTag(target));
        }
    }
}
