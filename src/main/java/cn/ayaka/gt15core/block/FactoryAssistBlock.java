package cn.ayaka.gt15core.block;

import cn.ayaka.gt15core.GT15Core;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class FactoryAssistBlock extends BaseEntityBlock {
    private final String title;
    private final String[] hints;

    public FactoryAssistBlock(BlockBehaviour.Properties properties, String title, String... hints) {
        super(properties);
        this.title = title;
        this.hints = hints;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FactoryAssistBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : createTickerHelper(type, GT15Core.FACTORY_ASSIST_BE.get(), FactoryAssistBlockEntity::serverTick);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof FactoryAssistBlockEntity assist) {
            return assist.getAnalogSignal();
        }
        return 0;
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
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof FactoryAssistBlockEntity assist) {
            player.sendSystemMessage(Component.literal("状态：" + (assist.isActive() ? "红石激活" : "待机")
                    + "，缓存预算=" + assist.getWorkBuffer()
                    + "，tick预算=" + assist.getTickBudget()
                    + "，比较器输出=" + assist.getAnalogSignal()).withStyle(ChatFormatting.GREEN));
        }
        player.sendSystemMessage(Component.literal("v0.1.1 已接入 BlockEntity：保存状态、低频预算累积、比较器诊断输出；不复制材料，不跳过电压阶段。")
                .withStyle(ChatFormatting.GREEN));
        return InteractionResult.CONSUME;
    }
}
