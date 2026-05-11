package wayoftime.bloodmagic.common.datacomponent;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;
import java.util.function.IntFunction;

public enum EnumWillType implements StringRepresentable {

    RAW,
    CORROSIVE,
    DESTRUCTIVE,
    STEADFAST,
    VENGEFUL;

    public static final IntFunction<EnumWillType> BY_ID = ByIdMap.continuous(
            EnumWillType::ordinal,
            EnumWillType.values(),
            ByIdMap.OutOfBoundsStrategy.ZERO
    );

    public static final Codec<EnumWillType> CODEC = StringRepresentable.fromEnum(EnumWillType::values);
    public static final StreamCodec<ByteBuf, EnumWillType> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, EnumWillType::ordinal);

    public static EnumWillType[] types() {
        return new EnumWillType[] {RAW, CORROSIVE, DESTRUCTIVE, STEADFAST, VENGEFUL};
    }

    public Component asComponent() {
        return asComponent(this);
    }

    public static Component asComponent(EnumWillType type) {
        return switch (type) {
            case RAW -> Component.translatable("will.bloodmagic.raw").withStyle(ChatFormatting.AQUA);
            case CORROSIVE -> Component.translatable("will.bloodmagic.corrosive").withStyle(ChatFormatting.GREEN);
            case DESTRUCTIVE -> Component.translatable("will.bloodmagic.destructive").withStyle(ChatFormatting.YELLOW);
            case STEADFAST -> Component.translatable("will.bloodmagic.steadfast").withStyle(ChatFormatting.BLUE);
            case VENGEFUL -> Component.translatable("will.bloodmagic.vengeful").withStyle(ChatFormatting.RED);
        };
    }

    @Override
    public String getSerializedName() {
        return name().toLowerCase(Locale.ROOT);
    }

    public String toCapitalized() {
        return StringUtils.capitalize(this.getSerializedName());
    }
}
