package wayoftime.bloodmagic.common.ingredient;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.crafting.IngredientType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import wayoftime.bloodmagic.BloodMagic;

public class BMIngredients {

    public static final DeferredRegister<IngredientType<?>> INGREDIENT_TYPES = DeferredRegister.create(NeoForgeRegistries.INGREDIENT_TYPES, BloodMagic.MODID);

    public static final DeferredHolder<IngredientType<?>, IngredientType<BloodOrbIngredient>> OBR_INGREDIENT = INGREDIENT_TYPES.register("blood_orb", () -> new IngredientType<>(BloodOrbIngredient.CODEC, BloodOrbIngredient.STREAM_CODEC));

    public static void register(IEventBus modBus) {
        INGREDIENT_TYPES.register(modBus);
    }
}
