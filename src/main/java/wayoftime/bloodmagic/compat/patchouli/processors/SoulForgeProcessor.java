package wayoftime.bloodmagic.compat.patchouli.processors;

import net.minecraft.client.renderer.entity.layers.DolphinCarryingItemLayer;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.common.item.BMItems;
import wayoftime.bloodmagic.common.recipe.soulforge.SoulForgeRecipe;
import wayoftime.bloodmagic.util.DemonWillType;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SoulForgeProcessor implements IComponentProcessor {

    private SoulForgeRecipe recipe;

    @Override
    public void setup(Level level, IVariableProvider variables) {
        ResourceLocation recipeId = ResourceLocation.parse(variables.get("recipe", level.registryAccess()).asString());
        Optional<RecipeHolder<?>> holder = ServerLifecycleHooks.getCurrentServer().getRecipeManager().byKey(recipeId);
        if (holder.isPresent()) {
            this.recipe = (SoulForgeRecipe) holder.get().value();
        } else {
            BloodMagic.LOGGER.info("missing recipe {} for soul forge", recipeId);
        }
    }

    @Override
    public IVariable process(Level level, String key) {
        if (recipe == null) {
            return null;
        }

        RegistryAccess registries = level.registryAccess();

        if (key.startsWith("input")) {
            int idx = Integer.parseInt(key.substring(5));
            if (idx < recipe.ingredients.size()) {
                ItemStack[] stacks = recipe.ingredients.get(idx).getItems();
                List<IVariable> list = Arrays.stream(stacks).map(stack -> IVariable.from(stack, registries)).toList();
                return IVariable.wrapList(list, registries);
            } else {
                BloodMagic.LOGGER.info("got '{}' as key?", key);
                return null;
            }
        }

        return switch (key) {
            case "output" -> IVariable.from(recipe.getOutput(), registries);
            case "willrequired" -> IVariable.wrap(recipe.minWill, registries);
            case "willdrain" -> IVariable.wrap(recipe.usedWill, registries);
            case "will" -> {
                ItemStack gemStack = ItemStack.EMPTY;
                double minWill = recipe.minWill;
                if (minWill <= 64) {
                    gemStack = new ItemStack(BMItems.SOUL_GEM_PETTY);
                } else if (minWill <= 256) {
                    gemStack = new ItemStack(BMItems.SOUL_GEM_LESSER);
                } else if (minWill <= 1024) {
                    gemStack = new ItemStack(BMItems.SOUL_GEM_COMMON);
                } else if (minWill <= 4096) {
                    gemStack = new ItemStack(BMItems.SOUL_GEM_GREATER);
                } else if (minWill <= 16384) {
                    gemStack = new ItemStack(BMItems.SOUL_GEM_GRAND);
                }
                gemStack.set(BMDataComponents.DEMON_WILL_AMOUNT, minWill);
                DemonWillType type = DemonWillType.DEFAULT;
                Optional<DemonWillType> opt = recipe.willType;
                if (opt.isPresent()) {
                    type = opt.get();
                }
                gemStack.set(BMDataComponents.DEMON_WILL_TYPE, type);
                yield IVariable.from(gemStack, registries);
            }
            default -> null;
        };
    }
}
