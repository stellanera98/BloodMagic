package wayoftime.bloodmagic.common.living.effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import wayoftime.bloodmagic.BloodMagic;

import java.util.List;

public record AttributeEffect(ResourceLocation id, Holder<Attribute> attribute, AttributeModifier.Operation operation, LevelBasedValue amounts) {
    public static final MapCodec<AttributeEffect> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(AttributeEffect::id),
            Attribute.CODEC.fieldOf("attribute").forGetter(AttributeEffect::attribute),
            AttributeModifier.Operation.CODEC.fieldOf("operation").forGetter(AttributeEffect::operation),
            LevelBasedValue.CODEC.fieldOf("amounts").forGetter(AttributeEffect::amounts)
    ).apply(builder, AttributeEffect::new));

    public AttributeModifier getModifier(int level) {
        BloodMagic.LOGGER.info("{}: {} {}", id, operation.getSerializedName(), amounts.calculate(level));
        return new AttributeModifier(id, amounts().calculate(level), operation);
    }

    public void addModifier(int level, ItemStack chestStack) {
        BloodMagic.LOGGER.info("addModifier");
        ItemAttributeModifiers mods = chestStack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
        ItemAttributeModifiers apply = mods.withModifierAdded(attribute, getModifier(level), EquipmentSlotGroup.CHEST);
        chestStack.set(DataComponents.ATTRIBUTE_MODIFIERS, apply);
    }

    public void removeModifier(ItemStack chestStack) {
        BloodMagic.LOGGER.info("removeModifier {}", id);
        ItemAttributeModifiers mods = chestStack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
        ItemAttributeModifiers.Builder buider = ItemAttributeModifiers.builder();
        for (ItemAttributeModifiers.Entry entry : mods.modifiers()) {
            if (!entry.modifier().id().equals(id)) {
                buider.add(entry.attribute(), entry.modifier(), entry.slot());
            }
        }
        chestStack.set(DataComponents.ATTRIBUTE_MODIFIERS, buider.build());
    }
}
