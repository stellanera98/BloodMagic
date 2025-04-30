package wayoftime.bloodmagic.compat.jei.recipe;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.block.BMBlocks;
import wayoftime.bloodmagic.common.fluid.BMFluids;
import wayoftime.bloodmagic.common.recipe.bloodaltar.BloodAltarRecipe;
import wayoftime.bloodmagic.util.ChatUtil;

import java.awt.*;

public class AltarRecipeCategory implements IRecipeCategory<BloodAltarRecipe> {

    public static final RecipeType<BloodAltarRecipe> TYPE = RecipeType.create(BloodMagic.MODID, "blood_altar", BloodAltarRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public AltarRecipeCategory(IGuiHelper helper) {
        background = helper.createDrawable(ResourceLocation.fromNamespaceAndPath(BloodMagic.MODID, "gui/jei/altar.png"), 3, 4, 155, 65);
        icon = helper.createDrawableItemStack(new ItemStack(BMBlocks.BLOOD_ALTAR));
    }

    @Override
    public RecipeType<BloodAltarRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.bloodmagic.altar.category");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BloodAltarRecipe recipe, IFocusGroup focuses) {
        IRecipeSlotBuilder output = builder.addSlot(RecipeIngredientRole.OUTPUT, 126, 31);
        IRecipeSlotBuilder input = builder.addSlot(RecipeIngredientRole.INPUT, 32, 1);
        output.addItemStack(recipe.getResult());
        input.addIngredients(recipe.getInput());
    }

    @Override
    public void draw(BloodAltarRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Minecraft mc = Minecraft.getInstance();
        String tier = I18n.get("jei.bloodmagic.altar.tier", recipe.minTier + 1);
        String lp = I18n.get("jei.bloodmagic.altar.lp", recipe.totalBlood);
        guiGraphics.drawString(mc.font, tier, 90 - mc.font.width(tier) / 2, 0, Color.GRAY.getRGB());
        guiGraphics.drawString(mc.font, lp, 90 - mc.font.width(lp) / 2, 10, Color.GRAY.getRGB());
    }

    @Override
    public void getTooltip(ITooltipBuilder builder, BloodAltarRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (mouseX >= 13 && mouseX <= 64 && mouseY >= 27 && mouseY <= 58) {
            builder.add(Component.translatable("jei.bloodmagic.altar.consumption", ChatUtil.DECIMAL_FORMAT.format(recipe.craftSpeed)));
            builder.add(Component.translatable("jei.bloodmagic.altar.drain", ChatUtil.DECIMAL_FORMAT.format(recipe.drainSpeed)));
        }
    }
}
