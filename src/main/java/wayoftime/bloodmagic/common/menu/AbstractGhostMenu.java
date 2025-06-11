package wayoftime.bloodmagic.common.menu;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.SlotItemHandler;

public abstract class AbstractGhostMenu<T extends AbstractContainerMenu> extends AbstractContainerMenu {

    public AbstractGhostMenu(MenuType<T> type, int containerId, Inventory playerInv, int dataSize, int rows, int columns, int xOff, int yOff) {
        this(type, containerId, playerInv, new SimpleContainerData(dataSize), new GhostItemHandler(rows * columns), rows, columns, xOff, yOff);
    }

    public AbstractGhostMenu(MenuType<T> type, int containerId, Inventory playerInventory, ContainerData tracker, GhostItemHandler handler, int rows, int columns, int xOff, int yOff) {
        super(type, containerId);
        this.tracker = tracker;
        this.addDataSlots(tracker);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.addSlot(new GhostSlot(handler, j + i * 9, xOff + j * 21, yOff + i * 21));
            }
        }

        // player inv
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 123 + i * 18));
            }
        }

        // player hotbar
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 181));
        }
    }

    public final ContainerData tracker;
    public int getLastGhostSlotClicked() {
        return tracker.get(0);
    }

    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        if (slotId < 0) {
            super.clicked(slotId, button, clickType, player);
        }

        Slot slot = slots.get(slotId);
        if (slot instanceof GhostSlot ghostSlot) {
            tracker.set(0, slot.getSlotIndex());
            if ((button == 0 || button == 1)) {
                ItemStack slotStack = slot.getItem();
                ItemStack heldStack = this.getCarried();

                if (button == 0) { // Left mouse click-eth
                    if (heldStack.isEmpty() && !slotStack.isEmpty()) {
                        // I clicked on the slot with an empty hand. Selecting!
                        // Return here to not save the server-side inventory
                        return;
                    } else if (!heldStack.isEmpty() && slotStack.isEmpty() && ghostSlot.isValid(heldStack)) {
                        ItemStack copyStack = heldStack.copy();
                        copyStack.setCount(1);
                        slot.set(copyStack);
                    }
                } else { // Right mouse click-eth away
                    slot.set(ItemStack.EMPTY);
                }
            }
        }

        super.clicked(slotId, button, clickType, player);
    }

    @Override // dont do quickMove here
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack movedStack = ItemStack.EMPTY;
        Slot movedSlot = this.slots.get(index);

        if (movedSlot.hasItem()) {
            ItemStack rawStack = movedSlot.getItem();
            movedStack = rawStack.copy();
        }

        return movedStack;
    }

    public static class GhostSlot extends SlotItemHandler {

        private final GhostItemHandler handler;
        public GhostSlot(GhostItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
            this.handler = itemHandler;
        }

        public boolean isValid(ItemStack stack) {
            return handler.isItemValid(this.index, stack);
        }

        @Override
        public boolean mayPickup(Player playerIn) {
            return false;
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return false;
        }
    }
}
