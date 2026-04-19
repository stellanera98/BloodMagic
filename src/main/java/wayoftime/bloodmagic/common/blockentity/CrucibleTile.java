package wayoftime.bloodmagic.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import wayoftime.bloodmagic.api.BMTags;
import wayoftime.bloodmagic.common.datamap.BMDataMaps;

public class CrucibleTile extends BaseTile {

    public ItemStackHandler inv = new ItemStackHandler(1) {
        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return stack.is(BMTags.Items.TARTARIC_GEM)
                    || stack.getItemHolder().getData(BMDataMaps.DEMON_CRUCIBLE) != null;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    public static final int GEM_DRAIN_RATE = 10;

    public CrucibleTile(BlockPos pos, BlockState blockState) {
        super(BMTiles.CRUCIBLE_TYPE.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, CrucibleTile crucible) {
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
}
