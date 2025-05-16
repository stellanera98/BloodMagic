package wayoftime.bloodmagic.datagen.providers;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.block.ARCBlock;
import wayoftime.bloodmagic.common.block.BMBlocks;
import wayoftime.bloodmagic.util.DemonWillType;

public class BMBlockstateProvider extends BlockStateProvider {
    public BMBlockstateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BloodMagic.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        BMBlocks.BASIC_BLOCKS.getEntries().forEach(block -> {
            simpleBlockWithItem(block.get(), cubeAll(block.get()));
        });

        VariantBlockStateBuilder builder = getVariantBuilder(BMBlocks.ARC.block().get());
        String bottom = "block/arc_bottom";
        String lit = "_lit";
        for (DemonWillType type : DemonWillType.values()) {
            String willName = type.getSerializedName();
            String side = "block/arc_side_" + willName;
            String front = "block/arc_front_" + willName;
            String top = "block/arc_top_" + willName;
            ModelFile on = models().orientableWithBottom("alchemical_reaction_chamber_" + willName + "_lit", bm(side + lit), bm(front + lit), bm(bottom), bm(top));
            ModelFile off = models().orientableWithBottom("alchemical_reaction_chamber_" + willName, bm(side), bm(front), bm(bottom), bm(top));
            if (type == DemonWillType.DEFAULT) {
                simpleBlockItem(BMBlocks.ARC.block().get(), off);
            }

            builder.partialState().with(ARCBlock.LIT, false).with(ARCBlock.FACING, Direction.NORTH).with(ARCBlock.TYPE, type).modelForState().modelFile(off).addModel();
            builder.partialState().with(ARCBlock.LIT, false).with(ARCBlock.FACING, Direction.EAST).with(ARCBlock.TYPE, type).modelForState().modelFile(off).rotationY(90).addModel();
            builder.partialState().with(ARCBlock.LIT, false).with(ARCBlock.FACING, Direction.SOUTH).with(ARCBlock.TYPE, type).modelForState().modelFile(off).rotationY(180).addModel();
            builder.partialState().with(ARCBlock.LIT, false).with(ARCBlock.FACING, Direction.WEST).with(ARCBlock.TYPE, type).modelForState().modelFile(off).rotationY(270).addModel();

            builder.partialState().with(ARCBlock.LIT, true).with(ARCBlock.FACING, Direction.NORTH).with(ARCBlock.TYPE, type).modelForState().modelFile(on).addModel();
            builder.partialState().with(ARCBlock.LIT, true).with(ARCBlock.FACING, Direction.EAST).with(ARCBlock.TYPE, type).modelForState().modelFile(on).rotationY(90).addModel();
            builder.partialState().with(ARCBlock.LIT, true).with(ARCBlock.FACING, Direction.SOUTH).with(ARCBlock.TYPE, type).modelForState().modelFile(on).rotationY(180).addModel();
            builder.partialState().with(ARCBlock.LIT, true).with(ARCBlock.FACING, Direction.WEST).with(ARCBlock.TYPE, type).modelForState().modelFile(on).rotationY(270).addModel();
        }
    }

    private static ResourceLocation bm(String path) {
        return ResourceLocation.fromNamespaceAndPath(BloodMagic.MODID, path);
    }
}
