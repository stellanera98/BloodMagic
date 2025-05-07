package wayoftime.bloodmagic.common.living.effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public record RepairRandomArmourItemEffect(List<Integer> amounts) implements StandaloneEffect {
    public static final MapCodec<RepairRandomArmourItemEffect> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            Codec.INT.listOf().fieldOf("amounts").forGetter(RepairRandomArmourItemEffect::amounts)
    ).apply(builder, RepairRandomArmourItemEffect::new));

    @Override
    public void apply(int level, Player wearer) {
        ItemStack winner = wearer.getInventory().getArmor(wearer.level().random.nextInt(4));
        int remove = Math.max(winner.getDamageValue(), amounts.get(level));
        if (remove > 0) {
            winner.setDamageValue(winner.getDamageValue() - remove);
        }
    }

    @Override
    public MapCodec<? extends StandaloneEffect> codec() {
        return CODEC;
    }
}
