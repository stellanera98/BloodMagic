package wayoftime.bloodmagic.common.living.effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.damagesource.DamageContainer;

import java.util.List;

public record DamageFlatIncreaseEffect(List<Float> amounts) implements DamageBasedEffect {
    public static final MapCodec<DamageFlatIncreaseEffect> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            Codec.FLOAT.listOf().fieldOf("amounts").forGetter(DamageFlatIncreaseEffect::amounts)
    ).apply(builder, DamageFlatIncreaseEffect::new));

    @Override
    public void apply(int level, Player wearer, DamageContainer damage) {
        damage.setNewDamage(damage.getNewDamage() + amounts().get(level));
    }

    @Override
    public MapCodec<? extends DamageBasedEffect> codec() {
        return CODEC;
    }
}
