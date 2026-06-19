package cn.ayaka.gt15core.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MaintenanceKitItem extends Item {
    public MaintenanceKitItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack kit = player.getItemInHand(hand);
        if (level.isClientSide) {
            return InteractionResultHolder.success(kit);
        }

        int repaired = 0;
        for (ItemStack stack : player.getInventory().items) {
            if (repaired >= 96) break;
            if (stack.isEmpty() || stack == kit || !stack.isDamageableItem() || !stack.isDamaged()) continue;
            int repair = Math.min(32, stack.getDamageValue());
            stack.setDamageValue(stack.getDamageValue() - repair);
            repaired += repair;
        }

        if (repaired <= 0) {
            player.displayClientMessage(Component.translatable("message.gt15core.maintenance.none").withStyle(ChatFormatting.YELLOW), true);
            return InteractionResultHolder.pass(kit);
        }

        int kitDamage = Math.max(1, (repaired + 15) / 16);
        kit.hurtAndBreak(kitDamage, player, p -> p.broadcastBreakEvent(hand));
        player.displayClientMessage(Component.translatable("message.gt15core.maintenance.done", repaired).withStyle(ChatFormatting.AQUA), true);
        return InteractionResultHolder.success(kit);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.gt15core.maintenance_kit.1").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.gt15core.maintenance_kit.2").withStyle(ChatFormatting.DARK_AQUA));
    }
}
