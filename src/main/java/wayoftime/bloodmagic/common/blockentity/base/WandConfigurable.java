package wayoftime.bloodmagic.common.blockentity.base;

import net.minecraft.core.BlockPos;
import wayoftime.bloodmagic.common.datacomponent.EnumWillType;

import javax.annotation.Nullable;

public interface WandConfigurable {

    void addConnection(BlockPos target, @Nullable EnumWillType type);

    void removeConnection(BlockPos target);

    void toggleSelectedState(boolean selected);
}
