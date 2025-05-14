package com.ssblur.sauced;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class SaucedFabric implements ModInitializer, ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Sauced.clientInit();
    }

    @Override
    public void onInitialize() {
        Sauced.init();
    }
}
