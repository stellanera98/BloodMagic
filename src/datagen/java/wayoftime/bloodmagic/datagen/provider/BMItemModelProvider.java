package wayoftime.bloodmagic.datagen.provider;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.api.BMIdentifiers;
import wayoftime.bloodmagic.common.datacomponent.EnumWillType;
import wayoftime.bloodmagic.common.item.BMItems;

import java.util.function.Supplier;

public class BMItemModelProvider extends ItemModelProvider {
    public BMItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BloodMagic.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        BMItems.BASIC_ITEMS.getEntries().stream().map(Supplier::get).forEach(this::basicItem);
        BMItems.TAB_REQ.getEntries().stream().map(Supplier::get).forEach(this::basicItem);
        BMItems.SIGILS.getEntries().stream().map(Supplier::get).forEach(this::basicItem);
        BMItems.SIGILS_TOGGLEABLE.getEntries().forEach(item -> {
            String path = item.getId().getPath();
            String pathActivated = "item/variant/" + path + "_activated";
            ItemModelBuilder builder = getBuilder(path);
            ModelFile activated = singleTexture(pathActivated, mcLoc("item/handheld"), "layer0", modLoc("item/" + path + "_activated"));
            builder.parent(new ModelFile.UncheckedModelFile("item/handheld")).texture("layer0", modLoc("item/" + path + "_deactivated"));
            builder.override().predicate(BMIdentifiers.ItemProperties.IS_SIGIL_ACTIVE, 1).model(activated).end();
        });

        BMItems.WILL_ITEMS.getEntries().forEach(item -> {
            String path = item.getId().getPath();
            ItemModelBuilder builder = getBuilder(path);
            for (EnumWillType type : EnumWillType.values()) {
                ModelFile modelFile = singleTexture(String.format("item/variant/%s_%s", path, type.getSerializedName()), mcLoc("item/handheld"), "layer0", modLoc(String.format("item/%s_%s", path, type.getSerializedName())));
                builder.override().predicate(BMIdentifiers.ItemProperties.DEMON_WILL_TYPE, type.ordinal()).model(modelFile).end();
            }
        });

        ItemModelBuilder builder = getBuilder(BMItems.SACRIFICIAL_DAGGER.getId().getPath());
        ModelFile chargedDagger = singleTexture("item/variant/sacrificial_dagger_charged", mcLoc("item/handheld"), "layer0", modLoc("item/sacrificial_dagger_charged"));
        builder.parent(new ModelFile.UncheckedModelFile("item/handheld")).texture("layer0", modLoc("item/sacrificial_dagger"));
        builder.override().predicate(BMIdentifiers.ItemProperties.HAS_INCENSE, 1).model(chargedDagger).end();
    }
}
