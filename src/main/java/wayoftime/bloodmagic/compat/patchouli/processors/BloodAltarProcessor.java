package wayoftime.bloodmagic.compat.patchouli.processors;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.recipe.bloodaltar.BloodAltarRecipe;

import java.util.Arrays;
import java.util.Optional;

public class BloodAltarProcessor implements IComponentProcessor {

    private BloodAltarRecipe recipe;

    @Override
    public void setup(Level level, IVariableProvider variables) {
        ResourceLocation recipeId = ResourceLocation.parse(variables.get("recipe", level.registryAccess()).asString());
        Optional<RecipeHolder<?>> holder = ServerLifecycleHooks.getCurrentServer().getRecipeManager().byKey(recipeId);
        if (holder.isPresent()) {
            BloodMagic.LOGGER.info("holder");
            this.recipe = (BloodAltarRecipe) holder.get().value();
        }
    }

    @Override
    public IVariable process(Level level, String key) {
        if (recipe == null) {
            BloodMagic.LOGGER.info("recipe is null, cant process");
            return null;
        }
        return switch (key) {
            case "input" -> IVariable.wrapList(Arrays.stream(recipe.getInput().getItems()).map(in -> IVariable.from(in, level.registryAccess())).toList(), level.registryAccess());
            case "output" -> IVariable.from(recipe.getResult(), level.registryAccess());
            case "tier" -> IVariable.wrap(recipe.minTier+1, level.registryAccess()); // internal tiers are 0 based
            case "lp" -> IVariable.wrap(recipe.totalBlood, level.registryAccess());
            default -> null;
        };
    }
}
