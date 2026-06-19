package cn.ayaka.gt15core.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IOBlueprintItem extends Item {
    public IOBlueprintItem(Properties properties) { super(properties); }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level level = ctx.getLevel();
        Player player = ctx.getPlayer();
        ItemStack stack = ctx.getItemInHand();
        if (level.isClientSide || player == null) return InteractionResult.SUCCESS;

        BlockPos pos = ctx.getClickedPos();
        ResourceLocation blockId = ForgeRegistries.BLOCKS.getKey(level.getBlockState(pos).getBlock());
        BlockEntity be = level.getBlockEntity(pos);
        CompoundTag tag = stack.getOrCreateTag();
        tag.putString("Dimension", level.dimension().location().toString());
        tag.putString("Block", blockId == null ? "unknown" : blockId.toString());
        tag.putString("Face", ctx.getClickedFace().getName());
        tag.putInt("X", pos.getX());
        tag.putInt("Y", pos.getY());
        tag.putInt("Z", pos.getZ());
        tag.putString("BlockEntity", be == null ? "none" : be.getType().toString());
        player.displayClientMessage(Component.translatable("message.gt15core.io.saved", tag.getString("Block"), tag.getString("Face"), pos.toShortString()).withStyle(ChatFormatting.AQUA), false);
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            CompoundTag tag = stack.getTag();
            if (tag == null || !tag.contains("Block")) {
                player.displayClientMessage(Component.translatable("message.gt15core.io.empty").withStyle(ChatFormatting.YELLOW), false);
            } else {
                player.displayClientMessage(Component.literal("[GT15 IO] ")
                        .append(Component.literal(tag.getString("Block") + " @ " + tag.getString("Dimension") + " " + tag.getInt("X") + "," + tag.getInt("Y") + "," + tag.getInt("Z") + " face=" + tag.getString("Face")))
                        .withStyle(ChatFormatting.AQUA), false);
            }
        }
        return InteractionResultHolder.success(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.gt15core.io_blueprint.1").withStyle(ChatFormatting.GRAY));
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains("Block")) {
            tooltip.add(Component.literal(tag.getString("Block") + " | " + tag.getString("Face")).withStyle(ChatFormatting.DARK_AQUA));
        }
    }
}
