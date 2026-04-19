package wayoftime.bloodmagic.common.datamap;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import wayoftime.bloodmagic.common.datacomponent.EnumWillType;

public record WillStack(EnumWillType type, Double amount) {

    public static final Codec<WillStack> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            EnumWillType.CODEC.fieldOf("type").forGetter(WillStack::type),
            Codec.DOUBLE.fieldOf("amount").forGetter(WillStack::amount)
    ).apply(builder, WillStack::new));
}
