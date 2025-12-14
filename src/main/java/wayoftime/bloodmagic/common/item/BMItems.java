package wayoftime.bloodmagic.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemLore;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.api.BMIdentifiers;
import wayoftime.bloodmagic.api.item.SigilItem;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.common.datacomponent.Binding;
import wayoftime.bloodmagic.common.sigil.SigilConfig;
import wayoftime.bloodmagic.common.sigil.SigilEffect;

import java.util.List;
import java.util.function.Supplier;

public class BMItems {
    public static final DeferredRegister<Item> BASIC_ITEMS = DeferredRegister.createItems(BloodMagic.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(BloodMagic.MODID);
    public static final DeferredRegister<Item> WILL_ITEMS = DeferredRegister.createItems(BloodMagic.MODID);
    public static final DeferredRegister<Item> SIGILS = DeferredRegister.createItems(BloodMagic.MODID);
    public static final DeferredRegister<Item> SIGILS_TOGGLEABLE = DeferredRegister.createItems(BloodMagic.MODID);
    public static final DeferredRegister<Item> TAB_REQ = DeferredRegister.createItems(BloodMagic.MODID);

    // these go first for creative tab order
    public static final DeferredHolder<Item, ArmorItem> LIVING_HELMET = BASIC_ITEMS.register("living_helmet", makeLivingArmour(ArmorItem.Type.HELMET));
    public static final DeferredHolder<Item, LivingArmourItem> LIVING_PLATE = TAB_REQ.register("living_plate", LivingArmourItem::new);
    public static final DeferredHolder<Item, ArmorItem> LIVING_LEGGINGS = BASIC_ITEMS.register("living_leggings", makeLivingArmour(ArmorItem.Type.LEGGINGS));
    public static final DeferredHolder<Item, ArmorItem> LIVING_BOOTS = BASIC_ITEMS.register("living_boots", makeLivingArmour(ArmorItem.Type.BOOTS));
    public static final DeferredHolder<Item, UpgradeTomeItem> UPGRADE_TOME = TAB_REQ.register("upgrade_tome", UpgradeTomeItem::new);

    public static final DeferredHolder<Item, ScrapItem> UPGRADE_SCRAP = BASIC_ITEMS.register("upgrade_scrap", () -> new ScrapItem(new Item.Properties().stacksTo(1)));
    public static final DeferredHolder<Item, ScrapItem> SYNTHETIC_POINT = BASIC_ITEMS.register("synthetic_point", () -> new ScrapItem(new Item.Properties().component(BMDataComponents.UPGRADE_SCRAP, 1)));

    public static final DeferredHolder<Item, TrainerItem> TRAINING_BRACELET = BASIC_ITEMS.register("training_bracelet", TrainerItem::new);

    public static final DeferredHolder<Item, BloodOrbItem> ORB_WEAK = BASIC_ITEMS.register("blood_orb_weak", BloodOrbItem::new);
    public static final DeferredHolder<Item, BloodOrbItem> ORB_APPRENTICE = BASIC_ITEMS.register("blood_orb_apprentice", BloodOrbItem::new);
    public static final DeferredHolder<Item, BloodOrbItem> ORB_MAGICIAN = BASIC_ITEMS.register("blood_orb_magician", BloodOrbItem::new);
    public static final DeferredHolder<Item, BloodOrbItem> ORB_MASTER = BASIC_ITEMS.register("blood_orb_master", BloodOrbItem::new);
    public static final DeferredHolder<Item, BloodOrbItem> ORB_ARCHMAGE = BASIC_ITEMS.register("blood_orb_archmage", BloodOrbItem::new);
    public static final DeferredHolder<Item, BloodOrbItem> ORB_TRANSCENDENT = BASIC_ITEMS.register("blood_orb_transcendent", BloodOrbItem::new);

    private static Supplier<ArmorItem> makeLivingArmour(ArmorItem.Type type) {
        return () -> new ArmorItem(BMMaterialsAndTiers.LIVING_ARMOUR_MATERIAL, type, new Item.Properties().durability(type.getDurability(33)));
    }
    public static final DeferredHolder<Item, SacrificialDaggerItem> SACRIFICIAL_DAGGER = ITEMS.register("sacrificial_dagger", SacrificialDaggerItem::new);

    public static final DeferredHolder<Item, RawSoulItem> RAW_WILL = WILL_ITEMS.register("raw_will", RawSoulItem::new);

    public static final DeferredHolder<Item, SoulGemItem> SOUL_GEM_PETTY = WILL_ITEMS.register("soul_gem_petty", SoulGemItem::new);
    public static final DeferredHolder<Item, SoulGemItem> SOUL_GEM_LESSER = WILL_ITEMS.register("soul_gem_lesser", SoulGemItem::new);
    public static final DeferredHolder<Item, SoulGemItem> SOUL_GEM_COMMON = WILL_ITEMS.register("soul_gem_common", SoulGemItem::new);
    public static final DeferredHolder<Item, SoulGemItem> SOUL_GEM_GREATER = WILL_ITEMS.register("soul_gem_greater", SoulGemItem::new);
    public static final DeferredHolder<Item, SoulGemItem> SOUL_GEM_GRAND = WILL_ITEMS.register("soul_gem_grand", SoulGemItem::new);

    private static Item.Properties makeSigilProperties(String type, boolean toggleable) {
        Item.Properties props = new Item.Properties()
                .component(DataComponents.LORE, new ItemLore(List.of(Component.translatable("tooltip.bloodmagic.sigil." + type).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY))))
                .component(BMDataComponents.BINDING, Binding.EMPTY)
                .stacksTo(1);
        // item cooldowns are stored on player -> blood light sigil

        if (toggleable) {
            props.component(BMDataComponents.SIGIL_ACTIVE, false);
        }

        return props;
    }

    private static ResourceKey<SigilEffect> sigilEffectKey(ResourceLocation loc) {
        return ResourceKey.create(BMIdentifiers.RegistryKeys.SIGIL_EFFECTS, loc);
    }

    public static final DeferredHolder<Item, SigilItem> SIGIL_DIVINATION = SIGILS.register("sigil_divination", () -> new SigilItem(
            makeSigilProperties("divination", false),
            SigilConfig.DIVINATION,
            sigilEffectKey(BMIdentifiers.Sigils.DIVINATION))
    );

    public static final DeferredHolder<Item, SigilItem> SIGIL_SEER = SIGILS.register("sigil_seer", () -> new SigilItem(
            makeSigilProperties("seer", false),
            SigilConfig.SEER,
            sigilEffectKey(BMIdentifiers.Sigils.SEER))
    );

    public static final DeferredHolder<Item, SigilItem> SIGIL_LAVA = SIGILS.register("sigil_lava", () -> new SigilItem(
            makeSigilProperties("lava", false),
            SigilConfig.LAVA,
            sigilEffectKey(BMIdentifiers.Sigils.LAVA))
    );

    public static final DeferredHolder<Item, SigilItem> SIGIL_WATER = SIGILS.register("sigil_water", () -> new SigilItem(
            makeSigilProperties("water", false),
            SigilConfig.WATER,
            sigilEffectKey(BMIdentifiers.Sigils.WATER))
    );

    public static final DeferredHolder<Item, SigilItem> SIGIL_VOID = SIGILS.register("sigil_void", () -> new SigilItem(
            makeSigilProperties("void", false),
            SigilConfig.VOID,
            sigilEffectKey(BMIdentifiers.Sigils.VOID))
    );

    public static final DeferredHolder<Item, SigilItem> SIGIL_MINER = SIGILS_TOGGLEABLE.register("sigil_miner", () -> new SigilItem(
            makeSigilProperties("miner", true),
            SigilConfig.MINER,
            sigilEffectKey(BMIdentifiers.Sigils.MINER))
    );

    public static void register(IEventBus modBus) {
        BASIC_ITEMS.register(modBus);
        ITEMS.register(modBus);
        WILL_ITEMS.register(modBus);
        SIGILS.register(modBus);
        SIGILS_TOGGLEABLE.register(modBus);
        TAB_REQ.register(modBus);
    }
}
