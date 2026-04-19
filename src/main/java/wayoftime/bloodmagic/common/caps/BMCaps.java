package wayoftime.bloodmagic.common.caps;

import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.api.capability.IRunePowers;
import wayoftime.bloodmagic.api.capability.IWillHandler;

public class BMCaps {
    public static final BlockCapability<IRunePowers, Void> RUNE_POWERS = BlockCapability.createVoid(BloodMagic.rl("rune_powers"), IRunePowers.class);

    public static final ItemCapability<IWillHandler, Void> ITEM_WILL_HANDLER = ItemCapability.createVoid(BloodMagic.rl("will_handler"), IWillHandler.class);

    public static final BlockCapability<IWillHandler, Void> BLOCK_WILL_HANDLER = BlockCapability.createVoid(BloodMagic.rl("will_handler"), IWillHandler.class);
}
