package wayoftime.bloodmagic.common.living.effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.damagesource.DamageContainer;

import java.util.List;

public record DamageReductionEffect(List<Float> amounts) implements DamageBasedEffect {
    public static final MapCodec<DamageReductionEffect> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            Codec.FLOAT.listOf().fieldOf("amounts").forGetter(DamageReductionEffect::amounts)
    ).apply(builder, DamageReductionEffect::new));

    @Override
    public void apply(int level, Player wearer, DamageContainer damage) {
        damage.setNewDamage(damage.getNewDamage() * (1 - amounts().get(level)));
    }

    @Override
    public MapCodec<? extends DamageBasedEffect> codec() {
        return CODEC;
    }
}
