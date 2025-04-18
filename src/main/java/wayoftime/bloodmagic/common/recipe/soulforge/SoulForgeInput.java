package wayoftime.bloodmagic.common.recipe.soulforge;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.List;

public class SoulForgeInput implements RecipeInput {

    private final List<ItemStack> inputStacks;
    private final ItemStack gemStack;
    private final int gemIndex;

    public SoulForgeInput(List<ItemStack> items, ItemStack gemStack, int gemIndex) {
        this.inputStacks = items;
        this.gemStack = gemStack;
        this.gemIndex = gemIndex;
    }

    @Override
    public ItemStack getItem(int index) {
        return inputStacks.get(index);
    }

    public ItemStack getGem() {
        return this.gemStack;
    }

    public int getGemIndex() {
        return this.gemIndex;
    }

    @Override
    public int size() {
        return inputStacks.size();
    }
}
