package wayoftime.bloodmagic.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.AttributeTooltipContext;
import net.neoforged.neoforge.common.util.AttributeUtil;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.api.BMTags;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.common.datacomponent.EnumWillType;
import wayoftime.bloodmagic.common.datacomponent.SentientInfo;
import wayoftime.bloodmagic.common.datamap.BMDataMaps;
import wayoftime.bloodmagic.common.datamap.SentientStats;
import wayoftime.bloodmagic.common.mobeffect.BMMobEffects;
import wayoftime.bloodmagic.util.InventoryHelper;

import java.util.List;
import java.util.Map;

public class SentientWeaponItem extends Item {
    // TODO have a way to extend this. or just add a bunch more to be save?
    public static final int[] TIERS = {30, 60, 120, 240, 500, 1000, 2000, 4000, 8000, 16000};

    public static final ResourceLocation BONUS_DAMAGE = BloodMagic.rl("bonus_damage");
    public static final ResourceLocation BONUS_ATTACK_SPEED = BloodMagic.rl("bonus_attack_speed");

    public final float baseDamage;
    public final float baseAttackSpeed;

    public SentientWeaponItem(Properties properties, float baseDamage, float baseAttackSpeed) {
        super(
                properties
                        .component(BMDataComponents.SENTIENT_INFO, SentientInfo.NONE)
                        .component(BMDataComponents.DEMON_WILL_TYPE, EnumWillType.RAW)
                        .component(DataComponents.ATTRIBUTE_MODIFIERS, defaultModifiers(baseDamage, baseAttackSpeed).build())
        );

        this.baseDamage = baseDamage;
        this.baseAttackSpeed = baseAttackSpeed;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
        SentientInfo info = stack.get(BMDataComponents.SENTIENT_INFO);
        if (info != null && info != SentientInfo.NONE) {
            list.add(Component.translatable("tooltip.bloodmagic.sentient.gem").append(info.gemName()));
            list.add(Component.translatable("tooltip.bloodmagic.sentient.will", info.amount(), info.type().asComponent(), info.tier()));
        }
    }

    public static int getTier(double willAmount) {
        int tier = 0;
        for (int i = 0; i < TIERS.length; i++) {
            if (willAmount >= TIERS[i]) {
                tier = i + 1;
            } else {
                break;
            }
        }

        return tier;
    }

    public static ItemAttributeModifiers.Builder defaultModifiers(float attackDamage, float attackSpeed) {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        EquipmentSlotGroup slot = EquipmentSlotGroup.MAINHAND;
        builder.add(
                Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, attackDamage, AttributeModifier.Operation.ADD_VALUE), slot
        );
        builder.add(
                Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, attackSpeed - 4, AttributeModifier.Operation.ADD_VALUE), slot
        );

        return builder;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (!(entity instanceof LivingEntity target)) {
            return false; // if we hit arrow, snowball, fireball etc we dont care, but still want the other things to happen
        }
        NonNullList<ItemStack> inv = InventoryHelper.getGemOrder(player);
        ItemStack gemStack = inv.stream()
                .filter(gem -> gem.is(BMTags.Items.TARTARIC_GEM))
                .findFirst().orElse(ItemStack.EMPTY);

        EnumWillType type = EnumWillType.RAW;
        int tier = 0;
        ItemAttributeModifiers.Builder builder = defaultModifiers(baseDamage, baseAttackSpeed);
        if (!gemStack.isEmpty()) {
            double amount = gemStack.getOrDefault(BMDataComponents.DEMON_WILL_AMOUNT, 0d);
            tier = getTier(amount);
            type = gemStack.getOrDefault(BMDataComponents.DEMON_WILL_TYPE, type);
            if (tier > 0) {
                Map<EnumWillType, SentientStats> statsMap = stack.getItemHolder().getData(BMDataMaps.SENTIENT_STATS);
                if (statsMap == null) {
                    return false;
                }
                SentientStats stats = statsMap.get(type);
                builder.add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(BONUS_DAMAGE, stats.bonusDamage().calculate(tier), AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                );
                builder.add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(BONUS_ATTACK_SPEED, stats.bonusAttackSpeed().calculate(tier), AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                );
                for (SentientStats.Attributes entry : stats.attributes()) {
                    builder.add(entry.holder(), entry.getModifier(tier), EquipmentSlotGroup.MAINHAND);
                }

                for (SentientStats.Effects entry : stats.effects()) {
                    target.addEffect(entry.getEffect(tier), player);
                }
            }
            stack.set(BMDataComponents.SENTIENT_INFO, new SentientInfo(type, amount, tier, gemStack.getHoverName()));
            stack.set(BMDataComponents.DEMON_WILL_TYPE, type);
            gemStack.set(BMDataComponents.DEMON_WILL_AMOUNT, Math.max(amount - getDrain(tier), 0));
        } else {
            stack.set(BMDataComponents.SENTIENT_INFO, SentientInfo.NONE);
        }

        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, builder.build());

        Holder<MobEffect> holder = switch (type) {
            case RAW -> BMMobEffects.SOUL_SNARE_RAW;
            case CORROSIVE -> BMMobEffects.SOUL_SNARE_CORROSIVE;
            case DESTRUCTIVE -> BMMobEffects.SOUL_SNARE_DESTRUCTIVE;
            case STEADFAST -> BMMobEffects.SOUL_SNARE_STEADFAST;
            case VENGEFUL -> BMMobEffects.SOUL_SNARE_VENGEFUL;
        };
        MobEffectInstance instance = new MobEffectInstance(holder, 10 * 20, tier);
        target.addEffect(instance, player);

        return false;
    }

    private static double getDrain(int tier) {
        return tier * 0.05;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
        if (target.isDeadOrDying()) {
            attacker.setAbsorptionAmount(attacker.getAbsorptionAmount() + (target.getMaxHealth() / 10));
        }
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return !player.isCreative();
    }

    @Override
    public int getEnchantmentValue() {
        return 50; // TODO maybe a *little* high, but its whats been used in 1.20
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return stack.is(BMTags.Items.SHARDS)
                || stack.is(BMItems.SLATE_IMBUED); // TODO not sure if this makes sense here, taken from 1.20
    }
}
