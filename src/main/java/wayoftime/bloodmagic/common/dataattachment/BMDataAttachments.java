package wayoftime.bloodmagic.common.dataattachment;

import com.mojang.serialization.Codec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import wayoftime.bloodmagic.BloodMagic;

public class BMDataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, BloodMagic.MODID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Double>> INCENSE = ATTACHMENT_TYPES.register(
            "incense", () -> AttachmentType.builder(() -> 0D).serialize(Codec.DOUBLE).build()
    );

    public static void register(IEventBus modBus) {
        ATTACHMENT_TYPES.register(modBus);
    }
}
