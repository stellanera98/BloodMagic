package wayoftime.bloodmagic.compat.jei.recipe;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.Nullable;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.block.BMBlocks;
import wayoftime.bloodmagic.common.tag.BMTags;

public abstract class ARCFurnaceCategory<T extends AbstractCookingRecipe> implements IRecipeCategory<T> {

    public static final RecipeType<SmeltingRecipe> SMELTING = RecipeType.create(BloodMagic.MODID, "arc_smelting", SmeltingRecipe.class);
    public static final RecipeType<BlastingRecipe> BLASTING = RecipeType.create(BloodMagic.MODID, "arc_blasting", BlastingRecipe.class);
    public static final RecipeType<SmokingRecipe> SMOKING = RecipeType.create(BloodMagic.MODID, "arc_smoking", SmokingRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    public ARCFurnaceCategory(IGuiHelper helper) {
        background = helper.createDrawable(ResourceLocation.fromNamespaceAndPath(BloodMagic.MODID, "gui/jei/arc.png"), 0, 0, 157, 43);
        icon = helper.createDrawableItemStack(new ItemStack(BMBlocks.ARC));
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.bloodmagic.arc_furnace." + getRecipeType().getUid().getPath());
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
    public void setRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup focus) {
        IRecipeSlotBuilder tool = builder.addSlot(RecipeIngredientRole.CATALYST, 22, 17);
        if (recipe instanceof SmeltingRecipe) {
            tool.addIngredients(Ingredient.of(BMTags.Items.ARC_TOOL_SMELTING));
        } else if (recipe instanceof BlastingRecipe) {
            tool.addIngredients(Ingredient.of(BMTags.Items.ARC_TOOL_BLASTING));
        } else if (recipe instanceof SmokingRecipe) {
            tool.addIngredients(Ingredient.of(BMTags.Items.ARC_TOOL_SMOKING));
        }

        builder.addSlot(RecipeIngredientRole.INPUT, 1, 6).addIngredients(recipe.getIngredients().getFirst());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 54, 17).addItemStack(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()));
    }
}
