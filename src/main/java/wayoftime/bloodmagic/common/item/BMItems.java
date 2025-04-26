package wayoftime.bloodmagic.common.item;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import wayoftime.bloodmagic.BloodMagic;

public class BMItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(BloodMagic.MODID);
    public static final DeferredRegister<Item> BASICITEMS = DeferredRegister.createItems(BloodMagic.MODID);
    public static final DeferredRegister<Item> WILLITEMS = DeferredRegister.createItems(BloodMagic.MODID);

    public static final DeferredHolder<Item, BloodOrbItem> ORB_WEAK = BASICITEMS.register("blood_orb_weak", BloodOrbItem::new);
    public static final DeferredHolder<Item, BloodOrbItem> ORB_APPRENTICE = BASICITEMS.register("blood_orb_apprentice", BloodOrbItem::new);
    public static final DeferredHolder<Item, BloodOrbItem> ORB_MAGICIAN = BASICITEMS.register("blood_orb_magician", BloodOrbItem::new);
    public static final DeferredHolder<Item, BloodOrbItem> ORB_MASTER = BASICITEMS.register("blood_orb_master", BloodOrbItem::new);
    public static final DeferredHolder<Item, BloodOrbItem> ORB_ARCHMAGE = BASICITEMS.register("blood_orb_archmage", BloodOrbItem::new);
    public static final DeferredHolder<Item, BloodOrbItem> ORB_TRANSCENDENT = BASICITEMS.register("blood_orb_transcendent", BloodOrbItem::new);

    public static final DeferredHolder<Item, Item> SLATE_BLANK = BASICITEMS.register("slate_blank", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> SLATE_REINFORCED = BASICITEMS.register("slate_reinforced", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> SLATE_IMBUED = BASICITEMS.register("slate_imbued", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> SLATE_DEMONIC = BASICITEMS.register("slate_demonic", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> SLATE_ETHEREAL = BASICITEMS.register("slate_ethereal", () -> new Item(new Item.Properties()));

    public static final DeferredHolder<Item, Item> BLOOD_SHARD_WEAK = BASICITEMS.register("blood_shard_weak", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> INGOT_HELLFORGED = BASICITEMS.register("ingot_hellforged", () -> new Item(new Item.Properties()));

    public static final DeferredHolder<Item, SacrificialDaggerItem> SACRIFICIAL_DAGGER = ITEMS.register("sacrificial_dagger", SacrificialDaggerItem::new);

    public static final DeferredHolder<Item, SoulGemItem> SOUL_GEM_PETTY = WILLITEMS.register("soul_gem_petty", SoulGemItem::new);
    public static final DeferredHolder<Item, SoulGemItem> SOUL_GEM_LESSER = WILLITEMS.register("soul_gem_lesser", SoulGemItem::new);
    public static final DeferredHolder<Item, SoulGemItem> SOUL_GEM_COMMON = WILLITEMS.register("soul_gem_common", SoulGemItem::new);
    public static final DeferredHolder<Item, SoulGemItem> SOUL_GEM_GREATER = WILLITEMS.register("soul_gem_greater", SoulGemItem::new);
    public static final DeferredHolder<Item, SoulGemItem> SOUL_GEM_GRAND = WILLITEMS.register("soul_gem_grand", SoulGemItem::new);

    public static final DeferredHolder<Item, RawSoulItem> RAW_WILL = WILLITEMS.register("raw_will", RawSoulItem::new);

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
        BASICITEMS.register(modBus);
        WILLITEMS.register(modBus);
    }
}
