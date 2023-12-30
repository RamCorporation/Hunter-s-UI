package net.ramgames.hunters_ui.client.mixins;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(CraftingScreenHandler.class)
public abstract class CraftingScreenHandlerMixin extends AbstractRecipeScreenHandler<RecipeInputInventory> {



    public CraftingScreenHandlerMixin(ScreenHandlerType<?> screenHandlerType, int i) {
        super(screenHandlerType, i);
    }

    @ModifyArgs(method = "<init>*", at = @At(value = "INVOKE", target = "net/minecraft/screen/slot/Slot.<init> (Lnet/minecraft/inventory/Inventory;III)V"))
    private void MoveInventorySlots(Args args) {
        int index = args.get(1);
        if(args.get(0) instanceof RecipeInputInventory) {
            args.set(2, 25+20*(index % 3));
            args.set(3, 67+20*(index / 3));
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

    @ModifyArgs(method = "<init>*", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/CraftingResultSlot;<init>(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/inventory/RecipeInputInventory;Lnet/minecraft/inventory/Inventory;III)V"))
    public void moveOutputSlots(Args args) {
        args.set(4, 45);
        args.set(5, 139);
    }



}
