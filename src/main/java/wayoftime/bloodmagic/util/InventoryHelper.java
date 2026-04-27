package wayoftime.bloodmagic.util;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class InventoryHelper {

    // Offhand, Hotbar, (Curios), Main Inv
    public static NonNullList<ItemStack> getGemOrder(Player player) {
        NonNullList<ItemStack> ret = NonNullList.create();

        ret.addAll(player.getInventory().offhand);
        for (int i = 0; i < 9; i++) {
            ret.add(player.getInventory().items.get(i));
        }
        // TODO curios!
        for (int i = 9; i < player.getInventory().items.size(); i++) {
            ret.add(player.getInventory().items.get(i));
        }

        return ret;
    }
}
