package wayoftime.bloodmagic.datagen.provider;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.block.ARCBlock;
import wayoftime.bloodmagic.common.block.BMBlocks;
import wayoftime.bloodmagic.common.block.BellJarBlock;
import wayoftime.bloodmagic.common.block.WillClusterBlock;
import wayoftime.bloodmagic.common.datacomponent.EnumWillType;
import wayoftime.bloodmagic.util.blockitem.BlockWithItemHolder;

import java.util.Set;

public class BMBlockstateProvider extends BlockStateProvider {
    public BMBlockstateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BloodMagic.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        BMBlocks.BASIC_BLOCKS.getEntries().forEach(block -> {
            simpleBlockWithItem(block.get(), cubeAll(block.get()));
        });

        jar(BMBlocks.BELLJAR_OAK, "oak");
        jar(BMBlocks.BELLJAR_SPRUCE, "spruce");
        jar(BMBlocks.BELLJAR_BIRCH, "birch");
        jar(BMBlocks.BELLJAR_CHERRY, "cherry");
        jar(BMBlocks.BELLJAR_JUNGLE, "jungle");
        jar(BMBlocks.BELLJAR_DARK_OAK, "dark_oak");
        jar(BMBlocks.BELLJAR_CRIMSON, "crimson");
        jar(BMBlocks.BELLJAR_WARPED, "warped");
        jar(BMBlocks.BELLJAR_MANGROVE, "mangrove");
        jar(BMBlocks.BELLJAR_BAMBOO, "bamboo");

        standard(BMBlocks.BUDDING_WILL_RAW);
        standard(BMBlocks.BUDDING_WILL_CORROSIVE);
        standard(BMBlocks.BUDDING_WILL_DESTRUCTIVE);
        standard(BMBlocks.BUDDING_WILL_STEADFAST);
        standard(BMBlocks.BUDDING_WILL_VENGEFUL);

        cross(BMBlocks.WILL_BUD_SMALL_RAW);
        cross(BMBlocks.WILL_BUD_SMALL_CORROSIVE);
        cross(BMBlocks.WILL_BUD_SMALL_DESTRUCTIVE);
        cross(BMBlocks.WILL_BUD_SMALL_STEADFAST);
        cross(BMBlocks.WILL_BUD_SMALL_VENGEFUL);

        cross(BMBlocks.WILL_BUD_MEDIUM_RAW);
        cross(BMBlocks.WILL_BUD_MEDIUM_CORROSIVE);
        cross(BMBlocks.WILL_BUD_MEDIUM_DESTRUCTIVE);
        cross(BMBlocks.WILL_BUD_MEDIUM_STEADFAST);
        cross(BMBlocks.WILL_BUD_MEDIUM_VENGEFUL);

        cross(BMBlocks.WILL_BUD_LARGE_RAW);
        cross(BMBlocks.WILL_BUD_LARGE_CORROSIVE);
        cross(BMBlocks.WILL_BUD_LARGE_DESTRUCTIVE);
        cross(BMBlocks.WILL_BUD_LARGE_STEADFAST);
        cross(BMBlocks.WILL_BUD_LARGE_VENGEFUL);

        cross(BMBlocks.WILL_CLUSTER_RAW);
        cross(BMBlocks.WILL_CLUSTER_CORROSIVE);
        cross(BMBlocks.WILL_CLUSTER_DESTRUCTIVE);
        cross(BMBlocks.WILL_CLUSTER_STEADFAST);
        cross(BMBlocks.WILL_CLUSTER_VENGEFUL);

        genARC();
        genStation();
        genPedestal();
    }

    private void standard(BlockWithItemHolder<? extends Block, BlockItem> block) {
        simpleBlockWithItem(block.block().get(), cubeAll(block.block().get()));
    }

    private void cross(BlockWithItemHolder<? extends Block, BlockItem> block) {
        ModelFile cross = models().cross(name(block), block.block().getId().withPrefix("block/")).renderType("cutout");
        VariantBlockStateBuilder builder = getVariantBuilder(block.block().get());

        builder.partialState().with(WillClusterBlock.FACING, Direction.DOWN).modelForState().modelFile(cross).rotationX(180).addModel();
        builder.partialState().with(WillClusterBlock.FACING, Direction.UP).modelForState().modelFile(cross).addModel();
        builder.partialState().with(WillClusterBlock.FACING, Direction.NORTH).modelForState().modelFile(cross).rotationX(90).addModel();
        builder.partialState().with(WillClusterBlock.FACING, Direction.SOUTH).modelForState().modelFile(cross).rotationX(90).rotationY(180).addModel();
        builder.partialState().with(WillClusterBlock.FACING, Direction.WEST).modelForState().modelFile(cross).rotationX(90).rotationY(270).addModel();
        builder.partialState().with(WillClusterBlock.FACING, Direction.EAST).modelForState().modelFile(cross).rotationX(90).rotationY(90).addModel();
    }

    private void jar(BlockWithItemHolder<BellJarBlock, BlockItem> block, String planks) {
        simpleBlockWithItem(
                block.block().get(),
                models().withExistingParent(block.block().getId().getPath(), bm("block/belljar_base"))
                        .texture("wood", mcLoc("block/" + planks + "_planks"))
        );
    }

    private void genARC() {
        VariantBlockStateBuilder builder = getVariantBuilder(BMBlocks.ARC_BLOCK.block().get());
        String bottom = "block/arc_bottom";
        String lit = "_lit";
        for (EnumWillType type : EnumWillType.types()) {
            String willName = type.getSerializedName();
            String side = "block/arc_side_" + willName;
            String front = "block/arc_front_" + willName;
            String top = "block/arc_top_" + willName;
            ModelFile on = models().orientableWithBottom("alchemical_reaction_chamber_" + willName + "_lit", bm(side + lit), bm(front + lit), bm(bottom), bm(top));
            ModelFile off = models().orientableWithBottom("alchemical_reaction_chamber_" + willName, bm(side), bm(front), bm(bottom), bm(top));
            if (type == EnumWillType.RAW) {
                simpleBlockItem(BMBlocks.ARC_BLOCK.block().get(), off);
            }

            for (Direction facing : Direction.Plane.HORIZONTAL) {
                builder.partialState().with(ARCBlock.LIT, false).with(ARCBlock.FACING, facing).with(ARCBlock.TYPE, type).modelForState().modelFile(off).rotationY((int) facing.getOpposite().toYRot()).addModel();
                builder.partialState().with(ARCBlock.LIT, true).with(ARCBlock.FACING, facing).with(ARCBlock.TYPE, type).modelForState().modelFile(on).rotationY((int) facing.getOpposite().toYRot()).addModel();
            }
        }
    }

    private void genPedestal() {
        ModelFile model = models().withExistingParent(name(BMBlocks.PEDESTAL), mcLoc("block/block"))
                .texture("air", modLoc("block/ritual_stone_air"))
                .texture("earth", modLoc("block/ritual_stone_earth"))
                .texture("water", modLoc("block/ritual_stone_water"))
                .texture("fire", modLoc("block/ritual_stone_fire"))
                .texture("blank", modLoc("block/ritual_stone_blank"))
                // foot
                .element().from(0, 0, 0)
                .to(16, 1, 16)
                .face(Direction.UP).texture("#blank").end()
                .face(Direction.DOWN).texture("#blank").end()
                .face(Direction.NORTH).texture("#blank").end()
                .face(Direction.SOUTH).texture("#blank").end()
                .face(Direction.WEST).texture("#blank").end()
                .face(Direction.EAST).texture("#blank").end().end()
                // pillar
                .element().from(3, 1, 3)
                .to(13, 11, 13)
                .face(Direction.UP).texture("#blank").uvs(0, 0, 16, 16).end()
                .face(Direction.NORTH).texture("#fire").uvs(0, 0, 16, 16).end()
                .face(Direction.SOUTH).texture("#water").uvs(0, 0, 16, 16).end()
                .face(Direction.WEST).texture("#air").uvs(0, 0, 16, 16).end()
                .face(Direction.EAST).texture("#earth").uvs(0, 0, 16, 16).end().end()
                // "cup" bottom
                .element().from(5, 11, 5)
                .to(11, 12, 11)
                .face(Direction.UP).texture("#blank").uvs(5, 5, 11, 11).end()
                .face(Direction.DOWN).texture("#blank").uvs(5, 5, 11, 11).end()
                .face(Direction.NORTH).texture("#blank").uvs(5, 0, 11, 1).end()
                .face(Direction.SOUTH).texture("#blank").uvs(5, 0, 11, 1).end()
                .face(Direction.WEST).texture("#blank").uvs(5, 0, 11, 1).end()
                .face(Direction.EAST).texture("#blank").uvs(5, 0, 11, 1).end().end()
                // layer 1, north
                .element().from(4, 12, 4)
                .to(12, 13, 5)
                .allFaces((dir, builder) -> builder.texture("#blank")).end()
                // layer 1, south
                .element().from(4, 12, 11)
                .to(12, 13, 12)
                .allFaces((dir, builder) -> builder.texture("#blank")).end()
                // layer 1, east
                .element().from(4, 12, 5)
                .to(5, 13, 11)
                .allFaces((dir, builder) -> builder.texture("#blank")).end()
                // layer 1, west
                .element().from(11, 12, 5)
                .to(12, 13, 11)
                .allFaces((dir, builder) -> builder.texture("#blank")).end()

                // layer 2, north
                .element().from(3, 13, 3)
                .to(13, 14, 4)
                .allFaces((dir, builder) -> builder.texture("#blank")).end()
                // layer 2, south
                .element().from(3, 13, 12)
                .to(13, 14, 13)
                .allFaces((dir, builder) -> builder.texture("#blank")).end()
                // layer 2, east
                .element().from(3, 13, 4)
                .to(4, 14, 12)
                .allFaces((dir, builder) -> builder.texture("#blank")).end()
                // layer 2, west
                .element().from(12, 13, 4)
                .to(13, 14, 12)
                .allFaces((dir, builder) -> builder.texture("#blank")).end()

                // layer 3, north
                .element().from(2, 14, 2)
                .to(14, 15, 3)
                .allFaces((dir, builder) -> builder.texture("#blank")).end()
                // layer 3, south
                .element().from(2, 14, 13)
                .to(14, 15, 14)
                .allFaces((dir, builder) -> builder.texture("#blank")).end()
                // layer 3, east
                .element().from(2, 14, 3)
                .to(3, 15, 13)
                .allFaces((dir, builder) -> builder.texture("#blank")).end()
                // layer 3, west
                .element().from(13, 14, 3)
                .to(14, 15, 13)
                .allFaces((dir, builder) -> builder.texture("#blank")).end()

                // layer 4, north
                .element().from(1, 15, 1)
                .to(15, 16, 2)
                .allFaces((dir, builder) -> builder.texture("#blank")).end()
                // layer 4, south
                .element().from(1, 15, 14)
                .to(15, 16, 15)
                .allFaces((dir, builder) -> builder.texture("#blank")).end()
                // layer 4, east
                .element().from(1, 15, 2)
                .to(2, 16, 14)
                .allFaces((dir, builder) -> builder.texture("#blank")).end()
                // layer 4, west
                .element().from(14, 15, 2)
                .to(15, 16, 14)
                .allFaces((dir, builder) -> builder.texture("#blank")).end();

        simpleBlockWithItem(BMBlocks.PEDESTAL.block().get(), model);
    }

    private void genStation() {
        ModelFile model = models().withExistingParent(name(BMBlocks.LIVING_STATION), mcLoc("block/block"))
                .texture("side", mcLoc("block/stripped_oak_log"))
                .texture("top", mcLoc("block/stripped_oak_log_top"))
                .texture("bark", mcLoc("block/oak_log"))
                .texture("dark_side", mcLoc("block/stripped_dark_oak_log"))
                .texture("dark_top", mcLoc("block/stripped_dark_oak_log_top"))
                .texture("desk", modLoc("block/living_desk"))
                .texture("under", modLoc("block/rune_blank"))
                .texture("particle", "#desk")
                // North-East foot
                .element().from(2, 0, 2)
                .to(4, 3, 4)
                .allFacesExcept((direction, builder) -> builder.uvs(0, 0, 16, 16).texture("#dark_side").end(), Set.of(Direction.UP, Direction.DOWN))
                .face(Direction.DOWN).uvs(0, 0, 16, 16).texture("#dark_top").end().end()
                // South-East foot
                .element().from(2, 0, 12)
                .to(4, 3, 14)
                .allFacesExcept((direction, builder) -> builder.uvs(0, 0, 16, 16).texture("#dark_side").end(), Set.of(Direction.UP, Direction.DOWN))
                .face(Direction.DOWN).uvs(0, 0, 16, 16).texture("#dark_top").end().end()
                // South-West foot
                .element().from(12, 0, 12)
                .to(14, 3, 14)
                .allFacesExcept((direction, builder) -> builder.uvs(0, 0, 16, 16).texture("#dark_side").end(), Set.of(Direction.UP, Direction.DOWN))
                .face(Direction.DOWN).uvs(0, 0, 16, 16).texture("#dark_top").end().end()
                // North-West foot
                .element().from(12, 0, 2)
                .to(14, 3, 4)
                .allFacesExcept((direction, builder) -> builder.uvs(0, 0, 16, 16).texture("#dark_side").end(), Set.of(Direction.UP, Direction.DOWN))
                .face(Direction.DOWN).uvs(0, 0, 16, 16).texture("#dark_top").end().end()
                // Body
                .element().from(2, 3, 2)
                .to(14, 14, 14)
                .face(Direction.UP).uvs(0, 0, 16, 16).texture("#top").end()
                .face(Direction.DOWN).uvs(0, 0, 16, 16).texture("#top").end()
                .allFacesExcept((direction, builder) -> builder.uvs(0, 0, 16, 16).texture("#side").end(), Set.of(Direction.UP, Direction.DOWN)).end()
                // Desk-Plate
                .element().from(0, 14, 0)
                .to(16, 16, 16)
                .face(Direction.UP).uvs(0, 0, 16, 16).texture("#desk").end()
                .face(Direction.DOWN).uvs(0, 0, 16, 16).texture("#under").end()
                .allFacesExcept((side, builder) -> builder.uvs(0, 0, 16, 1).texture("#under"), Set.of(Direction.UP, Direction.DOWN)).end()
                // Upper Drawer
                .element().from(4, 10, 14)
                .to(12, 14, 15)
                .face(Direction.SOUTH).uvs(0, 0, 8, 4).texture("#dark_side").end()
                .face(Direction.UP).uvs(0, 0, 8, 1).texture("#bark").end()
                .face(Direction.DOWN).uvs(0, 1, 8, 0).texture("#bark").end()
                .face(Direction.WEST).uvs(0, 0, 1, 4).texture("#bark").end()
                .face(Direction.EAST).uvs(1, 0, 0, 4).texture("#bark").end().end()
                // Lower Drawer
                .element().from(4, 4, 14)
                .to(12, 8, 15)
                .face(Direction.SOUTH).uvs(0, 0, 8, 4).texture("#dark_side").end()
                .face(Direction.UP).uvs(0, 0, 8, 1).texture("#bark").end()
                .face(Direction.DOWN).uvs(8, 0, 0, 1).texture("#bark").end()
                .face(Direction.WEST).uvs(0, 0, 1, 4).texture("#bark").end()
                .face(Direction.EAST).uvs(1, 0, 0, 4).texture("#bark").end().end()
                // Upper Drawer Knob
                .element().from(7, 11, 15)
                .to(9, 13, 16)
                .face(Direction.SOUTH).uvs(0, 0, 2, 2).texture("#side").end()
                .face(Direction.UP).uvs(0, 0, 2, 1).texture("#side").end()
                .face(Direction.DOWN).uvs(2, 0, 0, 1).texture("#side").end()
                .face(Direction.WEST).uvs(0, 0, 1, 2).texture("#side").end()
                .face(Direction.EAST).uvs(1, 0, 0, 2).texture("#side").end().end()
                // Lower Drawer Knob
                .element().from(7, 5, 15)
                .to(9, 7, 16)
                .face(Direction.SOUTH).uvs(0, 0, 2, 2).texture("#side").end()
                .face(Direction.UP).uvs(0, 0, 2, 1).texture("#side").end()
                .face(Direction.DOWN).uvs(2, 0, 0, 1).texture("#side").end()
                .face(Direction.WEST).uvs(0, 0, 1, 2).texture("#side").end()
                .face(Direction.EAST).uvs(1, 0, 0, 2).texture("#side").end().end();

        VariantBlockStateBuilder builder = getVariantBuilder(BMBlocks.LIVING_STATION.block().get());
        for (Direction facing : Direction.Plane.HORIZONTAL) {
            builder.partialState().with(ARCBlock.FACING, facing).modelForState().modelFile(model).rotationY((int) facing.toYRot()).addModel();
        }
    }

    private static String name(BlockWithItemHolder<? extends Block, ? extends BlockItem> block) {
        return block.block().getId().toString();
    }

    private static ResourceLocation bm(String path) {
        return ResourceLocation.fromNamespaceAndPath(BloodMagic.MODID, path);
    }
}
