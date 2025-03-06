package wayoftime.bloodmagic.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.datamap.BMDataMaps;
import wayoftime.bloodmagic.common.datamap.BloodRune;
import wayoftime.bloodmagic.common.registry.AltarComponent;
import wayoftime.bloodmagic.common.registry.AltarTier;
import wayoftime.bloodmagic.common.registry.BMRegistries;
import wayoftime.bloodmagic.common.tag.BMTags;

import java.util.*;

public class AltarUtil {
    public static List<List<AltarComponent>> TIERS = null;

    public static int getTier(Level level, BlockPos altarPos) {
        if (TIERS == null) {
            buildTiers(level.registryAccess());
        }

        int lastTier = 0;
        outer:
        for (int i = 0; i < TIERS.size(); i++) {
            for (AltarComponent component : TIERS.get(i)) {
                BlockPos checkPos = altarPos.offset(component.pos());
                if (!check(level, checkPos, component.material())) {
                    BloodMagic.LOGGER.info("{} failed the check for {}", checkPos, component.material());
                    break outer;
                }
            }
            lastTier = i;
        }

        return lastTier;
    }

    public static Map<RuneType, Integer> getUpgrades(int tier, Level level, BlockPos altarPos) {
        if (TIERS == null) {
            buildTiers(level.registryAccess());
        }

        Map<RuneType, Integer> upgrades = new HashMap<>();
        for (AltarComponent component : TIERS.get(tier)) {
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

    public static void onDataPackLoaded(OnDatapackSyncEvent event) {
        TIERS = null;
    }

    public static void buildTiers(RegistryAccess registry) {
        Registry<AltarTier> tierRegistry = registry.registryOrThrow(BMRegistries.ALTAR_TIER_KEY);
        List<Holder<AltarTier>> tierList = tierRegistry.getOrCreateTag(BMTags.Tiers.VALID_TIERS).stream().toList();
        TIERS = new ArrayList<>();
        for (int i = 0; i < tierList.size(); i++) {
            TIERS.add(null);
        }
        for (Holder<AltarTier> tier : tierList) {
            AltarTier tmp = tier.value();
            BloodMagic.LOGGER.info("Tier {} has {} components", tmp.tier(), tmp.components().size());
            TIERS.set(tmp.tier(), tmp.components());
        }
    }

    public static void onServerShutdown(ServerStoppedEvent event) {
        TIERS = null;
    }
}
