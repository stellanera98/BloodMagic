package wayoftime.bloodmagic.datagen.content;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidType;
import wayoftime.bloodmagic.api.BMIdentifiers.Sigils;
import wayoftime.bloodmagic.api.BMIdentifiers.RegistryKeys;
import wayoftime.bloodmagic.common.sigil.*;

import java.util.Optional;
import java.util.function.BiConsumer;

public class SigilData {
    public static void effects(BootstrapContext<SigilEffect> context) {
        context.register(key(Sigils.DIVINATION), new DivinationEffect(false));
        context.register(key(Sigils.SEER), new DivinationEffect(true));
        context.register(key(Sigils.LAVA), new FluidPlaceEffect(Fluids.LAVA.builtInRegistryHolder(), 10 * FluidType.BUCKET_VOLUME));
        context.register(key(Sigils.WATER), new FluidPlaceEffect(Fluids.WATER.builtInRegistryHolder(), 10 * FluidType.BUCKET_VOLUME));
        context.register(key(Sigils.VOID), new FluidRemoveEffect(1 * FluidType.BUCKET_VOLUME));
        context.register(key(Sigils.MINER), new ApplyPotionEffect(MobEffects.DIG_SPEED, 0, 40, Optional.of(2), Optional.of(600)));
    }

    private static ResourceKey<SigilEffect> key(ResourceLocation id) {
        return ResourceKey.create(RegistryKeys.SIGIL_EFFECTS, id);
    }

    public static void translations(BiConsumer<String, String> translator) {
        translator.accept("tooltip.bloodmagic.sigil.divination", "Peer into the soul");
        translator.accept("tooltip.bloodmagic.sigil.seer", "When seeing all is not enough.");
        translator.accept("tooltip.bloodmagic.sigil.lava", "HOT! DO NOT EAT!");
        translator.accept("tooltip.bloodmagic.sigil.water", "Infinite water, anyone?");
        translator.accept("tooltip.bloodmagic.sigil.void", "Better than a Swiffer®!");
        translator.accept("tooltip.bloodmagic.sigil.miner", "Keep mining, and mining...");

        translator.accept("tooltip.bloodmagic.sigil.activated", "Activated");
        translator.accept("tooltip.bloodmagic.sigil.deactivated", "Deactivated");

        translator.accept("chat.bloodmagic.divination.current_essence", "Current Essence: %s");
        translator.accept("chat.bloodmagic.divination.other_network", "Peering into the Soul of %s");

        translator.accept("chat.bloodmagic.divination.altar.tier", "Current Tier: %s");
        translator.accept("chat.bloodmagic.divination.altar.essence", "Current Essence in main tank: %s");
        translator.accept("chat.bloodmagic.divination.altar.max_essence", "Main Essence tank capacity: %s");
    }
}
