package wayoftime.bloodmagic.common.living.effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import wayoftime.bloodmagic.common.dataattachment.BMDataAttachments;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public record ResetCooldownEffect(ResourceLocation id, List<Integer> amounts, Optional<StandaloneEffect> effect) implements StandaloneEffect {
    public static final MapCodec<ResetCooldownEffect> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(ResetCooldownEffect::id),
            Codec.INT.listOf().fieldOf("amounts").forGetter(ResetCooldownEffect::amounts),
            StandaloneEffect.CODEC.optionalFieldOf("effect").forGetter(ResetCooldownEffect::effect)
    ).apply(builder, ResetCooldownEffect::new));

    @Override
    public void apply(int level, Player wearer) {
        if (effect.isPresent()) {
            effect.get().apply(level, wearer);
        }
        Map<ResourceLocation, Integer> map = wearer.getData(BMDataAttachments.LIVING_COOLDOWN.get());
        map.remove(id);
        map.put(id, amounts.get(level));
        wearer.setData(BMDataAttachments.LIVING_COOLDOWN.get(), map);
    }

    @Override
    public MapCodec<? extends StandaloneEffect> codec() {
        return CODEC;
    }
}
