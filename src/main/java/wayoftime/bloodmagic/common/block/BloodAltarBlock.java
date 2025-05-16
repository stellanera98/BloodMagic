package wayoftime.bloodmagic.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import wayoftime.bloodmagic.common.blockentity.BMTiles;
import wayoftime.bloodmagic.common.blockentity.BloodAltarTile;
import wayoftime.bloodmagic.util.helper.BlockEntityHelper;

public class BloodAltarBlock extends Block implements EntityBlock {
    public BloodAltarBlock() {
        super(Properties.of()
                .forceSolidOn()
                .requiresCorrectToolForDrops()
                .strength(2.0F, 5.0F)
        );
    }

    public static final VoxelShape BOX = box(0, 0, 0, 16, 12, 16);

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return BOX;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BloodAltarTile(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return BlockEntityHelper.getTicker(blockEntityType, BMTiles.BLOOD_ALTAR_TYPE.get(), BloodAltarTile::tick);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (hand != InteractionHand.MAIN_HAND) {
            return ItemInteractionResult.CONSUME;
        }
        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof BloodAltarTile tile)) {
            return ItemInteractionResult.FAIL;
        }
        ItemStack altarStack = tile.inv.getStackInSlot(0);
        //altarStack.getCapability(BloodMagicCapabilities.ALTAR_READER);
        if (altarStack.isEmpty() && !stack.isEmpty()) {
            tile.inv.setStackInSlot(0, stack.copy());
            level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
            player.setItemInHand(hand, ItemStack.EMPTY);
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        } else if (!altarStack.isEmpty() && stack.isEmpty()) {
            player.setItemInHand(hand, altarStack.copy());
            tile.inv.setStackInSlot(0, ItemStack.EMPTY);
            level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return ItemInteractionResult.CONSUME;
    }
}
