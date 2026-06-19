package cn.ayaka.gt15core.multiblock;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.LinkedHashMap;
import java.util.Map;

public class MultiblockControllerBlock extends Block {
    private final ControllerSpec spec;

    public MultiblockControllerBlock(BlockBehaviour.Properties properties, ControllerSpec spec) {
        super(properties);
        this.spec = spec;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide || hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.SUCCESS;
        }

        ScanResult result = scan(level, pos);
        player.sendSystemMessage(Component.literal("[GT15] " + spec.displayName()).withStyle(ChatFormatting.AQUA));
        if (result.complete()) {
            player.sendSystemMessage(Component.literal("结构检查完成：已满足 v1.1 多方块要求。后续会接入真实配方处理。")
                    .withStyle(ChatFormatting.GREEN));
        } else {
            player.sendSystemMessage(Component.literal("结构还缺这些方块：").withStyle(ChatFormatting.YELLOW));
            for (Map.Entry<String, Integer> entry : result.missing().entrySet()) {
                player.sendSystemMessage(Component.literal("- " + entry.getKey() + " x" + entry.getValue()).withStyle(ChatFormatting.GRAY));
            }
        }
        return InteractionResult.CONSUME;
    }

    private ScanResult scan(Level level, BlockPos center) {
        Map<String, Integer> found = new LinkedHashMap<>();
        int r = spec.radius();
        for (BlockPos p : BlockPos.betweenClosed(center.offset(-r, -r, -r), center.offset(r, r, r))) {
            BlockState state = level.getBlockState(p);
            ResourceLocation id = ForgeRegistries.BLOCKS.getKey(state.getBlock());
            if (id == null) {
                continue;
            }
            String key = id.toString();
            if (spec.requiredBlocks().containsKey(key)) {
                found.put(key, found.getOrDefault(key, 0) + 1);
            }
        }

        Map<String, Integer> missing = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> need : spec.requiredBlocks().entrySet()) {
            int have = found.getOrDefault(need.getKey(), 0);
            if (have < need.getValue()) {
                missing.put(need.getKey(), need.getValue() - have);
            }
        }
        return new ScanResult(missing);
    }

    private record ScanResult(Map<String, Integer> missing) {
        boolean complete() {
            return missing.isEmpty();
        }
    }
}
