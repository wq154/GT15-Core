package cn.ayaka.gt15core.multiblock;

import cn.ayaka.gt15core.GT15Core;
import cn.ayaka.gt15core.menu.GT15DiagnosticMenu;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

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
        if (blockEntity instanceof MultiblockControllerBlockEntity be && player instanceof ServerPlayer serverPlayer) {
            be.scanNow(level, pos, state);
            ContainerData data = new ContainerData() {
                @Override
                public int get(int index) {
                    return switch (index) {
                        case 0 -> spec.radius();
                        case 1 -> be.isFormed() ? 1 : 0;
                        case 2 -> be.getMissingCount();
                        case 3 -> (int) (be.getLastScanGameTime() % 1_000_000L);
                        case 4 -> getAnalogOutputSignal(state, level, pos);
                        case 5 -> spec.requiredBlocks().size();
                        default -> 0;
                    };
                }

                @Override
                public void set(int index, int value) {
                }

                @Override
                public int getCount() {
                    return GT15DiagnosticMenu.DATA_SIZE;
                }
            };
            String line1 = "扫描半径 " + spec.radius() + "，需求方块种类 " + spec.requiredBlocks().size() + "。";
            String line2 = "每 100 tick 自动低频扫描，避免大型工厂卡顿。";
            String line3 = be.isFormed() ? "结构已成型，可作为任务/自动化锚点。" : "当前缺失总数 " + be.getMissingCount() + "，右侧比较器可读诊断强度。";
            NetworkHooks.openScreen(serverPlayer,
                    new SimpleMenuProvider((containerId, inventory, p) ->
                            new GT15DiagnosticMenu(containerId, inventory, pos, "controller", spec.displayName(), line1, line2, line3, data), Component.literal(spec.displayName())),
                    buf -> {
                        buf.writeBlockPos(pos);
                        buf.writeUtf("controller", 64);
                        buf.writeUtf(spec.displayName(), 128);
                        buf.writeUtf(line1, 256);
                        buf.writeUtf(line2, 256);
                        buf.writeUtf(line3, 256);
                    });
            return InteractionResult.CONSUME;
        }

        player.sendSystemMessage(Component.literal("[GT15] 控制器缺少 BlockEntity，请重新放置方块。").withStyle(ChatFormatting.RED));
        return InteractionResult.CONSUME;
    }
}
