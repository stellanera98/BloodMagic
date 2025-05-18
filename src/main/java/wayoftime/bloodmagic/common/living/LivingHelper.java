package wayoftime.bloodmagic.common.living;

import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.mutable.MutableBoolean;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.common.datacomponent.LivingStats;
import wayoftime.bloodmagic.common.item.LivingArmourItem;
import wayoftime.bloodmagic.common.tag.BMTags;

import java.util.List;
import java.util.function.BiConsumer;

public class LivingHelper {
    public static boolean hasFullSet(Player player) {
        ItemStack chestStack = getChest(player);
        TagKey<Item> set = chestStack.get(BMDataComponents.REQUIRED_SET);
        if (set == null) {
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
        return !getChest(player).has(BMDataComponents.REQUIRED_SET);
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

    public static void runIterationOnItem(ItemStack stack, BiConsumer<Holder<LivingUpgrade>, Integer> visitor) {
        LivingStats upgrades = stack.getOrDefault(BMDataComponents.LIVING_UPGRADES, LivingStats.EMPTY);

        for (Object2FloatMap.Entry<Holder<LivingUpgrade>> entry : upgrades.entrySet()) {
            int level = getLevelFromXp(entry.getKey(), entry.getFloatValue());
            visitor.accept(entry.getKey(), level);
        }
    }

    private static int getLevelFromXp(Holder<LivingUpgrade> upgrade, float exp) {
        int level = 0;
        for (LivingUpgrade.Level upgradeLevel : upgrade.value().levels()) {
            if (upgradeLevel.xpNeeded() >= exp) {
                level++;
            }
        }

        return level;
    }

    private static int nextLevelExp(Holder<LivingUpgrade> upgrade, float exp) {
        int level = getLevelFromXp(upgrade, exp);
        List<LivingUpgrade.Level> levels = upgrade.value().levels();
        if (level + 1 >= levels.size()) {
            return levels.getLast().xpNeeded();
        }

        return levels.get(level + 1).xpNeeded();
    }

    // TODO all these are to be implemented
    public static float modifyKnockback(Level level, Player player, DamageSource lastDamageSource, float originalStrength) {
        return originalStrength;
    }

    public static int modifyExperience(Level level, Player entity, int value) {
        return value;
    }

    public static float modifyHealing(Level level, Player player, float amount) {
        return amount;
    }

    public static float modifyDamageDealt(Player playerCauser, Entity victim, DamageSource source, float originalDamage) {
        return 0;
    }

    public static float modifyDamageTaken(Player playerVictim, DamageSource source, float newDamage) {
        return 0;
    }

    public static void reactToDamageDealt(Player playerCauser, Entity victim, DamageSource source, float newDamage) {
    }

    public static void reactToDamageTaken(Player playerVictim, DamageSource source, float newDamage) {
    }

    public static void runBlockBroken(Player player, BlockState state) {
    }

    public static void runTick(Player entity) {
    }

    public static void runProjectile(Player player, Projectile projectile) {
    }

    public static void removeAttributes(RegistryAccess registryAccess, ItemStack chestStack) {
    }

    public static void addAttributes(Player player) {
    }

    public static void applyExp(Player wearer, Holder<LivingUpgrade> upgrade, float amount) {
    }

    public static Component getTooltip(Holder<LivingUpgrade> upgrade, float exp, boolean hasShiftDown) {
        MutableComponent mutable = Component.translatable(Util.makeDescriptionId("living_upgrade", upgrade.getKey().location()));
        if (upgrade.is(BMTags.Living.IS_DOWNGRADE)) {
            ComponentUtils.mergeStyles(mutable, Style.EMPTY.withColor(ChatFormatting.RED));
        } else {
            ComponentUtils.mergeStyles(mutable, Style.EMPTY.withColor(ChatFormatting.GRAY));
        }

        if (hasShiftDown) {
            mutable.append(CommonComponents.SPACE).append(Component.translatable("living_upgrade.bloodmagic.exp", exp, nextLevelExp(upgrade, exp)));
        } else {
            mutable.append(CommonComponents.SPACE).append(Component.translatable("living_upgrade.bloodmagic.level", getLevelFromXp(upgrade, exp)));
        }

        return mutable;
    }
}
