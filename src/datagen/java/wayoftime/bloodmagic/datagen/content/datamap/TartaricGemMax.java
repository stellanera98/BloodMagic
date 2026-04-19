package wayoftime.bloodmagic.datagen.content.datamap;

import net.minecraft.world.item.Item;
import wayoftime.bloodmagic.common.datamap.BMDataMaps;
import wayoftime.bloodmagic.common.item.BMItems;
import wayoftime.bloodmagic.datagen.provider.DataMapBuilder;

public class TartaricGemMax {
    public static void bootstrap(DataMapBuilder<Item, Double> setup) {
        setup.apply(BMDataMaps.TARTARIC_GEM_MAX_AMOUNTS)
                .add(BMItems.SOUL_GEM_PETTY.getKey(), 64D, false)
                .add(BMItems.SOUL_GEM_LESSER.getKey(), 256D, false)
                .add(BMItems.SOUL_GEM_COMMON.getKey(), 1024D, false)
                .add(BMItems.SOUL_GEM_GREATER.getKey(), 4096D, false)
                .add(BMItems.SOUL_GEM_GRAND.getKey(), 16384D, false)
                .build();
    }
}
