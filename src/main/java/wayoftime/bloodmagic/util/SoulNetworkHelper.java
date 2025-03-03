package wayoftime.bloodmagic.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.saveddata.SavedData.Factory;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.common.datacomponent.Binding;
import wayoftime.bloodmagic.common.datacomponent.SoulNetwork;
import wayoftime.bloodmagic.common.world.BMSavedData;

import javax.annotation.Nullable;
import java.util.UUID;


public class SoulNetworkHelper {
    public static Binding fromStack(ItemStack stack) {
        return stack.getOrDefault(BMDataComponents.BINDING, Binding.EMPTY);
    }

    public static void applyBinding(ItemStack stack, Player player) {
        Binding binding = new Binding(player.getGameProfile().getId(), player.getGameProfile().getName());
        applyBinding(stack, binding);
    }

    public static void applyBinding(ItemStack stack, Binding binding) {
        stack.set(BMDataComponents.BINDING, binding);
    }

    @Nullable
    private static BMSavedData SD_INSTANCE;

    public static void resetSavedDataInstance(ServerStoppedEvent event) {
        SD_INSTANCE = null;
    }

    public static SoulNetwork getSoulNetwork(UUID uuid) {
        if (SD_INSTANCE == null) {
            if (ServerLifecycleHooks.getCurrentServer() == null)
                return null;

            DimensionDataStorage dimData = ServerLifecycleHooks.getCurrentServer().overworld().getDataStorage();
            SD_INSTANCE = dimData.computeIfAbsent(new Factory<>(BMSavedData::new, BMSavedData::load), BMSavedData.ID);
        }

        return SD_INSTANCE.getNetwork(uuid);
    }

    public static SoulNetwork getSoulNetwork(Binding binding) {
        return getSoulNetwork(binding.uuid());
    }

    public static SoulNetwork getSoulNetwork(Player player) {
        return getSoulNetwork(player.getUUID());
    }

    public static SoulNetwork getSoulNetwork(String uuid) {
        return getSoulNetwork(UUID.fromString(uuid));
    }
}
