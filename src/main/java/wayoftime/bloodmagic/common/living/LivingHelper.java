package wayoftime.bloodmagic.common.living;

import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableFloat;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.common.datacomponent.LivingStats;
import wayoftime.bloodmagic.common.registry.BMRegistries;
import wayoftime.bloodmagic.common.tag.BMTags;
import wayoftime.bloodmagic.util.ChatUtil;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class LivingHelper {
    public static boolean hasFullSet(Player player) {
        ItemStack chestStack = getChest(player);
        TagKey<Item> set = chestStack.get(BMDataComponents.REQUIRED_SET);
        if (set == null) {
            return false;
        }
        if (chestStack.getDamageValue() +1 >= chestStack.getMaxDamage()) {
            return false;
        }

        for (ItemStack stack : player.getArmorSlots()) {
            if (!stack.is(set)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isNeverValid(Player player) {
        return isNeverValid(getChest(player));
    }

    public static boolean isNeverValid(ItemStack plate) {
        return !plate.has(BMDataComponents.REQUIRED_SET);
    }

    public static ItemStack getChest(Player player) {
        return player.getItemBySlot(EquipmentSlot.CHEST);
    }

    public static boolean has(ItemStack stack, DataComponentType<?> type) {
        MutableBoolean found = new MutableBoolean(false);
        runIterationOnItem(stack, (p_344620_, p_344621_) -> {
            if (p_344620_.value().effects().has(type)) {
                found.setTrue();
            }
        });
        return found.booleanValue();
    }

    public static boolean has(Player player, DataComponentType<?> type) {
        return has(getChest(player), type);
    }

    public static void runIterationOnPlayer(Player player, BiConsumer<Holder<LivingUpgrade>, Integer> visitor) {
        runIterationOnItem(getChest(player), visitor);
    }

    public static final Object2FloatOpenHashMap<Holder<LivingUpgrade>> EMPTY_UPGRADE_MAP = new Object2FloatOpenHashMap<>();
    public static void runIterationOnItem(ItemStack stack, BiConsumer<Holder<LivingUpgrade>, Integer> visitor) {
        Object2FloatOpenHashMap<Holder<LivingUpgrade>> upgrades = stack.getOrDefault(BMDataComponents.UPGRADES, LivingStats.EMPTY).upgrades();

        for (Object2FloatMap.Entry<Holder<LivingUpgrade>> entry : upgrades.object2FloatEntrySet()) {
            int level = getLevelFromXp(entry.getKey(), entry.getFloatValue());
            if (level < 1) {
                continue;
            }
            visitor.accept(entry.getKey(), level);
        }
    }

    private static int getLevelFromXp(Holder<LivingUpgrade> upgrade, float exp) {
        Map.Entry<Integer, Integer> level = upgrade.value().levels().expToLevel().floorEntry((int) exp);
        return level == null ? 0 : level.getValue();
    }

    private static int nextLevelExp(Holder<LivingUpgrade> upgrade, float exp) {
        Map.Entry<Integer, Integer> level = upgrade.value().levels().expToLevel().ceilingEntry((int) exp + 1); // otherwise it'll get the same level again
        return level == null ? 0 : level.getKey();
    }

    public static float modifyKnockback(Player player, LivingEntity victim, DamageSource damageSource, float knockback) {
        MutableFloat mutablefloat = new MutableFloat(knockback);
        runIterationOnPlayer(player, (holder, level) -> holder.value().modifyKnockback(level, victim, damageSource, mutablefloat));
        return mutablefloat.floatValue();
    }

    public static int modifyExperience(Player player, int startingValue) {
        MutableFloat value = new MutableFloat(startingValue);
        runIterationOnPlayer(player, (holder, level) -> holder.value().modifyExperience(level, player, value));
        float xp = value.floatValue();
        return xp + player.level().random.nextFloat() < xp % 1 ? 1 : 0;
    }

    public static float modifyHealing(Player player, float amount) {
        MutableFloat value = new MutableFloat(amount);
        runIterationOnPlayer(player, (holder, level) -> holder.value().modifyHealing(level, player, value));
        return value.getValue();
    }

    public static float modifyDamageDealt(Player playerCauser, LivingEntity victim, DamageSource source, float originalDamage) {
        MutableFloat value = new MutableFloat(originalDamage);
        runIterationOnPlayer(playerCauser, (holder, level) -> holder.value().modifyDamageDealt(level, victim, source, value));
        return value.getValue();
    }

    public static float modifyDamageTaken(Player playerVictim, DamageSource source, float newDamage) {
        MutableFloat value = new MutableFloat(newDamage);
        runIterationOnPlayer(playerVictim, (holder, level) -> holder.value().modifyDamageTaken(level, playerVictim, source, value));
        return value.getValue();
    }

    public static void reactToDamageDealt(Player playerCauser, LivingEntity victim, DamageSource source, float newDamage) {
        runIterationOnPlayer(playerCauser, (holder, level) -> holder.value().reactToDamageDealt(level, victim, source, newDamage));
    }

    public static void reactToDamageTaken(Player playerVictim, DamageSource source, float newDamage) {
        runIterationOnPlayer(playerVictim, (holder, level) -> holder.value().reactToDamageTaken(level, playerVictim, source, newDamage));
    }

    public static void runBlockBroken(Player player, BlockState state) {
        runIterationOnPlayer(player, (holder, level) -> holder.value().blockBroken(level, player, state));
    }

    public static void runTick(Player player) {
        runIterationOnPlayer(player, (holder, level) -> holder.value().tick(level, player));
    }

    public static void runProjectile(Player player, Projectile projectile) {
        runIterationOnPlayer(player, (holder, level) -> holder.value().modifyProjectile(level, player, projectile));
    }

    public static void removeAttributes(ItemStack chestStack) {
        runIterationOnItem(chestStack, (holder, level) -> holder.value().removeAttribute(chestStack));
    }

    public static void removeAttributes(Player player) {
        removeAttributes(LivingHelper.getChest(player));
    }

    public static void addAttributes(Player player) {
        ItemStack chestStack = LivingHelper.getChest(player);
        runIterationOnItem(chestStack, (holder, level) -> holder.value().addAttribute(level, chestStack));
    }

    public static float applyExp(Player wearer, Holder<LivingUpgrade> upgrade, float amount) {

        return 0;
    }

    public static Component getTooltip(Holder<LivingUpgrade> upgrade, float exp, boolean hasShiftDown) {
        MutableComponent mutable = Component.translatable(Util.makeDescriptionId("living_upgrade", upgrade.getKey().location()));
        if (upgrade.is(BMTags.Living.IS_DOWNGRADE)) {
            ComponentUtils.mergeStyles(mutable, Style.EMPTY.withColor(ChatFormatting.RED));
        } else {
            ComponentUtils.mergeStyles(mutable, Style.EMPTY.withColor(ChatFormatting.GRAY));
        }

        if (hasShiftDown) {
            mutable.append(CommonComponents.SPACE).append(Component.literal("%s/%s".formatted((int) exp, nextLevelExp(upgrade, exp))));
        } else {
            mutable.append(CommonComponents.SPACE).append(Component.literal(ChatUtil.toRoman(getLevelFromXp(upgrade, exp))));
        }

        return mutable;
    }

    public static Object2FloatOpenHashMap<Holder<LivingUpgrade>> fromHolderSet(HolderSet<LivingUpgrade> template) {
        return fromHolderSet(template, 0);
    }

    public static Object2FloatOpenHashMap<Holder<LivingUpgrade>> fromHolderSet(HolderSet<LivingUpgrade> template, float val) {
        Object2FloatOpenHashMap<Holder<LivingUpgrade>> ret = new Object2FloatOpenHashMap<>();
        template.forEach(holder -> ret.put(holder, val));
        return ret;
    }

    public static void setDefaultLiving(ItemStack livingPlate, HolderLookup.Provider holders) {
        HolderSet<LivingUpgrade> set = holders.lookupOrThrow(BMRegistries.Keys.LIVING_UPGRADES).get(BMTags.Living.LIVING_START).orElseThrow();
        livingPlate.set(BMDataComponents.UPGRADES, new LivingStats(fromHolderSet(set)));
        livingPlate.set(BMDataComponents.CURRENT_MAX_UPGRADE_POINTS, BloodMagic.SERVER_CONFIG.DEFAULT_UPGRADE_POINTS.get());
    }
}
