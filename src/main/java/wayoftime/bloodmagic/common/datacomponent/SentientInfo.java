package wayoftime.bloodmagic.common.datacomponent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record SentientInfo(EnumWillType type, double amount, int tier, Component gemName) {
    public static final Codec<SentientInfo> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            EnumWillType.CODEC.fieldOf("type").forGetter(SentientInfo::type),
            Codec.DOUBLE.fieldOf("amount").forGetter(SentientInfo::amount),
            Codec.INT.fieldOf("tier").forGetter(SentientInfo::tier),
            ComponentSerialization.FLAT_CODEC.fieldOf("name").forGetter(SentientInfo::gemName)
    ).apply(builder, SentientInfo::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, SentientInfo> STREAM_CODEC = StreamCodec.composite(
            EnumWillType.STREAM_CODEC, SentientInfo::type,
            ByteBufCodecs.DOUBLE, SentientInfo::amount,
            ByteBufCodecs.INT, SentientInfo::tier,
            ComponentSerialization.STREAM_CODEC, SentientInfo::gemName,
            SentientInfo::new
    );

    public static final SentientInfo NONE = new SentientInfo(EnumWillType.RAW, 0, 0, Component.empty());
}
