package wayoftime.bloodmagic.common.living.effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import wayoftime.bloodmagic.common.living.LivingHelper;
import wayoftime.bloodmagic.common.living.LivingUpgrade;
import wayoftime.bloodmagic.common.registry.BMRegistries;

public record DamageBasedExpGain(Holder<LivingUpgrade> upgrade, boolean pre) implements DamageBasedEffect {
    public static final MapCodec<DamageBasedExpGain> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            RegistryFixedCodec.create(BMRegistries.Keys.LIVING_UPGRADES).fieldOf("upgrade").forGetter(DamageBasedExpGain::upgrade),
            Codec.BOOL.fieldOf("pre").forGetter(DamageBasedExpGain::pre)
    ).apply(builder, DamageBasedExpGain::new));

    public static final boolean PRE = true;
    public static final boolean POST = false;

    @Override
    public void apply(int level, Player wearer, DamageContainer damage) {
        if (pre) {
            float afterArmour = damage.getOriginalDamage() * 15F / 25F;
            LivingHelper.applyExp(wearer, upgrade, afterArmour);
        } else {
            LivingHelper.applyExp(wearer, upgrade, damage.getNewDamage());
        }
    }

    @Override
    public MapCodec<? extends DamageBasedEffect> codec() {
        return CODEC;
    }
}
