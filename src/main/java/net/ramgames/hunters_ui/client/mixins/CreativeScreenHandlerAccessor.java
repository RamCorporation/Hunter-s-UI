package net.ramgames.hunters_ui.client.mixins;

import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CreativeInventoryScreen.CreativeScreenHandler.class)
public interface CreativeScreenHandlerAccessor {

    @Invoker("getScrollPosition")
    float getScrollPositionInvoker(float scrollPosition, double verticalAmount);

}
