package wayoftime.bloodmagic.compat.jei.recipe;

import com.mojang.datafixers.util.Pair;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.HolderSet;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.block.BMBlocks;
import wayoftime.bloodmagic.common.recipe.arc.ARCRecipe;

import java.awt.*;
import java.util.List;

public class ARCRecipeCategory implements IRecipeCategory<ARCRecipe> {

    public static final RecipeType<ARCRecipe> TYPE = RecipeType.create(BloodMagic.MODID, "alchemical_reaction_chamber", ARCRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;
    public ARCRecipeCategory(IGuiHelper helper) {
        background = helper.createDrawable(ResourceLocation.fromNamespaceAndPath(BloodMagic.MODID, "gui/jei/arc.png"), 0, 0, 157, 43);
        icon = helper.createDrawableItemStack(new ItemStack(BMBlocks.ARC));
    }

    @Override
    public RecipeType<ARCRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.bloodmagic.arc.category");
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
    public void setRecipe(IRecipeLayoutBuilder builder, ARCRecipe recipe, IFocusGroup focuses) {
        IRecipeSlotBuilder tool = builder.addSlot(RecipeIngredientRole.CATALYST, 22, 17);
        IRecipeSlotBuilder input = builder.addSlot(RecipeIngredientRole.INPUT, 1, 6);
        tool.addIngredients(recipe.getTool());
        input.addIngredients(recipe.getInput());
        if (recipe.getInputFluid().isPresent()) {
            FluidStack fluidStackIn = recipe.getInputFluid().get();
            IRecipeSlotBuilder fluidIn = builder.addSlot(RecipeIngredientRole.INPUT, 1, 26);
            fluidIn.setFluidRenderer(20_000, true, 16, 16);
            fluidIn.addFluidStack(fluidStackIn.getFluid(), fluidStackIn.getAmount());
        }

        if (recipe.getOutputFluid().isPresent()) {
            FluidStack fluidStackOut = recipe.getInputFluid().get();
            IRecipeSlotBuilder fluidOut = builder.addSlot(RecipeIngredientRole.OUTPUT, 140, 7);
            fluidOut.setFluidRenderer(20_000, true, 16, 36);
            fluidOut.addFluidStack(fluidStackOut.getFluid(), fluidStackOut.getAmount());
        }

        List<ItemStack> allListedOutputs = recipe.getAllListedOutputs().stream().map(Pair::getFirst).toList();
        for (int i = 0; i < allListedOutputs.size(); i++) {
            IRecipeSlotBuilder output = builder.addSlot(RecipeIngredientRole.OUTPUT, 54 + i * 22, 17).addItemStack(allListedOutputs.get(i));
        }
    }

    @Override
    public void draw(ARCRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Minecraft mc = Minecraft.getInstance();
        List<Double> chances = recipe.getAllListedOutputs().stream().map(Pair::getSecond).toList();
        for (int i = 0; i < chances.size(); i++) {
            String info = "";
            if (chances.get(i) < 1) {
                info = (int) Math.round(100 * chances.get(i)) + "%";
            }

            guiGraphics.drawString(mc.font, info, 64 + i * 22 - mc.font.width(info) / 2, 5, Color.WHITE.getRGB(), true);
        }
    }
}
