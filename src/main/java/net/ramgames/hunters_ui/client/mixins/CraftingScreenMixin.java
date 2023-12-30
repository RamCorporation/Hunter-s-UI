package net.ramgames.hunters_ui.client.mixins;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.text.Text;
import net.ramgames.hunters_ui.client.DynamicTitle;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingScreen.class)
public abstract class CraftingScreenMixin extends HandledScreen<CraftingScreenHandler> {

    @Final
    @Shadow
    private RecipeBookWidget recipeBook;

    public CraftingScreenMixin(CraftingScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    public void adjustScreenSize(CallbackInfo ci) {
        backgroundWidth = 219;
        backgroundHeight = 200;
        titleX = 53;
        super.init();
        this.recipeBook.initialize(this.width, this.height, this.client, false, this.handler);
        ci.cancel();
    }

    @Unique
    @Override
    public void drawForeground(DrawContext context, int mouseX, int mouseY) {
        DynamicTitle.applyTitleBox(context, textRenderer, title,titleX, titleY);
    }
}
