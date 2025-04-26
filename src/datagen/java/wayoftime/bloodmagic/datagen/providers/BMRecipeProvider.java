package wayoftime.bloodmagic.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.block.BMBlocks;
import wayoftime.bloodmagic.common.fluid.BMFluids;
import wayoftime.bloodmagic.common.ingredient.BloodOrbIngredient;
import wayoftime.bloodmagic.common.item.BMItems;
import wayoftime.bloodmagic.common.tag.BMTags;
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

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BMBlocks.HELLFIRE_FORGE)
                .pattern("i i")
                .pattern("sSs")
                .pattern("sIs")
                .define('i', Tags.Items.INGOTS_IRON)
                .define('I', Tags.Items.STORAGE_BLOCKS_IRON)
                .define('s', Tags.Items.STONES)
                .define('S', BMItems.SLATE_BLANK.get())
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
                .unlockedBy("has_blank_slate", has(BMItems.SLATE_BLANK.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BMBlocks.RUNE_BLANK)
                .pattern("sSs")
                .pattern("sos")
                .pattern("sss")
                .define('s', Tags.Items.STONES)
                .define('S', BMItems.SLATE_BLANK.get())
                .define('o', new BloodOrbIngredient(0).toVanilla()) //BMItems.ORB_WEAK.get())
                .unlockedBy("has_weak_orb", has(BMItems.ORB_WEAK.get()))
                .unlockedBy("has_blank_slate", has(BMItems.SLATE_BLANK.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BMItems.SACRIFICIAL_DAGGER.get())
                .pattern("ggg")
                .pattern(" Gg")
                .pattern("i g")
                .define('g', Tags.Items.GLASS_BLOCKS)
                .define('G', Tags.Items.INGOTS_GOLD)
                .define('i', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
                .unlockedBy("has_gold", has(Tags.Items.INGOTS_GOLD))
                .unlockedBy("has_glass", has(Tags.Items.GLASS_BLOCKS))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BMBlocks.BLOOD_ALTAR)
                .pattern("s s")
                .pattern("sfs")
                .pattern("ggg")
                .define('s', Tags.Items.STONES)
                .define('g', Tags.Items.INGOTS_GOLD)
                .define('f', Blocks.FURNACE)
                .unlockedBy("has_furnace", has(Blocks.FURNACE))
                .unlockedBy("has_gold", has(Tags.Items.INGOTS_GOLD))
                .save(output);

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
                .minTier(0)
                .bloodNeeded(2000)
                .consumption(5)
                .drain(5)
                .from(Tags.Items.GEMS_DIAMOND)
                .save(output);

        AltarRecipeBuilder.build(BMItems.ORB_APPRENTICE.get())
                .minTier(1)
                .bloodNeeded(5000)
                .consumption(5)
                .drain(5)
                .from(Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .save(output);

        AltarRecipeBuilder.build(BMItems.ORB_MAGICIAN.get())
                .minTier(2)
                .bloodNeeded(25000)
                .consumption(20)
                .drain(20)
                .from(Tags.Items.STORAGE_BLOCKS_GOLD)
                .save(output);

        AltarRecipeBuilder.build(BMItems.ORB_MASTER.get())
                .minTier(3)
                .bloodNeeded(40000)
                .consumption(30)
                .drain(30)
                .from(BMItems.BLOOD_SHARD_WEAK.get())
                .save(output);

        AltarRecipeBuilder.build(BMItems.ORB_ARCHMAGE.get())
                .minTier(4)
                .bloodNeeded(80000)
                .consumption(50)
                .drain(100)
                .from(BMTags.Items.STORAGE_BLOCKS_HELLFORGED)
                .save(output);

        AltarRecipeBuilder.build(BMItems.ORB_TRANSCENDENT.get())
                .minTier(5)
                .bloodNeeded(200000)
                .consumption(100)
                .drain(200)
                .from(BMTags.Items.CRYSTAL_CLUSTER)
                .save(output);

        AltarRecipeBuilder.build(BMItems.SLATE_BLANK.get())
                .minTier(0)
                .bloodNeeded(1000)
                .consumption(5)
                .drain(5)
                .from(Tags.Items.STONES)
                .save(output);

        AltarRecipeBuilder.build(BMItems.SLATE_REINFORCED.get())
                .minTier(1)
                .bloodNeeded(2000)
                .consumption(5)
                .drain(5)
                .from(BMItems.SLATE_BLANK.get())
                .save(output);

        AltarRecipeBuilder.build(BMItems.SLATE_IMBUED.get())
                .minTier(2)
                .bloodNeeded(5000)
                .consumption(15)
                .drain(10)
                .from(BMItems.SLATE_REINFORCED.get())
                .save(output);

        AltarRecipeBuilder.build(BMItems.SLATE_DEMONIC.get())
                .minTier(3)
                .bloodNeeded(15000)
                .consumption(20)
                .drain(20)
                .from(BMItems.SLATE_IMBUED.get())
                .save(output);

        AltarRecipeBuilder.build(BMItems.SLATE_ETHEREAL.get())
                .minTier(4)
                .bloodNeeded(30000)
                .consumption(40)
                .drain(100)
                .from(BMItems.SLATE_DEMONIC.get())
                .save(output);

        AltarRecipeBuilder.build(BMFluids.LIFE_ESSENCE_BUCKET.get())
                .minTier(0)
                .bloodNeeded(1000)
                .consumption(5)
                .drain(0)
                .from(Items.BUCKET)
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, BMBlocks.BLOODSTONE, 8)
                .requires(BMItems.BLOOD_SHARD_WEAK.get())
                .requires(Tags.Items.STONES)
                .unlockedBy("has_weak_shard", has(BMItems.BLOOD_SHARD_WEAK.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BMBlocks.BLOODSTONE_BRICK, 4)
                .pattern("bb")
                .pattern("bb")
                .define('b', BMBlocks.BLOODSTONE)
                .unlockedBy("has_bloodstone", has(BMBlocks.BLOODSTONE))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, BMItems.INGOT_HELLFORGED.get(), 9)
                .requires(BMBlocks.HELLFORGED_BLOCK)
                .unlockedBy("has_hellforged_ingot", has(BMItems.INGOT_HELLFORGED.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, BMBlocks.HELLFORGED_BLOCK)
                .requires(BMItems.INGOT_HELLFORGED.get(), 9)
                .unlockedBy("has_hellforged_block", has(BMBlocks.HELLFORGED_BLOCK))
                .save(output);
    }

    private static ResourceLocation bm(String path) {
        return ResourceLocation.fromNamespaceAndPath(BloodMagic.MODID, path);
    }
}
