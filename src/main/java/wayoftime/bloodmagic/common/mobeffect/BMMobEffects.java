package wayoftime.bloodmagic.common.mobeffect;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import wayoftime.bloodmagic.BloodMagic;

public class BMMobEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, BloodMagic.MODID);

    public static final DeferredHolder<MobEffect, BMEffect> SOUL_SNARE_RAW = EFFECTS.register("soul_snare_raw", () -> new BMEffect(MobEffectCategory.NEUTRAL, 0xFF_FF_FF));
    public static final DeferredHolder<MobEffect, BMEffect> SOUL_SNARE_CORROSIVE = EFFECTS.register("soul_snare_corrosive", () -> new BMEffect(MobEffectCategory.NEUTRAL, 0xFF_FF_FF));
    public static final DeferredHolder<MobEffect, BMEffect> SOUL_SNARE_DESTRUCTIVE = EFFECTS.register("soul_snare_destructive", () -> new BMEffect(MobEffectCategory.NEUTRAL, 0xFF_FF_FF));
    public static final DeferredHolder<MobEffect, BMEffect> SOUL_SNARE_STEADFAST = EFFECTS.register("soul_snare_steadfast", () -> new BMEffect(MobEffectCategory.NEUTRAL, 0xFF_FF_FF));
    public static final DeferredHolder<MobEffect, BMEffect> SOUL_SNARE_VENGEFUL = EFFECTS.register("soul_snare_vengeful", () -> new BMEffect(MobEffectCategory.NEUTRAL, 0xFF_FF_FF));

    public static void register(IEventBus modBus) {
        EFFECTS.register(modBus);
    }
}
