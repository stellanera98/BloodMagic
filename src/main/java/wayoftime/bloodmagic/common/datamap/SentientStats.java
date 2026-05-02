package wayoftime.bloodmagic.common.datamap;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.enchantment.LevelBasedValue;

import java.util.List;


public record SentientStats(LevelBasedValue bonusDamage, LevelBasedValue bonusAttackSpeed, List<Attributes> attributes, List<Effects> effects) {
    public static final Codec<SentientStats> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            LevelBasedValue.DISPATCH_CODEC.fieldOf("damage").forGetter(SentientStats::bonusDamage),
            LevelBasedValue.DISPATCH_CODEC.fieldOf("attack_speed").forGetter(SentientStats::bonusAttackSpeed),
            Attributes.CODEC.listOf().fieldOf("attributes").forGetter(SentientStats::attributes),
            Effects.CODEC.listOf().fieldOf("effects").forGetter(SentientStats::effects)
    ).apply(builder, SentientStats::new));

    public record Attributes(Holder<Attribute> holder, ResourceLocation id, AttributeModifier.Operation operation, LevelBasedValue amount) {
        public static final Codec<Attributes> CODEC = RecordCodecBuilder.create(builder -> builder.group(
                BuiltInRegistries.ATTRIBUTE.holderByNameCodec().fieldOf("holder").forGetter(Attributes::holder),
                ResourceLocation.CODEC.fieldOf("id").forGetter(Attributes::id),
                AttributeModifier.Operation.CODEC.fieldOf("operation").forGetter(Attributes::operation),
                LevelBasedValue.DISPATCH_CODEC.fieldOf("amount").forGetter(Attributes::amount)
        ).apply(builder, Attributes::new));

        public AttributeModifier getModifier(int level) {
            return new AttributeModifier(id, amount.calculate(level), operation);
        }
    }

    public record Effects(Holder<MobEffect> holder, LevelBasedValue amplifier, LevelBasedValue duration) {
        public static final Codec<Effects> CODEC = RecordCodecBuilder.create(builder -> builder.group(
                BuiltInRegistries.MOB_EFFECT.holderByNameCodec().fieldOf("holder").forGetter(Effects::holder),
                LevelBasedValue.DISPATCH_CODEC.fieldOf("amplifier").forGetter(Effects::amplifier),
                LevelBasedValue.DISPATCH_CODEC.fieldOf("duration").forGetter(Effects::duration)
        ).apply(builder, Effects::new));

        public MobEffectInstance getEffect(int level) {
            return new MobEffectInstance(holder, (int) duration.calculate(level), (int) amplifier.calculate(level));
        }
    }
}
