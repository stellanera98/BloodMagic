package wayoftime.bloodmagic.common.datamap;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.blockentity.InfuserTile;
import wayoftime.bloodmagic.common.datacomponent.EnumWillType;

import java.util.List;
import java.util.Map;

public class BMDataMaps {
    public static final DataMapType<Item, Double> TARTARIC_GEM_MAX_AMOUNTS = DataMapType.builder(
            BloodMagic.rl("tartaric_gem_max"),
            Registries.ITEM,
            Codec.DOUBLE
    ).synced(Codec.DOUBLE, true).build();

    public static final DataMapType<Item, BloodOrb> BLOOD_ORB_STATS = DataMapType.builder(
            BloodMagic.rl("blood_orb_stats"),
            Registries.ITEM,
            BloodOrb.CODEC
    ).synced(BloodOrb.CODEC, true).build();


    public static final DataMapType<Block, List<BloodRune>> BLOOD_RUNES = DataMapType.builder(
            BloodMagic.rl("blood_runes"),
            Registries.BLOCK,
            BloodRune.CODEC.listOf()
    ).synced(BloodRune.CODEC.listOf(), true).build();

    public static final DataMapType<Item, LivingArmorData> LIVING_ARMOUR_DATA = DataMapType.builder(
            BloodMagic.rl("armour_data"),
            Registries.ITEM,
            LivingArmorData.CODEC
    ).synced(LivingArmorData.CODEC, true).build();

    public static final DataMapType<Block, ResourceLocation> IMPERFECT_RITUAL_CATALYST = DataMapType.builder(
            BloodMagic.rl("imperfect_ritual_catalysts"),
            Registries.BLOCK,
            ResourceLocation.CODEC
    ).synced(ResourceLocation.CODEC, true).build();

    public static final DataMapType<Item, WillStack> DEMON_CRUCIBLE = DataMapType.builder(
            BloodMagic.rl("demon_crucible"),
            Registries.ITEM,
            WillStack.CODEC
    ).synced(WillStack.CODEC, true).build();

    private static final Codec<Pair<WillStack, Block>> WILL_INFUSION_CODEC =
            Codec.pair(WillStack.CODEC.fieldOf("will").codec(), BuiltInRegistries.BLOCK.byNameCodec().fieldOf("result").codec());
    public static final DataMapType<Block, Pair<WillStack, Block>> WILL_INFUSION = DataMapType.builder(
            BloodMagic.rl("will_infusion"),
            Registries.BLOCK,
            WILL_INFUSION_CODEC
    ).synced(WILL_INFUSION_CODEC, true).build();

    private static final Codec<Map<EnumWillType, SentientStats>> SENTIENT_STATS_CODEC = Codec.unboundedMap(EnumWillType.CODEC, SentientStats.CODEC);
    public static final DataMapType<Item, Map<EnumWillType, SentientStats>> SENTIENT_STATS = DataMapType.builder(
            BloodMagic.rl("sentient_stats"),
            Registries.ITEM,
            SENTIENT_STATS_CODEC
    ).build();

    public static void register(RegisterDataMapTypesEvent event) {
        event.register(TARTARIC_GEM_MAX_AMOUNTS);
        event.register(BLOOD_ORB_STATS);
        event.register(BLOOD_RUNES);
        event.register(LIVING_ARMOUR_DATA);

        event.register(IMPERFECT_RITUAL_CATALYST);
        event.register(DEMON_CRUCIBLE);
        event.register(WILL_INFUSION);
    }
}
