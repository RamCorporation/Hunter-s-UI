package net.ramgames.hunters_ui.client;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HuntersUIClient implements ClientModInitializer{

    public static String MOD_ID = "hunters_ui";
    public static Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitializeClient() {
        LOGGER.info("Hunter's UI is booting up!");
    }
}
