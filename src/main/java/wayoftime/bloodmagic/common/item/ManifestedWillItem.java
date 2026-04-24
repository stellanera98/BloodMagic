package wayoftime.bloodmagic.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import wayoftime.bloodmagic.api.capability.IWillHandler;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.common.datacomponent.EnumWillType;
import wayoftime.bloodmagic.util.ChatUtil;

import java.util.List;
import java.util.Locale;

public class ManifestedWillItem extends Item {

    public ManifestedWillItem() {
        super(new Properties()
                .stacksTo(1)
                .component(BMDataComponents.DEMON_WILL_TYPE, EnumWillType.RAW)
                .component(BMDataComponents.DEMON_WILL_AMOUNT, 5D)
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        EnumWillType type = stack.getOrDefault(BMDataComponents.DEMON_WILL_TYPE, EnumWillType.RAW);
        double amount = stack.getOrDefault(BMDataComponents.DEMON_WILL_AMOUNT, 0D);

        tooltipComponents.add(Component.translatable("tooltip.bloodmagic.will", ChatUtil.DECIMAL_FORMAT.format(amount)).withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.translatable("tooltip.bloodmagic.current_type." + type.name().toLowerCase(Locale.ROOT)).withStyle(ChatFormatting.GRAY));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        EnumWillType type = stack.getOrDefault(BMDataComponents.DEMON_WILL_TYPE, EnumWillType.RAW);
        String baseId = Util.makeDescriptionId("item", stack.getItemHolder().getKey().location());
        return baseId + "." + type.getSerializedName();
    }

    // not sure its needed. I keep it for now
    public static IWillHandler getWillHandler(ItemStack willItem, Void unused) {
        return new IWillHandler() {
            @Override
            public double fill(EnumWillType type, double max, boolean doFill) {
                // we are a Raw Will. Mob drop thingy. we do not fill this
                return 0;
            }

            @Override
            public double drain(EnumWillType type, double max, boolean doDrain) {
                EnumWillType containedType = willItem.getOrDefault(BMDataComponents.DEMON_WILL_TYPE, EnumWillType.RAW);
                // same type, deal
                if (containedType == type) {
                    double containedAmount = willItem.getOrDefault(BMDataComponents.DEMON_WILL_AMOUNT, 0d);
                    double toDrain = Math.clamp(max, 0, containedAmount);
                    if (doDrain) {
                        if (containedAmount - toDrain <= 0) {
                            willItem.shrink(1); // hoping this works
                        } else {
                            willItem.set(BMDataComponents.DEMON_WILL_AMOUNT, containedAmount - toDrain);
                        }
                    }
                    return toDrain;
                }

                // not same type, pls go away
                return 0;
            }
        };
    }
}