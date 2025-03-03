package wayoftime.bloodmagic.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import wayoftime.bloodmagic.common.datacomponent.Binding;
import wayoftime.bloodmagic.util.SoulNetworkHelper;

import java.util.List;

public class BindableBaseItem extends Item implements IBindable {
    public BindableBaseItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Binding binding = SoulNetworkHelper.fromStack(stack);
        if (binding.isEmpty())
            return;

        tooltipComponents.add(Component.translatable("tooltip.bloodmagic.currentOwner", binding.name()).withStyle(ChatFormatting.GRAY));
    }
}
