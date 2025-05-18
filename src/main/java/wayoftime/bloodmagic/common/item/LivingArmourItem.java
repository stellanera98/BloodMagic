package wayoftime.bloodmagic.common.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.common.living.LivingEffectComponents;
import wayoftime.bloodmagic.common.living.LivingHelper;
import wayoftime.bloodmagic.common.tag.BMTags;

import java.util.List;
import java.util.function.Consumer;

public class LivingArmourItem extends ArmorItem {

    public LivingArmourItem(Type type) {
        super(
                BMMaterialsAndTiers.LIVING_ARMOUR_MATERIAL,
                type,
                new Properties()
                        .durability(type.getDurability(33))
                        .component(BMDataComponents.REQUIRED_SET, BMTags.Items.IS_LIVING_SET)
                );
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        super.setDamage(stack, damage);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, @Nullable T entity, Consumer<Item> onBroken) {
        return super.damageItem(stack, amount, entity, onBroken);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        // TODO LivingStats.addToTooltip()
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        if (!(wearer instanceof Player player)) {
            return false;
        }
        return LivingHelper.hasFullSet(player) && LivingHelper.has(player, LivingEffectComponents.MAKE_PIGLIN_NEUTRAL.get());
    }

    @Override
    public boolean canElytraFly(ItemStack stack, LivingEntity wearer) {
        if (!(wearer instanceof Player player)) {
            return false;
        }
        return LivingHelper.hasFullSet(player) && LivingHelper.has(player, LivingEffectComponents.ENABLE_ELYTRA.get());
    }

    @Override
    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
        return super.elytraFlightTick(stack, entity, flightTicks); // TODO reduce damage sustained with higher levels? also actually doing the damage
    }
}
