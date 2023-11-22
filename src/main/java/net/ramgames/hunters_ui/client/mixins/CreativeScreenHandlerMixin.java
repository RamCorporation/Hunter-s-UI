package net.ramgames.hunters_ui.client.mixins;


import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(CreativeInventoryScreen.CreativeScreenHandler.class)
public abstract class CreativeScreenHandlerMixin extends ScreenHandler {




    protected CreativeScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId) {
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

    @ModifyConstant(method = "scrollItems", constant = @Constant(intValue = 5))
    public int changeAssumedInventoryRows(int constant) {
        return 9;
    }

    @ModifyConstant(method = "scrollItems", constant = @Constant(intValue = 9))
    public int changeAssumedInventoryColumns(int constant) {
        return 5;
    }

    @ModifyConstant(method = "getOverflowRows", constant = @Constant(intValue = 9))
    public int changeSlotsPerColumns(int constant) {
        return 5;
    }
    @ModifyConstant(method = "getOverflowRows", constant = @Constant(intValue = 5))
    public int changeNumberOfColumns(int constant) {
        return 9;
    }
}
