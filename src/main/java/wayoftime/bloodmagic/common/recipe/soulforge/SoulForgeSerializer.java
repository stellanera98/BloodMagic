package wayoftime.bloodmagic.common.recipe.soulforge;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import wayoftime.bloodmagic.util.DemonWillType;

public class SoulForgeSerializer implements RecipeSerializer<SoulForgeRecipe> {

    public static final MapCodec<SoulForgeRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.DOUBLE.fieldOf("minDrain").forGetter(SoulForgeRecipe::getMinWill),
            Codec.DOUBLE.fieldOf("drain").forGetter(SoulForgeRecipe::getDrain),
            Codec.list(Ingredient.CODEC_NONEMPTY).fieldOf("inputs").forGetter(SoulForgeRecipe::getCraftingIngredients),
            ItemStack.CODEC.fieldOf("output").forGetter(SoulForgeRecipe::getOutput),
            DemonWillType.CODEC.optionalFieldOf("willType").forGetter(SoulForgeRecipe::getWillType)
    ).apply(instance, SoulForgeRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, SoulForgeRecipe> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.DOUBLE, SoulForgeRecipe::getMinWill,
            ByteBufCodecs.DOUBLE, SoulForgeRecipe::getDrain,
            Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), SoulForgeRecipe::getCraftingIngredients,
            ItemStack.STREAM_CODEC, SoulForgeRecipe::getOutput,
            DemonWillType.STREAM_CODEC.apply(ByteBufCodecs::optional), SoulForgeRecipe::getWillType,
            SoulForgeRecipe::new
    );

    @Override
    public MapCodec<SoulForgeRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, SoulForgeRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
