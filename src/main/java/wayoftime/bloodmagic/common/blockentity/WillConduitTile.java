package wayoftime.bloodmagic.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import wayoftime.bloodmagic.api.capability.IWillHandler;
import wayoftime.bloodmagic.common.blockentity.base.BaseTile;
import wayoftime.bloodmagic.common.caps.BMCaps;
import wayoftime.bloodmagic.common.datacomponent.EnumWillType;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class WillConduitTile extends BaseTile implements IWillHandler {

    public Map<BlockPos, Optional<EnumWillType>> outputPositions = new HashMap<>();
    public EnumMap<EnumWillType, Double> willStored = new EnumMap<>(EnumWillType.class);

    public WillConduitTile(BlockPos pos, BlockState blockState) {
        super(BMTiles.WILL_CONDUIT_TYPE.get(), pos, blockState);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state) {
        // TODO implement
    }

    public boolean setOutputPosition(EnumWillType type, BlockPos pos) {
        if (outputPositions.containsKey(pos) // override
                || outputPositions.size() <= 21) { // can add
            outputPositions.put(pos, Optional.of(type));
            setChanged();
            return true;
        }
        return false;
    }

    public void removeOutput(BlockPos pos) {
        outputPositions.remove(pos);
        setChanged();
    }

    @Override
    public void setChanged() {
        super.setChanged();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }

    public void pushWill(double rate) {
        for (Map.Entry<BlockPos, Optional<EnumWillType>> entry : outputPositions.entrySet()) {
            IWillHandler targetHandler = level.getCapability(BMCaps.BLOCK_WILL_HANDLER, entry.getKey());
            if (targetHandler == null) {
                continue;
            }
            for (EnumWillType type : EnumWillType.types()) {
                if (entry.getValue().isPresent() && (type != entry.getValue().get())) {
                    continue;
                }
                double available = drain(type, rate, false);
                double filled = targetHandler.fill(type, available, true);
                drain(type, filled, true);
            }
        }
    }

    public double getCapacity(EnumWillType type) {
        return 100;
    }

    @Override
    public double fill(EnumWillType type, double max, boolean doFill) {
        double stored = willStored.getOrDefault(type, 0d);
        double cap = getCapacity(type);

        double maxFill = Math.clamp(max, 0, cap - stored);

        if (doFill) {
            willStored.put(type, stored + maxFill);
            setChanged();
        }

        return maxFill;
    }

    @Override
    public double drain(EnumWillType type, double max, boolean doDrain) {
        double stored = willStored.getOrDefault(type, 0d);
        double maxDrain = Math.clamp(max, 0, stored);

        if (doDrain) {
            double remaining = stored - maxDrain;
            if (remaining <= 0) {
                willStored.remove(type);
            } else {
                willStored.put(type, remaining);
            }
            setChanged();
        }

        return maxDrain;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        loadConduitData(registries, tag.getCompound("conduit_data"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("conduit_data", saveConduitData(registries));
    }

    public CompoundTag saveConduitData(HolderLookup.Provider registries) {
        CompoundTag conduitTag = new CompoundTag();
        for (Map.Entry<EnumWillType, Double> entry : willStored.entrySet()) {
            conduitTag.putDouble(entry.getKey().getSerializedName(), entry.getValue());
        }

        ListTag targets = new ListTag();
        for (Map.Entry<BlockPos, Optional<EnumWillType>> entry : outputPositions.entrySet()) {
            CompoundTag tag = new CompoundTag();
            putPos(tag, entry.getKey());
            tag.putString("type", entry.getValue().isPresent() ? entry.getValue().get().getSerializedName() : "all");
            targets.add(tag);
        }
        conduitTag.put("targets", targets);

        return conduitTag;
    }

    private void putPos(CompoundTag tag, BlockPos pos) {
        CompoundTag posTag = new CompoundTag();
        posTag.putInt("x", pos.getX());
        posTag.putInt("y", pos.getY());
        posTag.putInt("z", pos.getZ());
        tag.put("pos", posTag);
    }

    public void loadConduitData(HolderLookup.Provider registries, CompoundTag conduitTag) {
        for (EnumWillType type : EnumWillType.values()) {
            if (!conduitTag.contains(type.getSerializedName())) {
                continue;
            }
            willStored.put(type, conduitTag.getDouble(type.getSerializedName()));
        }
        ListTag targets = conduitTag.getList("targets", ListTag.TAG_COMPOUND);
        targets.forEach(tag -> {
            BlockPos pos = readPos((CompoundTag) tag);
            String kind = ((CompoundTag) tag).getString("type");
            outputPositions.put(pos, kind.equals("all") ? Optional.empty() : Optional.of(EnumWillType.valueOf(kind.toUpperCase())));
        });
    }

    @Nullable
    private BlockPos readPos(CompoundTag tag) {
        if (tag.contains("pos")) {
            CompoundTag posTag = tag.getCompound("pos");
            return new BlockPos(posTag.getInt("x"), posTag.getInt("y"), posTag.getInt("y"));
        }
        return null;
    }
}
