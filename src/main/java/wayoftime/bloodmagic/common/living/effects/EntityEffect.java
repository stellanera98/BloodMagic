package wayoftime.bloodmagic.common.living.effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.registry.BMRegistries;

import java.util.function.Function;

public interface EntityEffect {
    Codec<EntityEffect> CODEC = BMRegistries.ENTITY_EFFECT_TYPE_REGISTRY.byNameCodec().dispatch(EntityEffect::codec, Function.identity());
    DeferredRegister<MapCodec<? extends EntityEffect>> ENTITY_EFFECT_TYPE = DeferredRegister.create(BMRegistries.Keys.ENTITY_EFFECT_TYPE, BloodMagic.MODID);

    DeferredHolder<MapCodec<? extends EntityEffect>, MapCodec<MovementModifier>> MOVEMENT = ENTITY_EFFECT_TYPE.register("movement_modifier", () -> MovementModifier.CODEC);

    void apply(int level, Player wearer, Projectile projectile);

    MapCodec<? extends EntityEffect> codec();
}
