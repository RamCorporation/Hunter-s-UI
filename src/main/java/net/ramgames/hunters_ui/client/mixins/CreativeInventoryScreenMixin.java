package net.ramgames.hunters_ui.client.mixins;


import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenMixin extends AbstractInventoryScreen<CreativeInventoryScreen.CreativeScreenHandler> {


    public CreativeInventoryScreenMixin(CreativeInventoryScreen.CreativeScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void setBackgroundSize(PlayerEntity player, FeatureSet enabledFeatures, boolean operatorTabEnabled, CallbackInfo ci) {
        backgroundWidth = 249;
        backgroundHeight = 226;
    }

    @ModifyArgs(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;<init>(Lnet/minecraft/client/font/TextRenderer;IIIILnet/minecraft/text/Text;)V"))
    public void moveSearchBar(Args args) {
        args.set(1, 68+(int) args.get(1));
        args.set(2, 4+(int) args.get(2));
    }

    @ModifyArgs(method = "drawBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V"))
    public void moveSlider(Args args) {
        args.set(1, -47+(int) args.get(1));
        args.set(2, 38+(int) args.get(2));
    }

    @Inject(method = "isClickInScrollbar", at = @At("HEAD"), cancellable = true)
    public void moveSliderHotbox(double mouseX, double mouseY, CallbackInfoReturnable<Boolean> cir) {

        int topLeftX = this.x + 128;
        int topLeftY = this.y + 45;
        int bottomRightX = this.x + 140;
        int bottomRightY = this.y + 225;
        cir.setReturnValue(mouseX >= topLeftX && mouseX <= bottomRightX && mouseY >= topLeftY && mouseY <= bottomRightY);
    }


    @ModifyConstant(method = "mouseDragged", constant = @Constant(intValue = 18))
    public int adjustSliderTopYCalculation(int constant) {
        return constant+30;
    }

    @ModifyConstant(method = "mouseDragged", constant = @Constant(intValue = 112))
    public int adjustSliderBottomYCalculation(int constant) {
        return constant+13;
    }

    @ModifyArgs(method = "drawForeground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawText(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;IIIZ)I"))
    public void moveTabTitle(Args args) {
        int width = ((TextRenderer) args.get(0)).getWidth(((Text) args.get(1)));
        args.set(2, (width % 2) + 120 + (width / 2));
        args.set(3, 205);
    }
}
