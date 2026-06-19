package cn.ayaka.gt15core.client;

import cn.ayaka.gt15core.menu.GT15DiagnosticMenu;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GT15DiagnosticScreen extends AbstractContainerScreen<GT15DiagnosticMenu> {
    public GT15DiagnosticScreen(GT15DiagnosticMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 248;
        this.imageHeight = 166;
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int x = leftPos;
        int y = topPos;
        graphics.fill(x, y, x + imageWidth, y + imageHeight, 0xEE0B1320);
        graphics.fill(x + 4, y + 4, x + imageWidth - 4, y + 22, 0xFF18384A);
        graphics.fill(x + 8, y + 34, x + imageWidth - 8, y + imageHeight - 10, 0xAA101820);
        graphics.fill(x + 8, y + 28, x + imageWidth - 8, y + 30, 0xFF5AD8FF);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        draw(graphics, menu.getScreenTitle(), 10, 10, 0x72E8FF);
        draw(graphics, "类型: " + (menu.getKind().equals("controller") ? "多方块控制器" : "工厂辅助方块"), 12, 36, 0xFFFFFF);
        draw(graphics, "坐标: " + menu.getPos().toShortString(), 12, 48, 0xBBBBBB);
        draw(graphics, menu.getLine1(), 12, 64, 0xDDDDDD);
        draw(graphics, menu.getLine2(), 12, 76, 0xDDDDDD);
        draw(graphics, menu.getLine3(), 12, 88, 0xDDDDDD);

        if (menu.getKind().equals("controller")) {
            boolean formed = menu.getDataValue(1) == 1;
            int missing = menu.getDataValue(2);
            int lastScan = menu.getDataValue(3);
            int analog = menu.getDataValue(4);
            draw(graphics, "结构状态: " + (formed ? "已成型" : "未完成"), 12, 110, formed ? 0x60FF9A : 0xFFD45A);
            draw(graphics, "缺失总数: " + missing + "    比较器: " + analog, 12, 122, 0xCFEFFF);
            draw(graphics, "上次扫描 tick(取模): " + lastScan + "    自动扫描: 100 tick", 12, 134, 0x8BD6FF);
        } else {
            boolean active = menu.getDataValue(1) == 1;
            int buffer = menu.getDataValue(2);
            int budget = menu.getDataValue(3);
            int analog = menu.getDataValue(4);
            draw(graphics, "运行状态: " + (active ? "红石激活" : "待机"), 12, 110, active ? 0x60FF9A : 0xFFD45A);
            draw(graphics, "缓存预算: " + buffer + " / 10000    tick预算: " + budget, 12, 122, 0xCFEFFF);
            draw(graphics, "比较器输出: " + analog + "    低频更新: 20 tick", 12, 134, 0x8BD6FF);
        }
        draw(graphics, ChatFormatting.GRAY + "GT15 Core 0.1.2 诊断界面：只做低数据展示，不移动物品实体。", 12, 152, 0xAAAAAA);
    }

    private void draw(GuiGraphics graphics, String text, int x, int y, int color) {
        graphics.drawString(this.font, Component.literal(text), x, y, color, false);
    }
}
