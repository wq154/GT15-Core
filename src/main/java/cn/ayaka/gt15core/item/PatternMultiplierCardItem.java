package cn.ayaka.gt15core.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
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

public class PatternMultiplierCardItem extends Item {
    private static final int[] STEPS = new int[]{1, 2, 5, 10};

    public PatternMultiplierCardItem(Properties properties) { super(properties); }

    private int getMultiplier(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        int value = tag.getInt("Multiplier");
        return value <= 0 ? 1 : value;
    }

    private int next(int current) {
        for (int i = 0; i < STEPS.length; i++) {
            if (STEPS[i] == current) return STEPS[(i + 1) % STEPS.length];
        }
        return 1;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack card = player.getItemInHand(hand);
        if (level.isClientSide) return InteractionResultHolder.success(card);

        int mult = getMultiplier(card);
        ItemStack other = player.getItemInHand(hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);
        ResourceLocation otherId = other.isEmpty() ? null : ForgeRegistries.ITEMS.getKey(other.getItem());
        boolean isPattern = otherId != null && otherId.getNamespace().equals("ae2") && otherId.getPath().contains("pattern");

        if (isPattern) {
            other.getOrCreateTag().putInt("gt15core_multiplier", mult);
            player.displayClientMessage(Component.translatable("message.gt15core.pattern.stamped", other.getHoverName(), mult).withStyle(ChatFormatting.AQUA), false);
        } else {
            int next = next(mult);
            card.getOrCreateTag().putInt("Multiplier", next);
            player.displayClientMessage(Component.translatable("message.gt15core.pattern.multiplier", next).withStyle(ChatFormatting.GREEN), true);
        }
        return InteractionResultHolder.success(card);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.gt15core.pattern_multiplier_card.1", getMultiplier(stack)).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.gt15core.pattern_multiplier_card.2").withStyle(ChatFormatting.DARK_AQUA));
    }
}
