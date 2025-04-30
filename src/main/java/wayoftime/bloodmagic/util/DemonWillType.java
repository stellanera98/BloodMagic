package wayoftime.bloodmagic.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import org.apache.commons.lang3.StringUtils;

public enum DemonWillType implements StringRepresentable {

    DEFAULT("default"),
    CORROSIVE("corrosive"),
    DESTRUCTIVE("destructive"),
    STEADFAST("steadfast"),
    VENGEFUL("vengeful");

    private final String name;
    DemonWillType(String name) {
        this.name = name;
    }

    public String toCapitalized() {
        return StringUtils.capitalize(this.name);
    }

    public static final Codec<DemonWillType> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("will_type").forGetter(DemonWillType::name)
    ).apply(instance, DemonWillType::valueOf));

    public static final StreamCodec<ByteBuf, DemonWillType> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.STRING_UTF8, DemonWillType::name, DemonWillType::valueOf);

    @Override
    public String getSerializedName() {
        return name;
    }
}
