package wayoftime.bloodmagic.common.item;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import wayoftime.bloodmagic.BloodMagic;

public class BloodMagicItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(BloodMagic.MODID);
    public static final DeferredRegister<Item> BASICITEMS = DeferredRegister.createItems(BloodMagic.MODID);

    public static final DeferredHolder<Item, BloodOrbItem> BLOOD_ORB = ITEMS.register("blood_orb", BloodOrbItem::new);

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
        BASICITEMS.register(modBus);
    }
}
