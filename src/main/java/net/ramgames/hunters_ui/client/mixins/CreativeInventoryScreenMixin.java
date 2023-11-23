package net.ramgames.hunters_ui.client.mixins;


import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.ramgames.hunters_ui.client.HuntersUIClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.List;
import java.util.Objects;

@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenMixin extends AbstractInventoryScreen<CreativeInventoryScreen.CreativeScreenHandler> {


    @Shadow
    private static ItemGroup selectedTab;

    @Shadow protected abstract void setSelectedTab(ItemGroup group);

    @Unique
    private static int tabsOffset = 0;


    @Unique
    private static int selectedTabIndex = 0;

    @Unique
    private static ButtonWidget upButton;
    @Unique
    private static ButtonWidget downButton;

    @Unique
    private static Identifier TABS_TEXTURE = new Identifier("textures/gui/container/creative_inventory/tabs.png");
    @Unique
    private static List<ItemGroup> itemGroups = Registries.ITEM_GROUP.stream().filter(tab -> {
        String name = tab.getDisplayName().getString();
        return !name.equals("Search Items") && !name.equals("Saved Hotbars") && !name.equals("Survival Inventory");
    }).toList();

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
        args.set(1, 69+(int) args.get(1));
        args.set(2, 4+(int) args.get(2));
    }

    @ModifyArgs(method = "drawBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V"))
    public void moveSlider(Args args) {
        args.set(1, -47+(int) args.get(1));
        args.set(2, 38+(int) args.get(2));
    }

    @Inject(method = "isClickInScrollbar", at = @At("HEAD"), cancellable = true)
    public void moveSliderHitBox(double mouseX, double mouseY, CallbackInfoReturnable<Boolean> cir) {

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
        args.set(2, 194 - (width / 2));
        args.set(3, 206);
    }

    @ModifyArgs(method = "setSelectedTab", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/CreativeInventoryScreen$CreativeSlot;<init>(Lnet/minecraft/screen/slot/Slot;III)V"))
    public void moveCreativeInventorySlots(Args args) {
        int index = args.get(1);
        switch (index) {
            case 0,1,2,3,4 -> {}
            case 36,37,38,39,40 -> {
                args.set(2, 6+(20 * (index-36)));
                args.set(3, 22);
            }
            case 41,42,43,44 -> {
                args.set(2, 16+(20 * (index-41)));
                args.set(3, 42);
            }
            case 5,6,7,8 -> {
                args.set(2, 112);
                args.set(3, 96+(20 * (index-5)));
            }
            case 45 -> {
                args.set(2, 112);
                args.set(3, 65);
            }
            case 9,10 -> {
                args.set(2, 146+(80 * (index-9)));
                args.set(3, 56);
            }

            default -> {
                args.set(2, 146+(20 * ((index-11) % 5)));
                args.set(3, 76+(20 * ((index-11) / 5)));
            }
        }
    }

    @ModifyArgs(method = "setSelectedTab", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;<init>(Lnet/minecraft/inventory/Inventory;III)V"))
    public void moveTrashSlot(Args args) {
        args.set(2, 186);
        args.set(3, 46);
    }

    @ModifyArgs(method = "drawBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/InventoryScreen;drawEntity(Lnet/minecraft/client/gui/DrawContext;IIIIIFFFLnet/minecraft/entity/LivingEntity;)V"))
    public void adjustInventoryPlayerPreview(Args args) {
        args.set(1, this.x+24);
        args.set(2, this.y+76);
        args.set(3, this.x+83);
        args.set(4, this.y+170);
        args.set(5, 45);
    }

    @Inject(method = "drawBackground", at = @At("TAIL"))
    public void addPlayerPreviewToOthers(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        if (selectedTab.getType() != ItemGroup.Type.INVENTORY) {
            InventoryScreen.drawEntity(context, this.x+24, this.y+76, this.x+83, this.y+170, 45, 0.0625F, (float)mouseX, (float)mouseY, this.client.player);
        }
    }

    @Inject(method = "drawBackground", at = @At("HEAD"))
    public void addTabSidebar(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        context.drawTexture(TABS_TEXTURE, this.x+247, this.y+34, 5, 0, 25, 154);
        for(int i = tabsOffset; i < 7+tabsOffset; i++)
            context.drawItem(itemGroups.get(i).getIcon(), this.x+251, this.y+43+(20 * (i-tabsOffset)));
        if(selectedTabIndex >= tabsOffset && selectedTabIndex < tabsOffset+7) {
            context.drawTexture(TABS_TEXTURE, this.x + 247, this.y + 39 + (20 * (selectedTabIndex - tabsOffset)), 38, 0, 24, 24);
        }

    }

    @Redirect(method = "drawBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/CreativeInventoryScreen;renderTabIcon(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/item/ItemGroup;)V"))
    public void stopTabRendering(CreativeInventoryScreen instance, DrawContext context, ItemGroup group) {

    }

    @Inject(method = "mouseClicked", at = @At("HEAD"))
    public void detectNewTabsClick(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        int tab = tabClicked((int) mouseX, (int) mouseY);
        if(tab != -1) {
            setSelectedTab(itemGroups.get(tab+tabsOffset));
        }
    }

    @Unique
    private int tabClicked(int mouseX, int mouseY) {
        int topLeftX = this.x+250;
        for(int i = 0; i < 7; i++) {
            int topLeftY = this.y+43+(20 * i);
            if(mouseX >= topLeftX && mouseX < topLeftX+16 && mouseY >= topLeftY && mouseY <= topLeftY+16) {
                selectedTabIndex = i+tabsOffset;
                return i;
            }
        }
        return -1;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void initTabsButtons(PlayerEntity player, FeatureSet enabledFeatures, boolean operatorTabEnabled, CallbackInfo ci) {
        upButton = new ButtonWidget.Builder(Text.empty(), (widget) -> tabsOffset--).dimensions(this.x+362,this.y+45,34,20).build();
        downButton = new ButtonWidget.Builder(Text.empty(), (widget) -> tabsOffset++).dimensions(this.x+362,this.y+200,34,20).build();
        this.addSelectableChild(upButton);
        this.addSelectableChild(downButton);
    }

    @Inject(method = "handledScreenTick", at = @At("TAIL"))
    private void updateTabsButtons(CallbackInfo ci) {
        if(upButton == null || downButton == null) return;
        upButton.active = tabsOffset != 0;
        downButton.active = tabsOffset+7 < itemGroups.size();
    }

    @Inject(method = "drawBackground", at = @At("TAIL"))
    private void drawTabsButtons(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        if(upButton == null || downButton == null) return;
        context.drawTexture(TABS_TEXTURE, upButton.getX(), upButton.getY(), upButton.active ? 118: 141, 0, 23, 16);
        context.drawTexture(TABS_TEXTURE, downButton.getX(), downButton.getY(), downButton.active ? 118: 141, 16, 23, 16);
    }

}
