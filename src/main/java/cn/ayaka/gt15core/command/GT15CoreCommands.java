package cn.ayaka.gt15core.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class GT15CoreCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("gt15core")
                .then(Commands.literal("check-patterns").executes(ctx -> checkPatterns(ctx.getSource())))
                .then(Commands.literal("factory-scan")
                        .executes(ctx -> factoryScan(ctx.getSource(), 12))
                        .then(Commands.argument("radius", IntegerArgumentType.integer(4, 24))
                                .executes(ctx -> factoryScan(ctx.getSource(), IntegerArgumentType.getInteger(ctx, "radius"))))));
    }

    private static int checkPatterns(CommandSourceStack source) throws com.mojang.brigadier.exceptions.CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        int patterns = 0, stamped = 0, x1 = 0, x2 = 0, x5 = 0, x10 = 0;
        for (ItemStack stack : player.getInventory().items) {
            ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());
            if (id == null || !id.getNamespace().equals("ae2") || !id.getPath().contains("pattern")) continue;
            patterns += stack.getCount();
            if (stack.hasTag() && stack.getTag().contains("gt15core_multiplier")) {
                stamped += stack.getCount();
                int m = stack.getTag().getInt("gt15core_multiplier");
                if (m == 2) x2 += stack.getCount(); else if (m == 5) x5 += stack.getCount(); else if (m == 10) x10 += stack.getCount(); else x1 += stack.getCount();
            }
        }
        final int patternsFinal = patterns;
        final int stampedFinal = stamped;
        final int x1Final = x1;
        final int x2Final = x2;
        final int x5Final = x5;
        final int x10Final = x10;
        source.sendSuccess(() -> Component.literal("[GT15 Core] AE2样板：总数 " + patternsFinal + "，已倍率标记 " + stampedFinal + "（x1=" + x1Final + ", x2=" + x2Final + ", x5=" + x5Final + ", x10=" + x10Final + "）").withStyle(ChatFormatting.AQUA), false);
        if (patternsFinal > stampedFinal) {
            source.sendSuccess(() -> Component.literal("提示：未标记样板建议用“样板倍率卡”盖章，后续订单桥会按倍率合并请求。" ).withStyle(ChatFormatting.YELLOW), false);
        }
        return patterns;
    }

    private static int factoryScan(CommandSourceStack source, int radius) throws com.mojang.brigadier.exceptions.CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        Level level = player.level();
        BlockPos center = player.blockPosition();
        Map<String, Integer> counts = new HashMap<>();
        int gtBlocks = 0, ae2Blocks = 0, blockEntities = 0;
        for (BlockPos pos : BlockPos.betweenClosed(center.offset(-radius, -radius, -radius), center.offset(radius, radius, radius))) {
            if (level.isEmptyBlock(pos)) continue;
            Block block = level.getBlockState(pos).getBlock();
            ResourceLocation id = ForgeRegistries.BLOCKS.getKey(block);
            if (id == null) continue;
            if (level.getBlockEntity(pos) != null) blockEntities++;
            if (id.getNamespace().equals("gtceu") || id.getNamespace().equals("ae2")) {
                counts.merge(id.getNamespace(), 1, Integer::sum);
                if (id.getNamespace().equals("gtceu")) gtBlocks++;
                if (id.getNamespace().equals("ae2")) ae2Blocks++;
            }
        }
        int beFinal = blockEntities, gtFinal = gtBlocks, aeFinal = ae2Blocks;
        source.sendSuccess(() -> Component.literal("[GT15 Core] 半径 " + radius + " 工厂扫描：GT方块=" + gtFinal + "，AE2方块=" + aeFinal + "，方块实体=" + beFinal).withStyle(ChatFormatting.AQUA), false);
        if (beFinal > 180) {
            source.sendSuccess(() -> Component.literal("性能提示：附近方块实体偏多，建议用大型机器、批处理接口和订单桥减少小机器数量。" ).withStyle(ChatFormatting.YELLOW), false);
        }
        return gtBlocks + ae2Blocks;
    }
}
