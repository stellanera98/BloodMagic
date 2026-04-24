package wayoftime.bloodmagic.common.item;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import wayoftime.bloodmagic.api.capability.IWandConfigurable;
import wayoftime.bloodmagic.common.caps.BMCaps;
import wayoftime.bloodmagic.common.datacomponent.BMDataComponents;
import wayoftime.bloodmagic.common.datacomponent.EnumWillType;
import wayoftime.bloodmagic.util.ChatUtil;

import java.util.List;
import java.util.function.Consumer;

public class WandItem extends Item {

    public WandItem() {
        super(new Properties()
                .stacksTo(1)
        );
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        EnumWillType type = stack.get(BMDataComponents.DEMON_WILL_TYPE);
        String baseId = Util.makeDescriptionId("item", stack.getItemHolder().getKey().location());
        return type == null ? baseId : baseId + "." + type.getSerializedName();
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        boolean isShiftDown = context.isSecondaryUseActive();
        ItemStack wand = context.getItemInHand();
        BlockPos stored = wand.get(BMDataComponents.STORED_POSITION);
        EnumWillType type = wand.get(BMDataComponents.DEMON_WILL_TYPE);
        Consumer<MutableComponent> sender = text -> {
            if (level.isClientSide) {
                ChatUtil.sendChatNoSpam(player, List.of(text));
            }
        };

        IWandConfigurable source;
        if (stored == null) {
            source = level.getCapability(BMCaps.WAND_CONFIGURABLE, pos);
            if (source == null) {
                return InteractionResult.PASS;
            }
            if (isShiftDown) {
                if (type != null) {
                    source.toggleWillType(type, sender);
                    return InteractionResult.sidedSuccess(level.isClientSide);
                } else {
                    // TODO has shift down but no type stored?
                    return InteractionResult.PASS;
                }
            } else {
                source.toggleSelectedState(true);
                wand.set(BMDataComponents.STORED_POSITION, pos);
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }

        source = level.getCapability(BMCaps.WAND_CONFIGURABLE, stored);
        if (source == null) {
            wand.remove(BMDataComponents.STORED_POSITION); // probably removed source block in-between, remove it
            return InteractionResult.FAIL;
        }

        if (!isShiftDown) { // link
            source.addConnection(pos, type, sender);
        } else { // delete
            source.removeConnection(pos, sender);
        }

        source.toggleSelectedState(false);
        wand.remove(BMDataComponents.STORED_POSITION); // we are done with this action
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack wand = player.getItemInHand(usedHand);
        EnumWillType type = wand.get(BMDataComponents.DEMON_WILL_TYPE);
        boolean dir = player.isShiftKeyDown();
        EnumWillType next = switch (type) {
            case RAW -> dir ? null : EnumWillType.CORROSIVE;
            case CORROSIVE -> dir ? EnumWillType.RAW : EnumWillType.DESTRUCTIVE;
            case DESTRUCTIVE -> dir ? EnumWillType.CORROSIVE : EnumWillType.STEADFAST;
            case STEADFAST -> dir ? EnumWillType.DESTRUCTIVE : EnumWillType.VENGEFUL;
            case VENGEFUL -> dir ? EnumWillType.STEADFAST : null;
            case null -> dir ? EnumWillType.VENGEFUL : EnumWillType.RAW;
        };
        if (next == null) {
            wand.remove(BMDataComponents.DEMON_WILL_TYPE);
        } else {
            wand.set(BMDataComponents.DEMON_WILL_TYPE, next);
        }
        
        return InteractionResultHolder.sidedSuccess(wand, level.isClientSide);
    }
}
