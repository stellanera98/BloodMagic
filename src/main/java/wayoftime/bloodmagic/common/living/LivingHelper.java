package wayoftime.bloodmagic.common.living;

import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.apache.commons.lang3.mutable.MutableBoolean;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.common.datacomponent.LivingStats;
import wayoftime.bloodmagic.common.item.LivingArmourItem;
import wayoftime.bloodmagic.common.living.effects.*;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class LivingHelper {
    public static boolean hasFullSet(Player player) {
        for (ItemStack stack : player.getArmorSlots()) {
            if (!(stack.getItem() instanceof LivingArmourItem armourItem)) {
                return false;
            }
            if (armourItem.getType() == ArmorItem.Type.CHESTPLATE) {
                if (stack.getDamageValue() == stack.getMaxDamage()) {
                    return false;
                }
            }
        }

        return true;
    }

    public static ItemStack getChest(Player player) {
        return player.getInventory().getArmor(2);
    }

    public static boolean has(ItemStack stack, RegistryAccess registries, DataComponentType<?> type) {
        MutableBoolean mutableboolean = new MutableBoolean(false);
        runIterationOnItem(stack, registries, (p_344620_, p_344621_) -> {
            if (p_344620_.value().effects().has(type)) {
                mutableboolean.setTrue();
            }
        });
        return mutableboolean.booleanValue();
    }

    public static boolean has(Player player, DataComponentType<?> type) {
        return has(getChest(player), player.level().registryAccess(), type);
    }

    public static void runDamageDealt(Player player, Entity victim, DamageContainer container) {
        LootContext lootContext = LivingContextParamSets.damageBased(victim, container.getSource());
        runDamage(player, container, lootContext, LivingEffectComponents.DEALING_DAMAGE.get());
    }

    public static void runDamageTaken(Player player, DamageContainer container) {
        LootContext lootContext = LivingContextParamSets.damageBased(player, container.getSource());
        runDamage(player, container, lootContext, LivingEffectComponents.TAKING_DAMAGE.get());
    }

    public static void runDamage(Player player, DamageContainer container, LootContext lootContext, DataComponentType<List<ConditionalEffect<DamageBasedEffect>>> type) {
        runIterationOnPlayer(player, (upgrade, level) -> {
            for (ConditionalEffect<DamageBasedEffect> effect : upgrade.value().effects().getOrDefault(type, List.<ConditionalEffect<DamageBasedEffect>>of())) {
                if (effect.matches(lootContext)) {
                    effect.effect().apply(level, player, container);
                }
            }
        });
    }

    public static void runIterationOnPlayer(Player player, BiConsumer<Holder<LivingUpgrade>, Integer> visitor) {
        runIterationOnItem(getChest(player), player.level().registryAccess(), visitor);
    }

    public static void runIterationOnItem(ItemStack stack, RegistryAccess registries, BiConsumer<Holder<LivingUpgrade>, Integer> visitor) {
        LivingStats upgrades = stack.getOrDefault(BMDataComponents.LIVING_UPGRADES, LivingStats.EMPTY);

        for (Object2FloatMap.Entry<Holder<LivingUpgrade>> entry : upgrades.entrySet(registries)) {
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

    public static void runBlockBroken(Player player, BlockState state) {
        LootContext lootContext = LivingContextParamSets.breakBlock(player, state);
        runStandalone(player, lootContext, LivingEffectComponents.BREAK_BLOCK.get());
    }

    private static void runStandalone(Player player, LootContext lootContext, DataComponentType<List<ConditionalEffect<StandaloneEffect>>> type) {
        runIterationOnPlayer(player, (upgrade, level) -> {
            for (ConditionalEffect<StandaloneEffect> effect : upgrade.value().effects().getOrDefault(type, List.<ConditionalEffect<StandaloneEffect>>of())) {
                if (effect.matches(lootContext)) {
                    effect.effect().apply(level, player);
                }
            }
        });
    }

    public static void runTick(Player player) {
        LootContext lootContext = LivingContextParamSets.tick(player);
        runStandalone(player, lootContext, LivingEffectComponents.TICK.get());
    }

    public static void removeAttributes(RegistryAccess registries, ItemStack chestStack) {
        runIterationOnItem(chestStack, registries, (upgrade, level) -> {
            for (AttributeEffect effect : upgrade.value().effects().getOrDefault(LivingEffectComponents.ATTRIBUTES.get(), List.<AttributeEffect>of())) {
                effect.removeModifier(level, chestStack);
            }
        });
    }

    public static void addAttributes(RegistryAccess registries, ItemStack chestStack) {
        runIterationOnItem(chestStack, registries, (upgrade, level) -> {
            for (AttributeEffect effect : upgrade.value().effects().getOrDefault(LivingEffectComponents.ATTRIBUTES.get(), List.<AttributeEffect>of())) {
                effect.addModifier(level, chestStack);
            }
        });
    }

    public static void runHealing(Player player, float amount, Consumer<Float> setter) {
        LootContext lootContext = LivingContextParamSets.valueBased(player);
        runIterationOnPlayer(player, (upgrade, level) -> {
            for (ConditionalEffect<ValueBasedEffect> effect : upgrade.value().effects().getOrDefault(LivingEffectComponents.HEALING.get(), List.<ConditionalEffect<ValueBasedEffect>>of())) {
                if (effect.matches(lootContext)) {
                    setter.accept(effect.effect().process(level, player, amount));
                }
            }
        });
    }

    public static void runExp(Player player, int exp, Consumer<Integer> setter) {
        LootContext lootContext = LivingContextParamSets.valueBased(player);
        runIterationOnPlayer(player, (upgrade, level) -> {
            for (ConditionalEffect<ValueBasedEffect> effect : upgrade.value().effects().getOrDefault(LivingEffectComponents.EXP_LOOTED.get(), List.<ConditionalEffect<ValueBasedEffect>>of())) {
                if (effect.matches(lootContext)) {
                    float after = effect.effect().process(level, player, exp);
                    int changedExp = ((int) after + (player.level().random.nextDouble() < after % 1 ? 1 : 0));
                    setter.accept(changedExp);
                }
            }
        });
    }

    public static void applyExp(Player wearer, Holder<LivingUpgrade> upgrade, float exp) {
        applyExp(getChest(wearer), upgrade, exp);
    }

    public static void applyExp(ItemStack stack, Holder<LivingUpgrade> upgrade, float exp) {
        LivingStats stats = stack.getOrDefault(BMDataComponents.LIVING_UPGRADES, LivingStats.EMPTY);
        LivingStats.Mutable mutable = stats.toMutable();
        mutable.addExp(upgrade, exp);
        stack.set(BMDataComponents.LIVING_UPGRADES, mutable.toImmutable());
    }

    public static void runProjectile(Player player, Projectile projectile) {
        LootContext lootContext = LivingContextParamSets.projectileBased(player, projectile, projectile.position());
        runIterationOnPlayer(player, (upgrade, level) -> {
            for (ConditionalEffect<EntityEffect> effect : upgrade.value().effects().getOrDefault(LivingEffectComponents.PROJECTILE_SHOT.get(), List.<ConditionalEffect<EntityEffect>>of())) {
                if (effect.matches(lootContext)) {
                    effect.effect().apply(level, player, projectile);
                }
            }
        });
    }
}
