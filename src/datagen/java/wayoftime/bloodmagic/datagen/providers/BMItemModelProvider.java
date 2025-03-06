package wayoftime.bloodmagic.datagen.providers;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.item.BMItems;

import java.util.function.Supplier;

public class BMItemModelProvider extends ItemModelProvider {
    public BMItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BloodMagic.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        BMItems.BASICITEMS.getEntries().stream().map(Supplier::get).forEach(this::basicItem);
    }
}
