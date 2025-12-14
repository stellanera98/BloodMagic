package wayoftime.bloodmagic.api;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.sigil.SigilEffect;

public class BMIdentifiers {
    public static class ImperfectRituals {
        public static final ResourceLocation DAY_RITUAL = bm("day");
        public static final ResourceLocation NIGHT_RITUAL = bm("night");
        public static final ResourceLocation RESISTANCE_RITUAL = bm("resistance");
        public static final ResourceLocation ZOMBIE_RITUAL = bm("zombie");
    }

    public static class Sigils {
        public static final ResourceLocation DIVINATION = bm("divination");
        public static final ResourceLocation SEER = bm("seer");
        public static final ResourceLocation LAVA = bm("lava");
        public static final ResourceLocation WATER = bm("water");
        public static final ResourceLocation VOID = bm("void");
        public static final ResourceLocation MINER = bm("miner");
    }

    public static class ItemProperties {
        public static final ResourceLocation DEMON_WILL_TYPE = bm("will_type");
        public static final ResourceLocation HAS_INCENSE = bm("has_incense");
        public static final ResourceLocation IS_SIGIL_ACTIVE = bm("is_sigil_active");
    }

    public static class RegistryKeys {
        public static final ResourceKey<Registry<MapCodec<? extends SigilEffect>>> SIGIL_EFFECT_TYPE = ResourceKey.createRegistryKey(bm("sigil_effect_type"));
        public static final ResourceKey<Registry<SigilEffect>> SIGIL_EFFECTS = ResourceKey.createRegistryKey(bm("sigil_effect"));
    }

    // TODO move all the stuff here, including from Datagen

    private static ResourceLocation bm(String path) {
        return ResourceLocation.fromNamespaceAndPath(BloodMagic.MODID, path);
    }
}
