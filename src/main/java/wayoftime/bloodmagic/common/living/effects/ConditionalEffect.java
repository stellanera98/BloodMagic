package wayoftime.bloodmagic.common.living.effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.Optional;

public record ConditionalEffect<T>(T effect, Optional<LootItemCondition> requirements) {
    public static Codec<LootItemCondition> conditionCodec(LootContextParamSet params) {
        return LootItemCondition.DIRECT_CODEC
                .validate(
                        p_351949_ -> {
                            ProblemReporter.Collector problemreporter$collector = new ProblemReporter.Collector();
                            ValidationContext validationcontext = new ValidationContext(problemreporter$collector, params);
                            p_351949_.validate(validationcontext);
                            return problemreporter$collector.getReport()
                                    .map(p_344978_ -> DataResult.<LootItemCondition>error(() -> "Validation error in living effect condition: " + p_344978_))
                                    .orElseGet(() -> DataResult.success(p_351949_));
                        }
                );
    }

    public static <T> Codec<ConditionalEffect<T>> codec(Codec<T> codec, LootContextParamSet params) {
        return RecordCodecBuilder.create(
                p_345993_ -> p_345993_.group(
                                codec.fieldOf("effect").forGetter(ConditionalEffect::effect),
                                conditionCodec(params).optionalFieldOf("requirements").forGetter(ConditionalEffect::requirements)
                        )
                        .apply(p_345993_, ConditionalEffect::new)
        );
    }

    public boolean matches(LootContext context) {
        return this.requirements.isEmpty() ? true : this.requirements.get().test(context);
    }
}
