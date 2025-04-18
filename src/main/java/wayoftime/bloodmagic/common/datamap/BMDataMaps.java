package wayoftime.bloodmagic.common.datamap;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;
import wayoftime.bloodmagic.BloodMagic;

import java.util.List;

public class BMDataMaps {
    public static final DataMapType<Block, List<BloodRune>> BLOOD_RUNES = DataMapType.builder(
            ResourceLocation.fromNamespaceAndPath(BloodMagic.MODID, "blood_rune"),
            Registries.BLOCK,
            Codec.list(BloodRune.CODEC)
    ).build();

    public static final DataMapType<Item, BloodOrb> BLOOD_ORBS = DataMapType.builder(
            ResourceLocation.fromNamespaceAndPath(BloodMagic.MODID, "blood_orb"),
            Registries.ITEM,
            BloodOrb.CODEC
    ).build();

    public static final DataMapType<Item, Double> SOUL_GEM_AMOUNTS = DataMapType.builder(
            ResourceLocation.fromNamespaceAndPath(BloodMagic.MODID, "soul_gem_amounts"),
            Registries.ITEM,
            Codec.DOUBLE
    ).synced(Codec.DOUBLE, true).build();

    private static void registerDataMapTypes(RegisterDataMapTypesEvent event) {
        event.register(BLOOD_RUNES);
        event.register(BLOOD_ORBS);
        event.register(SOUL_GEM_AMOUNTS);
    }

    public static void register(IEventBus modBus) {
        modBus.addListener(BMDataMaps::registerDataMapTypes);
    }
}
