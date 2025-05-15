package wayoftime.bloodmagic.datagen.builders;

import net.minecraft.data.tags.TagsProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import wayoftime.bloodmagic.common.registry.BMRegistries;
import wayoftime.bloodmagic.common.tag.BMTags;

import java.util.function.Function;

public class DamageTypes {
    public static final DamageType SACRIFICE = new DamageType(
            BMRegistries.Keys.SACRIFICE_DAMAGE_KEY.location().getPath(),
            DamageScaling.NEVER,
            0F
    );

    public static void bootstrap(BootstrapContext<DamageType> bootstrap) {
        bootstrap.register(BMRegistries.Keys.SACRIFICE_DAMAGE_KEY, SACRIFICE);
    }

    public static void tags(Function<TagKey<DamageType>, TagsProvider.TagAppender<DamageType>> adder) {
        adder.apply(BMTags.Damage.SELF_SACRIFICE).add(BMRegistries.Keys.SACRIFICE_DAMAGE_KEY);
    }
}
