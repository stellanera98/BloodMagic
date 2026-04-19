package wayoftime.bloodmagic.datagen.content.datamap;

import net.minecraft.world.item.Item;
import wayoftime.bloodmagic.common.block.BMBlocks;
import wayoftime.bloodmagic.common.datacomponent.EnumWillType;
import wayoftime.bloodmagic.common.datamap.BMDataMaps;
import wayoftime.bloodmagic.common.datamap.WillStack;
import wayoftime.bloodmagic.common.item.BMItems;
import wayoftime.bloodmagic.datagen.provider.DataMapBuilder;

public class CrucibleData {
        public static void bootstrap(DataMapBuilder<Item, WillStack> setup) {
                setup.apply(BMDataMaps.DEMON_CRUCIBLE)
                        .add(BMItems.WILL_SHARD_RAW, new WillStack(EnumWillType.RAW, 10d), false)
                        .add(BMItems.WILL_SHARD_CORROSIVE, new WillStack(EnumWillType.CORROSIVE, 10d), false)
                        .add(BMItems.WILL_SHARD_DESTRUCTIVE, new WillStack(EnumWillType.DESTRUCTIVE, 10d), false)
                        .add(BMItems.WILL_SHARD_STEADFAST, new WillStack(EnumWillType.STEADFAST, 10d), false)
                        .add(BMItems.WILL_SHARD_VENGEFUL, new WillStack(EnumWillType.VENGEFUL, 10d), false)

                        .add(BMBlocks.WILL_BLOCK_RAW.item(), new WillStack(EnumWillType.RAW, 40d), false)
                        .add(BMBlocks.WILL_BLOCK_CORROSIVE.item(), new WillStack(EnumWillType.CORROSIVE, 40d), false)
                        .add(BMBlocks.WILL_BLOCK_DESTRUCTIVE.item(), new WillStack(EnumWillType.DESTRUCTIVE, 40d), false)
                        .add(BMBlocks.WILL_BLOCK_STEADFAST.item(), new WillStack(EnumWillType.STEADFAST, 40d), false)
                        .add(BMBlocks.WILL_BLOCK_VENGEFUL.item(), new WillStack(EnumWillType.VENGEFUL, 40d), false)

                        .add(BMItems.WILL_CRYSTAL_RAW, new WillStack(EnumWillType.RAW, 50d), false)
                        .add(BMItems.WILL_CRYSTAL_CORROSIVE, new WillStack(EnumWillType.CORROSIVE, 50d), false)
                        .add(BMItems.WILL_CRYSTAL_DESTRUCTIVE, new WillStack(EnumWillType.DESTRUCTIVE, 50d), false)
                        .add(BMItems.WILL_CRYSTAL_STEADFAST, new WillStack(EnumWillType.STEADFAST, 50d), false)
                        .add(BMItems.WILL_CRYSTAL_VENGEFUL, new WillStack(EnumWillType.VENGEFUL, 50d), false);
        }
}