package wayoftime.bloodmagic.common.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public record AltarTier(int tier, List<AltarComponent> components) implements Comparable<AltarTier> {
    public static final Codec<AltarTier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("tier").forGetter(AltarTier::tier),
            Codec.list(AltarComponent.CODEC).fieldOf("components").forGetter(AltarTier::components)
    ).apply(instance, AltarTier::new));

    @Override
    public int compareTo(@NotNull AltarTier altarTier) {
        return Integer.compare(this.tier, altarTier.tier);
    }
}
