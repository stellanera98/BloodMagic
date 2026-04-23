package wayoftime.bloodmagic.datagen.provider;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.api.BMIdentifiers;
import wayoftime.bloodmagic.api.BMIdentifiers.Sigils;
import wayoftime.bloodmagic.api.sigil.SigilEffect;
import wayoftime.bloodmagic.common.block.BMBlocks;
import wayoftime.bloodmagic.common.datacomponent.EnumWillType;
import wayoftime.bloodmagic.common.item.BMItems;
import wayoftime.bloodmagic.util.blockitem.BlockWithItemHolder;

import java.util.function.Supplier;

public class BMItemModelProvider extends ItemModelProvider {

    private final ResourceLocation handheld = mcLoc("item/handheld");
    private final ResourceLocation generated = mcLoc("item/generated");

    public BMItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BloodMagic.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        BMItems.BASIC_ITEMS.getEntries().stream().map(Supplier::get).forEach(this::basicItem);
        basicItem(BMItems.LIVING_PLATE.get());
        basicItem(BMItems.UPGRADE_TOME.get());
        BMItems.WILL_ITEMS.getEntries().forEach(item -> {
            String path = item.getId().getPath();
            ItemModelBuilder builder = getBuilder(path);
            for (EnumWillType type : EnumWillType.types()) {
                ModelFile modelFile = singleTexture(String.format("item/variant/%s_%s", path, type.getSerializedName()), handheld, "layer0", modLoc(String.format("item/%s_%s", path, type.getSerializedName())));
                builder.override().predicate(BMIdentifiers.ItemProperties.TYPE_PROPERTY, type.ordinal()).model(modelFile).end();
            }
        });

        ItemModelBuilder builder = getBuilder(BMItems.SACRIFICIAL_DAGGER.getId().getPath());
        ModelFile normalDagger = singleTexture("item/variant/sacrificial_dagger_normal", handheld, "layer0", modLoc("item/sacrificial_dagger"));
        ModelFile chargedDagger = singleTexture("item/variant/sacrificial_dagger_charged", handheld, "layer0", modLoc("item/sacrificial_dagger_charged"));
        builder.override().predicate(BMIdentifiers.ItemProperties.INCENSE_PROPERTY, 0).model(normalDagger).end();
        builder.override().predicate(BMIdentifiers.ItemProperties.INCENSE_PROPERTY, 1).model(chargedDagger).end();

        // TODO createWand();
        bud(BMBlocks.WILL_BUD_SMALL_RAW);
        bud(BMBlocks.WILL_BUD_MEDIUM_RAW);
        bud(BMBlocks.WILL_BUD_LARGE_RAW);
        bud(BMBlocks.WILL_CLUSTER_RAW);

        bud(BMBlocks.WILL_BUD_SMALL_CORROSIVE);
        bud(BMBlocks.WILL_BUD_MEDIUM_CORROSIVE);
        bud(BMBlocks.WILL_BUD_LARGE_CORROSIVE);
        bud(BMBlocks.WILL_CLUSTER_CORROSIVE);

        bud(BMBlocks.WILL_BUD_SMALL_DESTRUCTIVE);
        bud(BMBlocks.WILL_BUD_MEDIUM_DESTRUCTIVE);
        bud(BMBlocks.WILL_BUD_LARGE_DESTRUCTIVE);
        bud(BMBlocks.WILL_CLUSTER_DESTRUCTIVE);

        bud(BMBlocks.WILL_BUD_SMALL_STEADFAST);
        bud(BMBlocks.WILL_BUD_MEDIUM_STEADFAST);
        bud(BMBlocks.WILL_BUD_LARGE_STEADFAST);
        bud(BMBlocks.WILL_CLUSTER_STEADFAST);

        bud(BMBlocks.WILL_BUD_SMALL_VENGEFUL);
        bud(BMBlocks.WILL_BUD_MEDIUM_VENGEFUL);
        bud(BMBlocks.WILL_BUD_LARGE_VENGEFUL);
        bud(BMBlocks.WILL_CLUSTER_VENGEFUL);

        createSigilModels();
    }

    private void bud(BlockWithItemHolder<? extends Block, ? extends BlockItem> item) {
        singleTexture(item.item().getId().toString(), generated, "layer0", item.block().getId().withPrefix("block/")).renderType("cutout");
    }

    private void createWand() {
        ItemModelBuilder builder = getBuilder(BMItems.WILL_ROUTING_WAND.getId().getPath());

        builder.parent(new ModelFile.UncheckedModelFile(handheld))
                .texture("layer0", modLoc("item/wand_all"));

        for (EnumWillType type : EnumWillType.types()) {
            builder.override().predicate(BMIdentifiers.ItemProperties.WILL_CONFIGURATION, type.ordinal() + 1)
                    .model(singleTexture("item/variant/wand_" + type.getSerializedName(), handheld, modLoc("item/wand_" + type.getSerializedName())))
                    .end();
        }
    }

    private void createSigilModels() {
        createStandard(Sigils.DIVINATION);
        createStandard(Sigils.SEER);
        createStandard(Sigils.LAVA);
        createStandard(Sigils.WATER);
        createStandard(Sigils.VOID);
        createToggle(Sigils.MINER);
    }

    private void createStandard(ResourceKey<SigilEffect> key) {
        String path = key.location().getPath();
        getBuilder("sigil_" + path)
                .parent(new ModelFile.UncheckedModelFile(handheld))
                .texture("layer0", modLoc("item/sigil_" + path));
    }

    private void createToggle(ResourceKey<SigilEffect> key) {
        String path = key.location().getPath();
        getBuilder("sigil_" + path)
                .parent(new ModelFile.UncheckedModelFile(handheld))
                .texture("layer0", modLoc("item/sigil_" + path + "_deactivated"))
                .override().predicate(BMIdentifiers.ItemProperties.SIGIL_ACTIVE, 1)
                .model(singleTexture("item/sigil_" + path + "_activated", mcLoc("item/handheld"), "layer0", modLoc("item/sigil_" + path + "_activated"))).end();
    }
}
