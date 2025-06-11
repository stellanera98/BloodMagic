package wayoftime.bloodmagic.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.menu.AbstractGhostMenu;

public abstract class AbstractGhostScreen<T extends AbstractGhostMenu<?>> extends AbstractContainerScreen<T> {
    public static final ResourceLocation SELECTED = BloodMagic.rl("container/ghost_selected");

    public AbstractGhostScreen(T menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    public abstract ResourceLocation background();

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(this.background(), leftPos, topPos, 0, 0, imageWidth, imageHeight);
        int lastIdx = this.menu.getLastGhostSlotClicked();
        if (lastIdx >= 0) {
            Slot lastSlot = this.menu.getSlot(lastIdx);
            guiGraphics.blitSprite(SELECTED, lastSlot.x - 3, lastSlot.y - 3, 24, 24);
        }
    }
}
