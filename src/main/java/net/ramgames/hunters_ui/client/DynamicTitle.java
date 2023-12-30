package net.ramgames.hunters_ui.client;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public interface DynamicTitle {

    Identifier TEXTURE = new Identifier("minecraft:textures/gui/container/title.png");

    static void applyTitleBox(DrawContext context, TextRenderer textRenderer, Text title, int x, int y) {
        int width = textRenderer.getWidth(title);
        int centerX = x - (width / 2);
        context.drawTexture(TEXTURE, centerX, y-5, 0, 0, 4, 16, 16, 16);
        for(int i = 0; i < width; i++) context.drawTexture(TEXTURE, centerX+4+i, y-5, 5, 0, 1, 16, 16, 16);
        context.drawTexture(TEXTURE, centerX+width+4, y-5, 7, 0, 4, 16, 16, 16);
        context.drawText(textRenderer, title, centerX+4, y-1, 0x404040, false);
    }



}
