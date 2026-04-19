package wayoftime.bloodmagic.datagen.content.datamap;

import net.minecraft.world.item.Item;
import wayoftime.bloodmagic.common.datamap.BMDataMaps;
import wayoftime.bloodmagic.common.datamap.LivingArmorData;
import wayoftime.bloodmagic.common.item.BMItems;
import wayoftime.bloodmagic.api.BMTags;
import wayoftime.bloodmagic.datagen.provider.DataMapBuilder;

public class LivingData {
    public static void bootstrap(DataMapBuilder<Item, LivingArmorData> setup) {
        setup.apply(BMDataMaps.LIVING_ARMOUR_DATA)
                .add(BMItems.LIVING_PLATE, new LivingArmorData(BMTags.Items.LIVING_SET, BMTags.Living.LIVING_START, BMTags.Living.LIVING_BLACKLIST), false);
    }
}
