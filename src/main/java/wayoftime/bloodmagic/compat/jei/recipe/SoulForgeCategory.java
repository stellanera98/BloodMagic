package wayoftime.bloodmagic.compat.jei.recipe;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.Nullable;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.block.BMBlocks;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.common.datamap.BMDataMaps;
import wayoftime.bloodmagic.common.item.BMItems;
import wayoftime.bloodmagic.common.recipe.soulforge.SoulForgeRecipe;
import wayoftime.bloodmagic.util.DemonWillType;

import java.util.*;


public class SoulForgeCategory implements IRecipeCategory<SoulForgeRecipe> {

    public static final RecipeType<SoulForgeRecipe> TYPE = RecipeType.create(BloodMagic.MODID, "soul_forge", SoulForgeRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final Map<ItemStack, Double> GEMS = new HashMap<>();
    public SoulForgeCategory(IGuiHelper helper) {
        icon = helper.createDrawableItemStack(new ItemStack(BMBlocks.HELLFIRE_FORGE));
        background = helper.createDrawable(ResourceLocation.fromNamespaceAndPath(BloodMagic.MODID, "gui/jei/soul_forge.png"), 0, 0, 100, 40);
        RegistryAccess registries = ServerLifecycleHooks.getCurrentServer().registryAccess();
        Registry<Item> itemRegistry = registries.registryOrThrow(Registries.ITEM);
        itemRegistry.getDataMap(BMDataMaps.SOUL_GEM_AMOUNTS).forEach((key, val) -> {
            GEMS.put(new ItemStack(itemRegistry.get(key)), val);
        });

    }

    @Override
    public RecipeType<SoulForgeRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.bloodmagic.soul_forge.category");
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
    public void setRecipe(IRecipeLayoutBuilder builder, SoulForgeRecipe recipe, IFocusGroup focuses) {
        Optional<DemonWillType> opt = recipe.willType;
        List<DemonWillType> types = opt.map(List::of).orElseGet(() -> List.of(DemonWillType.values()));

        List<ItemStack> validGems = new ArrayList<>();
        GEMS.forEach((stack, maxWill) -> {
            if (maxWill >= recipe.minWill) {
                for (DemonWillType type : types) {
                    ItemStack tmp = stack.copy();
                    tmp.set(BMDataComponents.DEMON_WILL_TYPE, type);
                    tmp.set(BMDataComponents.DEMON_WILL_AMOUNT, recipe.minWill);
                    validGems.add(tmp);
                    if (recipe.minWill < 16) {
                        ItemStack raw = new ItemStack(BMItems.RAW_WILL.get());
                        raw.set(BMDataComponents.DEMON_WILL_AMOUNT, recipe.minWill);
                        raw.set(BMDataComponents.DEMON_WILL_TYPE, type);
                        validGems.add(raw);
                    }
                }
            }
        });


        IRecipeSlotBuilder gem = builder.addSlot(RecipeIngredientRole.CATALYST, 43, 1);
        gem.addItemStacks(validGems);

        IRecipeSlotBuilder output = builder.addSlot(RecipeIngredientRole.OUTPUT, 74, 14);
        output.addItemStack(recipe.resultItem);

        for (int i = 0; i < recipe.ingredients.size(); i++) {
            int x = i % 2;
            int y = i / 2;
            IRecipeSlotBuilder input = builder.addSlot(RecipeIngredientRole.INPUT, x * 18 + 1, y * 18 + 1);
            input.addIngredients(recipe.ingredients.get(i));
        }
    }

    @Override
    public void getTooltip(ITooltipBuilder tooltip, SoulForgeRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (mouseX <= 60 && mouseX >= 40 && mouseY <= 34 && mouseY >= 21) {
            tooltip.add(Component.translatable("jei.bloodmagic.soul_forge.min_will"));
            tooltip.add(Component.translatable("jei.bloodmagic.soul_forge.drain_will"));
        }
    }
}
