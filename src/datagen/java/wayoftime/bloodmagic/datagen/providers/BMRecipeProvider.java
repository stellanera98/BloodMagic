package wayoftime.bloodmagic.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.block.BMBlocks;
import wayoftime.bloodmagic.datagen.builders.recipe.TieredRecipeBuilder;

import java.util.concurrent.CompletableFuture;

public class BMRecipeProvider extends RecipeProvider {
    public BMRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output, HolderLookup.Provider holderLookup) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BMBlocks.BLOOD_TANK)
                .pattern("RBR")
                .pattern("G G")
                .pattern("RRR")
                .define('R', Blocks.POLISHED_DEEPSLATE)
                .define('B', Blocks.POLISHED_GRANITE)
                .define('G', Blocks.GLASS)
                .unlockedBy("has_bloodstone", has(Blocks.POLISHED_GRANITE))
                .unlockedBy("has_blank_rune", has(Blocks.POLISHED_DEEPSLATE))
                .unlockedBy("has_glass", has(Blocks.GLASS))
                .save(output, ResourceLocation.fromNamespaceAndPath(BloodMagic.MODID, "initial_blood_tank"));

        TieredRecipeBuilder.fluid(RecipeCategory.MISC, BMBlocks.BLOOD_TANK)
                .pattern("RBR")
                .pattern("T T")
                .pattern("RRR")
                .define('R', Blocks.POLISHED_DEEPSLATE)
                .define('B', Blocks.POLISHED_GRANITE)
                .define('T', BMBlocks.BLOOD_TANK)
                .primary(3)
                .secondary(5)
                .unlockedBy("has_bloodstone", has(Blocks.POLISHED_GRANITE))
                .unlockedBy("has_blank_rune", has(Blocks.POLISHED_DEEPSLATE))
                .unlockedBy("has_blood_tank", has(BMBlocks.BLOOD_TANK))
                .save(output, ResourceLocation.fromNamespaceAndPath(BloodMagic.MODID, "blood_tank_tiered"));
    }
}
