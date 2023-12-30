package net.ramgames.hunters_ui.client.mixins;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(AbstractFurnaceScreenHandler.class)
public class AbstractFurnaceScreenHandlerMixin {

    @ModifyArgs(method = "<init>*", at = @At(value = "INVOKE", target = "net/minecraft/screen/slot/Slot.<init> (Lnet/minecraft/inventory/Inventory;III)V"))
    private void MoveInventorySlots(Args args) {
        int index = args.get(1);
        if(args.get(0) instanceof PlayerInventory) {
            switch (index) {
                case 0, 1, 2, 3, 4 -> {
                    args.set(2, 117 + (20 * index));
                    args.set(3, 6);
                }
                case 5, 6, 7, 8 -> {
                    args.set(2, 127 + (20 * (index - 5)));
                    args.set(3, 26);
                }

                case 9 -> {
                    args.set(2, 117);
                    args.set(3, 59);
                }
                case 10 -> {
                    args.set(2, 197);
                    args.set(3, 59);
                }
                default -> {
                    args.set(2, 117 + (20 * ((index - 11) % 5)));
                    args.set(3, 79 + (20 * ((index - 11) / 5)));
                }
            }
        } else {
            args.set(2, 15);
            args.set(3, 60);
        }

    }

    @ModifyArgs(method = "<init>*", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/FurnaceFuelSlot;<init>(Lnet/minecraft/screen/AbstractFurnaceScreenHandler;Lnet/minecraft/inventory/Inventory;III)V"))
    public void moveFuelSlot(Args args) {
        args.set(3, 46);
        args.set(4, 118);
    }

    @ModifyArgs(method = "<init>*", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/FurnaceOutputSlot;<init>(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/inventory/Inventory;III)V"))
    public void moveOutputSlot(Args args) {
        args.set(3, 77);
        args.set(4, 59);
    }

}
