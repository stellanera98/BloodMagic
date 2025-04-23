package wayoftime.bloodmagic.datagen.builders.recipe;

import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import wayoftime.bloodmagic.common.recipe.soulforge.SoulForgeRecipe;
import wayoftime.bloodmagic.util.DemonWillType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SoulForgeRecipeBuilder extends BaseRecipeBuilder {

    protected double minWill;
    protected double drainedWill;
    protected List<Ingredient> ingredients = new ArrayList<>();
    protected boolean requireWillType = false;
    protected Optional<DemonWillType> willType = Optional.empty();

    protected SoulForgeRecipeBuilder(ItemStack result) {
        super(result);
    }

    public static SoulForgeRecipeBuilder build(ItemLike result) {
        return new SoulForgeRecipeBuilder(new ItemStack(result));
    }

    public SoulForgeRecipeBuilder requires(TagKey<Item> tag) {
        return this.requires(Ingredient.of(tag));
    }

    public SoulForgeRecipeBuilder requires(ItemLike item) {
        return this.requires(item, 1);
    }

    public SoulForgeRecipeBuilder requires(ItemLike item, int quantity) {
        this.requires(Ingredient.of(item), quantity);
        return this;
    }

    public SoulForgeRecipeBuilder requires(Ingredient ingredient) {
        return this.requires(ingredient, 1);
    }

    public SoulForgeRecipeBuilder requires(Ingredient ingredient, int quantity) {
        for (int i = 0; i < quantity; i++) {
            this.ingredients.add(ingredient);
        }

        return this;
    }

    public SoulForgeRecipeBuilder minWill(double minWill) {
        this.minWill = minWill;
        return this;
    }

    public SoulForgeRecipeBuilder drain(double drain) {
        this.drainedWill = drain;
        return this;
    }

    public SoulForgeRecipeBuilder requiredWillType(DemonWillType type) {
        this.willType = Optional.of(type);
        return this;
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation id) {
        Advancement.Builder advBuilder = getBuilder(output, id);
        SoulForgeRecipe recipe = new SoulForgeRecipe(minWill, drainedWill, ingredients, result, willType);
        output.accept(id.withPrefix("soul_forge/"), recipe, advBuilder.build(advancementId(id, "soulforge")));
    }
}
