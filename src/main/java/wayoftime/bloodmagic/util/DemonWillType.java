package wayoftime.bloodmagic.util;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import org.apache.commons.lang3.StringUtils;

public enum DemonWillType implements StringRepresentable {

    DEFAULT,
    VENGEFUL,
    CORROSIVE,
    STEADFAST,
    DESTRUCTIVE;

    @Override
    public String getSerializedName() {
        return this.toString();
    }

    public String toLower() {
        return this.toString().toLowerCase();
    }

    public String toCapitalized() {
        return StringUtils.capitalize(this.toLower());
    }

    public static final Codec<DemonWillType> CODEC = StringRepresentable.fromEnum(DemonWillType::values);

    public static final StreamCodec<ByteBuf, DemonWillType> STREAM_CODEC = ByteBufCodecs.STRING_UTF8.map(DemonWillType::valueOf, DemonWillType::getSerializedName);
}
