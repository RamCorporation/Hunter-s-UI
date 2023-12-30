package net.ramgames.hunters_ui.client.mixins;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.client.gui.screen.ingame.FurnaceScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.recipebook.AbstractFurnaceRecipeBookScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.ramgames.hunters_ui.client.DynamicTitle;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(AbstractFurnaceScreen.class)
public abstract class AbstractFurnaceScreenMixin<T extends AbstractFurnaceScreenHandler> extends HandledScreen<T> {

    @Final
    @Shadow
    public AbstractFurnaceRecipeBookScreen recipeBook;

    public AbstractFurnaceScreenMixin(T handler, PlayerInventory inventory, Text title) {
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



    @ModifyConstant(method = "drawBackground", constant = @Constant(intValue = 56))
    public int moveFuelX(int constant) {
        return 47;
    }

    @ModifyConstant(method = "drawBackground", constant = @Constant(intValue = 36))
    public int moveFuelY(int constant) {
        return 88;
    }

    @ModifyConstant(method = "drawBackground", constant = @Constant(floatValue = 24f))
    public float changeProgressWidth(float constant) {
        return 16f;
    }

    @ModifyArgs(method = "drawBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIIIIIII)V"))
    public void moveSprites(Args args) {
        Identifier identifier = args.get(0);
        if(identifier.getPath().endsWith("burn_progress")) {
            args.set(1, 16);
            args.set(2, 15);
            args.set(5, this.x+45);
            args.set(6 , this.y+59);
            args.set(8, 15);
        }
    }
}
