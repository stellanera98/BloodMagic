package wayoftime.bloodmagic.common.datacomponent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.entity.EnchantingTableBlockEntity;
import wayoftime.bloodmagic.common.living.LivingUpgrade;
import wayoftime.bloodmagic.common.registry.BMRegistries;

import java.util.Collections;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public class LivingStats implements TooltipProvider {
    public static final Codec<LivingStats> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Codec.unboundedMap(RegistryFixedCodec.create(BMRegistries.Keys.LIVING_UPGRADES), Codec.FLOAT).xmap(Object2FloatOpenHashMap::new, Function.identity()).fieldOf("upgrades").forGetter(inst -> inst.upgrades)
    ).apply(builder, LivingStats::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, LivingStats> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.map(Object2FloatOpenHashMap::new, LivingUpgrade.STREAM_CODEC, ByteBufCodecs.FLOAT),
            inst -> inst.upgrades,
            LivingStats::new
    );

    public static final LivingStats EMPTY = new LivingStats(new Object2FloatOpenHashMap<>());

    private final Object2FloatOpenHashMap<Holder<LivingUpgrade>> upgrades;
    public LivingStats(Object2FloatOpenHashMap<Holder<LivingUpgrade>> upgrades) {
        this.upgrades = upgrades;
    }

    public Set<Object2FloatMap.Entry<Holder<LivingUpgrade>>> entrySet(RegistryAccess registries) {
        this.upgrades.putIfAbsent(registries.holderOrThrow(BMRegistries.Keys.LIVING_EXP), 0);
        return Collections.unmodifiableSet(this.upgrades.object2FloatEntrySet());
    }

    @Override
    public int hashCode() {
        return this.upgrades.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            return obj instanceof LivingStats stats && this.upgrades.equals(stats.upgrades);
        }
    }

    @Override
    public String toString() {
        return "LivingStats{" + this.upgrades + "}";
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltipAdder, TooltipFlag tooltipFlag) {
        // TODO ItemEnchantments.addToTooltip
    }

    public LivingStats.Mutable toMutable() {
        return new Mutable(this);
    }

    public static class Mutable {
        private final Object2FloatOpenHashMap<Holder<LivingUpgrade>> upgrades = new Object2FloatOpenHashMap<>();

        public Mutable(LivingStats from) {
            upgrades.putAll(from.upgrades);
        }

        public void set(Holder<LivingUpgrade> upgrade, int exp) {
            this.upgrades.removeFloat(upgrade);
            this.upgrades.put(upgrade, exp);
        }

        public void addExp(Holder<LivingUpgrade> upgrade, float exp) {
            // TODO clamp exp based on available points
            this.upgrades.merge(upgrade, exp, Float::sum);
        }

        public void remove(Holder<LivingUpgrade> upgrade) {
            this.upgrades.removeFloat(upgrade);
        }

        public LivingStats toImmutable() {
            return new LivingStats(this.upgrades);
        }
    }
}
