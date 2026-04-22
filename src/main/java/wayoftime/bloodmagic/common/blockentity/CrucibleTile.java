package wayoftime.bloodmagic.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import wayoftime.bloodmagic.api.BMTags;
import wayoftime.bloodmagic.api.capability.IWillHandler;
import wayoftime.bloodmagic.common.blockentity.base.SingleTargetWillInteractor;
import wayoftime.bloodmagic.common.caps.BMCaps;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.common.datacomponent.EnumWillType;
import wayoftime.bloodmagic.common.datamap.BMDataMaps;
import wayoftime.bloodmagic.common.datamap.WillStack;

public class CrucibleTile extends SingleTargetWillInteractor {

    public ItemStackHandler inv = new ItemStackHandler(1) {
        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return stack.is(BMTags.Items.TARTARIC_GEM)
                    || stack.getItemHolder().getData(BMDataMaps.DEMON_CRUCIBLE) != null;
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    public static final int GEM_DRAIN_RATE = 10;

    public CrucibleTile(BlockPos pos, BlockState blockState) {
        super(BMTiles.CRUCIBLE_TYPE.get(), pos, blockState, 0);
    }


    @Override
    public void tick(Level level, BlockPos pos, BlockState state) {
        IWillHandler targetHandler = level.getCapability(BMCaps.BLOCK_WILL_HANDLER, target);
        if (targetHandler == null) {
            return;
        }
        double toSend;
        EnumWillType sendType;
        ItemStack stack = inv.getStackInSlot(0);
        IWillHandler gemHandler = stack.getCapability(BMCaps.ITEM_WILL_HANDLER);
        if (stack.is(BMTags.Items.TARTARIC_GEM) && gemHandler != null) {
            sendType = stack.getOrDefault(BMDataComponents.DEMON_WILL_TYPE, EnumWillType.RAW);
            toSend = gemHandler.drain(sendType, GEM_DRAIN_RATE, false);
        } else {
            WillStack has = stack.getItemHolder().getData(BMDataMaps.DEMON_CRUCIBLE);
            toSend = has != null ? has.amount() : 0;
            sendType = has != null ? has.type() : null;
        }
        if (sendType == null || toSend <= 0) {
            return;
        }

        double filled = targetHandler.fill(sendType, toSend, false);
        if (filled != toSend) {
            return;
        }

        targetHandler.fill(sendType, toSend, true);
        if (stack.is(BMTags.Items.TARTARIC_GEM)) {
            gemHandler.drain(sendType, toSend, true);
        } else {
            stack.shrink(1);
        }
        inv.setStackInSlot(0, stack);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inv.deserializeNBT(registries, tag.getCompound("inv"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inv", inv.serializeNBT(registries));
    }

    @Override
    public void setChanged() {
        super.setChanged();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }

    @Override
    public void toggleSelectedState(boolean selected) {
        // TODO implement line rendering
    }

    @Override
    public void toggleWillType(EnumWillType type) {
        // TODO only have it accept correct type items?
    }

    @Override
    public @Nullable IItemHandler getItemHandler(@Nullable Direction direction) {
        return inv;
    }
}
