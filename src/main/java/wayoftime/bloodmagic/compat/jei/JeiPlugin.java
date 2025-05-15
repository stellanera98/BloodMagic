package wayoftime.bloodmagic.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.block.BMBlocks;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.common.datacomponent.StoredUpgrade;
import wayoftime.bloodmagic.common.item.BMItems;
import wayoftime.bloodmagic.common.recipe.BMRecipes;
import wayoftime.bloodmagic.compat.jei.recipe.ARCFurnaceCategory;
import wayoftime.bloodmagic.compat.jei.recipe.ARCRecipeCategory;
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
        registration.addRecipeCategories(new ARCRecipeCategory(helper));
        registration.addRecipeCategories(new ARCFurnaceCategory<SmeltingRecipe>(helper) {
            @Override
            public mezz.jei.api.recipe.RecipeType<SmeltingRecipe> getRecipeType() {
                return ARCFurnaceCategory.SMELTING;
            }
        });
        registration.addRecipeCategories(new ARCFurnaceCategory<BlastingRecipe>(helper) {
            @Override
            public mezz.jei.api.recipe.RecipeType<BlastingRecipe> getRecipeType() {
                return ARCFurnaceCategory.BLASTING;
            }
        });
        registration.addRecipeCategories(new ARCFurnaceCategory<SmokingRecipe>(helper) {
            @Override
            public mezz.jei.api.recipe.RecipeType<SmokingRecipe> getRecipeType() {
                return ARCFurnaceCategory.SMOKING;
            }
        });
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(BMBlocks.BLOOD_ALTAR), AltarRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(BMBlocks.HELLFIRE_FORGE), SoulForgeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(BMBlocks.ARC), ARCRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(BMBlocks.ARC), ARCFurnaceCategory.SMELTING);
        registration.addRecipeCatalyst(new ItemStack(BMBlocks.ARC), ARCFurnaceCategory.BLASTING);
        registration.addRecipeCatalyst(new ItemStack(BMBlocks.ARC), ARCFurnaceCategory.SMOKING);
    }

    private static <I extends RecipeInput, T extends Recipe<I>> List<T> getRecipes(RecipeManager karen, RecipeType<T> type) {
        return karen.getAllRecipesFor(type).stream().map(RecipeHolder::value).toList();
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager karen = Minecraft.getInstance().level.getRecipeManager();

        registration.addRecipes(AltarRecipeCategory.TYPE, getRecipes(karen, BMRecipes.BLOOD_ALTAR_TYPE.get()));
        registration.addRecipes(SoulForgeCategory.TYPE, getRecipes(karen, BMRecipes.SOUL_FORGE_TYPE.get()));
        registration.addRecipes(ARCRecipeCategory.TYPE, getRecipes(karen, BMRecipes.ALCHEMICAL_REACTION_CHAMBER_TYPE.get()));

        registration.addRecipes(ARCFurnaceCategory.SMELTING, getRecipes(karen, RecipeType.SMELTING));
        registration.addRecipes(ARCFurnaceCategory.BLASTING, getRecipes(karen, RecipeType.BLASTING));
        registration.addRecipes(ARCFurnaceCategory.SMOKING, getRecipes(karen, RecipeType.SMOKING));

        registration.addItemStackInfo(new ItemStack(BMBlocks.BLOOD_TANK), Component.translatable("jei.bloodmagic.info.blood_tank"));
    }

    private static final IIngredientSubtypeInterpreter<ItemStack> WILL_TYPE = (stack, context) -> {
        DemonWillType type = stack.get(BMDataComponents.DEMON_WILL_TYPE);
        return type == null ? IIngredientSubtypeInterpreter.NONE : type.getSerializedName();
    };

    private static final IIngredientSubtypeInterpreter<ItemStack> LIVING_UPGRADE = (stack, context) -> {
        StoredUpgrade upgrade = stack.get(BMDataComponents.STORED_UPGRADE);
        return upgrade == null ? "empty" : upgrade.upgrade().getRegisteredName();
    };

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(BMItems.SOUL_GEM_PETTY.get(), WILL_TYPE);
        registration.registerSubtypeInterpreter(BMItems.SOUL_GEM_LESSER.get(), WILL_TYPE);
        registration.registerSubtypeInterpreter(BMItems.SOUL_GEM_COMMON.get(), WILL_TYPE);
        registration.registerSubtypeInterpreter(BMItems.SOUL_GEM_GREATER.get(), WILL_TYPE);
        registration.registerSubtypeInterpreter(BMItems.SOUL_GEM_GRAND.get(), WILL_TYPE);
        registration.registerSubtypeInterpreter(BMItems.RAW_WILL.get(), WILL_TYPE);

        registration.registerSubtypeInterpreter(BMItems.UPGRADE_TOME.get(), LIVING_UPGRADE);
    }
}
