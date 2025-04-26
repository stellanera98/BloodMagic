package wayoftime.bloodmagic.common.ingredient;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;
import net.neoforged.neoforge.common.crafting.IngredientType;
import wayoftime.bloodmagic.common.datamap.BMDataMaps;
import wayoftime.bloodmagic.common.datamap.BloodOrb;
import wayoftime.bloodmagic.common.item.BMItems;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public record BloodOrbIngredient(int tier) implements ICustomIngredient {

    private static List<Item> orbs = List.of(BMItems.ORB_WEAK.get(), BMItems.ORB_APPRENTICE.get(), BMItems.ORB_MAGICIAN.get(), BMItems.ORB_MASTER.get(), BMItems.ORB_ARCHMAGE.get(), BMItems.ORB_TRANSCENDENT.get());
    public static final MapCodec<BloodOrbIngredient> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codec.INT.fieldOf("minTier").forGetter(BloodOrbIngredient::tier)
            ).apply(instance, BloodOrbIngredient::new)
    );

    public static final StreamCodec<ByteBuf, BloodOrbIngredient> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, BloodOrbIngredient::tier,
            BloodOrbIngredient::new
    );

    @Override
    public boolean test(ItemStack stack) {
        BloodOrb orb = stack.getItemHolder().getData(BMDataMaps.BLOOD_ORBS);
        return (orb == null || orb.tier() < this.tier);
    }

    @Override
    public Stream<ItemStack> getItems() {
        return BuiltInRegistries.ITEM.getDataMap(BMDataMaps.BLOOD_ORBS).entrySet().stream()
                .map(entry -> entry.getValue().tier() < this.tier ? null : entry.getKey())
                .map(BuiltInRegistries.ITEM::get).filter(Objects::nonNull)
                .map(ItemStack::new);
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public IngredientType<?> getType() {
        return BMIngredients.OBR_INGREDIENT.get();
    }
}
