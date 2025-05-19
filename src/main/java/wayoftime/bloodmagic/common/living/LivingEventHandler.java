package wayoftime.bloodmagic.common.living;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.VanillaGameEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.common.item.BMItems;
import wayoftime.bloodmagic.common.item.LivingArmourItem;

import java.util.List;

@EventBusSubscriber(modid = BloodMagic.MODID)
public class LivingEventHandler {

    @SubscribeEvent
    public static void onTotemUse(LivingUseTotemEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (LivingHelper.hasFullSet(player) && event.getHandHolding() == InteractionHand.OFF_HAND) {
                if (LivingHelper.has(player, LivingEffectComponents.CRIPPLED_ARM.get())) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.RightClickItem event) {
        if (LivingHelper.hasFullSet(event.getEntity())) {
            boolean cancel = false;
            if (event.getHand() == InteractionHand.OFF_HAND && LivingHelper.has(event.getEntity(), LivingEffectComponents.CRIPPLED_ARM.get())) {
                cancel = true;
            }
            if (event.getItemStack().getUseAnimation() == UseAnim.DRINK) {
                cancel = true;
            }

            if (cancel) {
                event.setCancellationResult(InteractionResult.FAIL);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
        if (LivingHelper.hasFullSet(event.getEntity()) && event.getHand() == InteractionHand.OFF_HAND) {
            if (LivingHelper.has(event.getEntity(), LivingEffectComponents.CRIPPLED_ARM.get())) {
                event.setCancellationResult(InteractionResult.FAIL);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onKnockback(LivingKnockBackEvent event) {
        DamageSource source = event.getEntity().getLastDamageSource();
        if (source == null) {
            return;
        }
        Entity causer = event.getEntity().getLastDamageSource().getEntity();
        if (causer instanceof Player player) {
            if (LivingHelper.hasFullSet(player)) {
                float changed = LivingHelper.modifyKnockback(player.level(), player, event.getEntity().getLastDamageSource(), event.getOriginalStrength());
                event.setStrength(changed);
            }
        }
    }

    @SubscribeEvent
    public static void onExpPickup(PlayerXpEvent.PickupXp event) {
        if (LivingHelper.hasFullSet(event.getEntity())) {
            event.getOrb().value = LivingHelper.modifyExperience(event.getEntity().level(), event.getEntity(), event.getOrb().value);
        }
    }

    @SubscribeEvent
    public static void onHeal(LivingHealEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (LivingHelper.hasFullSet(player)) {
                float changed = LivingHelper.modifyHealing(player.level(), player, event.getAmount());
                event.setAmount(changed);
            }
        }
    }

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent.Pre event) {
        Entity causer = event.getSource().getEntity();
        Entity victim = event.getEntity();

        if (causer instanceof Player playerCauser) {
            if (LivingHelper.hasFullSet(playerCauser)) {
                float newDamage = LivingHelper.modifyDamageDealt(playerCauser, victim, event.getSource(), event.getOriginalDamage());
                event.setNewDamage(newDamage);
                LivingHelper.reactToDamageDealt(playerCauser, victim, event.getSource(), newDamage);
            }
        }

        if (victim instanceof Player playerVictim) {
            if (LivingHelper.hasFullSet(playerVictim)) {
                float newDamage = LivingHelper.modifyDamageTaken(playerVictim, event.getSource(), event.getNewDamage());
                event.setNewDamage(newDamage);
                LivingHelper.reactToDamageTaken(playerVictim, event.getSource(), newDamage);
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBroken(BlockEvent.BreakEvent event) {
        if (LivingHelper.hasFullSet(event.getPlayer())) {
            LivingHelper.runBlockBroken(event.getPlayer(), event.getState());
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (LivingHelper.hasFullSet(event.getEntity())) {
            LivingHelper.runTick(event.getEntity());
        }
    }

    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        if (!event.getSlot().isArmor()) {
            return;
        }
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        ItemStack fromStack = event.getFrom();
        if (fromStack.getItem() instanceof LivingArmourItem) {
            ItemStack chestStack = player.getItemBySlot(EquipmentSlot.CHEST);
            if (chestStack.getItem() instanceof LivingArmourItem) {
                LivingHelper.removeAttributes(player.level().registryAccess(), chestStack);
            }
        }

        if (LivingHelper.hasFullSet(player)) {
            LivingHelper.addAttributes(player);
        }
    }

    @SubscribeEvent
    public static void vanillaEvents(VanillaGameEvent event) {
        if (!(event.getCause() instanceof Player player)) {
            return;
        }
        if (!LivingHelper.hasFullSet(player)) {
            return;
        }

        if (event.getVanillaEvent() == GameEvent.DRINK) {
            if (LivingHelper.has(player, LivingEffectComponents.QUENCHED.get())) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void entityJoin(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Projectile projectile) {
            if (projectile.getOwner() instanceof Player player) {
                if (LivingHelper.hasFullSet(player)) {
                    LivingHelper.runProjectile(player, projectile);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
    public static void armourBreak(ArmorHurtEvent event) {
        ItemStack stack = event.getArmorItemStack(EquipmentSlot.CHEST);
        float damage = event.getNewDamage(EquipmentSlot.CHEST);
        if (stack.is(BMItems.LIVING_PLATE)) {
            if (damage + stack.getDamageValue() >= stack.getMaxDamage()) {
                stack.set(DataComponents.CUSTOM_NAME, Component.translatable("item.bloodmagic.living_plate.dead"));
                stack.set(DataComponents.LORE, new ItemLore(List.of(Component.translatable("tooltip.bloodmagic.has_living_stats"))));
                ItemStack converted = stack.hurtAndConvertOnBreak((int) Math.ceil(damage), Items.IRON_CHESTPLATE, event.getEntity(), EquipmentSlot.CHEST);
                event.getEntity().setItemSlot(EquipmentSlot.CHEST, converted);
                event.setNewDamage(EquipmentSlot.CHEST, 0);
            }
        }
    }
}
