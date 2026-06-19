package cn.ayaka.gt15core.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OrderBridgeCardItem extends Item {
    public OrderBridgeCardItem(Properties properties) { super(properties); }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack card = player.getItemInHand(hand);
        if (!level.isClientSide) {
            int patterns = 0, stamped = 0, gtMachines = 0, ae2Parts = 0;
            for (ItemStack stack : player.getInventory().items) {
                if (stack.isEmpty()) continue;
                ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());
                if (id == null) continue;
                if (id.getNamespace().equals("ae2") && id.getPath().contains("pattern")) {
                    patterns += stack.getCount();
                    if (stack.hasTag() && stack.getTag().contains("gt15core_multiplier")) stamped += stack.getCount();
                }
                if (id.getNamespace().equals("gtceu") && (id.getPath().contains("machine") || id.getPath().contains("hatch") || id.getPath().contains("bus"))) gtMachines += stack.getCount();
                if (id.getNamespace().equals("ae2")) ae2Parts += stack.getCount();
            }
            player.displayClientMessage(Component.literal("[GT15 Order] AE2 patterns=" + patterns + ", stamped=" + stamped + ", GT machine parts=" + gtMachines + ", AE2 parts=" + ae2Parts).withStyle(ChatFormatting.AQUA), false);
        }
        return InteractionResultHolder.success(card);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.gt15core.order_bridge_card.1").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.gt15core.order_bridge_card.2").withStyle(ChatFormatting.DARK_AQUA));
    }
}
