package cn.ayaka.gt15core.block;

import cn.ayaka.gt15core.GT15Core;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FactoryAssistBlockEntity extends BlockEntity {
    private boolean active;
    private int workBuffer;
    private int tickBudget = 20;
    private long lastUpdateGameTime;

    public FactoryAssistBlockEntity(BlockPos pos, BlockState state) {
        super(GT15Core.FACTORY_ASSIST_BE.get(), pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, FactoryAssistBlockEntity be) {
        if (level.isClientSide) {
            return;
        }
        long now = level.getGameTime();
        if (now - be.lastUpdateGameTime < 20L) {
            return;
        }
        boolean powered = level.hasNeighborSignal(pos);
        int oldBuffer = be.workBuffer;
        boolean oldActive = be.active;
        be.active = powered;
        if (powered) {
            be.workBuffer = Math.min(10_000, be.workBuffer + be.tickBudget);
        } else if (be.workBuffer > 0) {
            be.workBuffer = Math.max(0, be.workBuffer - 1);
        }
        be.lastUpdateGameTime = now;
        if (oldBuffer != be.workBuffer || oldActive != be.active) {
            be.setChanged();
            level.updateNeighbourForOutputSignal(pos, state.getBlock());
        }
    }

    public boolean isActive() {
        return active;
    }

    public int getWorkBuffer() {
        return workBuffer;
    }

    public int getTickBudget() {
        return tickBudget;
    }

    public int getAnalogSignal() {
        return Math.max(0, Math.min(15, workBuffer / 667));
    }

    public long getLastUpdateGameTime() {
        return lastUpdateGameTime;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("Active", active);
        tag.putInt("WorkBuffer", workBuffer);
        tag.putInt("TickBudget", tickBudget);
        tag.putLong("LastUpdateGameTime", lastUpdateGameTime);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        active = tag.getBoolean("Active");
        workBuffer = tag.getInt("WorkBuffer");
        tickBudget = tag.contains("TickBudget") ? tag.getInt("TickBudget") : 20;
        lastUpdateGameTime = tag.getLong("LastUpdateGameTime");
    }
}
