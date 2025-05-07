package wayoftime.bloodmagic.common.living.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.common.living.LivingHelper;
import wayoftime.bloodmagic.common.living.LivingUpgrade;
import wayoftime.bloodmagic.common.registry.BMRegistries;

public record ItemDamageBasedExpGain(Holder<LivingUpgrade> upgrade) implements StandaloneEffect {
    public static final MapCodec<ItemDamageBasedExpGain> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            RegistryFixedCodec.create(BMRegistries.Keys.LIVING_UPGRADES).fieldOf("upgrade").forGetter(ItemDamageBasedExpGain::upgrade)
    ).apply(builder, ItemDamageBasedExpGain::new));

    @Override
    public void apply(int level, Player wearer) {
        ItemStack chestStack = LivingHelper.getChest(wearer);
        if (chestStack.has(BMDataComponents.PREVIOUS_DAMAGE)) {
            Integer prev = chestStack.get(BMDataComponents.PREVIOUS_DAMAGE);
            int delta = prev - chestStack.getDamageValue();
            if (delta > 0) {
                LivingHelper.applyExp(wearer, upgrade, delta);
            }
            if (delta != 0) {
                chestStack.set(BMDataComponents.PREVIOUS_DAMAGE, chestStack.getDamageValue());
            }
        } else {
            chestStack.set(BMDataComponents.PREVIOUS_DAMAGE, chestStack.getDamageValue());
        }
    }

    @Override
    public MapCodec<? extends StandaloneEffect> codec() {
        return CODEC;
    }
}
