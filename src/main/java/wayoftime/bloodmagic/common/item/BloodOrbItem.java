package wayoftime.bloodmagic.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.FakePlayer;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.common.datacomponent.Binding;
import wayoftime.bloodmagic.common.datacomponent.SoulNetwork;
import wayoftime.bloodmagic.util.SoulTicket;
import wayoftime.bloodmagic.util.SoulNetworkHelper;

import java.util.List;

public class BloodOrbItem extends BindableBaseItem implements IBindable  {

    public BloodOrbItem() {
        super();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player instanceof FakePlayer)
            return InteractionResultHolder.consume(stack);


        Binding binding = getBinding(stack);
        if (binding == null)
            return InteractionResultHolder.consume(stack);

        int capacity = getCapacity(stack);
        if (capacity == 0)
            return InteractionResultHolder.consume(stack);

        if (level instanceof ServerLevel) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS,
                    0.5F, 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F
            );
        }

        SoulNetwork ownerNetwork = SoulNetworkHelper.getSoulNetwork(binding);
        ownerNetwork.add(SoulTicket.item(stack, level, player, 200), capacity);
        ownerNetwork.hurtPlayer(player, 200);

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.bloodmagic.orb.desc").withStyle(ChatFormatting.GRAY));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public int getCapacity(ItemStack stack) {
        return stack.getOrDefault(BMDataComponents.ORB_CAPACITY, 0);
    }

    public int getFillRate(ItemStack stack) {
        return stack.getOrDefault(BMDataComponents.ORB_FILL_RATE, 0);
    }

    public ResourceLocation getOrbTier(ItemStack stack) {
        return stack.getOrDefault(BMDataComponents.ORB_TIER, ResourceLocation.fromNamespaceAndPath(BloodMagic.MODID, "none"));
    }
}
