package wayoftime.bloodmagic.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.RangedWrapper;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.common.item.SoulGemItem;
import wayoftime.bloodmagic.common.recipe.BMRecipes;
import wayoftime.bloodmagic.common.recipe.soulforge.SoulForgeInput;
import wayoftime.bloodmagic.common.recipe.soulforge.SoulForgeRecipe;
import wayoftime.bloodmagic.common.tag.BMTags;
import wayoftime.bloodmagic.util.DemonWillType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HellfireForgeTile extends BlockEntity {
    public ItemStackHandler inv = new ItemStackHandler(6) {
        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            if (slot == OUTPUT_SLOT) {
                return false;
            }

            if (slot == GEM_SLOT && !stack.has(BMDataComponents.DEMON_WILL_AMOUNT)) {
                return false;
            }

            return true;
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            BloodMagic.LOGGER.info("called for {}", slot);
            setChanged();
        }
    };

    @Override
    public void setChanged() {
        super.setChanged();
        this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
    }

    // one day mojang is going to change Direction. But not today
    public static final int SOUTH = Direction.SOUTH.get2DDataValue(); // 0
    public static final int WEST = Direction.WEST.get2DDataValue(); // 1
    public static final int NORTH = Direction.NORTH.get2DDataValue(); // 2
    public static final int EAST = Direction.EAST.get2DDataValue(); // 3

    public static final int GEM_SLOT = 4;
    public static final int OUTPUT_SLOT = 5;

    public HellfireForgeTile(BlockPos pos, BlockState blockState) {
        super(BMTiles.HELLFIRE_FORGE_TYPE.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, HellfireForgeTile hellfireForgeTile) {
        SoulForgeInput input = hellfireForgeTile.getInput();
        Optional<RecipeHolder<SoulForgeRecipe>> recipeOptional = level.getRecipeManager().getRecipeFor(BMRecipes.SOUL_FORGE_TYPE.get(), input, level);
        if (!recipeOptional.isPresent()) {
            return;
        }
        SoulForgeRecipe recipe = recipeOptional.get().value();
        ItemStack output = recipe.assemble(input, level.registryAccess());
        if (output.isEmpty()) {
            BloodMagic.LOGGER.info("input matched but no result");
            return;
        }
        ItemStack currentOutput = hellfireForgeTile.inv.getStackInSlot(OUTPUT_SLOT);
        if (!currentOutput.isEmpty() && !ItemStack.isSameItemSameComponents(currentOutput, output)) {
            BloodMagic.LOGGER.info("outputs dont stack!");
            return;
        }

        ItemStack gemStack = hellfireForgeTile.inv.getStackInSlot(input.getGemIndex());
        double will = gemStack.getOrDefault(BMDataComponents.DEMON_WILL_AMOUNT, 0D);
        will -= recipe.usedWill;
        gemStack.set(BMDataComponents.DEMON_WILL_AMOUNT, will);

        for (int i = SOUTH; i < GEM_SLOT; i++) {
            ItemStack item = hellfireForgeTile.inv.getStackInSlot(i);
            if (item.hasCraftingRemainingItem()) {
                hellfireForgeTile.inv.setStackInSlot(i, item.getCraftingRemainingItem());
                continue;
            }
            item.shrink(1);
            if (item.isEmpty()) {
                hellfireForgeTile.inv.setStackInSlot(i, ItemStack.EMPTY);
            }
        }
        hellfireForgeTile.inv.setStackInSlot(OUTPUT_SLOT, output);
        level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
    }

    public SoulForgeInput getInput() {
        ItemStack gemStack = inv.getStackInSlot(GEM_SLOT);
        List<ItemStack> stacks = new ArrayList<>();
        int gemIndex = GEM_SLOT;
        for (int i = SOUTH; i < GEM_SLOT; i++) {
            ItemStack testStack = inv.getStackInSlot(i);
            stacks.add(testStack);
            if (testStack.is(BMTags.Items.SOUL_GEM)) {
                gemStack = testStack;
                gemIndex = i;
            }
        }
        return new SoulForgeInput(stacks, gemStack, gemIndex);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inv.deserializeNBT(registries, tag.getCompound("inventory"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        CompoundTag inventory = inv.serializeNBT(registries);
        tag.put("inventory", inventory);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    public @Nullable IItemHandler getInventory(Direction side) {
        if (side == null) {
            return inv;
        }

        return switch (side) {
            case UP -> new RangedWrapper(inv, GEM_SLOT, GEM_SLOT + 1);
            case DOWN -> new RangedWrapper(inv, OUTPUT_SLOT, OUTPUT_SLOT + 1);
            default -> new RangedWrapper(inv, side.get2DDataValue(), side.get2DDataValue() + 1);
        };
    }
}
