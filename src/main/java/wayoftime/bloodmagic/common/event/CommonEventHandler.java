package wayoftime.bloodmagic.common.event;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.api.BMTags;
import wayoftime.bloodmagic.api.capability.IWillHandler;
import wayoftime.bloodmagic.common.caps.BMCaps;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.api.datacomponent.Binding;
import wayoftime.bloodmagic.common.datacomponent.EnumWillType;
import wayoftime.bloodmagic.common.item.BMItems;
import wayoftime.bloodmagic.common.mobeffect.BMMobEffects;
import wayoftime.bloodmagic.util.InventoryHelper;

import java.util.Collection;
import java.util.Objects;

@EventBusSubscriber(modid = BloodMagic.MODID)
public class CommonEventHandler {

    @SubscribeEvent
    public static void mobKilled(LivingDropsEvent event) {
        DamageSource source = event.getSource();
        LivingEntity target = event.getEntity();
        Entity causer = source.getEntity();
        if (!(causer instanceof LivingEntity livingCauser)) {
            return;
        }
        Collection<MobEffectInstance> effects = target.getActiveEffects();
        EnumWillType type = null;
        int amplifier = 0;
        for (MobEffectInstance instance : effects) {
            if (instance.is(BMMobEffects.SOUL_SNARE_RAW)) {
                type = EnumWillType.RAW;
                amplifier = instance.getAmplifier();
                break;
            }
            if (instance.is(BMMobEffects.SOUL_SNARE_CORROSIVE)) {
                type = EnumWillType.CORROSIVE;
                amplifier = instance.getAmplifier();
                break;
            }
            if (instance.is(BMMobEffects.SOUL_SNARE_DESTRUCTIVE)) {
                type = EnumWillType.DESTRUCTIVE;
                amplifier = instance.getAmplifier();
                break;
            }
            if (instance.is(BMMobEffects.SOUL_SNARE_STEADFAST)) {
                type = EnumWillType.STEADFAST;
                amplifier = instance.getAmplifier();
                break;
            }
            if (instance.is(BMMobEffects.SOUL_SNARE_VENGEFUL)) {
                type = EnumWillType.VENGEFUL;
                amplifier = instance.getAmplifier();
                break;
            }
        }
        if (type == null) {
            return;
        }
        Collection<ItemEntity> drops = event.getDrops();
        Holder<Enchantment> looting = livingCauser.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.LOOTING);
        RandomSource random = livingCauser.getRandom();
        drops.add(new ItemEntity(livingCauser.level(), target.getX(), target.getY(), target.getZ(), makeDrop(type, amplifier, target.getMaxHealth(), random)));
        for (int i = 0; i < EnchantmentHelper.getEnchantmentLevel(looting, livingCauser); i++) {
            if (random.nextFloat() < 0.4f) {
                drops.add(new ItemEntity(livingCauser.level(), target.getX(), target.getY(), target.getZ(), makeDrop(type, amplifier, target.getMaxHealth(), random)));
            }
        }
    }

    public static ItemStack makeDrop(EnumWillType type, int amplifier, double maxHp, RandomSource random) {
        ItemStack willStack = new ItemStack(BMItems.MANIFESTED_WILL);
        willStack.set(BMDataComponents.DEMON_WILL_TYPE, type);
        double amount = (3 * amplifier * random.nextDouble()) + (amplifier/2) * maxHp / 20d;
        if (amplifier == 0) {
            amount = 1 + random.nextDouble() * 4;
        }
        willStack.set(BMDataComponents.DEMON_WILL_AMOUNT, amount);

        return willStack;
    }

    @SubscribeEvent
    public static void itemPickup(ItemEntityPickupEvent.Pre event) {
        ItemStack stack = event.getItemEntity().getItem();
        if (!stack.is(BMItems.MANIFESTED_WILL)) {
            return;
        }
        EnumWillType type = stack.getOrDefault(BMDataComponents.DEMON_WILL_TYPE, EnumWillType.RAW);
        double amount = stack.getOrDefault(BMDataComponents.DEMON_WILL_AMOUNT, 0D);
        NonNullList<ItemStack> inv = InventoryHelper.getGemOrder(event.getPlayer());

        for (int i = 0; i < inv.size(); i++) {
            ItemStack gemStack = inv.get(i);
            if (gemStack.is(BMTags.Items.TARTARIC_GEM)) {
                IWillHandler gemHandler = gemStack.getCapability(BMCaps.ITEM_WILL_HANDLER);
                if (gemHandler != null) {
                    amount -= gemHandler.fill(type, amount, true);
                    if (amount <= 0) {
                        break;
                    }
                }
            }
        }
        if (amount <= 0) {
            stack.shrink(1);
            event.setCanPickup(TriState.TRUE);
        } else {
            stack.set(BMDataComponents.DEMON_WILL_AMOUNT, amount);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onInteract(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();

        if (player instanceof FakePlayer)
            return;

        ItemStack held = event.getItemStack();
        if (held.isEmpty()) {
            return;
        }

        Binding binding = held.get(BMDataComponents.BINDING);
        if (binding == null) {
            return;
        }
        GameProfile profile = event.getEntity().getGameProfile();
        if (binding.isEmpty()) {
            binding = new Binding(profile.getId(), profile.getName());
            if (NeoForge.EVENT_BUS.post(new ItemBindEvent(event.getEntity(), held)).isCanceled()) {
                return;
            }
            held.set(BMDataComponents.BINDING, binding);
        } else if (binding.uuid() == profile.getId() && !Objects.equals(binding.name(), profile.getName())) {
            binding = new Binding(profile.getId(), profile.getName());
            held.set(BMDataComponents.BINDING, binding);
        }
    }
}
