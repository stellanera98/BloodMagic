package wayoftime.bloodmagic.common.block.base;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.items.IItemHandler;
import wayoftime.bloodmagic.common.blockentity.base.BaseTile;

public abstract class WorldInteractionBlock<W extends BaseTile> extends BaseTileBlock<W> implements EntityBlock {

    public WorldInteractionBlock(Properties properties, BlockEntityType<W> type) {
        super(properties, type);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (hand == InteractionHand.OFF_HAND) {
            return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
        }
        W tile = type.getBlockEntity(level, pos);
        if (tile == null) {
            return ItemInteractionResult.FAIL;
        }
        IItemHandler handler = tile.getItemHandler(hitResult.getDirection());
        if (stack.isEmpty()) {
            player.setItemInHand(hand, take(handler, player, hitResult));
        } else {
            if (put(handler, stack.copyWithCount(1), player, hitResult).isEmpty()) {
                stack.shrink(1);
            }
        }

        level.sendBlockUpdated(pos, state, state, UPDATE_CLIENTS);
        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    protected ItemStack take(IItemHandler handler, Player player, BlockHitResult result) {
        return handler.extractItem(0, player.getInventory().getMaxStackSize(), false);
    }

    protected ItemStack put(IItemHandler handler, ItemStack stack, Player player, BlockHitResult result) {
        return handler.insertItem(0, stack, false);
    }
}
