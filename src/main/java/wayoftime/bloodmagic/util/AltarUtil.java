package wayoftime.bloodmagic.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.datamap.BMDataMaps;
import wayoftime.bloodmagic.common.datamap.BloodRune;
import wayoftime.bloodmagic.common.registry.AltarComponent;
import wayoftime.bloodmagic.common.registry.AltarTier;
import wayoftime.bloodmagic.common.registry.BMRegistries;
import wayoftime.bloodmagic.common.tag.BMTags;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EventBusSubscriber
public class AltarUtil {
    public static List<AltarComponent>[] TIERS;

    public static int getTier(Level level, BlockPos altarPos) {
        int lastTier = 0;
        outer:
        for (int i = 0; i < TIERS.length; i++) {
            for (AltarComponent component : TIERS[i]) {
                BlockPos checkPos = altarPos.offset(component.pos());
                if (!check(level, checkPos, component.material())) {
                    BloodMagic.LOGGER.debug("{} failed the check for {}", checkPos, component.material());
                    break outer;
                }
            }
            lastTier = i;
        }

        return lastTier;
    }

    public static Map<RuneType, Integer> getUpgrades(int tier, Level level, BlockPos altarPos) {
        Map<RuneType, Integer> upgrades = new HashMap<>();
        for (AltarComponent component : TIERS[tier]) {
            if (component.isUpgrade()) {
                List<BloodRune> runes = level.getBlockState(altarPos.offset(component.pos())).getBlockHolder().getData(BMDataMaps.BLOOD_RUNES);
                if (runes != null) {
                    runes.forEach(rune -> upgrades.merge(rune.type(), rune.amount(), Integer::sum));
                }
            }
        }

        return upgrades;
    }

    private static boolean check(Level level, BlockPos checkPos, ExtraCodecs.TagOrElementLocation material) {
        BlockState checkState = level.getBlockState(checkPos);
        if (material.tag()) {
            TagKey<Block> tag = TagKey.create(Registries.BLOCK, material.id());
            if (tag.equals(BMTags.Blocks.PILLAR)) {
                if (level.registryAccess().lookupOrThrow(Registries.BLOCK).getOrThrow(tag).stream().findAny().isEmpty()) {
                    return checkState.isSolid();
                }
            }
            return checkState.is(tag);
        } else {
            return checkState.is(ResourceKey.create(Registries.BLOCK, material.id()));
        }
    }

    @SubscribeEvent
    public static void onDataPackLoaded(OnDatapackSyncEvent event) {
        Registry<AltarTier> tierRegistry = event.getPlayer().registryAccess().registryOrThrow(BMRegistries.ALTAR_TIER_KEY);
        List<Holder<AltarTier>> tierList = tierRegistry.getOrCreateTag(BMTags.Tiers.VALID_TIERS).stream().toList();
        TIERS = new List[tierList.size()];
        for (Holder<AltarTier> tier : tierList) {
            AltarTier tmp = tier.value();
            BloodMagic.LOGGER.debug("Tier {} has {} components", tmp.tier(), tmp.components().size());
            TIERS[tmp.tier()] = tmp.components();
        }
    }
}
