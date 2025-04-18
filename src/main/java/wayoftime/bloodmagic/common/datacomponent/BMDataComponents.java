package wayoftime.bloodmagic.common.datacomponent;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.datacomponent.Binding;
import wayoftime.bloodmagic.util.DemonWillType;

public class BMDataComponents {
    public static final DeferredRegister.DataComponents COMPONENT_TYPES = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, BloodMagic.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> CONTAINER_TIER = COMPONENT_TYPES.registerComponentType("container_tier", builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SimpleFluidContent>> FLUID_CONTENT = COMPONENT_TYPES.registerComponentType("fluid_content", builder -> builder.persistent(SimpleFluidContent.CODEC).networkSynchronized(SimpleFluidContent.STREAM_CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ENERGY_CONTENT = COMPONENT_TYPES.registerComponentType("energy_content", builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<DemonWillType>> DEMON_WILL_TYPE = COMPONENT_TYPES.registerComponentType("demon_will_type", builder -> builder.persistent(DemonWillType.CODEC).networkSynchronized(DemonWillType.STREAM_CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Double>> DEMON_WILL_AMOUNT = COMPONENT_TYPES.registerComponentType("demon_will_amount", builder -> builder.persistent(Codec.DOUBLE).networkSynchronized(ByteBufCodecs.DOUBLE));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Double>> DISCRETE_DEMON_WILL_AMOUNT = COMPONENT_TYPES.registerComponentType("discrete_demon_will_amount", builder -> builder.persistent(Codec.DOUBLE).networkSynchronized(ByteBufCodecs.DOUBLE));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Binding>> BINDING = COMPONENT_TYPES.registerComponentType("binding_type", builder -> builder.persistent(Binding.BASIC_CODEC).networkSynchronized(Binding.STREAM_CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> INCENSE = COMPONENT_TYPES.registerComponentType("incense", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

    public static void register(IEventBus modBus) {
        COMPONENT_TYPES.register(modBus);
    }
}
