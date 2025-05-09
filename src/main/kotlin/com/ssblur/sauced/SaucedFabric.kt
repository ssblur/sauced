package com.ssblur.sauced

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.ModInitializer


object SaucedFabric : ModInitializer, ClientModInitializer {
    override fun onInitialize() {
        Sauced.init()
    }

    override fun onInitializeClient() {
        Sauced.clientInit()
    }
}