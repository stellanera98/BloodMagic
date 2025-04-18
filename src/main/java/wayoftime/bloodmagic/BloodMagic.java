package wayoftime.bloodmagic;

import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import org.slf4j.Logger;
import wayoftime.bloodmagic.common.block.BMBlocks;
import wayoftime.bloodmagic.common.blockentity.BMTiles;
import wayoftime.bloodmagic.common.command.BMCommands;
import wayoftime.bloodmagic.common.creativetab.BMCreativeTab;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.common.datamap.BMDataMaps;
import wayoftime.bloodmagic.common.fluid.BMFluids;
import wayoftime.bloodmagic.common.item.BMItems;
import wayoftime.bloodmagic.common.recipe.BMRecipes;
import wayoftime.bloodmagic.common.registry.BMRegistries;


@Mod(BloodMagic.MODID)
public class BloodMagic {
    public static final String MODID = "bloodmagic";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final ResourceLocation TYPE_PROPERTY = ResourceLocation.fromNamespaceAndPath(BloodMagic.MODID, "type");
    public static final ResourceLocation INCENSE_PROPERTY = ResourceLocation.fromNamespaceAndPath(BloodMagic.MODID, "incense");

    public BloodMagic(IEventBus modBus) {
        BMDataComponents.register(modBus);
        BMFluids.register(modBus);
        BMBlocks.register(modBus);
        BMItems.register(modBus);
        BMTiles.register(modBus);
        BMRecipes.register(modBus);
        BMDataMaps.register(modBus);
        BMRegistries.register(modBus);
        BMCreativeTab.register(modBus);

        NeoForge.EVENT_BUS.addListener(BMCommands::register);
        modBus.addListener(BloodMagic::addPacks);
    }

    private static void addPacks(AddPackFindersEvent event) {
        event.addPackFinders(bm("packs/tier6"), PackType.SERVER_DATA, Component.translatable("packname.tier6"), PackSource.FEATURE, false, Pack.Position.TOP);
    }

    public static ResourceLocation bm(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
