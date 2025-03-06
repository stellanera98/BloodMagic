package wayoftime.bloodmagic.client.event;

import com.mojang.datafixers.util.Either;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.common.datacomponent.Binding;

@EventBusSubscriber(Dist.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public static void onBindingHoverText(RenderTooltipEvent.GatherComponents event) {
        Binding binding = event.getItemStack().get(BMDataComponents.BINDING);
        if (binding == null) {
            return;
        }

        if (binding.isEmpty()) {
            event.getTooltipElements().add(Either.left(Component.translatable("tooltip.bloodmagic.noOwner").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC)));
            return;
        }

        event.getTooltipElements().add(Either.left(binding.getHoverText()));
    }
}
