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
import wayoftime.bloodmagic.common.blockentity.BloodTankTile;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.common.datacomponent.StoredUpgrade;
import wayoftime.bloodmagic.common.datamap.BMDataMaps;
import wayoftime.bloodmagic.common.fluid.BMFluids;
import wayoftime.bloodmagic.common.item.BMItems;
import wayoftime.bloodmagic.common.living.LivingUpgrade;
import wayoftime.bloodmagic.common.registry.BMRegistries;
import wayoftime.bloodmagic.util.DemonWillType;

import java.util.function.Supplier;

public class BMCreativeTab {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, BloodMagic.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = TABS.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + BloodMagic.MODID + ".main"))
                    .icon(() -> new ItemStack(BMBlocks.BLOOD_ALTAR.asItem()))
                    .displayItems((params, output) -> {
                        BMBlocks.BLOCK_ITEMS.getEntries().stream().map(Supplier::get).forEach(output::accept);
                        BMBlocks.BASIC_BLOCK_ITEMS.getEntries().stream().map(Supplier::get).forEach(output::accept);
                        BMItems.WILLITEMS.getEntries().forEach(item -> {
                            for (DemonWillType type : DemonWillType.values()) {
                                ItemStack stack = new ItemStack(item);
                                stack.set(BMDataComponents.DEMON_WILL_TYPE, type);
                                Double max = item.getData(BMDataMaps.SOUL_GEM_AMOUNTS);
                                if (max != null) {
                                    stack.set(BMDataComponents.DEMON_WILL_AMOUNT, max);
                                }
                                output.accept(stack);
                            }
                        });
                        BMItems.ITEMS.getEntries().stream().map(Supplier::get).forEach(output::accept);
                        BMItems.BASICITEMS.getEntries().stream().map(Supplier::get).forEach(output::accept);
                        BMFluids.BUCKETS.getEntries().stream().map(Supplier::get).forEach(output::accept);
                        for (int i = 1; i < BloodTankTile.CAPACITIES.length; i++) {
                            ItemStack tmp = new ItemStack(BMBlocks.BLOOD_TANK.asItem());
                            tmp.set(BMDataComponents.CONTAINER_TIER, i);
                            output.accept(tmp);
                        }
                    })
                    .build()
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> UPGRADE_TAB = TABS.register("upgrades",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + BloodMagic.MODID + ".upgrades"))
                    .icon(() -> new ItemStack(BMItems.UPGRADE_TOME.get()))
                    .displayItems((params, output) -> {
                        params.holders().lookupOrThrow(BMRegistries.Keys.LIVING_UPGRADES).listElements().forEach(holder -> {
                            for (LivingUpgrade.Level level : holder.value().levels()) {
                                ItemStack stack = new ItemStack(BMItems.UPGRADE_TOME);
                                stack.set(BMDataComponents.STORED_UPGRADE, new StoredUpgrade(holder, level.xpNeeded()));
                                output.accept(stack);
                            }
                        });
                    })
                    .build()
            );

    public static void register(IEventBus modBus) {
        TABS.register(modBus);
    }
}
