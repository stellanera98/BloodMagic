package wayoftime.bloodmagic.common.recipe;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.recipe.bloodaltar.BloodAltarRecipe;
import wayoftime.bloodmagic.common.recipe.bloodaltar.BloodAltarRecipeSerializer;
import wayoftime.bloodmagic.common.recipe.soulforge.SoulForgeRecipe;
import wayoftime.bloodmagic.common.recipe.soulforge.SoulForgeSerializer;
import wayoftime.bloodmagic.common.recipe.tiered.EnergyTieredRecipe;
import wayoftime.bloodmagic.common.recipe.tiered.EnergyTieredSerializer;
import wayoftime.bloodmagic.common.recipe.tiered.FluidTieredRecipe;
import wayoftime.bloodmagic.common.recipe.tiered.FluidTieredSerializer;

public class BMRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, BloodMagic.MODID);
    public static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, BloodMagic.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<FluidTieredRecipe>> FLUID_TIERED_SERIALIZER = SERIALIZERS.register("fluid_tiered", FluidTieredSerializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<EnergyTieredRecipe>> ENERGY_TIERED_SERIALIZER = SERIALIZERS.register("energy_tiered", EnergyTieredSerializer::new);

    public static final DeferredHolder<RecipeType<?>, RecipeType<SoulForgeRecipe>> SOUL_FORGE_TYPE = TYPES.register(SoulForgeRecipe.RECIPE_TYPE_NAME, () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(BloodMagic.MODID, SoulForgeRecipe.RECIPE_TYPE_NAME)));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<SoulForgeRecipe>> SOUL_FORGE_SERIALIZER = SERIALIZERS.register("soul_forge", SoulForgeSerializer::new);

    public static final DeferredHolder<RecipeType<?>, RecipeType<BloodAltarRecipe>> BLOOD_ALTAR_TYPE = TYPES.register(BloodAltarRecipe.RECIPE_TYPE_NAME, () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(BloodMagic.MODID, BloodAltarRecipe.RECIPE_TYPE_NAME)));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BloodAltarRecipe>> BLOOD_ALTAR_SERIALIZER = SERIALIZERS.register("blood_altar", BloodAltarRecipeSerializer::new);

    public static void register(IEventBus modBus) {
        SERIALIZERS.register(modBus);
        TYPES.register(modBus);
    }
}
