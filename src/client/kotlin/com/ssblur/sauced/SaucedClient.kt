package com.ssblur.sauced

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.minecraft.core.component.DataComponents

object SaucedClient : ClientModInitializer {
	override fun onInitializeClient() {
        Sauced.LOGGER.info("Registering Sauced client")

		ColorProviderRegistry.ITEM.register({ stack, layer ->
            stack.get(DataComponents.POTION_CONTENTS)?.color ?: 0xFFFFFF
        }, Sauced.SAUCE_ITEM)
	}
}