package wayoftime.bloodmagic;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig {

    public final ModConfigSpec.ConfigValue<Integer> SELF_SACRIFICE_CONVERSION;
    public final ModConfigSpec.ConfigValue<Integer> DEFAULT_UPGRADE_POINTS;
    public final ModConfigSpec.ConfigValue<Integer> EVOLVED_UPGRADE_POINTS;

    public final ModConfigSpec.ConfigValue<Integer> BUDDING_CHANCE;

    protected ServerConfig(ModConfigSpec.Builder builder) {
        SELF_SACRIFICE_CONVERSION = builder.comment("How much life essence half a heart is worth for self sacrifice, before any buffs")
                .define("self_sacrifice_conversion", 100);

        DEFAULT_UPGRADE_POINTS = builder.comment("How much upgrade points Living Armour has by default")
                .define("default_upgrade_points", 100);
        EVOLVED_UPGRADE_POINTS = builder.comment("How much upgrade points Living Armour has after Living Evolution Ritual")
                .define("evolved_upgrade_points", 300);

        BUDDING_CHANCE = builder.comment("Budding Will blocks have 1 in X chance to grow on one side on every random tick (Budding Amethyst has this at 5)")
                .define("budding_chance", 5);
    }
}
