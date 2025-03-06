package wayoftime.bloodmagic.common.creativetab;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.block.BMBlocks;
import wayoftime.bloodmagic.common.fluid.BMFluids;
import wayoftime.bloodmagic.common.item.BMItems;

import java.util.function.Supplier;

public class BMCreativeTab {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, BloodMagic.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = TABS.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + BloodMagic.MODID + ".main"))
                    .icon(() -> new ItemStack(BMBlocks.BLOOD_ALTAR.asItem()))
                    .displayItems((params, output) -> {
                        BMBlocks.BLOCK_ITEMS.getEntries().stream().map(Supplier::get).forEach(output::accept);
                        BMFluids.BUCKETS.getEntries().stream().map(Supplier::get).forEach(output::accept);
                        BMBlocks.BASIC_BLOCK_ITEMS.getEntries().stream().map(Supplier::get).forEach(output::accept);
                        BMItems.BASICITEMS.getEntries().stream().map(Supplier::get).forEach(output::accept);
                    })
                    .build()
    );

    public static void register(IEventBus modBus) {
        TABS.register(modBus);
    }
}
