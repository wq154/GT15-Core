package cn.ayaka.gt15core.multiblock;

import cn.ayaka.gt15core.GT15Core;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.LinkedHashMap;
import java.util.Map;

public class MultiblockControllerBlockEntity extends BlockEntity {
    private boolean formed;
    private int missingCount;
    private long lastScanGameTime;
    private final Map<String, Integer> missing = new LinkedHashMap<>();

    public MultiblockControllerBlockEntity(BlockPos pos, BlockState state) {
        super(GT15Core.MULTIBLOCK_CONTROLLER_BE.get(), pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, MultiblockControllerBlockEntity be) {
        if (level.isClientSide) {
            return;
        }
        long now = level.getGameTime();
        if (now - be.lastScanGameTime >= 100L) {
            be.scanNow(level, pos, state);
        }
    }

    public void scanNow(Level level, BlockPos center, BlockState state) {
        if (!(state.getBlock() instanceof MultiblockControllerBlock controller)) {
            return;
        }
        ControllerSpec spec = controller.getSpec();
        Map<String, Integer> found = new LinkedHashMap<>();
        int r = spec.radius();
        for (BlockPos p : BlockPos.betweenClosed(center.offset(-r, -r, -r), center.offset(r, r, r))) {
            BlockState checkState = level.getBlockState(p);
            ResourceLocation id = ForgeRegistries.BLOCKS.getKey(checkState.getBlock());
            if (id == null) {
                continue;
            }
            String key = id.toString();
            if (spec.requiredBlocks().containsKey(key)) {
                found.put(key, found.getOrDefault(key, 0) + 1);
            }
        }

        missing.clear();
        int totalMissing = 0;
        for (Map.Entry<String, Integer> need : spec.requiredBlocks().entrySet()) {
            int have = found.getOrDefault(need.getKey(), 0);
            if (have < need.getValue()) {
                int miss = need.getValue() - have;
                missing.put(need.getKey(), miss);
                totalMissing += miss;
            }
        }
        boolean newFormed = missing.isEmpty();
        if (newFormed != formed || totalMissing != missingCount) {
            formed = newFormed;
            missingCount = totalMissing;
            setChanged();
        } else {
            formed = newFormed;
            missingCount = totalMissing;
        }
        lastScanGameTime = level.getGameTime();
    }

    public boolean isFormed() {
        return formed;
    }

    public int getMissingCount() {
        return missingCount;
    }

    public long getLastScanGameTime() {
        return lastScanGameTime;
    }

    public Map<String, Integer> getMissing() {
        return new LinkedHashMap<>(missing);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("Formed", formed);
        tag.putInt("MissingCount", missingCount);
        tag.putLong("LastScanGameTime", lastScanGameTime);
        CompoundTag missingTag = new CompoundTag();
        for (Map.Entry<String, Integer> entry : missing.entrySet()) {
            missingTag.putInt(entry.getKey(), entry.getValue());
        }
        tag.put("Missing", missingTag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        formed = tag.getBoolean("Formed");
        missingCount = tag.getInt("MissingCount");
        lastScanGameTime = tag.getLong("LastScanGameTime");
        missing.clear();
        CompoundTag missingTag = tag.getCompound("Missing");
        for (String key : missingTag.getAllKeys()) {
            missing.put(key, missingTag.getInt(key));
        }
    }
}
