package wayoftime.bloodmagic.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.fluid.BMFluids;
import wayoftime.bloodmagic.util.RuneType;
import wayoftime.bloodmagic.util.AltarUtil;

import java.util.Map;

public class BloodAltarTile extends BlockEntity implements IFluidHandler {
    public int tier = 0;
    public int ticks;
    public int inputTank = 0;
    public int outputTank = 0;
    public int mainTank = 0;
    public int chargingTank = 0;
    public ItemStackHandler inv = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            setChanged();
        }
    };

    private float capacityMod = 1;
    private int tickRate = 20;
    private float consumptionMod = 1;
    private float sacrificeMod = 1;
    private float selfSacMod = 1;
    private float dislocationMod = 1;
    private float orbCapMod = 1;
    private float chargeAmountMod = 1;
    private float chargeCapMod = 1;
    private float efficiencyMod = 1;

    public BloodAltarTile(BlockPos pos, BlockState blockState) {
        super(BMTiles.BLOOD_ALTAR_TYPE.get(), pos, blockState);
    }

    public void calculateStats(Map<RuneType, Integer> upgrades) {
        capacityMod = (float) ((1D + 0.2D * upgrades.getOrDefault(RuneType.CAPACITY, 0) * Math.pow(1.075, upgrades.getOrDefault(RuneType.AUGMENTED_CAPACITY, 0))));
        tickRate = Math.max(1, 20 - upgrades.getOrDefault(RuneType.ACCELERATION, 0));
        consumptionMod = 1F + 0.2F * upgrades.getOrDefault(RuneType.SPEED, 0);
        sacrificeMod = 1F + 0.1F * upgrades.getOrDefault(RuneType.SACRIFICE, 0);
        selfSacMod = 1F + 0.1F * upgrades.getOrDefault(RuneType.SELF_SACRIFICE, 0);
        dislocationMod = (float) Math.pow(1.2, upgrades.getOrDefault(RuneType.DISPLACEMENT, 0));
        orbCapMod = 1F + 0.2F * upgrades.getOrDefault(RuneType.ORB, 0);
        chargeAmountMod = (10 * upgrades.getOrDefault(RuneType.CHARGING, 0) * (1 + consumptionMod/2));
        chargeCapMod = (float) Math.max(0.5 * capacityMod, 1) * upgrades.getOrDefault(RuneType.CHARGING, 0);
        efficiencyMod = (float) Math.pow(0.85, upgrades.getOrDefault(RuneType.EFFICIENCY, 0));
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BloodAltarTile tile) {
        if (level.isClientSide) {
            return;
        }

        tile.ticks++;
        if (tile.ticks % (20 * 5) == 0) {
            int newTier = AltarUtil.getTier(level, pos);
            Map<RuneType, Integer> newUpgrades = AltarUtil.getUpgrades(newTier, level, pos);
            tile.calculateStats(newUpgrades);
            level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
        }

        if (tile.ticks % Math.max(tile.tickRate, 1) == 0) {
            float ioAmount = 20F * tile.dislocationMod;
            int input = (int) Math.min(tile.inputTank, ioAmount);
            input = (int) Math.min(input, tile.getMainCapacity() - tile.mainTank);
            tile.inputTank -= input;
            tile.mainTank += input;

            int output = (int) Math.min(tile.mainTank, ioAmount);
            output = (int) Math.min(output, tile.getIOCapacity() - tile.outputTank);
            tile.mainTank -= output;
            tile.outputTank += output;

            //if (!tile.isActive) {
                int charge = (int) Math.min(tile.mainTank, tile.chargeAmountMod);
                charge = (int) Math.min(charge, tile.getChargingCapacity() - tile.chargingTank);
                tile.mainTank -= charge;
                tile.chargingTank += charge;
            //}
        }
    }

    public int getMainCapacity() {
        return (int) ((float) FluidType.BUCKET_VOLUME * 10F * capacityMod);
    }

    public int getIOCapacity() {
        return (int) ((float) FluidType.BUCKET_VOLUME * 1F * capacityMod);
    }

    public int getChargingCapacity() {
        return (int) ((float) FluidType.BUCKET_VOLUME * chargeCapMod);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        CompoundTag stats = tag.getCompound("upgrades");
        tickRate = stats.getInt("tickrate");
        capacityMod = stats.getFloat("capacity");
        consumptionMod = stats.getFloat("consumption");
        efficiencyMod = stats.getFloat("efficiency");
        sacrificeMod = stats.getFloat("sacrifice");
        selfSacMod = stats.getFloat("selfsacrifice");
        dislocationMod = stats.getFloat("dislocation");
        orbCapMod = stats.getFloat("orb");
        chargeAmountMod = stats.getFloat("chargeamount");
        chargeCapMod = stats.getFloat("chargecap");

        CompoundTag tanks = tag.getCompound("tanks");

        inputTank = tanks.getInt("input");
        outputTank = tanks.getInt("output");
        mainTank = tanks.getInt("main");
        chargingTank = tanks.getInt("charging");

        inv.deserializeNBT(registries, tag.getCompound("inventory"));

        this.tier = tag.getInt("tier");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        CompoundTag stats = new CompoundTag();
        stats.putInt("tickrate", tickRate);
        stats.putFloat("capacity", capacityMod);
        stats.putFloat("consumption", consumptionMod);
        stats.putFloat("efficiency", efficiencyMod);
        stats.putFloat("sacrifice", sacrificeMod);
        stats.putFloat("selfsacrifice", selfSacMod);
        stats.putFloat("dislocation", dislocationMod);
        stats.putFloat("orb", orbCapMod);
        stats.putFloat("chargeamount", chargeAmountMod);
        stats.putFloat("chargecap", chargeCapMod);

        CompoundTag tanks = new CompoundTag();
        tanks.putInt("input", inputTank);
        tanks.putInt("output", outputTank);
        tanks.putInt("main", mainTank);
        tanks.putInt("charging", chargingTank);

        CompoundTag inventory = inv.serializeNBT(registries);

        tag.put("tanks", tanks);

        tag.put("inventory", inventory);

        tag.put("stats", stats);
        tag.putInt("tier", this.tier);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL); // send to client
    }

    @Override
    public int getTanks() {
        return 3;
    }

    @Override
    public FluidStack getFluidInTank(int tank) {
        return switch (tank) {
            case 0 -> new FluidStack(BMFluids.LIFE_ESSENCE_SOURCE, mainTank);
            case 1 -> new FluidStack(BMFluids.LIFE_ESSENCE_SOURCE, inputTank);
            case 2 -> new FluidStack(BMFluids.LIFE_ESSENCE_SOURCE, outputTank);
            default -> FluidStack.EMPTY;
        };
    }

    @Override
    public int getTankCapacity(int tank) {
        return switch (tank) {
          case 0 -> getMainCapacity();
          case 1,2 -> getIOCapacity();
          default -> 0;
        };
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
        return stack.is(BMFluids.LIFE_ESSENCE_TYPE.get());
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (!isFluidValid(0, resource)) {
            return 0;
        }
        int canFill = Math.min(getIOCapacity() - inputTank, 0);
        canFill = Math.max(canFill, resource.getAmount());

        if (action.execute()) {
            inputTank += canFill;
            this.setChanged();
        }

        return canFill;
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        if (!isFluidValid(0, resource)) {
            return FluidStack.EMPTY;
        }

        return drain(resource.getAmount(), action);
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        int toDrain = Math.min(outputTank, maxDrain);

        if (action.execute()) {
            outputTank -= toDrain;
            this.setChanged();
        }

        return new FluidStack(BMFluids.LIFE_ESSENCE_SOURCE, toDrain);
    }
}
