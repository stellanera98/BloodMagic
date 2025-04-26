package wayoftime.bloodmagic.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.block.BMBlocks;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.common.item.BMItems;
import wayoftime.bloodmagic.common.recipe.BMRecipes;
import wayoftime.bloodmagic.compat.jei.recipe.AltarRecipeCategory;
import wayoftime.bloodmagic.util.DemonWillType;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(BloodMagic.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new AltarRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(BMBlocks.BLOOD_ALTAR), AltarRecipeCategory.TYPE);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager karen = Minecraft.getInstance().level.getRecipeManager();
        registration.addRecipes(AltarRecipeCategory.TYPE, karen.getAllRecipesFor(BMRecipes.BLOOD_ALTAR_TYPE.get()).stream().map(RecipeHolder::value).toList());
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
