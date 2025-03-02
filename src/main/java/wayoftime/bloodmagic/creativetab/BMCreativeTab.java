package wayoftime.bloodmagic.creativetab;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.block.BMBlocks;
import wayoftime.bloodmagic.fluid.BMFluids;

public class BMCreativeTab {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, BloodMagic.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = TABS.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + BloodMagic.MODID + ".main"))
                    .icon(() -> new ItemStack(BMBlocks.BLOOD_ALTAR.asItem()))
                    .displayItems((params, output) -> {
                        BMBlocks.BLOCK_ITEMS.getEntries().forEach(item -> output.accept(item.get()));
                        BMFluids.BUCKETS.getEntries().forEach(bucket -> output.accept(bucket.get()));
                        BMBlocks.BASIC_BLOCK_ITEMS.getEntries().forEach(item -> output.accept(item.get()));
                    })
                    .build()
    );

    public static void register(IEventBus modBus) {
        TABS.register(modBus);
    }
}
