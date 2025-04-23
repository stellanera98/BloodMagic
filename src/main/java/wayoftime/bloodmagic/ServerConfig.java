package wayoftime.bloodmagic;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig {

    public final ModConfigSpec.ConfigValue<Integer> selfSacrificeConversion;

    protected ServerConfig(ModConfigSpec.Builder builder) {
        selfSacrificeConversion = builder.define("self_sacrifice_conversion", 100);
    }
}
