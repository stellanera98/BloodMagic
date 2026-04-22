package wayoftime.bloodmagic.common.block;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.api.BMTags;
import wayoftime.bloodmagic.common.caps.BMCaps;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.common.datacomponent.EnumWillType;
import wayoftime.bloodmagic.common.datamap.BMDataMaps;
import wayoftime.bloodmagic.common.datamap.BloodRune;
import wayoftime.bloodmagic.api.altar.EnumRuneType;
import wayoftime.bloodmagic.util.BlockEntityHelper;
import wayoftime.bloodmagic.util.blockitem.BlockWithItemHolder;
import wayoftime.bloodmagic.util.blockitem.BlockWithItemRegister;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BMBlocks {
    public static final DeferredRegister<Block> BASIC_BLOCKS = DeferredRegister.createBlocks(BloodMagic.MODID);
    public static final DeferredRegister<Item> BASIC_BLOCK_ITEMS = DeferredRegister.createItems(BloodMagic.MODID);
    public static final BlockWithItemRegister BASIC_REG = new BlockWithItemRegister(BASIC_BLOCKS, BASIC_BLOCK_ITEMS);

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(BloodMagic.MODID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.createItems(BloodMagic.MODID);
    public static final BlockWithItemRegister BLOCK_REG = new BlockWithItemRegister(BLOCKS, BLOCK_ITEMS);

    public static final BlockWithItemHolder<BloodAltarBlock, BlockItem> BLOOD_ALTAR = BLOCK_REG.register("blood_altar", BloodAltarBlock::new);
    public static final BlockWithItemHolder<BloodTankBlock, BlockItem> BLOOD_TANK = BLOCK_REG.register("blood_tank", BloodTankBlock::new, block -> new BlockItem(block, new Item.Properties().component(BMDataComponents.CONTAINER_TIER, 1)));
    public static final BlockWithItemHolder<HellfireForgeBlock, BlockItem> HELLFIRE_FORGE = BLOCK_REG.register("hellfire_forge", HellfireForgeBlock::new);
    public static final BlockWithItemHolder<ARCBlock, BlockItem> ARC_BLOCK = BLOCK_REG.register("arc", ARCBlock::new);
    public static final BlockWithItemHolder<AlchemyTableBlock, BlockItem> ALCHEMY_TABLE = BLOCK_REG.register("alchemy_table", AlchemyTableBlock::new);

    public static final BlockWithItemHolder<InfuserBlock, BlockItem> WILL_INFUSER = BASIC_REG.register("will_infuser", InfuserBlock::new);
    public static final BlockWithItemHolder<CrucibleBlock, BlockItem> WILL_CRUCIBLE = BASIC_REG.register("will_crucible", CrucibleBlock::new);

    // TODO add model/textures for this and change registry to BASIC_REG
    public static final BlockWithItemHolder<LivingStationBlock, BlockItem> LIVING_STATION = BLOCK_REG.register("living_station", LivingStationBlock::new);

    public static final BlockWithItemHolder<ImperfectRitualBlock, BlockItem> IMPERFECT_RITUAL_BLOCK = BASIC_REG.register("ritual_stone_imperfect", ImperfectRitualBlock::new);

    private static final BlockBehaviour.Properties rune_properties = BlockBehaviour.Properties.of().strength(2.0F, 5.0F).sound(SoundType.STONE).requiresCorrectToolForDrops();
    private static final ItemLore safe_decoration = new ItemLore(List.of(BlockEntityHelper.translatableHover("tooltip.bloodmagic.safe_for_decoration").withStyle(ChatFormatting.ITALIC)));
    private static final Item.Properties decoration_item_properties = new Item.Properties().component(DataComponents.LORE, safe_decoration);

    public static final BlockWithItemHolder<Block, BlockItem> RUNE_BLANK = BASIC_REG.register("rune_blank", rune_properties, decoration_item_properties);

    public static final BlockWithItemHolder<Block, BlockItem> RUNE_SACRIFICE = BASIC_REG.register("rune_sacrifice", rune_properties, decoration_item_properties);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_SELF_SACRIFICE = BASIC_REG.register("rune_sacrifice_self", rune_properties, decoration_item_properties);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_CAPACITY = BASIC_REG.register("rune_capacity", rune_properties, decoration_item_properties);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_CAPACITY_AUGMENTED = BASIC_REG.register("rune_capacity_augmented", rune_properties, decoration_item_properties);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_CHARGING = BASIC_REG.register("rune_charging", rune_properties, decoration_item_properties);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_SPEED = BASIC_REG.register("rune_speed", rune_properties, decoration_item_properties);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_ACCELERATION = BASIC_REG.register("rune_acceleration", rune_properties, decoration_item_properties);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_DISLOCATION = BASIC_REG.register("rune_dislocation", rune_properties, decoration_item_properties);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_ORB = BASIC_REG.register("rune_orb", rune_properties, decoration_item_properties);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_EFFICIENCY = BASIC_REG.register("rune_efficiency", rune_properties, decoration_item_properties);

    public static final BlockWithItemHolder<Block, BlockItem> RUNE_2_SACRIFICE = BASIC_REG.register("rune_2_sacrifice", rune_properties, decoration_item_properties);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_2_SELF_SACRIFICE = BASIC_REG.register("rune_2_sacrifice_self", rune_properties, decoration_item_properties);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_2_CAPACITY = BASIC_REG.register("rune_2_capacity", rune_properties, decoration_item_properties);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_2_CAPACITY_AUGMENTED = BASIC_REG.register("rune_2_capacity_augmented", rune_properties, decoration_item_properties);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_2_CHARGING = BASIC_REG.register("rune_2_charging", rune_properties, decoration_item_properties);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_2_SPEED = BASIC_REG.register("rune_2_speed", rune_properties, decoration_item_properties);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_2_ACCELERATION = BASIC_REG.register("rune_2_acceleration", rune_properties, decoration_item_properties);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_2_DISLOCATION = BASIC_REG.register("rune_2_dislocation", rune_properties, decoration_item_properties);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_2_ORB = BASIC_REG.register("rune_2_orb", rune_properties, decoration_item_properties);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_2_EFFICIENCY = BASIC_REG.register("rune_2_efficiency", rune_properties, decoration_item_properties);

    public static final BlockWithItemHolder<Block, BlockItem> BLOODSTONE = BASIC_REG.register("bloodstone", rune_properties, decoration_item_properties);
    public static final BlockWithItemHolder<Block, BlockItem> BLOODSTONE_BRICK = BASIC_REG.register("bloodstone_brick", rune_properties, decoration_item_properties);

    public static final BlockWithItemHolder<Block, BlockItem> HELLFORGED_BLOCK = BASIC_REG.register("hellforged_block", BlockBehaviour.Properties.of().strength(5, 6).sound(SoundType.METAL).requiresCorrectToolForDrops(), new Item.Properties());

    public static final BlockWithItemHolder<Block, BlockItem> CRYSTAL_CLUSTER = BASIC_REG.register("crystal_cluster", rune_properties, decoration_item_properties);
    public static final BlockWithItemHolder<Block, BlockItem> CRYSTAL_CLUSTER_BRICK = BASIC_REG.register("crystal_cluster_brick", rune_properties, decoration_item_properties);

    private static BlockBehaviour.Properties getClusterProp(SoundType type, int light, MapColor color) {
        return BlockBehaviour.Properties.of()
                .forceSolidOn()
                .noOcclusion()
                .strength(1.5f)
                .pushReaction(PushReaction.DESTROY)
                .mapColor(color)
                .sound(type)
                .lightLevel(state -> light);
    }

    private static BlockBehaviour.Properties getWillBlockProp(MapColor color) {
        return BlockBehaviour.Properties.of()
                .strength(1.5f)
                .requiresCorrectToolForDrops()
                .sound(SoundType.AMETHYST)
                .mapColor(color);
    }

    private static final MapColor MAP_COLOR_RAW = MapColor.COLOR_LIGHT_BLUE;
    private static final MapColor MAP_COLOR_CORROSIVE = MapColor.COLOR_GREEN;
    private static final MapColor MAP_COLOR_DESTRUCTIVE = MapColor.COLOR_ORANGE;
    private static final MapColor MAP_COLOR_STEADFAST = MapColor.COLOR_BLUE;
    private static final MapColor MAP_COLOR_VENGEFUL = MapColor.COLOR_RED;

    // TODO switch to BASIC once textures exist
    public static final BlockWithItemHolder<Block, BlockItem> WILL_BLOCK_RAW = BLOCK_REG.register("will_block_raw", getWillBlockProp(MAP_COLOR_RAW));
    public static final BlockWithItemHolder<Block, BlockItem> WILL_BLOCK_CORROSIVE = BLOCK_REG.register("will_block_corrosive", getWillBlockProp(MAP_COLOR_CORROSIVE));
    public static final BlockWithItemHolder<Block, BlockItem> WILL_BLOCK_DESTRUCTIVE = BLOCK_REG.register("will_block_destructive", getWillBlockProp(MAP_COLOR_DESTRUCTIVE));
    public static final BlockWithItemHolder<Block, BlockItem> WILL_BLOCK_STEADFAST = BLOCK_REG.register("will_block_steadfast", getWillBlockProp(MAP_COLOR_STEADFAST));
    public static final BlockWithItemHolder<Block, BlockItem> WILL_BLOCK_VENGEFUL = BLOCK_REG.register("will_block_vengeful", getWillBlockProp(MAP_COLOR_VENGEFUL));

    // these stay block for models
    public static final BlockWithItemHolder<WillClusterBlock, BlockItem> WILL_BUD_SMALL_RAW = BLOCK_REG.register("will_bud_small_raw", () -> new WillClusterBlock(3, 4, getClusterProp(SoundType.SMALL_AMETHYST_BUD, 1, MAP_COLOR_RAW)));
    public static final BlockWithItemHolder<WillClusterBlock, BlockItem> WILL_BUD_MEDIUM_RAW = BLOCK_REG.register("will_bud_medium_raw", () -> new WillClusterBlock(4, 3, getClusterProp(SoundType.MEDIUM_AMETHYST_BUD, 2, MAP_COLOR_RAW)));
    public static final BlockWithItemHolder<WillClusterBlock, BlockItem> WILL_BUD_LARGE_RAW = BLOCK_REG.register("will_bud_large_raw", () -> new WillClusterBlock(5, 3, getClusterProp(SoundType.LARGE_AMETHYST_BUD, 4, MAP_COLOR_RAW)));
    public static final BlockWithItemHolder<WillClusterBlock, BlockItem> WILL_CLUSTER_RAW = BLOCK_REG.register("will_cluster_raw", () -> new WillClusterBlock(7, 3, getClusterProp(SoundType.AMETHYST_CLUSTER, 5, MAP_COLOR_RAW)));
    public static final BlockWithItemHolder<BuddingWillBlock, BlockItem> BUDDING_WILL_RAW = BLOCK_REG.register("budding_will_raw", () -> new BuddingWillBlock(WILL_BUD_SMALL_RAW.block(), 1, BMTags.Blocks.WILL_BUD_RAW, MAP_COLOR_RAW));

    public static final BlockWithItemHolder<WillClusterBlock, BlockItem> WILL_BUD_SMALL_CORROSIVE = BLOCK_REG.register("will_bud_small_corrosive", () -> new WillClusterBlock(3, 4, getClusterProp(SoundType.SMALL_AMETHYST_BUD, 1, MAP_COLOR_CORROSIVE)));
    public static final BlockWithItemHolder<WillClusterBlock, BlockItem> WILL_BUD_MEDIUM_CORROSIVE = BLOCK_REG.register("will_bud_medium_corrosive", () -> new WillClusterBlock(4, 3, getClusterProp(SoundType.MEDIUM_AMETHYST_BUD, 2, MAP_COLOR_CORROSIVE)));
    public static final BlockWithItemHolder<WillClusterBlock, BlockItem> WILL_BUD_LARGE_CORROSIVE = BLOCK_REG.register("will_bud_large_corrosive", () -> new WillClusterBlock(5, 3, getClusterProp(SoundType.LARGE_AMETHYST_BUD, 4, MAP_COLOR_CORROSIVE)));
    public static final BlockWithItemHolder<WillClusterBlock, BlockItem> WILL_CLUSTER_CORROSIVE = BLOCK_REG.register("will_cluster_corrosive", () -> new WillClusterBlock(7, 3, getClusterProp(SoundType.AMETHYST_CLUSTER, 5, MAP_COLOR_CORROSIVE)));
    public static final BlockWithItemHolder<BuddingWillBlock, BlockItem> BUDDING_WILL_CORROSIVE = BLOCK_REG.register("budding_will_corrosive", () -> new BuddingWillBlock(WILL_BUD_SMALL_CORROSIVE.block(), 25, BMTags.Blocks.WILL_BUD_CORROSIVE, MAP_COLOR_CORROSIVE));

    public static final BlockWithItemHolder<WillClusterBlock, BlockItem> WILL_BUD_SMALL_DESTRUCTIVE = BLOCK_REG.register("will_bud_small_destructive", () -> new WillClusterBlock(3, 4, getClusterProp(SoundType.SMALL_AMETHYST_BUD, 1, MAP_COLOR_DESTRUCTIVE)));
    public static final BlockWithItemHolder<WillClusterBlock, BlockItem> WILL_BUD_MEDIUM_DESTRUCTIVE = BLOCK_REG.register("will_bud_medium_destructive", () -> new WillClusterBlock(4, 3, getClusterProp(SoundType.MEDIUM_AMETHYST_BUD, 2, MAP_COLOR_DESTRUCTIVE)));
    public static final BlockWithItemHolder<WillClusterBlock, BlockItem> WILL_BUD_LARGE_DESTRUCTIVE = BLOCK_REG.register("will_bud_large_destructive", () -> new WillClusterBlock(5, 3, getClusterProp(SoundType.LARGE_AMETHYST_BUD, 4, MAP_COLOR_DESTRUCTIVE)));
    public static final BlockWithItemHolder<WillClusterBlock, BlockItem> WILL_CLUSTER_DESTRUCTIVE = BLOCK_REG.register("will_cluster_destructive", () -> new WillClusterBlock(7, 3, getClusterProp(SoundType.AMETHYST_CLUSTER, 5, MAP_COLOR_DESTRUCTIVE)));
    public static final BlockWithItemHolder<BuddingWillBlock, BlockItem> BUDDING_WILL_DESTRUCTIVE = BLOCK_REG.register("budding_will_destructive", () -> new BuddingWillBlock(WILL_BUD_SMALL_DESTRUCTIVE.block(), 25, BMTags.Blocks.WILL_BUD_DESTRUCTIVE, MAP_COLOR_DESTRUCTIVE));

    public static final BlockWithItemHolder<WillClusterBlock, BlockItem> WILL_BUD_SMALL_STEADFAST = BLOCK_REG.register("will_bud_small_steadfast", () -> new WillClusterBlock(3, 4, getClusterProp(SoundType.SMALL_AMETHYST_BUD, 1, MAP_COLOR_STEADFAST)));
    public static final BlockWithItemHolder<WillClusterBlock, BlockItem> WILL_BUD_MEDIUM_STEADFAST = BLOCK_REG.register("will_bud_medium_steadfast", () -> new WillClusterBlock(4, 3, getClusterProp(SoundType.MEDIUM_AMETHYST_BUD, 2, MAP_COLOR_STEADFAST)));
    public static final BlockWithItemHolder<WillClusterBlock, BlockItem> WILL_BUD_LARGE_STEADFAST = BLOCK_REG.register("will_bud_large_steadfast", () -> new WillClusterBlock(5, 3, getClusterProp(SoundType.LARGE_AMETHYST_BUD, 4, MAP_COLOR_STEADFAST)));
    public static final BlockWithItemHolder<WillClusterBlock, BlockItem> WILL_CLUSTER_STEADFAST = BLOCK_REG.register("will_cluster_steadfast", () -> new WillClusterBlock(7, 3, getClusterProp(SoundType.AMETHYST_CLUSTER, 5, MAP_COLOR_STEADFAST)));
    public static final BlockWithItemHolder<BuddingWillBlock, BlockItem> BUDDING_WILL_STEADFAST = BLOCK_REG.register("budding_will_steadfast", () -> new BuddingWillBlock(WILL_BUD_SMALL_STEADFAST.block(), 25, BMTags.Blocks.WILL_BUD_STEADFAST, MAP_COLOR_STEADFAST));

    public static final BlockWithItemHolder<WillClusterBlock, BlockItem> WILL_BUD_SMALL_VENGEFUL = BLOCK_REG.register("will_bud_small_vengeful", () -> new WillClusterBlock(3, 4, getClusterProp(SoundType.SMALL_AMETHYST_BUD, 1, MAP_COLOR_VENGEFUL)));
    public static final BlockWithItemHolder<WillClusterBlock, BlockItem> WILL_BUD_MEDIUM_VENGEFUL = BLOCK_REG.register("will_bud_medium_vengeful", () -> new WillClusterBlock(4, 3, getClusterProp(SoundType.MEDIUM_AMETHYST_BUD, 2, MAP_COLOR_VENGEFUL)));
    public static final BlockWithItemHolder<WillClusterBlock, BlockItem> WILL_BUD_LARGE_VENGEFUL = BLOCK_REG.register("will_bud_large_vengeful", () -> new WillClusterBlock(5, 3, getClusterProp(SoundType.LARGE_AMETHYST_BUD, 4, MAP_COLOR_VENGEFUL)));
    public static final BlockWithItemHolder<WillClusterBlock, BlockItem> WILL_CLUSTER_VENGEFUL = BLOCK_REG.register("will_cluster_vengeful", () -> new WillClusterBlock(7, 3, getClusterProp(SoundType.AMETHYST_CLUSTER, 5, MAP_COLOR_VENGEFUL)));
    public static final BlockWithItemHolder<BuddingWillBlock, BlockItem> BUDDING_WILL_VENGEFUL = BLOCK_REG.register("budding_will_vengeful", () -> new BuddingWillBlock(WILL_BUD_SMALL_VENGEFUL.block(), 25, BMTags.Blocks.WILL_BUD_VENGEFUL, MAP_COLOR_VENGEFUL));

    public static final BlockWithItemHolder<BellJarBlock, BlockItem> BELLJAR_OAK = BLOCK_REG.register("belljar_oak", BellJarBlock::new);
    public static final BlockWithItemHolder<BellJarBlock, BlockItem> BELLJAR_SPRUCE = BLOCK_REG.register("belljar_spruce", BellJarBlock::new);
    public static final BlockWithItemHolder<BellJarBlock, BlockItem> BELLJAR_BIRCH = BLOCK_REG.register("belljar_birch", BellJarBlock::new);
    public static final BlockWithItemHolder<BellJarBlock, BlockItem> BELLJAR_CHERRY = BLOCK_REG.register("belljar_cherry", BellJarBlock::new);
    public static final BlockWithItemHolder<BellJarBlock, BlockItem> BELLJAR_JUNGLE = BLOCK_REG.register("belljar_jungle", BellJarBlock::new);
    public static final BlockWithItemHolder<BellJarBlock, BlockItem> BELLJAR_DARK_OAK = BLOCK_REG.register("belljar_dark_oak", BellJarBlock::new);
    public static final BlockWithItemHolder<BellJarBlock, BlockItem> BELLJAR_CRIMSON = BLOCK_REG.register("belljar_crimson", BellJarBlock::new);
    public static final BlockWithItemHolder<BellJarBlock, BlockItem> BELLJAR_WARPED = BLOCK_REG.register("belljar_oak", BellJarBlock::new);
    public static final BlockWithItemHolder<BellJarBlock, BlockItem> BELLJAR_MANGROVE = BLOCK_REG.register("belljar_mangrove", BellJarBlock::new);
    public static final BlockWithItemHolder<BellJarBlock, BlockItem> BELLJAR_BAMBOO = BLOCK_REG.register("belljar_bamboo", BellJarBlock::new);

    private static void registerBlockCapability(RegisterCapabilitiesEvent event) {
        event.registerBlock(
                BMCaps.RUNE_POWERS,
                (level, pos, state, blockEntity, context) -> () -> {
                    List<BloodRune> runes = state.getBlockHolder().getData(BMDataMaps.BLOOD_RUNES);
                    Map<EnumRuneType, Integer> upgrades = new HashMap<>();
                    if (runes == null) {
                        return upgrades;
                    }

                    for (BloodRune rune : runes) {
                        upgrades.compute(rune.type(), (k, v) -> v == null ? rune.amount() : v + rune.amount());
                    }
                    return upgrades;
                },
                RUNE_ACCELERATION.block().get(), RUNE_SPEED.block().get(), RUNE_CHARGING.block().get(),
                RUNE_SACRIFICE.block().get(), RUNE_SELF_SACRIFICE.block().get(), RUNE_ORB.block().get(),
                RUNE_CAPACITY.block().get(), RUNE_CAPACITY_AUGMENTED.block().get(), RUNE_DISLOCATION.block().get(),
                RUNE_EFFICIENCY.block().get(),
                RUNE_2_ACCELERATION.block().get(), RUNE_2_SPEED.block().get(), RUNE_2_CHARGING.block().get(),
                RUNE_2_SACRIFICE.block().get(), RUNE_2_SELF_SACRIFICE.block().get(), RUNE_2_ORB.block().get(),
                RUNE_2_CAPACITY.block().get(), RUNE_2_CAPACITY_AUGMENTED.block().get(), RUNE_2_DISLOCATION.block().get(),
                RUNE_2_EFFICIENCY.block().get()
        );

        event.registerBlock(
                BMCaps.BLOCK_WILL_HANDLER,
                (level, pos, state, be, context) -> BuddingWillBlock.getWillHandler(level, pos, state, EnumWillType.RAW),
                BUDDING_WILL_RAW.block().get()
        );
        event.registerBlock(
                BMCaps.BLOCK_WILL_HANDLER,
                (level, pos, state, be, context) -> BuddingWillBlock.getWillHandler(level, pos, state, EnumWillType.CORROSIVE),
                BUDDING_WILL_CORROSIVE.block().get()
        );
        event.registerBlock(
                BMCaps.BLOCK_WILL_HANDLER,
                (level, pos, state, be, context) -> BuddingWillBlock.getWillHandler(level, pos, state, EnumWillType.DESTRUCTIVE),
                BUDDING_WILL_DESTRUCTIVE.block().get()
        );
        event.registerBlock(
                BMCaps.BLOCK_WILL_HANDLER,
                (level, pos, state, be, context) -> BuddingWillBlock.getWillHandler(level, pos, state, EnumWillType.STEADFAST),
                BUDDING_WILL_STEADFAST.block().get()
        );
        event.registerBlock(
                BMCaps.BLOCK_WILL_HANDLER,
                (level, pos, state, be, context) -> BuddingWillBlock.getWillHandler(level, pos, state, EnumWillType.VENGEFUL),
                BUDDING_WILL_VENGEFUL.block().get()
        );
    }

    public static void register(IEventBus modBus) {
        BASIC_BLOCKS.register(modBus);
        BASIC_BLOCK_ITEMS.register(modBus);
        BLOCKS.register(modBus);
        BLOCK_ITEMS.register(modBus);
        modBus.addListener(BMBlocks::registerBlockCapability);
    }
}
