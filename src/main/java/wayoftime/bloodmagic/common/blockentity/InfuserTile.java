package wayoftime.bloodmagic.common.blockentity;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import wayoftime.bloodmagic.api.capability.IWillHandler;
import wayoftime.bloodmagic.common.blockentity.base.BaseTile;
import wayoftime.bloodmagic.common.datacomponent.EnumWillType;
import wayoftime.bloodmagic.common.datamap.BMDataMaps;
import wayoftime.bloodmagic.common.datamap.WillStack;

public class InfuserTile extends BaseTile implements IWillHandler {

    protected EnumWillType storedType;
    protected double storedAmount;
    public static final double MAX_STORED = 500d;

    public InfuserTile(BlockPos pos, BlockState blockState) {
        super(BMTiles.INFUSER_TYPE.get(), pos, blockState);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state) {
        Pair<WillStack, Block> recipe = level.getBlockState(pos.above()).getBlockHolder().getData(BMDataMaps.WILL_INFUSION);
        if (recipe == null) {
            return;
        }
        EnumWillType recipeType = recipe.getFirst().type();
        double recipeCost = recipe.getFirst().amount();
        if (recipeType == storedType && recipeCost <= storedAmount) {
            storedAmount -= recipeCost;
            if (storedAmount <= 0) {
                storedAmount = 0;
                storedType = null;
            }
            // TODO store block state to place and insert pretty animation
            level.setBlockAndUpdate(pos.above(), recipe.getSecond().defaultBlockState());
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        storedAmount = tag.contains("stored_amount") ? tag.getDouble("stored_amount") : 0;
        storedType = tag.contains("stored_type") ? EnumWillType.valueOf(tag.getString("stored_type").toUpperCase()) : null;
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
    }

    public IWillHandler getWillHandler(Void unused) {
        return this;
    }

    @Override
    public WillStack getWillStack() {
        return new WillStack(storedType != null ? storedType : EnumWillType.RAW, storedAmount);
    }

    @Override
    public double fill(EnumWillType type, double max, boolean doFill) {
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
                storedAmount = 0;
                storedType = null;
            }
        }
        return maxDrain;
    }
}
