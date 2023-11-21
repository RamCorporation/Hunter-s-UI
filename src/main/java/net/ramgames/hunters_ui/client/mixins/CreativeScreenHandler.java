package net.ramgames.hunters_ui.client.mixins;


import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(CreativeInventoryScreen.CreativeScreenHandler.class)
public abstract class CreativeScreenHandler extends ScreenHandler {

    protected CreativeScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/CreativeInventoryScreen$LockableSlot;<init>(Lnet/minecraft/inventory/Inventory;III)V"))
    public void moveCreativeSlots(Args args) {
        int index = args.get(1);
        args.set(2, (146+(20 * (index % 5))));
        args.set(3, (23+(20 * (index / 5))));
    }

    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;<init>(Lnet/minecraft/inventory/Inventory;III)V"))
    public void moveHotBarSlots(Args args) {
        int index = args.get(1);
        switch (index) {
            case 0,1,2,3,4 -> {
                args.set(2, 6+(20 * index));
                args.set(3, 22);
            }
            case 5,6,7,8 -> {
                args.set(2, 16+(20 * (index-5)));
                args.set(3, 42);
            }
        }
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void addInventorySlots(PlayerEntity player, CallbackInfo ci) {
        this.addSlot(new Slot(player.getInventory(), 9, 6, 75));
        this.addSlot(new Slot(player.getInventory(), 10, 86, 75));
        for (int i = 11; i < 36; i++)
            this.addSlot(new Slot(player.getInventory(), i, 6 + (20 * ((i-11) % 5)), 95 + (20 * ((i-11) / 5))));
    }
}
