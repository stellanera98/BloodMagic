package wayoftime.bloodmagic.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.block.BMBlocks;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.common.item.BMItems;
import wayoftime.bloodmagic.common.recipe.BMRecipes;
import wayoftime.bloodmagic.compat.jei.recipe.AltarRecipeCategory;
import wayoftime.bloodmagic.compat.jei.recipe.SoulForgeCategory;
import wayoftime.bloodmagic.util.DemonWillType;

import java.util.List;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(BloodMagic.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(new AltarRecipeCategory(helper));
        registration.addRecipeCategories(new SoulForgeCategory(helper));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(BMBlocks.BLOOD_ALTAR), AltarRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(BMBlocks.HELLFIRE_FORGE), SoulForgeCategory.TYPE);
    }

    private static <I extends RecipeInput, T extends Recipe<I>> List<T> getRecipes(RecipeManager karen, RecipeType<T> type) {
        return karen.getAllRecipesFor(type).stream().map(RecipeHolder::value).toList();
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager karen = Minecraft.getInstance().level.getRecipeManager();

        registration.addRecipes(AltarRecipeCategory.TYPE, getRecipes(karen, BMRecipes.BLOOD_ALTAR_TYPE.get()));
        registration.addRecipes(SoulForgeCategory.TYPE, getRecipes(karen, BMRecipes.SOUL_FORGE_TYPE.get()));
    }

    private static final IIngredientSubtypeInterpreter<ItemStack> WILL_TYPE = (stack, context) -> {
        DemonWillType type = stack.get(BMDataComponents.DEMON_WILL_TYPE);
        return type == null ? IIngredientSubtypeInterpreter.NONE : type.toLower();
    };

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(BMItems.SOUL_GEM_PETTY.get(), WILL_TYPE);
        registration.registerSubtypeInterpreter(BMItems.SOUL_GEM_LESSER.get(), WILL_TYPE);
        registration.registerSubtypeInterpreter(BMItems.SOUL_GEM_COMMON.get(), WILL_TYPE);
        registration.registerSubtypeInterpreter(BMItems.SOUL_GEM_GREATER.get(), WILL_TYPE);
        registration.registerSubtypeInterpreter(BMItems.SOUL_GEM_GRAND.get(), WILL_TYPE);
        registration.registerSubtypeInterpreter(BMItems.RAW_WILL.get(), WILL_TYPE);
    }
}
