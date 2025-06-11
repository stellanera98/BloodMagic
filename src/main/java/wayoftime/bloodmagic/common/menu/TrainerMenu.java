package wayoftime.bloodmagic.common.menu;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;

public class TrainerMenu extends AbstractGhostMenu<TrainerMenu> {

    // CLIENT constructor
    public TrainerMenu(int containerId, Inventory playerInv, RegistryFriendlyByteBuf buf) {
        super(BMMenus.TRAINER.get(), containerId, playerInv, buf.readInt(), 4, 4, 89, 15);
    }

    // SERVER constructor
    public TrainerMenu(int containerId, Inventory playerInv, GhostItemHandler handler, ContainerData trainerData) {
        super(BMMenus.TRAINER.get(), containerId, playerInv, trainerData, handler, 4, 4, 89, 15);
    }

    public boolean isWhitelist() {
        return tracker.get(1) == 0;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
