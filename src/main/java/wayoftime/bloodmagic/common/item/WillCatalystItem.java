package wayoftime.bloodmagic.common.item;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import wayoftime.bloodmagic.common.block.WillClusterBlock;

public class WillCatalystItem extends Item {

    public final TagKey<Block> validBlocks;

    // TODO revisit, check if we actually want them to do this and not something else
    public WillCatalystItem(TagKey<Block> validBlocks) {
        super(new Properties());

        this.validBlocks = validBlocks;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        if (state.is(validBlocks) && !state.getValue(WillClusterBlock.ANOINTED)) {
            level.sendBlockUpdated(pos, state, state.setValue(WillClusterBlock.ANOINTED, Boolean.TRUE), Block.UPDATE_ALL);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }
}
