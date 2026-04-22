package wayoftime.bloodmagic.datagen.content.loot;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import wayoftime.bloodmagic.common.block.AlchemyTableBlock;
import wayoftime.bloodmagic.common.block.BMBlocks;
import wayoftime.bloodmagic.util.TablePart;
import net.neoforged.neoforge.registries.DeferredHolder;
import wayoftime.bloodmagic.common.item.BMItems;
import wayoftime.bloodmagic.util.blockitem.BlockWithItemHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MineBlock extends BlockLootSubProvider {
    public MineBlock(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
        BMBlocks.BASIC_BLOCKS.getEntries().forEach(holder -> dropSelfList.add(holder.get()));
        BMBlocks.BLOCKS.getEntries().forEach(holder -> specialDropList.add(holder.get()));
        addDropSelf(BMBlocks.ARC_BLOCK); // TODO maybe let it keep fluids?
        addDropSelf(BMBlocks.BLOOD_ALTAR);
        addDropSelf(BMBlocks.HELLFIRE_FORGE);
        addDropSelf(BMBlocks.WILL_INFUSER);
        addDropSelf(BMBlocks.WILL_CRUCIBLE);
        addDropSpecial(BMBlocks.ALCHEMY_TABLE);

        addDropSelf(BMBlocks.WILL_BLOCK_RAW);
        addDropSelf(BMBlocks.WILL_BLOCK_CORROSIVE);
        addDropSelf(BMBlocks.WILL_BLOCK_DESTRUCTIVE);
        addDropSelf(BMBlocks.WILL_BLOCK_STEADFAST);
        addDropSelf(BMBlocks.WILL_BLOCK_VENGEFUL);
    }

    private void addDropSelf(BlockWithItemHolder<? extends Block, ? extends BlockItem> toAdd) {
        dropSelfList.add(toAdd.block().get());
        specialDropList.remove(toAdd.block().get());
    }

    private void addDropSpecial(BlockWithItemHolder<? extends Block, ? extends BlockItem> toAdd) {
        specialDropList.add(toAdd.block().get());
        dropSelfList.remove(toAdd.block().get());
    }

    private final List<Block> specialDropList = new ArrayList<>();
    private List<Block> dropSelfList = new ArrayList<>();

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> list = new ArrayList<>();
        list.addAll(specialDropList);
        list.addAll(dropSelfList);
        return list;
    }

    @Override
    protected void generate() {
        dropSelfList.forEach(this::dropSelf);
        copyComponents(BMBlocks.BLOOD_TANK);
        copyComponents(BMBlocks.LIVING_STATION);

        add(BMBlocks.ALCHEMY_TABLE.block().get(), block -> createSinglePropConditionTable(block, AlchemyTableBlock.PART, TablePart.LEFT));

        dropWhenSilkTouch(BMBlocks.WILL_BUD_SMALL_RAW);
        dropWhenSilkTouch(BMBlocks.WILL_BUD_MEDIUM_RAW);
        dropWhenSilkTouch(BMBlocks.WILL_BUD_LARGE_RAW);

        dropWhenSilkTouch(BMBlocks.WILL_BUD_SMALL_CORROSIVE);
        dropWhenSilkTouch(BMBlocks.WILL_BUD_MEDIUM_CORROSIVE);
        dropWhenSilkTouch(BMBlocks.WILL_BUD_LARGE_CORROSIVE);

        dropWhenSilkTouch(BMBlocks.WILL_BUD_SMALL_DESTRUCTIVE);
        dropWhenSilkTouch(BMBlocks.WILL_BUD_MEDIUM_DESTRUCTIVE);
        dropWhenSilkTouch(BMBlocks.WILL_BUD_LARGE_DESTRUCTIVE);

        dropWhenSilkTouch(BMBlocks.WILL_BUD_SMALL_STEADFAST);
        dropWhenSilkTouch(BMBlocks.WILL_BUD_MEDIUM_STEADFAST);
        dropWhenSilkTouch(BMBlocks.WILL_BUD_LARGE_STEADFAST);

        dropWhenSilkTouch(BMBlocks.WILL_BUD_SMALL_VENGEFUL);
        dropWhenSilkTouch(BMBlocks.WILL_BUD_MEDIUM_VENGEFUL);
        dropWhenSilkTouch(BMBlocks.WILL_BUD_LARGE_VENGEFUL);

        addCluster(BMBlocks.WILL_CLUSTER_RAW, BMItems.WILL_SHARD_RAW);
        addCluster(BMBlocks.WILL_CLUSTER_CORROSIVE, BMItems.WILL_SHARD_CORROSIVE);
        addCluster(BMBlocks.WILL_CLUSTER_DESTRUCTIVE, BMItems.WILL_SHARD_DESTRUCTIVE);
        addCluster(BMBlocks.WILL_CLUSTER_STEADFAST, BMItems.WILL_SHARD_STEADFAST);
        addCluster(BMBlocks.WILL_CLUSTER_VENGEFUL, BMItems.WILL_SHARD_VENGEFUL);

        add(BMBlocks.BUDDING_WILL_RAW.block().get(), noDrop());
        add(BMBlocks.BUDDING_WILL_CORROSIVE.block().get(), noDrop());
        add(BMBlocks.BUDDING_WILL_DESTRUCTIVE.block().get(), noDrop());
        add(BMBlocks.BUDDING_WILL_STEADFAST.block().get(), noDrop());
        add(BMBlocks.BUDDING_WILL_VENGEFUL.block().get(), noDrop());
    }

    HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
    private void addCluster(BlockWithItemHolder<? extends Block, ? extends BlockItem> holder, DeferredHolder<Item, ? extends Item> shard) {
        this.add(
                holder.block().get(),
                cluster -> this.createSilkTouchDispatchTable(
                        cluster,
                        LootItem.lootTableItem(shard.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0f)))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0f), true)
                                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))))
                                .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                                .apply(ApplyExplosionDecay.explosionDecay())
                )
        );
    }

    private void dropWhenSilkTouch(BlockWithItemHolder<? extends Block, ? extends BlockItem> holder) {
        dropWhenSilkTouch(holder.block().get());
    }

    private void copyComponents(BlockWithItemHolder<? extends Block, ? extends BlockItem> holder) {
        add(
                holder.block().get(),
                LootTable.lootTable().withPool(
                        this.applyExplosionCondition(holder.block().get(), LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(holder)
                                        .apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY))
                                )
                        )
                )
        );
    }
}
