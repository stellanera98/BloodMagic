package wayoftime.bloodmagic.common.item;

import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.util.DemonWillType;

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

    public static final DeferredHolder<Item, LivingArmourItem> LIVING_HELMET = BASICITEMS.register("living_helmet", () -> new LivingArmourItem(ArmorItem.Type.HELMET));
    public static final DeferredHolder<Item, LivingArmourItem> LIVING_PLATE = BASICITEMS.register("living_plate", () -> new LivingArmourItem(ArmorItem.Type.CHESTPLATE));
    public static final DeferredHolder<Item, LivingArmourItem> LIVING_LEGGINGS = BASICITEMS.register("living_leggings", () -> new LivingArmourItem(ArmorItem.Type.LEGGINGS));
    public static final DeferredHolder<Item, LivingArmourItem> LIVING_BOOTS = BASICITEMS.register("living_boots", () -> new LivingArmourItem(ArmorItem.Type.BOOTS));

    public static final DeferredHolder<Item, UpgradeTomeItem> UPGRADE_TOME = BASICITEMS.register("upgrade_tome", UpgradeTomeItem::new);

    public static final DeferredHolder<Item, Item> BLOOD_SHARD_WEAK = BASICITEMS.register("blood_shard_weak", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> INGOT_HELLFORGED = BASICITEMS.register("ingot_hellforged", () -> new Item(new Item.Properties()));

    public static final DeferredHolder<Item, SacrificialDaggerItem> SACRIFICIAL_DAGGER = ITEMS.register("sacrificial_dagger", SacrificialDaggerItem::new);

    public static final DeferredHolder<Item, SoulGemItem> SOUL_GEM_PETTY = WILLITEMS.register("soul_gem_petty", SoulGemItem::new);
    public static final DeferredHolder<Item, SoulGemItem> SOUL_GEM_LESSER = WILLITEMS.register("soul_gem_lesser", SoulGemItem::new);
    public static final DeferredHolder<Item, SoulGemItem> SOUL_GEM_COMMON = WILLITEMS.register("soul_gem_common", SoulGemItem::new);
    public static final DeferredHolder<Item, SoulGemItem> SOUL_GEM_GREATER = WILLITEMS.register("soul_gem_greater", SoulGemItem::new);
    public static final DeferredHolder<Item, SoulGemItem> SOUL_GEM_GRAND = WILLITEMS.register("soul_gem_grand", SoulGemItem::new);

    public static final DeferredHolder<Item, RawSoulItem> RAW_WILL = WILLITEMS.register("raw_will", RawSoulItem::new);

    public static final DeferredHolder<Item, Item> HELLFORGED_PARTS = BASICITEMS.register("hellforged_parts", () -> new Item(new Item.Properties()));

    public static final DeferredHolder<Item, Item> SANGUINE_REVERTER = BASICITEMS.register("reverter_basic", () -> new Item(arcProperties(DemonWillType.STEADFAST, 1, 2, 64)));

    public static final DeferredHolder<Item, Item> HYDRATION_CELL = BASICITEMS.register("hydration_basic", () -> new Item(arcProperties(DemonWillType.DEFAULT, 1, 1, 64)));
    public static final DeferredHolder<Item, Item> PRIMITIVE_HYDRATION_CELL = BASICITEMS.register("hydration_primitive", () -> new Item(arcProperties(DemonWillType.DEFAULT, 1, 1.5, 256)));

    public static final DeferredHolder<Item, Item> PRIMITIVE_FURNACE_CELL = BASICITEMS.register("smelting_primitive", () -> new Item(arcProperties(DemonWillType.DEFAULT, 1, 3, 256)));

    public static final DeferredHolder<Item, Item> EXPLOSIVE_POWDER = BASICITEMS.register("explosive_basic", () -> new Item(arcProperties(DemonWillType.DESTRUCTIVE, 1, 1, 64)));
    public static final DeferredHolder<Item, Item> PRIMITIVE_EXPLOSIVE_CELL = BASICITEMS.register("explosive_primitive", () -> new Item(arcProperties(DemonWillType.DESTRUCTIVE, 1, 1.5, 256)));
    public static final DeferredHolder<Item, Item> HELLFORGED_EXPLOSIVE_CELL = BASICITEMS.register("explosive_hellforged", () -> new Item(arcProperties(DemonWillType.DESTRUCTIVE, 1, 2, 1024)));

    public static final DeferredHolder<Item, Item> RESONATOR = BASICITEMS.register("resonator_basic", () -> new Item(arcProperties(DemonWillType.VENGEFUL, 1, 1, 64)));
    public static final DeferredHolder<Item, Item> PRIMITIVE_RESONATOR = BASICITEMS.register("resonator_primitive", () -> new Item(arcProperties(DemonWillType.VENGEFUL, 1, 1.5, 256)));
    public static final DeferredHolder<Item, Item> HELLFORGED_RESONATOR = BASICITEMS.register("resonator_hellforged", () -> new Item(arcProperties(DemonWillType.VENGEFUL, 2, 2, 1024)));

    public static final DeferredHolder<Item, Item> CUTTING_FLUID = BASICITEMS.register("cutting_fluid_basic", () -> new Item(arcProperties(DemonWillType.CORROSIVE, 1, 1, 64)));
    public static final DeferredHolder<Item, Item> PRIMITIVE_CUTTING_FLUID = BASICITEMS.register("cutting_fluid_primitive", () -> new Item(arcProperties(DemonWillType.CORROSIVE, 1, 1.5, 256)));
    public static final DeferredHolder<Item, Item> HELLFORGED_CUTTING_FLUID = BASICITEMS.register("cutting_fluid_hellforged", () -> new Item(arcProperties(DemonWillType.CORROSIVE, 2, 2, 1024)));

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
        BASICITEMS.register(modBus);
        WILLITEMS.register(modBus);
    }

    private static Item.Properties arcProperties(DemonWillType type, double chance, double speed, int uses) {
        return new Item.Properties().stacksTo(1).durability(uses)
                .component(BMDataComponents.ARC_SPEED, speed)
                .component(BMDataComponents.ARC_CHANCE, chance)
                .component(BMDataComponents.DEMON_WILL_TYPE, type);
    }
}
