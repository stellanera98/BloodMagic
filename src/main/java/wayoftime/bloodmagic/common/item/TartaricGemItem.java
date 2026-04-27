package wayoftime.bloodmagic.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import wayoftime.bloodmagic.api.capability.IWillHandler;
import wayoftime.bloodmagic.common.caps.BMCaps;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.common.datacomponent.EnumWillType;
import wayoftime.bloodmagic.common.datamap.BMDataMaps;
import wayoftime.bloodmagic.common.datamap.WillStack;
import wayoftime.bloodmagic.util.ChatUtil;
import wayoftime.bloodmagic.util.InventoryHelper;

import java.util.List;

public class TartaricGemItem extends Item {

    public TartaricGemItem() {
        super(new Properties()
                .stacksTo(1)
                .component(BMDataComponents.DEMON_WILL_AMOUNT, 0D)
                .component(BMDataComponents.DEMON_WILL_TYPE, EnumWillType.RAW)
        );
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        ItemStack gem = context.getItemInHand();

        IWillHandler blockHandler = level.getCapability(BMCaps.BLOCK_WILL_HANDLER, pos);
        IWillHandler gemHandler = gem.getCapability(BMCaps.ITEM_WILL_HANDLER);
        if (blockHandler == null || gemHandler == null) {
            return InteractionResult.FAIL;
        }
        Double max = gem.getItemHolder().getData(BMDataMaps.TARTARIC_GEM_MAX_AMOUNTS);
        WillStack gemWill = gemHandler.getWillStack();
        EnumWillType drainType = gemWill.type();
        double drainAmount = max - gemWill.amount();
        if (gemWill.amount() == 0) {
            WillStack blockWill = blockHandler.getWillStack();
            drainType = blockWill.type();
            drainAmount = max;
        }
        double drained = blockHandler.drain(drainType, drainAmount, true);
        gemHandler.fill(drainType, drained, true);

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        NonNullList<ItemStack> inv = InventoryHelper.getGemOrder(player);
        ItemStack gem = player.getItemInHand(usedHand);
        EnumWillType type = gem.getOrDefault(BMDataComponents.DEMON_WILL_TYPE, EnumWillType.RAW);
        double amount = gem.getOrDefault(BMDataComponents.DEMON_WILL_AMOUNT, 0D);
        Double max = gem.getItemHolder().getData(BMDataMaps.TARTARIC_GEM_MAX_AMOUNTS);
        double limit = max - amount; // NPE deserved for not having a max set for TartaricGemItem
        for (int i = 0; i < inv.size(); i++) {
            if (usedHand == InteractionHand.MAIN_HAND && i == player.getInventory().selected) {
                continue;
            }
            if (usedHand == InteractionHand.OFF_HAND && i == 9) {
                continue;
            }

            ItemStack other = inv.get(i);
            IWillHandler otherHandler = other.getCapability(BMCaps.ITEM_WILL_HANDLER);
            if (otherHandler != null) {
                double drained = otherHandler.drain(type, limit, true);
                amount += drained;
                limit -= drained;

                if (limit <= 0) {
                    break;
                }
            }
        }

        gem.set(BMDataComponents.DEMON_WILL_AMOUNT, amount);
        return InteractionResultHolder.sidedSuccess(gem, level.isClientSide);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.getItemHolder().getData(BMDataMaps.TARTARIC_GEM_MAX_AMOUNTS) != null;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        double currentWill = stack.getOrDefault(BMDataComponents.DEMON_WILL_AMOUNT, 0D);
        Holder<Item> holder = stack.getItemHolder();
        Double maxWill = holder.getData(BMDataMaps.TARTARIC_GEM_MAX_AMOUNTS); // should never be called if this is null since thats covered by isBarVisible
        return (int) ((currentWill / maxWill) * 13);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        double currentWill = stack.getOrDefault(BMDataComponents.DEMON_WILL_AMOUNT, 0D);
        Holder<Item> holder = stack.getItemHolder();
        Double maxWill = holder.getData(BMDataMaps.TARTARIC_GEM_MAX_AMOUNTS); // should never be called if this is null since thats covered by isBarVisible
        return Mth.hsvToRgb(Math.max(0.0F, (float) (currentWill / maxWill)) / 3.0F, 1.0F, 1.0F);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
        EnumWillType type = stack.getOrDefault(BMDataComponents.DEMON_WILL_TYPE, EnumWillType.RAW);
        double amount = stack.getOrDefault(BMDataComponents.DEMON_WILL_AMOUNT, 0D);
        ResourceLocation loc = stack.getItemHolder().getKey().location();

        tooltip.add(Component.translatable("tooltip.bloodmagic.soul_gem." + loc.getPath()).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.bloodmagic.will", ChatUtil.DECIMAL_FORMAT.format(amount)).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.bloodmagic.current_type." + type.getSerializedName()).withStyle(ChatFormatting.GRAY));

        super.appendHoverText(stack, context, tooltip, tooltipFlag);
    }

    public static IWillHandler getWillHandler(ItemStack gem, Void unused) {
        return new IWillHandler() {
            @Override
            public WillStack getWillStack() {
                return new WillStack(
                        gem.getOrDefault(BMDataComponents.DEMON_WILL_TYPE, EnumWillType.RAW),
                        gem.getOrDefault(BMDataComponents.DEMON_WILL_AMOUNT, 0d)
                );
            }

            @Override
            public double fill(EnumWillType type, double max, boolean doFill) {
                EnumWillType containedType = gem.getOrDefault(BMDataComponents.DEMON_WILL_TYPE, EnumWillType.RAW);
                double containedAmount = gem.getOrDefault(BMDataComponents.DEMON_WILL_AMOUNT, 0d);
                double maxAmount = gem.getItemHolder().getData(BMDataMaps.TARTARIC_GEM_MAX_AMOUNTS); // if thats called on non-tartaric gem, NPEs are deserved
                double toFill = Math.clamp(max, 0, maxAmount - containedAmount);

                // if empty we fill regardless and take on filled type
                if (containedAmount == 0) {
                    if (doFill) {
                        gem.set(BMDataComponents.DEMON_WILL_AMOUNT, toFill);
                        gem.set(BMDataComponents.DEMON_WILL_TYPE, type);
                    }
                    return toFill;
                }

                // if same type we add as much as we can
                if (containedType == type) {
                    if (doFill) {
                        gem.set(BMDataComponents.DEMON_WILL_AMOUNT, containedAmount + toFill);
                    }
                    return toFill;
                }

                // not same type, gem not empty. go away pls
                return 0;
            }

            @Override
            public double drain(EnumWillType type, double max, boolean doDrain) {
                EnumWillType containedType = gem.getOrDefault(BMDataComponents.DEMON_WILL_TYPE, EnumWillType.RAW);
                // same type, deal
                if (containedType == type) {
                    double containedAmount = gem.getOrDefault(BMDataComponents.DEMON_WILL_AMOUNT, 0d);
                    double toDrain = Math.clamp(max, 0, containedAmount);
                    if (doDrain) {
                        gem.set(BMDataComponents.DEMON_WILL_AMOUNT, containedAmount - toDrain);
                    }
                    return toDrain;
                }

                // not same type, pls go away
                return 0;
            }
        };
    }
}
