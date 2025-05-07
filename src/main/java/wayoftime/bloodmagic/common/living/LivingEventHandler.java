package wayoftime.bloodmagic.common.living;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.VanillaGameEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import wayoftime.bloodmagic.common.item.LivingArmourItem;

@EventBusSubscriber(Dist.DEDICATED_SERVER)
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
    public static void onExpDropped(LivingExperienceDropEvent event) {
        if (event.getAttackingPlayer() != null) {
            if (LivingHelper.hasFullSet(event.getAttackingPlayer())) {
                LivingHelper.runExp(event.getAttackingPlayer(), event.getOriginalExperience(), event::setDroppedExperience);
            }
        }
    }

    @SubscribeEvent
    public static void onHeal(LivingHealEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (LivingHelper.hasFullSet(player)) {
                LivingHelper.runHealing(player, event.getAmount(), event::setAmount);
            }
        }
    }

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent.Pre event) {
        Entity causer = event.getSource().getEntity();
        Entity victim = event.getEntity();

        if (causer instanceof Player playerCauser) {
            if (LivingHelper.hasFullSet(playerCauser)) {
                LivingHelper.runDamageDealt(playerCauser, victim, event.getContainer());
            }
        }

        if (victim instanceof Player playerVictim) {
            if (LivingHelper.hasFullSet(playerVictim)) {
                LivingHelper.runDamageTaken(playerVictim, event.getContainer());
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
        if (LivingHelper.hasFullSet(player)) {
            LivingHelper.addAttributes(player.level().registryAccess(), player.getItemBySlot(EquipmentSlot.CHEST));
        } else {
            if (fromStack.getItem() instanceof LivingArmourItem) {
                ItemStack chestStack = player.getItemBySlot(EquipmentSlot.CHEST);
                if (chestStack.getItem() instanceof LivingArmourItem) {
                    LivingHelper.removeAttributes(player.level().registryAccess(), chestStack);
                }
            }
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
}
