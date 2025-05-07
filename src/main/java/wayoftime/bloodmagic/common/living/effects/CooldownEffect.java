package wayoftime.bloodmagic.common.living.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import wayoftime.bloodmagic.common.dataattachment.BMDataAttachments;

import java.util.Map;

public record CooldownEffect(ResourceLocation id) implements StandaloneEffect {
    public static final MapCodec<CooldownEffect> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(CooldownEffect::id)
    ).apply(builder, CooldownEffect::new));

    @Override
    public void apply(int level, Player wearer) {
        Map<ResourceLocation, Integer> map = wearer.getData(BMDataAttachments.LIVING_COOLDOWN.get());
        Integer got = map.get(id);
        int set = 1200; // setting non-existent IDs to 1min so max cd info doesnt need to be duplicated // TODO make configurable?
        if (got != null) {
            set = Math.max(0, got -1);
        }
        map.put(id, set);

        wearer.setData(BMDataAttachments.LIVING_COOLDOWN.get(), map);
    }

    @Override
    public MapCodec<? extends StandaloneEffect> codec() {
        return CODEC;
    }
}
