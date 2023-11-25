package net.ramgames.hunters_ui.client.mixins;


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemGroup;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenMixin extends AbstractInventoryScreen<CreativeInventoryScreen.CreativeScreenHandler> {


    @Shadow
    private static ItemGroup selectedTab;

    @Shadow protected abstract void setSelectedTab(ItemGroup group);

    @Shadow
    private boolean shouldShowOperatorTab(PlayerEntity player) {
        return false;
    }

    @Shadow protected abstract boolean hasScrollbar();

    @Shadow private float scrollPosition;

    @Unique
    private static int tabsOffset = 0;


    @Unique
    private static int selectedTabIndex = 0;

    @Unique
    private static ButtonWidget upButton;
    @Unique
    private static ButtonWidget downButton;
    @Unique
    private static ButtonWidget searchButton;
    @Unique
    private static ButtonWidget inventoryButton;
    @Unique
    private static ButtonWidget savedHotBarsButton;



    @Unique
    private static Identifier TABS_TEXTURE = new Identifier("textures/gui/container/creative_inventory/tabs.png");
    @Unique
    private static List<ItemGroup> itemGroups = new ArrayList<>();

    @Unique
    private static ItemGroup searchItemGroup = Registries.ITEM_GROUP.get(new Identifier("search"));

    @Unique
    private static ItemGroup inventoryItemGroup = Registries.ITEM_GROUP.get(new Identifier("inventory"));

    @Unique
    private static ItemGroup savedHotBarsItemGroup = Registries.ITEM_GROUP.get(new Identifier("hotbar"));



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
        context.drawTexture(TABS_TEXTURE, this.x+249, this.y+34, 5, 0, 25, 154);

        for(int i = tabsOffset; i < 7+tabsOffset; i++) if(i < itemGroups.size())
            context.drawItem(itemGroups.get(i).getIcon(), this.x+253, this.y+43+(20 * (i-tabsOffset)));


    }

    @Inject(method = "drawBackground", at = @At("TAIL"))
    public void addTabSelectedHighlight(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        if(selectedTabIndex >= tabsOffset && selectedTabIndex < tabsOffset+7 && selectedTabIndex >= 0) {
            context.drawTexture(TABS_TEXTURE, this.x + 245, this.y + 39 + (20 * (selectedTabIndex - tabsOffset)), 34, 0, 28, 24);
        }
    }

    @Redirect(method = "drawBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/CreativeInventoryScreen;renderTabIcon(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/item/ItemGroup;)V"))
    public void stopTabRendering(CreativeInventoryScreen instance, DrawContext context, ItemGroup group) {

    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    public void detectNewTabsClick(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        int tab = tabHoveringOver((int) mouseX, (int) mouseY);
        if(tab != -1) {
            selectedTabIndex = tab+tabsOffset;
            setSelectedTab(itemGroups.get(tab+tabsOffset));
            cir.setReturnValue(false);
        }
    }

    @Unique
    private int tabHoveringOver(int mouseX, int mouseY) {
        int topLeftX = this.x+250;
        for(int i = 0; i < 7; i++) {
            int topLeftY = this.y+43+(20 * i);
            if(mouseX >= topLeftX && mouseX < topLeftX+16 && mouseY >= topLeftY && mouseY <= topLeftY+16) {
                return i;
            }
        }
        return -1;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void initButtons(PlayerEntity player, FeatureSet enabledFeatures, boolean operatorTabEnabled, CallbackInfo ci) {
        upButton = new ButtonWidget.Builder(Text.empty(), (widget) -> tabsOffset--).size(24,16).build();
        downButton = new ButtonWidget.Builder(Text.empty(), (widget) -> tabsOffset++).size(24,16).build();
        searchButton = new ButtonWidget.Builder(Text.empty(), (widget) -> {
            setSelectedTab(searchItemGroup);
            selectedTabIndex = -1;
        }).size(24,24).tooltip(Tooltip.of(searchItemGroup.getDisplayName())).build();
        inventoryButton = new ButtonWidget.Builder(Text.empty(), (widget) -> {
            setSelectedTab(inventoryItemGroup);
            selectedTabIndex = -2;
        }).size(24,24).tooltip(Tooltip.of(inventoryItemGroup.getDisplayName())).build();
        savedHotBarsButton = new ButtonWidget.Builder(Text.empty(), (widget) -> {
            setSelectedTab(savedHotBarsItemGroup);
            selectedTabIndex = -3;
        }).size(24,24).tooltip(Tooltip.of(savedHotBarsItemGroup.getDisplayName())).build();

        this.addSelectableChild(upButton);
        this.addSelectableChild(downButton);
        this.addSelectableChild(searchButton);
        this.addSelectableChild(inventoryButton);
        this.addSelectableChild(savedHotBarsButton);
    }

    @Inject(method = "handledScreenTick", at = @At("TAIL"))
    private void updateButtons(CallbackInfo ci) {
        if(upButton == null || downButton == null || searchButton == null || inventoryButton == null || savedHotBarsButton == null) return;
        upButton.setPosition(this.x+249,this.y+17);
        downButton.setPosition(this.x+249,this.y+189);
        searchButton.setPosition(this.x+15,this.y+179);
        inventoryButton.setPosition(this.x+41,this.y+179);
        savedHotBarsButton.setPosition(this.x+67,this.y+179);
        upButton.active = tabsOffset != 0;
        downButton.active = tabsOffset+7 < itemGroups.size();
        searchButton.active = selectedTabIndex != -1 && searchItemGroup != null;
        inventoryButton.active = selectedTabIndex != -2 && inventoryItemGroup != null;
        savedHotBarsButton.active = selectedTabIndex != -3 && savedHotBarsItemGroup != null;

    }
    @Inject(method = "init", at = @At("TAIL"))
    private void earlyUpdateTabsButtons(CallbackInfo ci) {
        updateButtons(ci);
    }

    @Inject(method = "renderTabTooltipIfHovered", at = @At("HEAD"), cancellable = true)
    private void stopRenderingOldTabTooltips(DrawContext context, ItemGroup group, int mouseX, int mouseY, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void renderNewTabTooltips(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        int tabIndex = tabHoveringOver(mouseX, mouseY)+tabsOffset;
        if(tabIndex != -1) context.drawTooltip(textRenderer, itemGroups.get(tabIndex).getDisplayName(), mouseX, mouseY);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void updateItemGroupsList(CallbackInfo ci) {

        itemGroups = Registries.ITEM_GROUP.getEntrySet().stream().filter((entry) -> {
            Identifier id = entry.getKey().getValue();
            boolean logic = !id.equals(new Identifier("search")) && !id.equals(new Identifier("hotbar")) && !id.equals(new Identifier("inventory"));
            if(!shouldShowOperatorTab(MinecraftClient.getInstance().player)) logic &= !id.equals(new Identifier("op_blocks"));
            HuntersUIClient.LOGGER.info(id.toString());
            return logic;
        }).map(Map.Entry::getValue).toList();
        while(tabsOffset+6 >= itemGroups.size()) tabsOffset--;
    }

    @Inject(method = "drawBackground", at = @At("TAIL"))
    private void drawButtons(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        if(upButton == null || downButton == null) return;
        context.drawTexture(TABS_TEXTURE, upButton.getX(), upButton.getY(), upButton.active ? 118: 142, 0, upButton.getWidth(), upButton.getHeight());
        context.drawTexture(TABS_TEXTURE, downButton.getX(), downButton.getY(), downButton.active ? 118: 142, 16, downButton.getWidth(), downButton.getHeight());

        context.drawTexture(TABS_TEXTURE, searchButton.getX(), searchButton.getY(),searchButton.active ? 66: 94, 0, searchButton.getWidth(), searchButton.getHeight());
        if(searchItemGroup != null) context.drawItem(searchItemGroup.getIcon(),searchButton.getX()+4, searchButton.getY()+4);

        context.drawTexture(TABS_TEXTURE, inventoryButton.getX(), inventoryButton.getY(),inventoryButton.active ? 66: 94, 0, inventoryButton.getWidth(), inventoryButton.getHeight());
        if(inventoryButton != null) context.drawItem(inventoryItemGroup.getIcon(),inventoryButton.getX()+4, inventoryButton.getY()+4);

        context.drawTexture(TABS_TEXTURE, savedHotBarsButton.getX(), savedHotBarsButton.getY(), savedHotBarsButton.active ? 66: 94, 0, savedHotBarsButton.getWidth(), savedHotBarsButton.getHeight());
        if(savedHotBarsItemGroup != null) context.drawItem(savedHotBarsItemGroup.getIcon(),savedHotBarsButton.getX()+4, savedHotBarsButton.getY()+4);

    }

    @Inject(method = "mouseScrolled", at = @At("HEAD"), cancellable = true)
    private void redefineMouseScrolling(double mouseX, double mouseY, double horizontalAmount, double verticalAmount, CallbackInfoReturnable<Boolean> cir) {
        if((mouseX >= 255 && mouseX <= 362 && mouseY >= 36 && mouseY <= 224) || (mouseX >= 235 && mouseX <= 260 && mouseY >= 67 && mouseY <= 193)) {
            if (!hasScrollbar()) cir.setReturnValue(false);
            else {

                scrollPosition = ((CreativeScreenHandlerAccessor)handler).getScrollPositionInvoker(scrollPosition, verticalAmount);
                handler.scrollItems(this.scrollPosition);
                cir.setReturnValue(true);
            }
            return;
        }
        if(mouseX >= 364 && mouseX <= 388 && mouseY >= 55 && mouseY <= 207) {
            if (verticalAmount < 0) {
                if (tabsOffset + 7 < itemGroups.size()) tabsOffset++;
            }
            else if(tabsOffset > 0) tabsOffset--;
            cir.setReturnValue(true);
            return;
        }
        cir.setReturnValue(false);
    }


}
