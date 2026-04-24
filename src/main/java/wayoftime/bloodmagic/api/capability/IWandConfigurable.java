package wayoftime.bloodmagic.api.capability;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import wayoftime.bloodmagic.common.datacomponent.EnumWillType;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public interface IWandConfigurable {

    void addConnection(BlockPos target, @Nullable EnumWillType type, Consumer<MutableComponent> response);

    void removeConnection(BlockPos target, Consumer<MutableComponent> response);

    void toggleSelectedState(boolean selected);

    void toggleWillType(EnumWillType type, Consumer<MutableComponent> response);
}
