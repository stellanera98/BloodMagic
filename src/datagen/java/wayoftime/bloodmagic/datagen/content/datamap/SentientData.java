package wayoftime.bloodmagic.datagen.content.datamap;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.datacomponent.EnumWillType;
import wayoftime.bloodmagic.common.datamap.BMDataMaps;
import wayoftime.bloodmagic.common.datamap.SentientStats;
import wayoftime.bloodmagic.common.item.BMItems;
import wayoftime.bloodmagic.datagen.provider.DataMapBuilder;

import java.util.List;
import java.util.Map;

public class SentientData {
    public static void bootstrap(DataMapBuilder<Item, Map<EnumWillType, SentientStats>> builder) {
        builder.apply(BMDataMaps.SENTIENT_STATS)
                .add(BMItems.SENTIENT_SWORD.getId(), Map.of(
                        EnumWillType.RAW, new SentientStats(
                                LevelBasedValue.perLevel(0.5f),
                                LevelBasedValue.constant(0f),
                                List.of(),
                                List.of()),
                        EnumWillType.CORROSIVE, new SentientStats(
                                LevelBasedValue.constant(0f),
                                LevelBasedValue.constant(0f),
                                List.of(),
                                List.of(
                                        new SentientStats.Effects(
                                                MobEffects.WITHER,
                                                LevelBasedValue.lookup(List.of(0f, 0f, 0f, 0f, 1f, 1f, 1f, 1f), LevelBasedValue.constant(2f)),
                                                LevelBasedValue.perLevel(20f)
                                        )
                                )
                        ),
                        EnumWillType.DESTRUCTIVE, new SentientStats(
                                LevelBasedValue.perLevel(1.5f, 0.75f),
                                LevelBasedValue.lookup(List.of(-0.1f, -0.2f, -0.3f, -0.4f, -0.5f), LevelBasedValue.constant(-0.6f)),
                                List.of(),
                                List.of()
                        ),
                        EnumWillType.STEADFAST, new SentientStats(
                                LevelBasedValue.perLevel(0f, 0.5f),
                                LevelBasedValue.constant(0f),
                                List.of(
                                        new SentientStats.Attributes(
                                                Attributes.MAX_ABSORPTION,
                                                BloodMagic.rl("steadfast"),
                                                AttributeModifier.Operation.ADD_VALUE,
                                                LevelBasedValue.perLevel(2f)
                                        )
                                ),
                                List.of()
                        ),
                        EnumWillType.VENGEFUL, new SentientStats(
                                LevelBasedValue.perLevel(0f, 0.5f),
                                LevelBasedValue.perLevel(0.2f, 0.1f),
                                List.of(
                                        new SentientStats.Attributes(
                                                Attributes.MOVEMENT_SPEED,
                                                BloodMagic.rl("vengeful"),
                                                AttributeModifier.Operation.ADD_VALUE,
                                                LevelBasedValue.perLevel(0.05f)
                                        )
                                ),
                                List.of()
                        )
                ), false);
    }
}
