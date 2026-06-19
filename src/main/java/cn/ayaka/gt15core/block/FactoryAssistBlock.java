package cn.ayaka.gt15core.block;

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
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof FactoryAssistBlockEntity assist && player instanceof ServerPlayer serverPlayer) {
            ContainerData data = new ContainerData() {
                @Override
                public int get(int index) {
                    return switch (index) {
                        case 0 -> 2;
                        case 1 -> assist.isActive() ? 1 : 0;
                        case 2 -> assist.getWorkBuffer();
                        case 3 -> assist.getTickBudget();
                        case 4 -> assist.getAnalogSignal();
                        case 5 -> (int) (assist.getLastUpdateGameTime() % 1_000_000L);
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
            String line1 = safeLine(0);
            String line2 = safeLine(1);
            String line3 = "红石激活时累积低频预算；比较器输出用于自动化诊断。";
            NetworkHooks.openScreen(serverPlayer,
                    new SimpleMenuProvider((containerId, inventory, p) ->
                            new GT15DiagnosticMenu(containerId, inventory, pos, "factory", title, line1, line2, line3, data), Component.literal(title)),
                    buf -> {
                        buf.writeBlockPos(pos);
                        buf.writeUtf("factory", 64);
                        buf.writeUtf(title, 128);
                        buf.writeUtf(line1, 256);
                        buf.writeUtf(line2, 256);
                        buf.writeUtf(line3, 256);
                    });
        } else {
            player.sendSystemMessage(Component.literal("[GT15] 方块实体未就绪，请重新放置。")
                    .withStyle(ChatFormatting.RED));
        }
        return InteractionResult.CONSUME;
    }

    private String safeLine(int index) {
        return index >= 0 && index < hints.length ? hints[index] : "GT15 工厂辅助诊断节点。";
    }
}
