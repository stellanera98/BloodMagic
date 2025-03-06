package wayoftime.bloodmagic.common.datacomponent;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import wayoftime.bloodmagic.util.helper.BlockEntityHelper;

import java.util.Objects;
import java.util.UUID;

public record Binding(UUID uuid, String name) {
    public static final Codec<Binding> BASIC_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    UUIDUtil.CODEC.fieldOf("uuid").forGetter(Binding::uuid),
                    Codec.STRING.fieldOf("name").forGetter(Binding::name)
            ).apply(instance, Binding::new)
    );

    public static final StreamCodec<ByteBuf, Binding> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, Binding::uuid,
            ByteBufCodecs.STRING_UTF8, Binding::name,
            Binding::new
    );

    public boolean isEmpty() {
        return this == EMPTY || (this.uuid == null && Objects.equals(this.name, ""));
    }

    public static final Binding EMPTY = new Binding(null, "");

    public Component getHoverText() {
        return BlockEntityHelper.translatableHover("tooltip.bloodmagic.currentOwner", this.name);
    }
}
