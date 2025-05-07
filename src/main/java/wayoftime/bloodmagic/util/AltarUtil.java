package wayoftime.bloodmagic.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.PatchouliAPI;
import wayoftime.bloodmagic.common.block.BMBlocks;
import wayoftime.bloodmagic.common.datamap.BMDataMaps;
import wayoftime.bloodmagic.common.datamap.BloodRune;
import wayoftime.bloodmagic.common.registry.AltarComponent;
import wayoftime.bloodmagic.common.registry.BMRegistries;
import wayoftime.bloodmagic.common.structure.BMMultiblock;

import java.util.*;

public class AltarUtil {
    public static BlockPos findAltar(Level level, BlockPos pos, int radius) {
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos testPos = pos.offset(x, y, z);
                    BlockState testState = level.getBlockState(testPos);
                    if (testState.is(BMBlocks.BLOOD_ALTAR.block())) {
                        return testPos;
                    }
                }
            }
        }
        return null;
    }

    public static DamageSource sacrificeDamage(Player causer) {
        return new DamageSource(causer.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(BMRegistries.Keys.SACRIFICE_DAMAGE_KEY), null, causer, causer.position());
    }

    public static int getTier(Level level, BlockPos altarPos) {
        int tier = -1;
        PatchouliAPI.IPatchouliAPI api = PatchouliAPI.get();
        for (int i = 0; i < BMMultiblock.TIER_KEYS.length; i++) {
            IMultiblock toCheck = api.getMultiblock(BMMultiblock.TIER_KEYS[i]);
            Rotation rot = toCheck.validate(level, altarPos.above());
            if (!(rot == null)) {
                tier = i;
            }
        }
        return tier;
    }

    public static Map<RuneType, Integer> getUpgrades(int tier, Level level, BlockPos altarPos) {
        Map<RuneType, Integer> upgrades = new HashMap<>();
        for (AltarComponent component : BMMultiblock.TIER_LIST[tier].components()) {
            if (component.isUpgrade()) {
                List<BloodRune> runes = level.getBlockState(altarPos.offset(component.pos())).getBlockHolder().getData(BMDataMaps.BLOOD_RUNES);
                if (runes != null) {
                    runes.forEach(rune -> upgrades.merge(rune.type(), rune.amount(), Integer::sum));
                }
            }
        }

        return upgrades;
    }
}
