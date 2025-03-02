package wayoftime.bloodmagic;

import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import org.slf4j.Logger;
import wayoftime.bloodmagic.block.BMBlocks;
import wayoftime.bloodmagic.blockentity.BMTiles;
import wayoftime.bloodmagic.creativetab.BMCreativeTab;
import wayoftime.bloodmagic.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.datamap.BMDataMaps;
import wayoftime.bloodmagic.fluid.BMFluids;
import wayoftime.bloodmagic.recipe.BMRecipes;
import wayoftime.bloodmagic.registry.BMRegistries;


@Mod(BloodMagic.MODID)
public class BloodMagic {
    public static final String MODID = "bloodmagic";
    public static final Logger LOGGER = LogUtils.getLogger();

    public BloodMagic(IEventBus modBus) {
        BMDataComponents.register(modBus);
        BMFluids.register(modBus);
        BMBlocks.register(modBus);
        BMTiles.register(modBus);
        BMRecipes.register(modBus);
        BMDataMaps.register(modBus);
        BMRegistries.register(modBus);
        BMCreativeTab.register(modBus);

        modBus.addListener(BloodMagic::addPacks);
    }

    private static void addPacks(AddPackFindersEvent event) {
        event.addPackFinders(bm("packs/tier6"), PackType.SERVER_DATA, Component.translatable("packname.tier6"), PackSource.FEATURE, false, Pack.Position.TOP);
    }

    public static ResourceLocation bm(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
