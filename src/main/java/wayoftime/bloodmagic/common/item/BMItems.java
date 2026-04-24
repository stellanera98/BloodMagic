package wayoftime.bloodmagic.common.item;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.api.BMTags;
import wayoftime.bloodmagic.common.caps.BMCaps;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;

import java.util.function.Supplier;

public class BMItems {
    public static final DeferredRegister<Item> BASIC_ITEMS = DeferredRegister.createItems(BloodMagic.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(BloodMagic.MODID);
    public static final DeferredRegister<Item> WILL_ITEMS = DeferredRegister.createItems(BloodMagic.MODID);
    public static final DeferredRegister<Item> TAB_REQ = DeferredRegister.createItems(BloodMagic.MODID);

    private static Supplier<ArmorItem> makeLivingArmor(ArmorItem.Type type) {
        return () -> new ArmorItem(BMMaterialsAndTiers.LIVING_ARMOR_MATERIAL, type, new Item.Properties().durability(type.getDurability(33)));
    }
    // these go first for creative tab order
    public static final DeferredHolder<Item, ArmorItem> LIVING_HELMET = BASIC_ITEMS.register("living_helmet", makeLivingArmor(ArmorItem.Type.HELMET));
    public static final DeferredHolder<Item, LivingArmorItem> LIVING_PLATE = TAB_REQ.register("living_plate", LivingArmorItem::new);
    public static final DeferredHolder<Item, ArmorItem> LIVING_LEGGINGS = BASIC_ITEMS.register("living_leggings", makeLivingArmor(ArmorItem.Type.LEGGINGS));
    public static final DeferredHolder<Item, ArmorItem> LIVING_BOOTS = BASIC_ITEMS.register("living_boots", makeLivingArmor(ArmorItem.Type.BOOTS));
    public static final DeferredHolder<Item, UpgradeTomeItem> UPGRADE_TOME = TAB_REQ.register("upgrade_tome", UpgradeTomeItem::new);

    public static final DeferredHolder<Item, ScrapItem> UPGRADE_SCRAP = BASIC_ITEMS.register("upgrade_scrap", () -> new ScrapItem(new Item.Properties().stacksTo(1)));
    public static final DeferredHolder<Item, ScrapItem> SYNTHETIC_POINT = BASIC_ITEMS.register("synthetic_point", () -> new ScrapItem(new Item.Properties().component(BMDataComponents.UPGRADE_SCRAP, 1)));

    public static final DeferredHolder<Item, TrainerItem> TRAINING_BRACELET = BASIC_ITEMS.register("training_bracelet", TrainerItem::new);

    public static final DeferredHolder<Item, SigilItem> SIGIL = TAB_REQ.register("sigil", SigilItem::new);

    public static final DeferredHolder<Item, BloodOrbItem> ORB_WEAK = BASIC_ITEMS.register("blood_orb_weak", BloodOrbItem::new);
    public static final DeferredHolder<Item, BloodOrbItem> ORB_APPRENTICE = BASIC_ITEMS.register("blood_orb_apprentice", BloodOrbItem::new);
    public static final DeferredHolder<Item, BloodOrbItem> ORB_MAGICIAN = BASIC_ITEMS.register("blood_orb_magician", BloodOrbItem::new);
    public static final DeferredHolder<Item, BloodOrbItem> ORB_MASTER = BASIC_ITEMS.register("blood_orb_master", BloodOrbItem::new);
    public static final DeferredHolder<Item, BloodOrbItem> ORB_ARCHMAGE = BASIC_ITEMS.register("blood_orb_archmage", BloodOrbItem::new);
    public static final DeferredHolder<Item, BloodOrbItem> ORB_TRANSCENDENT = BASIC_ITEMS.register("blood_orb_transcendent", BloodOrbItem::new);

    // these are here because I was trying to use the blank slate model for missing SigilType and Im not gonna delete them now that I remembered about the black/purple missing model
    public static final DeferredHolder<Item, Item> SLATE_BLANK = BASIC_ITEMS.register("slate_blank", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> SLATE_REINFORCED = BASIC_ITEMS.register("slate_reinforced", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> SLATE_IMBUED = BASIC_ITEMS.register("slate_imbued", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> SLATE_DEMONIC = BASIC_ITEMS.register("slate_demonic", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> SLATE_ETHEREAL = BASIC_ITEMS.register("slate_ethereal", () -> new Item(new Item.Properties()));
    // TODO I dont think there ever was/should be a T6 slate? if there was we should add it here as well

    public static final DeferredHolder<Item, SacrificialDaggerItem> SACRIFICIAL_DAGGER = ITEMS.register("sacrificial_dagger", SacrificialDaggerItem::new);

    public static final DeferredHolder<Item, WandItem> WILL_ROUTING_WAND = ITEMS.register("will_routing_wand", WandItem::new);

    public static final DeferredHolder<Item, ManifestedWillItem> MANIFESTED_WILL = WILL_ITEMS.register("manifested_will", ManifestedWillItem::new);

    public static final DeferredHolder<Item, TartaricGemItem> SOUL_GEM_PETTY = WILL_ITEMS.register("soul_gem_petty", TartaricGemItem::new);
    public static final DeferredHolder<Item, TartaricGemItem> SOUL_GEM_LESSER = WILL_ITEMS.register("soul_gem_lesser", TartaricGemItem::new);
    public static final DeferredHolder<Item, TartaricGemItem> SOUL_GEM_COMMON = WILL_ITEMS.register("soul_gem_common", TartaricGemItem::new);
    public static final DeferredHolder<Item, TartaricGemItem> SOUL_GEM_GREATER = WILL_ITEMS.register("soul_gem_greater", TartaricGemItem::new);
    public static final DeferredHolder<Item, TartaricGemItem> SOUL_GEM_GRAND = WILL_ITEMS.register("soul_gem_grand", TartaricGemItem::new);

    public static final DeferredHolder<Item, WillCatalystItem> WILL_CATALYST_RAW = BASIC_ITEMS.register("will_catalyst_raw", () -> new WillCatalystItem(BMTags.Blocks.CATALYST_TARGET_RAW));
    public static final DeferredHolder<Item, WillCatalystItem> WILL_CATALYST_CORROSIVE = BASIC_ITEMS.register("will_catalyst_corrosive", () -> new WillCatalystItem(BMTags.Blocks.CATALYST_TARGET_CORROSIVE));
    public static final DeferredHolder<Item, WillCatalystItem> WILL_CATALYST_DESTRUCTIVE = BASIC_ITEMS.register("will_catalyst_destructive", () -> new WillCatalystItem(BMTags.Blocks.CATALYST_TARGET_DESTRUCTIVE));
    public static final DeferredHolder<Item, WillCatalystItem> WILL_CATALYST_STEADFAST = BASIC_ITEMS.register("will_catalyst_steadfast", () -> new WillCatalystItem(BMTags.Blocks.CATALYST_TARGET_STEADFAST));
    public static final DeferredHolder<Item, WillCatalystItem> WILL_CATALYST_VENGEFUL = BASIC_ITEMS.register("will_catalyst_vengeful", () -> new WillCatalystItem(BMTags.Blocks.CATALYST_TARGET_VENGEFUL));

    // TODO switch back once textures exist
    public static final DeferredHolder<Item, Item> WILL_SHARD_RAW = BASIC_ITEMS.register("will_shard_raw", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> WILL_SHARD_CORROSIVE = BASIC_ITEMS.register("will_shard_corrosive", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> WILL_SHARD_DESTRUCTIVE = BASIC_ITEMS.register("will_shard_destructive", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> WILL_SHARD_STEADFAST = BASIC_ITEMS.register("will_shard_steadfast", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> WILL_SHARD_VENGEFUL = BASIC_ITEMS.register("will_shard_vengeful", () -> new Item(new Item.Properties()));

    public static final DeferredHolder<Item, Item> WILL_CRYSTAL_RAW = BASIC_ITEMS.register("will_crystal_raw", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> WILL_CRYSTAL_CORROSIVE = BASIC_ITEMS.register("will_crystal_corrosive", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> WILL_CRYSTAL_DESTRUCTIVE = BASIC_ITEMS.register("will_crystal_destructive", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> WILL_CRYSTAL_STEADFAST = BASIC_ITEMS.register("will_crystal_steadfast", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> WILL_CRYSTAL_VENGEFUL = BASIC_ITEMS.register("will_crystal_vengeful", () -> new Item(new Item.Properties()));

    private static void registerItemCaps(RegisterCapabilitiesEvent event) {
        event.registerItem(
                BMCaps.ITEM_WILL_HANDLER,
                TartaricGemItem::getWillHandler,
                SOUL_GEM_PETTY.get(), SOUL_GEM_LESSER.get(), SOUL_GEM_COMMON.get(), SOUL_GEM_GREATER.get(), SOUL_GEM_GRAND.get()
        );
        // unsure if needed. keeping it for now
        event.registerItem(
                BMCaps.ITEM_WILL_HANDLER,
                ManifestedWillItem::getWillHandler,
                MANIFESTED_WILL.get()
        );
    }

    public static void register(IEventBus modBus) {
        BASIC_ITEMS.register(modBus);
        ITEMS.register(modBus);
        WILL_ITEMS.register(modBus);
        TAB_REQ.register(modBus);

        modBus.addListener(BMItems::registerItemCaps);
    }
}
