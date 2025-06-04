package wayoftime.bloodmagic.client.event;

import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.PlayerSkin;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.client.render.entity.layer.LivingElytraLayer;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.common.datacomponent.EnumWillType;
import wayoftime.bloodmagic.common.item.BMItems;

@EventBusSubscriber(modid = BloodMagic.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ClientModEventHandler {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            BMItems.WILL_ITEMS.getEntries().forEach(item -> {
                ItemProperties.register(item.get(), BloodMagic.TYPE_PROPERTY, (stack, level, player, seed) -> stack.getOrDefault(BMDataComponents.DEMON_WILL_TYPE, EnumWillType.DEFAULT).ordinal());
            });
            ItemProperties.register(BMItems.SACRIFICIAL_DAGGER.get(), BloodMagic.INCENSE_PROPERTY, ((stack, level, entity, seed) -> stack.getOrDefault(BMDataComponents.INCENSE, false) ? 1 : 0));
        });
    }

    @SubscribeEvent
    public static void registerRenderLayer(EntityRenderersEvent.AddLayers event) {
        // TODO figure out if there is a better way to get a whatever renderer that has #addLayer on it
        PlayerRenderer skin = event.getSkin(PlayerSkin.Model.WIDE);
        skin.addLayer(new LivingElytraLayer<>(skin, event.getEntityModels()));

        skin = event.getSkin(PlayerSkin.Model.SLIM);
        skin.addLayer(new LivingElytraLayer<>(skin, event.getEntityModels()));
    }
}