package wayoftime.bloodmagic.datagen.content.datamap;

import net.minecraft.world.item.Item;
import wayoftime.bloodmagic.common.datamap.BMDataMaps;
import wayoftime.bloodmagic.common.datamap.BloodOrb;
import wayoftime.bloodmagic.common.item.BMItems;
import wayoftime.bloodmagic.datagen.provider.DataMapBuilder;

public class BloodOrbStats {
        public static void bootstrap(DataMapBuilder<Item, BloodOrb> setup) {
                setup.apply(BMDataMaps.BLOOD_ORB_STATS)
                        .add(BMItems.ORB_WEAK, new BloodOrb(0, 5_000, 2), false)
                        .add(BMItems.ORB_APPRENTICE, new BloodOrb(1, 25_000, 5), false)
                        .add(BMItems.ORB_MAGICIAN, new BloodOrb(2, 150_000, 15), false)
                        .add(BMItems.ORB_MASTER, new BloodOrb(3, 1_000_000, 25), false)
                        .add(BMItems.ORB_ARCHMAGE, new BloodOrb(4, 5_000_000, 50), false)
                        .add(BMItems.ORB_TRANSCENDENT, new BloodOrb(5, 10_000_000, 50), false);
        }
}