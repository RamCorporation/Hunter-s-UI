package net.ramgames.hunters_ui.client.mixins;


import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.ramgames.hunters_ui.client.HuntersUIClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.logging.Logger;

@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerMixin extends AbstractRecipeScreenHandler<RecipeInputInventory> {






    public PlayerScreenHandlerMixin(ScreenHandlerType<?> screenHandlerType, int i) {
        super(screenHandlerType, i);
    }


    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/CraftingResultSlot;<init>(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/inventory/RecipeInputInventory;Lnet/minecraft/inventory/Inventory;III)V"), slice = @Slice(from = @At("HEAD"), to = @At(value = "INVOKE", target = "Lnet/minecraft/screen/PlayerScreenHandler;addSlot(Lnet/minecraft/screen/slot/Slot;)Lnet/minecraft/screen/slot/Slot;", shift = At.Shift.AFTER)))
    private void setCraftingResultArgs(Args args) {
        args.set(4, 58);
        args.set(5, 145);
    }

    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "net/minecraft/screen/PlayerScreenHandler$1.<init> (Lnet/minecraft/screen/PlayerScreenHandler;Lnet/minecraft/inventory/Inventory;IIILnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/EquipmentSlot;)V"))
    private void setArmorArgs(Args args) {
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
    private void setOffHandArgs(Args args) {
        args.set(3, 85);
        args.set(4, 11);
    }



    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "net/minecraft/screen/slot/Slot.<init> (Lnet/minecraft/inventory/Inventory;III)V"))
    private void setSlotArgs(Args args) {
        for(int i = 0; i < ((Inventory) args.get(0)).size(); i++) HuntersUIClient.LOGGER.info("item in {}: {}", i, ((Inventory) args.get(0)).getStack(i));
        if(args.get(0) instanceof RecipeInputInventory) {
            switch ((int) args.get(1)) {
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
        } else switch ((int) args.get(1)) {
            case 0 -> {
                args.set(2, 117);
                args.set(3, 6);
            }
            case 1 -> {
                args.set(2, 137);
                args.set(3, 6);
            }
            case 2 -> {
                args.set(2, 157);
                args.set(3, 6);
            }
            case 3 -> {
                args.set(2, 177);
                args.set(3, 6);
            }
            case 4 -> {
                args.set(2, 197);
                args.set(3, 6);
            }
            case 5 -> {
                args.set(2, 127);
                args.set(3, 26);
            }
            case 6 -> {
                args.set(2, 147);
                args.set(3, 26);
            }
            case 7 -> {
                args.set(2, 167);
                args.set(3, 26);
            }
            case 8 -> {
                args.set(2, 187);
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

            case 11 -> {
                args.set(2, 117);
                args.set(3, 79);
            }
            case 12 -> {
                args.set(2, 137);
                args.set(3, 79);
            }
            case 13 -> {
                args.set(2, 157);
                args.set(3, 79);
            }
            case 14 -> {
                args.set(2, 177);
                args.set(3, 79);
            }
            case 15 -> {
                args.set(2, 197);
                args.set(3, 79);
            }

            case 16 -> {
                args.set(2, 117);
                args.set(3, 99);
            }
            case 17 -> {
                args.set(2, 137);
                args.set(3, 99);
            }
            case 18 -> {
                args.set(2, 157);
                args.set(3, 99);
            }
            case 19 -> {
                args.set(2, 177);
                args.set(3, 99);
            }
            case 20 -> {
                args.set(2, 197);
                args.set(3, 99);
            }

            case 21 -> {
                args.set(2, 117);
                args.set(3, 119);
            }
            case 22 -> {
                args.set(2, 137);
                args.set(3, 119);
            }
            case 23 -> {
                args.set(2, 157);
                args.set(3, 119);
            }
            case 24 -> {
                args.set(2, 177);
                args.set(3, 119);
            }
            case 25 -> {
                args.set(2, 197);
                args.set(3, 119);
            }

            case 26 -> {
                args.set(2, 117);
                args.set(3, 139);
            }
            case 27 -> {
                args.set(2, 137);
                args.set(3, 139);
            }
            case 28 -> {
                args.set(2, 157);
                args.set(3, 139);
            }
            case 29 -> {
                args.set(2, 177);
                args.set(3, 139);
            }
            case 30 -> {
                args.set(2, 197);
                args.set(3, 139);
            }

            case 31 -> {
                args.set(2, 117);
                args.set(3, 159);
            }
            case 32 -> {
                args.set(2, 137);
                args.set(3, 159);
            }
            case 33 -> {
                args.set(2, 157);
                args.set(3, 159);
            }
            case 34 -> {
                args.set(2, 177);
                args.set(3, 159);
            }
            case 35 -> {
                args.set(2, 197);
                args.set(3, 159);
            }
        }
    }
}
