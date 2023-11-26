package net.ramgames.hunters_ui.client.mixins;

import com.bawnorton.mixinsquared.api.MixinCanceller;

import java.util.List;

public class ModMixinCanceller implements MixinCanceller {

    @Override
    public boolean shouldCancel(List<String> targetClassNames, String mixinClassName) {
        return mixinClassName.equals("net.fabricmc.fabric.mixin.itemgroup.client.CreativeInventoryScreenMixin");
    }
}
