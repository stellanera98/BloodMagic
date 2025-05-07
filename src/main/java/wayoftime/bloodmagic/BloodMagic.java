package wayoftime.bloodmagic;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import wayoftime.bloodmagic.client.menu.BMMenus;
import wayoftime.bloodmagic.common.attribute.BMAttributes;
import wayoftime.bloodmagic.common.block.BMBlocks;
import wayoftime.bloodmagic.common.blockentity.BMTiles;
import wayoftime.bloodmagic.common.command.BMCommands;
import wayoftime.bloodmagic.common.creativetab.BMCreativeTab;
import wayoftime.bloodmagic.common.dataattachment.BMDataAttachments;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.common.datamap.BMDataMaps;
import wayoftime.bloodmagic.common.fluid.BMFluids;
import wayoftime.bloodmagic.common.ingredient.BMIngredients;
import wayoftime.bloodmagic.common.item.BMItems;
import wayoftime.bloodmagic.common.item.BMMaterialsAndTiers;
import wayoftime.bloodmagic.common.living.LivingEffectComponents;
import wayoftime.bloodmagic.common.living.effects.DamageBasedEffect;
import wayoftime.bloodmagic.common.living.effects.EntityEffect;
import wayoftime.bloodmagic.common.living.effects.StandaloneEffect;
import wayoftime.bloodmagic.common.living.effects.ValueBasedEffect;
import wayoftime.bloodmagic.common.mobeffect.BMMobEffects;
import wayoftime.bloodmagic.common.recipe.BMRecipes;
import wayoftime.bloodmagic.common.registry.BMRegistries;
import wayoftime.bloodmagic.common.structure.BMMultiblock;


@Mod(BloodMagic.MODID)
public class BloodMagic {
    public static final String MODID = "bloodmagic";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final ServerConfig SERVER_CONFIG;
    public static final ModConfigSpec SERVER_SPEC;

    static {
        Pair<ServerConfig, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(ServerConfig::new);
        SERVER_CONFIG = pair.getLeft();
        SERVER_SPEC = pair.getRight();
    }

    public static final ResourceLocation TYPE_PROPERTY = bm("type");
    public static final ResourceLocation INCENSE_PROPERTY = bm("incense");

    public BloodMagic(IEventBus modBus, ModContainer container) {
        BMDataComponents.register(modBus);
        BMDataAttachments.register(modBus);
        BMFluids.register(modBus);
        BMBlocks.register(modBus);
        BMItems.register(modBus);
        BMTiles.register(modBus);
        BMRecipes.register(modBus);
        BMDataMaps.register(modBus);
        BMRegistries.register(modBus);
        BMCreativeTab.register(modBus);
        BMIngredients.register(modBus);
        BMMenus.register(modBus);
        BMMaterialsAndTiers.register(modBus);
        BMMobEffects.register(modBus);
        BMAttributes.register(modBus);

        LivingEffectComponents.LIVING_EFFECT_COMPONENTS.register(modBus);
        StandaloneEffect.STANDALONE_EFFECT_TYPE.register(modBus);
        DamageBasedEffect.DAMAGE_BASED_EFFECT_TYPE.register(modBus);
        ValueBasedEffect.VALUE_BASED_EFFECT_TYPE.register(modBus);
        EntityEffect.ENTITY_EFFECT_TYPE.register(modBus);

        BMMultiblock.register(NeoForge.EVENT_BUS);
        NeoForge.EVENT_BUS.addListener(BMCommands::register);
        modBus.addListener(BloodMagic::addPacks);

        container.registerConfig(ModConfig.Type.SERVER, SERVER_SPEC);
    }

    private static void addPacks(AddPackFindersEvent event) {
        //event.addPackFinders(bm("packs/tier6"), PackType.SERVER_DATA, Component.translatable("packname.tier6"), PackSource.FEATURE, false, Pack.Position.TOP);
    }

    public static ResourceLocation bm(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
