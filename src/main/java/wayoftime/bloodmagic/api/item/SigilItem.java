package wayoftime.bloodmagic.api.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.ModConfigSpec;
import wayoftime.bloodmagic.common.sigil.SigilConfig;
import wayoftime.bloodmagic.api.BMIdentifiers;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.common.sigil.SigilEffect;
import wayoftime.bloodmagic.util.SoulTicket;
import wayoftime.bloodmagic.util.helper.SoulNetworkHelper;

import java.util.List;

public class SigilItem extends Item {

    public final SigilConfig.ConfigHolder CONFIG;
    public final ResourceKey<SigilEffect> EFFECT_KEY;
    public SigilItem(Properties properties, SigilConfig.ConfigHolder config, ResourceKey<SigilEffect> effectKey) {
        super(properties);

        CONFIG = config;
        EFFECT_KEY = effectKey;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (stack.has(BMDataComponents.SIGIL_ACTIVE)) {
            boolean isActive = stack.getOrDefault(BMDataComponents.SIGIL_ACTIVE, false);
            tooltipComponents.add(Component.translatable("tooltip.bloodmagic.sigil." + (isActive ? "activated" : "deactivated")).withStyle(ChatFormatting.GRAY));
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public static int getOrDefault(ModConfigSpec.IntValue conf, int value) {
        if (conf == null) {
            return value;
        }

        return conf.get();
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();
        if (player == null || player.isFakePlayer()) {
            return InteractionResult.PASS;
        }

        if (!(stack.getItem() instanceof SigilItem sigil)) {
            return InteractionResult.PASS;
        }

        if (context.getLevel().isClientSide) {
            return InteractionResult.sidedSuccess(true);
        }

        SigilEffect effect = getEffect(player.level().registryAccess(), sigil.EFFECT_KEY);
        int cost = getOrDefault(sigil.CONFIG.USE_BLOCK_COST, 0);
        boolean result = effect.useOnBlock(stack, player, context);
        if (result) {
            SoulNetworkHelper.getSoulNetwork(player).syphonAndDamage(SoulTicket.item(stack, player.level(), player, cost), player);
            return InteractionResult.sidedSuccess(false);
        }

        return InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (player.isFakePlayer()) {
            return InteractionResultHolder.pass(stack);
        }

        if (!(stack.getItem() instanceof SigilItem sigil)) {
            return InteractionResultHolder.pass(stack);
        }

        if (stack.has(BMDataComponents.SIGIL_ACTIVE) && player.isShiftKeyDown()) {
            boolean state = stack.getOrDefault(BMDataComponents.SIGIL_ACTIVE, false); // using .get() makes it complain about it being null, so using getOrDefault anyways
            stack.set(BMDataComponents.SIGIL_ACTIVE, !state);
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
        }

        if (level.isClientSide) {
            return InteractionResultHolder.sidedSuccess(stack, true);
        }

        SigilEffect effect = getEffect(player.level().registryAccess(), sigil.EFFECT_KEY);
        int cost = getOrDefault(sigil.CONFIG.USE_AIR_COST, 0);
        boolean result = effect.useOnAir(stack, player, usedHand);
        if (result) {
            SoulNetworkHelper.getSoulNetwork(player).syphonAndDamage(SoulTicket.item(stack, player.level(), player, cost), player);
            return InteractionResultHolder.sidedSuccess(stack, false);
        }

        return super.use(level, player, usedHand);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if (player.isFakePlayer()) {
            return InteractionResult.PASS;
        }

        if (!(stack.getItem() instanceof SigilItem sigil)) {
            return InteractionResult.PASS;
        }

        if (player.level().isClientSide) {
            return InteractionResult.sidedSuccess(true);
        }

        SigilEffect effect = getEffect(player.level().registryAccess(), sigil.EFFECT_KEY);
        int cost = getOrDefault(sigil.CONFIG.USE_ENTITY_COST, 0);
        boolean result = effect.useOnEntity(stack, player, interactionTarget);
        if (result) {
            SoulNetworkHelper.getSoulNetwork(player).syphonAndDamage(SoulTicket.item(stack, player.level(), player, cost), player);
            return InteractionResult.sidedSuccess(true);
        }

        return InteractionResult.PASS;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (level.isClientSide) {
            return;
        }

        if (!(stack.getItem() instanceof SigilItem sigil)) {
            return;
        }

        if (!(entity instanceof Player player)) {
            return;
        }

        if (player.isFakePlayer()) {
            return;
        }

        if (stack.getOrDefault(BMDataComponents.SIGIL_ACTIVE, false)) {
            if (player.tickCount % 100 == 0) { // this could be cheesed by switching it off just before the damage would occur and back on just after. not sure if this is an actual issue
                                               // if it is, switch to a data component for tracking time
                int cost = getOrDefault(sigil.CONFIG.ONGOING_COST, 0);
                SoulNetworkHelper.getSoulNetwork(player).syphonAndDamage(SoulTicket.item(stack, level, player, cost), player);
            }
            SigilEffect effect = getEffect(level.registryAccess(), sigil.EFFECT_KEY);
            effect.activeTick(stack, level, player); // slotId and isSelected? I dont think those are relevant ever for a sigil
        }
    }

    private static SigilEffect getEffect(RegistryAccess registries, ResourceKey<SigilEffect> effectKey) {
        return registries.registryOrThrow(BMIdentifiers.RegistryKeys.SIGIL_EFFECTS).getOrThrow(effectKey);
    }
}
