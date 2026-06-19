package cn.ayaka.gt15core.multiblock;

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

import java.util.Map;

public class MultiblockControllerBlock extends BaseEntityBlock {
    private final ControllerSpec spec;

    public MultiblockControllerBlock(BlockBehaviour.Properties properties, ControllerSpec spec) {
        super(properties);
        this.spec = spec;
    }

    public ControllerSpec getSpec() {
        return spec;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MultiblockControllerBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : createTickerHelper(type, GT15Core.MULTIBLOCK_CONTROLLER_BE.get(), MultiblockControllerBlockEntity::serverTick);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof MultiblockControllerBlockEntity controller) {
            return controller.isFormed() ? 15 : Math.max(0, Math.min(14, 14 - controller.getMissingCount()));
        }
        return 0;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide || hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.SUCCESS;
        }

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof MultiblockControllerBlockEntity be) {
            be.scanNow(level, pos, state);
            player.sendSystemMessage(Component.literal("[GT15] " + spec.displayName()).withStyle(ChatFormatting.AQUA));
            if (be.isFormed()) {
                player.sendSystemMessage(Component.literal("结构检查完成：BlockEntity 已记录 formed=true，比较器输出 15。")
                        .withStyle(ChatFormatting.GREEN));
            } else {
                player.sendSystemMessage(Component.literal("结构未完成：缺失总数 " + be.getMissingCount() + "，比较器会输出诊断强度。")
                        .withStyle(ChatFormatting.YELLOW));
                for (Map.Entry<String, Integer> entry : be.getMissing().entrySet()) {
                    player.sendSystemMessage(Component.literal("- " + entry.getKey() + " x" + entry.getValue()).withStyle(ChatFormatting.GRAY));
                }
            }
            player.sendSystemMessage(Component.literal("上次扫描 gameTime=" + be.getLastScanGameTime() + "；自动扫描间隔 100 tick，低数据运行。")
                    .withStyle(ChatFormatting.DARK_AQUA));
            return InteractionResult.CONSUME;
        }

        player.sendSystemMessage(Component.literal("[GT15] 控制器缺少 BlockEntity，请重新放置方块。").withStyle(ChatFormatting.RED));
        return InteractionResult.CONSUME;
    }
}
