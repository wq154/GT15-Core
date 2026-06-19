package cn.ayaka.gt15core.menu;

import cn.ayaka.gt15core.GT15Core;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;

public class GT15DiagnosticMenu extends AbstractContainerMenu {
    public static final int DATA_SIZE = 6;

    private final BlockPos pos;
    private final String kind;
    private final String screenTitle;
    private final String line1;
    private final String line2;
    private final String line3;
    private final ContainerData data;

    public GT15DiagnosticMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(containerId, inventory,
                buf.readBlockPos(),
                buf.readUtf(64),
                buf.readUtf(128),
                buf.readUtf(256),
                buf.readUtf(256),
                buf.readUtf(256),
                new SimpleContainerData(DATA_SIZE));
    }

    public GT15DiagnosticMenu(int containerId, Inventory inventory, BlockPos pos, String kind, String screenTitle,
                              String line1, String line2, String line3, ContainerData data) {
        super(GT15Core.DIAGNOSTIC_MENU.get(), containerId);
        this.pos = pos;
        this.kind = kind;
        this.screenTitle = screenTitle;
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.data = data;
        addDataSlots(data);
    }

    @Override
    public boolean stillValid(Player player) {
        return player.distanceToSqr(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D * 64.0D;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    public int getDataValue(int index) {
        if (index < 0 || index >= DATA_SIZE) {
            return 0;
        }
        return data.get(index);
    }

    public BlockPos getPos() {
        return pos;
    }

    public String getKind() {
        return kind;
    }

    public String getScreenTitle() {
        return screenTitle;
    }

    public String getLine1() {
        return line1;
    }

    public String getLine2() {
        return line2;
    }

    public String getLine3() {
        return line3;
    }
}
