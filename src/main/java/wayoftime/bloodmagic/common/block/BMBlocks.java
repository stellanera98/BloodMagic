package wayoftime.bloodmagic.common.block;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.util.helper.BlockEntityHelper;
import wayoftime.bloodmagic.util.helper.BlockWithItemHolder;
import wayoftime.bloodmagic.util.helper.BlockWithItemRegister;

import java.util.List;

public class BMBlocks {
    public static final DeferredRegister<Block> BASIC_BLOCKS = DeferredRegister.createBlocks(BloodMagic.MODID);
    public static final DeferredRegister<Item> BASIC_BLOCK_ITEMS = DeferredRegister.createItems(BloodMagic.MODID);
    public static final BlockWithItemRegister BASIC_REG = new BlockWithItemRegister(BASIC_BLOCKS, BASIC_BLOCK_ITEMS);

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(BloodMagic.MODID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.createItems(BloodMagic.MODID);
    public static final BlockWithItemRegister BLOCK_REG = new BlockWithItemRegister(BLOCKS, BLOCK_ITEMS);

    public static final BlockWithItemHolder<BloodTankBlock, BlockItem> BLOOD_TANK = BLOCK_REG.register("blood_tank", BloodTankBlock::new, block -> new BlockItem(block, new Item.Properties().stacksTo(1).component(BMDataComponents.CONTAINER_TIER, 1)));
    public static final BlockWithItemHolder<BloodAltarBlock, BlockItem> BLOOD_ALTAR = BLOCK_REG.register("blood_altar", BloodAltarBlock::new);
    public static final BlockWithItemHolder<HellfireForgeBlock, BlockItem> HELLFIRE_FORGE = BLOCK_REG.register("soul_forge", HellfireForgeBlock::new);
    public static final BlockWithItemHolder<ARCBlock, BlockItem> ARC = BLOCK_REG.register("alchemical_reaction_chamber", ARCBlock::new);

    private static final BlockBehaviour.Properties RUNE_PROPERTIES = BlockBehaviour.Properties.of().strength(2.0F, 5.0F).requiresCorrectToolForDrops();
    private static final ItemLore SAVE_DECORATION = new ItemLore(List.of(BlockEntityHelper.translatableHover("tooltip.bloodmagic.save_for_decoration").withStyle(ChatFormatting.ITALIC)));
    private static final Item.Properties DECORATION_ITEM_PROPERTIES = new Item.Properties().component(DataComponents.LORE, SAVE_DECORATION);

    public static final BlockWithItemHolder<Block, BlockItem> RUNE_BLANK = BASIC_REG.register("rune_blank", RUNE_PROPERTIES, DECORATION_ITEM_PROPERTIES);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_SELF_SACRIFICE = BASIC_REG.register("rune_sacrifice_self", RUNE_PROPERTIES, DECORATION_ITEM_PROPERTIES);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_SACRIFICE = BASIC_REG.register("rune_sacrifice", RUNE_PROPERTIES, DECORATION_ITEM_PROPERTIES);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_CAPACITY = BASIC_REG.register("rune_capacity", RUNE_PROPERTIES, DECORATION_ITEM_PROPERTIES);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_CAPACITY_AUGMENTED = BASIC_REG.register("rune_capacity_augmented", RUNE_PROPERTIES, DECORATION_ITEM_PROPERTIES);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_SPEED = BASIC_REG.register("rune_speed", RUNE_PROPERTIES, DECORATION_ITEM_PROPERTIES);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_ACCELERATION = BASIC_REG.register("rune_acceleration", RUNE_PROPERTIES, DECORATION_ITEM_PROPERTIES);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_ORB = BASIC_REG.register("rune_orb", RUNE_PROPERTIES, DECORATION_ITEM_PROPERTIES);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_DISLOCATION = BASIC_REG.register("rune_dislocation", RUNE_PROPERTIES, DECORATION_ITEM_PROPERTIES);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_CHARGING = BASIC_REG.register("rune_charging", RUNE_PROPERTIES, DECORATION_ITEM_PROPERTIES);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_EFFICIENCY = BASIC_REG.register("rune_efficiency", RUNE_PROPERTIES, DECORATION_ITEM_PROPERTIES);

    public static final BlockWithItemHolder<Block, BlockItem> RUNE_2_SELF_SACRIFICE = BASIC_REG.register("rune_2_sacrifice_self", RUNE_PROPERTIES, DECORATION_ITEM_PROPERTIES);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_2_SACRIFICE = BASIC_REG.register("rune_2_sacrifice", RUNE_PROPERTIES, DECORATION_ITEM_PROPERTIES);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_2_CAPACITY = BASIC_REG.register("rune_2_capacity", RUNE_PROPERTIES, DECORATION_ITEM_PROPERTIES);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_2_CAPACITY_AUGMENTED = BASIC_REG.register("rune_2_capacity_augmented", RUNE_PROPERTIES, DECORATION_ITEM_PROPERTIES);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_2_SPEED = BASIC_REG.register("rune_2_speed", RUNE_PROPERTIES, DECORATION_ITEM_PROPERTIES);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_2_ACCELERATION = BASIC_REG.register("rune_2_acceleration", RUNE_PROPERTIES, DECORATION_ITEM_PROPERTIES);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_2_ORB = BASIC_REG.register("rune_2_orb", RUNE_PROPERTIES, DECORATION_ITEM_PROPERTIES);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_2_DISLOCATION = BASIC_REG.register("rune_2_dislocation", RUNE_PROPERTIES, DECORATION_ITEM_PROPERTIES);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_2_CHARGING = BASIC_REG.register("rune_2_charging", RUNE_PROPERTIES, DECORATION_ITEM_PROPERTIES);
    public static final BlockWithItemHolder<Block, BlockItem> RUNE_2_EFFICIENCY = BASIC_REG.register("rune_2_efficiency", RUNE_PROPERTIES, DECORATION_ITEM_PROPERTIES);

    private static final BlockBehaviour.Properties CAPSTONE_PROPERTIES = BlockBehaviour.Properties.of().strength(2.0F, 5.0F).requiresCorrectToolForDrops();
    public static final BlockWithItemHolder<Block, BlockItem> BLOODSTONE = BASIC_REG.register("bloodstone", CAPSTONE_PROPERTIES, DECORATION_ITEM_PROPERTIES);
    public static final BlockWithItemHolder<Block, BlockItem> BLOODSTONE_BRICK = BASIC_REG.register("bloodstone_brick", CAPSTONE_PROPERTIES, DECORATION_ITEM_PROPERTIES);
    public static final BlockWithItemHolder<Block, BlockItem> CRYSTAL_CLUSTER = BASIC_REG.register("crystal_cluster", CAPSTONE_PROPERTIES, DECORATION_ITEM_PROPERTIES);
    public static final BlockWithItemHolder<Block, BlockItem> CRYSTAL_BRICK = BASIC_REG.register("crystal_brick", CAPSTONE_PROPERTIES, DECORATION_ITEM_PROPERTIES);
    public static final BlockWithItemHolder<Block, BlockItem> HELLFORGED_BLOCK = BASIC_REG.register("hellforged_block", CAPSTONE_PROPERTIES, DECORATION_ITEM_PROPERTIES);

    public static void register(IEventBus modBus) {
        BASIC_BLOCKS.register(modBus);
        BASIC_BLOCK_ITEMS.register(modBus);
        BLOCKS.register(modBus);
        BLOCK_ITEMS.register(modBus);
    }
}
