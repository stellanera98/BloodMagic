package wayoftime.bloodmagic.common.datacomponent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import wayoftime.bloodmagic.common.living.LivingHelper;
import wayoftime.bloodmagic.common.living.LivingUpgrade;
import wayoftime.bloodmagic.common.registry.BMRegistries;
import wayoftime.bloodmagic.common.tag.BMTags;

import java.util.Collections;
import java.util.Optional;
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

    public static LivingStats fromHolderSet(HolderSet<LivingUpgrade> holders) {
        Object2FloatOpenHashMap<Holder<LivingUpgrade>> in = new Object2FloatOpenHashMap<>();
        holders.forEach(holder -> in.computeFloat(holder, (key, exp) -> exp == null ? 0 : exp));
        return new LivingStats(in);
    }

    public Set<Object2FloatMap.Entry<Holder<LivingUpgrade>>> entrySet() {
        return Collections.unmodifiableSet(this.upgrades.object2FloatEntrySet());
    }

    public float getExp(Holder<LivingUpgrade> holder) {
        return upgrades.getFloat(holder);
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
        HolderSet<LivingUpgrade> order = getOrder(context.registries());

        for (Holder<LivingUpgrade> holder : order) {
            if (this.upgrades.containsKey(holder)) {
                float exp = this.upgrades.getFloat(holder);
                tooltipAdder.accept(LivingHelper.getTooltip(holder, exp, tooltipFlag.hasShiftDown()));
            }
        }

        for (Object2FloatMap.Entry<Holder<LivingUpgrade>> entry : this.upgrades.object2FloatEntrySet()) {
            if (!order.contains(entry.getKey()) && !entry.getKey().is(BMTags.Living.TOOLTIP_HIDE)) {
                tooltipAdder.accept(LivingHelper.getTooltip(entry.getKey(), entry.getFloatValue(), tooltipFlag.hasShiftDown()));
            }
        }
    }

    private static HolderSet<LivingUpgrade> getOrder(HolderLookup.Provider registries) {
        if (registries != null) {
            Optional<HolderSet.Named<LivingUpgrade>> optional = registries.lookupOrThrow(BMRegistries.Keys.LIVING_UPGRADES).get(BMTags.Living.TOOLTIP_ORDER);
            if (optional.isPresent()) {
                return optional.get();
            }
        }

        return HolderSet.empty();
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
