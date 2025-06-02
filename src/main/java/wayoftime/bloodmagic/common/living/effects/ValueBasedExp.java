package wayoftime.bloodmagic.common.living.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import wayoftime.bloodmagic.common.living.LivingHelper;
import wayoftime.bloodmagic.common.living.LivingUpgrade;
import wayoftime.bloodmagic.common.living.LivingValueEffect;

public record ValueBasedExp(Holder<LivingUpgrade> upgrade) implements LivingValueEffect {
    public static final MapCodec<ValueBasedExp> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            LivingUpgrade.HOLDER_CODEC.fieldOf("upgrade").forGetter(ValueBasedExp::upgrade)
    ).apply(builder, ValueBasedExp::new));

    @Override
    public float process(int level, LootContext lootContext, float value) {
        Player player = (Player) lootContext.getParam(LootContextParams.THIS_ENTITY);
        LivingHelper.applyExp(player, upgrade, value);
        return 0;
    }

    @Override
    public MapCodec<? extends LivingValueEffect> codec() {
        return CODEC;
    }
}
