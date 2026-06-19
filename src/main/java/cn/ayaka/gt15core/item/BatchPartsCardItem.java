package cn.ayaka.gt15core.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BatchPartsCardItem extends Item {
    private static final int[] STEPS = new int[]{8, 16, 32, 64};
    public BatchPartsCardItem(Properties properties) { super(properties); }

    private int getBatch(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        int value = tag.getInt("BatchSize");
        return value <= 0 ? 8 : value;
    }

    private int next(int current) {
        for (int i = 0; i < STEPS.length; i++) if (STEPS[i] == current) return STEPS[(i + 1) % STEPS.length];
        return 8;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            int next = next(getBatch(stack));
            stack.getOrCreateTag().putInt("BatchSize", next);
            player.displayClientMessage(Component.translatable("message.gt15core.batch.size", next).withStyle(ChatFormatting.GREEN), true);
        }
        return InteractionResultHolder.success(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.gt15core.batch_parts_card.1", getBatch(stack)).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.gt15core.batch_parts_card.2").withStyle(ChatFormatting.DARK_AQUA));
    }
}
