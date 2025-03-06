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

    private static void registerDataMapTypes(RegisterDataMapTypesEvent event) {
        event.register(BLOOD_RUNES);
        event.register(BLOOD_ORBS);
    }

    public static void register(IEventBus modBus) {
        modBus.addListener(BMDataMaps::registerDataMapTypes);
    }
}
