package cn.ayaka.gt15core.block;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;

public class FactoryAssistBlock extends Block {
    private final String title;
    private final String[] hints;

    public FactoryAssistBlock(BlockBehaviour.Properties properties, String title, String... hints) {
        super(properties);
        this.title = title;
        this.hints = hints;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide || hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.SUCCESS;
        }
        player.sendSystemMessage(Component.literal("[GT15] " + title).withStyle(ChatFormatting.AQUA));
        for (String hint : hints) {
            player.sendSystemMessage(Component.literal("- " + hint).withStyle(ChatFormatting.GRAY));
        }
        player.sendSystemMessage(Component.literal("当前版本为轻量诊断/任务方块：不复制材料，不跳过电压阶段。后续可扩展为真实菜单与缓存逻辑。")
                .withStyle(ChatFormatting.GREEN));
        return InteractionResult.CONSUME;
    }
}
