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

import java.util.List;

public record AttributeEffect(ResourceLocation id, Holder<Attribute> attribute, AttributeModifier.Operation operation, List<Double> amounts) {
    public static final MapCodec<AttributeEffect> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(AttributeEffect::id),
            Attribute.CODEC.fieldOf("attribute").forGetter(AttributeEffect::attribute),
            AttributeModifier.Operation.CODEC.fieldOf("operation").forGetter(AttributeEffect::operation),
            Codec.DOUBLE.listOf().fieldOf("amounts").forGetter(AttributeEffect::amounts)
    ).apply(builder, AttributeEffect::new));

    public AttributeModifier getModifier(int level) {
        return new AttributeModifier(id, amounts().get(level), operation);
    }

    public void addModifier(int level, ItemStack chestStack) {
        ItemAttributeModifiers mods = chestStack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
        ItemAttributeModifiers apply = mods.withModifierAdded(attribute, getModifier(level), EquipmentSlotGroup.CHEST);
        chestStack.set(DataComponents.ATTRIBUTE_MODIFIERS, apply);
    }

    public void removeModifier(int level, ItemStack chestStack) {
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
