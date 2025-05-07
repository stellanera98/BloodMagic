package wayoftime.bloodmagic.common.datacomponent;

import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import wayoftime.bloodmagic.common.registry.BMRegistries;
import wayoftime.bloodmagic.common.world.BMSavedData;
import wayoftime.bloodmagic.util.SoulTicket;

import java.util.UUID;

public class SoulNetwork {
    private UUID playerId;
    private int currentEssence;
    private BMSavedData parent;

    public static SoulNetwork newEmpty(UUID playerId, BMSavedData parent) {
        SoulNetwork soulNetwork = new SoulNetwork();
        soulNetwork.playerId = playerId;
        soulNetwork.parent = parent;
        return soulNetwork;
    }

    public UUID getPlayerId() {
        return this.playerId;
    }

    public int getCurrentEssence() {
        return currentEssence;
    }

    private void setCurrentEssence(int currentEssence) {
        this.currentEssence = currentEssence;
        markDirty();
    }

    private void markDirty() {
        if (parent != null)
            parent.setDirty();
    }

    public static SoulNetwork fromNBT(CompoundTag tag, BMSavedData parent) {
        SoulNetwork soulNetwork = new SoulNetwork();

        soulNetwork.playerId = tag.getUUID("playerId");
        soulNetwork.currentEssence = tag.getInt("currentEssence");
        soulNetwork.parent = parent;

        return soulNetwork;
    }

    public CompoundTag toNBT() {
        CompoundTag tag = new CompoundTag();

        tag.putUUID("playerId", getPlayerId());
        tag.putInt("currentEssence", getCurrentEssence());

        return tag;
    }

    public int add(SoulTicket ticket, int maximum) {
        int curr = getCurrentEssence();
        if (curr >= maximum)
            return 0;

        int newEss = Math.min(maximum, curr + ticket.getAmount());
        setCurrentEssence(newEss);

        return newEss - curr;
    }

    public int set(SoulTicket ticket, int maximum) {
        int val = Math.min(maximum, ticket.getAmount());
        setCurrentEssence(val);
        return val;
    }

    public void hurtPlayer(Player user, float syphon) {
        if (user != null) {
            if (syphon > 0) {
                if (!user.isCreative()) {
//                    int dmg = (int) ((syphon + 99F) / 100F); // cast to int rounds down, +99 makes it round up from orig
                    int dmg = Math.ceilDiv((int) syphon, 100);
                    user.invulnerableTime = 0;
                    user.hurt(new DamageSource(user.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE)
                            .getHolderOrThrow(BMRegistries.Keys.SACRIFICE_DAMAGE_KEY)), dmg);
                }
            }
        }
    }
}
