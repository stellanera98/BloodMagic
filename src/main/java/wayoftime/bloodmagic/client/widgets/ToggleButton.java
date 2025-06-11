package wayoftime.bloodmagic.client.widgets;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public class ToggleButton extends AbstractButton {

    private final ResourceLocation iconOn;
    private final ResourceLocation iconOff;
    private final Tooltip tooltipOn;
    private final Tooltip tooltipOff;
    private final OnPress onPress;
    private boolean state;
    public ToggleButton(int x, int y, int width, int height, Tooltip tOn, Tooltip tOff, ResourceLocation rOn, ResourceLocation rOff, OnPress onPress, boolean initialState) {
        super(x, y, width, height, Component.literal(""));
        this.iconOn = rOn;
        this.iconOff = rOff;
        this.tooltipOn = tOn;
        this.tooltipOff = tOff;
        this.onPress = onPress;
        this.state = initialState;
    }

    public ToggleButton(Builder builder) {
        this(builder.x, builder.y, builder.width, builder.height, builder.tooltipOn, builder.tooltipOff, builder.iconOn, builder.iconOff, builder.onPress, builder.initialState);
    }

    public boolean getState() {
        return this.state;
    }

    @Override
    public void onPress() {
        this.state = !this.state;
        onPress.onPress(this);
    }

    @Override
    public @Nullable Tooltip getTooltip() {
        return this.state ? tooltipOn : tooltipOff;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.blitSprite(this.state ? iconOn : iconOff, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        this.defaultButtonNarrationText(narrationElementOutput);
    }

    public static Builder builder(OnPress onPress) {
        return new Builder(onPress);
    }

    public static class Builder {
        private final OnPress onPress;
        @Nullable
        private Tooltip tooltipOn;
        private Tooltip tooltipOff;
        private ResourceLocation iconOn;
        private ResourceLocation iconOff;
        private int x;
        private int y;
        private int width = 150;
        private int height = 20;
        private boolean initialState = true;

        public Builder(OnPress onPress) {
            this.onPress = onPress;
        }

        public Builder pos(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder size(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder bounds(int x, int y, int width, int height) {
            return this.pos(x, y).size(width, height);
        }

        public Builder tooltip(@Nullable Tooltip tooltipOn, @Nullable Tooltip tooltipOff) {
            this.tooltipOn = tooltipOn;
            this.tooltipOff = tooltipOff;
            return this;
        }

        public Builder icon(ResourceLocation on, ResourceLocation off) {
            this.iconOn = on;
            this.iconOff = off;
            return this;
        }

        public Builder state(boolean initialState) {
            this.initialState = initialState;
            return this;
        }

        public ToggleButton build() {
            return build(ToggleButton::new);
        }

        public ToggleButton build(java.util.function.Function<Builder, ToggleButton> builder) {
            return builder.apply(this);
        }
    }

    @FunctionalInterface
    public interface OnPress {
        void onPress(ToggleButton button);
    }
}
