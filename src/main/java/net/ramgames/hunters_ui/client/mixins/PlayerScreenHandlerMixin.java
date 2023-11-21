package net.ramgames.hunters_ui.client.mixins;


import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerMixin extends AbstractRecipeScreenHandler<RecipeInputInventory> {






    public PlayerScreenHandlerMixin(ScreenHandlerType<?> screenHandlerType, int i) {
        super(screenHandlerType, i);
    }


    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/CraftingResultSlot;<init>(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/inventory/RecipeInputInventory;Lnet/minecraft/inventory/Inventory;III)V"), slice = @Slice(from = @At("HEAD"), to = @At(value = "INVOKE", target = "Lnet/minecraft/screen/PlayerScreenHandler;addSlot(Lnet/minecraft/screen/slot/Slot;)Lnet/minecraft/screen/slot/Slot;", shift = At.Shift.AFTER)))
    private void MoveCraftingResultSlot(Args args) {
        args.set(4, 58);
        args.set(5, 145);
    }

    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "net/minecraft/screen/PlayerScreenHandler$1.<init> (Lnet/minecraft/screen/PlayerScreenHandler;Lnet/minecraft/inventory/Inventory;IIILnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/EquipmentSlot;)V"))
    private void MoveArmorSlots(Args args) {
        switch ((int) args.get(2)) {
            case 39 -> {
                args.set(3, 85);
                args.set(4, 42);
            }
            case 38 -> {
                args.set(3, 85);
                args.set(4, 62);
            }
            case 37 -> {
                args.set(3, 85);
                args.set(4, 82);
            }
            case 36 -> {
                args.set(3, 85);
                args.set(4, 102);
            }
        }
    }

    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "net/minecraft/screen/PlayerScreenHandler$2.<init> (Lnet/minecraft/screen/PlayerScreenHandler;Lnet/minecraft/inventory/Inventory;IIILnet/minecraft/entity/player/PlayerEntity;)V"))
    private void MoveOffhandSlot(Args args) {
        args.set(3, 85);
        args.set(4, 11);
    }



    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "net/minecraft/screen/slot/Slot.<init> (Lnet/minecraft/inventory/Inventory;III)V"))
    private void MoveInventorySlots(Args args) {
        int index = args.get(1);
        if(args.get(0) instanceof RecipeInputInventory) {
            switch (index) {
                case 0 -> {
                    args.set(2, 6);
                    args.set(3, 125);
                }
                case 1 -> {
                    args.set(2, 26);
                    args.set(3, 125);
                }
                case 2 -> {
                    args.set(2, 6);
                    args.set(3, 145);
                }
                case 3 -> {
                    args.set(2, 26);
                    args.set(3, 145);
                }
            }
        } else switch (index) {
            case 0,1,2,3,4 -> {
                args.set(2, 117+(20 * index));
                args.set(3, 6);
            }
            case 5,6,7,8 -> {
                args.set(2, 127+(20 * (index-5)));
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
                args.set(2, 117+(20 * ((index-11) % 5)));
                args.set(3, 79+(20 * ((index-11) / 5)));
            }
        }
    }
}
