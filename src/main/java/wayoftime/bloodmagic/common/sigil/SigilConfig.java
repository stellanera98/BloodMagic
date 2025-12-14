package wayoftime.bloodmagic.common.sigil;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import wayoftime.bloodmagic.api.BMIdentifiers.Sigils;

import javax.annotation.Nullable;

public class SigilConfig {
    public static ConfigHolder DIVINATION;
    public static ConfigHolder SEER;
    public static ConfigHolder LAVA;
    public static ConfigHolder WATER;
    public static ConfigHolder VOID;
    public static ConfigHolder MINER;

    public static void register(ModContainer container) {
        DIVINATION = new Divination();
        SEER = new Seer();
        LAVA = new Lava();
        WATER = new Water();
        VOID = new Void();
        MINER = new Miner();

        container.registerConfig(ModConfig.Type.SERVER, DIVINATION.build(), filename(Sigils.DIVINATION));
        container.registerConfig(ModConfig.Type.SERVER, SEER.build(), filename(Sigils.SEER));
        container.registerConfig(ModConfig.Type.SERVER, LAVA.build(), filename(Sigils.LAVA));
        container.registerConfig(ModConfig.Type.SERVER, WATER.build(), filename(Sigils.WATER));
        container.registerConfig(ModConfig.Type.SERVER, VOID.build(), filename(Sigils.VOID));
        container.registerConfig(ModConfig.Type.SERVER, MINER.build(), filename(Sigils.MINER));
    }

    private static String filename(ResourceLocation effect) {
        return effect.getNamespace() + "/sigils/" + effect.getPath() + ".toml";
    }

    // TODO fill these out
    private static final String right_click_block_cost_desc = "Cost for successfully right clicking a block";
    public static final String RIGHT_CLICK_BLOCK_COST_ID = "click_block";

    private static final String right_click_air_cost_desc = "Cost for successfully right clicking air";
    public static final String RIGHT_CLICK_AIR_COST_ID = "click_air";

    private static final String right_click_entity_cost_desc = "Cost for successfully right clicking a LivingEntity";
    public static final String RIGHT_CLICK_ENTITY_COST_ID = "click_entity";

    private static final String active_cost_desc = "Upkeep to be payed every 5s (100 ticks)";
    public static final String ACTIVE_COST_ID = "upkeep";

    private static final String beacon_cost_desc = "Not Yet Implemented - no effect";
    public static final String BEACON_COST_ID = "nyi_beacon_cost";

    public abstract static class ConfigHolder {
        public @Nullable ModConfigSpec.IntValue USE_BLOCK_COST;
        public @Nullable ModConfigSpec.IntValue USE_AIR_COST;
        public @Nullable ModConfigSpec.IntValue USE_ENTITY_COST;
        public @Nullable ModConfigSpec.IntValue ONGOING_COST;
        public @Nullable ModConfigSpec.IntValue BEACON_COST;

        public abstract ModConfigSpec build();
    }

    public static class Divination extends ConfigHolder {
        public ModConfigSpec build() {
            ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
            USE_AIR_COST = addAir(0, builder);
            USE_BLOCK_COST = addBlock(0, builder);

            return builder.build();
        }
    }

    public static class Seer extends ConfigHolder {
        public ModConfigSpec build() {
            ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
            USE_AIR_COST = addAir(0, builder);
            USE_BLOCK_COST = addBlock(0, builder);

            return builder.build();
        }
    }

    public static class Lava extends ConfigHolder {
        public ModConfigSpec build() {
            ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
            USE_BLOCK_COST = addBlock(1000, builder);

            return builder.build();
        }
    }

    public static class Water extends ConfigHolder {
        public ModConfigSpec build() {
            ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
            USE_BLOCK_COST = addBlock(100, builder);

            return builder.build();
        }
    }

    public static class Void extends ConfigHolder {
        public ModConfigSpec build() {
            ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
            USE_BLOCK_COST = addBlock(50, builder);

            return builder.build();
        }
    }

    public static class Miner extends ConfigHolder {
        public ModConfigSpec build() {
            ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
            ONGOING_COST = addContinuous(100, builder);
            BEACON_COST = addBeacon(100, builder);

            return builder.build();
        }
    }

    private static ModConfigSpec.IntValue addAir(int value, ModConfigSpec.Builder builder) {
        return addConfig(right_click_air_cost_desc, RIGHT_CLICK_AIR_COST_ID, value, builder);
    }

    private static ModConfigSpec.IntValue addBlock(int value, ModConfigSpec.Builder builder) {
        return addConfig(right_click_block_cost_desc, RIGHT_CLICK_BLOCK_COST_ID, value, builder);
    }

    private static ModConfigSpec.IntValue addEntity(int value, ModConfigSpec.Builder builder) {
        return addConfig(right_click_entity_cost_desc, RIGHT_CLICK_ENTITY_COST_ID, value, builder);
    }

    private static ModConfigSpec.IntValue addContinuous(int value, ModConfigSpec.Builder builder) {
        return addConfig(active_cost_desc, ACTIVE_COST_ID, value, builder);
    }

    private static ModConfigSpec.IntValue addBeacon(int value, ModConfigSpec.Builder builder) {
        return addConfig(beacon_cost_desc, BEACON_COST_ID, value, builder);
    }

    private static ModConfigSpec.IntValue addConfig(String desc, String id, int defaultValue, ModConfigSpec.Builder builder) {
        return builder.comment(desc).defineInRange(id, defaultValue, 0, Integer.MAX_VALUE);
    }
}
