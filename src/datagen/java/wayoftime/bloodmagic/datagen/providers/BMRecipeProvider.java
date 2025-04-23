package wayoftime.bloodmagic.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.block.BMBlocks;
import wayoftime.bloodmagic.common.item.BMItems;
import wayoftime.bloodmagic.datagen.builders.recipe.AltarRecipeBuilder;
import wayoftime.bloodmagic.datagen.builders.recipe.SoulForgeRecipeBuilder;
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
                .save(output,bm("initial_blood_tank"));

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
                .save(output, bm("blood_tank_tiered"));

        SoulForgeRecipeBuilder.build(BMItems.SOUL_GEM_PETTY.get())
                .minWill(1)
                .drain(1)
                .requires(Tags.Items.GEMS_LAPIS)
                .requires(Tags.Items.DUSTS_REDSTONE)
                .requires(Tags.Items.GLASS_BLOCKS)
                .requires(Tags.Items.INGOTS_GOLD)
                .save(output);

        SoulForgeRecipeBuilder.build(BMItems.SOUL_GEM_LESSER.get())
                .minWill(60)
                .drain(20)
                .requires(BMItems.SOUL_GEM_PETTY.get())
                .requires(Tags.Items.GEMS_DIAMOND)
                .requires(Tags.Items.STORAGE_BLOCKS_LAPIS)
                .requires(Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .save(output);

        SoulForgeRecipeBuilder.build(BMItems.SOUL_GEM_COMMON.get())
                .minWill(240)
                .drain(50)
                .requires(BMItems.SOUL_GEM_LESSER.get())
                .requires(Tags.Items.GEMS_DIAMOND)
                .requires(Tags.Items.STORAGE_BLOCKS_GOLD)
                .requires(BMItems.ORB_MAGICIAN.get()) // T3 Slate
                .save(output);

        SoulForgeRecipeBuilder.build(BMItems.SOUL_GEM_GREATER.get())
                .minWill(1000)
                .drain(100)
                .requires(BMItems.SOUL_GEM_COMMON.get())
                .requires(BMItems.ORB_MASTER.get()) // T4 slate
                .requires(Tags.Items.GEMS_AMETHYST) // Demon Will Crystal
                .requires(BMItems.RAW_WILL.get()) // Weak Blood Shard
                .save(output);

        AltarRecipeBuilder.build(BMItems.ORB_WEAK.get())
                .minTier(1)
                .bloodNeeded(2000)
                .consumption(5)
                .drain(1)
                .from(Tags.Items.GEMS_DIAMOND)
                .save(output);
    }

    private static ResourceLocation bm(String path) {
        return ResourceLocation.fromNamespaceAndPath(BloodMagic.MODID, path);
    }
}
