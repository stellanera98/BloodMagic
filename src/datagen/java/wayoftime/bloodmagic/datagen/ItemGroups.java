package wayoftime.bloodmagic.datagen;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import wayoftime.bloodmagic.common.item.BMItems;

import java.util.List;

public class ItemGroups {
    public static List<ResourceKey<Item>> SOUL_GEMS = List.of(
            BMItems.SOUL_GEM_PETTY.getKey(), BMItems.SOUL_GEM_LESSER.getKey(), BMItems.SOUL_GEM_COMMON.getKey(),
            BMItems.SOUL_GEM_GREATER.getKey(), BMItems.SOUL_GEM_GRAND.getKey()
    );
}
