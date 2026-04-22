package wayoftime.bloodmagic.api.capability;

import net.minecraft.core.BlockPos;
import wayoftime.bloodmagic.common.datacomponent.EnumWillType;

import javax.annotation.Nullable;

public interface IWandConfigurable {

    void addConnection(BlockPos target, @Nullable EnumWillType type);

    void removeConnection(BlockPos target);

    void toggleSelectedState(boolean selected);

    void toggleWillType(EnumWillType type);
}
