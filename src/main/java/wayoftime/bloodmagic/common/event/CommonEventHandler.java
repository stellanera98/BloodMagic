package wayoftime.bloodmagic.common.event;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.api.BMTags;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.api.datacomponent.Binding;
import wayoftime.bloodmagic.common.datacomponent.EnumWillType;
import wayoftime.bloodmagic.common.item.BMItems;

import java.util.Objects;

@EventBusSubscriber(modid = BloodMagic.MODID)
public class CommonEventHandler {

    @SubscribeEvent
    public static void itemPickup(ItemEntityPickupEvent event) {
        ItemStack stack = event.getItemEntity().getItem();
        if (!stack.is(BMItems.RAW_WILL)) {
            return;
        }
        EnumWillType type = stack.getOrDefault(BMDataComponents.DEMON_WILL_TYPE, EnumWillType.DEFAULT);
        double amount = stack.getOrDefault(BMDataComponents.DEMON_WILL_AMOUNT, 0D);
        NonNullList<ItemStack> inv = event.getPlayer().getInventory().items;
        inv.addAll(event.getPlayer().getInventory().offhand);

        for (int i = 0; i < inv.size(); i++) {
            ItemStack gemStack = inv.get(i);
            if (gemStack.is(BMTags.Items.TARTARIC_GEM)) {
                if (gemStack.getOrDefault(BMDataComponents.DEMON_WILL_TYPE, EnumWillType.DEFAULT) == type) {
                    double has = gemStack.getOrDefault(BMDataComponents.DEMON_WILL_AMOUNT, 0D);
                    // TODO you know this is worthless. what happens if the gem is empty and you pick up non-default will? thats right, nothing
                    probably add WillStack and IWillHandler and use those
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onInteract(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();

        if (player instanceof FakePlayer)
            return;

        ItemStack held = event.getItemStack();
        if (held.isEmpty()) {
            return;
        }

        Binding binding = held.get(BMDataComponents.BINDING);
        if (binding == null) {
            return;
        }
        GameProfile profile = event.getEntity().getGameProfile();
        if (binding.isEmpty()) {
            binding = new Binding(profile.getId(), profile.getName());
            if (NeoForge.EVENT_BUS.post(new ItemBindEvent(event.getEntity(), held)).isCanceled()) {
                return;
            }
            held.set(BMDataComponents.BINDING, binding);
        } else if (binding.uuid() == profile.getId() && !Objects.equals(binding.name(), profile.getName())) {
            binding = new Binding(profile.getId(), profile.getName());
            held.set(BMDataComponents.BINDING, binding);
        }
    }
}
