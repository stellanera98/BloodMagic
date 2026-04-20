package wayoftime.bloodmagic.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

public class NBTHelper {
    public static CompoundTag getPosTag(BlockPos pos) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("x", pos.getX());
        tag.putInt("y", pos.getY());
        tag.putInt("z", pos.getZ());

        return tag;
    }

    public static BlockPos getBlockPos(CompoundTag tag) {
        return new BlockPos(
                tag.getInt("x"),
                tag.getInt("y"),
                tag.getInt("z")
        );
    }
}
