package wayoftime.bloodmagic.common.living;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.util.Unit;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import wayoftime.bloodmagic.common.living.effects.AttributeEffect;
import wayoftime.bloodmagic.common.living.effects.ConditionalEffect;
import wayoftime.bloodmagic.common.registry.BMRegistries;

import java.util.*;

public record LivingUpgrade(List<Level> levels, DataComponentMap effects) {
    public static final Codec<LivingUpgrade> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Level.CODEC.listOf().fieldOf("levels").forGetter(LivingUpgrade::levels),
            LivingEffectComponents.CODEC.fieldOf("effects").forGetter(LivingUpgrade::effects)
    ).apply(builder, LivingUpgrade::new)); // TODO .validate() levels.len and effects amounts. or call it a skill issue, idk

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<LivingUpgrade>> STREAM_CODEC = ByteBufCodecs.holderRegistry(BMRegistries.Keys.LIVING_UPGRADES);

    public static final Codec<Holder<LivingUpgrade>> HOLDER_CODEC = RegistryFixedCodec.create(BMRegistries.Keys.LIVING_UPGRADES);

    public record Level(int xpNeeded, int cost) {
        public static final Codec<Level> CODEC = RecordCodecBuilder.create(builder -> builder.group(
                Codec.INT.fieldOf("xp").forGetter(Level::xpNeeded),
                Codec.INT.fieldOf("cost").forGetter(Level::cost)
        ).apply(builder, Level::new));
    }

    public static class Builder {
        private final Map<DataComponentType<?>, List<?>> effectLists = new HashMap<>();
        private final DataComponentMap.Builder effectMapBuilder = DataComponentMap.builder();
        private final List<Level> levels = new ArrayList<>();

        public Builder level(int xpNeeded, int cost) {
            levels.add(new Level(xpNeeded, cost));
            return this;
        }

        public Builder withEffect(DataComponentType<List<AttributeEffect>> type, AttributeEffect effect) {
            getEffectsList(type).add(effect);
            return this;
        }

        public Builder withEffect(DataComponentType<Unit> type) {
            effectMapBuilder.set(type, Unit.INSTANCE);
            return this;
        }

        public <E> Builder withEffect(DataComponentType<List<ConditionalEffect<E>>> type, E effect, LootItemCondition.Builder condition) {
            getEffectsList(type).add(new ConditionalEffect<>(effect, Optional.of(condition.build())));
            return this;
        }

        public <E> Builder withEffect(DataComponentType<List<ConditionalEffect<E>>> type, E effect) {
            getEffectsList(type).add(new ConditionalEffect<>(effect, Optional.empty()));
            return this;
        }

        public LivingUpgrade build() {
            return new LivingUpgrade(levels, effectMapBuilder.build());
        }

        private <E> List<E> getEffectsList(DataComponentType<List<E>> componentType) {
            return (List<E>) this.effectLists.computeIfAbsent(componentType, p_346247_ -> {
                ArrayList<E> arraylist = new ArrayList<>();
                this.effectMapBuilder.set(componentType, arraylist);
                return arraylist;
            });
        }
    }
}
