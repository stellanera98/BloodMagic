package wayoftime.bloodmagic.blockentity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.block.BMBlocks;
import wayoftime.bloodmagic.blockentity.render.BloodAltarRenderer;
import wayoftime.bloodmagic.blockentity.render.BloodTankRenderer;
import wayoftime.bloodmagic.blockentity.render.HellfireForgeRenderer;

import java.util.Set;

public class BMTiles {
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, BloodMagic.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BloodTankTile>> BLOOD_TANK_TYPE = TILES.register("blood_tank",
            () -> new BlockEntityType<>(BloodTankTile::new, Set.of(BMBlocks.BLOOD_TANK.block().get()), null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BloodAltarTile>> BLOOD_ALTAR_TYPE = TILES.register("blood_altar",
            () -> new BlockEntityType<>(BloodAltarTile::new, Set.of(BMBlocks.BLOOD_ALTAR.block().get()), null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<HellfireForgeTile>> HELLFIRE_FORGE_TYPE = TILES.register("hellfire_forge",
            () -> new BlockEntityType<>(HellfireForgeTile::new, Set.of(BMBlocks.HELLFIRE_FORGE.block().get()), null));

    private static void registerTileCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                BLOOD_TANK_TYPE.get(),
                BloodTankTile::getFluidHandler
        );
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                BLOOD_ALTAR_TYPE.get(),
                (tile, side) -> tile
        );
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                BLOOD_ALTAR_TYPE.get(),
                (tile, side) -> tile.inv
        );
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                HELLFIRE_FORGE_TYPE.get(),
                HellfireForgeTile::getInventory
        );
    }

    private static void registerBlockEntityRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BLOOD_TANK_TYPE.get(), BloodTankRenderer::new);
        event.registerBlockEntityRenderer(BLOOD_ALTAR_TYPE.get(), BloodAltarRenderer::new);
        event.registerBlockEntityRenderer(HELLFIRE_FORGE_TYPE.get(), HellfireForgeRenderer::new);
    }

    public static void register(IEventBus modBus) {
        TILES.register(modBus);
        modBus.addListener(BMTiles::registerTileCapabilities);
        modBus.addListener(BMTiles::registerBlockEntityRenderer);
    }
}