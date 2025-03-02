package wayoftime.bloodmagic.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.blockentity.BMTiles;
import wayoftime.bloodmagic.blockentity.HellfireForgeTile;
import wayoftime.bloodmagic.util.helper.BlockEntityHelper;

public class HellfireForgeBlock extends Block implements EntityBlock {
    public static final VoxelShape BOX = box(1, 0, 1, 15, 12, 15);

    public HellfireForgeBlock() {
        super(Properties.of()
                .strength(2.0F, 5.0F)
                .requiresCorrectToolForDrops()
        );
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return ItemInteractionResult.FAIL;
        }

        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof HellfireForgeTile forge)) {
            return ItemInteractionResult.FAIL;
        }

        Vec3 relative = hitResult.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ());
        BloodMagic.LOGGER.info("{}, {}, {}", relative.x, relative.y, relative.z);


        return ItemInteractionResult.FAIL;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return BOX;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new HellfireForgeTile(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return BlockEntityHelper.getTicker(blockEntityType, BMTiles.HELLFIRE_FORGE_TYPE.get(), HellfireForgeTile::tick);
    }
}
