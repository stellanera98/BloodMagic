package wayoftime.bloodmagic.common.event;

import com.mojang.authlib.GameProfile;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import wayoftime.bloodmagic.common.datacomponent.Binding;
import wayoftime.bloodmagic.common.item.IBindable;
import wayoftime.bloodmagic.util.SoulNetworkHelper;

public class EventHandler {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onInteract(PlayerInteractEvent.RightClickItem event) {
        if (event.getLevel().isClientSide)
            return;

        Player player = event.getEntity();

        if (player instanceof FakePlayer)
            return;

        ItemStack held = event.getItemStack();
        if (!held.isEmpty() && held.getItem() instanceof IBindable bindable) {
            Binding binding = bindable.getBinding(held);
            GameProfile profile = player.getGameProfile();
            if (binding.isEmpty() || (binding.uuid() == profile.getId() && (binding.name().equals(profile.getName())))) {
                if (bindable.onBind(player, held)) {
                    ItemBindEvent toPost = new ItemBindEvent(player, held);
                    NeoForge.EVENT_BUS.post(toPost);
                    if (toPost.isCanceled())
                        return;

                    SoulNetworkHelper.applyBinding(held, player);
                }
            }
        }
    }
}
