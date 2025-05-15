package wayoftime.bloodmagic.common.datacomponent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import wayoftime.bloodmagic.common.living.LivingUpgrade;
import wayoftime.bloodmagic.common.registry.BMRegistries;

public record StoredUpgrade(Holder<LivingUpgrade> upgrade, float exp) {
    public static final Codec<StoredUpgrade> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            RegistryFixedCodec.create(BMRegistries.Keys.LIVING_UPGRADES).fieldOf("upgrade").forGetter(StoredUpgrade::upgrade),
            Codec.FLOAT.fieldOf("exp").forGetter(StoredUpgrade::exp)
    ).apply(builder, StoredUpgrade::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, StoredUpgrade> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.holderRegistry(BMRegistries.Keys.LIVING_UPGRADES), StoredUpgrade::upgrade,
            ByteBufCodecs.FLOAT, StoredUpgrade::exp,
            StoredUpgrade::new
    );
}
