package wayoftime.bloodmagic.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.client.widgets.ToggleButton;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.common.datacomponent.UpgradeTome;
import wayoftime.bloodmagic.common.living.LivingHelper;
import wayoftime.bloodmagic.common.menu.TrainerMenu;

public class TrainerScreen extends AbstractGhostScreen<TrainerMenu> {

    public TrainerScreen(TrainerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 187;
    }

    private static final Tooltip allowTooltip = Tooltip.create(Component.translatable("trainer.bloodmagic.allow_others"));
    private static final Tooltip denyTooltip = Tooltip.create(Component.translatable("trainer.bloodmagic.deny_others"));
    private static final ResourceLocation allow = ResourceLocation.fromNamespaceAndPath(BloodMagic.MODID, "trainer/allow_others");
    private static final ResourceLocation deny = ResourceLocation.fromNamespaceAndPath(BloodMagic.MODID, "trainer/deny_others");
    @Override
    protected void init() {
        super.init();

        addRenderableWidget(Button.builder(Component.literal("<"), button -> changeLevel(-1))
                .pos(leftPos + 16, topPos + 34)
                .size(8, 20)
                .build()
        );
        addRenderableWidget(Button.builder(Component.literal(">"), button -> changeLevel(1))
                .pos(leftPos + 44, topPos + 34)
                .size(8, 20)
                .build()
        );

        addRenderableWidget(ToggleButton.builder(button -> this.menu.setData(0, button.getState() ? 1 : 0))
                .tooltip(allowTooltip, denyTooltip)
                .icon(allow, deny)
                .pos(leftPos + 24, topPos + 55)
                .size(20, 20)
                .state(this.menu.isWhitelist())
                .build()
        );
    }

    private void changeLevel(int amount) {
        int slotId = this.menu.getLastGhostSlotClicked();
        if (slotId < 0) {
            BloodMagic.LOGGER.info("No slot selected ({}), cannot change level", slotId);
            return;
        }

        ItemStack tomeStack = this.menu.getSlot(slotId).getItem();
        if (tomeStack.isEmpty()) {
            return;
        }
        UpgradeTome tome = tomeStack.get(BMDataComponents.UPGRADE_TOME_DATA);
        if (tome == null) {
            return;
        }

        int currLevel = LivingHelper.getLevelFromXp(tome.upgrade(), tome.exp());
        int changedExp = LivingHelper.getExpForLevel(tome.upgrade(), currLevel + amount);
        if (changedExp == -1) {
            return;
        }

        tomeStack.set(BMDataComponents.UPGRADE_TOME_DATA, new UpgradeTome(tome.upgrade(), changedExp));
        this.menu.getSlot(slotId).set(tomeStack);
    }

    public String getLevelString() {
        int slotId = this.menu.getLastGhostSlotClicked();
        if (slotId < 0) {
            BloodMagic.LOGGER.info("No slot selected ({}), cannot get level", slotId);
            return "";
        }

        ItemStack tomeStack = this.menu.getSlot(slotId).getItem();
        if (tomeStack.isEmpty()) {
            return "";
        }
        UpgradeTome tome = tomeStack.get(BMDataComponents.UPGRADE_TOME_DATA);
        if (tome == null) {
            return "";
        }

        int currLevel = LivingHelper.getLevelFromXp(tome.upgrade(), tome.exp());
        return currLevel == -1 ? "" : "" + currLevel;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);
        String level = getLevelString();
        if (!level.isEmpty()) {
            int xOff = -3 * level.length();
            guiGraphics.drawString(this.font, Component.literal(level), 34 + xOff, 40, 0xFFFFFF /*4210752*/, false);
        }
    }

    @Override
    public ResourceLocation background() {
        return BloodMagic.rl("gui/container/training_bracelet.png");
    }
}
