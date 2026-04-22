package wayoftime.bloodmagic.datagen.content.datamap;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import wayoftime.bloodmagic.common.block.BMBlocks;
import wayoftime.bloodmagic.common.datacomponent.EnumWillType;
import wayoftime.bloodmagic.common.datamap.BMDataMaps;
import wayoftime.bloodmagic.common.datamap.WillStack;
import wayoftime.bloodmagic.datagen.provider.DataMapBuilder;

@SuppressWarnings("deprecation") // builtInRegistryHolder is mojang deprecated - "dont override, only use >:"
public class InfusionData {
    public static void bootstrap(DataMapBuilder<Block, Pair<WillStack, Block>> builder) {
        builder.apply(BMDataMaps.WILL_INFUSION)
                .add(Blocks.BUDDING_AMETHYST.builtInRegistryHolder(), Pair.of(new WillStack(EnumWillType.RAW, 100d), BMBlocks.BUDDING_WILL_RAW.block().get()), false)

                .add(BMBlocks.WILL_BLOCK_RAW.block(), Pair.of(new WillStack(EnumWillType.RAW, 100d), BMBlocks.BUDDING_WILL_RAW.block().get()), false)
                .add(BMBlocks.WILL_BLOCK_CORROSIVE.block(), Pair.of(new WillStack(EnumWillType.CORROSIVE, 100d), BMBlocks.BUDDING_WILL_CORROSIVE.block().get()), false)
                .add(BMBlocks.WILL_BLOCK_DESTRUCTIVE.block(), Pair.of(new WillStack(EnumWillType.DESTRUCTIVE, 100d), BMBlocks.BUDDING_WILL_DESTRUCTIVE.block().get()), false)
                .add(BMBlocks.WILL_BLOCK_STEADFAST.block(), Pair.of(new WillStack(EnumWillType.STEADFAST, 100d), BMBlocks.BUDDING_WILL_STEADFAST.block().get()), false)
                .add(BMBlocks.WILL_BLOCK_VENGEFUL.block(), Pair.of(new WillStack(EnumWillType.VENGEFUL, 100d), BMBlocks.BUDDING_WILL_VENGEFUL.block().get()), false)

                .add(Blocks.AMETHYST_BLOCK.builtInRegistryHolder(), Pair.of(new WillStack(EnumWillType.RAW, 500d), Blocks.BUDDING_AMETHYST), false);
    }
}
